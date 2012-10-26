package org.zooper.remosko.business;

import org.zooper.remosko.business.interfaces.BusinessModelAbstract;
import org.zooper.remosko.model.Settings;

/**
 * 
 * @author bebo
 *
 */
public class SettingsBusiness extends BusinessModelAbstract<Settings>{

	public SettingsBusiness() {
		super(Settings.class);
	}
	
	public Settings get() {
		return (Settings) getAll().iterator().next();
	}
	
}
