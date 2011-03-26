package org.techhouse.shirts.data.query;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueryExtras {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryExtras.class);
	
	private QueryExtras() {}

	public static <T> List<T> list(final Class<T> entityClass, final EntityManager entityManager, final SortParam... sortParams) {
		final QueryParam queryParam = new QueryParam();
		queryParam.setSort(Arrays.asList(sortParams));
		return list(entityClass, entityManager, queryParam);
	}
	
	public static <T> List<T> list(final Class<T> entityClass, final EntityManager entityManager, final QueryParam queryParam) {
		final String entityClassName = entityClass.getSimpleName();
		final char rowSymbol = entityClassName.toLowerCase().charAt(0);
		
		final String queryString = "select "+rowSymbol+" from "+entityClassName+" "+rowSymbol + queryParam.toQueryString(rowSymbol);
		final TypedQuery<T> query = entityManager.createQuery(queryString, entityClass);
		
		LOGGER.debug(queryString);
		
		if(queryParam.isLimit()){
			query.setMaxResults(queryParam.getLimit());
		}
		if(queryParam.isStart()){
			query.setFirstResult(queryParam.getStart());
		}
		
		return query.getResultList();
	}
}
