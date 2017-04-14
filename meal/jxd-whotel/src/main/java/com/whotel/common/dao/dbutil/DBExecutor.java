package com.whotel.common.dao.dbutil;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据操作工具类
 * @author fy
 *
 */
public class DBExecutor {

	private static Log log = LogFactory.getLog(DBExecutor.class);

	private static RowProcessor DEFAULT_PROCESSOR = new BasicRowProcessor(new CoreBeanProcessor());

	private final DataSource dataSource;

	private QueryRunner runner = new QueryRunner();

	/**
	 * for batch
	 */
	private ThreadLocal<Connection> batchConn = new ThreadLocal<Connection>();
	private ThreadLocal<JDBCTransaction> batchTrans = new ThreadLocal<JDBCTransaction>();

	/**
	 * constructor
	 */
	public DBExecutor() {
		this.dataSource = DataSourceFactory.getDataSource();
	}

	/**
	 * constructor
	 * @param provider
	 */
	public DBExecutor(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Get List from SQL
	 *
	 * @param sql	SQL
	 * @param params Parameters
	 * @return List with Bean class in each rows.
	 */
	public List<Object[]> list(String sql, Object... params) {
		ArrayListHandler h = new ArrayListHandler();
		params = convertParams(params);
		//debug info
		log.trace("SQL:[" + sql + "]\nparams:" + ArrayUtils.toString(params));
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return runner.query(conn, sql, h, params);
		} catch (SQLException e) {
			log.error("[Error] Query bean list.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
			throw new DBException("[Error] Query bean list.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
		} finally {
			close(conn, null, null);
		}
	}
	
	/**
	 * Get List from SQL
	 *
	 * @param sql	SQL
	 * @param params Parameters
	 * @return List with Bean class in each rows.
	 */
	public <T> List<T> list(String sql, Class<T> clz, Object... params) {

		// Use the BeanHandler implementation to convert all of the
		// ResultSet row into a specified JavaBean.
		BeanListHandler<T> h = new BeanListHandler<T>(clz, DEFAULT_PROCESSOR);
		params = convertParams(params);

		log.trace("SQL:[" + sql + "]\nparams:" + ArrayUtils.toString(params));
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return runner.query(conn, sql, h, params);
		} catch (SQLException e) {
			log.error("[Error] Query bean list.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
			throw new DBException("[Error] Query bean list.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
		} finally {
			close(conn, null, null);
		}
	}

	/**
	 * list with specified handler
	 * @param sql
	 * @param handler
	 * @param params
	 * @return
	 */
	public <T> List<T> list(String sql, ResultSetHandler<List<T>> handler, Object... params) {
		List<T> ret = null;

		log.trace("SQL:[" + sql + "]\nparams:" + ArrayUtils.toString(params));
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			ret = runner.query(conn, sql, handler, params);
		} catch (SQLException ex) {
			log.error("Query bean.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), ex);
			throw new DBException("Query bean.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), ex);
		} finally {
			close(conn, null, null);
		}
		return ret;
	}

	/**
	 * get List<Number> results.
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> listWithOneColumn(String sql, final int column, Object... params) {
		// Use the BeanHandler implementation to convert all of the
		// ResultSet row into a specified JavaBean.

		params = convertParams(params);

		log.trace("SQL:[" + sql + "]\nparams:" + ArrayUtils.toString(params));
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return runner.query(conn, sql, new ResultSetHandler<List<T>>() {
				final List<T> ret = new ArrayList<T>();

				@SuppressWarnings("unchecked")
				@Override
				public List<T> handle(ResultSet rs) throws SQLException {
					while (rs.next()) {
						ret.add((T) rs.getObject(column));
					}
					return ret;
				}
			}, params);
		} catch (SQLException e) {
			log.error("[Error] Query bean list.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
			throw new DBException("[Error] Query bean list.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
		} finally {
			close(conn, null, null);
		}
	}

	/**
	 * Execute SQL, e.g. update, delete or insert.
	 *
	 * @param sql	the SQL to execute
	 * @param params The parameters to be passed in
	 */
	public void execute(String sql, Object... params) {
		params = convertParams(params);

		log.trace("SQL:[" + sql + "]\nparams:" + ArrayUtils.toString(params));
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			runner.update(conn, sql, params);
		} catch (SQLException e) {
			log.error("Error execute:\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
			throw new DBException("Error execute:\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
		} finally {
			close(conn, null, null);
		}
	}

	/**
	 * insert values, return new inserted ID.
	 * @param sql
	 * @param params
	 * @return
	 */
	public long insertWithAutoKey(String sql, Object... params) {
		long ret = 0;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					Object each = params[i];
					pstmt.setObject(i + 1, each);
				}
			}
			pstmt.execute();
			rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				ret = rs.getLong(1);
			}
		} catch (SQLException e) {
			log.error("[Error] insertWithAutoKey.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
			throw new DBException("[Error]insertWithAutoKey.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
		} finally {
			close(conn, pstmt, rs);
		}
		return ret;
	}

	/**
	 * Get List from SQL
	 *
	 * @param sql	SQL
	 * @param params Parameters
	 * @return List with Bean class in each rows.
	 */
	public <T> T load(String sql, Class<T> clz, Object... params) {
		BeanHandler<T> h = new BeanHandler<T>(clz, DEFAULT_PROCESSOR);
		params = convertParams(params);

		log.trace("SQL:[" + sql + "]\nparams:" + ArrayUtils.toString(params));
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return runner.query(conn, sql, h, params);
		} catch (SQLException e) {
			log.error("[Error] Query bean.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
			throw new DBException("[Error] Query bean.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
		} finally {
			close(conn, null, null);
		}
	}

	/**
	 * Get List from SQL
	 *
	 * @param sql	SQL
	 * @param params Parameters
	 * @return List with Bean class in each rows.
	 */
	public Object load(String sql, Object... params) {
		ArrayListHandler h = new ArrayListHandler();
		params = convertParams(params);

		log.trace("SQL:[" + sql + "]\nparams:" + ArrayUtils.toString(params));
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			ArrayList<?> al = (ArrayList<?>) runner.query(conn, sql, h, params);
			if (al == null || al.size() == 0)
				return null;
			return al.get(0);
		} catch (SQLException e) {
			log.error("[Error] Query bean.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
			throw new DBException("[Error] Query bean.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
		} finally {
			close(conn, null, null);
		}
	}

	/**
	 * load to Object array
	 * @param sql
	 * @param handler
	 * @param params
	 * @return
	 */
	public <T> T load(String sql, ResultSetHandler<T> handler, Object... params) {
		T ret = null;

		log.trace("SQL:[" + sql + "]\nparams:" + ArrayUtils.toString(params));
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			ret = runner.query(conn, sql, handler, params);
		} catch (SQLException ex) {
			log.error("Query bean.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), ex);
			throw new DBException("Query bean.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), ex);
		} finally {
			close(conn, null, null);
		}
		return ret;
	}

	/**
	 * Get count
	 * @param sql
	 * @param params
	 * @return
	 */
	public Integer getCount(String sql, Object... params) {
		return load(sql, new ResultSetHandler<Integer>() {
			@Override
			public Integer handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		}, params);
	}

	/**
	 * get first cell
	 * @param sql
	 * @param clz
	 * @param params
	 * @return
	 */
	public <T> T getFirstCell(String sql, Class<T> clz, Object... params) {
		return load(sql, new ResultSetHandler<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					return (T) rs.getObject(1);
				}
				return null;
			}
		}, params);
	}

	/*--------------------- transaction relative methods --------------------------------*/

	/**
	 * Execute SQL, e.g. update, delete or insert.
	 *
	 * @param sql	the SQL to execute
	 * @param params The parameters to be passed in
	 */
	public void executeBatch(String sql, Object... params) {
		Connection conn = batchConn.get();
		if (conn == null) {
			throw new DBException("Havn't call startTransaction yet.");
		}
		params = convertParams(params);

		log.trace("SQL:[" + sql + "]\nparams:" + ArrayUtils.toString(params));
		try {
			runner.update(conn, sql, params);
		} catch (SQLException e) {
			log.error("[Error] executeBatch.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
			throw new DBException("[Error]executeBatch.\n[SQL] " + sql + "\n[params] " + ArrayUtils.toString(params), e);
		}
	}

	/**
	 * start transaction
	 */
	public void startTransaction() {
		JDBCTransaction trans = batchTrans.get();
		if (trans == null) {
			trans = new JDBCTransaction(dataSource);
			batchTrans.set(trans);
		}
		batchTrans.get().begin();

		Connection bconn = batchConn.get();
		if (bconn == null) {
			bconn = trans.getConnection();
			batchConn.set(bconn);
		}
	}

	/**
	 * commit trans
	 */
	public void commitTransaction() {
		JDBCTransaction trans = batchTrans.get();
		if (trans == null) {
			throw new DBException("Havn't call startTransaction yet.");
		}
		trans.commit();
		try {
			batchConn.get().close();
		} catch (SQLException e) {
			throw new DBException("Connection close error.", e);
		}
		batchConn.set(null);
		batchTrans.set(null);
	}

	/**
	 * rollback trans
	 */
	public void rollbackTransaction() {
		JDBCTransaction trans = batchTrans.get();
		if (trans != null) {
			trans.rollback();
		}
		Connection bconn = batchConn.get();
		try {
			if (bconn != null && !bconn.isClosed()) {
				batchConn.get().close();
			}
		} catch (SQLException e) {
			throw new DBException("Connection close error.", e);
		} finally {
			batchConn.set(null);
			batchTrans.set(null);
		}
	}

	/**
	 * Convert underscore like string to bean name like string.
	 * e.g. "module_id" to "moduleId"
	 * @param s the underscore like string
	 * @return the bean name like string.
	 */
	public static String getCamelName(String s) {
		StringBuffer sb = new StringBuffer();
		boolean prevIsUnderscore = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '_') {
				prevIsUnderscore = true;
				continue;
			} else {
				sb.append(prevIsUnderscore ? Character.toUpperCase(c) : c);
				prevIsUnderscore = false;
			}
		}
		return sb.toString();
	}

	/**
	 * Convert bean name like string to underscore string.
	 * e.g. "moduleId" to "module_id".
	 *@deprecated useless
	 * @param s the Bean name like string.
	 * @return the underscore string.
	 */
	@Deprecated
	public static String getUnderscoreName(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 'A' && c <= 'Z') {
				sb.append("_").append((char) (c + 32));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	//============ private methods ============================

	/**
	 * Convert from java.util.Date to java.sql.Timestamp
	 *
	 * @param params parameters
	 * @return converted parameters
	 */
	private Object[] convertParams(Object[] params) {
		if (params == null)
			return null;
		for (int i = 0; i < params.length; i++) {
			Object o = params[i];
			if (o instanceof java.util.Date && !(o instanceof java.sql.Timestamp)) {
				java.sql.Timestamp newDate = new java.sql.Timestamp(((java.util.Date) o).getTime());
				params[i] = newDate;
			}
		}
		return params;
	}

	//============ private classes ============================

	private static class CoreBeanProcessor extends BeanProcessor {
		public CoreBeanProcessor() {
			super();
		}

		/**
		 * The positions in the returned array represent column numbers.  The
		 * values stored at each position represent the index in the
		 * <code>PropertyDescriptor[]</code> for the bean property that matches
		 * the column name.  If no bean property was found for a column, the
		 * position is set to <code>PROPERTY_NOT_FOUND</code>.
		 *
		 * @param rsmd  The <code>ResultSetMetaData</code> containing column information.
		 * @param props The bean property descriptors.
		 * @return An int[] with column index to property index mappings.
		 *         The 0th element is meaningless because JDBC column indexing starts at 1.
		 * @throws SQLException if a database access error occurs
		 */
		@Override
		protected int[] mapColumnsToProperties(ResultSetMetaData rsmd, PropertyDescriptor[] props) throws SQLException {

			int cols = rsmd.getColumnCount();
			int columnToProperty[] = new int[cols + 1];
			Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);

			for (int col = 1; col <= cols; col++) {
				String columnName = rsmd.getColumnLabel(col); //rsmd.getColumnName(col);
				for (int i = 0; i < props.length; i++) {
					//log.info("***** columnName:[" + columnName + "], props[" + i + "].getName():[" + props[i].getName()
					//		+ "], BeanLikeName:[" + getBeanLikeName(columnName) + "], getColumnLable:[" + rsmd.getColumnLabel(col) + "].O");
					if (columnName.equalsIgnoreCase(props[i].getName()) || getCamelName(columnName).equalsIgnoreCase(props[i].getName())) {
						columnToProperty[col] = i;
						break;
					}
				}
			}

			return columnToProperty;
		}

		/**
		 * Override processColumn method, add Boolean propType determination.
		 *
		 * @see org.apache.commons.dbutils.BeanProcessor#processColumn(java.sql.ResultSet, int, java.lang.Class)
		 */
		@Override
		protected Object processColumn(ResultSet rs, int index, Class<?> propType) throws SQLException {
			if (propType.equals(String.class)) {
				return rs.getString(index);

			} else if (propType.equals(Integer.TYPE) || propType.equals(Integer.class)) {
				return new Integer(rs.getInt(index));

			} else if (propType.equals(Boolean.TYPE) || propType.equals(Boolean.class)) {
				Object bVal = rs.getObject(index);
				if (bVal instanceof String) {
					log.info("String value of Boolean type: " + bVal);
					if ("T".equalsIgnoreCase((String) bVal) || "Y".equalsIgnoreCase((String) bVal) || "1".equalsIgnoreCase((String) bVal)
							|| "True".equalsIgnoreCase((String) bVal) || "Yes".equalsIgnoreCase((String) bVal)) {
						return new Boolean(true);
					} else {
						return new Boolean(false);
					}
				} else {
					return new Boolean(rs.getBoolean(index));
				}

			} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
				return new Long(rs.getLong(index));

			} else if (propType.equals(Double.TYPE) || propType.equals(Double.class)) {
				return new Double(rs.getDouble(index));

			} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
				return new Float(rs.getFloat(index));

			} else if (propType.equals(BigDecimal.class)) {
				return rs.getBigDecimal(index);
			} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
				return new Short(rs.getShort(index));

			} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
				return new Byte(rs.getByte(index));

			} else if (propType.equals(Timestamp.class) || propType.equals(Date.class) || propType.equals(java.sql.Date.class)) {
				return rs.getTimestamp(index);

			} else {
				log.info("Get unkown type: name=" + propType.getName() + ", simpleName=" + propType.getSimpleName() + ", isArray="
						+ propType.isArray());
				Object o = rs.getObject(index);
				if (o != null) {
					log.info("[Undown] getObject: " + o.getClass().getName() + ", " + o.getClass().getCanonicalName() + ", "
							+ o.getClass().getPackage() + ", " + o.getClass().getSimpleName() + ", " + o.getClass().getSuperclass());
				}
				return o;
			}
		}
	}

	private void close(Connection conn, Statement stm, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			
			if(stm != null) {
				stm.close();
				stm = null;
			}
			
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
