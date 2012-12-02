package org.zooper.becuz.restmote.ui.panels;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;

import org.zooper.becuz.restmote.model.interfaces.Persistable;

public abstract class PanelPersistables extends JPanel{

	/**
     * Swing list model
     */
    protected DefaultListModel<Persistable> listModel = new DefaultListModel<Persistable>();
	
	/**
	 * 
	 */
	public abstract void copyToView();
	
	/**
	 * 
	 */
	public abstract void save();
	
	/**
	 * 
	 * @return 
	 */
	public abstract Persistable getNew();
	
	/**
	 * 
	 * @param p 
	 */
	public abstract void edit(Persistable p);
	
	/**
	 * 
	 * @return 
	 */
	public DefaultListModel<Persistable> getListModel(){
		return listModel;
	};
}
