<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}

th, td {
	padding: 5px;
	text-align: left;
}
</style>
</head>
<body>
	<table style="width: 100%">
		<tr>
			<th>Databases</th>
			<th>Output</th>
		</tr>
		<tr>
			<td>MongoDb</td>
			<td>${alien.mongoDbResult}</td>

		</tr>
		<tr>
			<td>Cassandra</td>
			<td>${alien.cassandraResult}</td>

		</tr>
		<tr>
			<td>Neo4j</td>
			<td>${alien.neo4jResult}</td>

		</tr>
		<tr>
			<td>MySQL</td>
			<td>${alien.mysqlResult}</td>

		</tr>
	</table>

</body>
</html>