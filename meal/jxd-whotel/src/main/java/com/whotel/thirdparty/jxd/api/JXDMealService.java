package com.whotel.thirdparty.jxd.api;

import java.util.List;

import com.whotel.hotel.entity.Hotel;
import com.whotel.meal.entity.*;
import com.whotel.thirdparty.jxd.mode.*;
import org.apache.log4j.Logger;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.util.ApiXmlVoParser;
import com.whotel.thirdparty.jxd.util.JxdXmlUtils;

/**
 * 基础数据信息接口
 * @author 柯鹏程
 */
public class JXDMealService {
	
	private static final Logger log = Logger.getLogger(JXDMealService.class);

	/**
	 * 菜式列表
	 * @return
	 * @throws Exception
	 */
	public List<Dishes> loadDishesList(MealDishesQuery query,String mealUrl) throws Exception {
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<Dishes> dishesList = null;
		 Response res = HttpHelper.connect(mealUrl).header("Content-Type",
				 "text/xml").timeout(JXDConstants.TIMEOUT*6).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 dishesList = ApiXmlVoParser.parseDishesList(res.html(), res.charset());
		 return dishesList;
	}
	
	/**
	 * 餐台列表
	 * @return
	 * @throws Exception
	 */
	public List<MealTab> loadMealTabList(MealTabQuery query,String mealUrl) throws Exception {
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<MealTab> mealTabList = null;
		 Response res = HttpHelper.connect(mealUrl).header("Content-Type",
				 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 mealTabList = ApiXmlVoParser.parseMealTabList(res.html(), res.charset());
		 return mealTabList;
	}
	
	/**
	 * 餐厅列表
	 * @return
	 * @throws Exception
	 */
	public List<Restaurant> loadRestaurantList(MealRestaurantQuery query,String mealUrl) throws Exception {
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<Restaurant> restaurantList = null;
		 Response res = HttpHelper.connect(mealUrl).header("Content-Type",
				 "text/xml").timeout(JXDConstants.TIMEOUT*3).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 restaurantList = ApiXmlVoParser.parseRestaurantList(res.html(), res.charset());
		 return restaurantList;
	}
	
	/**
	 * 餐饮分店列表
	 * @return
	 * @throws Exception
	 */
	public List<MealBranch> loadMealBranchList(MealBranchQuery query,String mealUrl) throws Exception {
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<MealBranch> mealBranchList = null;
		 Response res = HttpHelper.connect(mealUrl).header("Content-Type",
				 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 mealBranchList = ApiXmlVoParser.parseMealBranchList(res.html(), res.charset());
		 return mealBranchList;
	}
	/**
	 * 市别列表
	 * @return
	 * @throws Exception
	 */
	public List<Shuffle> loadShuffleList(MealShuffleQuery query,String mealUrl) throws Exception {
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<Shuffle> shuffleList = null;
		 Response res = HttpHelper.connect(mealUrl).header("Content-Type",
				 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 shuffleList = ApiXmlVoParser.parseShuffleList(res.html(), res.charset());
		 return shuffleList;
	}
	/**
	 * 菜式分类列表
	 */
	public List<DishesCategory> loadDishesCategoryList(MealDishesCategoryQuery query,String mealUrl) throws Exception {
		String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<DishesCategory> dishesCategoryList = null;
		 Response res = HttpHelper.connect(mealUrl).header("Content-Type",
				 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 dishesCategoryList = ApiXmlVoParser.parseDishesCategoryList(res.html(), res.charset());
		 return dishesCategoryList;
	}

	/**
	 * 查询做法列表
	 * @param query
	 * @param hotel
	 * @return
	 * @throws Exception
	 */
	public List<DishesAction> loadDishesAction(MealDishesActionQuery query,Hotel hotel) throws Exception{
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		// 请求接口并获得响应
		List<DishesAction> dishesCategoryList = null;
		Response res = HttpHelper.connect(hotel.getMealUrl()).header("Content-Type",
				"text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);
		dishesCategoryList = ApiXmlVoParser.parseDishesActionList(res.html(), res.charset(), hotel);
		return dishesCategoryList;
	}

	public List<Dishes> loadSuiteItem(MealDishesSuiteQuery query,Restaurant restaurant,String mealUrl) throws Exception{
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		// 请求接口并获得响应
		List<Dishes> dishesList = null;
		Response res = HttpHelper.connect(mealUrl).header("Content-Type",
				"text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);
		dishesList = ApiXmlVoParser.parseDishesSuiteList(res.html(), res.charset(), restaurant);
		return dishesList;
	}
}
