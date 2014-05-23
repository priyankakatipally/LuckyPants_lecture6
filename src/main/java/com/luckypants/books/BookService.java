package com.luckypants.books;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.map.ObjectMapper;

import com.luckypants.command.CreateBookCommand;
import com.luckypants.command.DeleteBookCommand;
import com.luckypants.command.GetBookCommand;
import com.luckypants.command.ListAllBooksCommand;
import com.luckypants.command.ProvidePackagedFileCommand;
import com.luckypants.model.Book;
import com.luckypants.properties.PropertiesLookup;
import com.mongodb.DBObject;

@Path("/books")
public class BookService {
	ObjectMapper mapper = new ObjectMapper();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listBooks() {
		ListAllBooksCommand listBooks = new ListAllBooksCommand();
		ArrayList<DBObject> list = listBooks.execute();
		return Response.status(200).entity(list).build();
	}

	@GET
	@Path("properties/{property}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response propertiesCheck(@PathParam("property") String property) {
		PropertiesLookup pl = new PropertiesLookup();
		String pValue = pl.getProperty(property);
		return Response.status(200).entity(pValue).build();
	}

	@GET
	@Path("/{isbn}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook(@PathParam("isbn") String isbn) {
		GetBookCommand getBookCommand = new GetBookCommand();
		DBObject book = getBookCommand.execute(isbn);
		return Response.status(200).entity(book).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response createBook(String bookStr) {

		try {
			CreateBookCommand create = new CreateBookCommand();
			Book book = mapper.readValue(bookStr, Book.class);
			boolean success = create.execute(book);
			String bookJSON = mapper.writeValueAsString(book);
			if (success) {
				return Response.status(201).entity(bookJSON).build();
			} else
				return Response.status(500).entity("").build();
		} catch (Exception e) {
			return Response.status(500).entity(e.toString()).build();
		}
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchBook(
			@DefaultValue("*") @QueryParam("query") String query,
			@DefaultValue("author") @QueryParam("sortby") String sortby) {
		HashMap<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("query", query);
		responseMap.put("sortby", sortby);
		String rString = "";
		try {
			rString = mapper.writeValueAsString(responseMap);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.toString()).build();
		}
		return Response.status(200).entity(rString).build();
	}

	@DELETE
	@Path("/{isbn}")
	public Response deleteBook(@PathParam("isbn") String isbn) {
		DeleteBookCommand delete = new DeleteBookCommand();
		delete.execute(isbn);
		return Response.status(200).build();
	}

	@GET
	@Path("files/{filename}")
	@Produces(MediaType.TEXT_HTML)
	public Response getFile(@PathParam("filename") String filename) {
		try {
			ProvidePackagedFileCommand getFile = new ProvidePackagedFileCommand();
			InputStream is = getFile.execute(filename);

			ResponseBuilder response = Response.ok((Object) is);
			response.header("Content-Disposition", "attachment; filename=\""
					+ filename + "\"");
			return response.build();
		} catch (Exception e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("inline/{filename}")
	@Produces(MediaType.WILDCARD)
	public Response renderFile(@PathParam("filename") String filename) {
		try {
			ProvidePackagedFileCommand getFile = new ProvidePackagedFileCommand();
			InputStream is = getFile.execute(filename);

			ResponseBuilder response = Response.ok((Object) is);
			response.header("Content-Disposition", "inline; filename=\""
					+ filename + "\"");
			return response.build();
		} catch (Exception e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}
}
