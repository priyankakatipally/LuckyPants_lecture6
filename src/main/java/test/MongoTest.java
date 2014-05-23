package test;

/*
 * this code uses example from 
 * http://www.mkyong.com/mongodb/java-mongodb-hello-world-example/
 */
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * this is a test class with some sample code for you to try your Mongo HQ
 * connection Do not use this same connection, create your own account/DB and
 * user
 * 
 */
public class MongoTest {
	public static void main(String[] args) {

		try {

			/**
			 * Connect to Mongo DB
			 */
			MongoClient mongo = new MongoClient("oceanic.mongohq.com", 10010);

			/**
			 * Connect to DB
			 */
			DB db = mongo.getDB("luckypants1");
			if (db == null) {
				System.out.println("Could not connect to Database");
			}

			/**
			 * authenticate to Mongo HQ
			 */
			boolean auth = db.authenticate("priyanka", "priyanka".toCharArray());
			if (auth == false) {
				System.out.println("Could not authenticate");
			}

			/**
			 * get a handler to books collection
			 */
			DBCollection booksColl = db.getCollection("books");

			/**
			 * add a book to books table (collection)
			 */
			BasicDBObject document = new BasicDBObject();
			document.put("title", "Lucky Pants");
			document.put("author", "John Doe");
			document.put("ISBN", "1234");
			booksColl.insert(document);

			/**
			 * find a book by title
			 */
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("title", "Lucky Pants");

			DBCursor cursor = booksColl.find(searchQuery);

			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}

			/**
			 * update a book - this will update only one record
			 */
			searchQuery.put("title", "Lucky Pants");

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("titile", "Super Pants");

			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			booksColl.update(searchQuery, updateObj);

			/**
			 * Delete book by title - this will delete only one book
			 */
			searchQuery.put("title", "Lucky Pants");
			booksColl.remove(searchQuery);

			/**
			 * Delete all books by title
			 */
			searchQuery.put("title", "Lucky Pants");

			cursor = booksColl.find(searchQuery);

			while (cursor.hasNext()) {
				booksColl.remove(searchQuery);
			}

			/**
			 * End
			 */

			System.out.println("Done");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}