package org.zooper.becuz.restmote.business;

import org.zooper.becuz.restmote.business.interfaces.BusinessModelAbstract;
import org.zooper.becuz.restmote.model.Settings;

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
