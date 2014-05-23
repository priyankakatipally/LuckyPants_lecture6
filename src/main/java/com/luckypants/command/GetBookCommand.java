package com.luckypants.command;

import com.luckypants.mongo.BooksConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class GetBookCommand {

	public DBObject execute(String isbn) {
		BooksConnectionProvider booksConn = new BooksConnectionProvider();
		DBCollection booksCollection = booksConn.getCollection();

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("isbn", isbn);

		DBCursor cursor = booksCollection.find(searchQuery);
		DBObject book = cursor.next();
		
		return book;
	}
	
	public static void main(String[] args) {
		GetBookCommand command = new GetBookCommand();
		System.out.println(command.execute("1234"));
	}

}
