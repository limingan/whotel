package com.whotel.common.dao.dbutil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.whotel.common.dto.FilterDto;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.enums.FilterRelation;

/**
 * SQL helper
 * @author fengyong
 *
 */
public class SQLHelpUtil {

	//private static Log log = LogFactory.getFactory().getInstance(SQLHelpUtil.class);

	private static List<String> filedNames;

	private static List<Object> filedValues;

	private SQLHelpUtil() {}
	
	/**
	 * create SQL by filter
	 * @param filter
	 * @return SQL
	 */
	public static String createSQL(List<FilterDto> filterDtos) {
		StringBuffer sb = new StringBuffer();
		filedNames = new ArrayList<String>();
		filedValues = new ArrayList<Object>();
		int index = 0;
		for (FilterDto filterDto : filterDtos) {
			List<FilterDto> relationFilters = filterDto.getFilterDtos();
			if (relationFilters != null && relationFilters.size() > 0) {
				sb.append(" (");
				constructField(sb, filterDto);
				int size = relationFilters.size();
				for(int i=0; i<size; i++) {
					FilterDto relationFilter = relationFilters.get(i);
					if (relationFilter.getFilterRelation().equals(FilterRelation.OR)) {
						sb.append(" or ");
					} else {
						sb.append(" and ");
					}
					constructField(sb, relationFilter);
				}
				sb.append(") ");
			} else {
				constructField(sb, filterDto);
			}
			if (sb.length() > 0 && index < filterDtos.size() - 1) {
				if (filterDto.getFilterRelation().equals(FilterRelation.OR)) {
					sb.append(" or ");
				} else {
					sb.append(" and ");
				}
			}
			index++;
		}
		return sb.toString();
	}

	@SuppressWarnings({ "incomplete-switch" })
	private static void constructField(StringBuffer sb, FilterDto filterDto) {
		String fieldName = filterDto.getFiledName();
		Object fieldValue = filterDto.getFiledValue();
		switch (filterDto.getModel()) {
		case EQ:
			sb.append(fieldName).append(" = ? ");
			break;
		case NE:
			sb.append(fieldName).append(" <> ? ");
			break;
		case LT:
			sb.append(fieldName).append(" < ? ");
			break;
		case GT:
			sb.append(fieldName).append(" > ? ");
			break;
		case LE:
			sb.append(fieldName).append(" <= ? ");
			break;
		case GE:
			sb.append(fieldName).append(" >= ? ");
			break;
		case LIKE:
			sb.append(fieldName).append(" like ? ");
			break;
		case IS:
			sb.append(fieldName).append(" is ? ");
			break;
		case NOT:
			sb.append(fieldName).append(" is not ? ");
			break;
		case BETWEEN:
			sb.append("(").append(fieldName).append(" between ? and ? ) ");
			break;
		}

		filedNames.add(fieldName);

		if (filterDto.getModel().equals(FilterModel.LIKE)) {
			filedValues.add("%" + fieldValue + "%");
		} else if (filterDto.getModel().equals(FilterModel.BETWEEN)) {

			if (fieldValue instanceof Collection) {
				filedValues.addAll((Collection<?>) fieldValue);

			} else if (fieldValue.getClass().isArray()) {
				for (int i = 0; i < Array.getLength(fieldValue); i++) {
					Object val = Array.get(fieldValue, i);
					filedValues.add(val);
				}
			}

		} else {
			filedValues.add(fieldValue);
		}
	}

	/**
	 * create contain collection sql by filter
	 * @param filter
	 * @return SQL
	 */
	public static String createCollectionSQL(List<FilterDto> filters) {
		StringBuffer sb = new StringBuffer();
		filedNames = new ArrayList<String>();
		filedValues = new ArrayList<Object>();
		int index = 0;
		for (FilterDto filterDto : filters) {
			List<FilterDto> relationFilters = filterDto.getFilterDtos();
			if (relationFilters != null && relationFilters.size() > 0) {
				sb.append(" (");
				constructCollectionField(sb, filterDto);
				int size = relationFilters.size();
				for(int i=0; i<size; i++) {
					FilterDto relationFilter = relationFilters.get(i);
					if (relationFilter.getFilterRelation().equals(FilterRelation.OR)) {
						sb.append(" or ");
					} else {
						sb.append(" and ");
					}
					constructCollectionField(sb, relationFilter);
				}
				sb.append(") ");
			} else {
				constructCollectionField(sb, filterDto);
			}
			if (sb.length() > 0 && index < filters.size() - 1) {
				if (filterDto.getFilterRelation().equals(FilterRelation.OR)) {
					sb.append(" or ");
				} else {
					sb.append(" and ");
				}
			}
			index++;
		}
		return sb.toString();
	}

	private static void constructCollectionField(StringBuffer sb, FilterDto filterDto) {
		String fieldName = filterDto.getFiledName();
		Object fieldValue = filterDto.getFiledValue();
		switch (filterDto.getModel()) {
		case EQ:
			sb.append(fieldName).append(" = :").append(fieldName.trim());
			break;
		case NE:
			sb.append(fieldName).append(" <> :").append(fieldName.trim());
			break;
		case LT:
			sb.append(fieldName).append(" < :").append(fieldName.trim());
			break;
		case GT:
			sb.append(fieldName).append(" > :").append(fieldName.trim());
			break;
		case LE:
			sb.append(fieldName).append(" <= :").append(fieldName.trim());
			break;
		case GE:
			sb.append(fieldName).append(" >= :").append(fieldName.trim());
			break;
		case IN:
			sb.append(fieldName).append(" in (:").append(fieldName.trim() + ")");
			break;
		case LIKE:
			sb.append(fieldName).append(" like :").append(fieldName.trim());
			break;
		case IS:
			sb.append(fieldName).append(" is :").append(fieldName.trim());
			break;
		case NOT:
			sb.append(fieldName).append(" is not :").append(fieldName.trim());
			break;
		case BETWEEN:
			sb.append("(").append(fieldName).append(" between :").append(fieldName.trim()).append(" and :").append(fieldName.trim())
					.append(")");
			break;
		}

		filedNames.add(fieldName);

		if (filterDto.getModel().equals(FilterModel.LIKE)) {
			filedValues.add("%" + fieldValue + "%");

		} else if (filterDto.getModel().equals(FilterModel.IN) || filterDto.getModel().equals(FilterModel.BETWEEN)) {

			if (fieldValue instanceof Collection) {
				filedValues.addAll((Collection<?>) fieldValue);

			} else if (fieldValue.getClass().isArray()) {
				for (int i = 0; i < Array.getLength(fieldValue); i++) {
					Object val = Array.get(fieldValue, i);
					filedValues.add(val);
				}
			}

		} else {
			filedValues.add(fieldValue);
		}
	}

	public static List<String> getFiledNames() {
		return filedNames;
	}

	public static List<Object> getFiledValues() {
		return filedValues;
	}
}
