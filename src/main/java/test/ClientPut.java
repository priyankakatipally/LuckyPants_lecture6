package test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientPut {
	public static void main(String[] args) {
		try {
			//create a client object for consuming the REST response
			Client client = Client.create();
			
			//the REST URL you want to connect to
			WebResource webResource = client
					.resource("http://localhost:5100/rest/books/");
			
			String input = "{\"title\":\"Client book\",\"author\":\"Client Author\",\"isbn\":\"Client isbn\"}";

			//tell the service you are connecting to that you accept JSON
			ClientResponse response = webResource.accept("application/json")
					.put(ClientResponse.class, input);

			if(response.getStatus()!=200 && response.getStatus()!=201){
				throw new Exception("Book was not created");
			}
			else{
				System.out.println("created");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
