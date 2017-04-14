package com.whotel.common.dao.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import com.whotel.common.dto.FilterDto;
import com.whotel.common.enums.FilterRelation;

/**
 * mongo query helper
 * @author fengyong
 *
 */
public class QueryHelpUtil<T> {

	//private static Log log = LogFactory.getFactory().getInstance(SQLHelpUtil.class);

	public QueryHelpUtil() {}
	
	/**
	 * create Query by filter
	 * @param filter
	 * @return query
	 */
	public void createQuery(Query<T> query, List<FilterDto> filterDtos) {
		if(filterDtos != null) {
			for (FilterDto filterDto : filterDtos) {
				List<FilterDto> relationFilters = filterDto.getFilterDtos();
				if (relationFilters != null && relationFilters.size() > 0) {
					
					List<Criteria> orCriterias = new ArrayList<Criteria>();
					List<Criteria> andCriterias = new ArrayList<Criteria>();
					
					int size = relationFilters.size();
					for(int i=0; i<size; i++) {
						FilterDto relationFilter = relationFilters.get(i);
						Criteria criteria = constructFieldCriterias(query, relationFilter);
						if(criteria != null) {
							if (relationFilter.getFilterRelation().equals(FilterRelation.OR)) {
								orCriterias.add(criteria);
							} else {
								andCriterias.add(criteria);
							}
						}
					}
					
					if(orCriterias.size() > 0) {
						query.or(orCriterias.toArray(new Criteria[orCriterias.size()]));
					}
					if(andCriterias.size() > 0){
						query.and(andCriterias.toArray(new Criteria[andCriterias.size()]));
					}
				} else {
					constructField(query, filterDto);
				}
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void constructField(Query<T> query, FilterDto filterDto) {
		String fieldName = filterDto.getFiledName();
		Object fieldValue = filterDto.getFiledValue();
		switch (filterDto.getModel()) {
		case EQ:
			query.field(fieldName).equal(fieldValue);
			break;
		case NE:
			query.field(fieldName).notEqual(fieldValue);
			break;
		case LT:
			query.field(fieldName).lessThan(fieldValue);
			break;
		case GT:
			query.field(fieldName).greaterThan(fieldValue);
			break;
		case LE:
			query.field(fieldName).lessThanOrEq(fieldValue);
			break;
		case GE:
			query.field(fieldName).greaterThanOrEq(fieldValue);
			break;
		case LIKE:
			query.field(fieldName).equal(Pattern.compile("^.*" + fieldValue.toString().trim().replaceAll("\\*", "") + ".*$",
									Pattern.CASE_INSENSITIVE));
			break;
		case IS:
			query.field(fieldName).exists();
			break;
		case NOT:
			query.field(fieldName).doesNotExist();
			break;
		case IN:
			query.field(fieldName).in((Iterable<?>) fieldValue);
			break;
		}
	}

	@SuppressWarnings("incomplete-switch")
	private Criteria constructFieldCriterias(Query<T> query, FilterDto filterDto) {
		Criteria criteria = null;
		String fieldName = filterDto.getFiledName();
		Object fieldValue = filterDto.getFiledValue();
		switch (filterDto.getModel()) {
		case EQ:
			criteria = query.criteria(fieldName).equal(fieldValue);
			break;
		case NE:
			criteria = query.criteria(fieldName).notEqual(fieldValue);
			break;
		case LT:
			criteria = query.criteria(fieldName).lessThan(fieldValue);
			break;
		case GT:
			criteria = query.criteria(fieldName).greaterThan(fieldValue);
			break;
		case LE:
			criteria = query.criteria(fieldName).lessThanOrEq(fieldValue);
			break;
		case GE:
			criteria = query.criteria(fieldName).greaterThanOrEq(fieldValue);
			break;
		case LIKE:
			criteria = query.criteria(fieldName).equal(Pattern.compile("^.*" + fieldValue.toString().trim().replaceAll("\\*", "") + ".*$",
									Pattern.CASE_INSENSITIVE));
			break;
		case IS:
			criteria = query.criteria(fieldName).exists();
			break;
		case NOT:
			criteria = query.criteria(fieldName).doesNotExist();
			break;
		}
		return criteria;
	}
}
