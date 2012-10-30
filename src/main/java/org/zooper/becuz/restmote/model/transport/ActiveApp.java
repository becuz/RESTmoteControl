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
	private String pid;
	
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
	
	public ActiveApp(String pid, String name, String windowLbl, boolean hasFocus) {
		this.pid = pid;
		this.name = name;
		this.windowLbl = windowLbl;
		this.focus = hasFocus;
	}
	
	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
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
        if (!app.getPid().equals(getPid())) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + getPid().hashCode();
        return hash;
    }
	
	@Override
	public String toString() {
		return "ActiveApp: " + getPid() + "_-_" + getName() + "_-_" + getWindowLbl();
	}

	@Override
	public int compareTo(ActiveApp o) {
		return getPid().compareTo(o.getPid());
	}
	
	
}