package org.zooper.becuz.restmote.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.Responses;

/**
 * @author bebo
 *
 */
public class NotFoundException extends WebApplicationException {
	
	private static final long serialVersionUID = 1L;
	
	public NotFoundException() {
		super(Responses.notFound().build());
	}
	
    public NotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}