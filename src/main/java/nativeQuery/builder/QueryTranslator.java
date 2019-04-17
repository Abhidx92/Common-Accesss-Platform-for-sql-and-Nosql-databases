/**
 * 
 */
package nativeQuery.builder;

import java.util.ArrayList;
import java.util.List;

import model.AttributeValues;
import model.Results;
import model.TableColumns;
import query.parser.ParseRelation;

/**
 * @author Abhilash
 *
 */
public class QueryTranslator {

	/* Call respective databases */

	public Results callDatabase(String database[], AttributeValues attributeValues, String query) {
		MongoDBQb mongoDBQb = new MongoDBQb();
		CassandraDb cassandraDb = new CassandraDb();
		Neo4jQb neo4jQb = new Neo4jQb();
		MysqlDb mysql = new MysqlDb();
		Results results = new Results();

		for (int i = 0; i < database.length; i++) {
			if (database[i].equalsIgnoreCase("MongoDb")) {
				results.setMongoDbResult(mongoDBQb.buildQuery(attributeValues));

			}
			if (database[i].equalsIgnoreCase("Cassandra")) {
				results.setCassandraResult(cassandraDb.buildQuery(attributeValues, query));

			}
			if (database[i].equalsIgnoreCase("Neo4j")) {
				results.setNeo4jResult(neo4jQb.buildQuery(attributeValues));

			}
			if (database[i].equalsIgnoreCase("MySQL")) {
				try {
					results.setMysqlResult(mysql.readDataBase(query, attributeValues));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		return results;
	}

	public AttributeValues fetchAllDetails(String parsedString, String query) {

		AttributeValues attributeValues = new AttributeValues();
		ParseRelation parseRelation = new ParseRelation();
		String[] clauseSplits = query.split("\\s+");
		if (clauseSplits[0].equalsIgnoreCase("insert")) {
			attributeValues = parseRelation.fetchInsertParams(parsedString, query);
		} else if (clauseSplits[0].equalsIgnoreCase("select")) {
			attributeValues = parseRelation.fetchSelectParams(parsedString, query);
		} else if (clauseSplits[0].equalsIgnoreCase("update")) {
			attributeValues = parseRelation.fetchUpdateParameter(parsedString, query);
		} else if (clauseSplits[0].equalsIgnoreCase("delete")) {
			attributeValues = parseRelation.fetchDeleteParameter(parsedString, query);
		}
		return attributeValues;
	}

}
