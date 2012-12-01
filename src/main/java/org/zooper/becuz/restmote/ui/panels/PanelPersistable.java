package org.zooper.becuz.restmote.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.zooper.becuz.restmote.model.interfaces.Persistable;
import org.zooper.becuz.restmote.ui.UIUtils;

/**
 *
 * @author bebo
 */
public abstract class PanelPersistable extends JPanel{
	
	/**
	 * array of components that has to be "listened" with ListenerChanges
	 */
	protected JComponent[] jComponentsToObserve;
	
	/*
	 * 
	 */
	protected PanelListPersistable panelListPersistable;
	
	/**
	 * 
	 */
	private boolean viewModified = false;
	
	/**
	 * 
	 */
	protected boolean listenViewChanges = false;
	
	/*
	 * 
	 */
	private ListenerChanges listenerChanges = new ListenerChanges();
	
	/**
	 * 
	 */
	class ListenerChanges implements DocumentListener, ActionListener{

		private void changed(){
			if (listenViewChanges){
				viewModified = true;
			}
			System.out.println("viewModified: " + viewModified);
		}
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			changed();					
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			changed();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			changed();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			changed();
		}
		
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		UIUtils.setEnabledRecursive(this, enabled);
	}
	
	
	public void edit(Persistable p){
		setEnabled(p != null);
		viewModified = false;
		listenViewChanges = false;
	}
	
	protected void activateViewChangesListener(){
		if (jComponentsToObserve != null){
			for(JComponent jComponent: jComponentsToObserve){
				if (jComponent instanceof JTextField){
					((JTextField)jComponent).getDocument().addDocumentListener(listenerChanges);
				} else if (jComponent instanceof JComboBox){
					((JComboBox)jComponent).addActionListener(listenerChanges);
				} else if (jComponent instanceof JCheckBox){
					((JCheckBox)jComponent).addActionListener(listenerChanges);
				}
			}
		}
	}
	
	
	
}
