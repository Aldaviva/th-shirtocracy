package org.techhouse.shirts.data.query;

import java.util.ArrayList;
import java.util.List;

public class QueryParam {

	private List<SortParam> sort;
	private Integer start;
	private Integer limit;

	public QueryParam() {
		this.sort = new ArrayList<SortParam>();
	}
	
	public QueryParam(SortParam sort, Integer start, Integer limit) {
		this();
		this.sort.add(sort);
		this.start = start;
		this.limit = limit;
	}

	public QueryParam(List<SortParam> sort, Integer start, Integer limit) {
		this();
		this.sort.addAll(sort);
		this.start = start;
		this.limit = limit;
	}

	public List<SortParam> getSort() {
		return sort;
	}

	public QueryParam setSort(List<SortParam> sort) {
		this.sort = sort;
		return this;
	}

	public QueryParam addSort(SortParam sort) {
		this.sort.add(sort);
		return this;
	}
	
	public boolean isSort(){
		return getSort() != null && !getSort().isEmpty();
	}

	public Integer getStart() {
		return start;
	}

	public QueryParam setStart(Integer start) {
		this.start = start;
		return this;
	}
	
	public boolean isStart(){
		return getStart() != null;
	}

	public Integer getLimit() {
		return limit;
	}

	public QueryParam setLimit(Integer limit) {
		this.limit = limit;
		return this;
	}
	
	public boolean isLimit(){
		return getLimit() != null;
	}

	public String toQueryString(final char rowSymbol) {
		final StringBuilder builder = new StringBuilder();

		if (isSort()) {
			final StringBuilder orderByBuilder = new StringBuilder(" order by");
			for (int i = 0; i < getSort().size(); i++) {
				final SortParam sortParam = getSort().get(i);
				if (i > 0) {
					orderByBuilder.append(",");
				}
				orderByBuilder.append(" " + rowSymbol + ".").append(sortParam.getField());
				orderByBuilder.append(" ").append(sortParam.getAscendingKeyword());
			}
			return orderByBuilder.toString();
		}
		
		return builder.toString();
		
	}
}
