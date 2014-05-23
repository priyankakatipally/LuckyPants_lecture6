package test;

import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import com.luckypants.model.Book;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientTest {
	public static void main(String[] args) {
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource("http://localhost:5100/rest/books/");

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			String responseStr = response.getEntity(String.class);

			ObjectMapper mapper = new ObjectMapper();
			JavaType type = mapper.getTypeFactory().constructCollectionType(
					ArrayList.class, Book.class);

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