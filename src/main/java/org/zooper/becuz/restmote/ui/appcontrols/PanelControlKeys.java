/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zooper.becuz.restmote.ui.appcontrols;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlInterface;
import org.zooper.becuz.restmote.model.KeysEvent;
import org.zooper.becuz.restmote.ui.UIConstants;
import org.zooper.becuz.restmote.ui.UIUtils;
import org.zooper.becuz.restmote.ui.panels.PanelPersistable;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * Panel to view and edit the shortcut of a {@link Control}
 * @author bebo
 */
@SuppressWarnings("serial")
public class PanelControlKeys extends javax.swing.JPanel implements EditControl {

	private Logger logger = Logger.getLogger(PanelControlKeys.class.getName());
	
	/**
	 * model Control
	 */
	private Control control;
	
	/**
	 * 
	 */
	private List<KeysEvent> keysEvents;
	
	/**
	 * current index of {@link Control#getKeysEvents()}
	 */
	private int indexKeysEvents;
	
	/**
	 * 
	 */
	private JTable appControlsTable;
	
	/**
	 * 
	 */
	private PanelPersistable panelEditApp;
	
	/**
     * Swing list model for {@link #comboVirtualKey} 
     */
    private DefaultComboBoxModel<String> listVirtualKeyModel = new DefaultComboBoxModel<String>();
	
	public PanelControlKeys(){}
        
	/**
	 * Creates new form PanelControlKeys
	 */
	public PanelControlKeys(PanelPersistable panelEditApp, JTable appControlsTable) {
		this.panelEditApp = panelEditApp;
		this.appControlsTable = appControlsTable;
		initComponents();
		postInitComponents();
	}
	
	/**
	 * called after {@link #initComponents()}
	 */
	private void postInitComponents(){
		lblPaging.setHorizontalAlignment(JLabel.CENTER);
		listVirtualKeyModel.addElement("");
		Field[] fields = KeyEvent.class.getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().startsWith("VK_")) {
				listVirtualKeyModel.addElement(f.getName());
			}
		}
	}

	/**
	 * Set the Contol to edit
	 * @param control
	 */
	@Override
	public void setControl(ControlInterface controlInterface){
		listVirtualKeyModel.setSelectedItem("");
		this.control = (Control)controlInterface;
		this.keysEvents = control == null ? null : new ArrayList<KeysEvent>();
		if (control != null){
			for(KeysEvent keysEvent: control.getKeysEvents()){
				KeysEvent copyKeysEvent = new KeysEvent();
				Set<Integer> copyKeys = new LinkedHashSet<Integer>();
				for(Integer k: keysEvent.getKeys()){
					copyKeys.add(new Integer(k));
				}
				copyKeysEvent.setKeys(copyKeys);
				//copyKeysEvent.setId(keysEvent.getId());
				keysEvents.add(copyKeysEvent);
			}
		}
		indexKeysEvents = 0;
		setEnabled(control != null);
		btnSave.setEnabled(false);
		copyToView();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		UIUtils.setEnabledRecursive(this, enabled);
	}
	
	/**
	 * 
	 */
	private boolean canAddShortcutInControl(){
		//return (!control.getKeysEvents().isEmpty() && !control.getKeysEvents().last().getKeys().isEmpty());
		return keysEvents != null && !keysEvents.get(keysEvents.size()-1).getKeys().isEmpty();
	}
	
	/**
	 * Update the view accordingly to the {@link #keysEvents}
	 */
	private void copyToView(){
		textFieldKeyStroke.setText("");
		lblPaging.setText(null);
		checkBoxKeyAlt.setSelected(false);
		checkBoxKeyCtrl.setSelected(false);
		checkBoxKeyShift.setSelected(false);
		btnPrev.setEnabled(indexKeysEvents > 0);
		btnNext.setIcon(keysEvents == null || indexKeysEvents == keysEvents.size()-1 ? UIUtils.ICON_ADD : UIUtils.ICON_RIGHT);
		btnNext.setToolTipText(keysEvents == null || indexKeysEvents == keysEvents.size()-1 ? UIConstants.TOOLTIP_APP_CONTROLS_KEYS_ADD : UIConstants.TOOLTIP_APP_CONTROLS_KEYS_NEXT);
		btnNext.setEnabled(keysEvents != null && (indexKeysEvents < keysEvents.size()-1 || canAddShortcutInControl()));
		if (keysEvents != null){
			KeysEvent currentKeysEvent = keysEvents.get(indexKeysEvents);
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
			lblPaging.setText((indexKeysEvents+1) + "/" + keysEvents.size());
		}
	}
	
	private void copyToModel(){
		control.setKeysEvents(null);
		for(KeysEvent keysEvent: keysEvents){
			control.addKeysEvent(keysEvent);
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
        btnSave = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        comboVirtualKey.setModel(listVirtualKeyModel);
        comboVirtualKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVirtualKeyActionPerformed(evt);
            }
        });

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

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/arrow_left.png"))); // NOI18N
        btnPrev.setToolTipText(UIConstants.TOOLTIP_APP_CONTROLS_KEYS_PREV);
        btnPrev.setFocusPainted(false);
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/arrow_right.png"))); // NOI18N
        btnNext.setToolTipText("");
        btnNext.setFocusPainted(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        lblPaging.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPaging.setText("0/0");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jButton2.setIcon(UIUtils.ICON_DELETE);
        jButton2.setToolTipText(UIConstants.TOOLTIP_APP_CONTROLS_KEYS_DELETE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextFieldKeyStroke, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblComboVirtualKey, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(btnSave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblPaging, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboVirtualKey, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldKeyStroke, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkBoxKeyShift)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxKeyCtrl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkBoxKeyAlt)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnNext, btnPrev});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboVirtualKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblComboVirtualKey))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldKeyStroke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTextFieldKeyStroke)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkBoxKeyAlt)
                            .addComponent(checkBoxKeyCtrl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkBoxKeyShift))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblPaging))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSave))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnNext, btnPrev});

    }// </editor-fold>//GEN-END:initComponents

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        indexKeysEvents--;
		copyToView();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        indexKeysEvents++;
		if (keysEvents.size() == indexKeysEvents){
			keysEvents.add(new KeysEvent());
		}
		copyToView();
    }//GEN-LAST:event_btnNextActionPerformed

	private void setModified(boolean modified){
		btnSave.setEnabled(modified);
	}
	
    private void checkBoxKeyCtrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxKeyCtrlActionPerformed
		checkMetaKey(checkBoxKeyCtrl.isSelected(), KeyEvent.VK_CONTROL);
		copyToView();
    }//GEN-LAST:event_checkBoxKeyCtrlActionPerformed

    private void checkBoxKeyAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxKeyAltActionPerformed
		checkMetaKey(checkBoxKeyAlt.isSelected(), KeyEvent.VK_ALT);
		copyToView();
    }//GEN-LAST:event_checkBoxKeyAltActionPerformed

    private void checkBoxKeyShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxKeyShiftActionPerformed
		checkMetaKey(checkBoxKeyShift.isSelected(), KeyEvent.VK_SHIFT);
		copyToView();
    }//GEN-LAST:event_checkBoxKeyShiftActionPerformed

	private void checkMetaKey(boolean selected, Integer keyCode){
        if (selected){
			keysEvents.get(indexKeysEvents).getKeys().add(keyCode);
		} else {
			keysEvents.get(indexKeysEvents).getKeys().remove(keyCode);
		}
		setModified(true);
	}
	
    private void textFieldKeyStrokeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyStrokeKeyTyped
    	evt.consume();
		keysEvents.get(indexKeysEvents).getKeys().clear();
    }//GEN-LAST:event_textFieldKeyStrokeKeyTyped

    private void textFieldKeyStrokeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyStrokeKeyReleased
    	keysEvents.get(indexKeysEvents).getKeys().add(evt.getKeyCode());
		evt.consume();
		setModified(true);
		copyToView();
    }//GEN-LAST:event_textFieldKeyStrokeKeyReleased

    private void textFieldKeyStrokeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyStrokeKeyPressed
        
    }//GEN-LAST:event_textFieldKeyStrokeKeyPressed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        panelEditApp.setModified(true);
		copyToModel();
		appControlsTable.clearSelection();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void comboVirtualKeyActionPerformed(java.awt.event.ActionEvent evt) {//GENf-FIRST:event_comboVirtualKeyActionPerformed
        try {
			String keyName = (String) listVirtualKeyModel.getSelectedItem();
			if (!Utils.isEmpty(keyName)){
				Field field = KeyEvent.class.getDeclaredField(keyName);
				int code = ((Integer) field.get(null)).intValue();
				keysEvents.get(indexKeysEvents).getKeys().clear();
				keysEvents.get(indexKeysEvents).getKeys().add(code);
				setModified(true);
				copyToView();
			}
		} catch (Exception e){
			logger.error("", e);
		}
    }//GEN-LAST:event_comboVirtualKeyActionPerformed

	
	
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox checkBoxKeyAlt;
    private javax.swing.JCheckBox checkBoxKeyCtrl;
    private javax.swing.JCheckBox checkBoxKeyShift;
    private javax.swing.JComboBox comboVirtualKey;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel lblComboVirtualKey;
    private javax.swing.JLabel lblPaging;
    private javax.swing.JLabel lblTextFieldKeyStroke;
    private javax.swing.JTextField textFieldKeyStroke;
    // End of variables declaration//GEN-END:variables
}
