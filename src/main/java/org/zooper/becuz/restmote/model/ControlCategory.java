package org.zooper.becuz.restmote.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.model.interfaces.Editable;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * @author bebo
 */
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
public class ControlCategory implements Comparable<ControlCategory>, Editable {

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
	 * order for {@link Comparable}
	 */
	private Integer logicOrder;
	
	public ControlCategory() {
		super();
	}
	
	public ControlCategory(String name) {
		super();
		this.name = name;
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

	public Integer getLogicOrder() {
		return logicOrder;
	}

	public void setLogicOrder(Integer logicOrder) {
		this.logicOrder = logicOrder;
	}

	@Override
	public void validate() throws IllegalArgumentException {
		if (Utils.isEmpty(getName())){
			throw new IllegalArgumentException("Name is mandatory");
		}
	}

	@Override
	public int compareTo(ControlCategory o) {
		return this.getLogicOrder().compareTo(o.getLogicOrder());
	}
	

	
	

}
