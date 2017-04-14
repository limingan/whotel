package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.LuckDrawMember;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class LuckDrawMemberDao extends MongoDao<LuckDrawMember, String> {
	
}
