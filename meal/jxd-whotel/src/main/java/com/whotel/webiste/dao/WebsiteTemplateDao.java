package com.whotel.webiste.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.webiste.entity.WebsiteTemplate;

@Repository
public class WebsiteTemplateDao extends UnDeletedEntityMongoDao<WebsiteTemplate, String> {

}
