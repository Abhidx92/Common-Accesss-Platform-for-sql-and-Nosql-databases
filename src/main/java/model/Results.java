package model;

public class Results {
	String mysqlResult;
	String mongoDbResult;
	String cassandraResult;
	String neo4jResult;

	public String getMysqlResult() {
		return mysqlResult;
	}

	public void setMysqlResult(String mysqlResult) {
		this.mysqlResult = mysqlResult;
	}

	public String getMongoDbResult() {
		return mongoDbResult;
	}

	public void setMongoDbResult(String mongoDbResult) {
		this.mongoDbResult = mongoDbResult;
	}

	public String getCassandraResult() {
		return cassandraResult;
	}

	public void setCassandraResult(String cassandraResult) {
		this.cassandraResult = cassandraResult;
	}

	public String getNeo4jResult() {
		return neo4jResult;
	}

	public void setNeo4jResult(String neo4jResult) {
		this.neo4jResult = neo4jResult;
	}

}
