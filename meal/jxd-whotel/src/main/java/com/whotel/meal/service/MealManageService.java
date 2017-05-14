package com.whotel.meal.service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whotel.hotel.dao.SceneQrcodeDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.enums.PayMent;
import com.whotel.common.util.BeanUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.service.HotelService;
import com.whotel.meal.dao.DishesCategoryDao;
import com.whotel.meal.dao.DishesDao;
import com.whotel.meal.dao.MealTabDao;
import com.whotel.meal.dao.RestaurantDao;
import com.whotel.meal.dao.ShuffleDao;
import com.whotel.meal.entity.Dishes;
import com.whotel.meal.entity.DishesCategory;
import com.whotel.meal.entity.MealTab;
import com.whotel.meal.entity.Restaurant;
import com.whotel.meal.entity.Shuffle;
import com.whotel.thirdparty.jxd.api.JXDMealService;
import com.whotel.thirdparty.jxd.mode.MealDishesCategoryQuery;
import com.whotel.thirdparty.jxd.mode.MealDishesQuery;
import com.whotel.thirdparty.jxd.mode.MealRestaurantQuery;
import com.whotel.thirdparty.jxd.mode.MealShuffleQuery;
import com.whotel.thirdparty.jxd.mode.MealTabQuery;

/**
 * 餐饮管理服务类
 * @author 柯鹏程
 *
 */
@Service
public class MealManageService {
	
	private static final Logger logger = Logger.getLogger(MealManageService.class);
	
	@Autowired
	private MealService mealService;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	@Autowired
	private DishesDao dishesDao;
	
	@Autowired
	private MealTabDao mealTabDao;
	
	@Autowired
	private RestaurantDao restaurantDao;
	
//	@Autowired
//	private MealBranchDao mealBranchDao;
	
	@Autowired
	private ShuffleDao shuffleDao;

	@Autowired 
	private DishesCategoryDao dishesCategoryDao;
	@Autowired
	SceneQrcodeDao sceneQrcodeDao;
	
	/**
	 * 同步菜式
	 * @param companyId
	 */
	public void synchronizeDishesByJXD(Company company){
		List<Hotel> hotels = hotelService.findAllHotels(company.getId());
		Map<String,String> mapRestaurant = getRestaurantMap(company.getId());
		Map<String,String> mapShuffle = getShuffleMap(company.getId());
		Map<String,String> mapCategory = getDishesCategoryMap(company.getId());
		
		for (Hotel hotel : hotels) {
			MealDishesQuery query = getMealDishesQuery(hotel.getCode(),company.getId());
			
			List<Dishes> dishesList = findDishesList(query,company.getId(),hotel.getMealUrl());
			for (Dishes dishes : dishesList) {
				String resKey = hotel.getCode()+"+"+dishes.getRestaurantId();
				dishes.setRestaurantId(mapRestaurant.get(resKey));
				String restaurantId = dishes.getRestaurantId();
				if(StringUtils.isBlank(restaurantId)){
					continue;
				}
				
				//市别id
				String shuffe = "";
				if(StringUtils.isNotBlank(dishes.getShuffleNo())){
					String[] shuffles = dishes.getShuffleNo().split(",");
					for (int i = 0; i < shuffles.length; i++) {
						if(i != 0){
							shuffe += ",";
						}
						String shuKey = restaurantId+"+"+shuffles[i];
						System.out.println(shuKey);
						shuffe += mapShuffle.get(shuKey);
					}
				}
				dishes.setShuffleNo(shuffe);
				
				
				String categoryIds = "";
				if(dishes.getDishno1()!=null){
					String[] dishNo1 = dishes.getDishno1().split(",");
					for (int i = 0; i < dishNo1.length; i++) {
						if(i != 0){
							shuffe += ",";
						}
						String categoryId = mapCategory.get(restaurantId+"+"+dishNo1[i]);
						categoryIds+=categoryId;
					}
				}
				dishes.setDishno1(categoryIds);
				
				//
				Dishes updateDishes = mealService.getDishesByDishNo(company.getId(), dishes.getDishNo(), restaurantId);
				if(updateDishes!=null){
//					if(!StringUtils.contains(updateDishes.getDishno1(), dishes.getDishno1())){
//						dishes.setDishno1(updateDishes.getDishno1()+","+dishes.getDishno1());
//						dishes.setDishType(updateDishes.getDishType()+","+dishes.getDishType());
//					}
					
					dishes.setPrice(null);//微信端的价格
					BeanUtil.copyProperties(updateDishes, dishes, true);
					dishesDao.save(updateDishes);
				}else{
					dishes.setCompanyId(company.getId());
					dishes.setHotelCode(hotel.getCode());
					dishes.setCreateDate(new Date());
					dishes.setIsEnable(true);
					dishesDao.save(dishes);
				}
			}
		}
	}
	
	private MealDishesQuery getMealDishesQuery(String hotelCode,String companyId){
		String lastQueryTime = "1970-01-01 00:00";
//		Page<Dishes> page = new Page<>();
//		page.setPageSize(1);
//		page.addOrder(Order.desc("createDate"));
//		page.addFilter("hotelCode",FilterModel.EQ, hotelCode);
//		page.addFilter("companyId",FilterModel.EQ, companyId);
//		dishesDao.find(page);
//		if(page.getResult().size()>0){
//			Dishes lastDishes = page.getResult().get(0);
//			lastQueryTime = DateUtil.format(lastDishes.getCreateDate(), "yyyy-MM-dd HH:mm:ss");
//		}
		
		MealDishesQuery query = new MealDishesQuery();
		query.setHotelCode(hotelCode);
		query.setLastQueryTime(lastQueryTime);
		return query;
	}
	
	private List<Dishes> findDishesList(MealDishesQuery query,String companyId,String mealUrl){
		List<Dishes> dishesList = new ArrayList<>();
		JXDMealService service = new JXDMealService();
		
		try {
			if(StringUtils.isBlank(mealUrl)){
				InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
				if(interfaceConfig!=null){
					mealUrl = interfaceConfig.getHost();
				}
			}
			
			if(StringUtils.isNotBlank(mealUrl)){
				dishesList = service.loadDishesList(query,mealUrl);
			}
		} catch (Exception e) {
			logger.error("菜式列表查询接口获取数据出错！");
			e.printStackTrace();
		}
		return dishesList;
	}
	
	/**
	 * 同步餐台
	 * @param companyId
	 */
	public void synchronizeMealTabByJXD(Company company){
		List<Hotel> hotels = hotelService.findAllHotels(company.getId());
		Map<String,String> mapRestaurant = getRestaurantMap(company.getId());
		for (Hotel hotel : hotels) {
			MealTabQuery query = new MealTabQuery();
			query.setHotelCode(hotel.getCode());
			
			List<MealTab> mealTabList = findMealTabList(query,company.getId(),hotel.getMealUrl());
			for (MealTab mealTab : mealTabList) {
				mealTab.setRestaurantId(mapRestaurant.get(hotel.getCode()+"+"+mealTab.getRestaurantId()));
				
				Map<String, Serializable> properties = new HashMap<>();
				properties.put("companyId", company.getId());
				properties.put("tabNo", mealTab.getTabNo());
				properties.put("restaurantId", mealTab.getRestaurantId());
				MealTab updateMealTab = mealTabDao.getByProperties(properties);
				if(updateMealTab!=null){
					BeanUtil.copyProperties(updateMealTab, mealTab, true);
					mealTabDao.save(updateMealTab);
				}else{
					mealTab.setCompanyId(company.getId());
					mealTab.setHotelCode(hotel.getCode());
					mealTab.setCreateDate(new Date());
					mealTab.setIsEnable(true);
					mealTabDao.save(mealTab);
				}
			}
		}
	}
	
	
	public List<MealTab> findMealTabList(MealTabQuery query,String companyId,String mealUrl){
		List<MealTab> mealTabList = new ArrayList<>();
		JXDMealService service = new JXDMealService();
		
		try {
			if(StringUtils.isBlank(mealUrl)){
				InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
				if(interfaceConfig!=null){
					mealUrl = interfaceConfig.getHost();
				}
			}
			
			if(StringUtils.isNotBlank(mealUrl)){
				mealTabList = service.loadMealTabList(query,mealUrl);
			}
		} catch (Exception e) {
			logger.error("餐台列表查询接口获取数据出错！");
			e.printStackTrace();
		}
		return mealTabList;
	}
	
	/**
	 * 同步餐厅
	 * @param companyId
	 */
	public void synchronizeRestaurantByJXD(Company company){
		List<Hotel> hotels = hotelService.findAllHotels(company.getId());
		for (Hotel hotel : hotels) {
			MealRestaurantQuery query = new MealRestaurantQuery();
			query.setHotelCode(hotel.getCode());
			List<Restaurant> restaurantList = findRestaurantList(query,company.getId(),hotel.getMealUrl());
			for (Restaurant restaurant : restaurantList) {
				Restaurant updateMealTab = mealService.getRestaurantByRefeNo(company.getId(), restaurant.getRefeNo(),hotel.getCode());
				if(updateMealTab!=null){
					BeanUtil.copyProperties(updateMealTab, restaurant, true);
					restaurantDao.save(updateMealTab);
				}else{
					restaurant.setHotelCode(hotel.getCode());
					restaurant.setHotelName(hotel.getName());
					restaurant.setCompanyId(company.getId());
					restaurant.setCreateDate(new Date());
					
					List<PayMent> payMents = new ArrayList<PayMent>();
					payMents.add(PayMent.OFFLINEPAY);
					restaurant.setPayMents(payMents);
					
					restaurantDao.save(restaurant);
				}
			}
		}
	}
	
	private List<Restaurant> findRestaurantList(MealRestaurantQuery query,String companyId,String mealUrl){
		List<Restaurant> restaurantList = new ArrayList<>();
		JXDMealService service = new JXDMealService();
		try {
			if(StringUtils.isBlank(mealUrl)){
				InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
				if(interfaceConfig!=null){
					mealUrl = interfaceConfig.getHost();
				}
			}
			
			if(StringUtils.isNotBlank(mealUrl)){
				restaurantList = service.loadRestaurantList(query,mealUrl);
			}
		} catch (Exception e) {
			logger.error("餐台列表查询接口获取数据出错！");
			e.printStackTrace();
		}
		return restaurantList;
	}
	
//	/**
//	 * 同步餐饮分店
//	 * @param companyId
//	 */
//	public void synchronizeMealBranchByJXD(Company company){
//		List<MealBranch> mealBranchList = findMealBranchList(new MealBranchQuery(),company.getId());
//		for (MealBranch mealBranch : mealBranchList) {
//			MealBranch updateMealBranch = mealService.getMealBranchByHotelCode(company.getId(), mealBranch.getHotelCode());
//			if(updateMealBranch!=null){
//				BeanUtil.copyProperties(updateMealBranch, mealBranch, true);
//				mealBranchDao.save(updateMealBranch);
//			}else{
//				mealBranch.setCompanyId(company.getId());
//				mealBranch.setCreateDate(new Date());
//				mealBranchDao.save(mealBranch);
//			}
//		}
//	}
	
//	private List<MealBranch> findMealBranchList(MealBranchQuery query,String companyId,String mealUrl){
//		List<MealBranch> mealBranchList = null;
//		
//		if(StringUtils.isBlank(mealUrl)){
//			InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
//			if(interfaceConfig!=null){
//				mealUrl = interfaceConfig.getHost();
//			}
//		}
//		
//		if(StringUtils.isNotBlank(mealUrl)){
//			try {
//				JXDMealService service = new JXDMealService();
//				mealBranchList = service.loadMealBranchList(query,mealUrl);
//			} catch (Exception e) {
//				logger.error("餐饮分店列表查询接口获取数据出错！");
//				e.printStackTrace();
//			}
//		}
//		
//		return mealBranchList;
//	}
	
	public Map<String,String> getRestaurantMap(String companyId){
		List<Restaurant> restaurants = mealService.findRestaurantListByCode(companyId, null);
		Map<String,String> mapRestaurant = new HashMap<>();
		
		for (Restaurant restaurant : restaurants) {
			String key = restaurant.getHotelCode()+"+"+restaurant.getRefeNo();
			mapRestaurant.put(key, restaurant.getId());
		}
		return mapRestaurant;
	}
	
	/**
	 * 同步市别...
	 * @param companyId
	 */
	public void synchronizeShuffleByJXD(Company company){
		List<Hotel> hotels = hotelService.findAllHotels(company.getId());
		for (Hotel hotel : hotels) {
			List<Restaurant> restaurants = mealService.findRestaurantListByCode(company.getId(), hotel.getCode());
			for (Restaurant restaurant : restaurants) {
				MealShuffleQuery query = getMealShuffleQuery(hotel.getCode(),restaurant.getRefeNo());
				List<Shuffle> shuffleList = findShuffleList(query,company.getId(),hotel.getMealUrl());
				for (Shuffle shuffle : shuffleList) {
					shuffle.setCompanyId(company.getId());
					shuffle.setRestaurantId(restaurant.getId());//*****
					shuffle.setHotelCode(hotel.getCode());
					Shuffle updateShuffle = mealService.getShuffleByNo(company.getId(), shuffle.getShuffleNo(),shuffle.getRestaurantId());
					
					String startTime = shuffle.getStartTime();
					if(startTime != null){
						shuffle.setStartTime(startTime.substring(0,2)+":"+startTime.substring(2));
					}
					String endTime = shuffle.getEndTime();
					if(endTime != null){
						shuffle.setEndTime(endTime.substring(0,2)+":"+endTime.substring(2));
					}
					
					if(updateShuffle!=null){
						BeanUtil.copyProperties(updateShuffle, shuffle, true);
						shuffleDao.save(updateShuffle);
					}else{
						shuffleDao.save(shuffle);
					}
				}
			}
		}
	}
	
	private MealShuffleQuery getMealShuffleQuery(String hotelCode,String refeNo){
		MealShuffleQuery query = new MealShuffleQuery();
		query.setHotelCode(hotelCode);
		query.setRefeNo(refeNo);
		return query;
	}
	
	private List<Shuffle> findShuffleList(MealShuffleQuery query,String companyId,String mealUrl){
		List<Shuffle> shuffleList = new ArrayList<>();
		JXDMealService service = new JXDMealService();
		try {
			if(StringUtils.isBlank(mealUrl)){
				InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
				if(interfaceConfig!=null){
					mealUrl = interfaceConfig.getHost();
				}
			}
			
			if(StringUtils.isNotBlank(mealUrl)){
				shuffleList = service.loadShuffleList(query,mealUrl);
			}
		} catch (Exception e) {
			logger.error("市别列表查询接口获取数据出错！");
			e.printStackTrace();
		}
		return shuffleList;
	}
	
	private Map<String,String> getShuffleMap(String companyId){
		List<Shuffle> shuffles = mealService.findShufflesByRest(companyId, null);
		Map<String,String> mapShuffles = new HashMap<>();
		for (Shuffle shuffle : shuffles) {
			String key = shuffle.getRestaurantId().trim()+"+"+shuffle.getShuffleNo().trim();
			System.out.println("getShuffleMap=="+key);
			mapShuffles.put(key, shuffle.getId());
		}
		return mapShuffles;
	}
	
	public Map<String,List<Shuffle>> getShuffleMapByRest(String companyId){
		Map<String,List<Shuffle>> map = new HashMap<>();
		List<Restaurant> restaurants = mealService.findRestaurantListByCode(companyId, null);
		if(restaurants != null){
			for (Restaurant restaurant : restaurants) {
				List<Shuffle> shuffles = mealService.findShufflesByRest(companyId, restaurant.getId());
				map.put(restaurant.getId(), shuffles);
			}
		}
		return map;
	}
	
	private Map<String,String> getDishesCategoryMap(String companyId){
		Map<String,String> mapCategory = new HashMap<>();
		List<DishesCategory> list = mealService.findDishesCategoryByCompanyId(companyId);
		for (DishesCategory category : list) {
			String key = category.getRestaurantId()+"+"+category.getDishNo();
			System.out.println("getDishesCategoryMap=="+key);
			mapCategory.put(key, category.getId());
		}
		return mapCategory;
	}
	/**
	 * 同步菜式分类
	 */
	public void synchronizeDishesCategoryByJXD(Company company){
		List<Hotel> hotels = hotelService.findAllHotels(company.getId());
		for (Hotel hotel : hotels) {
			MealDishesCategoryQuery query = new MealDishesCategoryQuery();
			List<Restaurant> restaurants = mealService.findRestaurantListByCode(company.getId(), hotel.getCode());
			for (Restaurant restaurant : restaurants) {
				query = getMealDishesCategoryQuery(hotel.getCode(),restaurant.getRefeNo());
				List<DishesCategory> dishesCategoryList = findDishesCategoryList(query,company.getId(),hotel.getMealUrl());
				for (DishesCategory dishesCategory : dishesCategoryList) {
					dishesCategory.setCompanyId(company.getId());
					dishesCategory.setCreateTime(new Date());
					dishesCategory.setRestaurantId(restaurant.getId());
					dishesCategory.setHotelCode(hotel.getCode());
					DishesCategory updateDishesCategory = mealService.getDishesCategoryByDishNo(company.getId(), dishesCategory.getDishNo(),restaurant.getId());
					if(updateDishesCategory!=null){
						BeanUtil.copyProperties(updateDishesCategory, dishesCategory, true);
						dishesCategoryDao.save(updateDishesCategory);
					}else{
						dishesCategoryDao.save(dishesCategory);
					}
				}
			}
		}
	}
	
	private MealDishesCategoryQuery getMealDishesCategoryQuery(String hotelCode,String refeNo){
		MealDishesCategoryQuery query = new MealDishesCategoryQuery();
		query.setHotelCode(hotelCode);
		query.setRefeNo(refeNo);
		return query;
	}
	
	private List<DishesCategory> findDishesCategoryList(MealDishesCategoryQuery query,String companyId,String mealUrl){
		List<DishesCategory> dishesCategoryList = new ArrayList<>();
		JXDMealService service = new JXDMealService();
		try {
			if(StringUtils.isBlank(mealUrl)){
				InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
				if(interfaceConfig!=null){
					mealUrl = interfaceConfig.getHost();
				}
			}
			
			if(StringUtils.isNotBlank(mealUrl)){
				dishesCategoryList = service.loadDishesCategoryList(query,mealUrl);
			}
		} catch (Exception e) {
			logger.error("菜式分类列表查询接口获取数据出错！");
			e.printStackTrace();
		}
		return dishesCategoryList;
	}
}
