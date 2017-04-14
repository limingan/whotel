package com.whotel.admin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.QueryParam;
import com.whotel.common.dto.ResultData;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.util.SystemConfig;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.CompanyModule;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.entity.OauthInterface;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.entity.SmsConfig;
import com.whotel.company.enums.ModuleType;
import com.whotel.company.service.CompanyModuleService;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.company.service.OauthInterfaceService;
import com.whotel.company.service.PublicNoService;
import com.whotel.company.service.SmsConfigService;

/**
 * 商家信息管理
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/admin/company")
public class CompanyManageController extends BaseAdminController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	@Autowired
	private SmsConfigService smsConfigService;
	
	@Autowired
	private PublicNoService publicNoService;
	
	@Autowired
	private CompanyModuleService companyModuleService;
	
	@Autowired
	private OauthInterfaceService oauthInterfaceService;
	
	/**
	 * 加载商户列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/listCompany")
	public String listCompany(Page<Company> page, QueryParam queryParam, HttpServletRequest req) {
			
		if(queryParam != null && queryParam.getParams() != null) {
			
			Map<String, String> params = queryParam.getParams();
			
			String name = params.get("name");
			
			String code = params.get("code");
			
			if(StringUtils.isNotBlank(name)) {
				page.addFilter("name", FilterModel.LIKE, name);
			}
			
			if(StringUtils.isNotBlank(code)) {
				page.addFilter("code", FilterModel.LIKE, code);
			}
		}
		page.addOrder(Order.desc("createTime"));
		companyService.findCompanys(page);
		return "/admin/company/company_list";
	}
	
	/**
	 * 编辑商户信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditCompany")
	public String toEditCompany(String id, HttpServletRequest req) {
		Company company = companyService.getCompanyById(id);
		req.setAttribute("company", company);
		List<CompanyModule> modules = companyModuleService.findAllCompanyModules();
		
		if(company.getModules()!=null){
			
			Map<String,String> map = new HashMap<>();
			for (CompanyModule module : company.getModules()) {
				map.put(module.getId(), module.getId());
			}
			
			for (CompanyModule module : modules) {
				if(map.get(module.getId())!=null){
					module.setChecked(true);
				}
			}
		}
		req.setAttribute("modules", modules);
		
		List<ModuleType> moduleTypes = new ArrayList<ModuleType>();
		ModuleType hotel = ModuleType.HOTEL;
		ModuleType ticket = ModuleType.TICKET;
		ModuleType mall = ModuleType.MALL;
		ModuleType meal = ModuleType.MEAL;
		ModuleType combo = ModuleType.COMBO;
		
		if(company.getModuleTypes()!=null){
			hotel.setChecked(company.getModuleTypes().contains(hotel));
			ticket.setChecked(company.getModuleTypes().contains(ticket));
			mall.setChecked(company.getModuleTypes().contains(mall));
			meal.setChecked(company.getModuleTypes().contains(meal));
			combo.setChecked(company.getModuleTypes().contains(combo));
		}
		
		moduleTypes.add(hotel);
		moduleTypes.add(ticket);
		moduleTypes.add(mall);
		moduleTypes.add(meal);
		moduleTypes.add(combo);
		req.setAttribute("moduleTypes", moduleTypes);
		
		return "/admin/company/company_edit";
	}
	
	/**
	 * 更新保存商户信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateCompany")
	public String updateCompany(Company company, HttpServletRequest req) {
		if(company!=null&&company.getModules()!=null){
			List<CompanyModule> modules = new ArrayList<>();
			for (CompanyModule module : company.getModules()) {
				if(StringUtils.isNotBlank(module.getId())){
					modules.add(companyModuleService.getCompanyModuleById(module.getId()));
				}
			}
			company.setModules(modules);
		}
		companyService.saveCompany(company);
		return "redirect:/admin/company/listCompany.do";
	}
	
	@RequestMapping("/toCompanyRegister")
	public String toCompanyRegister(HttpServletRequest req) {
		return "/admin/company/company_register";
	}
	
	@RequestMapping("/companyRegister")
	public String companyRegister(String companyName, String code, Boolean group, CompanyAdmin companyAdmin, HttpServletRequest req) throws UnsupportedEncodingException {
		ResultData rd = companyService.register(companyName, code, group, companyAdmin);
		return "redirect:/admin/company/listCompany.do?message="+URLEncoder.encode(rd.getMessage(), "UTF8");
	}
	
	@RequestMapping("/ajaxCodeExist")
	@ResponseBody
	public Boolean ajaxCodeExist(String code, HttpServletRequest req) throws UnsupportedEncodingException {
		Company company = companyService.getCompanyByCode(code);
		if(company != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 商家接口数据配置
	 * @param companyId
	 * @param req
	 * @return
	 */
	@RequestMapping("/interfaceConfig")
	public String interfaceConfig(String companyId, HttpServletRequest req) {
		if(StringUtils.isNotBlank(companyId)) {
			Company company = companyService.getCompanyById(companyId);
			InterfaceConfig config = interfaceConfigService.getInterfaceConfig(companyId);
			req.setAttribute("company", company);
			req.setAttribute("config", config);
		}
		return "/admin/company/interface_config";
	}
	
	/**
	 * 更新接口配置数据
	 * @param config
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/updateInterfaceConfig")
	public String updateInterfaceConfig(InterfaceConfig config, HttpServletRequest req) throws UnsupportedEncodingException {
		interfaceConfigService.saveInterfaceConfig(config);
		return "redirect:/admin/company/listCompany.do?message="+URLEncoder.encode("保存成功", "UTF8");
	}
	
	/**
	 * 商家短信接口配置
	 * @param companyId
	 * @param req
	 * @return
	 */
	@RequestMapping("/smsConfig")
	public String smsConfig(String companyId, HttpServletRequest req) {
		if(StringUtils.isNotBlank(companyId)) {
			Company company = companyService.getCompanyById(companyId);
			SmsConfig config = smsConfigService.getSmsConfig(companyId);
			req.setAttribute("company", company);
			req.setAttribute("config", config);
		}
		return "/admin/company/sms_config";
	}
	
	/**
	 * 更新短信接口数据
	 * @param config
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/updateSmsConfig")
	public String updateSmsConfig(SmsConfig config, HttpServletRequest req) throws UnsupportedEncodingException {
		smsConfigService.saveSmsConfig(config);
		return "redirect:/admin/company/listCompany.do?message="+URLEncoder.encode("保存成功", "UTF8");
	}
	
	/**
	 * 编辑公众号信息
	 * @param companyId
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditPublicNo")
	public String toEditPublicNo(String companyId, HttpServletRequest req) {
		if(StringUtils.isNotBlank(companyId)) {
			Company company = companyService.getCompanyById(companyId);
			PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
			req.setAttribute("company", company);
			req.setAttribute("publicNo", publicNo);
			req.setAttribute("weixinCallHost", SystemConfig.getProperty("weixin.call.host"));
		}
		return "/admin/company/publicno_edit";
	}
	
	/**
	 * 更新公众号信息
	 * @param publicNo
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/updatePublicNo")
	public String updatePublicNo(PublicNo publicNo, HttpServletRequest req) throws UnsupportedEncodingException {
		if(publicNo != null) {
			publicNoService.savePublicNo(publicNo);
		}
		return "redirect:/admin/company/toEditPublicNo.do?companyId="+publicNo.getCompanyId()+"&message="+URLEncoder.encode("保存成功！", "UTF8");
	}
	
	@RequestMapping("/ajaxDeveloperCodeExist")
	@ResponseBody
	public Boolean ajaxDeveloperCodeExist(String developerCode, HttpServletRequest req) throws UnsupportedEncodingException {
		return publicNoService.getPublicNoByDeveloperCode(developerCode)!=null;
	}
	
	@RequestMapping("/ajaxAccountExist")
	@ResponseBody
	public Boolean ajaxAccountExist(String account, HttpServletRequest req) throws UnsupportedEncodingException {
		return publicNoService.getPublicNoByAccount(account)!=null;
	}
	
	/**
	 * 进入商户后台
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/toCompanyIndex")
	public String toCompanyIndex(String id, HttpServletRequest req) throws UnsupportedEncodingException {
		Company company = companyService.getCompanyById(id);
		
		CompanyAdmin companyAdmin = companyService.getFirstCompanyAdmin(company.getId());
		if(companyAdmin != null) {
			HttpSession session = req.getSession();
			session.setAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_CODE, company.getCode());
			session.setAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_KEY, companyAdmin.getAccount());
			session.setAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_COMPANY, company.getName());
			session.setAttribute(Constants.Session.DOMAIN, SystemConfig.getProperty("server.url"));
			
			List<CompanyModule> modules = companyModuleService.findAllCompanyModules();
			List<CompanyModule> companyModules = companyAdmin.getCompany().getModules();
			if(companyModules!=null){
				for (CompanyModule module : companyModules) {
					if(module!=null){
						modules.remove(module);
					}
				}
			}
			
			Map<String,CompanyModule> noPrivilegeMap = new HashMap<String,CompanyModule>();
			for(CompanyModule module:modules) {
				if(module!=null){
					noPrivilegeMap.put(module.getLinkUrl(), module);
				}
			}
			session.setAttribute(Constants.Session.COMPANY_PRIVILEGE_MAP, noPrivilegeMap);
			
		} else {
			return "redirect:/admin/company/listCompany.do?message="+URLEncoder.encode("该商户无管理员账号", "UTF8");
		}
		return "redirect:/company/index.do";
	}
	
	@RequestMapping("/listOauthInterface")
	public String listOauthInterface(String companyId, HttpServletRequest req){
		if(StringUtils.isNotBlank(companyId)) {
			List<OauthInterface> oauthInterfaces = oauthInterfaceService.findOauthInterfaces(companyId);
			req.setAttribute("oauthInterfaces", oauthInterfaces);
		}
		req.setAttribute("companyId", companyId);
		return "/admin/company/oauth_interface_list";
	}
	
	@RequestMapping("/ajaxOauthInterfaceCodeExist")
	@ResponseBody
	public Boolean ajaxOauthInterfaceCodeExist(String companyId,String code, HttpServletRequest req) throws UnsupportedEncodingException {
		OauthInterface oauthInterface = oauthInterfaceService.getCompanyByCode(companyId,code);
		if(oauthInterface == null) {
			return true;
		}
		return false;
	}
	
	@RequestMapping("/editOauthInterface")
	public String editOauthInterface(String id,String companyId, HttpServletRequest req){
		OauthInterface oauthInterface = null;
		if(StringUtils.isNotBlank(id)) {
			oauthInterface = oauthInterfaceService.getOauthInterfaceById(id);
		}else{
			oauthInterface = new OauthInterface();
			oauthInterface.setCompanyId(companyId);
		}
		req.setAttribute("oauthInterface", oauthInterface);
		
		return "/admin/company/oauth_interface_edit";
	}
	
	@RequestMapping("/updateOauthInterface")
	public String updateOauthInterface(OauthInterface oauthInterface, HttpServletRequest req) throws UnsupportedEncodingException{
		if(StringUtils.isBlank(oauthInterface.getId())){
			oauthInterface.setCreateTime(new Date());
		}
		oauthInterfaceService.saveOauthInterface(oauthInterface);
		return "redirect:/admin/company/listOauthInterface.do?companyId="+oauthInterface.getCompanyId()+"&message="+URLEncoder.encode("保存成功", "UTF8");
	}
	
	@RequestMapping("/deleteOauthInterface")
	public String deleteOauthInterface(String id) throws UnsupportedEncodingException{
		OauthInterface oauthInterface = oauthInterfaceService.getOauthInterfaceById(id);
		String companyId = oauthInterface.getCompanyId();
		oauthInterfaceService.deleteOauthInterface(id);
		return "redirect:/admin/company/listOauthInterface.do?companyId="+companyId+"&message="+URLEncoder.encode("删除成功", "UTF8");
	}
}
