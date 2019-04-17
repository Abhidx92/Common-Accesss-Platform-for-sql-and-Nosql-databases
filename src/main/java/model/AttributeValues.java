package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeValues {

	Map columnValues = new HashMap<>();
	Map conditionValues = new HashMap<>();
	String clause;
	String tableName;
	List selectColumnList = new ArrayList<String>();
	public Map getColumnValues() {
		return columnValues;
	}
	public void setColumnValues(Map columnValues) {
		this.columnValues = columnValues;
	}
	public Map getConditionValues() {
		return conditionValues;
	}
	public void setConditionValues(Map conditionValues) {
		this.conditionValues = conditionValues;
	}
	public String getClause() {
		return clause;
	}
	public void setClause(String clause) {
		this.clause = clause;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List getSelectColumnList() {
		return selectColumnList;
	}
	public void setSelectColumnList(List selectColumnList) {
		this.selectColumnList = selectColumnList;
	}
	@Override
	public String toString() {
		return "AttributeValues [columnValues=" + columnValues + ", conditionValues=" + conditionValues + ", clause="
				+ clause + ", tableName=" + tableName + ", selectColumnList=" + selectColumnList + "]";
	}
	
	

	
}
