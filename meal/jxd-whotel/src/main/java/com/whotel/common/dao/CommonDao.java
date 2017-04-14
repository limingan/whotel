package com.whotel.common.dao;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.common.entity.OwnerCheck;

/**
 * 公共DAO
 * @author 冯勇
 */
public class CommonDao<T, PK extends Serializable> extends MongoDao<T, PK> {
	@Autowired
	private OwnerCheckDao<T, PK> ownerCheckDao;

	@Override
	public void save(T entity) {
		if(entity == null)
			return;
		if (entity instanceof OwnerCheck) {
			if (ownerCheckDao.isOwner(entity, this)) {
				super.save(entity);
			} else {
				throw new RuntimeException("no auth to save entity:"
						+ entity.getClass().getSimpleName() + ","
						+ entity.toString());
			}
		} else {
			super.save(entity);
		}
	}
	
	@Override
	public void delete(T entity) {
		if(entity == null)
			return;
		if (entity instanceof OwnerCheck) {
			if (ownerCheckDao.isOwner(entity, this)) {
				super.delete(entity);
			} else {
				throw new RuntimeException("no auth to delete entity:"
						+ entity.getClass().getSimpleName() + ","
						+ entity.toString());
			}
		} else {
			super.delete(entity);
		}
	}
}
