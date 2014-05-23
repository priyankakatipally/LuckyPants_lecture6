package test;

import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import com.luckypants.model.Book;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CleintGet {
	public static void main(String[] args) {
		try {
			//create a client object for consuming the REST response
			Client client = Client.create();
			
			//the REST URL you want to connect to
			WebResource webResource = client
					.resource("http://localhost:5100/rest/books/");

			//tell the service you are connecting to that you accept JSON
			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			//responseStr now holds the response from the REST service
			String responseStr = response.getEntity(String.class);

			//mapper object we have used in the last class
			ObjectMapper mapper = new ObjectMapper();
			
			//We are expecting to receive back an ArrayList of Book objects, 
			//Here we build a new type that will allow Jackson to serialize
			//the JSON we receive into it
			JavaType type = mapper.getTypeFactory().constructCollectionType(
					ArrayList.class, Book.class);

			//Serialize the response into our newly described type
			ArrayList<Book> bookArray = mapper.readValue(responseStr, type);

			System.out.println("Got " + bookArray.size() + " books");
			int k = 1;

			for (Book book : bookArray) {
				System.out.println("Book " + k + ":");
				System.out.println("ISBN: " + book.getISBN());
				System.out.println("Author: " + book.getAuthor());
				System.out.println("Title: " + book.getTitle());
				System.out.println();
				k++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
