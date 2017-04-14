package com.whotel.card.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.stereotype.Repository;

import com.whotel.card.entity.RecommendFan;
import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.common.util.DateUtil;

@Repository
public class RecommendFanDao extends MongoDao<RecommendFan, String> {
	
	public List<RecommendFan> findRecommendFans(String mobile,String recommendOpenId,Date beginDate,Date endDate){
		Query<RecommendFan> query = createQuery();
		query.field("recommendOpenId").equal(recommendOpenId);
		if(StringUtils.isNotBlank(mobile)){
			query.field("mobile").equal(mobile);
		}
		if(beginDate!=null){
			query.field("createTime").greaterThanOrEq(DateUtil.getStartTime(beginDate));
		}
		if(endDate!=null){
			query.field("createTime").lessThanOrEq(DateUtil.getEndTime(endDate));
		}
		query.order("-createTime");
		QueryResults qr = this.findByQuery(query);
		return qr.asList();
	}
}
