package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.Member;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class MemberDao extends MongoDao<Member, String> {
	
}
