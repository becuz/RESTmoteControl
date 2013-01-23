package org.zooper.becuz.restmote.model;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.conf.ModelFactoryAbstract;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.controller.PcControllerAbstract;
import org.zooper.becuz.restmote.controller.PcControllerWindows;
import org.zooper.becuz.restmote.model.interfaces.Completable;
import org.zooper.becuz.restmote.model.interfaces.Editable;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.rest.resources.PcResource;
import org.zooper.becuz.restmote.utils.Constants;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * Identifies a computer application, with its shortcuts.
 * 
 * @see ModelFactoryAbstract#getAppMusic() 
 * @author bebo
 */
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
public class App implements Editable, Completable{

	@JsonView(Views.All.class)
	private Long id;
	
	/**
	 * Name of the application, es "Firefox", or "Firefox v2" 
	 */
	private String name;
	
	/**
	 * Name of the family of application. 
	 * This is needed when there are several definitions of a common program, usually because some versions introduce different shortcuts. 
	 * For example there could exist an app Firefox and a Firefox v2. Both of them should belong to the same family "Firefox" and just one of them will be {@link #chosen}. 
	 */
	@JsonView(Views.All.class)
	private String family;
	
	/**
	 * Name of the application as is in {@link ActiveApp#getName()} it's returned fom {@link PcControllerAbstract#rebuildActiveApps()}
	 */
	private String windowName;
	
	/**
	 * True if this is the default {@link #chosen} of the {@link #family}
	 */
	@JsonView(Views.All.class)
	private Boolean chosenDefault;
	
	/**
	 * True if this entity is the default of its family
	 */
	@JsonView(Views.All.class)
	private Boolean chosen;
	
	/**
	 * The os of this app
	 */
	@JsonView(Views.All.class)
	private String os;
	
	/**
	 * Full path of the executable. The name of the executable is enough if its path is in the system bin paths variable.
	 * This string is a command that the system console has to able to run. 
	 */
	@JsonView(Views.All.class)
	private String path;
	
	/**
	 * Relative path
	 */
	private String relativePath;
	
	/**
	 * meta arguments for file opening
	 */
	@JsonView({Views.All.class})
	private String argumentsFile = Constants.APP_ARGUMENT_FILE;
	
	/**
	 * meta arguments for directory opening
	 */
	@JsonView({Views.All.class})
	private String argumentsDir = null;
	
	
	/**
	 * if true, {@link PcControllerAbstract} makes this app to run always in just one instance.
	 * @see PcControllerWindows#openFile(String, App)
	 * @see PcControllerWindows#closeApp(App)
	 */
	@JsonView(Views.All.class)
	private Boolean forceOneInstance = true;
	
	/**
	 * Extensions that this App manages. 
	 * Needed just if this app isn't attached to any {@link MediaCategory} and the client wants to control this app through {@link PcResource}
	 */
	@JsonView(Views.All.class)
	private Set<String> extensions;
	
	/**
	 * time this model entity was created
	 */
	@JsonView(Views.All.class)
	private Date creationDate;
	
	/**
	 * last time this model entity was updated
	 */
	@JsonView(Views.All.class)
	private Date updateDate;

	/**
	 * Business controls for this app
	 */
	@JsonView(Views.All.class)
	private ControlsManager controlsManager;
	
	/**
	 * UI controls for this app
	 */
	private VisualControlsManager visualControlsManager;
	
	/**
	 * Categories of controls
	 */
	@JsonView(Views.All.class)
	private SortedSet<ControlCategory> controlCategories;
	
	public App() {
	}
	
	public App(String name) {
		this();
		this.name = name;
	}
	
	public App(String name, String path) {
		this(name);
		this.path = path;
	}
	
	@Override
	public void validate() throws IllegalArgumentException {
		isComplete();
	}
	
	@Override
	public void isComplete() throws IllegalArgumentException {
		StringBuffer errors = new StringBuffer();
		if (Utils.isEmpty(getName())){
			errors.append("\nName is mandatory");
		}
		if (Utils.isEmpty(getPath())){
			errors.append("\nPath is mandatory");
		} else if (!new File(getPath()).exists()){
			errors.append("\nPath doesn't exist");
		}
		if (Utils.isEmpty(getWindowName())){
			errors.append("\nWindow is mandatory");
		}
//		if (controls == null || controls.isEmpty()){
//			errors.append("\nNo controls are defined");
//		}
		if (Utils.isEmpty(getArgumentsFile())){
			errors.append("\nArg file is mandatory");
		}
		if (errors.length() > 0){
			throw new IllegalArgumentException(errors.toString());
		}
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
		return getName();
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public String getWindowName() {
		return windowName;
	}
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
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
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public Boolean isChosenDefault() {
		return chosenDefault;
	}
	public void setChosenDefault(Boolean chosenDefault) {
		this.chosenDefault = chosenDefault;
	}
	public Boolean isChosen() {
		return chosen;
	}
	public void setChosen(Boolean chosen) {
		this.chosen = chosen;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public Boolean getForceOneInstance() {
		return forceOneInstance;
	}
	public void setForceOneInstance(Boolean forceOneInstance) {
		this.forceOneInstance = forceOneInstance;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updatedDate) {
		this.updateDate = updatedDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SortedSet<ControlCategory> getControlCategories() {
		if (controlCategories == null){
			controlCategories = new TreeSet<ControlCategory>();
		}
		return controlCategories;
	}
	public void setControlCategories(SortedSet<ControlCategory> controlCategories) {
		this.controlCategories = controlCategories;
	}
	public void addControlCategory(ControlCategory controlCategory) {
		controlCategory.setLogicOrder(getControlCategories().size());
		getControlCategories().add(controlCategory);
	}
	
	public void removeKeysEvent(ControlCategory controlCategory) {
		getControlCategories().remove(controlCategory);
		int i = 0;
		for(ControlCategory c: getControlCategories()){
			c.setLogicOrder(i++);
		}
	}

	public ControlsManager getControlsManager() {
		if (controlsManager == null){
			controlsManager = new ControlsManager();
		}
		return controlsManager;
	}

	public void setControlsManager(ControlsManager controlsManager) {
		this.controlsManager = controlsManager;
	}

	public VisualControlsManager getVisualControlsManager() {
		if (visualControlsManager == null){
			visualControlsManager = new VisualControlsManager();
		}
		return visualControlsManager;
	}

	public void setVisualControlsManager(VisualControlsManager visualControlsManager) {
		this.visualControlsManager = visualControlsManager;
	}

	
}
