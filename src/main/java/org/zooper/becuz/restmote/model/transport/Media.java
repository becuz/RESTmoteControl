package org.zooper.becuz.restmote.model.transport;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Represents a file in the file system of the server.
 * This class is never stored in db, it's used in memory and for transport (it's serialized to the client(s))
 * @author bebo
 */
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
public class Media {

	/**
	 * Child nodes of the directory represented by this {@link Media}
	 */
	protected List<Media> mediaChildren;
	
	/**
	 * If file the field is not used and is always -1.
	 * if !file: if it's the directory has not been traversed; if it's 0 the directory is empty. 
	 */
	protected Integer mediaChildrenSize = -1;
	
	protected Boolean file = false;
	
	protected String path;
	
	/**
	 * Currently equals to the last fragment of the path
	 */
	protected String name;
	
	public Media() {}

	public Media(String path) {
		setPath(path);
	}
	
	@Override
	public String toString() {
		return "path:" + getPath();
	}
	
	public List<Media> getMediaChildren() {
		if (mediaChildren == null){
			mediaChildren = new ArrayList<Media>();
		}
		return mediaChildren;
	}
	
	public void setMediaChildren(List<Media> children) {
		this.mediaChildren = children;
		if (mediaChildren != null){
			this.mediaChildrenSize = mediaChildren.size();
		}
	}
	
	public void addMediaChild(Media media) {
		this.getMediaChildren().add(media);
		this.mediaChildrenSize = mediaChildren.size();
	}
	
	public void clearChildren(){
		if (mediaChildren != null){
			mediaChildren.clear();
		}
		mediaChildrenSize = 0;
	}

	public String getName() {
		return name;
	}

	public boolean isFile() {
		return file;
	}

	public void setFile(boolean file) {
		this.file = file;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		if (path != null){
			String[] p = path.split("\\"+System.getProperty("file.separator"));
			setName(p.length > 0 ? p[p.length-1] : path);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMediaChildrenSize() {
		return mediaChildrenSize;
	}

	public void setMediaChildrenSize(Integer mediaChildrenSize) {
		this.mediaChildrenSize = mediaChildrenSize;
	}
	
	
}
