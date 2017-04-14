package com.whotel.webiste.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.webiste.entity.ColumnTemplate;

@Repository
public class ColumnTemplateDao extends UnDeletedEntityMongoDao<ColumnTemplate, String> {

}
