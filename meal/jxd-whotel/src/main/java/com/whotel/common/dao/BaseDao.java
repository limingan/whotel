package com.whotel.common.dao;

import java.io.Serializable;
import java.util.List;

import com.whotel.common.dao.mongo.Page;

public interface BaseDao<T, PK extends Serializable> {

	/**
	 * Get a persistent entity.
	 * 
	 * @param Id
	 *            entity Primary Key
	 * @return entity
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	T get(PK Id);

	public T getByProperty(String propertyName, Serializable value);

	/**
	 * Perform an initial save of a previously unsaved entity. This operation
	 * must be performed within the a database transaction context for the
	 * entity's data to be permanently saved to the persistence store, i.e.,
	 * database.
	 * 
	 * @param entity
	 *            entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	void save(T entity);

	/**
	 * Delete a persistent entity. This operation must be performed within the a
	 * database transaction context for the entity's data to be permanently
	 * deleted from the persistence store, i.e., database.
	 * 
	 * @param entity
	 *            entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	void delete(T entity);

	/**
	 * Find all entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * @return List<T> all entities
	 */
	List<T> findAll(int... rowStartIdxAndCount);

	/**
	 * Find all Album entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the entity property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
	 *            row index in the query result-set to begin collecting the
	 *            results. rowStartIdxAndCount[1] specifies the the maximum
	 *            count of results to return.
	 * 
	 * @return List<T> found by query
	 */
	List<T> findByProperty(String propertyName, Serializable value, int... rowStartIdxAndCount);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<T> find(Page<T> page);

	long count();

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	boolean isPropertyUnique(final String propertyName, final Serializable newValue, final Serializable oldValue);

}
