package nativeQuery.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.types.Node;

import model.AttributeValues;

public class Neo4jQb {

	/* Native Query builder */
	String buildQuery(AttributeValues attributeValues) {
		String output = null;
		List<String> list = new ArrayList<>();
		try {
			Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "Dxrules151092"));
			System.out.println("Connection successfull");
			Session session = driver.session();

			if (attributeValues.getClause().equalsIgnoreCase("select")) {
				output = find(session, attributeValues);
			} else if (attributeValues.getClause().equalsIgnoreCase("update")) {
				output = update(session, attributeValues);

			} else if (attributeValues.getClause().equalsIgnoreCase("delete")) {
				output = delete(session, attributeValues);

			} else if (attributeValues.getClause().equalsIgnoreCase("insert")) {
				output = insert(session, attributeValues);
			}

			driver.close();

		} catch (Exception e) {

			System.out.println(e);
		}
		System.out.println("server is ready");

		return output;

	}

	/* Find Object in Cassandra */
	public String find(Session session, AttributeValues attributeValues) {
		List keys = new ArrayList<>();
		String columnValues = "";
		String output = "";
		String neo4jColumns = "";
		// Transaction tx = session.beginTransaction();
		if (attributeValues.getSelectColumnList().contains("*")) {
			StatementResult rs = session.run("MATCH (user:" + attributeValues.getTableName() + ") RETURN keys(user)");
			Record rec = rs.next();
			keys = rec.get(0).asList();
			for (Object values : keys) {
				neo4jColumns += "user." + values.toString() + ",";

			}
			neo4jColumns = neo4jColumns.substring(0, neo4jColumns.length() - 1);
		 rs = session.run("MATCH (user:" + attributeValues.getTableName() + ") RETURN " + neo4jColumns);
			Record record = rs.next();
			for (int i = 0; i < keys.size(); i++) {

				output += record.get(i).asString() + ",";
				
			}
			output = output.substring(0,output.length()-1 );
			session.close();
			return output;
		} else {
			for (Object values : attributeValues.getSelectColumnList()) {
				columnValues += "user." + values.toString() + ",";

			}
			columnValues = columnValues.substring(0, columnValues.length() - 1);
			StatementResult rs = session
					.run("MATCH (user:" + attributeValues.getTableName() + ") RETURN " + columnValues);
			Record record = rs.next();
			for (int i = 0; i < attributeValues.getSelectColumnList().size(); i++) {

				output += record.get(i).asString() + ",";
				
			}
			output = output.substring(0,output.length()-1 );
			session.close();
			return output;
		}

		// return rs.single().get(0).asString();

	}

	/* Find all Objects */
	public List<String> findAllNodes(Session session, AttributeValues attributeValues) {

		List<String> names = new ArrayList<>();
		Transaction tx = session.beginTransaction();
		Set<String> nodes = new HashSet<>();
		StatementResult rs = tx.run("MATCH (n) RETURN n;");
		while (rs.hasNext()) {
			names.add(rs.next().get(0).asString());
		}
		return names;

	}

	/* Find Object in Cassandra */
	public String insert(Session session, AttributeValues attributeValues) {
		String columnValues = "";
		for (Object keys : attributeValues.getColumnValues().keySet()) {

			columnValues += keys.toString() + ":'" + attributeValues.getColumnValues().get(keys) + "',";

		}
		columnValues = columnValues.substring(0, columnValues.length() - 1);
		// Transaction tx = session.beginTransaction();

		StatementResult rs = session.run("CREATE (n:" + attributeValues.getTableName() + " { " + columnValues + "})");
		session.close();
		return "Inserted sucessfully in neo4j ";

		// return rs.single().get(0).asString();

	}

	/* Find Object in Cassandra */
	public String update(Session session, AttributeValues attributeValues) {
		String columnValues = "";
		String condition = "";
		for (Object keys : attributeValues.getColumnValues().keySet()) {

			columnValues += "n." + keys.toString() + "='" + attributeValues.getColumnValues().get(keys) + "',";

		}
		columnValues = columnValues.substring(0, columnValues.length() - 1);
		for (Object key : attributeValues.getConditionValues().keySet()) {
			condition = key.toString() + ":'" + attributeValues.getConditionValues().get(key)+"'";
		}
		// Transaction tx = session.beginTransaction();

		StatementResult rs = session.run("MATCH (n {" + condition + "}) SET " + columnValues);
		session.close();
		return "Successfully updated value in Neo4j";

		// return rs.single().get(0).asString();

	}

	/* Find Object in Cassandra */
	public String delete(Session session, AttributeValues attributeValues) {
		String condition = "";
		for (Object key : attributeValues.getConditionValues().keySet()) {
			condition = key.toString() + ":'" + attributeValues.getConditionValues().get(key) + "'";
		}

		if (attributeValues.getConditionValues().isEmpty()) {
			StatementResult rs = session.run("MATCH (n:" + attributeValues.getTableName() + ") DELETE n");
			session.close();
			return "Deleted node successfully";
		} else {
			StatementResult rs = session.run("MATCH (n{ " + condition + " }) DELETE n");
			session.close();
			return "Deleted node successfully";
		}
		 

		// return rs.single().get(0).asString();

	}
}
