package org.zooper.becuz.restmote.ui.panels;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;

import org.zooper.becuz.restmote.model.interfaces.Persistable;

/**
 * 
 * @author bebo
 */
public abstract class PanelPersistables extends JPanel{

	/**
     * Swing list model
     */
    protected DefaultListModel<Persistable> listModel = new DefaultListModel<Persistable>();
	
	/**
	 * Load the UI with the model
	 */
	public abstract void copyToView();
	
	/**
	 * Save the model using the business layer
	 */
	public abstract void save();
	
	/**
	 * 
	 * @return a new Persistable
	 */
	public abstract Persistable getNew();
	
	/**
	 * edit
	 * @param p 
	 */
	public abstract void edit(Persistable p);
	
	/**
	 * 
	 * @return listModel
	 */
	public DefaultListModel<Persistable> getListModel(){
		return listModel;
	};
}
