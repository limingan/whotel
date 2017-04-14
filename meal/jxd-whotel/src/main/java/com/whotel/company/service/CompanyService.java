package com.whotel.company.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.ResultData;
import com.whotel.common.util.BeanUtil;
import com.whotel.common.util.EncryptUtil;
import com.whotel.company.dao.CompanyAdminDao;
import com.whotel.company.dao.CompanyDao;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.enums.MessageType;
import com.whotel.ext.sms.EmailUtils;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAdminDao companyAdminDao;
	
	/**
	 * 注册商户
	 */
	public ResultData register(String companyName, String code, Boolean group, CompanyAdmin companyAdmin) {
		ResultData rm = null;
		if(StringUtils.isBlank(companyName) || StringUtils.isBlank(code) || companyAdmin == null) {
			return null;
		}
		Company hasCompany = getCompanyByCode(code);
		
		if(hasCompany != null) {
			rm = new ResultData(Constants.MessageCode.RESULT_ERROR, "商户编码已经被注册！");
		} else {
			Company company = new Company();
			company.setCode(code);
			company.setName(companyName);
			company.setValid(true);
			company.setGroup(group);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, 1);
			company.setValidTime(cal.getTime());
			company.setAdminAccount(companyAdmin.getAccount());
			company.setAdminPwd(companyAdmin.getPassword());
			saveCompany(company);
			companyAdmin.setCompanyId(company.getId());
			companyAdmin.setAdmin(true);
			company.setCreatePerson(companyAdmin.getName());
			saveCompanyAdmin(companyAdmin);
			rm = new ResultData(Constants.MessageCode.RESULT_SUCCESS, "注册成功！");
		}
		return rm;
	}
	
	
	/**
	 * 商户管理员后台登录
	 */
	public CompanyAdmin login(String code, String account, String password) {
		if(StringUtils.isNotBlank(code) && StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)) {
			CompanyAdmin companyAdmin = getCompanyAdmin(code, account);
			if(companyAdmin != null && StringUtils.equals(EncryptUtil.md5(password), companyAdmin.getPassword())) {
				return companyAdmin;
			}
		}
		return null;
	}

	
	/**
	 * 保存商家信息
	 * @param company
	 */
	public void saveCompany(Company company) {
		if(company != null) {
			if(StringUtils.isBlank(company.getTheme())){
				company.setTheme(null);
			}
			
			String id = company.getId();
			Date now = new Date();
			
			if(StringUtils.isNotBlank(id)) {
				company.setUpdateTime(now);
				Company updateCompany = getCompanyById(id);
				BeanUtil.copyProperties(updateCompany, company, true);
				companyDao.save(updateCompany);
			} else {
				company.setCreateTime(now);
				companyDao.save(company);
			}
			
			if(StringUtils.isNotBlank(company.getAdminAccount())&&StringUtils.isNotBlank(company.getAdminPwd())){
				CompanyAdmin companyAdmin = getCompanyAdminByAccount(company.getId(),company.getAdminAccount());
				if(companyAdmin!=null){
					companyAdmin.setPassword(EncryptUtil.md5(company.getAdminPwd()));
				}
				companyAdminDao.save(companyAdmin);
			}
		}
	}
	
	/**
	 * 分页查找商户信息
	 * @param page 分页工具类
	 */
	public void findCompanys(Page<Company> page) {
		companyDao.find(page);
	}
	
	public Company getCompanyById(String id) {
		return companyDao.get(id);
	}
	
	public Company getCompanyByCode(String code) {
		return companyDao.getByProperty("code", code);
	}
	
	/**
	 * 根据商户编码及管理员账号获取管理员信息
	 * @param code
	 * @param account
	 * @return
	 */
	public CompanyAdmin getCompanyAdmin(String code, String account) {
		Company company = getCompanyByCode(code);
		if(company != null) {
			return getCompanyAdminByAccount(company.getId(), account);
		}
		return null;
	}
	
	public CompanyAdmin getCompanyAdminByOpenId(String companyId, String openId){
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("openId", openId);
		return companyAdminDao.getByProperties(properties);
	}
	
	public CompanyAdmin getCompanyAdminByAccount(String companyId, String account) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		if(StringUtils.isNotBlank(companyId)){
			properties.put("companyId", companyId);
		}
		if(StringUtils.isNotBlank(account)) {
			properties.put("account", account);
		}
		return companyAdminDao.getByProperties(properties);
	}
	
	/**
	 * 根据商户id获取超级管理员
	 * @param companyId
	 * @return
	 */
	public CompanyAdmin getSupperCompanyAdmin(String companyId) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("admin", true);
		return companyAdminDao.getByProperties(properties, Order.asc("createTime"));
	}
	
	/**
	 * 根据商户id获取可登录的管理员
	 * @param companyId
	 * @return
	 */
	public CompanyAdmin getFirstCompanyAdmin(String companyId) {
		return companyAdminDao.getByProperty("companyId", companyId, Order.asc("createTime"));
	}
	
	/**
	 * 根据商户邮箱获取管理员信息
	 * @param email
	 * @return
	 */
	public CompanyAdmin getCompanyAdminByEmail(String email) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("email", email);
		return companyAdminDao.getByProperties(properties);
	}
	
	public List<CompanyAdmin> findCompanyAdminAll(String companyId,String hotelCode){
		Query<CompanyAdmin> query = companyAdminDao.createQuery();
		query.field("companyId").equal(companyId);
		if(StringUtils.isNotBlank(hotelCode)){
			query.field("hotelCode").contains(hotelCode);
		}
		return companyAdminDao.findByQuery(query).asList();
	}
	
	/**
	 * 保存商家管理员信息
	 * @param companyAdmin
	 */
	public void saveCompanyAdmin(CompanyAdmin companyAdmin) {
		CompanyAdmin updateCompanyAdmin = null;
		if(companyAdmin != null) {
			
			//保存管理员同步商户
			if(Boolean.TRUE.equals(companyAdmin.isAdmin())
					&&StringUtils.isNotBlank(companyAdmin.getPassword())){
				Company company = companyDao.get(companyAdmin.getCompanyId());
				company.setAdminAccount(companyAdmin.getAccount());
				company.setAdminPwd(companyAdmin.getPassword());
				companyDao.save(company);
			}
			
			String password = companyAdmin.getPassword();
			if(StringUtils.isNotBlank(password)) {
				companyAdmin.setPassword(EncryptUtil.md5(password));
			}else{
				companyAdmin.setPassword(null);
			}
			if(companyAdmin.getMessageTypes()==null){
				companyAdmin.setMessageTypes(new ArrayList<MessageType>());
			}
			
			String id = companyAdmin.getId();
			Date now = new Date();
			if(StringUtils.isNotBlank(id)) {
				companyAdmin.setUpdateTime(now);
				updateCompanyAdmin = getCompanyAdminById(id);
				BeanUtil.copyProperties(updateCompanyAdmin, companyAdmin, true);
			} else {
				updateCompanyAdmin = companyAdmin;
				updateCompanyAdmin.setCreateTime(now);
			}
			companyAdminDao.save(updateCompanyAdmin);
		}
	}
	
	/**
	 * 分页查找商户管理员信息
	 * @param page 分页工具类
	 */
	public void findCompanyAdmins(Page<CompanyAdmin> page) {
		companyAdminDao.find(page);
	}
	
	public CompanyAdmin getCompanyAdminById(String id) {
		return companyAdminDao.get(id);
	}

    public void deleteCompanyAdmin(CompanyAdmin companyAdmin) {
    	companyAdminDao.delete(companyAdmin);
    }
    
	public void deleteMoreCompanyAdmin(String ids, String companyId) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				CompanyAdmin companyAdmin = getCompanyAdminById(id);
				companyAdmin.setCompanyId(companyId);
				deleteCompanyAdmin(companyAdmin);
			}
		}
	}
	
	/**
	 * 发送邮件
	 * @param userEmail 用户账号
	 * @param userPsw 用户密码
	 * @param toEmail 接收账号
	 * @param url
	 */
	public void sendEmail(final String userEmail,final String userPsw,CompanyAdmin admin,String url){
		StringBuffer sb = new StringBuffer();
		sb.append("<span style=\"font-weight:bold;\">尊敬的捷信达微信商户平台用户：</span><br/>");
		sb.append("您好!");
		sb.append("您的捷信达微信商户平台登录帐号:");
		sb.append(admin.getAccount());
		sb.append("申请了密码重置，请" + "<a href=\"" + url + "\">点击这里</a>进行密码重置。<br/>");
		sb.append("为保障您帐号的安全性，以上链接有效期为30分钟，并将在点击一次后失效！<br/>");
		sb.append("如果以上链接无法点击，可以复制以下链接在浏览器中打开：<br/>");
		sb.append(url+"<br/>");
		sb.append("如果你错误的收到了本电子邮件，请您忽略上述内容<br/>");
		sb.append("<br/>");
		sb.append("<span style=\"font-weight:bold;\">" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "</span><br/>");
		sb.append("<br/>");
		sb.append("友情提示：此邮件由系统自动发送，请勿回复。");
		EmailUtils.sendEmail(userEmail, userPsw, admin.getEmail(), "捷信达科技有限公司", sb.toString());
	}
	
	/**
	 * 修改错误次数
	 */
	public void updateErrorCount(String companyAdminId,boolean isSuccess){
		CompanyAdmin companyAdmin = getCompanyAdminById(companyAdminId);
		if(isSuccess){
			companyAdmin.setErrorCount(0);
		}else{
			companyAdmin.setErrorCount(companyAdmin.getErrorCount()==null?1:companyAdmin.getErrorCount()+1);
		}
		companyAdminDao.save(companyAdmin);
	}
}
