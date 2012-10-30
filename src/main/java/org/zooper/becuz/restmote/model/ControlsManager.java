package org.zooper.becuz.restmote.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.zooper.becuz.restmote.controller.PcControllerAbstract;
import org.zooper.becuz.restmote.model.interfaces.Persistable;

/**
 * Simple utility class to link several {@link Control}s to an other {@link Persistable}: {@link App} or {@link PcControllerAbstract} implementations.
 *
 * @author bebo
 *
 */
public class ControlsManager implements Persistable{

	/**
	 * Local store id
	 */
	@JsonIgnore
	private Long id;
	
	/**
	 * 
	 */
	private Set<Control> controls;
	
	public ControlsManager() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Control> getControls() {
		return controls;
	}

	public void setControls(Set<Control> controls) {
		this.controls = controls;
	}
	
	public void addControls(Collection<Control> newControls) {
		for(Control c: newControls){
			addControl(c);
		}
	}
	
	public void addControl(Control control) {
		if (controls == null){
			controls = new HashSet<Control>();
		}
//		for(Control c: controls){
//			if (c.getName().equals(control.getName())){
//				throw new IllegalArgumentException("Control " + control.getName() + " already present");
//			}
//		}
		controls.add(control);
	}
	
	public Control getControl(String controlName) {
		if (controls != null){
			for(Control c: controls){
				if (c.getName().equals(controlName)){
					return c;
				}
			}
		}
		return null;
	}
	
	public void removeControl(Control control) {
		getControls().remove(control);
	}
	
}
