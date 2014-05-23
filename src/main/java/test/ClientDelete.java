package test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientDelete {
	public static void main(String[] args) {
		try {
			// create a client object for consuming the REST response
			Client client = Client.create();

			// the REST URL you want to connect to
			WebResource webResource = client
					.resource("http://localhost:5100/rest/books/12345");

			// tell the service you are connecting to that you accept JSON
			ClientResponse response = webResource.accept("application/json")
					.delete(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new Exception("Book was not deleted");
			} else {
				System.out.println("deleted");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
