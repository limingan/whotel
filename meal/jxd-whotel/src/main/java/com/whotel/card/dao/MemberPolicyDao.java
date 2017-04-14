package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.MemberPolicy;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class MemberPolicyDao extends MongoDao<MemberPolicy, String> {
	
}
