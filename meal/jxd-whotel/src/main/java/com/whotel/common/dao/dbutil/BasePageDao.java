package com.whotel.common.dao.dbutil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.whotel.common.dto.FilterDto;
import com.whotel.common.enums.FilterModel;

public class BasePageDao {
	/**
	 * DBExeutor
	 */
	protected DBExecutor dbe = new DBExecutor();
	
	private static final long MAX_ROW = Long.MAX_VALUE;

	protected SQLWithParams initSQLWithParams(DataPageUtil<?> dataPageUtil, List<Object> filedValues, String countSql, String sql) {
		if (filedValues == null) {
			filedValues = new ArrayList<Object>();
		}
		SQLWithParams swp = null;
		if (dataPageUtil != null) {
			List<FilterDto> filter = dataPageUtil.getFilter();
			if (filter != null) {
				String filterSql = SQLHelpUtil.createSQL(filter);
				countSql += " and " + filterSql;
				sql += " and " + filterSql;
				filedValues.addAll(SQLHelpUtil.getFiledValues());
			}
			Order[] os = dataPageUtil.getOrders();
			String orderType = "";
			if (os != null && os.length > 0) {
				orderType = " order by ";
				List<String> orderStr = new ArrayList<String>();
				for (Order o : os) {
					orderStr.add(o.getFiledName() + (o.isDesc() ? " desc " : " "));
				}
				orderType += StringUtils.join(orderStr, ",");
			}
			sql += orderType;
			if (filedValues != null && filedValues.size() > 0) {
				dataPageUtil.setRowCount(dbe.getCount(countSql, filedValues.toArray()));
				swp = getPaginationSQLWithParams(sql, filedValues.toArray(),
						dataPageUtil.getStart() + dataPageUtil.getRowNumOffset(), dataPageUtil.getLimit());
			} else {
				dataPageUtil.setRowCount(dbe.getCount(countSql));
				swp = getPaginationSQLWithParams(sql, null,
						dataPageUtil.getStart() + dataPageUtil.getRowNumOffset(), dataPageUtil.getLimit());
			}
		}
		return swp;
	}

	protected SQLWithParams initSQLWithCollectionParams(DataPageUtil<?> dataPageUtil, List<Object> filedValues, String countSql, String sql) {
		if (filedValues == null) {
			filedValues = new ArrayList<Object>();
		}
		SQLWithParams swp = null;
		if (dataPageUtil != null) {
			List<FilterDto> filter = dataPageUtil.getFilter();
			if (filter != null) {
				String filterSql = SQLHelpUtil.createCollectionSQL(filter);
				for (FilterDto f : filter) {
					if (f.getModel().equals(FilterModel.IN)) {

						//get params num
						int paramSize = 0;

						Object fieldValue = f.getFiledValue();
						if (fieldValue instanceof Collection) {
							paramSize = ((Collection<?>) fieldValue).size();
						} else if (fieldValue.getClass().isArray()) {
							paramSize = Array.getLength(fieldValue);
						}

						List<String> temps = new ArrayList<String>();
						for (int i = 0; i < paramSize; i++) {
							temps.add("?");
						}
						filterSql = filterSql.replace(":" + f.getFiledName(), StringUtils.join(temps, ","));
					} else {
						filterSql = filterSql.replace(":" + f.getFiledName(), "?");
					}
				}
				countSql += " and " + filterSql;
				sql += " and " + filterSql;
				filedValues.addAll(SQLHelpUtil.getFiledValues());
			}
			Order[] os = dataPageUtil.getOrders();
			String orderType = "";
			if (os != null && os.length > 0) {
				orderType = " order by ";
				List<String> orderStr = new ArrayList<String>();
				for (Order o : os) {
					orderStr.add(o.getFiledName() + (o.isDesc() ? " desc " : " "));
				}
				orderType += StringUtils.join(orderStr, ",");
			}
			sql += orderType;
			if (filedValues != null && filedValues.size() > 0) {
				dataPageUtil.setRowCount(dbe.getCount(countSql, filedValues.toArray()));
				swp = getPaginationSQLWithParams(sql, filedValues.toArray(),
						dataPageUtil.getStart() + dataPageUtil.getRowNumOffset(), dataPageUtil.getLimit());
			} else {
				dataPageUtil.setRowCount(dbe.getCount(countSql));
				swp = getPaginationSQLWithParams(sql, null,
						dataPageUtil.getStart() + dataPageUtil.getRowNumOffset(), dataPageUtil.getLimit());
			}
		}
		return swp;
	}
	
	public SQLWithParams getPaginationSQLWithParams(String sql, Object[] params, Number fromRow, Number toRow) {
		if (fromRow == null && toRow == null) {
			return new SQLWithParams(sql, params);
		}

		//start parse
		String newSql = sql + " limit ?,?";
		Object[] newParams = params;

		long from = fromRow == null ? 0 : fromRow.longValue();
		long to = toRow == null ? MAX_ROW : toRow.longValue();
		newParams = ArrayUtils.add(newParams, from);
		newParams = ArrayUtils.add(newParams, to);

		SQLWithParams ret = new SQLWithParams(newSql, newParams);
		return ret;
	}

	public String getCountSQL(String sql) {
		return "select count(*) from (" + sql + ") $sq";
	}
}
