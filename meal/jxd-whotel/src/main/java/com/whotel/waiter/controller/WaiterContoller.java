package com.whotel.waiter.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.util.MD5Util;
import com.whotel.front.controller.FanBaseController;
import com.whotel.meal.controller.req.ListRestaurantReq;
import com.whotel.meal.entity.MealTab;
import com.whotel.meal.entity.Restaurant;
import com.whotel.meal.service.MealService;
import com.whotel.meal.service.RestaurantService;
import com.whotel.waiter.entity.Waiter;
import com.whotel.waiter.service.WaiterService;


/** 
 * @ClassName: WaiterLoginContoller 
 * @Description: 服务员控制器
 * @author 李中辉 
 * @date 2017年5月9日 上午10:22:37  
 */
@Controller
public class WaiterContoller extends FanBaseController{
	
	@Autowired
	private WaiterService waiterService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private MealService mealService;

	/** 
	 * @author 李中辉 
	 * @Description: 服务员去登录页面
	 * @date 2017年5月9日
	 */
	@RequestMapping("/oauth/waiter/toLogin")
	public String toLogin(String message,  HttpServletRequest req) {
		req.setAttribute("message", message);
		return "/meal/webPage/waiter/login";
	}
	
	/** 
	 * @author 李中辉 
	 * @throws UnsupportedEncodingException 
	 * @Description: 登录
	 * @date 2017年5月9日
	 */
	@RequestMapping("/oauth/waiter/login")
	public String login(String userName, String password, HttpServletRequest req) throws UnsupportedEncodingException {
		if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
			String companyId = getCurrentCompanyId(req);
			Map<String, Serializable> properties = new HashMap<String, Serializable>();
			properties.put("loginUserName", userName.trim());
			properties.put("pwd", MD5Util.MD5(password));
			properties.put("companyId", companyId);
			List<Waiter> list = waiterService.findWaiterByProperties(properties);
			
			if(list != null && !list.isEmpty()) {
				if(list.size() > 1) {
					return "redirect:/oauth/waiter/toLogin.do?message=" + URLEncoder.encode("存在多个同名账号， 请联系管理员", "UTF8");
				}
				
				if("1".equals(list.get(0).getStatus())) {
					return "redirect:/oauth/waiter/toLogin.do?message=" + URLEncoder.encode("此账号已停用， 请联系管理员", "UTF8");
				}
				
				//验证成功， 放Waiter 的id 放到session中
				req.getSession().setAttribute(Constants.Session.WAITER_ID, list.get(0).getId());
				
				return "redirect:/oauth/waiter/listRestaurant.do";
			}
		}
		return "redirect:/oauth/waiter/toLogin.do?message=" + URLEncoder.encode("用户名或密码不匹配", "UTF8");
	}
	
    /** 
     * @author 李中辉 
     * @throws UnsupportedEncodingException 
     * @Description: 餐厅列表
     * @date 2017年5月9日
     */
	@RequestMapping("/oauth/waiter/listRestaurant")
    public String listRestaurant(HttpServletRequest req) throws UnsupportedEncodingException {
		String temp = validateWaiter(req);
		if(temp != null) {
			return temp;
		}
		
		String waiterId = getWaiterId(req);
		Waiter waiter = this.waiterService.getWaiterById(waiterId);
		String companyId = getCurrentCompanyId(req);
		String restaurantId = waiter.getRestaurantId();
		String hotelCode = waiter.getHotelCode();
		
		ListRestaurantReq param = new ListRestaurantReq();
		param.setCompanyId(companyId);
		param.setHotelCode(hotelCode);
		param.setIsEnable(Boolean.TRUE);
		if(StringUtils.isNotBlank(restaurantId)) {
			param.setRestaurantId(restaurantId);
		} 
		
		List<Restaurant> restaurantList = this.restaurantService.getByParam(param);
		if(restaurantList != null && restaurantList.size() == 1) {
			//size 为1 ： 直接跳转到桌台页面
			return toMealTabList(restaurantList.get(0).getId(), req);
		}
		
		req.setAttribute("restaurantList", restaurantList);
		
		return "/meal/webPage/waiter/restaurant_list";
    }
	
	/** 
	 * @author 李中辉 
	 * @Description: 去桌台页面
	 * @date 2017年5月9日
	 */
	@RequestMapping("/oauth/waiter/toMealTabList")
	public String toMealTabList(String restaurantId, HttpServletRequest req) throws UnsupportedEncodingException {
		String temp = validateWaiter(req);
		if(temp != null) {
			return temp;
		}
		
		if(StringUtils.isBlank(restaurantId)) {
			return "redirect:/oauth/waiter/listRestaurant.do";
		}
		
		Restaurant restaurant = this.restaurantService.getById(restaurantId);
		req.setAttribute("restaurant", restaurant);
		
		return "/meal/webPage/waiter/table_list";
	}
	
	/** 
	 * @author 李中辉 
	 * @throws UnsupportedEncodingException 
	 * @Description: ajax 请求获取桌台列表
	 * @date 2017年5月9日
	 */
	@RequestMapping("/oauth/waiter/ajaxGetMealTabPage")
	@ResponseBody
	public Page<MealTab> ajaxGetMealTabPage(Page<MealTab> page, String restaurantId, 
				HttpServletRequest req) throws UnsupportedEncodingException {
		String temp = validateWaiter(req);
		if(temp != null) {
			return page;
		}
		page.addFilter("restaurantId", FilterModel.EQ, restaurantId);
		this.mealService.findMealTabList(page);
		return page;
	}
	
	/** 
	 * @author 李中辉 
	 * @Description: 验证服务员是否登录, 验证通过返回null， 否则， 返回跳转登录页面链接
	 * @date 2017年5月9日
	 */
	private String validateWaiter(HttpServletRequest req) throws UnsupportedEncodingException {
		String waiterId = getWaiterId(req);
		if(waiterId != null) {
			return null;
		}
		
		return "redirect:/oauth/waiter/toLogin.do?message=" + URLEncoder.encode("请先登录", "UTF8");
	}
	
	/** 
	 * @author 李中辉 
	 * @Description: 获取服务员ID, 未登录， 返回null
	 * @date 2017年5月9日
	 */
	private String getWaiterId(HttpServletRequest req) {
		String waiterId = (String) req.getSession().getAttribute(Constants.Session.WAITER_ID);
		return StringUtils.isNotBlank(waiterId) ? waiterId : null;
	}
}

















