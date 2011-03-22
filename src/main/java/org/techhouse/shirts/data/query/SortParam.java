package org.techhouse.shirts.data.query;


public class SortParam {

	private final String field;
	private final boolean isAscending;
	
	private static final String ASC = "asc";
	private static final String DESC = "desc";
	
	
	public SortParam(final String field, final boolean isAscending) {
		this.field = field;
		this.isAscending = isAscending;
	}
	
	public String getField() {
		return field;
	}

	public boolean isAscending() {
		return isAscending;
	}
	
	public String getAscendingKeyword() {
		if(isAscending()){
			return ASC;
		} else {
			return DESC;
		}
	}
}
