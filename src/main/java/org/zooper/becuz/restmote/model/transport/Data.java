package org.zooper.becuz.restmote.model.transport;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.zooper.becuz.restmote.model.ControlsManager;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeKeyboard;
import org.zooper.becuz.restmote.model.Control.ControlDefaultTypeMouse;
import org.zooper.becuz.restmote.rest.resources.DataResource;

/**
 * Convenient wrapper to send at once all informations clients need to start: clients can initially performs a single call ({@link DataResource#get()}). 
 * This class is never stored in db, it's used in memory and for transport (it's serialized to the client(s))
 * @author bebo
 */
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
public class Data {

	/**
	 * 
	 */
	private Settings settings;
	
	/**
	 * Immediate children of Media root.
	 * @see ExploreBusiness#getMediaRoots()
	 */
	private List<MediaRoot> mediaRoots;
	
	/**
	 * Available keyboards controls
	 * @see ControlDefaultTypeKeyboard
	 */
	private ControlsManager keyboardControlsManager;
	
	/**
	 * Available mouse controls
	 * @see ControlDefaultTypeMouse
	 */
	private ControlsManager mouseControlsManager;
	
	//****************************************************************************************
	
	public Data() {
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public List<MediaRoot> getMediaRoots() {
		return mediaRoots;
	}

	public void setMediaRoots(List<MediaRoot> mediaRoots) {
		this.mediaRoots = mediaRoots;
	}

	public ControlsManager getKeyboardControlsManager() {
		return keyboardControlsManager;
	}

	public void setKeyboardControlsManager(ControlsManager keyboardControlsManager) {
		this.keyboardControlsManager = keyboardControlsManager;
	}

	public ControlsManager getMouseControlsManager() {
		return mouseControlsManager;
	}

	public void setMouseControlsManager(ControlsManager mouseControlsManager) {
		this.mouseControlsManager = mouseControlsManager;
	}
	
}
