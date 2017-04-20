package com.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.BSONEncoder;
import org.bson.BasicBSONEncoder;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBJDBC {

	public static void main(String[] args) {

		try {

			String user = "mongouser"; // the user name
			String password = "pass@123"; // the password as a character array
			String database = "extended_doc"; // the name of the database in which the user is defined

			List<ServerAddress> seeds = new ArrayList<ServerAddress>();
			seeds.add( new ServerAddress( "test.mongodb.firstrain.com",27017 ));

			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			credentials.add(
					MongoCredential.createScramSha1Credential(
							user,
							database,
							password.toCharArray()
							)
					);
			MongoClient mongoClient = new MongoClient( seeds, credentials );

			MongoDatabase db = mongoClient.getDatabase(database);

			System.out.println("Connect to DataBase Sucessfully");

			MongoCollection<Document> docs = db.getCollection("meta");

			System.out.println("docs == " + docs.count() );

			Document myDoc = docs.find().first();
			//System.out.println(myDoc.toJson());

		//	{ "_id" : { "$oid" : "58e720899b629f6a6dcf16c0" }, "itemsName" : "Orange", "soldQty" : 4.0 }
			
			// now use a query to get 1 document out
		//	myDoc = docs.find(eq("ng.ngrams.phrase", "supervisors chambers")).first();
			//System.out.println("Test ==" + myDoc.toJson());

			//db.meta.find({"ng.ngrams":{$elemMatch:{"phrase":"trump","firstLocation":"Q1"}}}).limit(1)
			
			Document  statusQuery = new Document("phrase", "trump").append("firstLocation", "Q1");
	        
			Document elemMatchQuery = new Document("$elemMatch", statusQuery);

			Document fields = new Document();
	        
	        fields.put("ng.ngrams", elemMatchQuery);
	        
	      //  fields.put("name", 1);
	        //fields.put("config", 1);

	        
	        System.out.println("fikter Q count == " + docs.count(fields));

	        FindIterable<Document> cursor = docs.find(fields).limit(2);
	        //int i = 0;
	        
			for (Document document : cursor) {
				System.out.println(document.toJson());
			}
	        
			//MongoCursor<Document> cursor = docs.find().iterator();
/*
			// now use a range query to get a larger subset
			cursor = docs.find(gt("i", 50)).iterator();

			try {
				while (cursor.hasNext()) {
					System.out.println(cursor.next().toJson());
				}
			} finally {
				cursor.close();
			}
*/
			// range query with multiple constraints
//			cursor = docs.find(and(eq("ng.ngrams.phrase", "trump"), eq("ng.ngrams.firstLocation", "Q1"))).iterator();

/*			try {
				while (cursor.hasNext()) {
					System.out.println(cursor.next().toJson());
				}
			} finally {
				cursor.close();
			}

			// Query Filters
			myDoc = docs.find(eq("i", 71)).first();
			System.out.println(myDoc.toJson());

			// Sorting
			myDoc = docs.find(exists("i")).sort(descending("i")).first();
			System.out.println(myDoc.toJson());

			// Projection
			myDoc = docs.find().projection(excludeId()).first();
			System.out.println(myDoc.toJson());
*/

			mongoClient.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
