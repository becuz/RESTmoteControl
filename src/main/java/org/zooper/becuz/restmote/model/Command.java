package org.zooper.becuz.restmote.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.model.interfaces.Completable;
import org.zooper.becuz.restmote.model.interfaces.Editable;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * Identifies a customizable pc command to execute
 * TODO examples
 * 
 * @author bebo
 */
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
public class Command implements Editable, Completable {

	/**
	 * pk
	 */
	@JsonView(Views.All.class)
	private Long id;
	
	/**
	 * Name of the application, es "Firefox", or "Firefox v2" 
	 */
	private String name;

	/**
	 * Command to execute in the shell
	 */
	private String command;
	
	/**
	 * Free description for this command
	 */
	private String description;
	
	public Command() {
		super();
	}
	

	public Command(String name, String command, String description) {
		this();
		this.name = name;
		this.command = command;
		this.description = description;
	}

	@Override
	public void isComplete() throws IllegalArgumentException {
		StringBuffer errors = new StringBuffer();
		if (Utils.isEmpty(getName())){
			errors.append("\nName is mandatory");
		}
		if (Utils.isEmpty(getCommand())){
			errors.append("\nName is mandatory");
		}
		if (errors.length() > 0){
			throw new IllegalArgumentException(errors.toString());
		}
	}
	
	@Override
	public void validate() throws IllegalArgumentException {
		isComplete();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
