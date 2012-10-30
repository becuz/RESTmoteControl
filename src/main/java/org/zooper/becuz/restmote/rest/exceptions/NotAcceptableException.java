package org.zooper.becuz.restmote.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.Responses;

/**
 * @author bebo
 *
 */
public class NotAcceptableException extends WebApplicationException {
    
	private static final long serialVersionUID = 1L;

	public NotAcceptableException() {
		super(Responses.notAcceptable().build());
	}
	
	public NotAcceptableException(String message) {
        super(Response.status(Response.Status.NOT_ACCEPTABLE).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}