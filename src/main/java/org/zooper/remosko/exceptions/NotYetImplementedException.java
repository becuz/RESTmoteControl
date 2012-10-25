package org.zooper.remosko.exceptions;

public class NotYetImplementedException extends RuntimeException {

	public NotYetImplementedException() {
		super("Not yet implemented. Fancy do it yourself? ;)");
	}
	
	public NotYetImplementedException(String message) {
		super(message);
	}
	
}
