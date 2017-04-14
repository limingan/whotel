package com.whotel.company.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.company.entity.CompanyModule;

@Repository
public class CompanyModuleDao extends MongoDao<CompanyModule, String> {

}
