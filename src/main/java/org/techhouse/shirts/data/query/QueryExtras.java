package org.techhouse.shirts.data.query;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueryExtras {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryExtras.class);
	
	private QueryExtras() {}

	public static <T> List<T> list(Class<T> entityClass, SortParam[] sortParams, EntityManager entityManager) {
		final String entityClassName = entityClass.getSimpleName();
		final char rowSymbol = entityClassName.toLowerCase().charAt(0);
		
		String queryString = "select "+rowSymbol+" from "+entityClassName+" "+rowSymbol + getOrderBy(sortParams, rowSymbol);
		LOGGER.info(queryString);
		return entityManager.createQuery(queryString, entityClass).getResultList();
	}
	
	private static String getOrderBy(final SortParam[] sortParams, char rowSymbol) {
		if (sortParams.length > 0) {

			final StringBuilder orderByBuilder = new StringBuilder(" order by");
			for (int i = 0; i < sortParams.length; i++) {
				final SortParam sortParam = sortParams[i];
				if (i > 0) {
					orderByBuilder.append(",");
				}
				orderByBuilder.append(" " + rowSymbol + ".").append(sortParam.getField());
				orderByBuilder.append(" ").append(sortParam.getAscendingKeyword());
			}
			return orderByBuilder.toString();

		} else {
			return "";
		}
	}
}
