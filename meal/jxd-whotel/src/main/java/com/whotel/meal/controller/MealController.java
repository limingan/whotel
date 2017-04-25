package com.whotel.meal.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whotel.card.entity.Member;
import com.whotel.meal.controller.req.ListHotelReq;
import com.whotel.meal.controller.req.ListRestaurantReq;
import com.whotel.meal.controller.req.PageOrderReq;
import com.whotel.meal.entity.*;
import com.whotel.meal.service.*;
import com.whotel.thirdparty.jxd.mode.HotelCityQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelCityVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.card.service.MemberTradeService;
import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.enums.PayMent;
import com.whotel.common.enums.PayMode;
import com.whotel.common.enums.TradeStatus;
import com.whotel.common.util.BeanUtil;
import com.whotel.common.util.DateUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.enums.ModuleType;
import com.whotel.front.controller.FanBaseController;
import com.whotel.front.entity.PayOrder;
import com.whotel.front.entity.WeixinFan;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.service.HotelService;
import com.whotel.meal.enums.MealOrderStatus;
import com.whotel.meal.enums.MealType;
import com.whotel.thirdparty.jxd.mode.HotelBranchQuery;
import com.whotel.thirdparty.jxd.mode.MealTabQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberCouponVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.weixin.bean.Location;
import com.whotel.weixin.service.LocationService;
import com.whotel.weixin.service.WeixinMessageService;

@Controller
public class MealController extends FanBaseController {

    private static final Logger logger = Logger.getLogger(MealController.class);

    @Autowired
    private MealService mealService;

    @Autowired
    private MemberTradeService memberTradeService;

    @Autowired
    private MealOrderService mealOrderService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private WeixinMessageService weixinMessageService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishesCategoryService dishesCategoryService;

    @Autowired
    private DishesService dishesService;

    @Autowired
    private DishesPracticeService dishesPracticeService;



    /**
     * 分店列表
     *
     * @param req
     * @param param
     * @return
     */
    @RequestMapping("/oauth/meal/list")
    public String list(HttpServletRequest req, ListHotelReq param) {
        Company company = getCurrentCompany(req);
        String companyId = company.getId();
        List<HotelCityVO> hotelCitys = hotelService.listHotelCityVO(companyId, new HotelCityQuery());
        req.setAttribute("cityList", hotelCitys);

        param.setCompanyId(companyId);
        req.setAttribute("hotelList", this.getHotelList(param));
        return "meal/webPage/restlist";
    }

    private List<Hotel> getHotelList(ListHotelReq param) {
        String city = "";
        String keyword = "";
        try {
            if(StringUtils.isNotEmpty(param.getCity())){
                city = URLDecoder.decode(param.getCity(), "UTF-8");
            }
            if(StringUtils.isNotEmpty(param.getKeyword())){
                keyword = URLDecoder.decode(param.getKeyword(), "UTF-8");
            }
        } catch (Exception e) {
            logger.error("MealController getHotelList", e);
        }
        param.setCity(city);
        param.setKeyword(keyword);
        List<Hotel> result = Lists.newArrayList();
        List<Hotel> list = hotelService.findHotel(param);
        Date current = new Date();
        Double lng = param.getLng();//经度
        Double lat = param.getLat();//纬度


        Map<Double, Hotel> map = Maps.newTreeMap();
        List<Hotel> blankList = Lists.newArrayList();

        Integer currentTime = Integer.parseInt(DateUtil.format(current, "HHmmss"));
        for (Hotel hotel : list) {
            Integer openTime = hotel.getOpenTime();
            Integer closeTime = hotel.getCloseTime();
            if (null != openTime && null != closeTime && (currentTime < openTime || currentTime > closeTime)) {
                hotel.setIsClose(true);
            } else {
                hotel.setIsClose(false);
            }

            Double hotelLng = hotel.getLng();
            Double hotelLat = hotel.getLat();
            if (null != hotelLat && null != hotelLng && null != lat && null != lng) {
                Double distance = getDistance(hotelLng, hotelLat, lng, lat);
                hotel.setDistance(formatDistance(distance));
                if (map.containsKey(distance)) {
                    distance = distance + 1;
                }
                map.put(distance, hotel);
            } else {
                hotel.setDistance("未知");
                blankList.add(hotel);
            }
        }
        if (null != param.getSortType() && 0 != param.getSortType()) {
            if (1 == param.getSortType()) {
                List<Hotel> openList = Lists.newArrayList();
                List<Hotel> closeList = Lists.newArrayList();
                for (Hotel hotel : map.values()) {
                    if (hotel.getIsClose()) {
                        closeList.add(hotel);
                    } else {
                        openList.add(hotel);
                    }
                }
                for (Hotel hotel : blankList) {
                    if (hotel.getIsClose()) {
                        closeList.add(hotel);
                    } else {
                        openList.add(hotel);
                    }
                }
                result.addAll(openList);
                result.addAll(closeList);
            } else if (2 == param.getSortType()) {
                for (Hotel hotel : map.values()) {
                    result.add(hotel);
                }
                result.addAll(blankList);
            } else {
                result.addAll(list);
            }
        } else {
            result.addAll(list);
        }
        return result;
    }

    private static String formatDistance(Double distance) {
        String result;
        long longValue = (long) Math.floor(distance);
        if (longValue < 1000) {
            result = longValue + "m";
        } else {
            longValue = (long) Math.floor(longValue / 1000);
            result = longValue + "km";
        }
        return result;
    }

    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1 第一点经度
     * @param lat1  第一点纬度
     * @param long2 第二点经度
     * @param lat2  第二点纬度
     * @return 返回距离 单位：米
     */
    public static double getDistance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    /**
     * 分厅列表
     * @param req
     * @param param
     * @return
     */
    @RequestMapping("/oauth/meal/restaurant")
    public String restlist(HttpServletRequest req, ListRestaurantReq param) {
        Company company = getCurrentCompany(req);
        String companyId = company.getId();
        param.setCompanyId(companyId);
        List<Restaurant> list = restaurantService.getByParam(param);
        req.setAttribute("restList",list);
        return "meal/webPage/restaurantList";
    }


    /**
     * 菜品分类列表
     * @param req
     * @param restaurantId
     * @return
     */
    @RequestMapping("/oauth/meal/dishCatList")
    public String dishCatList(HttpServletRequest req, String restaurantId) {
        Restaurant restaurant = restaurantService.getById(restaurantId);
        List<DishesCategory> cateList = dishesCategoryService.getByRestaurant(restaurant);
        for(DishesCategory category :cateList){
            List<Dishes> list = dishesService.getByCate(category);
            category.setDishesList(list);
        }

        req.setAttribute("rest", restaurant);
        req.setAttribute("bannerList", restaurant.getBannerUrls());
        req.setAttribute("cateList", cateList);
        return "meal/webPage/list";
    }

    /**
     * 搜索
     * @param req
     * @return
     */
    @RequestMapping("oauth/meal/search")
    public String search(HttpServletRequest req,String keyword){
        List<String> list = Lists.newArrayList();
        list.add("明月山");
        list.add("捷信达");
        list.add("华天");
        list.add("厦门");
        list.add("南昌");

        Company company = getCurrentCompany(req);
        String companyId = company.getId();
        ListHotelReq param = new ListHotelReq();
        String word = "";
        if(StringUtils.isNotEmpty(keyword)){
            try {
                word = URLDecoder.decode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("MealController search",e);
            }
        }
        param.setCompanyId(companyId);
        param.setKeyword(word);
        List<Hotel> hotels = hotelService.findHotel(param);
        req.setAttribute("keywordList", list);
        req.setAttribute("list",hotels);
        return "meal/webPage/search";
    }


    /**
     * 订单列表查询
     * @param req
     * @param param
     * @return
     */
    @RequestMapping("/oauth/meal/orderList")
    public String orderList(HttpServletRequest req,PageOrderReq param){
        Company company = getCurrentCompany(req);
        Member member = getCurrentMember(req);
        param.setCompanyId(company.getId());
        param.setOpenId(member.getOpenId());
        Page<MealOrder> orderPage = mealOrderService.pageQuery(param);
        req.setAttribute("orderPage",orderPage);
        return "meal/webPage/order";
    }

    @RequestMapping("/oauth/meal/orderDetail")
    public String orderDetail(HttpServletRequest req,String orderId){
        Company company = getCurrentCompany(req);
        Member member = getCurrentMember(req);

        return "meal/webPage/orderDetail";
    }



    /**---------------------------------------------------*/
    /**
     * 分店查询
     *
     * @param req
     * @return
     */
    @RequestMapping("/oauth/meal/mealBranchSearch")
    public String mealBranchSearch(HttpServletRequest req) {
        Company company = getCurrentCompany(req);
        HotelBranchQuery query = new HotelBranchQuery();
        query.setOpType("分店列表");
//		Calendar cal = Calendar.getInstance();
//		query.setBeginDate(DateUtil.format(cal.getTime(), "yyyy-MM-dd"));
//		cal.add(Calendar.DATE, 1);
//		query.setEndDate(DateUtil.format(cal.getTime(), "yyyy-MM-dd"));
        List<HotelBranchVO> hotelBranchs = hotelService.listHotelBranchVO(company.getId(), query);
        //判断是单店还是集团,多个分店为集团
        if (company.getGroup() != null && company.getGroup()) {//集团
            req.setAttribute("hotelBranchs", hotelBranchs);
//			List<HotelCityVO> hotelCitys = hotelService.listHotelCityVO(company.getId(), new HotelCityQuery());
//			req.setAttribute("hotelCitys", hotelCitys);
            return "front/meal/mealBranch_list";
        }
        return listRestaurant(hotelBranchs.get(0).getCode(), req);
    }

    /**
     * 餐厅列表
     *
     * @param req
     * @return
     */
    @RequestMapping("/oauth/meal/listRestaurant")
    public String listRestaurant(String hotelCode, HttpServletRequest req) {
        req.getSession().removeAttribute(Constants.Session.MEAL_BOOK);

        Company company = getCurrentCompany(req);
        Page<Restaurant> page = new Page<>();
        page.addFilter("companyId", FilterModel.EQ, company.getId());
        page.addFilter("hotelCode", FilterModel.EQ, hotelCode);
        page.addFilter("isEnable", FilterModel.EQ, true);
        page.addOrder("orderIndex", true);
        page.setPageSize(8);
        List<Restaurant> restaurantList = mealService.findRestaurantList(page).getResult();
        //单个的餐厅直接进入，多个提供选择
        if (restaurantList.size() == 1) {
            restaurantInfo(restaurantList.get(0).getId(), req);
        }
        req.setAttribute("page", page);
        req.setAttribute("hotelCode", hotelCode);
        return "front/meal/restaurant_list";
    }

    @RequestMapping("/oauth/meal/ajaxListRestaurant")
    @ResponseBody
    public Page<Restaurant> ajaxListRestaurant(String companyId, String hotelCode, Integer pageNo) {
        Page<Restaurant> page = new Page<>();
        page.addFilter("companyId", FilterModel.EQ, companyId);
        page.addFilter("hotelCode", FilterModel.EQ, hotelCode);
        page.addOrder("orderIndex", true);
        page.setPageSize(8);
        page.setPageNo(pageNo);
        mealService.findRestaurantList(page);
        return page;
    }

    /**
     * 餐厅详情
     *
     * @param req
     * @return
     */
    @RequestMapping("/oauth/meal/restaurantInfo")
    public String restaurantInfo(String restaurantId, HttpServletRequest req) {
        Restaurant restaurant = mealService.getRestaurantById(restaurantId);
        req.setAttribute("restaurant", restaurant);

        MealOrder mealOrder = (MealOrder) req.getSession().getAttribute(Constants.Session.MEAL_BOOK);
        if (mealOrder == null) {
            mealOrder = new MealOrder();
            mealOrder.setCompanyId(restaurant.getCompanyId());
            mealOrder.setHotelCode(restaurant.getHotelCode());
            mealOrder.setRestaurantId(restaurantId);
            mealOrder.setName(restaurant.getName());
            mealOrder.setArrDate(new Date());
        }
        req.getSession().setAttribute(Constants.Session.MEAL_BOOK, mealOrder);

        WeixinFan fan = getCurrentFan(req);
        if (fan != null) {
            Location location = LocationService.getLocationService().getLocation(fan.getOpenId());
            req.setAttribute("location", location);
        }
        return "front/meal/restaurant_info";
    }

    /**
     * 餐台预订
     */
    @RequestMapping("/oauth/meal/mealTabList")
    public String mealTabList(MealOrder mealOrder, HttpServletRequest req) {

        if (mealOrder != null) {
            req.setAttribute("contactName", mealOrder.getContactName());
            req.setAttribute("contactMobile", mealOrder.getContactMobile());
        }

        Restaurant restaurant = mealService.getRestaurantById(mealOrder.getRestaurantId());
        if (restaurant == null) {
            return "front/meal/mealTab_list";
        }
        assignmentMealBook(mealOrder, req);

        MealTabQuery query = new MealTabQuery();
        query.setHotelCode(restaurant.getHotelCode());
        query.setRefeNo(restaurant.getRefeNo());
        String orderDate = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
        if (mealOrder.getArrDate() != null) {
            orderDate = DateUtil.formatDate(mealOrder.getArrDate()) + " " + mealOrder.getArriveTime();
        }
        query.setOrderDate(orderDate);
        Map<String, Integer> mealTabMap = mealService.findCanBookMealTabMap(query, restaurant.getCompanyId());
        req.setAttribute("mealTabMap", mealTabMap);

        Page<MealTab> page = new Page<>();
        page.addFilter("companyId", FilterModel.EQ, restaurant.getCompanyId());
        page.addFilter("hotelCode", FilterModel.EQ, restaurant.getHotelCode());
        page.addFilter("restaurantId", FilterModel.EQ, restaurant.getId());
//		page.addFilter("isEnable",FilterModel.EQ, true);//可预订的

        if (mealOrder.getGuestNum() != null) {
            page.addFilter("seats", FilterModel.GE, mealOrder.getGuestNum());
        }
        page.addOrder("timeStamp", false);
        //page.addOrder("orderIndex", false);
        page.setPageSize(8);
        mealService.findMealTabList(page);
        req.setAttribute("page", page);
        return "front/meal/mealTab_list";
    }

    @RequestMapping("/oauth/meal/ajaxListMealTab")
    @ResponseBody
    public Page<MealTab> ajaxListMealTab(MealOrder mealOrder, Integer pageNo) {
        Restaurant restaurant = mealService.getRestaurantById(mealOrder.getRestaurantId());
        Page<MealTab> page = new Page<>();
        page.addFilter("companyId", FilterModel.EQ, restaurant.getCompanyId());
        page.addFilter("hotelCode", FilterModel.EQ, restaurant.getHotelCode());
        page.addFilter("restaurantId", FilterModel.EQ, restaurant.getId());
//		page.addFilter("isEnable",FilterModel.EQ, true);//可预订的

        if (mealOrder.getGuestNum() != null) {
            page.addFilter("seats", FilterModel.GE, mealOrder.getGuestNum());
        }
        page.addOrder("timeStamp", false);
//		page.addOrder("orderIndex", false);
        page.setPageSize(8);
        page.setPageNo(pageNo);
        mealService.findMealTabList(page);

        MealTabQuery query = new MealTabQuery();
        query.setHotelCode(restaurant.getHotelCode());
        query.setRefeNo(restaurant.getRefeNo());
        String orderDate = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
        if (mealOrder.getArrDate() != null) {
            orderDate = DateUtil.formatDate(mealOrder.getArrDate()) + " " + mealOrder.getArriveTime();
        }
        query.setOrderDate(orderDate);
        List<MealTab> list = mealService.findCanBookMealTabList(query, restaurant.getCompanyId());

        Map<String, Integer> mealTabMap = new HashMap<String, Integer>();
        for (MealTab mealTab : list) {
            mealTabMap.put(mealTab.getTabNo(), mealTab.getStatus());
        }
        for (MealTab mealTab : page.getResult()) {
            mealTab.setStatus(mealTabMap.get(mealTab.getTabNo()));
        }
        return page;
    }

    /**
     * 订菜
     *
     * @return
     */
    @RequestMapping("/oauth/meal/dishesList")
    public String dishesList(MealOrder mealOrder, HttpServletRequest req) {
//		Company company = getCurrentCompany(req);
        assignmentMealBook(mealOrder, req);

        MealOrder updateMealOrder = (MealOrder) req.getSession().getAttribute(Constants.Session.MEAL_BOOK);
        String restaurantId = updateMealOrder.getRestaurantId();
        List<Shuffle> shuffles = mealService.findShufflesByRest(null, restaurantId);
        req.setAttribute("shuffles", shuffles);

        Page<Dishes> page = new Page<>();
        page.addFilter("companyId", FilterModel.EQ, updateMealOrder.getCompanyId());
        page.addFilter("hotelCode", FilterModel.EQ, updateMealOrder.getHotelCode());
        page.addFilter("restaurantId", FilterModel.EQ, restaurantId);
        page.addFilter("isEnable", FilterModel.EQ, true);//上架的

        //判断之前选择的餐式在当前餐厅中是否存在
        boolean isExist = true;
        if (StringUtils.isNotBlank(updateMealOrder.getShuffleNo())) {
            Shuffle shuffle = mealService.getShuffleById(updateMealOrder.getShuffleNo());
            if (StringUtils.equals(shuffle.getRestaurantId(), restaurantId)) {
                isExist = false;
            }
        }

        if (isExist) {
            //查找时间最接近的一个餐式
            for (Shuffle shuffle : shuffles) {
                Integer end = Integer.valueOf(shuffle.getEndTime().replace(":", ""));
                Integer now = Integer.valueOf(DateUtil.format(new Date(), "HH:mm").replace(":", ""));
                if (now <= end) {
                    updateMealOrder.setShuffleNo(shuffle.getId());
                    updateMealOrder.setShuffleName(shuffle.getShuffleName());
                    break;
                }
            }

            //指定第一个餐式
            if (StringUtils.isBlank(updateMealOrder.getShuffleNo())) {
                updateMealOrder.setShuffleNo(shuffles.get(0).getId());
                updateMealOrder.setShuffleName(shuffles.get(0).getShuffleName());
            }
        }

        page.addFilter("shuffleNo", FilterModel.LIKE, updateMealOrder.getShuffleNo());

        page.addOrder("orderIndex", false);
        page.setPageSize(8);
        mealService.findDishesList(page);
        req.setAttribute("page", page);

        req.getSession().setAttribute(Constants.Session.MEAL_BOOK, updateMealOrder);

        List<DishesCategory> dishesCategories = mealService.findDishCatByRestaurantId(null, restaurantId);
        req.setAttribute("dishesCategories", dishesCategories);

        //可选时间
        Calendar cal = Calendar.getInstance();
        //cal.setTime(updateMealOrder.getArrDate());
//		cal.add(Calendar.DATE, -1);
//		req.setAttribute("startDate", cal.getTime());
        cal.add(Calendar.YEAR, 10);
        req.setAttribute("endDate", cal.getTime());
        return "front/meal/dishes_list";
    }

    /**
     * 订菜
     *
     * @return
     */
    @RequestMapping("/oauth/meal/ajaxDishesList")
    @ResponseBody
    public List<Dishes> ajaxDishesList(Integer pageNo, String dishno1, String shuffleNo, HttpServletRequest req) {
        MealOrder mealOrder = (MealOrder) req.getSession().getAttribute(Constants.Session.MEAL_BOOK);
        Page<Dishes> page = new Page<>();
        page.addFilter("companyId", FilterModel.EQ, mealOrder.getCompanyId());
        page.addFilter("hotelCode", FilterModel.EQ, mealOrder.getHotelCode());
        page.addFilter("restaurantId", FilterModel.EQ, mealOrder.getRestaurantId());
        page.addFilter("isEnable", FilterModel.EQ, true);//上架的

        if (StringUtils.isNotBlank(shuffleNo)) {
            page.addFilter("shuffleNo", FilterModel.LIKE, shuffleNo);
        }
        if (StringUtils.isNotBlank(dishno1) && !dishno1.equals("-1")) {
            page.addFilter("dishno1", FilterModel.LIKE, dishno1);
        }
        page.addOrder("orderIndex", false);
        page.setPageNo(pageNo);
        page.setPageSize(8);
        mealService.findDishesList(page);
        return page.getResult();
    }

    /**
     * 菜肴详情
     *
     * @param req
     * @return
     */
    @RequestMapping("/oauth/meal/dishesInfo")
    public String dishesInfo(String dishesId, MealOrder mealOrder, HttpServletRequest req) {
        Dishes dishes = mealService.getDishesById(dishesId);
        req.setAttribute("dishes", dishes);
        statisticalTotalPrice(mealOrder, req);
        return "front/meal/dishes_info";
    }

    /**
     * 计算菜的总价
     */
    @RequestMapping("/oauth/meal/statisticalTotalPrice")
    public String statisticalTotalPrice(MealOrder mealOrder, HttpServletRequest req) {
        MealOrder updateMealOrder = (MealOrder) req.getSession().getAttribute(Constants.Session.MEAL_BOOK);
        Float totalFee = 0.0f;
        List<MealOrderItem> items = new ArrayList<>();
        if (mealOrder.getItems() != null && mealOrder.getItems().size() > 0) {
            for (MealOrderItem item : mealOrder.getItems()) {
                if (item != null && StringUtils.isNotBlank(item.getDishesId()) && item.getItemQuantity() != null && item.getItemQuantity() > 0) {
                    Dishes dishes = mealService.getDishesById(item.getDishesId());
                    item.setItemCode(dishes.getDishNo());//菜式代码
                    item.setName(dishes.getDishName());//菜式名称
                    item.setUnit(dishes.getUnit());//单位
                    item.setItemPrice(dishes.getPrice());//价格

                    Float itemAmount = item.getItemQuantity() * item.getItemPrice();
                    String formatAmount = new DecimalFormat("#.00").format(itemAmount);
                    itemAmount = Float.valueOf(formatAmount);

                    item.setItemAmount(itemAmount);//消费项目金额
                    items.add(item);
                    totalFee += itemAmount;
                }
            }
        }
        updateMealOrder.setItems(items);
        updateMealOrder.setTotalFee(totalFee);
        updateMealOrder.setDishesPrice(totalFee);
        if (StringUtils.isNotBlank(mealOrder.getShuffleNo())) {
            updateMealOrder.setShuffleNo(mealOrder.getShuffleNo());
            updateMealOrder.setShuffleName(mealOrder.getShuffleName());
        }
        req.getSession().setAttribute(Constants.Session.MEAL_BOOK, updateMealOrder);
        return "redirect:/oauth/meal/dishesList.do";
    }

    /**
     * 菜肴预订
     *
     * @return
     */
    @RequestMapping("/oauth/meal/dishesBook")
    public String dishesBook(MealOrder mealOrder, String isRoom,
                             @RequestParam(required = false) String contactName,
                             @RequestParam(required = false) String contactMobile, HttpServletRequest req) {

        if (contactName != null && contactName.trim().length() > 0) {
            req.setAttribute("contactName", mealOrder.getContactName().trim());
        }
        if (contactMobile != null && contactMobile.trim().length() > 0) {
            req.setAttribute("contactMobile", mealOrder.getContactMobile().trim());
        }

        Company company = getCurrentCompany(req);
        assignmentMealBook(mealOrder, req);

        if (StringUtils.isBlank(isRoom)) {//只订餐
            statisticalTotalPrice(mealOrder, req);
        }

        MealOrder updateMealOrder = (MealOrder) req.getSession().getAttribute(Constants.Session.MEAL_BOOK);
        Float totalFee = updateMealOrder.getDishesPrice() == null ? 0f : updateMealOrder.getDishesPrice();//菜的总价

        if (mealOrder.getItems() != null) {
            if (updateMealOrder.getRestaurant() != null) {//服务费率
                Float serviceFee = totalFee * updateMealOrder.getRestaurant().getServiceFee() / 100;
                updateMealOrder.setServiceFee(serviceFee);
                totalFee += serviceFee;
            }
        }

        Float serviceFee = updateMealOrder.getServiceFee();
        if (serviceFee != null && serviceFee > 0) {
            totalFee += serviceFee;
        }

        if (StringUtils.isNotBlank(mealOrder.getMealTabId())) {
            updateMealOrder.setMealTabId(mealOrder.getMealTabId());
            MealTab mealTab = updateMealOrder.getMealTab();
            if (mealTab != null && mealTab.getDeposit() != null) {//包间价格
                totalFee += updateMealOrder.getMealTab().getDeposit();
            }
        }

        String formatTotalFee = new DecimalFormat("#.00").format(totalFee);
        totalFee = Float.valueOf(formatTotalFee);
        updateMealOrder.setTotalFee(totalFee);


        WeixinFan weixinFan = getCurrentFan(req);
        MemberVO memberVO = getCurrentMemberVO(req);
        if (memberVO != null) {
            List<MemberCouponVO> memberCoupons = memberTradeService.findMemberUseAbleCouponVO(company.getId(), updateMealOrder.getTotalFee(), memberVO.getProfileId(), company.getCode(), ModuleType.MEAL);
            req.setAttribute("memberCoupons", memberCoupons);
            updateMealOrder.setContactName(memberVO.getGuestCName());
            updateMealOrder.setContactMobile(memberVO.getMobile());
        } else {
            MealOrder lastMealOrder = mealOrderService.getLastestMealOrderByOpenId(weixinFan.getOpenId());
            if (lastMealOrder != null) {
                updateMealOrder.setContactName(lastMealOrder.getContactName());
                updateMealOrder.setContactMobile(lastMealOrder.getContactMobile());
            }
        }
        req.getSession().setAttribute(Constants.Session.MEAL_BOOK, updateMealOrder);

        List<Shuffle> shuffles = mealService.findShufflesByRest(null, updateMealOrder.getRestaurantId());
        req.setAttribute("shuffles", shuffles);

        Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, 1);
//		req.setAttribute("arrAfterDate", cal.getTime());

//		cal.add(Calendar.DATE, -1);
//		req.setAttribute("startDate", cal.getTime());
        cal.add(Calendar.YEAR, 10);
        req.setAttribute("endDate", cal.getTime());
        return "front/meal/meal_ordering";
    }

    /**
     * 给MEAL_BOOKr赋值
     *
     * @param mealOrder
     */
    public void assignmentMealBook(MealOrder mealOrder, HttpServletRequest req) {
        MealOrder updateMealOrder = (MealOrder) req.getSession().getAttribute(Constants.Session.MEAL_BOOK);
        String restaurantId = mealOrder.getRestaurantId();
        if (StringUtils.isNotBlank(restaurantId)) {
            Restaurant restaurant = mealService.getRestaurantById(restaurantId);
            if (updateMealOrder == null) {
                updateMealOrder = new MealOrder();
                updateMealOrder.setArrDate(new Date());//到店日期
            }
            updateMealOrder.setCompanyId(restaurant.getCompanyId());
            updateMealOrder.setHotelCode(restaurant.getHotelCode());
            updateMealOrder.setRestaurantId(restaurantId);
            updateMealOrder.setName(restaurant.getName());
        }
        if (StringUtils.isNotBlank(mealOrder.getShuffleNo())) {
            updateMealOrder.setArriveTime(mealOrder.getArriveTime());//到店时间
            updateMealOrder.setArrDate(mealOrder.getArrDate());//到店日期
            updateMealOrder.setShuffleNo(mealOrder.getShuffleNo());//市别编码
            updateMealOrder.setShuffleName(mealOrder.getShuffleName());//市别名称
            updateMealOrder.setGuestNum(mealOrder.getGuestNum());//客人人数
        }
//		if(mealOrder.getChargeamt() != null){
//			updateMealOrder.setChargeamt(mealOrder.getChargeamt());
//			updateMealOrder.setChargeamtmodel(mealOrder.getChargeamtmodel());
//			updateMealOrder.setCouponSeqid(mealOrder.getCouponSeqid());//优惠劵
//			updateMealOrder.setCouponCode(mealOrder.getCouponCode());
//		}
        req.getSession().setAttribute(Constants.Session.MEAL_BOOK, updateMealOrder);
    }

    @RequestMapping("/toMealCashierDesk")
    public String toMealCashierDesk(MealOrder mealOrder, HttpServletRequest req) {
        MealOrder updateMealOrder = (MealOrder) req.getSession().getAttribute(Constants.Session.MEAL_BOOK);

        Float totalFee = 0.0f;
        if (mealOrder.getItems() != null) {
            List<MealOrderItem> items = new ArrayList<MealOrderItem>();
            for (MealOrderItem item : mealOrder.getItems()) {
                if (StringUtils.isNotBlank(item.getDishesId())) {
                    totalFee += item.getItemAmount();
                    items.add(item);
                }
            }
            mealOrder.setItems(items);

            if (updateMealOrder.getRestaurant() != null) {//服务费率
                Float serviceFee = totalFee * updateMealOrder.getRestaurant().getServiceFee() / 100;
                mealOrder.setServiceFee(serviceFee);
                totalFee += serviceFee;
            }
        }

        MealTab mealTab = updateMealOrder.getMealTab();
        if (mealTab != null && mealTab.getDeposit() != null) {//包间价格
            totalFee += updateMealOrder.getMealTab().getDeposit();
        }

        if (mealOrder.getChargeamt() != null) {
            totalFee -= mealOrder.getChargeamt();

            updateMealOrder.setChargeamt(mealOrder.getChargeamt());
            updateMealOrder.setChargeamtmodel(mealOrder.getChargeamtmodel());
            updateMealOrder.setCouponSeqid(mealOrder.getCouponSeqid());//优惠劵
            updateMealOrder.setCouponCode(mealOrder.getCouponCode());
        }

        String formatTotalFee = new DecimalFormat("#.00").format(totalFee);
        totalFee = Float.valueOf(formatTotalFee);
        mealOrder.setTotalFee(totalFee);

        BeanUtil.copyProperties(updateMealOrder, mealOrder, true);
        req.getSession().setAttribute(Constants.Session.MEAL_ORDER, updateMealOrder);

        if (updateMealOrder.getTotalFee() > 0) {
            return "redirect:/pay/toMealCashierDesk.do?showwxpaytitle=1";
        }

        String orderSn = saveMealOrder(PayMent.OFFLINEPAY, req);
        if (!StringUtils.equals(orderSn, "-1")) {
            //sendMealBookMessage(orderSn, req);
            return "redirect:/meal/showMealBookRs.do?orderSn=" + orderSn;
        }
        return "/errorpage";
    }

    @RequestMapping("/meal/saveMealOrder")
    @ResponseBody
    public String saveMealOrder(PayMent payMent, HttpServletRequest req) {
        Company company = getCurrentCompany(req);
        WeixinFan fan = getCurrentFan(req);
        MealOrder mealOrder = (MealOrder) req.getSession().getAttribute(Constants.Session.MEAL_ORDER);
        boolean isOk = true;
        if (mealOrder != null) {
            if (StringUtils.isNotBlank(mealOrder.getMealTabId())) {//包间
                MealTabQuery query = new MealTabQuery();
                query.setHotelCode(mealOrder.getHotelCode());
                query.setRefeNo(mealOrder.getRestaurant().getRefeNo());
                query.setTabNo(mealOrder.getMealTab().getTabNo());
                String orderDate = DateUtil.formatDate(mealOrder.getArrDate()) + " " + mealOrder.getArriveTime();
                query.setOrderDate(orderDate);
                Map<String, Integer> mealTabMap = mealService.findCanBookMealTabMap(query, mealOrder.getCompanyId());
                if (mealTabMap.get(query.getTabNo()) == 1) {
                    return "-2";
                }
            }

            String companyId = company.getId();
            String openId = fan.getOpenId();
            mealOrder.setTradeStatus(TradeStatus.WAIT_PAY);
            mealOrder.setCompanyId(companyId);
            mealOrder.setOpenId(fan.getOpenId());
            mealOrder.setPayFee(0f);
            mealOrder.setPayMent(payMent);
            mealOrder.setCreateDate(new Date());
            if (PayMent.OFFLINEPAY.equals(payMent)) {
                if (mealOrder.getTotalFee() <= 0) {
                    mealOrder.setTradeStatus(TradeStatus.FINISHED);//0元的已支付？？？
                    mealOrder.setPayFee(0f);
                }
                mealOrder.setStatus(MealOrderStatus.NOARRIVE);
                String couponSeqid = mealOrder.getCouponSeqid();
                boolean rs = true;
                if (StringUtils.isNotBlank(couponSeqid)) {
                    rs = memberTradeService.useMemberCoupon(mealOrder.getCompanyId(), couponSeqid, "预订房型使用券", mealOrder.getOpenId(), company.getCode());  //核销券
                }
                if (Boolean.TRUE.equals(rs)) {
                    mealOrderService.saveMealOrder(mealOrder);
                    isOk = mealOrderService.synchronizeMealOrderToJXD(mealOrder);
                    if (isOk) {
                        sendMealBookMessage(mealOrder.getOrderSn(), req);
                    }
                }
            } else {
                mealOrder.setStatus(MealOrderStatus.WAIT_PAY);
                mealOrderService.saveMealOrder(mealOrder);

                PayOrder payOrder = new PayOrder();
                payOrder.setPayMode(PayMode.BOOKMEAL);
                payOrder.setOpenId(openId);
                payOrder.setName(mealOrder.getName());
                payOrder.setBusinessId(mealOrder.getId());
                payOrder.setTotalFee((long) (mealOrder.getTotalFee() * 100));
                payOrder.setCompanyId(companyId);
                payOrder.setOrderSn(mealOrder.getOrderSn());

                Hotel hotel = hotelService.getHotel(payOrder.getCompanyId(), mealOrder.getHotelCode());
                payOrder.setRemark(hotel.getName() + mealOrder.getName() + "使用余额");

                HttpSession session = req.getSession();
                session.setAttribute(Constants.Session.PAY_ORDER, payOrder);
            }

            if (isOk) {
                return mealOrder.getOrderSn();
            }
        }
        return "-1";
    }

    /**
     * 显示餐饮支付结果
     *
     * @param orderSn
     * @param req
     * @return
     */
    @RequestMapping("/meal/showMealBookRs")
    public String showMealPayRs(String orderSn, HttpServletRequest req) {
        MealOrder order = mealOrderService.getMealOrderByOrderSn(orderSn);
        if (order != null) {
            req.setAttribute("order", order);
        }
        if (StringUtils.equals(orderSn, "-2")) {
            WeixinFan fan = getCurrentFan(req);
            MealOrder mealOrder = (MealOrder) req.getSession().getAttribute(Constants.Session.MEAL_ORDER);
            mealOrder.setOrderSn("无");
            mealOrder.setOpenId(fan.getOpenId());
            mealOrder.setErrorMsg("此时间段选定的包间已被预订了，请重新预订");
            weixinMessageService.sendMealMessageToUser(mealOrder, false);
        }
        req.getSession().removeAttribute(Constants.Session.MEAL_ORDER);
        return "/front/meal/meal_book_rs";
    }

    /**
     * 发送模板消息
     */
    @RequestMapping("/meal/sendMealBookMessage")
    public void sendMealBookMessage(String orderSn, HttpServletRequest req) {
        weixinMessageService.sendMealOrderMsgToCompany(orderSn);//发送模板消息给商户
    }

    /**
     * 根据餐段查询到店时间
     */
    @RequestMapping("/oauth/meal/ajaxListArriveTime")
    @ResponseBody
    public List<MealConfig> ajaxListArriveTime(MealType mealType, HttpServletRequest req) {
        Company company = getCurrentCompany(req);
        return mealService.findArriveTimeByMealType(mealType, company.getId());
    }

    /**
     * 订单列表
     */
    @RequestMapping("/oauth/meal/listMealOrder")
    public String listMealOrder(TradeStatus tradeStatus, HttpServletRequest req) {
        Company company = getCurrentCompany(req);
        WeixinFan fan = getCurrentFan(req);
        List<MealOrder> mealOrders = mealOrderService.loadMealOrders(company.getId(), fan.getOpenId(), tradeStatus);
        req.setAttribute("mealOrders", mealOrders);
        req.setAttribute("tradeStatus", tradeStatus);

        List<ModuleType> moduleTypes = new ArrayList<ModuleType>();
        if (company.getModuleTypes() != null) {
            for (ModuleType moduleType : company.getModuleTypes()) {
                if (moduleType != null) {
                    moduleTypes.add(moduleType);
                }
            }
        }
        req.setAttribute("moduleTypes", moduleTypes);
        return "front/meal/meal_order_list";
    }

    /**
     * 订单详情
     */
    @RequestMapping("/oauth/meal/showMealOrder")
    public String showMealOrder(String orderSn, HttpServletRequest req) {
        MealOrder mealOrder = mealOrderService.getMealOrderByOrderSn(orderSn);
        req.setAttribute("mealOrder", mealOrder);
        WeixinFan weixinFan = getCurrentFan(req);
        if (StringUtils.equals(mealOrder.getOpenId(), weixinFan.getOpenId())) {
            req.setAttribute("sameOpenId", true);
        }
        return "front/meal/meal_order_info";
    }

    /**
     * 取消订单
     */
    @RequestMapping("/oauth/meal/cancelMealOrder")
    @ResponseBody
    public Boolean cancelMealOrder(String orderSn, String cancelReason, HttpServletRequest req) {
        Company company = getCurrentCompany(req);
        WeixinFan weixinFan = getCurrentFan(req);
        boolean isOk = mealOrderService.cancelMealOrder(company, weixinFan.getOpenId(), orderSn, cancelReason);
        if (isOk) {
            weixinMessageService.sendCancelMealOrderMsgToCompany(orderSn);
            weixinMessageService.sendCancelMealOrderMsgToUser(orderSn);
        }
        return isOk;
    }

    /**
     * 去支付
     */
    @RequestMapping("/oauth/meal/toOrderPayment")
    public String toOrderPayment(String orderSn, HttpServletRequest req) {
        MealOrder mealOrder = mealOrderService.getMealOrderByOrderSn(orderSn);
        req.getSession().setAttribute(Constants.Session.MEAL_ORDER, mealOrder);
        return "redirect:/pay/toMealCashierDesk.do?showwxpaytitle=1";
    }

//	/**
//	 * 验证包间是否可预订
//	 */
//	@RequestMapping("/oauth/meal/ajaxCheckMealTabStatus")
//	@ResponseBody
//	public Boolean ajaxCheckMealTabStatus(HttpServletRequest req){
//		MealOrder mealOrder = (MealOrder)req.getSession().getAttribute(Constants.Session.MEAL_BOOK);
//		if(mealOrder!=null && StringUtils.isNotBlank(mealOrder.getMealTabId())){
//			MealTabQuery query = new MealTabQuery();
//			query.setHotelCode(mealOrder.getHotelCode());
//			query.setBeginDate(DateUtil.format(mealOrder.getArrDate(), "yyyy-MM-dd"));
//			query.setRefeNo(mealOrder.getRestaurant().getRefeNo());
//			query.setShuffle(mealOrder.getShuffle().getShuffleNo());
//			query.setTabNo(mealOrder.getMealTab().getTabNo());
//			return mealOrderService.checkMealTabStatus(query, mealOrder.getCompanyId());
//		}
//		return true;
//	}
}
