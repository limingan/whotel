package com.whotel.meal.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.meal.entity.Shuffle;
/**
 * 市别数据操作
 * @author Administrator
 *
 */
@Repository
public class ShuffleDao extends UnDeletedEntityMongoDao<Shuffle, String> {

}
