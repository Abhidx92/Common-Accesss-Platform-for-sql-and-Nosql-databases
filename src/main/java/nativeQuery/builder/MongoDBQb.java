package nativeQuery.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Attributes;

import model.AttributeValues;

public class MongoDBQb {

	String buildQuery(AttributeValues attributeValues) {
		String output = null;
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB("test");
			System.out.println("Connection successfull");

			if (attributeValues.getClause().equalsIgnoreCase("select")) {
				output = find(db, attributeValues);
			} else if (attributeValues.getClause().equalsIgnoreCase("update")) {
				output = update(db, attributeValues);

			} else if (attributeValues.getClause().equalsIgnoreCase("delete")) {
				output = delete(db, attributeValues);
			} else if (attributeValues.getClause().equalsIgnoreCase("insert")) {
				output = insert(db, attributeValues);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("server is ready");

		return output;

		/*
		 * switch (clause) { ;}
		 */
	}

	String find(DB connectionDb, AttributeValues attributeValues) {

		// Iterator itr = attributeValues.getColumnList().iterator();
		String output = null;
		DBCollection collection = connectionDb.getCollection(attributeValues.getTableName());
		BasicDBObject whereQuery = new BasicDBObject();
		if (!attributeValues.getConditionValues().isEmpty()) {
			String keyV = null;
			Object value = new Object();
			for (Object key : attributeValues.getConditionValues().keySet()) {
				keyV = key.toString();

				value = attributeValues.getConditionValues().get(key).toString();

			}
			whereQuery = new BasicDBObject();
			/*if (keyV.equalsIgnoreCase("id")) {
				value = Integer.parseInt(value.toString());
			}*/
			
				whereQuery.put(keyV, value);
			
		}

		if (attributeValues.getSelectColumnList().isEmpty()) {

			DBObject dbObject = collection.findOne();

			output = dbObject.toString();

			return output;

		} else {
			if (attributeValues.getSelectColumnList().contains("*")) {
				BasicDBObject fields = new BasicDBObject();
				DBObject dbObject = collection.findOne(whereQuery);
				return dbObject.toString();
			} else {
				BasicDBObject fields = new BasicDBObject();
				for (Object key : attributeValues.getSelectColumnList()) {
					fields.put(key.toString(), 1);
				}

				DBCursor cursor = collection.find(whereQuery, fields);
				while (cursor.hasNext()) {
					output += cursor.next();
				}
				return output;

			}
		}

	}

	String insert(DB connectionDb, AttributeValues attributeValues) {
		// insert a document

		DBCollection collection = connectionDb.getCollection("move");
		BasicDBObject document = new BasicDBObject();

		if (attributeValues.getColumnValues() != null) {
			for (Object k : attributeValues.getColumnValues().keySet()) {
				Object value = (attributeValues.getColumnValues().get(k));
				document.append(k.toString(), value);
			}

		}
		try {
			collection.insert(document);
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}
		return "Insert operation in MongoDb performed successfully";

		// replace a document

	}

	String delete(DB connectionDb, AttributeValues attributeValues) {
		DBCollection collection = connectionDb.getCollection("move");
		BasicDBObject document = new BasicDBObject();
		if (attributeValues.getConditionValues().isEmpty()) {
			DBObject doc = collection.findOne(); // get first document
			collection.remove(doc);
		} else {
			for (Object key : attributeValues.getConditionValues().keySet()) {
				document.put(key.toString(), attributeValues.getConditionValues().get(key));
			}
			collection.remove(document);
		}
		return "Delete operation from MongoDb performed successfully";
	}

	String update(DB connectionDb, AttributeValues attributeValues) {

		// Iterator itr = attributeValues.getColumnList().iterator();
		String output = null;
		DBCollection collection = connectionDb.getCollection("move");
		BasicDBObject whereQuery = new BasicDBObject();

		String keyV = null;
		int value = 0;
		for (Object key : attributeValues.getConditionValues().keySet()) {
			keyV = key.toString();

			value = Integer.valueOf((String) attributeValues.getConditionValues().get(key));
			// value = value.substring(1, value.length() - 1);
		}
		whereQuery.append(keyV, value);

		BasicDBObject newDocument = new BasicDBObject();
		for (Object key : attributeValues.getColumnValues().keySet()) {
			newDocument.append("$set",
					new BasicDBObject().append(key.toString(), attributeValues.getColumnValues().get(key)));
		}

		// BasicDBObject searchQuery = new BasicDBObject().append("hosting",
		// "hostB");
		collection.update(whereQuery, newDocument);
		return "Update operation from MongoDb performed successfully";

	}

}
