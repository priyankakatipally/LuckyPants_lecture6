package com.luckypants.command;

import com.luckypants.mongo.BooksConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class DeleteBookCommand {

	public boolean execute(String isbn) {
		BooksConnectionProvider booksConn = new BooksConnectionProvider();
		DBCollection booksCollection = booksConn.getCollection();

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("isbn", isbn);

		//DBCursor cursor = booksCollection.find(searchQuery);

		booksCollection.remove(searchQuery);

		return true;
	}

	public static void main(String[] args) {
		DeleteBookCommand command = new DeleteBookCommand();
		boolean result =  command.execute("h");
		System.out.println(result);
	}

}
