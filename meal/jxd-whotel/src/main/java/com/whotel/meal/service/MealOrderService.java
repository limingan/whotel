package com.whotel.meal.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.weixin.pay.util.WxPayUtil;
import com.whotel.card.entity.Guest;
import com.whotel.card.entity.Member;
import com.whotel.card.service.MemberService;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.*;
import com.whotel.common.util.BeanUtil;
import com.whotel.common.util.DateUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.entity.PayConfig;
import com.whotel.company.enums.PayType;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.company.service.PayConfigService;
import com.whotel.company.service.SysParamConfigService;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;
import com.whotel.front.entity.PayOrder;
import com.whotel.front.service.PayOrderService;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.enums.HotelOrderStatus;
import com.whotel.hotel.service.HotelService;
import com.whotel.meal.controller.req.CreateOrderReq;
import com.whotel.meal.controller.req.PageOrderReq;
import com.whotel.meal.dao.MealOrderDao;
import com.whotel.meal.dao.MealTabDao;
import com.whotel.meal.entity.*;
import com.whotel.meal.enums.MealOrderStatus;
import com.whotel.meal.enums.MealOrderType;
import com.whotel.thirdparty.jxd.api.JXDMealService;
import com.whotel.thirdparty.jxd.api.JXDMemberService;
import com.whotel.thirdparty.jxd.api.JXDOrderService;
import com.whotel.thirdparty.jxd.mode.CyReservation;
import com.whotel.thirdparty.jxd.mode.CyReservationItem;
import com.whotel.thirdparty.jxd.mode.CyReservationResult;
import com.whotel.thirdparty.jxd.mode.RealOperate;
import com.whotel.thirdparty.jxd.mode.vo.*;
import com.whotel.weixin.service.WeixinMessageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class MealOrderService {

    private static final Logger logger = Logger.getLogger(MealOrderService.class);
    @Autowired
    private MealOrderDao mealOrderDao;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private InterfaceConfigService interfaceConfigService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private WeixinMessageService weixinMessageService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private DishesService dishesService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    DishesActionService dishesActionService;
    @Autowired
    GuestService getDeliverPrice;
    @Autowired
    PayConfigService payConfigService;
    @Autowired
    MealTabDao mealTabDao;
    @Autowired
    DishesRequestService dishesRequestService;



    /**
     * 查询餐饮订单
     *
     * @return
     */
    private List<MealOrder> findMealOrderList(String companyId, String openId, String hotelCode, String mealUrl) {
        List<MealOrder> mealOrders = new ArrayList<>();

        try {
            JXDOrderService orderService = new JXDOrderService();
            mealOrders = orderService.queryMealOrder(hotelCode, null, null, openId, mealUrl);
        } catch (Exception e) {
            logger.error("餐饮订单取消接口出错！");
            e.printStackTrace();
        }
        return mealOrders;
    }

    /**
     * 保存餐饮订单
     *
     * @param mealOrder
     */
    public void saveMealOrder(MealOrder mealOrder) {
        if (mealOrder != null) {
            String id = mealOrder.getId();
            if (StringUtils.isBlank(id)) {
                mealOrder.setCreateTime(new Date());
            } else {
                MealOrder updateMealOrder = getMealOrderById(id);
                mealOrder.setCreateTime(updateMealOrder.getCreateTime());
                mealOrder.setUpdateTime(new Date());
            }

            String orderSn = mealOrder.getOrderSn();
            if (StringUtils.isBlank(orderSn)) {
                mealOrder.setOrderSn(mealOrder.getHotelCode() + payOrderService.genPayOrderSn());
            }
            mealOrderDao.save(mealOrder);
        }
    }

    /**
     * 同步餐饮订单
     *
     * @return
     */
    public Boolean synchronizeMealOrderToJXD(MealOrder mealOrder) {
        if (mealOrder != null) {

            JXDOrderService orderService = new JXDOrderService();
            try {
                Hotel hotel = hotelService.getHotel(mealOrder.getCompanyId(), mealOrder.getHotelCode());
                String mealUrl = hotel.getMealUrl();
                if (StringUtils.isBlank(mealUrl)) {
                    InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(mealOrder.getCompanyId());
                    if (interfaceConfig != null) {
                        mealUrl = interfaceConfig.getHost();
                    }
                }

                if (StringUtils.isNotBlank(mealUrl)) {
                    ReservationResult result = orderService.createMealOrder(mealOrder, mealUrl);
                    if (result != null && StringUtils.isNotBlank(result.getErrorMsg())) {
                        mealOrder.setErrorMsg(result.getErrorMsg());
                        mealOrder.setSynchronizeState(false);
                    }
                    saveMealOrder(mealOrder);
                    weixinMessageService.sendMealMessageToUser(mealOrder, mealOrder.getSynchronizeState());
                    return true;
                }
            } catch (Exception e) {
                logger.error("餐饮订单取消接口出错！");
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 查询餐饮订单
     *
     * @param page
     */
    public void findMealOrder(Page<MealOrder> page) {
        mealOrderDao.find(page);
    }

    /**
     * 查询餐饮订单
     *
     * @param id
     * @return
     */
    public MealOrder getMealOrderById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return mealOrderDao.get(id);
        }
        return null;
    }

    /**
     * 查询未支付餐饮订单
     *
     * @param openId
     * @return
     */
    public List<MealOrder> findNoPayMealOrders(String openId) {
        Query<MealOrder> query = mealOrderDao.createQuery();
        query.field("openId").equal(openId);
        query.field("tradeStatus").equal(TradeStatus.WAIT_PAY);
        query.field("status").notEqual(HotelOrderStatus.CANCELED);
        query.field("status").notEqual(HotelOrderStatus.USED);
        query.order(mealOrderDao.getOrderString(Order.desc("createTime")));
        return query.asList();
    }

    /**
     * 查询上一次订单
     */
    public MealOrder getLastestMealOrderByOpenId(String openId) {
        if (StringUtils.isNotBlank(openId)) {
            MealOrder mealOrder = mealOrderDao.getByProperty("openId", openId, Order.desc("createTime"));
            return mealOrder;
        }
        return null;
    }

    /**
     * 查询餐饮订单
     *
     * @param orderSn
     * @return
     */
    public MealOrder getMealOrderByOrderSn(String orderSn) {
        if (StringUtils.isNotBlank(orderSn)) {
            MealOrder mealOrder = mealOrderDao.getByProperty("orderSn", orderSn);
            return mealOrder;
        }
        return null;
    }

    /**
     * 取消餐饮订单
     *
     * @param companyId
     * @param openId
     * @param orderSn
     * @return
     */
    public Boolean cancelMealOrder(Company company, String openId, String orderSn, String cancelReason) {

        MealOrder mealOrder = getMealOrderByOrderSn(orderSn);
        if (mealOrder != null && StringUtils.equals(openId, mealOrder.getOpenId())) {
            mealOrder.setStatus(MealOrderStatus.CANCELED);
            mealOrder.setTradeStatus(TradeStatus.CLOSED);
            mealOrder.setCancelReason(cancelReason);

            try {
                Hotel hotel = hotelService.getHotel(company.getId(), mealOrder.getHotelCode());
                String mealUrl = hotel.getMealUrl();
                if (StringUtils.isBlank(mealUrl)) {
                    InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(mealOrder.getCompanyId());
                    if (interfaceConfig != null) {
                        mealUrl = interfaceConfig.getHost();
                    }
                }

                if (StringUtils.isNotBlank(mealUrl)) {
                    JXDOrderService orderService = new JXDOrderService();
                    boolean isOk = orderService.cancelMealOrder(orderSn, mealOrder.getHotelCode(), mealOrder.getResortRegID(), cancelReason, mealUrl);
                    JXDMemberService memberService = new JXDMemberService();
                    Member member = this.memberService.getMemberByOpendId(openId);
                    if (member != null) {
                        if (StringUtils.isBlank(member.getProfileId())) {
                            MemberVO memberVO = memberService.getMemberByWeixinId(openId, company.getCode());
                            if (memberVO != null) {
                                member.setProfileId(memberVO.getProfileId());
                                this.memberService.saveMember(member);
                            }
                        }

                        if (isOk && StringUtils.isNotBlank(member.getProfileId()) && StringUtils.isNotBlank(mealOrder.getCouponCode())) {
                            memberService.sendMemberCoupon(member, mealOrder.getCouponCode(), 1, null, "订单取消，补发优惠券", company.getCode(), 1);
                        }
                    }

                    if (isOk) {
                        saveMealOrder(mealOrder);
                    }
                    return isOk;
                }
            } catch (Exception e) {
                logger.error("餐饮订单取消接口出错！");
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 加载餐饮订单
     */
    public List<MealOrder> loadMealOrders(String companyId, String openId, TradeStatus tradeStatus) {
        List<MealOrder> mealOrderList = null;
        if (TradeStatus.WAIT_PAY.equals(tradeStatus)) {
            mealOrderList = findNoPayMealOrders(openId);
        } else {
            Map<String, MealOrderStatus> mealOrderMap = new HashMap<String, MealOrderStatus>();
            List<Hotel> hotels = hotelService.findAllHotels(companyId);
            for (Hotel hotel : hotels) {
                String mealUrl = hotel.getMealUrl();
                if (StringUtils.isBlank(mealUrl)) {
                    InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
                    if (interfaceConfig != null) {
                        mealUrl = interfaceConfig.getHost();
                    }
                }

                if (StringUtils.isNotBlank(mealUrl)) {
                    List<MealOrder> mealOrders = findMealOrderList(companyId, openId, hotel.getCode(), mealUrl);
                    for (MealOrder mealOrder : mealOrders) {
                        mealOrderMap.put(mealOrder.getConfirmationID(), mealOrder.getStatus());
                    }
                }
            }

            mealOrderList = mealOrderDao.findByProperty("openId", openId, Order.desc("createTime"));
            for (MealOrder mealOrder : mealOrderList) {
                MealOrderStatus status = mealOrderMap.get(mealOrder.getOrderSn());
                if (status != null) {
                    mealOrder.setStatus(status);
                }
            }
        }
        return mealOrderList;
    }

    public Page<MealOrder> pageQuery(PageOrderReq param) {
        Page<MealOrder> page = new Page<>();
        page.addFilter("companyId", FilterModel.EQ, param.getCompanyId());
        page.addFilter("openId", FilterModel.EQ, param.getOpenId());
        Integer pageNo = param.getPageNo();
        Integer pageSize = param.getPageSize();
        if (null == pageNo) {
            pageNo = 1;
        }
        if (null == pageSize) {
            pageSize = 20;
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.addOrder("createDate", false);
        return mealOrderDao.find(page);
    }

    public MealOrder find(String companyId, String openId, String id) {
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", companyId);
        properties.put("openId", openId);
        properties.put("id", id);
        return mealOrderDao.getByProperties(properties);
    }

    public MealOrder createMealOrder(CreateOrderReq param) throws Exception {
        JSONDataUtil jsonDataUtil = JSONConvertFactory.getJacksonConverter();
        String companyId = param.getCompanyId();
        String openId = param.getOpenId();

        String hotelCode = null;
        String restaurantId = null;
        List<MealOrderItem> list = param.getList();

        float total = 0F;
        for (MealOrderItem item : list) {
            String dishId = item.getDishesId();
            Dishes dishes = dishesService.getDishesById(dishId);
            if (StringUtils.isEmpty(hotelCode)) {
                hotelCode = dishes.getHotelCode();
            }
            if (StringUtils.isEmpty(restaurantId)) {
                restaurantId = dishes.getRestaurantId();
            }

            item.setItemCode(dishes.getDishNo());
            item.setName(dishes.getDishName());
            item.setUnit(dishes.getUnit());
            item.setItemPrice(dishes.getPrice());
            item.setItemAmount(dishes.getPrice());


            if (null != dishes.getIsSuite() && dishes.getIsSuite().equals(1)) {
                item.setIsSuite(1);
                List<SuiteItem> orderItems = item.getItemList();
                Map<String, String> map = Maps.newHashMap();
                for (SuiteItem suiteItem : orderItems) {
                    String dishNo = suiteItem.getDishNo();
                    map.put(dishNo, dishNo);
                }
                List<SuiteItem> resultList = this.getSuiteDish(dishes, map);
                String suiteData = jsonDataUtil.jsonfromObject(resultList);
                item.setItemList(null);
                item.setSuiteData(suiteData);
            } else {
                item.setIsSuite(0);
            }

            List<DishProp> propList = item.getPropList();
            if (CollectionUtils.isNotEmpty(propList)) {
                Map<String, Object> map = this.getPropMap(propList,dishes);
                float tempTotal = (float) map.get("total");
                String propData = (String) map.get("propData");
                total += tempTotal;
                item.setPropData(propData);
                item.setPropList(null);
            }
            String unit = this.getUnit(propList,dishes);
            if(StringUtils.isNotBlank(unit)){
                item.setUnit(unit);
            }else{
                item.setUnit(dishes.getUnit());
            }

            int itemQuantity = item.getItemQuantity();
            total += (dishes.getPrice() * itemQuantity);
        }


        Hotel hotel = hotelService.getHotel(companyId, hotelCode);
        Restaurant restaurant = restaurantService.getById(restaurantId);

        if (null != hotel && null != hotel.getDeliverPrice()) {
            total += hotel.getDeliverPrice();
        }
        String orderSn = hotel.getCode() + payOrderService.genPayOrderSn();
        MealOrder mealOrder = new MealOrder();
        mealOrder.setOpenId(openId);
        mealOrder.setCompanyId(companyId);
        mealOrder.setRestaurantId(restaurantId);
        mealOrder.setHotelCode(hotelCode);
        mealOrder.setName(restaurant.getName());
        mealOrder.setOrderSn(orderSn);
        mealOrder.setGuestNum(param.getGuestNum());
        mealOrder.setRemark(param.getRemark());
        mealOrder.setItems(list);
        mealOrder.setMealOrderType(param.getMealOrderType());

        if (MealOrderType.OUT.equals(param.getMealOrderType())) {
            String addressId = param.getAddressId();
            Guest guest = getDeliverPrice.getById(addressId);
            mealOrder.setContactName(guest.getName());
            mealOrder.setContactMobile(guest.getMobile());
            mealOrder.setAddr(guest.getAddress());
        } else {
            Float teaFee = hotel.getTeaFee();
            Integer guestNum = param.getGuestNum();
            if (null != teaFee && null != guestNum) {
                total += teaFee * guestNum;
            }
            mealOrder.setTotalFee(total);
            mealOrder.setMealTabId(param.getTabId());
        }
        mealOrder.setTotalFee(total);


        Date now = new Date();
        mealOrder.setCreateTime(now);
        mealOrder.setUpdateTime(now);
        mealOrder.setCreateDate(now);
        mealOrderDao.save(mealOrder);

        if (param.getPayAfter().equals(1)) {
            mealOrder.setPayMent(PayMent.PAYAFTER);
            mealOrder.setTradeStatus(TradeStatus.FINISHED);
        } else {
            String payOrderSn = payOrderService.genPayOrderSn();
            mealOrder.setPaySn(payOrderSn);
            mealOrder.setTradeStatus(TradeStatus.WAIT_PAY);

            PayOrder payOrder = new PayOrder();
            payOrder.setOrderSn(orderSn);
            payOrder.setPayMode(PayMode.BOOKMEAL);
            payOrder.setOpenId(openId);
            payOrder.setName(mealOrder.getName());
            payOrder.setBusinessId(mealOrder.getId());
            payOrder.setTotalFee((long) (mealOrder.getTotalFee() * 100));
            payOrder.setCompanyId(companyId);

            payOrderService.savePayOrder(payOrder);

        }
        mealOrderDao.save(mealOrder);
        return mealOrder;
    }

    public CyReservationResult createOrder(MealOrder order) throws Exception {
        Hotel hotel = hotelService.getHotel(order.getCompanyId(), order.getHotelCode());
        Restaurant restaurant = restaurantService.getById(order.getRestaurantId());
        MealTab mealTab = mealTabDao.get(order.getMealTabId());
        JSONDataUtil jsonDataUtil = JSONConvertFactory.getJacksonConverter();

        JXDMealService jxdMealService = new JXDMealService();
        RealOperate operate = new RealOperate();
        CyReservation c = new CyReservation();
        c.setResStatus("G");
        c.setHotelCode(hotel.getCode());
        c.setRefe(restaurant.getRefeNo());
        c.setTabNo(mealTab.getTabNo());
        c.setRemark(order.getRemark());
        c.setWxid(order.getOpenId());
        c.setCreateDate(DateUtil.format(new Date(), DateUtil.DATETIME_PATTERN));
        c.setConfirmationID(order.getOrderSn());
        c.setGuestNum(order.getGuestNum());
        c.setPrepay(order.getTotalFee());
        c.setPrepayId(order.getOrderSn());
        c.setTicketAmount(0F);

        List<CyReservationItem> list = Lists.newArrayList();
        List<MealOrderItem> itemList = order.getItems();
        int size = itemList.size();
        for (int j = 0; j < size; j++) {
            MealOrderItem item = itemList.get(j);
            CyReservationItem i = new CyReservationItem();
            i.setItemCode(item.getItemCode());
            i.setItemName(item.getName());
            i.setItemQuantity(item.getItemQuantity());
            i.setUnit(item.getUnit());
            i.setItemPrice(item.getItemPrice());
            i.setActionName(this.getPropValue(item.getPropData()));
            i.setItemAmount(0);
            if (null != item.getIsSuite() && item.getIsSuite().equals(1)) {//套餐特殊处理
                i.setUpRowid(-1);
                List<SuiteItem> suiteItems = jsonDataUtil.listFromString(item.getSuiteData(), SuiteItem.class);
                int itemSize = suiteItems.size();
                for (int k = 0; k < itemSize; k++) {
                    SuiteItem suiteItem = suiteItems.get(k);
                    CyReservationItem ii = new CyReservationItem();
                    ii.setItemCode(item.getItemCode());
                    ii.setItemName(item.getName());
                    ii.setItemQuantity(item.getItemQuantity());
                    ii.setUnit(item.getUnit());
                    ii.setItemPrice(item.getItemPrice());
                    ii.setActionName(this.getPropValue(suiteItem.getPropData()));
                    ii.setUpRowid(0);
                    ii.setRowid(size + k);
                    ii.setItemAmount(0);
                    list.add(ii);
                }
            } else {
                i.setUpRowid(0);
            }
            i.setRowid(j);
            list.add(i);
        }
        c.setItems(list);
        operate.setCyReservation(c);
        CyReservationResult result = jxdMealService.createOrder(operate, hotel.getMealUrl());
        return result;
    }

    private String getPropValue(String propData) {
        JSONDataUtil jsonDataUtil = JSONConvertFactory.getJacksonConverter();
        StringBuffer sb = new StringBuffer("");
        if (StringUtils.isNotBlank(propData)) {
            List<JSONObject> list = jsonDataUtil.listFromString(propData, JSONObject.class);
            int outSize = list.size();
            for(int i = 0; i < outSize; i++){
                JSONObject object = list.get(i);
                if(!object.optString("propId").equals(DishesProperties.unit.name())){
                    sb.append(object.optString("propName")).append(":");

                    JSONArray jsonArray = object.getJSONArray("propValue");
                    int size = jsonArray.size();
                    for (int j = 0; j < size; j++) {
                        JSONObject job = jsonArray.getJSONObject(j);
                        if(j == size - 1){
                            if(i == outSize - 1){
                                sb.append(job.get("name"));
                            }else{
                                sb.append(job.get("name")).append(";");
                            }
                        }else {
                            sb.append(job.get("name")).append(",");
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    private String getUnit(List<DishProp> propList,Dishes dishes){
        StringBuffer sb = new StringBuffer();
        for (DishProp prop : propList) {
            if (prop.getPropId().equals(DishesProperties.unit.name())) {
                List<String> values = prop.getValueList();

                List<DishesUnit> unitList = dishes.getUnitList();
                if(CollectionUtils.isNotEmpty(unitList)){
                    Map<String,DishesUnit> unitMap = Maps.newHashMap();
                    for(DishesUnit unit : unitList){
                        unitMap.put(unit.getId(),unit);
                    }
                    for (String value : values) {
                        if (StringUtils.isNotEmpty(value)) {
                            DishesUnit unit = unitMap.get(value);
                            sb.append(unit.getName());
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    private Map<String, Object> getPropMap(List<DishProp> propList,Dishes dishes) {
        JSONDataUtil jsonDataUtil = JSONConvertFactory.getJacksonConverter();
        float total = 0F;
        List<Map<String, Object>> propResult = Lists.newArrayList();
        for (DishProp prop : propList) {
            if (prop.getPropId().equals(DishesProperties.action.name())) {
                Map<String, Object> actionMap = Maps.newHashMap();
                actionMap.put("propId",DishesProperties.action.name());
                actionMap.put("propName", DishesProperties.action.getLabel());
                List<Map<String, String>> propValue = Lists.newArrayList();
                List<String> values = prop.getValueList();
                for (String value : values) {
                    if (StringUtils.isNotEmpty(value)) {
                        DishesAction dishesAction = dishesActionService.getById(value);
                        Map<String, String> map = Maps.newHashMap();
                        map.put("id", dishesAction.getId());
                        map.put("name", dishesAction.getName());
                        propValue.add(map);

                        total += dishesAction.getAddPrice();
                    }
                }
                actionMap.put("propValue", propValue);
                propResult.add(actionMap);
            }

            if (prop.getPropId().equals(DishesProperties.unit.name())) {
                Map<String, Object> actionMap = Maps.newHashMap();
                actionMap.put("propId",DishesProperties.unit.name());
                actionMap.put("propName", DishesProperties.unit.getLabel());
                List<Map<String, String>> propValue = Lists.newArrayList();
                List<String> values = prop.getValueList();

                List<DishesUnit> unitList = dishes.getUnitList();
                Map<String,DishesUnit> unitMap = Maps.newHashMap();
                for(DishesUnit unit : unitList){
                    unitMap.put(unit.getId(),unit);
                }
                for (String value : values) {
                    if (StringUtils.isNotEmpty(value)) {
                        DishesUnit unit = unitMap.get(value);
                        Map<String, String> map = Maps.newHashMap();
                        map.put("id", unit.getId());
                        map.put("name", unit.getName());
                        propValue.add(map);

                        total += unit.getAddPrice();
                    }
                }
                actionMap.put("propValue", propValue);
                propResult.add(actionMap);
            }

            if (prop.getPropId().equals(DishesProperties.request.name())) {
                Map<String, Object> actionMap = Maps.newHashMap();
                actionMap.put("propId",DishesProperties.request.name());
                actionMap.put("propName", DishesProperties.request.getLabel());
                List<Map<String, String>> propValue = Lists.newArrayList();
                List<String> values = prop.getValueList();

                HotelDishesRequest request = dishesRequestService.getByHotelCode(dishes.getCompanyId(), dishes.getHotelCode());
                List<DishesRequest> unitList = request.getList();
                Map<String,DishesRequest> unitMap = Maps.newHashMap();
                for(DishesRequest req : unitList){
                    unitMap.put(req.getId().trim(),req);
                }
                for (String value : values) {
                    if (StringUtils.isNotEmpty(value)) {
                        DishesRequest req = unitMap.get(value);
                        Map<String, String> map = Maps.newHashMap();
                        map.put("id", req.getId());
                        map.put("name", req.getName());
                        propValue.add(map);
                        total += req.getAddPrice();
                    }
                }
                actionMap.put("propValue", propValue);
                propResult.add(actionMap);
            }
        }
        String propData = jsonDataUtil.jsonfromObject(propResult);
        Map<String, Object> map = Maps.newHashMap();
        map.put("total", total);
        map.put("propData", propData);
        return map;
    }


    private List<SuiteItem> getSuiteDish(Dishes dishes, Map<String, String> map) {
        List<SuiteItem> items = Lists.newArrayList();
        JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
        List<List> lists = jacksonConverter.listFromString(dishes.getSuiteData(), List.class);
        for (List list : lists) {
            for (Object itemObj : list) {
                Map<String, Object> temp = (Map<String, Object>) itemObj;
                SuiteItem suiteItem = new SuiteItem();
                BeanUtil.transMap2Bean(temp, suiteItem);
                String dishNo = suiteItem.getDishNo();
                if (map.containsKey(dishNo)) {
                    items.add(suiteItem);
                }

                List<DishProp> propList = suiteItem.getPropList();
                if (CollectionUtils.isNotEmpty(propList)) {
                    Map<String, Object> propMap = this.getPropMap(propList,dishes);
                    String propData = (String) propMap.get("propData");
                    suiteItem.setPropData(propData);
                    suiteItem.setPropList(null);
                }
            }
        }
        return items;
    }


    public String genJsApi(MealOrder order, String ip) {
        PayOrder payOrder = payOrderService.getPayOrderByOrderSn(order.getOrderSn());
        payOrder.setOpenId(order.getOpenId());
        payOrder.setCompanyId(order.getCompanyId());
        payOrder.setPayMent(PayMent.WXPAY);
        payOrder.setTotalFee((long) (order.getTotalFee() * 100));
        payOrderService.savePayOrder(payOrder);

        PayConfig payConfig = payConfigService.getPayConfigByType(payOrder.getCompanyId(), PayType.WX);
        String payApi = WxPayUtil.genJsApi(payConfig, payOrder, ip, null);
        return payApi;
    }
}
