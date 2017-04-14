package com.whotel.meal.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.QueryParam;
import com.whotel.common.enums.FilterModel;
import com.whotel.company.controller.BaseCompanyController;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.service.CompanyService;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.service.HotelService;
import com.whotel.meal.entity.Dishes;
import com.whotel.meal.entity.DishesCategory;
import com.whotel.meal.entity.Shuffle;
import com.whotel.meal.entity.MealBranch;
import com.whotel.meal.entity.MealConfig;
import com.whotel.meal.entity.MealOrder;
import com.whotel.meal.entity.MealTab;
import com.whotel.meal.entity.Restaurant;
import com.whotel.meal.service.MealManageService;
import com.whotel.meal.service.MealOrderService;
import com.whotel.meal.service.MealService;

@Controller
@RequestMapping("/company/meal")
public class MealManageController extends BaseCompanyController {
	
	@Autowired
	private MealService mealService;
	
	@Autowired
	private MealManageService mealManageService;
	
	@Autowired
	private MealOrderService mealOrderService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private HotelService hotelService;
	
	/**
	 * 提示同步顺序
	 * 分店0》餐厅1》市别2》包间3/（菜肴分类3》菜肴4）
	 */
	@RequestMapping("/promptSynchronizeOrder")
	@ResponseBody
	public String promptSynchronizeOrder(Integer seq,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		String companyId = companyAdmin.getCompanyId();
		List<Hotel> hotels = hotelService.findAllHotels(companyId);
		if(hotels==null || hotels.size()==0){
			return "请先同步酒店(微酒店>>酒店列表)";
		}
		
		if(seq>1){
			List<Restaurant> restaurants = mealService.findRestaurantListByCode(companyId, null);
			if(restaurants==null || restaurants.size()==0){
				return "请先同步餐厅(微餐饮>>餐厅管理)";
			}
			if(seq>2){
				List<Shuffle> shuffles = mealService.findShufflesByRest(companyId, null);
				if(shuffles==null || shuffles.size()==0){
					return "请先同步市别(微餐饮>>市别管理)";
				}
				if(seq>3){
					List<DishesCategory> dishesCategorys = mealService.findDishCatByRestaurantId(companyId,null);
					if(dishesCategorys==null || dishesCategorys.size()==0){
						return "请先同步菜肴分类(微餐饮>>菜肴管理>>菜肴分类)";
					}
				}
			}
		}
		return "";
	}
	
	/////////////////////////////////菜式/////////////////////////////////
	/**
	 * 查询菜式
	 * @param page
	 * @param queryParam
	 * @param req
	 * @return
	 */
	@RequestMapping("/listDishes")
	public String listDishes(Page<Dishes> page, QueryParam queryParam,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		String hotelCode = null;
		Boolean order = false;
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			
			Map<String, String> params = queryParam.getParams();
			
			String dishNo = params.get("dishNo");
			String dishName = params.get("dishName");
			String restaurantId = params.get("restaurantId");
			hotelCode = params.get("hotelCode");
			String orderParam = params.get("order");
			String dishType = params.get("dishType");
			
			if(StringUtils.equals("true", orderParam)) {
				order = true;
			}
			if(StringUtils.isNotBlank(dishType)) {
				page.addFilter("dishType", FilterModel.LIKE, dishType);
			}
			if(StringUtils.isNotBlank(hotelCode)) {
				page.addFilter("hotelCode", FilterModel.EQ, hotelCode);
			}
			if(StringUtils.isNotBlank(dishNo)) {
				page.addFilter("dishNo", FilterModel.LIKE, dishNo);
			}
			if(StringUtils.isNotBlank(dishName)) {
				page.addFilter("dishName", FilterModel.LIKE, dishName);
			}
			if(StringUtils.isNotBlank(restaurantId)) {
				page.addFilter("restaurantId", FilterModel.EQ, restaurantId);
			}
		}
		
		page.addOrder("updateTime", order);
		mealService.findDishesList(page);
		
		System.out.println("hotelCode===="+hotelCode+","+StringUtils.isNotBlank(hotelCode));
		if(StringUtils.isNotBlank(hotelCode)){
			List<Restaurant> restaurantList = mealService.findRestaurantListByCode(companyAdmin.getCompanyId(), hotelCode);
			req.setAttribute("restaurantList", restaurantList);
		}
		
		List<Hotel> hotels = hotelService.findAllHotels(companyAdmin.getCompanyId());
		req.setAttribute("hotels", hotels);
		return "/company/meal/dishes_list";
	}
	
	/**
	 * 修改菜式
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditDishes")
	public String toEditDishes(String id,HttpServletRequest req){
		Dishes dishes = mealService.getDishesById(id);
		req.setAttribute("dishes", dishes);
		return "/company/meal/dishes_edit";
	}
	
	/**
	 * 保存菜式
	 * @param dishes
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveDishes")
	public String saveDishes(Dishes dishes,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		mealService.saveOrUpdateDishes(dishes,companyAdmin.getId());
		return "redirect:/company/meal/listDishes.do";
	}
	
	/**
	 * 删除菜式
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteDishes")
	public String deleteDishes(String ids){
		mealService.deleteDishesByIds(ids);
		return "redirect:/company/meal/listDishes.do";
	}
	
	/**
	 * 同步菜式
	 * @param req
	 * @return
	 */
	@RequestMapping("/synchronizeDishesByJXD")
	public String synchronizeDishesByJXD(HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		Company company = companyService.getCompanyById(companyAdmin.getCompanyId());
		mealManageService.synchronizeDishesByJXD(company);
		return "redirect:/company/meal/listDishes.do";
	}
	
	/////////////////////////////////餐台/////////////////////////////////
	/**
	 * 查询餐台
	 * @param page
	 * @param queryParam
	 * @param req
	 * @return
	 */
	@RequestMapping("/listMealTab")
	public String listMealTab(Page<MealTab> page, QueryParam queryParam,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		String hotelCode = null;
		Boolean order = false;
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			String tabName = params.get("tabName");
			String tabNo = params.get("tabNo");
			String isEnable = params.get("isEnable");
			hotelCode = params.get("hotelCode");
			String restaurantId = params.get("restaurantId");
			String orderParam = params.get("order");
			
			if(StringUtils.equals("true", orderParam)) {
				order = true;
			}
			if(StringUtils.isNotBlank(restaurantId)) {
				page.addFilter("restaurantId", FilterModel.EQ, restaurantId);
			}
			if(StringUtils.isNotBlank(hotelCode)) {
				page.addFilter("hotelCode", FilterModel.EQ, hotelCode);
			}
			if(StringUtils.isNotBlank(tabNo)) {
				page.addFilter("tabNo", FilterModel.LIKE, tabNo);
			}
			if(StringUtils.isNotBlank(tabName)) {
				page.addFilter("tabName", FilterModel.LIKE, tabName);
			}
			if(StringUtils.isNotBlank(isEnable)) {
				if(isEnable.equals("true")){
					page.addFilter("isEnable", FilterModel.EQ, true);
				}else{
					page.addFilter("isEnable", FilterModel.NE, true);
				}
			}
		}
		page.addOrder("orderIndex",order);
		mealService.findMealTabList(page);
		
		if(StringUtils.isNotBlank(hotelCode)){
			List<Restaurant> restaurantList = mealService.findRestaurantListByCode(companyAdmin.getCompanyId(), hotelCode);
			req.setAttribute("restaurantList", restaurantList);
		}
		List<Hotel> hotels = hotelService.findAllHotels(companyAdmin.getCompanyId());
		req.setAttribute("hotels", hotels);
		return "/company/meal/mealTab_list";
	}
	
	/**
	 * 修改餐台
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditMealTab")
	public String toEditMealTab(String id,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		MealTab mealTab = mealService.getMealTabById(id);
		req.setAttribute("mealTab", mealTab);
		List<Restaurant> restaurantList = mealService.findRestaurantListByCode(companyAdmin.getCompanyId(), null);
		req.setAttribute("restaurantList", restaurantList);
		return "/company/meal/mealTab_edit";
	}
	
	/**
	 * 保存餐台
	 * @param mealTab
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveMealTab")
	public String saveMealTab(MealTab mealTab,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		mealService.saveOrUpdateMealTab(mealTab,companyAdmin.getId());
		return "redirect:/company/meal/listMealTab.do";
	}
	
	/**
	 * 删除餐台
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteMealTab")
	public String deleteMealTab(String ids){
		mealService.deleteMealTabByIds(ids);
		return "redirect:/company/meal/listMealTab.do";
	}
	
	/**
	 * 同步餐台
	 * @param req
	 * @return
	 */
	@RequestMapping("/synchronizeMealTabByJXD")
	public String synchronizeMealTabByJXD(HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		Company company = companyService.getCompanyById(companyAdmin.getCompanyId());
		mealManageService.synchronizeMealTabByJXD(company);
		return "redirect:/company/meal/listMealTab.do";
	}
	/////////////////////////////////餐厅/////////////////////////////////
	/**
	 * 查询餐厅
	 * @param page
	 * @param queryParam
	 * @param req
	 * @return
	 */
	@RequestMapping("/listRestaurant")
	public String listRestaurant(Page<Restaurant> page, QueryParam queryParam,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			
			Map<String, String> params = queryParam.getParams();
			
			String refeNo = params.get("refeNo");
			String hotelCode = params.get("hotelCode");
			
			if(StringUtils.isNotBlank(refeNo)) {
				page.addFilter("refeNo", FilterModel.LIKE, refeNo);
			}
			if(StringUtils.isNotBlank(hotelCode)) {
				page.addFilter("hotelCode", FilterModel.EQ, hotelCode);
			}
		}
		page.addOrder(Order.desc("createDate"));
		mealService.findRestaurantList(page);
		
		List<Hotel> hotels = hotelService.findAllHotels(companyAdmin.getCompanyId());
		req.setAttribute("hotels", hotels);
		
		Map<String,List<Shuffle>> map = mealManageService.getShuffleMapByRest(companyAdmin.getCompanyId());
		req.setAttribute("shuffleMap", map);
		return "/company/meal/restaurant_list";
	}
	
	/**
	 * 修改餐厅
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditRestaurant")
	public String toEditRestaurant(String id,HttpServletRequest req){
		Restaurant restaurant = mealService.getRestaurantById(id);
		req.setAttribute("restaurant", restaurant);
		
		List<Shuffle> shuffles = mealService.findShufflesByRest(null,restaurant.getId());
		req.setAttribute("shuffles", shuffles);
		return "/company/meal/restaurant_edit";
	}
	
	/**
	 * 保存餐厅
	 * @param restaurant
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveRestaurant")
	public String saveRestaurant(Restaurant restaurant,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		mealService.saveOrUpdateRestaurant(restaurant,companyAdmin.getId());
		return "redirect:/company/meal/listRestaurant.do";
	}
	
	/**
	 * 删除餐厅
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteRestaurant")
	public String deleteRestaurant(String ids){
		mealService.deleteRestaurantByIds(ids);
		return "redirect:/company/meal/listRestaurant.do";
	}
	
	/**
	 * 同步餐厅
	 * @param req
	 * @return
	 */
	@RequestMapping("/synchronizeRestaurantByJXD")
	public String synchronizeRestaurantByJXD(HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		Company company = companyService.getCompanyById(companyAdmin.getCompanyId());
		mealManageService.synchronizeRestaurantByJXD(company);
		return "redirect:/company/meal/listRestaurant.do";
	}
	
	/**
	 * 根据分店查询餐厅
	 */
	@RequestMapping("/ajaxRestaurantByHotel")
	@ResponseBody
	public List<Restaurant> ajaxRestaurantByHotel(String hotelCode,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		return mealService.findRestaurantListByCode(companyAdmin.getCompanyId(), hotelCode);
	}
	
	
	/////////////////////////////////餐饮分店/////////////////////////////////
	/**
	 * 查询餐饮分店
	 * @param page
	 * @param queryParam
	 * @param req
	 * @return
	 */
	@RequestMapping("/listMealBranch")
	public String listMealBranch(Page<MealBranch> page, QueryParam queryParam,HttpServletRequest req){
		page.addFilter("companyId", FilterModel.EQ, getCurrentCompanyAdmin(req).getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			
			Map<String, String> params = queryParam.getParams();
			
			String name = params.get("name");
			
			if(StringUtils.isNotBlank(name)) {
				page.addFilter("name", FilterModel.LIKE, name);
			}
		}
		page.addOrder(Order.desc("createDate"));
		mealService.findMealBranchList(page);
		return "/company/meal/mealBranch_list";
	}
	
	/**
	 * 修改餐饮分店
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditMealBranch")
	public String toEditMealBranch(String id,HttpServletRequest req){
		MealBranch mealBranch = mealService.getMealBranchById(id);
		req.setAttribute("mealBranch", mealBranch);
		return "/company/meal/mealBranch_edit";
	}
	
	/**
	 * 保存餐饮分店
	 * @param mealBranch
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveMealBranch")
	public String saveMealBranch(MealBranch mealBranch,HttpServletRequest req){
		mealService.saveOrUpdateMealBranch(mealBranch);
		return "redirect:/company/meal/listMealBranch.do";
	}
	
	/**
	 * 删除餐饮分店
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteMealBranch")
	public String deleteMealBranch(String ids){
		mealService.deleteMealBranchByIds(ids);
		return "redirect:/company/meal/listMealBranch.do";
	}
	
	/**
	 * 同步餐饮分店
	 * @param req
	 * @return
	 */
	@RequestMapping("/synchronizeMealBranchByJXD")
	public String synchronizeMealBranchByJXD(HttpServletRequest req){
//		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
//		Company company = companyService.getCompanyById(companyAdmin.getCompanyId());
//		mealManageService.synchronizeMealBranchByJXD(company);
		return "redirect:/company/meal/listMealBranch.do";
	}
	//////////////////////////////////////////////////餐饮订单//////////////////////////////////////////////////////////
	/**
	 * 查询餐饮订单
	 * @param page
	 * @param queryParam
	 * @param req
	 * @return
	 */
	@RequestMapping("/listMealOrder")
	public String listMealOrder(Page<MealOrder> page, QueryParam queryParam,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		page.addFilter("synchronizeState", FilterModel.EQ, false);
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			
			String orderSn = params.get("orderSn");
			if(StringUtils.isNotBlank(orderSn)) {
				page.addFilter("orderSn", FilterModel.LIKE, orderSn);
			}
			String contactName = params.get("contactName");
			if(StringUtils.isNotBlank(contactName)) {
				page.addFilter("contactName", FilterModel.LIKE, contactName);
			}
		}
		page.addOrder(Order.desc("createDate"));
		mealOrderService.findMealOrder(page);
		return "/company/meal/mealOrder_list";
	}
	
	/**
	 * 订单详情
	 */
	@RequestMapping("/mealOrderDetails")
	public String mealOrderDetails(String id,HttpServletRequest req){
		MealOrder mealOrder = mealOrderService.getMealOrderById(id);
		req.setAttribute("mealOrder", mealOrder);
		return "/company/meal/mealOrder_details";
	}
	
	/**
	 * 同步订单
	 * @param orderSn
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/syncOrderMeals")
	public String syncOrderMeals(String orderSn) throws UnsupportedEncodingException{
		MealOrder mealOrder = mealOrderService.getMealOrderByOrderSn(orderSn);
		boolean rs = mealOrderService.synchronizeMealOrderToJXD(mealOrder);
		if(!rs){
			return "redirect:/company/meal/listMealOrder.do?message="+URLEncoder.encode("同步失败", "UTF8");
		}
		mealOrder.setSynchronizeState(true);
		mealOrderService.saveMealOrder(mealOrder);
		return "redirect:/company/meal/listMealOrder.do?message="+URLEncoder.encode("同步成功", "UTF8");
	}
	//////////////////////////////////////////////////餐饮参数//////////////////////////////////////////////////////////
	/**
	 * 查询餐饮参数
	 * @param page
	 * @param queryParam
	 * @param req
	 * @return
	 */
	@RequestMapping("/listMealConfig")
	public String listMealConfig(Page<MealConfig> page, QueryParam queryParam,HttpServletRequest req){
		page.addFilter("companyId", FilterModel.EQ, getCurrentCompanyAdmin(req).getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			
			Map<String, String> params = queryParam.getParams();
			
			String orderSn = params.get("orderSn");
			if(StringUtils.isNotBlank(orderSn)) {
				page.addFilter("orderSn", FilterModel.LIKE, orderSn);
			}
		}
		page.addOrder("mealType",true);
		page.addOrder("arriveTime",true);
		mealService.findMealConfigList(page);
		return "/company/meal/mealConfig_list";
	}
	
	/**
	 * 修改餐饮参数
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditMealConfig")
	public String toEditMealConfig(String id,HttpServletRequest req){
		MealConfig mealConfig = mealService.getMealConfigById(id);
		req.setAttribute("mealConfig", mealConfig);
		return "/company/meal/mealConfig_edit";
	}
	
	/**
	 * 保存餐饮参数
	 * @param mealBranch
	 * @param req
	 * @return
	 */
	@RequestMapping("/saveMealConfig")
	public String saveMealConfig(MealConfig mealConfig,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		mealConfig.setCompanyId(companyAdmin.getCompanyId());
		mealService.saveOrUpdateMealConfig(mealConfig);
		return "redirect:/company/meal/listMealConfig.do";
	}
	
	/**
	 * 删除餐饮参数
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteMealConfig")
	public String deleteMealConfig(String ids){
		mealService.deleteMealConfigByIds(ids);
		return "redirect:/company/meal/listMealConfig.do";
	}
	
	/////////////////////////////////市别///////////////////////////////////////////////////////
	/**
	 * 查询市别
	 */
	@RequestMapping("/listShuffle")
	public String listShuffle(Page<Shuffle> page, QueryParam queryParam,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		String hotelCode = null;
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			
			String shuffleNo = params.get("shuffleNo");
			String shuffleName = params.get("shuffleName");
			String restaurantId = params.get("restaurantId");
			hotelCode = params.get("hotelCode");
			
			if(StringUtils.isNotBlank(hotelCode)) {
				page.addFilter("hotelCode", FilterModel.EQ, hotelCode);
			}
			if(StringUtils.isNotBlank(shuffleNo)) {
				page.addFilter("shuffleNo", FilterModel.LIKE, shuffleNo);
			}
			if(StringUtils.isNotBlank(shuffleName)) {
				page.addFilter("shuffleName", FilterModel.LIKE, shuffleName);
			}
			if(StringUtils.isNotBlank(restaurantId)) {
				page.addFilter("restaurantId", FilterModel.EQ, restaurantId);
			}
		}
		
		if(StringUtils.isNotBlank(hotelCode)){
			List<Restaurant> restaurantList = mealService.findRestaurantListByCode(companyAdmin.getCompanyId(), hotelCode);
			req.setAttribute("restaurantList", restaurantList);
		}
		
		List<Hotel> hotels = hotelService.findAllHotels(companyAdmin.getCompanyId());
		req.setAttribute("hotels", hotels);
		mealService.findShuffleList(page);
		return "/company/meal/shuffle_list";
	}
	
	/**
	 * 修改市别
	 */
	@RequestMapping("/toEditShuffle")
	public String toEditShuffle(String id,HttpServletRequest req){
		Shuffle shuffle = mealService.getShuffleById(id);
		req.setAttribute("shuffle", shuffle);
		return "/company/meal/shuffle_edit";
	}
	
	/**
	 * 保存市别
	 */
	@RequestMapping("/saveShuffle")
	public String saveShuffle(Shuffle shuffle,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		shuffle.setCompanyId(companyAdmin.getCompanyId());
		mealService.saveOrUpdateShuffle(shuffle);
		return "redirect:/company/meal/listShuffle.do";
	}
	
	/**
	 * 删除市别
	 */
	@RequestMapping("/deleteShuffle")
	public String deleteShuffle(String ids){
		mealService.deleteShuffleByIds(ids);
		return "redirect:/company/meal/listShuffle.do";
	}
	
	/**
	 * 同步市别
	 * @param req
	 * @return
	 */
	@RequestMapping("/synchronizeShuffleByJXD")
	public String synchronizeShuffleByJXD(HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		Company company = companyService.getCompanyById(companyAdmin.getCompanyId());
		mealManageService.synchronizeShuffleByJXD(company);
		return "redirect:/company/meal/listShuffle.do";
	}
	
	/////////////////////////////////菜式分类///////////////////////////////////////////////////////
	/**
	* 查询菜式分类
	*/
	@RequestMapping("/listDishesCategory")
	public String listDishesCategory(Page<DishesCategory> page, QueryParam queryParam,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		String hotelCode = null;
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			
			String dishNo = params.get("dishNo");
			String dishName = params.get("dishName");
			String restaurantId = params.get("restaurantId");
			hotelCode = params.get("hotelCode");
			
			if(StringUtils.isNotBlank(hotelCode)) {
				page.addFilter("hotelCode", FilterModel.EQ, hotelCode);
			}
			if(StringUtils.isNotBlank(dishNo)) {
				page.addFilter("dishNo", FilterModel.LIKE, dishNo);
			}
			if(StringUtils.isNotBlank(dishName)) {
				page.addFilter("dishName", FilterModel.LIKE, dishName);
			}
			if(StringUtils.isNotBlank(restaurantId)) {
				page.addFilter("restaurantId", FilterModel.EQ, restaurantId);
			}
		}
		
		page.addOrder(Order.desc("createTime"));
		mealService.findDishesCategoryList(page);
		
		if(StringUtils.isNotBlank(hotelCode)){
			List<Restaurant> restaurantList = mealService.findRestaurantListByCode(companyAdmin.getCompanyId(), hotelCode);
			req.setAttribute("restaurantList", restaurantList);
		}
		
		List<Hotel> hotels = hotelService.findAllHotels(companyAdmin.getCompanyId());
		req.setAttribute("hotels", hotels);
		return "/company/meal/dishesCategory_list";
	}
	
	/**
	* 修改菜式分类
	*/
	@RequestMapping("/toEditDishesCategory")
	public String toEditDishesCategory(String id,HttpServletRequest req){
		DishesCategory dishesCategory = mealService.getDishesCategoryById(id);
		req.setAttribute("dishesCategory", dishesCategory);
		return "/company/meal/dishesCategory_edit";
	}
	
	/**
	 * 验证 排序字段是否重复添加 
	 * @author 李中辉
	 * @param displayOrder
	 * @return  true: 数据库中已存在
	 */
	@RequestMapping("/checkDisplayOrder")
	@ResponseBody
	public Boolean checkDisplayOrder(Integer displayOrder, String id, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		return mealService.checkDisplayOrder(displayOrder, id, companyAdmin.getCompanyId());
	}
	
	/**
	* 保存菜式分类
	*/
	@RequestMapping("/saveDishesCategory")
	public String saveDishesCategory(DishesCategory dishesCategory,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		dishesCategory.setCompanyId(companyAdmin.getCompanyId());
		mealService.saveOrUpdateDishesCategory(dishesCategory);
		return "redirect:/company/meal/listDishesCategory.do";
	}
	
	/**
	* 删除菜式分类
	*/
	@RequestMapping("/deleteDishesCategory")
	public String deleteDishesCategory(String ids){
		mealService.deleteDishesCategoryByIds(ids);
		return "redirect:/company/meal/listDishesCategory.do";
	}
	
	/**
	* 同步菜式分类
	* @param req
	* @return
	*/
	@RequestMapping("/synchronizeDishesCategoryByJXD")
	public String synchronizeDishesCategoryByJXD(HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		Company company = companyService.getCompanyById(companyAdmin.getCompanyId());
		mealManageService.synchronizeDishesCategoryByJXD(company);
		return "redirect:/company/meal/listDishesCategory.do";
	}
	
}
