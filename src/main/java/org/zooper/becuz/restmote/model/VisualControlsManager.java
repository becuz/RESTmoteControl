package org.zooper.becuz.restmote.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.controller.PcControllerAbstract;
import org.zooper.becuz.restmote.model.interfaces.Persistable;

/**
 * Simple utility class to link several {@link Control}s to an other {@link Persistable}: {@link App} or {@link PcControllerAbstract} implementations.
 *
 * @author bebo
 *
 */
public class VisualControlsManager implements Persistable {

	/**
	 * Local store id
	 */
	@JsonView(Views.All.class)
	private Long id;
	
	/**
	 * 
	 */
	private Set<VisualControl> visualControls;
	
	public VisualControlsManager() {
	}
	
	@Override
	public void validate() throws IllegalArgumentException {}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Set<VisualControl> getControls() {
		if (visualControls == null){
			visualControls = new HashSet<VisualControl>();
		}
		return visualControls;
	}

	public void setControls(Set<VisualControl> controls) {
		this.visualControls = controls;
	}
	
	public void addControls(Collection<VisualControl> newControls) {
		for(VisualControl c: newControls){
			addControl(c);
		}
	}
	
	public VisualControl addControl(VisualControl control) {
//		for(Control c: visualControls){
//			if (c.getName().equals(control.getName())){
//				throw new IllegalArgumentException("Control " + control.getName() + " already present");
//			}
//		}
		getControls().add(control);
		return control;
	}
	
	public VisualControl getControl(String controlName) {
		if (visualControls != null){
			for(VisualControl c: visualControls){
				if (c.getName().toLowerCase().equals(controlName.toLowerCase())){
					return c;
				}
			}
		}
		return null;
	}
	
	public VisualControl getControl(Long id) {
		if (visualControls != null){
			for(VisualControl c: visualControls){
				if (c.getId().equals(id)){
					return c;
				}
			}
		}
		return null;
	}
	
	public void clear(){
		getControls().clear();
	}
	
	public void removeControl(Control control) {
		getControls().remove(control);
	}
	
}
