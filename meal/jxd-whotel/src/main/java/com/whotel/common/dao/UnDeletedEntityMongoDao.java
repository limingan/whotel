package com.whotel.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.util.Assert;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.entity.UnDeletedEntity;

/**
 * UnDeletedEntity操作dao
 * 
 * 只支持delete(T entity)，delete(T entity, boolean physicallyDeleted)删除方法，其它删除方法不支持
 * 
 * delete(T entity)为假删除 delete(T entity, boolean physicallyDeleted)中的physicallyDeleted为true时才真删除
 * 
 * 所有find方法及count方法已过滤deleted为true的实体，所有get方法没有过滤
 * 
 * 
 * @author
 * 
 */
public class UnDeletedEntityMongoDao<T extends UnDeletedEntity, PK extends Serializable> extends CommonDao<T, PK> {

	private static final String FLD_DELETED = "deleted <>";

	@Override
	public List<T> findAll(Order[] orders) {
		Query<T> query = this.createQuery();
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		query.filter(FLD_DELETED, true);
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findAll() {
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		return morphiaDao.find(query).asList();
	}

	/**
	 * <pre>
	 * findAll(true)	= all deleted entities
	 * findAll(false)	= all non-deleted entities
	 * findAll(null)	= both non-deleted and deleted entities
	 * </pre>
	 * 
	 * @param deleted
	 * @return
	 */
	public List<T> findAll(Boolean deleted) {
		Query<T> query = createQuery();
		if (deleted != null)
			query.filter(FLD_DELETED, !deleted);
		return morphiaDao.find(query).asList();
	}

	@Override
	public Query<T> createQuery() {
		Query<T> query = morphiaDao.createQuery();
		query.filter(FLD_DELETED, true);
		return query;
	}

	/**
	 * true - delete ; false - all;
	 * 
	 * @param isDel
	 * @return
	 */
	public Query<T> createQuery(boolean isDel) {
		Query<T> query = morphiaDao.createQuery();
		if (!isDel) {
			return query;
		} else {
			query.filter("deleted = ", true);
		}
		return query;
	}

	@Override
	public List<T> findAll(int... rowStartIdxAndCount) {
		Query<T> query = createQuery();
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
			query.offset(rowStartIdxAndCount[0]);
		}
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 1) {
			query.limit(rowStartIdxAndCount[1]);
		}
		query.filter(FLD_DELETED, true);
		return morphiaDao.find(query).asList();
	}

	@Override
	public QueryResults<T> findByQuery(Query<T> query) {
		query.filter(FLD_DELETED, true);
		return super.findByQuery(query);
	}

	@Override
	public List<T> findByProperty(String propName, Serializable propValue) {
		Assert.hasText(propName);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.filter(propName, propValue);
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findByProperty(String propName, Serializable propValue, Order... orders) {
		Assert.hasText(propName);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.filter(propName, propValue);
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		return morphiaDao.find(query).asList();
	}

	public List<T> findByNotExistProperty(String field, Order... orders) {
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.field(field).doesNotExist();
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		return morphiaDao.find(query).asList();
	}

	public List<T> findByProperty(String propName, Serializable propValue, int start, int limit, Order order) {
		Assert.hasText(propName);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.filter(propName, propValue);
		if (order != null) {
			query.order(getOrderString(order));
		}
		query.offset(start);
		query.limit(limit);
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findByProperty(String propName, Serializable propValue, int... rowStartIdxAndCount) {
		Assert.hasText(propName);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.filter(propName, propValue);
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
			query.offset(rowStartIdxAndCount[0]);
		}
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 1) {
			query.limit(rowStartIdxAndCount[1]);
		}
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findByProperties(Map<String, Serializable> properties, Order... orders) {
		Assert.notEmpty(properties);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		for (Map.Entry<String, Serializable> entry : properties.entrySet()) {
			query.filter(entry.getKey(), entry.getValue());
		}
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findWithBetween(String propName, Serializable minxValue, Serializable maxValue) {
		Assert.notNull(propName);
		Assert.notNull(minxValue);
		Assert.notNull(maxValue);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.field(propName).greaterThanOrEq(minxValue);
		query.field(propName).lessThanOrEq(maxValue);
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findWithNe(String propName, Serializable value) {
		Assert.notNull(propName);
		Assert.notNull(value);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.field(propName).notEqual(value);
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findWithIn(String propName, Iterable<?> values) {
		Assert.notNull(propName);
		Assert.notNull(values);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.field(propName).hasAnyOf(values);
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findWithHas(String propName, Serializable value) {
		Assert.notNull(propName);
		Assert.notNull(value);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.field(propName).hasThisOne(value);
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findWithNin(String propName, Iterable<Serializable> values) {
		Assert.notNull(propName);
		Assert.notNull(values);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.field(propName).hasNoneOf(values);
		return morphiaDao.find(query).asList();
	}

	@Override
	public List<T> findWithLike(String propName, String regexes) {
		Assert.hasText(propName);
		Assert.notNull(regexes);
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		query.filter(propName, Pattern.compile(regexes));
		return morphiaDao.find(query).asList();
	}

	@Override
	public long count(Map<String, Serializable> properties) {
		properties.put(FLD_DELETED, true);
		return super.count(properties);
	}

	@Override
	public long count(Query<T> query) {
		// query = query.clone();
		query.filter(FLD_DELETED, true);
		return super.count(query);
	}

	@Override
	public long count() {
		Query<T> query = createQuery();
		query.filter(FLD_DELETED, true);
		return super.count(query);
	}

	@Override
	public Page<T> find(Page<T> page) {
		page.addFilter(FLD_DELETED, true);
		return super.find(page);
	}

	public Page<T> findDeletedEntity(Page<T> page) {
		page.addFilter(FLD_DELETED, false);
		return super.find(page);
	}

	@Override
	public void delete(T entity) {
		entity.setDeleted(true);
		save(entity);
	}

	/**
	 * hard delete or soft delete
	 * 
	 * @param entity
	 * @param isHardDelete - true for hard delete; false for soft delete
	 */
	public void delete(T entity, boolean isHardDelete) {
		if (isHardDelete) {
			super.delete(entity);
		} else {
			delete(entity);
		}
	}

	@Override
	public void delete(PK id) {
		T entity = get(id);
		entity.setDeleted(true);
		save(entity);
	}

	public void delete(String propName, Serializable propValue, boolean isHardDelete) {
		if (isHardDelete) {
			super.delete(propName, propValue);
		} else {
			delete(propName, propValue);
		}
	}

	public void delete(Map<String, Serializable> properties, boolean isHardDelete) {
		if (isHardDelete) {
			super.delete(properties);
		} else {
			delete(properties);
		}
	}

	@Override
	public void delete(String propName, Serializable propValue) {
		Assert.hasText(propName);
		UpdateOperations<T> updateOperations = morphiaDao.createUpdateOperations();
		updateOperations.set("deleted", true);

		Query<T> query = createQuery().field(propName).equal(propValue);
		Assert.notNull(query);
		morphiaDao.update(query, updateOperations);
	}

	@Override
	public void delete(Map<String, Serializable> properties) {
		UpdateOperations<T> updateOperations = morphiaDao.createUpdateOperations();
		updateOperations.set("deleted", true);

		Query<T> query = createQuery();
		for (Map.Entry<String, Serializable> entry : properties.entrySet()) {
			query.filter(entry.getKey(), entry.getValue());
		}
		morphiaDao.update(query, updateOperations);
	}
}
