package org.zooper.becuz.restmote.persistence.export;

import java.util.List;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Command;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.Settings;

/**
 * Class used to serialize/deserialize the db or part of it
 * 
 * @author bebo
 */
public class Dump {

	private Settings settings;
	private List<MediaCategory> mediaCategories;
	private List<App> apps;
	private List<Command> commands;
	
	public Dump() {}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public List<MediaCategory> getMediaCategories() {
		return mediaCategories;
	}

	public void setMediaCategories(List<MediaCategory> mediaCategories) {
		this.mediaCategories = mediaCategories;
	}

	public List<App> getApps() {
		return apps;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	
	
}
