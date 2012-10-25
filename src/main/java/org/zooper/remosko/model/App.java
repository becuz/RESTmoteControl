package org.zooper.remosko.model;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.zooper.remosko.conf.modelfactory.ModelFactoryAbstract;
import org.zooper.remosko.controller.PcControllerWindows;
import org.zooper.remosko.model.interfaces.Editable;
import org.zooper.remosko.rest.resources.PcResource;

/**
 * Identifies an application installed on the server pc and configured by the user.
 * The path is needed to be able to run it with the proper arguments.
 * {@link #controlsManager} is needed to be able to control the app (es: PAUSE) through shourtcut.
 * @see ModelFactoryAbstract#getAppMusic() 
 * @author bebo
 */
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
public class App implements Editable{

	@JsonIgnore
	private Long id;

	/**
	 * Has to be the same used in the controller!!
	 */
	private String name;
	
	/**
	 * Full path of the executable
	 */
	private String path;
	
	/**
	 * meta arguments for file opening
	 */
	private String argumentsFile;
	
	/**
	 * meta arguments for directory opening
	 */
	private String argumentsDir;
	
	/**
	 * 
	 */
	private ControlsManager controlsManager;
	
	/**
	 * if true, the system makes this app runs always in just one instance.
	 * @see PcControllerWindows#open(String, App)
	 * @see PcControllerWindows#close(App)
	 */
	private Boolean forceOneInstance = true;
	
	/**
	 * Extensions that this App manages. 
	 * Needed just if this app isn't attached to any {@link MediaCategory} and the client wants to control this app through {@link PcResource}
	 */
	@JsonIgnore
	private Set<String> extensions;
	
//	/**
//	 * Remote id, for user-submitted Controls. It's the id in remosko website db
//	 */
//	@JsonIgnore
//	private Long idRemote;
//	
//	/**
//	 * 
//	 */
//	@JsonIgnore
//	private Long idSubmitter;
//	
//	/**
//	 * 
//	 */
//	@JsonIgnore
//	private Integer avgVote;
//	
//	/**
//	 * 
//	 */
//	@JsonIgnore
//	private Integer numVoters;
	
	public App() {
	}
	
	public App(String name, String path, String argumentsFile) {
		this();
		this.name = name;
		this.path = path;
		this.argumentsFile = argumentsFile;
	}

	public App(String name, String path, String argumentsFile,String argumentsDir) {
		this(name, path, argumentsFile);
		this.argumentsDir = argumentsDir;
	}

	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof App)) return false;
        final App app = (App) other;
        if (!app.getName().equals(getName())) return false;
        //if (!app.getPath().equals(getPath())) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + getName().hashCode();
        //hash = 31 * hash + getPath().hashCode();
        return hash;
    }
	
	@Override
	public String toString() {
		return "App; " + getId() + "_-_" + getName();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getArgumentsFile() {
		return argumentsFile;
	}
	public void setArgumentsFile(String argumentsFile) {
		this.argumentsFile = argumentsFile;
	}
	public String getArgumentsDir() {
		return argumentsDir;
	}
	public void setArgumentsDir(String argumentsDir) {
		this.argumentsDir = argumentsDir;
	}

	public boolean isForceOneInstance() {
		return forceOneInstance;
	}

	public void setForceOneInstance(boolean forceOneInstance) {
		this.forceOneInstance = forceOneInstance;
	}

	public ControlsManager getControlsManager() {
		return controlsManager;
	}

	public void setControlsManager(ControlsManager controls) {
		this.controlsManager = controls;
	}
	
	public Set<String> getExtensions() {
		if (extensions == null) {
			extensions = new HashSet<String>();
		}
		return extensions;
	}

	public void setExtensions(Set<String> extensions) {
		this.extensions = extensions;
	}

	public void addExtension(String extensions) {
		getExtensions().add(extensions);
	}

	public boolean removeExtension(String extensions) {
		return getExtensions().remove(extensions);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
