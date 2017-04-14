package com.whotel.common.dao.mongo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.IllegalClassException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.mapping.MappedClass;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.whotel.common.dao.BaseDao;
import com.whotel.common.util.ReflectionUtil;

/**
 * MongoDB Dao based on morphia API
 * 
 * @author
 * 
 * @param <T>
 * @param <PK>
 */
public class MongoDao<T, PK extends Serializable> implements BaseDao<T, PK> {

	// 实体类的缺省主键属性名,子类可重载getIdName方法返回自定义主键属性名
	public static final String DEFAULT_ID_NAME = "id";
	public static final int ASC = 1; // 升序(1)
	public static final int DESC = -1; // 降序(-1)

	// 实体类的类对象
	protected Class<T> entityClass;
	protected MongoCollection<Document> dbCollection;
	protected Field idField;
	protected BasicDAO<T, PK> morphiaDao;
	protected String collectionName;
	protected MongoFactoryBean mongoFactory;

	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class
	 * 例如: public class UserDao extends SpringMongoDao<User, Long>
	 */
	public MongoDao() {
		entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用GenericMongoDao的构造函数
	 * 
	 * 在构造函数中定义对象类型Class. 例如: SpringMongoDao<User, Long> userDao = new
	 * SpringMongoDao<User, Long>(mongoFactory, User.class);
	 */
	public MongoDao(MongoFactoryBean mongoFactory) {
		this.mongoFactory = mongoFactory;
		setMongoFactory(mongoFactory);
	}

	@Autowired
	public void setMongoFactory(MongoFactoryBean mongoFactory) {
		init(mongoFactory);
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * Get entity with specified PK, if PK is null, direct return null.
	 */
	@Override
	public T get(PK id) {
		return id != null ? morphiaDao.get(id) : null;
	}

	/**
	 * Get entity with specified attribute name/value.
	 * 
	 * @param propName
	 * @param propValue
	 * @return the first one found
	 */
	@Override
	public T getByProperty(final String propName, final Serializable propValue) {
		Assert.hasText(propName);
		//Assert.notNull(propValue);
		Query<T> query = createQuery().field(propName).equal(propValue);
		return morphiaDao.findOne(query);
	}

	/**
	 * Get entity with specified attribute name/value and sort result with specified orders.
	 * 
	 * @param propName
	 * @param propValue
	 * @param orders - sorting orders
	 * @return the first one found
	 */
	public T getByProperty(final String propName, final Serializable propValue, final Order... orders) {
		Assert.hasText(propName);
		// Assert.notNull(propValue);
		Query<T> query = createQuery().field(propName).equal(propValue);
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		return morphiaDao.findOne(query);
	}

	/**
	 * Get entity by filters, put all filters in the map.
	 * 
	 * @param properties - put all filters in this map
	 * @return the first one found
	 */
	public T getByProperties(final Map<String, Serializable> properties) {
		Assert.notEmpty(properties);
		Query<T> query = createQuery();
		for (Map.Entry<String, Serializable> entry : properties.entrySet()) {
			query.filter(entry.getKey(), entry.getValue());
		}
		return morphiaDao.findOne(query);
	}

	/**
	 * Get entity by filters, put all filters in the map.
	 * 
	 * @param properties - put all filters in this map
	 * @param orders - sorting orders
	 * @return the first one found
	 */
	public T getByProperties(final Map<String, Serializable> properties, final Order... orders) {
		Assert.notEmpty(properties);
		Query<T> query = createQuery();
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		for (Map.Entry<String, Serializable> entry : properties.entrySet()) {
			query.filter(entry.getKey(), entry.getValue());
		}
		return morphiaDao.findOne(query);
	}

	/**
	 * Use query object as criteria to get entity.
	 * 
	 * @param query - query that implement the search criteria
	 * @return the first one found
	 */
	public T getByQuery(Query<T> query) {
		Assert.notNull(query);
		return morphiaDao.findOne(query);
	}

	/**
	 * Save entity.
	 * 
	 * @param entity
	 */
	@Override
	public void save(T entity) {
		Assert.notNull(entity);
		if (idField != null) {
			PK id = getId(entity);
			if (id == null && idField.getType().equals(String.class)) {
				try {
					IdType annotation = idField.getAnnotation(com.whotel.common.dao.mongo.IdType.class);
					if (annotation != null && annotation.type().equals(IdType.TYPE_SEQUENCE)) {
						// sequence ID
						Datastore ds = morphiaDao.getDatastore();
						String collName = ds.getCollection(entity.getClass()).getName();
						Query<StoredId> q = ds.find(StoredId.class, "_id", collName);
						UpdateOperations<StoredId> uOps = ds.createUpdateOperations(StoredId.class).inc("value");
						StoredId newId = ds.findAndModify(q, uOps);
						if (newId == null) {
							newId = new StoredId(collName);
							ds.save(newId);
						}
						PropertyUtils.setProperty(entity, idField.getName(), newId.getValue().toString());
					} else {
						// random ID
						PropertyUtils.setProperty(entity, idField.getName(), new ObjectId().toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		morphiaDao.save(entity);
	}

	/**
	 * Used to store counters for other entities. (Copy from Morphia's LongIdEntity)
	 */
	@Entity(value = "_SEQUENCES", noClassnameStored = true)
	public static class StoredId {
		final @Id
		String className;
		protected Long value = 1L;

		public StoredId(String name) {
			className = name;
		}

		protected StoredId() {
			className = "";
		}

		public Long getValue() {
			return value;
		}
	}

	/**
	 * Delete entity.
	 * 
	 * @param entity
	 */
	@Override
	public void delete(T entity) {
		Assert.notNull(entity);
		morphiaDao.delete(entity);
	}

	/**
	 * Delete entity by id
	 * 
	 * @param id
	 */
	public void delete(PK id) {
		Assert.notNull(id);
		morphiaDao.deleteById(id);
	}

	/**
	 * Delete entities with specified propName & propValue.
	 * 
	 * @param propName
	 * @param propValue
	 */
	public void delete(final String propName, final Serializable propValue) {
		Assert.hasText(propName);
		Query<T> query = createQuery().field(propName).equal(propValue);
		Assert.notNull(query);
		morphiaDao.deleteByQuery(query);
	}

	/**
	 * Delete entities, according to all property key-value pairs in the map.
	 * 
	 * @param properties - put all property key-value pairs in this map
	 */
	public void delete(final Map<String, Serializable> properties) {
		Assert.notEmpty(properties);
		Query<T> query = createQuery();
		for (Map.Entry<String, Serializable> entry : properties.entrySet()) {
			query.filter(entry.getKey(), entry.getValue());
		}
		morphiaDao.deleteByQuery(query);
	}

	/**
	 * Clear all data under this Collection, dangerous operation.
	 */
	public void clear() {
		morphiaDao.getCollection().drop();
	}

	/**
	 * Retrieve all entities and sorting in orders.
	 * 
	 * @param orders
	 * @return List
	 */
	public List<T> findAll(final Order[] orders) {
		Query<T> query = this.createQuery();
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		return morphiaDao.find(query).asList();

	}

	/**
	 * Retrieve all entities.
	 * 
	 * @return List
	 */
	public List<T> findAll() {
		Query<T> query = createQuery();
		return morphiaDao.find(query).asList();
	}

	/**
	 * Retrieve all entities, add paging feature.
	 * 
	 * @return List
	 */
	@Override
	public List<T> findAll(final int... rowStartIdxAndCount) {
		Query<T> query = createQuery();
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
			query.offset(rowStartIdxAndCount[0]);
		}
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 1) {
			query.limit(rowStartIdxAndCount[1]);
		}
		return morphiaDao.find(query).asList();

	}

	/**
	 * Retrieve all entities and sorting in orders.
	 * 
	 * @param orders
	 * @return Iterable of entity
	 */
	public Iterable<T> fetch(Order... orders) {
		Query<T> query = this.createQuery();
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		return morphiaDao.find(query).fetch();
	}

	/**
	 * Retrieve all entities' key and sorting in orders.
	 * 
	 * @param orders
	 * @return Iterable of keys
	 */
	public Iterable<Key<T>> fetchKeys(Order... orders) {
		Query<T> query = this.createQuery();
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		return morphiaDao.find(query).fetchKeys();
	}

	/**
	 * Do query according to criteria defined in the query object.
	 * 
	 * @param query - carry query criteria
	 * @return QueryResults
	 */
	public QueryResults<T> findByQuery(Query<T> query) {
		Assert.notNull(query);
		return morphiaDao.find(query);
	}

	/**
	 * Do query according to criteria defined in the page object.
	 * 
	 * @param page - carry query criteria
	 * @return Page
	 */
	@Override
	public Page<T> find(Page<T> page) {
		Assert.notNull(page);
		Query<T> query = createQuery();
		// 条件过渡
		/*if (page.isFilterSetted()) {
			for (Map.Entry<String, Object> filter : page.getFilters().entrySet()) {
				query.filter(filter.getKey(), filter.getValue());
			}
		} */
		
		QueryHelpUtil<T> queryHelp = new QueryHelpUtil<T>();
		queryHelp.createQuery(query, page.getFilter());
				
		// 统计数量
		if (page.isAutoCount()) {
			page.setTotalCount(morphiaDao.count(query));
		}
		// 排序
		if (page.isOrderBySetted()) {
			query.order(getOrderString(page.getOrders()));
		}
		// 分页
		if (page.isPageSizeSetted()) {
			query.offset((page.getPageNo() - 1) * page.getPageSize()).limit(page.getPageSize());
		}
		page.setResult(morphiaDao.find(query).asList());
		return page;
	}

	/**
	 * Retrieve entities with property key-value.
	 * 
	 * @param propName
	 * @param propValue
	 * @return List
	 */
	public List<T> findByProperty(final String propName, final Serializable propValue) {
		Assert.hasText(propName);
		// Assert.notNull(propValue);
		Query<T> query = createQuery();
		query.filter(propName, propValue);
		return morphiaDao.find(query).asList();
	}

	/**
	 * Retrieve entities with property key-value, add order feature.
	 * 
	 * @param propName
	 * @param propValue
	 * @param orders - sorting
	 * @return
	 */
	public List<T> findByProperty(final String propName, final Serializable propValue, Order... orders) {
		Assert.hasText(propName);
		// Assert.notNull(propValue);
		Query<T> query = createQuery();
		query.filter(propName, propValue);
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		return morphiaDao.find(query).asList();
	}

	/**
	 * Retrieve entities with property key-value, add paging feature.
	 * 
	 * @param propName
	 * @param propValue
	 * @param rowStartIdxAndCount - start index and retrieve count
	 * @return
	 */
	@Override
	public List<T> findByProperty(final String propName, final Serializable propValue, int... rowStartIdxAndCount) {
		Assert.hasText(propName);
		// Assert.notNull(propValue);
		Query<T> query = createQuery();
		query.filter(propName, propValue);
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
			query.offset(rowStartIdxAndCount[0]);
		}
		if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 1) {
			query.limit(rowStartIdxAndCount[1]);
		}
		return morphiaDao.find(query).asList();
	}

	/**
	 * Do query according to all property key-value pairs in the map, and sorting condition.
	 * 
	 * @param properties - put all filters in this map
	 * @param orders - sorting condition
	 * @return List
	 */
	public List<T> findByProperties(final Map<String, Serializable> properties, Order... orders) {
		Assert.notEmpty(properties);
		Query<T> query = createQuery();
		for (Map.Entry<String, Serializable> entry : properties.entrySet()) {
			query.filter(entry.getKey(), entry.getValue());
		}
		if (orders != null && orders.length > 0)
			query.order(getOrderString(orders));
		return morphiaDao.find(query).asList();
	}

	/**
	 * 'Between' query
	 * 
	 * @param propName
	 * @param minxValue
	 * @param maxValue
	 * @return
	 */
	public List<T> findWithBetween(final String propName, final Serializable minxValue, final Serializable maxValue) {
		Assert.notNull(propName);
		Assert.notNull(minxValue);
		Assert.notNull(maxValue);
		Query<T> query = createQuery();
		query.field(propName).greaterThanOrEq(minxValue);
		query.field(propName).lessThanOrEq(maxValue);
		return morphiaDao.find(query).asList();
	}

	/**
	 * 'Not Equal' query
	 * 
	 * @param propName
	 * @param minxValue
	 * @param maxValue
	 * @return
	 */
	public List<T> findWithNe(final String propName, final Serializable value) {
		Assert.notNull(propName);
		Assert.notNull(value);
		Query<T> query = createQuery();
		query.field(propName).notEqual(value);
		return morphiaDao.find(query).asList();
	}

	/**
	 * 'In' query
	 * 
	 * @param propName
	 * @param values
	 * @return
	 */
	public List<T> findWithIn(final String propName, final Iterable<?> values) {
		Assert.notNull(propName);
		Assert.notNull(values);
		Query<T> query = createQuery();
		query.field(propName).hasAnyOf(values);
		return morphiaDao.find(query).asList();
	}

	/**
	 * 'Has' query
	 * 
	 * @param propName
	 * @param values
	 * @return
	 */
	public List<T> findWithHas(final String propName, final Serializable value) {
		Assert.notNull(propName);
		Assert.notNull(value);
		Query<T> query = createQuery();
		query.field(propName).hasThisOne(value);
		return morphiaDao.find(query).asList();
	}

	/**
	 * 'Not In' query
	 * 
	 * @param propName
	 * @param values
	 * @return
	 */
	public List<T> findWithNin(final String propName, final Iterable<Serializable> values) {
		Assert.notNull(propName);
		Assert.notNull(values);
		Query<T> query = createQuery();
		query.field(propName).hasNoneOf(values);
		return morphiaDao.find(query).asList();
	}

	/**
	 * 'Like' query
	 * 
	 * @param propName
	 * @param values
	 * @return
	 */
	public List<T> findWithLike(final String propName, final String regexes) {
		return findByProperty(propName, Pattern.compile(regexes));
	}

	/**
	 * Return true if propValue is still not in using in collection.
	 * 
	 */
	public boolean isPropertyUnique(String propName, Serializable propValue) {
		return getByProperty(propName, propValue) == null;
	}

	/**
	 * Count the entities that matched the criteria.
	 * 
	 * @param properties - put criteria in this map
	 * @return long
	 */
	public long count(Map<String, Serializable> properties) {
		Assert.notEmpty(properties);
		Query<T> query = createQuery();
		for (Map.Entry<String, Serializable> entry : properties.entrySet()) {
			query.filter(entry.getKey(), entry.getValue());
		}
		return count(query);
	}

	/**
	 * Count the entities that matched the criteria.
	 * 
	 * @param query - query criteria
	 * @return long
	 */
	public long count(Query<T> query) {
		return morphiaDao.count(query);
	}

	/**
	 * Count all entities.
	 * 
	 * @return long
	 */
	@Override
	public long count() {
		return morphiaDao.count();
	}

	/**
	 * Create a query object.
	 * 
	 * @return Query
	 */
	public Query<T> createQuery() {
		return morphiaDao.createQuery();
	}

	/**
	 * If property is still not in using.
	 */
	@Override
	public boolean isPropertyUnique(String propertyName, Serializable newValue, Serializable oldValue) {
		if (newValue == null || newValue.equals(oldValue))
			return true;

		Object object = getByProperty(propertyName, newValue);
		if (object == null)
			return true;
		else
			return false;
	}

	protected void init(MongoFactoryBean sessionFactory) {
		String databaseName = sessionFactory.getDatabase();
		MongoClient mongo = sessionFactory.getMongo();
		Morphia morphia = sessionFactory.getMorphia();
		morphiaDao = new BasicDAO<T, PK>(entityClass, mongo, morphia, databaseName);
		morphiaDao.ensureIndexes();
		// 获取集合名
		Entity annotation = this.entityClass.getAnnotation(Entity.class);
		if (annotation == null) {
			throw new IllegalClassException("the class " + entityClass.getName() + " has no morphia Entity annotation.");
		}
		collectionName = annotation.value();
		if (Mapper.IGNORED_FIELDNAME.equals(collectionName) || collectionName == null) {
			collectionName = entityClass.getSimpleName();
		}
		dbCollection = sessionFactory.getMongo().getDatabase(databaseName).getCollection(collectionName);

		idField = getIdField(morphia, collectionName);
	}

	protected Field getIdField(Morphia morphia, String collectionName) {
		MappedClass mappedClass = morphia.getMapper().getMCMap().get(entityClass.getName());
		return mappedClass != null ? mappedClass.getIdField() : null;
	}

	/**
	 * Get entity's ID
	 */
	@SuppressWarnings("unchecked")
	protected PK getId(T entity) {
		Assert.notNull(entity);
		try {
			return (PK) PropertyUtils.getProperty(entity, idField.getName());
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Query need a string represent sorting criteria, use it.
	 * 
	 * @param orders - Set of orders
	 * @return String
	 */
	public static String getOrderString(Set<Order> orders) {
		StringBuffer sb = new StringBuffer();
		for (Order order : orders) {
			sb.append(",").append(getOrderString(order));
		}
		String orderStr = sb.toString();
		return orderStr.startsWith(",") ? orderStr.substring(1) : orderStr;
	}

	/**
	 * Query need a string represent sorting criteria, use it.
	 * 
	 * @param orders - Array of orders
	 * @return String
	 */
	public static String getOrderString(final Order... orders) {
		StringBuffer sb = new StringBuffer();
		for (Order order : orders) {
			sb.append(",").append(getOrderString(order));
		}
		String orderStr = sb.toString();
		return orderStr.startsWith(",") ? orderStr.substring(1) : orderStr;
	}

	/**
	 * Query need a string represent sorting criteria, use it.
	 * 
	 * @param order - single order
	 * @return String
	 */
	public static String getOrderString(Order order) {
		if (order.isAsc()) {
			return order.getName();
		} else {
			return "-" + order.getName();
		}
	}

}
