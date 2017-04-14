package com.whotel.company.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.company.entity.NewsResource;

@Repository
public class NewsResourceDao extends UnDeletedEntityMongoDao<NewsResource, String> {

}
