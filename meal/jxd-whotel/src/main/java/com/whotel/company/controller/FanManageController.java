package com.whotel.company.controller;

import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.card.entity.MarketingFan;
import com.whotel.card.entity.Member;
import com.whotel.card.entity.RecommendFan;
import com.whotel.card.service.MemberService;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.QueryParam;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.enums.TradeStatus;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.ExportExcel;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.PublicNo;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.WeixinFanService;

/**
 * 商户粉丝管理
 * 
 * @author 冯勇
 */
@Controller
@RequestMapping("/company")
public class FanManageController extends BaseCompanyController {

	@Autowired
	private WeixinFanService weixinFanService;
	
	@Autowired
	private MemberService memberService;

	/**
	 * 加载商户粉丝
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/listWeixinFan")
	public String listWeixinFan(Page<WeixinFan> page, QueryParam queryParam, HttpServletRequest req) {
		PublicNo publicNo = getCurrentPublicNo(req);
		if (publicNo != null) {
			if (queryParam != null && queryParam.getParams() != null) {
				Map<String, String> params = queryParam.getParams();

				String nickname = params.get("nickname");
				if (StringUtils.isNotBlank(nickname)) {
					page.addFilter("nickName", FilterModel.LIKE, nickname);
				}
				
				String startDate = params.get("startDate");
				if (StringUtils.isNotBlank(startDate)) {
					page.addFilter("createTime", FilterModel.GE, DateUtil.parseDate(startDate));
				}
				
				String endDate = params.get("endDate");
				if (StringUtils.isNotBlank(endDate)) {
					page.addFilter("createTime", FilterModel.LE, DateUtil.parseDate(endDate));
				}
			}
			page.addFilter("nickName", FilterModel.IS, "exist");
			page.addFilter("devCode", FilterModel.EQ, publicNo.getDeveloperCode());
			page.addOrder(Order.desc("createTime"));
			weixinFanService.findWeixinFans(page);
		}
		return "/company/fan_list";
	}
	
	/**
	 * 加载商户会员
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/listMember")
	public String listMember(Page<Member> page, QueryParam queryParam, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			String name = params.get("name");
			String mobile = params.get("mobile");

			if (StringUtils.isNotBlank(name)) {
				page.addFilter("name", FilterModel.LIKE, name);
			}
			if (StringUtils.isNotBlank(mobile)) {
				page.addFilter("mobile", FilterModel.LIKE, mobile);
			}
			
			String startDate = params.get("startDate");
			if (StringUtils.isNotBlank(startDate)) {
				page.addFilter("createTime", FilterModel.GE, DateUtil.parseDate(startDate));
			}
			
			String endDate = params.get("endDate");
			if (StringUtils.isNotBlank(endDate)) {
				page.addFilter("createTime", FilterModel.LE, DateUtil.parseDate(endDate));
			}
		}
		page.addOrder(Order.desc("createTime"));
		memberService.findMembers(page);;
		return "/company/member_list";
	}
	
	/**
	 * 导出商户会员管理表
	 */
	@RequestMapping("/downloadMember")
	public void downloadGoodsOrder(String mobile,Date startDate,Date endDate,HttpServletRequest req,HttpServletResponse res) {
		try {
			CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
			List<Member> member = memberService.findMemberByMobile(mobile, companyAdmin.getCompanyId(), startDate, endDate);
			
			File file = new File("商户会员.xls");
			ExportExcel exportExecl = new ExportExcel();
			List<Object[]> columns = new ArrayList<Object[]>();
			columns.add(new Object[]{"姓名",  "name",  15});
			columns.add(new Object[]{"性别",  "gender.label",  7});
			columns.add(new Object[]{"手机",  "mobile",  15});
			columns.add(new Object[]{"邮箱",  "email",  15});
			columns.add(new Object[]{"地址",  "addr",  15});
			columns.add(new Object[]{"创建时间",  "createTime",  15});

			exportExecl.setColumns(columns);
	        exportExecl.setDataSource(member);
	        exportExecl.export(file);
	        
			byte[] b = FileUtils.readFileToByteArray(file);
			res.setContentType("application/x-msdownload");
			res.setHeader("Content-Disposition", "inline; filename="+URLEncoder.encode("商户会员.xls", "UTF8"));
			OutputStream out = res.getOutputStream();
			out.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除会员
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/deleteMember")
	public String deleteMember(String id,HttpServletRequest req) throws UnsupportedEncodingException {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		memberService.deleteMember(id,companyAdmin.getId());
		return "redirect:/company/listMember.do?message="+URLEncoder.encode("删除成功！", "UTF8");
	}
}
