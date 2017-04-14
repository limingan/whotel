package com.whotel.common.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.common.entity.Region;

@Repository
public class RegionDao extends MongoDao<Region, String> {

}
