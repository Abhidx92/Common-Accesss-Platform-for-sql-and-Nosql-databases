<html>
<head>
<link rel="stylesheet" type="text/css" href="design.css">
</head>
<body>

<header>
  <h2>Common Access Platform</h2>
</header>
<p class="solid">
	<form action="abhi">
	<table>
	<tr>
	<td>
	<label for="taste_1">Select database:</label>
	
	</td>
	
	<td>
<select name="database" multiple>
  <option value="MongoDb" >MongoDB</option>
  <option value="MySQL" >MySQL</option>
  <option value="Neo4j">Neo4j</option>
  <option value="Cassandra">Cassandra</option>
</select>
</td>
</tr>
<tr>
<td>
	<label for="taste_1">Enter Query:</label>
	</td>
	<td>
		<input type="text" size="200" name="query">
		<input type="submit">
		</td>
		</tr>
		</table>
	</form>
	</p>
</body>
</html>
