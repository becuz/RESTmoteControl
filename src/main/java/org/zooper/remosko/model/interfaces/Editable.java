package org.zooper.remosko.model.interfaces;

/**
 * All model classes that can be configured server side implements this interface 
 * @author bebo
 */
public interface Editable extends Persistable{

	public String getName();
	public void setName(String name);
	
}
