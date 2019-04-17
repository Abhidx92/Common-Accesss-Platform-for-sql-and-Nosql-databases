package model;

import java.util.ArrayList;
import java.util.List;

public class TableColumns {
	String tableName;
	List columns = new ArrayList<>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List getColumns() {
		return columns;
	}

	public void setColumns(List columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "TableColumns [tableName=" + tableName + ", columns=" + columns + "]";
	}

}
