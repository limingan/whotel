package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.WinningMember;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class WinningMemberDao extends MongoDao<WinningMember, String> {
	
}
