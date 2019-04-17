package nativeQuery.builder;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import model.AttributeValues;

public class CassandraDb {

	// Native Query Builder
	public String buildQuery(AttributeValues attributeValues, String query) {

		String output = "";
		// Connect to the cluster and keyspace "devjavasource"
		final Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		final Session session = cluster.connect("test1");

		System.out.println("*********Cluster Information *************");
		System.out.println(" Cluster Name is: " + cluster.getClusterName());
		System.out.println(" Driver Version is: " + cluster.getDriverVersion());
		System.out.println(" Cluster Configuration is: " + cluster.getConfiguration());
		System.out.println(" Cluster Metadata is: " + cluster.getMetadata());
		System.out.println(" Cluster Metrics is: " + cluster.getMetrics());

		/*ResultSet results = session.execute("SELECT * FROM user");
		for (Row row : results) {
			// System.out.format("%s %d %s\n", row.getString("name"),
			// row.getInt("id"), row.getInt("class"));
			System.out.println(row.toString());
		}
*/
		if (attributeValues.getClause().equalsIgnoreCase("select")) {
			output = find(session, attributeValues,query);
		} else if (attributeValues.getClause().equalsIgnoreCase("update")) {
			output = update(session, attributeValues,query);

		} else if (attributeValues.getClause().equalsIgnoreCase("delete")) {
			output = delete(session, attributeValues,query);
		}
		else if (attributeValues.getClause().equalsIgnoreCase("insert")) {
			output = insert(session, attributeValues,query);
		}

		// Close Cluster and Session objects
		System.out.println("\nIs Cluster Closed :" + cluster.isClosed());
		System.out.println("Is Session Closed :" + session.isClosed());
		cluster.close();
		session.close();
		System.out.println("Is Cluster Closed :" + cluster.isClosed());
		System.out.println("Is Session Closed :" + session.isClosed());

		return output;

	}

	// Update Object in Cassandra
	String update(Session session, AttributeValues attributeValues, String query) {
		// Update user data in users table
		System.out.println("\n*********Update User Data Example *************");
		session.execute(query);
		 return "Update operation from Cassandra was performed successfully";
		// tableColumn.getColumnList().get(0));
	}
	// getUsersAllDetails(session);

	// Update Object in Cassandra
	String delete(Session session, AttributeValues attributeValues, String query) {
		session.execute(query);
		 return "Delete operation from Cassandra was performed successfully";
		/*
		// Delete user from users table
		System.out.println("\n*********Delete User Data Example *************");
		if (attributeValues.getConditionValues().isEmpty()) {
			session.execute("delete FROM " + attributeValues.getTableName());

		} else {
			for (Object key : attributeValues.getConditionValues().keySet()) {
				session.execute("delete FROM " + attributeValues.getTableName() + " where " + key.toString() + "="
						+ attributeValues.getConditionValues().get(key) + ");");
			}

		}

		// tableColumn.getColumnList().get(0));

	*/}

	String find(Session session, AttributeValues attributeValues, String query) {
		
		ResultSet results=	 session.execute(query);
		String output = "";
		for (Row row : results) {
			// System.out.format("%s %d %s\n", row.getString("name"),
			// row.getInt("id"), row.getInt("class"));
			output += row.toString();
		} 
		return output;/*
		// find user from users table
		String output = null;
		if (attributeValues.getConditionValues().isEmpty()) {

		} else {

		}
		
		 * ResultSet results = session.execute("SELECT * FROM " +
		 * tableColumn.getTableList().get(0).toString()); for (Row row :
		 * results) { output = row.toString(); }
		 
		return output;
	*/}

	String insert(Session session, AttributeValues attributeValues, String query) {
		
		 session.execute(query);
		 return "Insert operation in Cassandra performed successfully";/*
		// find user from users table
		String output = null;
		String columns = null;
		String values = null;
		if (attributeValues.getColumnValues() != null) {
			for (Object k : attributeValues.getColumnValues().keySet()) {
				columns = columns + k + ",";
				Object value = (attributeValues.getColumnValues().get(k));
				values = values + value + ",";
			}

		}
		ResultSet results = session.execute(
				"INSERT INTO" + attributeValues.getTableName() + "(" + columns + ")" + "Values" + "(" + values + ");");
		for (Row row : results) {
			output = row.toString();
		}
		return output;
	*/}

}
