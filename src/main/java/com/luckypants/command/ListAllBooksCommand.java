package com.luckypants.command;

import java.util.ArrayList;

import com.luckypants.mongo.BooksConnectionProvider;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ListAllBooksCommand {
	
	public ArrayList<DBObject> execute(){
		BooksConnectionProvider booksConn = new BooksConnectionProvider();
		DBCollection booksCollection = booksConn.getCollection();
		
		DBCursor cursor = booksCollection.find();
		
		ArrayList<DBObject> books = new ArrayList<DBObject>();
		try {
		   while(cursor.hasNext()) {
			   books.add(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		return books;
		
	}
	public static void main(String[] args) {
		ListAllBooksCommand listBooks = new ListAllBooksCommand();
		ArrayList<DBObject> list = listBooks.execute();
		System.out.println(list);

	}
}
