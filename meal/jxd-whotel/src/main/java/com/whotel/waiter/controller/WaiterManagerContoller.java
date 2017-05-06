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

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.QueryParam;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.util.MD5Util;
import com.whotel.company.controller.BaseCompanyController;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.meal.controller.req.ListRestaurantReq;
import com.whotel.meal.entity.Restaurant;
import com.whotel.meal.service.RestaurantService;
import com.whotel.waiter.entity.Waiter;
import com.whotel.waiter.service.WaiterService;

/** 
 * @ClassName: WaiterManagerContoller 
 * @Description: 服务员管理控制器
 * @author 李中辉 
 * @date 2017年5月5日 下午6:50:48  
 */
@Controller
@RequestMapping("/company/waiter")
public class WaiterManagerContoller extends BaseCompanyController{

	@Autowired
	private WaiterService waiterService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	/** 
	 * @author 李中辉 
	 * @Description: 查询列表
	 * @date 2017年5月5日
	 */
	@RequestMapping("/listWaiter")
	public String listWaiter(Page<Waiter> page, QueryParam queryParam,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			String userNo = params.get("userNo");
			String userName = params.get("userName");
			
			if(StringUtils.isNotBlank(userNo)) {
				page.addFilter("userNo", FilterModel.LIKE, userNo.trim());
			}
			
			if(StringUtils.isNotBlank(userName)) {
				page.addFilter("userName", FilterModel.LIKE, userName.trim());
			}
		}
		page.addOrder(Order.desc("updateDate"));
		waiterService.findWaiter(page);
		return "/company/waiter/waiter_list";
	}
	
	
	/** 
	 * @author 李中辉 
	 * @throws UnsupportedEncodingException 
	 * @Description: 保存修改数据
	 * @date 2017年5月5日
	 */
	@RequestMapping("/saveWaiter")
	public String saveWaiter(Waiter waiter,HttpServletRequest req) throws UnsupportedEncodingException{
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		String restaurantId = waiter.getRestaurantId();
		if(StringUtils.isNotBlank(restaurantId)) {
			Restaurant restaurant = this.restaurantService.getById(restaurantId);
			waiter.setRestaurantName(restaurant.getName());
		} else {
			waiter.setRestaurantId("");
			waiter.setRestaurantName("");
		}
		
		if(StringUtils.isBlank(waiter.getPwd())) {
			waiter.setPwd(null);
		} else {
			waiter.setPwd(MD5Util.MD5(waiter.getPwd()));
		}
		
		//验证登录用户名
		String loginUserName = waiter.getLoginUserName();
		if(StringUtils.isNotBlank(loginUserName)) {
			Map<String, Serializable> properties = new HashMap<String, Serializable>();
			properties.put("loginUserName", loginUserName.trim());
			properties.put("companyId", companyAdmin.getCompanyId());
			List<Waiter> list = this.waiterService.findWaiterByProperties(properties);
			//能查询到， 并且把当前对象排除掉
			if(list != null && !list.isEmpty() && !list.get(0).getId().equals(waiter.getId())) {
				return "redirect:/company/waiter/toEditWaiter.do?id=" + waiter.getId() + "&message=" + URLEncoder.encode("账号已经存在，保存失败，请重新填写信息！", "UTF8");
			}
			waiter.setLoginUserName(loginUserName.trim());
		} else {
			waiter.setLoginUserName("");
		}
		
		waiterService.saveOrUpdateWaiter(waiter);
		return "redirect:/company/waiter/listWaiter.do";
	}
	
	/** 
	 * @author 李中辉 
	 * @Description: 删除
	 * @date 2017年5月5日
	 */
	@RequestMapping("/deleteWaiter")
	public String deleteWaiter(String ids){
		waiterService.deleteWaiterByIds(ids);
		return "redirect:/company/waiter/listWaiter.do";
	}
	
	/** 
	 * @author 李中辉 
	 * @Description: 去修改页面
	 * @date 2017年5月5日
	 */
	@RequestMapping("/toEditWaiter")
	public String toEditWaiter(String id,String message, HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		String companyId = companyAdmin.getCompanyId();
		
		Waiter waiter = waiterService.getWaiterById(id);
		ListRestaurantReq param = new ListRestaurantReq();
		param.setCompanyId(companyId);
		List<Restaurant> allRestaurant = this.restaurantService.getByParam(param);
		
		req.setAttribute("waiter", waiter);
		req.setAttribute("allRestaurant", allRestaurant);
		req.setAttribute("message", message);
		
		return "/company/waiter/waiter_edit";
	}
	
	
	/** 
	 * @author 李中辉 
	 * @Description: 同步服务员
	 * @date 2017年5月5日
	 */
	@RequestMapping("/synchronizeMealTabByJXD")
	public String synchronizeMealTabByJXD(HttpServletRequest req) throws Exception{
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		this.waiterService.synchronizeMealTabByJXD(companyAdmin.getCompanyId());
		return "redirect:/company/waiter/listWaiter.do";
	}
	
}