package com.whotel.company.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.company.entity.CompanyRole;

@Repository
public class CompanyRoleDao extends MongoDao<CompanyRole, String> {

}
