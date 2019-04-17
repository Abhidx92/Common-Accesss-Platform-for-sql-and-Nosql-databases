package query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import model.AttributeValues;
import model.TableColumns;

public class ParseRelation {

	public AttributeValues fetchUpdateParameter(String parsedString, String query) {
		AttributeValues attributeValues = new AttributeValues();
		TableColumns tableColumns = new TableColumns();
				tableColumns = translate(parsedString);
		attributeValues.setTableName(tableColumns.getTableName());
		String[] splits = query.split("\\s+");
		if (query.contains("SET")) {
			query = query.replace("SET", "set");
		}
		if (query.contains("WHERE")) {
			query = query.replace("WHERE", "where");
		}
		attributeValues.setClause("update");
		int setIndex = query.indexOf("set");
		int whereIndex = query.indexOf("where");

		String updateAttributes = query.substring(setIndex + 4, whereIndex);
		String condAttributes = query.substring(whereIndex + 6, query.length()-1).trim();
		String conditionArray[] = (condAttributes.split("="));
		attributeValues.getConditionValues().put(conditionArray[0].trim(), conditionArray[1].trim().replace("'", ""));

		String test[] = updateAttributes.split(",");
		for (int i = 0; i < test.length; i++) {
			String keyValue[] = test[i].trim().split("=");
			String value = keyValue[1].replaceAll("'", "").trim();
			attributeValues.getColumnValues().put(keyValue[0].trim(), value);

		}
		
		return attributeValues;

	}

	public AttributeValues fetchDeleteParameter(String parsedString, String query) {
		AttributeValues attributeValues = new AttributeValues();
		if (query.contains("WHERE")) {
			query = query.replace("WHERE", "where");

		}
		if (query.contains("where")) {
			int whereIndex = query.indexOf("where");
			String condAttributes = query.substring(whereIndex + 6, query.length()-1).trim();
			String conditionArray[] = (condAttributes.split("="));
			attributeValues.getConditionValues().put(conditionArray[0].trim(),
					conditionArray[1].replaceAll("'", "").trim());

		}
		TableColumns tablecolumns = translate(parsedString);
		attributeValues.setTableName(tablecolumns.getTableName());
		attributeValues.setClause("delete");

		return attributeValues;

	}

	public AttributeValues fetchInsertParams(String parsedString, String query) {

		AttributeValues attributeValues = new AttributeValues();
		Map columnMap = new HashMap<>();
		// Setting clause
		attributeValues.setClause("insert");

		if (query.contains("VALUES")) {
			query = query.replace("VALUES", "values");
		}

		int valueIndex = query.indexOf("values");
		String condAttributes = query.substring(valueIndex + 8, query.length() - 2).trim();
		condAttributes = condAttributes.replace("'", "");
		TableColumns tablecolumns = translate(parsedString);
		attributeValues.setTableName(tablecolumns.getTableName());

		for (int i = 0; i < tablecolumns.getColumns().size(); i++) {
			columnMap.put(tablecolumns.getColumns().get(i), Arrays.asList(condAttributes.split(",")).get(i));
		}

		attributeValues.setColumnValues(columnMap);

		return attributeValues;

	}

	public AttributeValues fetchSelectParams(String parsedString, String query) {

		AttributeValues attributeValues = new AttributeValues();

		if (query.contains("WHERE")) {
			query = query.replace("WHERE", "where");

		}
		if (query.contains("where")) {
			int whereIndex = query.indexOf("where");
			String condAttributes = query.substring(whereIndex + 6, query.length()).trim();
			String conditionArray[] = (condAttributes.split("="));
			attributeValues.getConditionValues().put(conditionArray[0].trim(),
					conditionArray[1].trim().replace(";", "").replaceAll("'", ""));

		}
		TableColumns tablecolumns = translate(parsedString);
		attributeValues.setTableName(tablecolumns.getTableName());
		attributeValues.setClause("select");
		attributeValues.setSelectColumnList(tablecolumns.getColumns());

		return attributeValues;

	}

	/* Fetch clause, tableName and ColumnName */
	public TableColumns translate(String parsedString) {

		TableColumns tableColumns = new TableColumns();
		List tableList = new ArrayList<String>();
		List columnList = new ArrayList<String>();
		parsedString = parsedString.replaceAll("(table determined:true)", "");
		String[] splitedParsed = parsedString.split("\\s+");

		for (int i = 0; i < splitedParsed.length; i++) {
			/*
			 * System.out.println(splited[i]);
			 * System.out.println("..................................");
			 */

			if (splitedParsed[i].matches("Tables:")) {
				tableColumns.setTableName(splitedParsed[i + 1]);

			}
			if (splitedParsed[i].matches("Columns:")) {
				for (int j =i+1; j<splitedParsed.length; j++) {
					String columns = splitedParsed[j];
					if (columns.contains("()")) {
						columns = columns.replace("()", "");
						columns = columns.replaceAll(tableColumns.getTableName() + ".", "");
						tableColumns.getColumns().add(columns.trim());
					} else if (columns.contains("*")) {
						tableColumns.getColumns().add("*");
						break;
					} 

				}
			}

		}
		// tableColumn.setTableList(tableList);
		// tableColumn.setColumnList(columnList);
		/*
		 * columnList = Lists.reverse(tableColumns.getColumns());
		 * tableColumns.setColumns(columnList);
		 */
		return tableColumns;
	}
}