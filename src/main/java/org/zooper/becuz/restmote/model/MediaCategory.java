package org.zooper.becuz.restmote.model;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.zooper.becuz.restmote.model.interfaces.Completable;
import org.zooper.becuz.restmote.model.interfaces.Editable;
import org.zooper.becuz.restmote.model.transport.MediaRoot;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * The class is a entry point for browsing files. It allows to specify paths and extensions to include.
 * It has an optional {@link App} associated to it, meaning the {@link MediaRoot#getMediaChildren()} should be opened with that app.
 * @author bebo
 */
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
public class MediaCategory implements Editable, Completable{

	@JsonIgnore
	public static final String ROOT_NAME = "root"; 
	
	/**
	 * 
	 */
	@JsonIgnore
	private Long id;
	
	/**
	 * Name
	 */
	private String name;
	
	/**
	 * Extensions that this MediaCategory manages
	 */
	@JsonIgnore
	private Set<String> extensions;
	
	/**
	 * Reference to the {@link App} linked to this {@link MediaCategory}
	 */
	private App app;
	
	/**
	 * 
	 */
	@JsonIgnore
	private String description;
	
	/**
	 * Paths.
	 */
	@JsonIgnore
	private Set<String> paths;
	
	/**
	 * 
	 */
	@JsonIgnore
	private Boolean active;
	
	public MediaCategory() {
	}
	
	public MediaCategory(String name) {
		this();
		this.name = name;
	}
	
	@Override
	public void validate() throws IllegalArgumentException {
		if (Utils.isEmpty(name)){
			throw new IllegalArgumentException("Name is mandatory");
		}
	}
	
	@Override
	public boolean isComplete() {
		if (extensions == null || extensions.isEmpty()){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Control) ) return false;
        final MediaCategory a = (MediaCategory) other;
        if (!a.getName().equals(getName())) return false;
        if (!a.getExtensions().equals(getExtensions())) return false;
        if (!a.getId().equals(getId())) return false;
        return true;
    }

	@Override
    public int hashCode() {
		int hash = 7;
        hash = 31 * hash + getName().hashCode();
        hash = 31 * hash + getExtensions().hashCode();
        hash = 31 * hash + getId().hashCode();
        return hash;
    }

	@Override
	public String toString() {
		return getName();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<String> getPaths() {
		if (paths == null) {
			paths = new HashSet<String>();
		}
		if (ROOT_NAME.equals(getName())){
			Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
			for (Path name: dirs) {
			    paths.add(name.toFile().getAbsolutePath());
			}
		}
		return paths;
	}

	public void setPaths(Set<String> paths) {
		this.paths = paths;
	}
	
	public void addPath(String path) {
		getPaths().add(path);
	}

	public boolean removePath(String path) {
		return getPaths().remove(path);
	}
	
	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonIgnore
	public Boolean isRoot() {
		return ROOT_NAME.equals(getName());
	}
	
	@JsonIgnore
	public Boolean isEditable() {
		return !isRoot();
	}

	
}
