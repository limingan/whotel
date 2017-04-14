package com.whotel.admin.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.admin.entity.SysNotice;
import com.whotel.admin.service.SysNoticeService;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.QueryParam;
import com.whotel.common.enums.FilterModel;

/**
 * 系统公告管理
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/admin/notice")
public class SysNoticeManageController extends BaseAdminController  {

	@Autowired
	private SysNoticeService noticeService;
	
	/**
	 * 加载系统公告列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/listSysNotice")
	public String listSysNotice(Page<SysNotice> page,QueryParam queryParam, HttpServletRequest req) {
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			String title = params.get("title");
			if(StringUtils.isNotBlank(title)) {
				page.addFilter("title", FilterModel.LIKE, title);
			}
		}
		page.addOrder(Order.desc("createTime"));
		noticeService.findSysNotices(page);
		return "/admin/notice/notice_list";
	}
	
	/**
	 * 编辑系统公告信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditSysNotice")
	public String toEditSysNotice(String id, HttpServletRequest req) {
		SysNotice notice = noticeService.getSysNoticeById(id);
		req.setAttribute("notice", notice);
		return "/admin/notice/notice_edit";
	}
	
	/**
	 * 发布或下架公告
	 */
	@RequestMapping("/publishSysNotice")
	public String publishSysNotice(String id, HttpServletRequest req) {
		SysNotice notice = noticeService.getSysNoticeById(id);
		if(Boolean.TRUE.equals(notice.getIsPublish())){
			notice.setIsPublish(false);
		}else{
			notice.setIsPublish(true);
		}
		noticeService.saveSysNotice(notice);
		return "redirect:/admin/notice/listSysNotice.do";
	}
	
	/**
	 * 更新保存系统公告信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateSysNotice")
	public String updateSysNotice(SysNotice sysNotice, HttpServletRequest req) {
		sysNotice.setCreateTime(new Date());
		noticeService.saveSysNotice(sysNotice);
		return "redirect:/admin/notice/listSysNotice.do";
	}
	
	/**
	 * 删除系统公告
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteSysNotice")
	public String deleteSysNotice(String ids, HttpServletRequest req) {
		noticeService.deleteMoreSysNotice(ids);
		return "redirect:/admin/notice/listSysNotice.do";
	}
}
