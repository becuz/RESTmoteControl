package org.zooper.becuz.restmote.model.interfaces;

/**
 * All model, persisted classes implements this interface 
 * @author bebo
 */
public interface Persistable {

	/**
	 * @return pk
	 */
	public Long getId();
	
	/**
	 * set pk
	 * @param id
	 */
	public void setId(Long id);
	
	/**
	 * 
	 * @throws IllegalArgumentException
	 */
	public void validate() throws IllegalArgumentException;
}
