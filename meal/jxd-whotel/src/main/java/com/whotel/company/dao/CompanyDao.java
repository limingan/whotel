package com.whotel.company.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.company.entity.Company;

@Repository
public class CompanyDao extends UnDeletedEntityMongoDao<Company, String> {

}
