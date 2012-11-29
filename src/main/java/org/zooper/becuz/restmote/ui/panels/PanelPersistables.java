package org.zooper.becuz.restmote.ui.panels;

import javax.swing.DefaultListModel;

import org.zooper.becuz.restmote.model.interfaces.Persistable;

/**
 *
 * @author bebo
 */
public interface PanelPersistables {
	
	public void copyToView();
	
	public void save();
	
	public Persistable getNew();
	
	public void edit(Persistable p);
	
	public DefaultListModel<Persistable> getListModel();
}
