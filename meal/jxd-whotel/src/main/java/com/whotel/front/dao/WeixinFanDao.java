package com.whotel.front.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.front.entity.WeixinFan;

/**
 * 微信粉丝数据操作
 * @author 冯勇
 */
@Repository
public class WeixinFanDao extends UnDeletedEntityMongoDao<WeixinFan, String> {
}
