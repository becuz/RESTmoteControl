/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zooper.becuz.restmote.ui.appcontrols;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.KeysEvent;
import org.zooper.becuz.restmote.model.VisualControl;
import org.zooper.becuz.restmote.ui.UIUtils;

/**
 * Panel to view and edit the shortcut of a {@link Control}
 * @author bebo
 */
@SuppressWarnings("serial")
public class PanelControlKeys extends javax.swing.JPanel {

	private boolean modified;
	
	/**
	 * model Control
	 */
	private Control control;
	
	/**
	 * current index of {@link Control#getKeysEvents()}
	 */
	private int indexKeysEvents;
	
	/**
	 * 
	 */
	private JTable appControlsTable;
	
	/**
	 * Listener for change selection and cell updates for appControlsTable
	 */
	public class ControlSelectionListener implements ListSelectionListener, TableModelListener {
	
		private int previousSelectedColumn = -1;
		private int previousSelectedRow = -1;

		//private boolean internalUpdate = false;
		
		public ControlSelectionListener() {
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				return;
			}
			int selectedColumn = appControlsTable.getSelectedColumn();
			int selectedRow = appControlsTable.getSelectedRow();
			
//			if (previousSelectedRow > -1 && previousSelectedColumn > -1 && (selectedRow != previousSelectedRow || selectedColumn != previousSelectedColumn)){
//				Control controlSelected = (Control) appControlsTable.getModel().getValueAt(previousSelectedRow, previousSelectedColumn);	
//				if (controlSelected != null){
//					boolean valid = true;
//					try {
//						controlSelected.validate();
//						valid = !controlSelected.isEmpty();
//					} catch (IllegalArgumentException ex){
//						valid = false;
//					}
//					if (!valid){
//						//internalUpdate = true;
//						appControlsTable.getModel().setValueAt(null, previousSelectedRow, previousSelectedColumn);
////					} else {
////						setControl(controlSelected);
//					}
//				}
//			}
//			VisualControl controlSelected = null;
//			if (selectedColumn > -1 && selectedRow > -1){
//				controlSelected = (VisualControl) appControlsTable.getModel().getValueAt(selectedRow, selectedColumn);	
//				if (
//						controlSelected == null 
//						//&& !internalUpdate 
//						&& (selectedColumn != previousSelectedColumn || selectedRow != previousSelectedRow)){
//					//internalUpdate = true;
//					controlSelected = ((AppVisualControlsTableModel)appControlsTable.getModel()).createDefaultControlAt("name", selectedRow, selectedColumn, false);
//					controlSelected.setHideImg(true);
//					appControlsTable.getModel().setValueAt(controlSelected, selectedRow, selectedColumn);
//				}
//			}
			if (selectedColumn > -1 && selectedRow > -1){
				Control controlSelected = ((AppControlsTableModel)appControlsTable.getModel()).getControlAt(selectedRow);
				setControl(controlSelected);
				previousSelectedRow = selectedRow;
				previousSelectedColumn = selectedColumn;
				//internalUpdate = false;
			}
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			Control controlSelected = null;
			int selectedColumn = appControlsTable.getSelectedColumn();
			int selectedRow = appControlsTable.getSelectedRow();
			if (selectedColumn > -1 && selectedRow > -1){
				controlSelected = (Control) appControlsTable.getModel().getValueAt(selectedRow, selectedColumn);	
			}
			setControl(controlSelected);
		}
		
		//private void updateView(){}
	}
	
	public PanelControlKeys(){}
        
	/**
	 * Creates new form PanelControlKeys
	 */
	public PanelControlKeys(JTable appControlsTable) {
		this.appControlsTable = appControlsTable;
		ControlSelectionListener controlSelectionListener = new ControlSelectionListener();
		appControlsTable.getModel().addTableModelListener(controlSelectionListener);
		appControlsTable.getSelectionModel().addListSelectionListener(controlSelectionListener);
		appControlsTable.getColumnModel().getSelectionModel().addListSelectionListener(controlSelectionListener);
		initComponents();
		lblPaging.setHorizontalAlignment(JLabel.CENTER);
	}

	/**
	 * Set the Contol to edit
	 * @param control
	 */
	public void setControl(Control control){
		this.control = control;
		modified = false;
		indexKeysEvents = 0;
		setEnabled(control != null);
		UIUtils.setEnabledRecursive(this, control != null);
		copyToView();
	}
	
	/**
	 * 
	 * @return true if the last {@link KeysEvent} of {@link #control} is not empty
	 */
	private boolean canAddShortcutInControl(){
		return (!control.getKeysEvents().isEmpty() && !control.getKeysEvents().last().getKeys().isEmpty());
	}
	
	public static String getKeysAsString(Control c, int index){
		List<KeysEvent> keysEventsList = new ArrayList(c.getKeysEvents());
		int from = index == -1 ? 0 : index;
		int to = index == -1 ? keysEventsList.size()-1 : index;
		String keysText = new String();
		for (int i = from; i <= to; i++) {
			if (i > from){
				keysText += ",";
			}
			KeysEvent currentKeysEvent = keysEventsList.get(i);
			if (currentKeysEvent.getKeys() != null){
				for(Integer key: currentKeysEvent.getKeys()){
					if (keysText.length() > 0){
						keysText += "+";
					}
					keysText += (KeyEvent.getKeyText(key));
				}
			}
		}
		return keysText;
	}
	
	/**
	 * Update the view accordingly to the {@link #control}
	 */
	private void copyToView(){
		textFieldKeyStroke.setText("");
		lblPaging.setText(null);
		checkBoxKeyAlt.setSelected(false);
		checkBoxKeyCtrl.setSelected(false);
		checkBoxKeyShift.setSelected(false);
		btnPrev.setEnabled(indexKeysEvents > 0);
		btnNext.setText(control == null || indexKeysEvents == control.getKeysEvents().size()-1 ? "+" : ">");
		btnNext.setEnabled(control != null && (indexKeysEvents < control.getKeysEvents().size()-1 || canAddShortcutInControl()));
		if (control != null){
			List<KeysEvent> keysEventsList = new ArrayList(control.getKeysEvents());
			KeysEvent currentKeysEvent = keysEventsList.get(indexKeysEvents);
			String keysText = new String();
			if (currentKeysEvent.getKeys() != null){
				for(Integer key: currentKeysEvent.getKeys()){
					if (key.equals(KeyEvent.VK_ALT)){
						checkBoxKeyAlt.setSelected(true);
					} else if (key.equals(KeyEvent.VK_CONTROL)){
						checkBoxKeyCtrl.setSelected(true);
					} else if (key.equals(KeyEvent.VK_SHIFT)){
						checkBoxKeyShift.setSelected(true);
					}
					if (keysText.length() > 0){
						keysText += "+";
					}
					keysText += (KeyEvent.getKeyText(key));
				}
				textFieldKeyStroke.setText(keysText);
			}
			lblPaging.setText((indexKeysEvents+1) + "/" + control.getKeysEvents().size());
		}
		
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboVirtualKey = new javax.swing.JComboBox();
        textFieldKeyStroke = new javax.swing.JTextField();
        checkBoxKeyCtrl = new javax.swing.JCheckBox();
        checkBoxKeyAlt = new javax.swing.JCheckBox();
        checkBoxKeyShift = new javax.swing.JCheckBox();
        lblComboVirtualKey = new javax.swing.JLabel();
        lblTextFieldKeyStroke = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblPaging = new javax.swing.JLabel();

        comboVirtualKey.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        textFieldKeyStroke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textFieldKeyStrokeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldKeyStrokeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldKeyStrokeKeyTyped(evt);
            }
        });

        checkBoxKeyCtrl.setText("Ctrl");
        checkBoxKeyCtrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxKeyCtrlActionPerformed(evt);
            }
        });

        checkBoxKeyAlt.setText("Alt");
        checkBoxKeyAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxKeyAltActionPerformed(evt);
            }
        });

        checkBoxKeyShift.setText("Shift");
        checkBoxKeyShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxKeyShiftActionPerformed(evt);
            }
        });

        lblComboVirtualKey.setText("Virtual Key:");

        lblTextFieldKeyStroke.setText("Shortcut:");

        btnPrev.setText("<");
        btnPrev.setFocusPainted(false);
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">");
        btnNext.setFocusPainted(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        lblPaging.setText("0/0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextFieldKeyStroke, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblComboVirtualKey, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboVirtualKey, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldKeyStroke, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkBoxKeyShift)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxKeyCtrl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkBoxKeyAlt)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(223, 223, 223)
                        .addComponent(lblPaging, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(checkBoxKeyAlt)
                                    .addComponent(checkBoxKeyCtrl)
                                    .addComponent(comboVirtualKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblComboVirtualKey))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textFieldKeyStroke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(checkBoxKeyShift)
                                    .addComponent(lblTextFieldKeyStroke)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPaging))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnNext, btnPrev});

    }// </editor-fold>//GEN-END:initComponents

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        indexKeysEvents--;
		copyToView();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        indexKeysEvents++;
		if (control.getKeysEvents().size() == indexKeysEvents){
			control.addKeysEvent(new KeysEvent());
		}
		copyToView();
    }//GEN-LAST:event_btnNextActionPerformed

    private void checkBoxKeyCtrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxKeyCtrlActionPerformed
		modified = true;
		checkMetaKey(checkBoxKeyCtrl.isSelected(), KeyEvent.VK_CONTROL);
		copyToView();
    }//GEN-LAST:event_checkBoxKeyCtrlActionPerformed

    private void checkBoxKeyAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxKeyAltActionPerformed
        modified = true;
		checkMetaKey(checkBoxKeyAlt.isSelected(), KeyEvent.VK_ALT);
		copyToView();
    }//GEN-LAST:event_checkBoxKeyAltActionPerformed

    private void checkBoxKeyShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxKeyShiftActionPerformed
		modified = true;
		checkMetaKey(checkBoxKeyShift.isSelected(), KeyEvent.VK_SHIFT);
		copyToView();
    }//GEN-LAST:event_checkBoxKeyShiftActionPerformed

	private void checkMetaKey(boolean selected, Integer keyCode){
		KeysEvent[] keysEventsArray = new KeysEvent[control.getKeysEvents().size()];
		keysEventsArray = control.getKeysEvents().toArray(keysEventsArray);
        if (selected){
 			keysEventsArray[indexKeysEvents].getKeys().add(keyCode);
		} else {
 		   keysEventsArray[indexKeysEvents].getKeys().remove(keyCode);
		}
	}
	
    private void textFieldKeyStrokeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyStrokeKeyTyped
    	//NO
    }//GEN-LAST:event_textFieldKeyStrokeKeyTyped

    private void textFieldKeyStrokeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyStrokeKeyReleased
        //System.out.println("textFieldKeyStrokeKeyTyped");
    	modified = true;
		KeysEvent[] keysEventsArray = new KeysEvent[control.getKeysEvents().size()];
		keysEventsArray = control.getKeysEvents().toArray(keysEventsArray);
		keysEventsArray[indexKeysEvents].getKeys().clear();
		checkMetaKey(checkBoxKeyAlt.isSelected(), KeyEvent.VK_ALT);
		checkMetaKey(checkBoxKeyCtrl.isSelected(), KeyEvent.VK_CONTROL);
		checkMetaKey(checkBoxKeyShift.isSelected(), KeyEvent.VK_SHIFT);
		keysEventsArray[indexKeysEvents].getKeys().add(evt.getKeyCode());
		evt.consume();
		int keyCode = evt.getKeyCode();
		System.out.println("keyChar " + evt.getKeyChar());
		System.out.println("keyCode " + keyCode);
		System.out.println("TextCode " + KeyEvent.getKeyText(keyCode));
		copyToView();
    }//GEN-LAST:event_textFieldKeyStrokeKeyReleased

    private void textFieldKeyStrokeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyStrokeKeyPressed
        //System.out.println("textFieldKeyStrokeKeyPressed");
		
    }//GEN-LAST:event_textFieldKeyStrokeKeyPressed

	
	
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JCheckBox checkBoxKeyAlt;
    private javax.swing.JCheckBox checkBoxKeyCtrl;
    private javax.swing.JCheckBox checkBoxKeyShift;
    private javax.swing.JComboBox comboVirtualKey;
    private javax.swing.JLabel lblComboVirtualKey;
    private javax.swing.JLabel lblPaging;
    private javax.swing.JLabel lblTextFieldKeyStroke;
    private javax.swing.JTextField textFieldKeyStroke;
    // End of variables declaration//GEN-END:variables
}
