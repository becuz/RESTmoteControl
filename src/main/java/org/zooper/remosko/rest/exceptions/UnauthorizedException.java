package org.zooper.remosko.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * TODO
 * @author bebo
 *
 */
public class UnauthorizedException extends WebApplicationException {
    
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedException(String message) {
        super(Response.status(Response.Status.UNAUTHORIZED).entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}