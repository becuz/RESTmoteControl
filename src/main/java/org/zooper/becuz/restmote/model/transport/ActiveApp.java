package org.zooper.becuz.restmote.model.transport;

import javax.xml.bind.annotation.XmlRootElement;

import org.zooper.becuz.restmote.controller.PcControllerAbstract;
import org.zooper.becuz.restmote.model.App;

/**
 * Represents a currently running application on the pc.
 * This class is never stored in db, it's used in memory and for transport (it's serialized to the client(s))
 * To maximize clients experience, for configured apps, {@link #name} has to be the same of {@link App#getName()}, 
 * ie {@link PcControllerAbstract#listApps(boolean)} has to create {@link ActiveApp}s with this constraint.
 * @author bebo
 */
@XmlRootElement
public class ActiveApp implements Comparable<ActiveApp>{
	
	/**
	 * 
	 */
	private String handle;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String windowLbl;
	
	/**
	 * 
	 */
	private boolean focus;
	
	public ActiveApp() {}
	
	public ActiveApp(String handle, String name, String windowLbl, boolean hasFocus) {
		this.handle = handle;
		this.name = name;
		this.windowLbl = windowLbl;
		this.focus = hasFocus;
	}
	
	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getWindowLbl() {
		return windowLbl;
	}
	
	public void setWindowLbl(String windowLbl) {
		this.windowLbl = windowLbl;
	}
	
	public boolean isFocus() {
		return focus;
	}
	
	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ActiveApp)) return false;
        final ActiveApp app = (ActiveApp) other;
        if (!app.getHandle().equals(getHandle())) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + getHandle().hashCode();
        return hash;
    }
	
	@Override
	public String toString() {
		return "ActiveApp: " + getHandle() + "_-_" + getName() + "_-_" + getWindowLbl() + "(" + isFocus() + ")";
	}

	@Override
	public int compareTo(ActiveApp o) {
		return getHandle().compareTo(o.getHandle());
	}

	public boolean isInstanceOf(App app) {
		if (app == null) return false;
		return getName().toLowerCase().equals(app.getName().toLowerCase());
	}
	
	
}