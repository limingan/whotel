package com.whotel.company.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.admin.entity.SysNotice;
import com.whotel.admin.service.SysNoticeService;
import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.ResultData;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.util.BeanUtil;
import com.whotel.common.util.EncryptUtil;
import com.whotel.common.util.SystemConfig;
import com.whotel.company.entity.ArriveTime;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.CompanyModule;
import com.whotel.company.entity.CompanyRole;
import com.whotel.company.entity.PayConfig;
import com.whotel.company.entity.SmsConfig;
import com.whotel.company.entity.SysParamConfig;
import com.whotel.company.entity.TemplateMessage;
import com.whotel.company.enums.MessageType;
import com.whotel.company.enums.ModuleType;
import com.whotel.company.enums.PayType;
import com.whotel.company.service.ArriveTimeService;
import com.whotel.company.service.CompanyModuleService;
import com.whotel.company.service.CompanyRoleService;
import com.whotel.company.service.PayConfigService;
import com.whotel.company.service.SmsConfigService;
import com.whotel.company.service.SysParamConfigService;
import com.whotel.company.service.TemplateMessageService;
import com.whotel.ext.redis.RedisCache;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.service.HotelService;
import com.whotel.system.entity.SysOperationLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.api.JXDSmsUtil;
import com.whotel.thirdparty.jxd.mode.HotelBranchQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;

import net.sf.json.JSONArray;

/**
 * 商户信息控制器
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseCompanyController {
	
	private static final Logger logger = Logger.getLogger(CompanyController.class);
	
	@Autowired
	private SysNoticeService noticeService;
	
	@Autowired
	private PayConfigService payConfigService;
	
	@Autowired
	private RedisCache cache;
	
	@Autowired
	private CompanyModuleService moduleService;
	
	@Autowired
	private CompanyRoleService roleService;
	
	@Autowired
	private ArriveTimeService arriveTimeService;
	
	@Autowired
	private TemplateMessageService templateMessageService;
	
	@Autowired
	private SmsConfigService smsConfigService;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private SysParamConfigService sysParamConfigService;
	
	@Autowired
	private SystemLogService systemLogService;
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Page<SysNotice> page, HttpServletRequest req) {
		page.addFilter("isPublish", FilterModel.EQ, true);
		page.addOrder(Order.desc("createTime"));
		noticeService.findSysNotices(page);
		
		systemLogService.cleanSysOperationLogs();
		return "/company/index";
	}
	
	/**
	 * 查看系统公告
	 */
	@RequestMapping("/indexNoticeDetails")
	public String indexNoticeDetails(String id, HttpServletRequest req) {
		SysNotice sysNotice = noticeService.getSysNoticeById(id);
		Integer readQty = sysNotice.getReadQty()==null?0:sysNotice.getReadQty();
		sysNotice.setReadQty(readQty+1);
		noticeService.saveSysNotice(sysNotice);
		req.setAttribute("sysNotice", sysNotice);
		return "/company/index_notice_details";
	}
	
	/**
	 * 进入注册页面
	 * @return
	 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "/company/register";
	}
	
	/**
	 * 商户注册
	 * @param company
	 * @param companyAdmin
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/register")
	public String register(String companyName, String code, Boolean group, CompanyAdmin companyAdmin, HttpServletRequest req) throws UnsupportedEncodingException {
		CompanyAdmin admin = companyService.getCompanyAdminByEmail(companyAdmin.getEmail());
		if(admin!=null){
			req.setAttribute("message", "该邮箱已被注册");
		}else{
			ResultData rd = companyService.register(companyName, code, group, companyAdmin);
			if(rd != null) {
				if(StringUtils.equals(Constants.MessageCode.RESULT_SUCCESS, rd.getCode())) {
					return "redirect:/company/toLogin.do?message="+URLEncoder.encode("注册成功，请登录", "UTF8");
				}
				req.setAttribute("message", rd.getMessage());
			} else {
				req.setAttribute("message", "注册参数错误");
			}
		}
		return "/company/register";
	}
	
	/**
	 * 进入登录页面
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "/company/login";
	}
	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("/login")
	public String login(String code, String account, String password, String captcha, HttpServletRequest req, HttpServletResponse res) {
		
		if (StringUtils.isNotBlank(captcha)) {
			String captchaStr = (String) req.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			req.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			
			// 验证码检验
			if (StringUtils.equalsIgnoreCase(captchaStr, captcha)) {
				
				CompanyAdmin companyAdmin = companyService.getCompanyAdmin(code, account);
				Integer count = 0;
				if(companyAdmin == null ){
					req.setAttribute("message", "账户或密码不正确!!!");
					return "/company/login";
				}
				if(companyAdmin.getErrorCount() != null){
					count = companyAdmin.getErrorCount();
				}
				
				if(count < 10){
					CompanyAdmin admin = companyService.login(code, account, password);
					if(admin != null) {
						logger.debug(admin.getAccount() + " Login success."); 
						HttpSession session = req.getSession();
						session.setAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_CODE, code);
						session.setAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_KEY, account);
						session.setAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_COMPANY, admin.getCompany().getName());
						session.setAttribute(Constants.Session.DOMAIN, SystemConfig.getProperty("server.url"));
						
						List<CompanyModule> modules = moduleService.findAllCompanyModules();
						if(admin.getRole()!=null&&admin.getRole().getModules()!=null){
							modules.removeAll(admin.getRole().getModules());
						}
						if(admin.isAdmin() && admin.getCompany().getModules()!=null){
							modules.removeAll(admin.getCompany().getModules());
						}
						
						Map<String,CompanyModule> noPrivilegeMap = new HashMap<String,CompanyModule>();
						for(CompanyModule module:modules) {
							if(module!=null){
								noPrivilegeMap.put(module.getLinkUrl(), module);
							}
						}
						session.setAttribute(Constants.Session.COMPANY_PRIVILEGE_MAP, noPrivilegeMap);

						companyService.updateErrorCount(admin.getId(),true);
						
						systemLogService.saveSysOperationLog(new SysOperationLog(admin.getCompanyId(), admin.getId(), "Login", "登录", JSONArray.fromObject(admin).toString(), "商户登录"));
						return "redirect:/company/index.do";
						
					}  else {
						logger.debug("Login failed.");
						req.setAttribute("message", "账户或密码不正确!");
						companyService.updateErrorCount(companyAdmin.getId(),false);
					}
				}else{
					logger.debug("Login failed.");
					req.setAttribute("message", "此账户已锁定！请联系管理员解锁！");
				}
			} else {
				req.setAttribute("message", "验证码不正确!");
			}
		}
		return "/company/login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session != null) {
			session.removeAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_CODE);
			session.removeAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_KEY);
			session.removeAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_AVATAR);
		}
		return "/company/login";
	}
	
	/**
	 * 编辑商户信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditCompany")
	public String toEditCompany(HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		req.setAttribute("company", companyAdmin.getCompany());
		return "/company/company_edit";
	}
	
	/**
	 * 更新保存商户信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateCompany")
	public String updateCompany(Company company, HttpServletRequest req) {
		companyService.saveCompany(company);
		return "redirect:/company/toEditCompany.do";
	}
	
	/**
	 * 加载商户员工
	 * @param req
	 * @return
	 */
	@RequestMapping("/listCompanyAdmin")
	public String listCompanyAdmin(Page<CompanyAdmin> page, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		page.addOrder(Order.desc("createTime"));
		companyService.findCompanyAdmins(page);
		req.setAttribute("isAdmin", companyAdmin.isAdmin());
		return "/company/companyAdmin_list";
	}
	
	/**
	 * 编辑商户员工信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditCompanyAdmin")
	public String toEditCompanyAdmin(String id, HttpServletRequest req) {
		CompanyAdmin companyLogin = getCurrentCompanyAdmin(req);
		CompanyAdmin companyAdmin = companyService.getCompanyAdminById(id);
		
		Map<MessageType,Boolean> map = new HashMap<MessageType,Boolean>();
		if(companyAdmin!=null&&companyAdmin.getMessageTypes() != null){
			for (MessageType type : companyAdmin.getMessageTypes()) {
				if(type!=null){
					map.put(type, true);
				}
			}
		}
		
		List<MessageType> messageTypes = new ArrayList<MessageType>();
		List<ModuleType> moduleTypes = companyLogin.getCompany().getModuleTypes();
		
		if(moduleTypes.contains(ModuleType.HOTEL)){
			MessageType roomBook = MessageType.ROOM_BOOK;
			roomBook.setChecked(map.get(MessageType.ROOM_BOOK)!=null);
			messageTypes.add(roomBook);
//			MessageType roomBookCancel = MessageType.ROOM_BOOK_CANCEL;
//			roomBookCancel.setChecked(map.get(MessageType.ROOM_BOOK_CANCEL)!=null);
//			messageTypes.add(roomBookCancel);
		}
		
		if(moduleTypes.contains(ModuleType.TICKET)){
			MessageType ticketBook = MessageType.TICKET_BOOK;
			ticketBook.setChecked(map.get(MessageType.TICKET_BOOK)!=null);
			messageTypes.add(ticketBook);
		}
		
		if(moduleTypes.contains(ModuleType.MALL)){
			MessageType shopBook = MessageType.SHOP_BOOK;
			shopBook.setChecked(map.get(MessageType.SHOP_BOOK)!=null);
			messageTypes.add(shopBook);
		}
		
		if(moduleTypes.contains(ModuleType.MEAL)){
			MessageType mealBook = MessageType.MEAL_BOOK;
			mealBook.setChecked(map.get(MessageType.MEAL_BOOK)!=null);
			messageTypes.add(mealBook);
		}
		
		MessageType exchangeGift = MessageType.EXCHANGE_GIFT;
		exchangeGift.setChecked(map.get(MessageType.EXCHANGE_GIFT)!=null);
		messageTypes.add(exchangeGift);
		
		
		MessageType customerService = MessageType.CUSTOMERSERVICE;
		customerService.setChecked(map.get(MessageType.CUSTOMERSERVICE)!=null);
		messageTypes.add(customerService);
		
		MessageType operationFailed = MessageType.OPERATION_FAILED;
		operationFailed.setChecked(map.get(MessageType.OPERATION_FAILED)!=null);
		messageTypes.add(operationFailed);
		
		
		MessageType toRemind = MessageType.TO_REMIND;
		toRemind.setChecked(map.get(MessageType.TO_REMIND)!=null);
		messageTypes.add(toRemind);
		
		
		MessageType consumeCard = MessageType.CONSUME_CARD;
		consumeCard.setChecked(map.get(MessageType.CONSUME_CARD)!=null);
		messageTypes.add(consumeCard);
		
		MessageType paySuccess = MessageType.PAY_SUCCESS;
		paySuccess.setChecked(map.get(MessageType.PAY_SUCCESS)!=null);
		messageTypes.add(paySuccess);
		
		MessageType mallOrderVerification = MessageType.MALL_ORDER_VERIFICATION;
		mallOrderVerification.setChecked(map.get(MessageType.MALL_ORDER_VERIFICATION)!=null);
		messageTypes.add(mallOrderVerification);
		
		req.setAttribute("messageTypes", messageTypes);
		req.setAttribute("companyAdmin", companyAdmin);
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		List<CompanyRole> roles = roleService.findAllSysRoles(currentCompanyAdmin.getCompanyId());
		req.setAttribute("roles", roles);
		List<HotelBranchVO> hotelBranchs = hotelService.listHotelBranchVO(currentCompanyAdmin.getCompanyId(), new HotelBranchQuery());
		req.setAttribute("hotelBranchs", hotelBranchs);
		if(companyAdmin != null){
			req.setAttribute("hotelCodes", companyAdmin.getHotelCodes());
		}
		return "/company/companyAdmin_edit";
	}
	
	/**
	 * 更新保存商户员工信息
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/updateCompanyAdmin")
	public String updateCompanyAdmin(CompanyAdmin companyAdmin, HttpServletRequest req) throws UnsupportedEncodingException {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		
//		if(!currentCompanyAdmin.isAdmin()) {
//			return "redirect:/company/listCompanyAdmin.do?message="+URLEncoder.encode("您无权限修改员工信息", "UTF8");
//		}
		
//		String id = companyAdmin.getId();
//		if(StringUtils.isNotBlank(id)) {
//			CompanyAdmin oldCompanyAdmin = companyService.getCompanyAdminById(id);
//			if(oldCompanyAdmin != null && !StringUtils.equals(currentCompanyAdmin.getCompanyId(), oldCompanyAdmin.getCompanyId())) {
//				return "redirect:/company/listCompanyAdmin.do?message="+URLEncoder.encode("您无法修改其它商户的员工信息", "UTF8");
//			}
//		}
		if(companyAdmin.getHotelCode() == null){
			companyAdmin.setHotelCode("");
		}
		companyAdmin.setCompanyId(currentCompanyAdmin.getCompanyId());
		companyService.saveCompanyAdmin(companyAdmin);
		
		CompanyAdmin updateCompanyAdmin = companyService.getCompanyAdminById(companyAdmin.getId());
		boolean bo = false;
		if(updateCompanyAdmin.getMessageTypes() != null){
			for (MessageType mType : updateCompanyAdmin.getMessageTypes()) {
				if(mType == MessageType.CUSTOMERSERVICE){//客服消息
					bo = true;
					break;
				}
			}
		}
		updateCompanyAdmin.setPassword(null);
		companyService.saveCompanyAdmin(updateCompanyAdmin);
		return "redirect:/company/listCompanyAdmin.do";
	}
	
	/**
	 * 删除商户员工
	 * @param ids
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/deleteCompanyAdmin")
	public String deleteCompanyAdmin(String ids, HttpServletRequest req) throws UnsupportedEncodingException {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
//		if(!currentCompanyAdmin.isAdmin()) {
//			return "redirect:/company/listCompanyAdmin.do?message="+URLEncoder.encode("您无权限删除员工信息", "UTF8");
//		}
		
		if(currentCompanyAdmin.getId().equals(ids)){
			return "redirect:/company/listCompanyAdmin.do?message="+URLEncoder.encode("不可以删除当前登录用户", "UTF8");
		}
		CompanyAdmin CompanyAdmin = companyService.getCompanyAdminById(ids);
		if(Boolean.TRUE.equals(CompanyAdmin.isAdmin())){
			return "redirect:/company/listCompanyAdmin.do?message="+URLEncoder.encode("此用户不可删除！", "UTF8");
		}
		
		return "redirect:/company/listCompanyAdmin.do";
	}
	
	/**
	 * 编辑商家支付配置
	 * @param companyId
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/ToEditPayConfig")
	public String editPayConfig(HttpServletRequest req,String id) throws UnsupportedEncodingException {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		PayConfig payConfig = payConfigService.getPayConfigById(currentCompanyAdmin.getCompanyId(),id);
		
		List<PayType> payTypes = payConfigService.getDbNotExistPayType(currentCompanyAdmin.getCompanyId());
		
		if(payConfig==null){
			if(payTypes.size()==0){
				return "redirect:/company/toListPayConfig.do?message="+URLEncoder.encode("无法继续新增", "UTF8");
			}
		}else{
			if(payConfig.getPayType()!=null){
				payTypes.add(payConfig.getPayType());
			}
		}
		
		req.setAttribute("payConfig", payConfig);
		req.setAttribute("payTypes", payTypes);
		req.setAttribute("isGroup", currentCompanyAdmin.getCompany().getGroup());
		
		List<HotelBranchVO> hotelBranchs = hotelService.listHotelBranchVO(currentCompanyAdmin.getCompanyId(), new HotelBranchQuery());
		req.setAttribute("hotelBranchs", hotelBranchs);
		req.setAttribute("company", currentCompanyAdmin.getCompany());
		return "/company/payConfig_edit";
	}
	
	/**
	 * 商家支付配置
	 * @param companyId
	 * @param req
	 * @return
	 */
	@RequestMapping("/toListPayConfig")
	public String listPayConfig(HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		List<PayConfig> payConfigs = payConfigService.findPayConfigAll(currentCompanyAdmin.getCompanyId());
		req.setAttribute("payConfigs", payConfigs);
		return "/company/payConfig_list";
	}
	
	/**
	 * 更新支付配置数据
	 * @param config
	 * @param req
	 * @return
	 */
	@RequestMapping("/updatePayConfig")
	public String updatePayConfig(PayConfig config, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		config.setCompanyId(currentCompanyAdmin.getCompanyId());
		payConfigService.savePayConfig(config);
		return "redirect:/company/toListPayConfig.do";
	}
	
	/**
	 * 删除支付配置数据
	 * @param config
	 * @param req
	 * @return
	 */
	@RequestMapping("/deletePayConfig")
	public String deletePayConfig(String id, HttpServletRequest req) {
		payConfigService.deletePayConfig(id);
		return "redirect:/company/toListPayConfig.do";
	}
	
	/**
	 * 密码找回,获取邮箱
	 * @return
	 */
	@RequestMapping("/toPwdRetrieve")
	public String toPwdRetrieve() {
		return "/company/pwdRetrieve";
	}
	
	/**
	 * 密码找回,发送邮件
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/pwdRetrieve")
	public String pwdRetrieve(String email, HttpServletRequest req) throws UnsupportedEncodingException {
		
		CompanyAdmin admin = companyService.getCompanyAdminByEmail(email);
		if(admin==null){
			return "redirect:/company/toPwdRetrieve.do?message="+URLEncoder.encode("系统不存在邮箱为："+email+"的用户!", "UTF8");
		}
		
		String code = String.valueOf(new Random().nextInt(9000)+1000);
		code = EncryptUtil.md5(code);
		cache.put(email, code, 60*30);
		
		//发送邮件
		String url = "http://localhost:8080/company/toResetPwd.do?code="+code+"&email="+email;
		companyService.sendEmail("15876938124@163.com", "rmpgyrpffzuqnhsc", admin, url);
		return "redirect:/company/toPwdRetrieve.do?message="+URLEncoder.encode("修改密码链接已经发送至您的"+email+"邮箱，请在30分钟内进行修改!", "UTF8");
	}
	
	/**
	 * 进入重新设置密码页面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/toResetPwd")
	public String toResetPwd(String code, String email,HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException {
		if(cache.get(email)!=null&&cache.get(email).equals(code)){
			req.setAttribute("email", email);
			req.setAttribute("code", code);
			return "/company/resetPwd";
		}
		return "redirect:/company/toPwdRetrieve.do?message="+URLEncoder.encode("链接已经过期,请重新申请", "UTF8");
	}
	
	/**
	 * 重新设置密码
	 * @param email
	 * @param code
	 * @param password
	 * @param req
	 * @param res
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/resetPwd")
	public String resetPwd(String email,String code, String password, HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
		
		if(cache.get(email)!=null&&cache.get(email).equals(code)){
			cache.evict(email);
			
			CompanyAdmin admin = companyService.getCompanyAdminByEmail(email);
			if(admin!=null){
				admin.setOldPassword(admin.getPassword());
				admin.setPassword(password);
				companyService.saveCompanyAdmin(admin);
				return "/company/login";
			}
		}
		return "redirect:/company/toResetPwd.do?message="+URLEncoder.encode("链接已经过期", "UTF8");
	}
	
	@RequestMapping("/ajaxCompanyAdminEmailExist")
	@ResponseBody
	public Boolean checkCompanyAdminByEmail(String email) throws IOException{
		return companyService.getCompanyAdminByEmail(email) !=null;
	}
	
	@RequestMapping("/ajaxCompanyAdminAccountExist")
	@ResponseBody
	public Boolean checkCompanyAdminByAccount(String account, HttpServletRequest req) throws IOException{
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		return companyService.getCompanyAdminByAccount(currentCompanyAdmin.getCompanyId(),account) !=null;
	}
	
	@RequestMapping("/toArriveTime")
	public String toArriveTime(Page<ArriveTime> page,HttpServletRequest req){
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, currentCompanyAdmin.getCompanyId());
		page.addOrder(Order.asc("createTime"));
		arriveTimeService.findArriveTimes(page);
		return "/company/arriveTime_list";
	}
	
	@RequestMapping("/toEditArriveTime")
	public String ToEditArriveTime(String id, HttpServletRequest req){
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		ArriveTime at = arriveTimeService.getArriveTimeById(currentCompanyAdmin.getCompanyId(), id);
		req.setAttribute("arriveTime", at);
		return "/company/arriveTime_edit";
	}
	
	@RequestMapping("/updateArriveTime")
	public String updateArriveTime(ArriveTime arriveTime, HttpServletRequest req){
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		arriveTime.setCompanyId(currentCompanyAdmin.getCompanyId());
		
		if(arriveTime.getIsDefault()){
			ArriveTime defaultArriveTime = arriveTimeService.getDefaultArriveTime(currentCompanyAdmin.getCompanyId());
			if(defaultArriveTime!=null){
				defaultArriveTime.setIsDefault(false);
				arriveTimeService.saveArriveTime(defaultArriveTime);
			}
		}
		
		if(StringUtils.isNotBlank(arriveTime.getId())){
			ArriveTime oldArriveTime = arriveTimeService.getArriveTimeById(currentCompanyAdmin.getCompanyId(), arriveTime.getId());
			BeanUtil.copyProperties(oldArriveTime, arriveTime,true);
			arriveTimeService.saveArriveTime(oldArriveTime);
		}else{
			arriveTime.setCreateTime(new Date());
			arriveTimeService.saveArriveTime(arriveTime);
		}
		return "redirect:/company/toArriveTime.do";
	}
	
	@RequestMapping("/deleteArriveTime")
	public String deleteArriveTime(String id){
		arriveTimeService.deleteArriveTime(id);
		return "redirect:/company/toArriveTime.do";
	}
	
	@RequestMapping("/ajaxArriveTimeExist")
	@ResponseBody
	public boolean ajaxArriveTimeExist(String time, HttpServletRequest req){
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		return arriveTimeService.findArriveTimeByTime(time,currentCompanyAdmin.getCompanyId()) !=null;
	}
	
	@RequestMapping("/toTemplateMessage")
	public String toTemplateMessage(Page<TemplateMessage> page,HttpServletRequest req){
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, currentCompanyAdmin.getCompanyId());
		templateMessageService.findTemplateMessages(page);
		return "/company/templateMessage_list";
	}
	
	@RequestMapping("/toEditTemplateMessage")
	public String ToEditTemplateMessage(String id, HttpServletRequest req) throws UnsupportedEncodingException{
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		TemplateMessage templateMessage = templateMessageService.getTemplateMessageById(currentCompanyAdmin.getCompanyId(), id);
		req.setAttribute("templateMessage", templateMessage);
		
		List<MessageType> messageTypes = new ArrayList<MessageType>();
		List<ModuleType> moduleTypes = currentCompanyAdmin.getCompany().getModuleTypes();
		if(moduleTypes.contains(ModuleType.HOTEL)){
			messageTypes.add(MessageType.ROOM_BOOK);
			messageTypes.add(MessageType.ROOM_BOOK_SUCCESS);
			messageTypes.add(MessageType.ROOM_BOOK_FAIL);
			messageTypes.add(MessageType.ROOM_BOOK_CANCEL);
		}
		
		if(moduleTypes.contains(ModuleType.TICKET)){
			messageTypes.add(MessageType.TICKET_BOOK_SUCCESS);
			messageTypes.add(MessageType.TICKET_BOOK_FAIL);
			messageTypes.add(MessageType.TICKET_BOOK_CANCEL);
			messageTypes.add(MessageType.TICKET_BOOK);
		}
		
		if(moduleTypes.contains(ModuleType.MALL)){
			messageTypes.add(MessageType.SHOP_BOOK_CANCEL);
			messageTypes.add(MessageType.SHOP_DELIVERED);
			messageTypes.add(MessageType.SHOP_BOOK);
		}
		
		if(moduleTypes.contains(ModuleType.MEAL)){
			messageTypes.add(MessageType.MEAL_BOOK_SUCCESS);
			messageTypes.add(MessageType.MEAL_BOOK_FAIL);
			messageTypes.add(MessageType.MEAL_BOOK_CANCEL);
			messageTypes.add(MessageType.MEAL_ORDER_CANCEL);
			messageTypes.add(MessageType.MEAL_BOOK);
		}
		
		messageTypes.add(MessageType.EXCHANGE_GIFT);
		messageTypes.add(MessageType.CREDIT_EXCHANGE_SUCCESS);
		messageTypes.add(MessageType.CREDIT_EXCHANGE_FAIL);
		messageTypes.add(MessageType.FOCUS_GIFTS);
		messageTypes.add(MessageType.MEMBER_CONSUMER);
		messageTypes.add(MessageType.OPERATION_FAILED);
		messageTypes.add(MessageType.CUSTOMERSERVICE);
		messageTypes.add(MessageType.TO_REMIND);
		messageTypes.add(MessageType.GET_COUPON);
		messageTypes.add(MessageType.CHECK_OUT);
		messageTypes.add(MessageType.CONSUME_CARD);
		messageTypes.add(MessageType.USE_REMIND);
		messageTypes.add(MessageType.PAY_SUCCESS);
		messageTypes.add(MessageType.COMBO_BOOK_SUCCESS);
		messageTypes.add(MessageType.MEMBER_REGISTERED);
//		messageTypes.add(MessageType.MEMBER_REGISTERED_FAIL);
		messageTypes.add(MessageType.COMMENT_MESSAGE);
		messageTypes.add(MessageType.GET_QIANDAO);
		messageTypes.add(MessageType.MALL_ORDER_VERIFICATION);
		
		List<TemplateMessage> templateMessages = templateMessageService.findTemplateMessageAll(currentCompanyAdmin.getCompanyId());
		for (TemplateMessage tm : templateMessages) {
			if(tm.getMessageType()!=null){
				messageTypes.remove(tm.getMessageType());
			}
		}
		
		if(StringUtils.isBlank(id)&&messageTypes.size()==0){
			return "redirect:/company/toTemplateMessage.do?message="+URLEncoder.encode("无法继续新增", "UTF8");
		}
		
		if(templateMessage!=null&&templateMessage.getMessageType()!=null){
			messageTypes.add(templateMessage.getMessageType());
		}
		req.setAttribute("messageTypes", messageTypes);
		return "/company/templateMessage_edit";
	}
	
	@RequestMapping("/updateTemplateMessage")
	public String updateTemplateMessage(TemplateMessage templateMessage, HttpServletRequest req){
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		templateMessage.setCompanyId(currentCompanyAdmin.getCompanyId());
		
		if(StringUtils.isBlank(templateMessage.getId())){
			templateMessage.setCreateTime(new Date());
		}
		templateMessageService.saveTemplateMessage(templateMessage);
		return "redirect:/company/toTemplateMessage.do";
	}
	
	@RequestMapping("/deleteTemplateMessage")
	public String deleteTemplateMessage(String id){
		templateMessageService.deleteTemplateMessage(id);
		return "redirect:/company/toTemplateMessage.do";
	}
	
	/**
	 * 商家短信接口配置
	 * @param companyId
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditSmsConfig")
	public String toEditSmsConfig(HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		SmsConfig config = smsConfigService.getSmsConfig(companyAdmin.getCompanyId());
		req.setAttribute("company", companyAdmin.getCompany());
		req.setAttribute("config", config);
		return "/company/smsConfig_edit";
	}
	
	/**
	 * 更新短信接口数据
	 * @param config
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateSmsConfig")
	public String updateSmsConfig(SmsConfig config, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		config.setCompanyId(companyAdmin.getCompanyId());
		smsConfigService.saveSmsConfig(config);
		return "redirect:/company/toEditSmsConfig.do";
	}
	
	/**
	 * 刷新短信余额
	 * @param config
	 * @param req
	 * @return
	 */
	@RequestMapping("/refreshSmsBalance")
	@ResponseBody
	public int refreshSmsBalance(HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		SmsConfig config = smsConfigService.getSmsConfig(companyAdmin.getCompanyId());
		int surplus = 0;
		if(config != null) {
			surplus = JXDSmsUtil.surplus(config.getAccount(), config.getPwd());
			config.setBalance(surplus);
			smsConfigService.saveSmsConfig(config);
		}
		return surplus;
	}
	
	/**
	 * 解除锁定
	 */
	@RequestMapping("/removeLock")
	public String removeLock(String id,HttpServletRequest req) {
		companyService.updateErrorCount(id,true);
		return "redirect:/company/listCompanyAdmin.do";
	}
	
	/**
	 * 系统配置
	 */
	@RequestMapping("/toSysParamConfig")
	public String toSysParamConfig(HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		SysParamConfig sysParamConfig = sysParamConfigService.getSysParamConfig(companyAdmin.getCompanyId());
		req.setAttribute("sysParamConfig", sysParamConfig);
		return "/company/systemConfig";
	}
	
	@RequestMapping("/editSysParamConfig")
	public String editSysParamConfig(SysParamConfig sysParamConfig,HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		sysParamConfig.setCompanyId(companyAdmin.getCompanyId());
		sysParamConfigService.saveSystemConfig(sysParamConfig);
		return "redirect:/company/toSysParamConfig.do";
	}
}
