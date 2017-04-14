package com.whotel.company.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.company.entity.PicResource;

@Repository
public class PicResourceDao extends UnDeletedEntityMongoDao<PicResource, String> {

}
