package com.whotel.meal.service;


import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.QrcodeUtil;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.PublicNoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.BeanUtil;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.service.HotelService;
import com.whotel.meal.dao.ShuffleDao;
import com.whotel.meal.dao.DishesCategoryDao;
import com.whotel.meal.dao.DishesDao;
import com.whotel.meal.dao.MealBranchDao;
import com.whotel.meal.dao.MealConfigDao;
import com.whotel.meal.dao.MealTabDao;
import com.whotel.meal.dao.RestaurantDao;
import com.whotel.meal.entity.Dishes;
import com.whotel.meal.entity.DishesCategory;
import com.whotel.meal.entity.Shuffle;
import com.whotel.meal.entity.MealBranch;
import com.whotel.meal.entity.MealConfig;
import com.whotel.meal.entity.MealTab;
import com.whotel.meal.entity.Restaurant;
import com.whotel.meal.enums.MealType;
import com.whotel.system.entity.SysOperationLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.mode.MealTabQuery;

import net.sf.json.JSONArray;

/**
 * 餐饮服务类
 * @author 柯鹏程
 *
 */
@Service
public class MealService {
	
	private static final Logger logger = Logger.getLogger(MealService.class);

	@Autowired
	private DishesDao dishesDao;
	
	@Autowired
	private MealTabDao mealTabDao;
	
	@Autowired
	private RestaurantDao restaurantDao;
	
	@Autowired
	private MealBranchDao mealBranchDao;
	
	@Autowired
	private MealConfigDao mealConfigDao;
	
	@Autowired
	private ShuffleDao shuffleDao;
	
	@Autowired 
	private DishesCategoryDao dishesCategoryDao;
	
	@Autowired 
	private HotelService hotelService;
	
	@Autowired 
	private MealManageService mealManageService;

	@Autowired
	private SystemLogService systemLogService;
	@Autowired
	PublicNoService publicNoService;

	public static final String wx_oauth_url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
	public static final String  web_url = "http://tiantianwutuo.com:8080/oauth/meal/login.do?tabId=";

	public String createQRcode(String tabId) throws Exception {
		MealTab mealTab = mealTabDao.get(tabId);

		String qrCodeUrl = mealTab.getQrCode();
		if(null == qrCodeUrl){
			String companyId = mealTab.getCompanyId();
			PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);

			StringBuffer weixinUrl = new StringBuffer(wx_oauth_url).append(publicNo.getAppId());

			StringBuffer webUrl = new StringBuffer(web_url).append(tabId);
			if(null != mealTab.getPayAfter() && mealTab.getPayAfter()){
				webUrl.append("&payAfter=1");
			}
			String url = URLEncoder.encode(webUrl.toString(), "UTF-8");
			weixinUrl.append("&redirect_uri=").append(url).append("&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
			byte[] imageByte = QrcodeUtil.zxingCodeCreate(weixinUrl.toString(), 300, 300);
			qrCodeUrl = QiniuUtils.upload(imageByte);
			mealTab.setQrCode(qrCodeUrl);
			mealTabDao.save(mealTab);
		}
		qrCodeUrl = QiniuUtils.getResUrl(qrCodeUrl);
		return  qrCodeUrl;
	}
	
	//////////////////////////菜式//////////////////////////
	public Dishes getDishesById(String id){
		return dishesDao.get(id);
	}
	
	/**
	 * 根据分店 分厅 菜肴编码查询菜
	 * @param companyId
	 * @return
	 */
	public Dishes getDishesByDishNo(String companyId,String dishNo,String restaurantId){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("dishNo", dishNo);
		if(StringUtils.isNotBlank(restaurantId)){
			properties.put("restaurantId", restaurantId);
		}
		return dishesDao.getByProperties(properties);
	}
	
	public List<Dishes> findDishesList(String companyId){
		return dishesDao.findByProperty("companyId", companyId);
	}
	
	public Page<Dishes> findDishesList(Page<Dishes> page){
		return dishesDao.find(page);
	}
	
	public void saveOrUpdateDishes(Dishes dishes,String adminId){
		Date nowDate = new Date();
		String operation = "update";
		if(StringUtils.isNotBlank(dishes.getId())){
			Dishes updateDishes = getDishesById(dishes.getId());
			BeanUtil.copyProperties(updateDishes, dishes, true);
			updateDishes.setIsEnable(dishes.getIsEnable()==null?false:dishes.getIsEnable());
			updateDishes.setPrice(dishes.getPrice()==null?updateDishes.getPrice():dishes.getPrice());//优惠价
			updateDishes.setMarketPrice(dishes.getMarketPrice()==null?updateDishes.getMarketPrice():dishes.getMarketPrice());//市场价
			updateDishes.setOrderIndex(dishes.getOrderIndex()==null?updateDishes.getOrderIndex():dishes.getOrderIndex());//排序
			updateDishes.setOrderWay(dishes.getOrderWay()==null?updateDishes.getOrderWay():dishes.getOrderWay());//点餐方式
			dishes = updateDishes;
		}else{
			operation = "add";
			dishes.setCreateDate(nowDate);
		}
		dishes.setUpdateTime(nowDate);
		dishesDao.save(dishes);
		
		if(StringUtils.isNotBlank(adminId)){
			systemLogService.saveSysOperationLog(new SysOperationLog(dishes.getCompanyId(), adminId, "MEAL", operation, JSONArray.fromObject(dishes).toString(), "菜肴管理："+dishes.getDishName()));
		}
	}
	
	public void delteDishes(Dishes dishes){
		dishesDao.delete(dishes);
	}
	
	public void deleteDishesByIds(String ids){
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				dishesDao.delete(id);
			}
		}
	}
	
	public void delteDishesByDishNo(String companyId,String dishNo){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("dishNo", dishNo);
		dishesDao.delete(properties);
	}
	
	//////////////////////////餐台//////////////////////////
	/**
	 * 查询可预订的包间
	 * @return
	 */
	public List<MealTab> findCanBookMealTabList(MealTabQuery query,String companyId){
		query.setOpType("餐饮餐台预订状态查询");
		Hotel hotel = hotelService.getHotel(companyId, query.getHotelCode());
		return mealManageService.findMealTabList(query,companyId,hotel.getMealUrl());
	}
	/**
	 * 修改排序
	 * @return
	 */
	public Map<String, Integer> findCanBookMealTabMap(MealTabQuery query,String companyId){
		query.setOpType("餐饮餐台预订状态查询");
		Map<String, Integer> mealTabMap = new HashMap<String, Integer>();
		List<MealTab> mealTabList = findCanBookMealTabList(query, companyId);
		
		if(StringUtils.isNotBlank(query.getTabNo())){//单个
			mealTabMap.put(query.getTabNo(), mealTabList.get(0).getStatus());
			return mealTabMap;
		}
		
		Map<String,String> mapRestaurant = mealManageService.getRestaurantMap(companyId);
		long time = System.currentTimeMillis();
		for (MealTab mealTab : mealTabList) {
			mealTabMap.put(mealTab.getTabNo(), mealTab.getStatus());
			if(mealTab.getStatus()==2){//可预订
				Map<String, Serializable> properties = new HashMap<>();
				properties.put("companyId", companyId);
				properties.put("tabNo", mealTab.getTabNo());
				properties.put("restaurantId", mapRestaurant.get(query.getHotelCode()+"+"+mealTab.getRestaurantId()));
				MealTab tab = getMealTabByTabNo(properties);
				if(tab != null){
					String orderIndex = tab.getOrderIndex()==null?"0":tab.getOrderIndex().toString();
					tab.setTimeStamp(time+orderIndex);
					saveOrUpdateMealTab(tab,null);
				}
			}
		}
		return mealTabMap;
	}
	
	public MealTab getMealTabById(String id){
		return mealTabDao.get(id);
	}
	
	public MealTab getMealTabByTabNo(Map<String, Serializable> properties){
		return mealTabDao.getByProperties(properties);
	}
	
	public List<MealTab> findMealTabList(String companyId,String hotelCode,String refeNo){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("refeNo", refeNo);
		properties.put("hotelCode", hotelCode);
		return mealTabDao.findByProperties(properties);
	}
	
	public Page<MealTab> findMealTabList(Page<MealTab> page){
		return mealTabDao.find(page);
	}
	
	public void saveOrUpdateMealTab(MealTab mealTab,String adminId){
		String operation = "update";
		if(StringUtils.isNotBlank(mealTab.getId())){
			MealTab updateMealTab = getMealTabById(mealTab.getId());
			BeanUtil.copyProperties(updateMealTab, mealTab, true);
			updateMealTab.setDeposit(mealTab.getDeposit());//定金
			updateMealTab.setMinimums(mealTab.getMinimums());//最低消费
			updateMealTab.setOrderIndex(mealTab.getOrderIndex());//排序
			mealTab = updateMealTab;
		}else{
			mealTab.setCreateDate(new Date());
			operation = "add";
		}
		mealTabDao.save(mealTab);
		if(StringUtils.isNotBlank(adminId)){
			systemLogService.saveSysOperationLog(new SysOperationLog(mealTab.getCompanyId(), adminId, "MEAL", operation, JSONArray.fromObject(mealTab).toString(), "包间管理："+mealTab.getTabName()));
		}
	}
	
	public void delteMealTab(MealTab mealTab){
		mealTabDao.delete(mealTab);
	}
	
	public void deleteMealTabByIds(String ids){
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				mealTabDao.delete(id);
			}
		}
	}
	
	public void delteMealTabByTabNo(String companyId,String tabNo){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("tabNo", tabNo);
		mealTabDao.delete(properties);
	}
	
	//////////////////////////餐厅//////////////////////////
	public Restaurant getRestaurantById(String id){
		return restaurantDao.get(id);
	}
	
	/**
	 * 根据分店和餐厅编码查询餐厅
	 * @param companyId
	 * @param hotelCode
	 * @return
	 */
	public Restaurant getRestaurantByRefeNo(String companyId,String refeNo,String hotelCode){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("refeNo", refeNo);
		if(StringUtils.isNotBlank(hotelCode)){
			properties.put("hotelCode", hotelCode);
		}
		return restaurantDao.getByProperties(properties);
	}
	
	public List<Restaurant> findRestaurantListByCode(String companyId,String hotelCode){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		if(StringUtils.isNotBlank(hotelCode)){
			properties.put("hotelCode", hotelCode);
		}
		return restaurantDao.findByProperties(properties);
	}
	
	public Page<Restaurant> findRestaurantList(Page<Restaurant> page){
		return restaurantDao.find(page);
	}
	
	public void saveOrUpdateRestaurant(Restaurant restaurant,String adminId){
		String operation = "update";
		if(StringUtils.isNotBlank(restaurant.getId())){
			Restaurant updateRestaurant = getRestaurantById(restaurant.getId());
			BeanUtil.copyProperties(updateRestaurant, restaurant, true);
			updateRestaurant.setIsEnable(restaurant.getIsEnable()==null?false:restaurant.getIsEnable());
			updateRestaurant.setIsCashBack(restaurant.getIsCashBack()==null?false:restaurant.getIsCashBack());
			updateRestaurant.setServiceFee(restaurant.getServiceFee());//服务费率
			updateRestaurant.setOrderIndex(restaurant.getOrderIndex());//排序
			restaurant = updateRestaurant;
		}else{
			restaurant.setCreateDate(new Date());
			operation = "add";
		}
		restaurantDao.save(restaurant);
		
		if(StringUtils.isNotBlank(adminId)){
			systemLogService.saveSysOperationLog(new SysOperationLog(restaurant.getCompanyId(), adminId, "MEAL", operation, JSONArray.fromObject(restaurant).toString(), "餐厅管理："+restaurant.getName()));
		}
	}
	
	public void delteRestaurant(Restaurant restaurant){
		restaurantDao.delete(restaurant);
	}
	
	public void deleteRestaurantByIds(String ids){
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				restaurantDao.delete(id);
			}
		}
	}
	
	public void delteRestaurantByRefeNo(String companyId,String refeNo){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("refeNo", refeNo);
		restaurantDao.delete(properties);
	}
	
	//////////////////////////餐厅分店//////////////////////////
	public MealBranch getMealBranchById(String id){
		return mealBranchDao.get(id);
	}
	
	public MealBranch getMealBranchByHotelCode(String companyId,String hotelCode){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("hotelCode", hotelCode);
		return mealBranchDao.getByProperties(properties);
	}
	
	public List<MealBranch> findMealBranchList(String companyId){
		return mealBranchDao.findByProperty("companyId", companyId);
	}
	
	public Page<MealBranch> findMealBranchList(Page<MealBranch> page){
		return mealBranchDao.find(page);
	}
	
	public void saveOrUpdateMealBranch(MealBranch mealBranch){
		if(StringUtils.isNotBlank(mealBranch.getId())){
			MealBranch updateMealBranch = getMealBranchById(mealBranch.getId());
			BeanUtil.copyProperties(updateMealBranch, mealBranch, true);
			mealBranchDao.save(updateMealBranch);
		}else{
			mealBranch.setCreateDate(new Date());
			mealBranchDao.save(mealBranch);
		}
	}
	
	public void delteMealBranch(MealBranch mealBranch){
		mealBranchDao.delete(mealBranch);
	}
	
	public void deleteMealBranchByIds(String ids){
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				mealBranchDao.delete(id);
			}
		}
	}
	
	public void delteMealBranchByRefeNo(String companyId,String hotelCode){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("hotelCode", hotelCode);
		mealBranchDao.delete(properties);
	}
	
	//////////////////////////餐厅参数//////////////////////////
	public MealConfig getMealConfigById(String id){
		return mealConfigDao.get(id);
	}
	
	public List<MealConfig> findMealConfigList(String companyId){
		return mealConfigDao.findByProperty("companyId", companyId);
	}
	
	public Page<MealConfig> findMealConfigList(Page<MealConfig> page){
		return mealConfigDao.find(page);
	}
	
	public void saveOrUpdateMealConfig(MealConfig mealConfig){
		if(StringUtils.isNotBlank(mealConfig.getId())){
			MealConfig updateMealConfig = getMealConfigById(mealConfig.getId());
			BeanUtil.copyProperties(updateMealConfig, mealConfig, true);
			mealConfigDao.save(updateMealConfig);
		}else{
			mealConfig.setCreateDate(new Date());
			mealConfigDao.save(mealConfig);
		}
	}
	
	public void delteMealConfig(MealConfig mealConfig){
		mealConfigDao.delete(mealConfig);
	}
	
	public void deleteMealConfigByIds(String ids){
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				mealConfigDao.delete(id);
			}
		}
	}
	
	/**
	 * 根据餐段查询到店时间
	 */
	public List<MealConfig> findArriveTimeByMealType(MealType mealType,String companyId){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("mealType", mealType);
		return mealConfigDao.findByProperties(properties);
	}
	
	////////////////////////////////////市别///////////////////////////////////////////////
	public Shuffle getShuffleByNo(String companyId,String shuffleNo,String restaurantId){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("shuffleNo", shuffleNo);
		if(StringUtils.isNotBlank(restaurantId)){
			properties.put("restaurantId", restaurantId);
		}
		return shuffleDao.getByProperties(properties);
	}
	/**
	 * companyId：此公司的所有市别，restaurantId：此餐厅的所有市别
	 * @param companyId
	 * @param restaurantId
	 * @return
	 */
	public List<Shuffle> findShufflesByRest(String companyId,String restaurantId){
		Map<String, Serializable> properties = new HashMap<>();
		if(StringUtils.isNotBlank(companyId)){
			properties.put("companyId", companyId);
		}
		if(StringUtils.isNotBlank(restaurantId)){
			properties.put("restaurantId", restaurantId);
		}
		return shuffleDao.findByProperties(properties, Order.asc("startTime"));
	}
	
	public void deleteShuffleByIds(String ids){
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				shuffleDao.delete(id);
			}
		}
	}
	
	public void saveOrUpdateShuffle(Shuffle shuffle){
		if(StringUtils.isNotBlank(shuffle.getId())){
			Shuffle updateShuffle = getShuffleById(shuffle.getId());
			BeanUtil.copyProperties(updateShuffle, shuffle, true);
			shuffleDao.save(updateShuffle);
		}else{
			shuffleDao.save(shuffle);
		}
	}
	
	public Shuffle getShuffleById(String id){
		return shuffleDao.get(id);
	}
	
	public Page<Shuffle> findShuffleList(Page<Shuffle> page){
		return shuffleDao.find(page);
	}
	
	//////////////////////////菜式分类//////////////////////////
	public DishesCategory getDishesCategoryByDishNo(String companyId,String dishNo,String restaurantId){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("dishNo", dishNo);
		if(StringUtils.isNotBlank(restaurantId)){
			properties.put("restaurantId", restaurantId);
		}
		return dishesCategoryDao.getByProperties(properties);
	}
	
	public void deleteDishesCategoryByIds(String ids){
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				dishesCategoryDao.delete(id);
			}
		}
	}
	
	public void saveOrUpdateDishesCategory(DishesCategory dishesCategory){
		if(StringUtils.isNotBlank(dishesCategory.getId())){
			DishesCategory updateDishesCategory = getDishesCategoryById(dishesCategory.getId());
			BeanUtil.copyProperties(updateDishesCategory, dishesCategory, true);
			dishesCategoryDao.save(updateDishesCategory);
		}else{
			dishesCategoryDao.save(dishesCategory);
		}
	}
	
	public DishesCategory getDishesCategoryById(String id){
		return dishesCategoryDao.get(id);
	}
	
	/**
	 * 验证 排序字段是否重复添加 
	 * @author 李中辉
	 * @param displayOrder
	 * @return  true: 数据库中已存在
	 */
	public Boolean checkDisplayOrder(Integer displayOrder, String id, String companyId) {
		if(displayOrder != null) {
			Map<String, Serializable> properties = new HashMap<String, Serializable>();
			properties.put("displayOrder", displayOrder);
			properties.put("companyId", companyId);
			
			List<DishesCategory> list = this.dishesCategoryDao.findByProperties(properties );
			if(list != null && list.size() > 0) {
				if(StringUtils.isNotBlank(id)) {
					for(DishesCategory gg : list) {
						if(!id.equals(gg.getId())) {
							return true;
						}
					}
				} else {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Page<DishesCategory> findDishesCategoryList(Page<DishesCategory> page){
		return dishesCategoryDao.find(page);
	}
	
	public List<DishesCategory> findDishCatByRestaurantId(String companyId,String restaurantId){
		Map<String, Serializable> properties = new HashMap<>();
		if(StringUtils.isNotBlank(companyId)){
			properties.put("companyId", companyId);
		}
		if(StringUtils.isNotBlank(restaurantId)){
			properties.put("restaurantId", restaurantId);
		}
		
		Order orders = Order.desc("displayOrder");
		return dishesCategoryDao.findByProperties(properties, orders);
	}
	
	public List<DishesCategory> findDishesCategoryByCompanyId(String companyId){
		return dishesCategoryDao.findByProperty("companyId", companyId);
	}



}
