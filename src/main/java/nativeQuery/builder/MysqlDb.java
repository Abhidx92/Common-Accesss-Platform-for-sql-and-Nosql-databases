package nativeQuery.builder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.AttributeValues;

public class MysqlDb {

 

public String readDataBase(String query,AttributeValues attributeValues) throws Exception {
	MysqlDb mysqlDb = new MysqlDb();
	List columnValues = new ArrayList<>();
	Connection connect = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	String val = "";
    try {
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Setup the connection with the DB
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost/research?useSSL=false","root","research");

        // Statements allow to issue SQL queries to the database
        statement = connect.createStatement();
        if(attributeValues.getClause().equalsIgnoreCase("select")){
        	ResultSet rs = statement.executeQuery(query);
        	rs.next();
        	if (attributeValues.getSelectColumnList().contains("*")) {
        		for  (int i = 1; i<= rs.getMetaData().getColumnCount(); i++){
        		
        			val += rs.getString(rs.getMetaData().getColumnName(i)) + ", ";
        			//columnValues.add(resultSet.getMetaData().getColumnName(i));
        	    }
    			
        		
        		
    		} else {
    			rs.next();
    			for  (int i = 0; i< attributeValues.getSelectColumnList().size()-1; i++){
    				String columnName = attributeValues.getSelectColumnList().get(i).toString();
        			String colValue = rs.getString(columnName);
        			val +=  colValue + ", ";
        			//columnValues.add(resultSet.getMetaData().getColumnName(i));
        	    }
    			
    		        
        }
        	
        }else{
        	
        	int i = statement
                    .executeUpdate(query);
        	val = "Operation Performed Successfully  ";
        }
        	
        
        // Result set get the result of the SQL query
        
       
        
       /* if (!resultSet.isBeforeFirst() ) {    
            System.out.println("No data"); 
        }
        //mysqlDb.writeResultSet(resultSet);
        Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
        String values ="";
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            values +=  resultSet.getNString(i);
        }
*/
//        // PreparedStatements can use variables and are more efficient
//        preparedStatement = connect
//                .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
//        // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
//        // Parameters start with 1
//        preparedStatement.setString(1, "Test");
//        preparedStatement.setString(2, "TestEmail");
//        preparedStatement.setString(3, "TestWebpage");
//        preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
//        preparedStatement.setString(5, "TestSummary");
//        preparedStatement.setString(6, "TestComment");
//        preparedStatement.executeUpdate();
//
//        preparedStatement = connect
//                .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
//        resultSet = preparedStatement.executeQuery();
//        writeResultSet(resultSet);
//
//        // Remove again the insert comment
//        preparedStatement = connect
//        .prepareStatement("delete from feedback.comments where myuser= ? ; ");
//        preparedStatement.setString(1, "Test");
//        preparedStatement.executeUpdate();
//
//        resultSet = statement
//        .executeQuery("select * from feedback.comments");
       
        if (resultSet != null) {
            resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connect != null) {
            connect.close();
        }
        val = val.substring(0, val.length()-2);
        return val;
    } catch (Exception e) {
        throw e;
    } finally {
        //close();
    }

}

private void writeMetaData(ResultSet resultSet) throws SQLException {
    //  Now get some metadata from the database
    // Result set get the result of the SQL query

    System.out.println("The columns in the table are: ");

    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
        System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
}

private void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
        // It is possible to get the columns via name
        // also possible to get the columns via the column number
        // which starts at 1
        // e.g. resultSet.getSTring(2);
        String name = resultSet.getString("name");
        String city = resultSet.getString("city");
        String college = resultSet.getString("college");
       
        int id = resultSet.getInt("id");
        System.out.println("User: " + name);
        System.out.println("Website: " + city);
        System.out.println("summary: " + college);

        System.out.println("Comment: " + id);
    }
}

// You need to close the resultSet
/*private void close() {
    try {
        if (resultSet != null) {
            resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connect != null) {
            connect.close();
        }
    } catch (Exception e) {

    }
}*/

}
