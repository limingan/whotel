package com.whotel.system.controller;

import java.text.SimpleDateFormat;
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
import com.whotel.common.util.DateUtil;
import com.whotel.company.controller.BaseCompanyController;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.system.entity.SysOperationLog;
import com.whotel.system.service.SystemLogService;

@Controller
@RequestMapping("/company/syslog")
public class SystemLogController extends BaseCompanyController {
	
	@Autowired
	private SystemLogService systemLogService;
	
	@RequestMapping("/listSysOperationLogs")
	public String listSysOperationLogs(Page<SysOperationLog> page, QueryParam queryParam,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ ,companyAdmin.getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			
			String moduleType = params.get("moduleType");
			if(StringUtils.isNotBlank(moduleType)) {
				page.addFilter("moduleTypes", FilterModel.EQ ,moduleType);
			}
		}
//		page.addFilter("moduleTypes", FilterModel.EQ ,"MALL");
		page.addOrder(Order.desc("createTime"));
		systemLogService.findSysOperationLogs(page);
		req.setAttribute("company", companyAdmin.getCompany());
		return "/company/systemLog_list";
	}
}
