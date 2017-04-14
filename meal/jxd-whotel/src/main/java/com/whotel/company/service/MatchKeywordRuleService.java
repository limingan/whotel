package com.whotel.company.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.BeanUtil;
import com.whotel.company.dao.MatchKeywordRuleDao;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.MatchKeyword;
import com.whotel.company.entity.MatchKeywordRule;
import com.whotel.company.entity.ResponseMsg;

/**
 * 关键词自动回复业务服务类
 * @author 冯勇
 *
 */
@Service
public class MatchKeywordRuleService {
	
	@Autowired
	private MatchKeywordRuleDao matchKeywordRuleDao;
	
	/**
	 * 保存关键词规则
	 * @param matchKeywordRule
	 */
	public void saveMatchKeywordRule(MatchKeywordRule matchKeywordRule) {
		if(matchKeywordRule != null) {
			String id = matchKeywordRule.getId();
			List<MatchKeyword> keywords = matchKeywordRule.getKeywords();
			if(StringUtils.isNotBlank(id)) {
				MatchKeywordRule updateMatchKeywordRule = getMatchKeywordRuleById(id);
				BeanUtil.copyProperties(updateMatchKeywordRule, matchKeywordRule, true);
				matchKeywordRule = updateMatchKeywordRule;
			} else {
				matchKeywordRule.setCreateTime(new Date());
			}
			Boolean def = matchKeywordRule.getDef();
			if(def == null) {
				matchKeywordRule.setDef(false);
			}
			List<MatchKeyword> validKeyword = null;
			if(keywords != null) {
				validKeyword = new ArrayList<MatchKeyword>();
				for(MatchKeyword keyword:keywords) {
					if(keyword != null && StringUtils.isNotBlank(keyword.getKeyword())) {           //防止删除导致的null内容
						validKeyword.add(keyword);
					}
				}
			}
			matchKeywordRule.setKeywords(validKeyword);
			matchKeywordRuleDao.save(matchKeywordRule);
		}
	}
	
	public MatchKeywordRule getMatchKeywordRuleById(String id) {
		return matchKeywordRuleDao.get(id);
	}
	
	public void deleteMatchKeywordRule(MatchKeywordRule rule) {
		matchKeywordRuleDao.delete(rule);
	}
	
	public List<MatchKeywordRule> findMatchKeywordRules(String companyId) {
		return matchKeywordRuleDao.findByProperty("companyId", companyId);
	}
	
	public void findMatchKeywordRules(Page<MatchKeywordRule> page) {
		matchKeywordRuleDao.find(page);
	}
	
	/**
	 * 获取系统默认回复
	 * @param companyId
	 * @return
	 */
	public MatchKeywordRule getDefMatchKeywordRule(String companyId) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("def", true);
		return matchKeywordRuleDao.getByProperties(properties);
	}
	
	/**
	 * 批量删除关键词匹配规则
	 * @param ids
	 */
	public void deleteMoreMatchKeywordRule(String ids, String companyId) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				MatchKeywordRule rule = getMatchKeywordRuleById(id);
				rule.setCompanyId(companyId);
				deleteMatchKeywordRule(rule);
			}
		}
	}

	/**
	 * 创建默认回复信息
	 * @param company
	 */
	public void createDefMatchKeywordRule(Company company) {
		
		if(company != null) {
			MatchKeywordRule defRule = new MatchKeywordRule();
			defRule.setCompanyId(company.getId());
			defRule.setDef(true);
			defRule.setName("系统默认");
			ResponseMsg rm = new ResponseMsg();
			rm.setText("欢迎关注"+company.getName()+"公众号！");
			defRule.setResponseMsg(rm);
			saveMatchKeywordRule(defRule);
		}
	}
}
