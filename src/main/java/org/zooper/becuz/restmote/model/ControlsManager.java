package org.zooper.becuz.restmote.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bebo
 *
 */
public class ControlsManager<E extends ControlInterface> {

	/**
	 * 
	 */
	private Set<E> controls;
	
	public ControlsManager(Set<E> controls) {
		this.controls = controls;
	}
	
	public Set<E> getControls() {
		if (controls == null){
			controls = new HashSet<E>();
		}
		return controls;
	}

	public void setControls(Set<E> controls) {
		this.controls = controls;
	}
	
	public void addControls(Collection<E> newControls) {
		for(E c: newControls){
			addControl(c);
		}
	}
	
	public E addControl(E control) {
//		for(Control c: controls){
//			if (c.getName().equals(control.getName())){
//				throw new IllegalArgumentException("Control " + control.getName() + " already present");
//			}
//		}
		getControls().add(control);
		return control;
	}
	
	public E getControl(Long id) {
		if (controls != null){
			for(E c: controls){
				if (c.getId().equals(id)){
					return c;
				}
			}
		}
		return null;
	}
	
	public E getControl(String controlName) {
		if (controls != null){
			for(E c: controls){
				if (c.getName().toLowerCase().equals(controlName.toLowerCase())){
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
