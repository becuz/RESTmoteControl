package org.zooper.becuz.restmote.business;

import java.util.Collection;

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
		Collection<Settings> all = getAll();
		if (all == null || all.isEmpty()){
			return null;
		} else {
			return all.iterator().next();
		}
	}
	
}
