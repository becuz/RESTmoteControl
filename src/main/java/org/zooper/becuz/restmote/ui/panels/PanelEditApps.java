/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zooper.becuz.restmote.ui.panels;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.ui.MainWindow;
import org.zooper.becuz.restmote.ui.UIConstants;
import org.zooper.becuz.restmote.ui.UIUtils;
import org.zooper.becuz.restmote.ui.appcontrols.AppControlsTableModel;
import org.zooper.becuz.restmote.ui.appcontrols.ControlRenderer;
import org.zooper.becuz.restmote.ui.appcontrols.ControlsTable;
import org.zooper.becuz.restmote.ui.appcontrols.ImageList;
import org.zooper.becuz.restmote.utils.Utils;

/**
 *
 * @author bebo
 */
@SuppressWarnings("serial")
public class PanelEditApps extends javax.swing.JPanel {

	private boolean modified = false;
	
	private JList listApps;
	
	private DefaultListModel<App> listAppsModel;
	
	 /**
     * 
     */
    private AppControlsTableModel appTableModel = new AppControlsTableModel();
	
	
	public PanelEditApps(JList listApps, DefaultListModel<App> listAppsModel) {
		this();
		this.listApps = listApps;
		this.listAppsModel = listAppsModel;
	}
	
	
	/**
	 * Creates new form PanelEditApps
	 */
	public PanelEditApps() {
		initComponents();
		tableControls.setDefaultRenderer(Control.class, new ControlRenderer());
		tableControls.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void editControl(Control control){
		panelControlKeys.setControl(control);
	}
	
	public void editApp(App app){
        textFieldNameApp.setText(app == null ? "" : app.getName());
        textFieldExtensionsApp.setText(app == null ? "" : Utils.join(app.getExtensions(), ","));
		checkInstanceApp.setSelected(app == null ? false : app.isForceOneInstance());
		textFieldArgFileApp.setText(app == null ? "" : app.getArgumentsFile());
		textFieldArgDirApp.setText(app == null ? "" : app.getArgumentsDir());
		textFieldPathApp.setText(app == null ? "" : app.getPath());
		if (app != null){
			appTableModel.setData(app.getControlsManager());
		} else {
			appTableModel.clearData();
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

        btnEditAppCancel = new javax.swing.JButton();
        btnEditAppSave = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        panelConfiguration = new javax.swing.JPanel();
        lblTextFieldNameApp = new javax.swing.JLabel();
        textFieldNameApp = new javax.swing.JTextField();
        lblTextFieldExtensionsApp = new javax.swing.JLabel();
        textFieldExtensionsApp = new javax.swing.JTextField();
        checkInstanceApp = new javax.swing.JCheckBox();
        lblTextFieldArgFileApp = new javax.swing.JLabel();
        textFieldArgFileApp = new javax.swing.JTextField();
        textFieldArgDirApp = new javax.swing.JTextField();
        lblTextFieldArgDirApp = new javax.swing.JLabel();
        lblTextFieldPathApp = new javax.swing.JLabel();
        textFieldPathApp = new javax.swing.JTextField();
        btnBrowsePath1 = new javax.swing.JButton();
        panelControls = new javax.swing.JPanel();
        scrollPaneTableControls = new javax.swing.JScrollPane();
        tableControls = new ControlsTable();
        tableControls.setModel(appTableModel);
        jScrollPane1 = new javax.swing.JScrollPane();
        listImages = new ImageList(false);
        panelControlKeys = new org.zooper.becuz.restmote.ui.appcontrols.PanelControl(tableControls);
        lblHelpIcons = new javax.swing.JLabel();
        lblHelpTable = new javax.swing.JLabel();

        btnEditAppCancel.setText("Cancel");
        btnEditAppCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditAppCancelActionPerformed(evt);
            }
        });

        btnEditAppSave.setText("Save");
        btnEditAppSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditAppSaveActionPerformed(evt);
            }
        });

        lblTextFieldNameApp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTextFieldNameApp.setText("Name");

        textFieldNameApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNameAppActionPerformed(evt);
            }
        });

        lblTextFieldExtensionsApp.setText("Extensions");

        textFieldExtensionsApp.setToolTipText("Comma separated list of extensions. Example: .mp3, .m3u, .ogg");

        checkInstanceApp.setText("One Instance");

        lblTextFieldArgFileApp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTextFieldArgFileApp.setText("Arg file");

        textFieldArgFileApp.setToolTipText(UIConstants.TOOLTIP_APP_ARGFILE);

        textFieldArgDirApp.setToolTipText(UIConstants.TOOLTIP_APP_ARGFILE);

        lblTextFieldArgDirApp.setText("Arg dir");

        lblTextFieldPathApp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTextFieldPathApp.setText("Path");

        btnBrowsePath1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/folder.png"))); // NOI18N
        btnBrowsePath1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowsePath1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelConfigurationLayout = new javax.swing.GroupLayout(panelConfiguration);
        panelConfiguration.setLayout(panelConfigurationLayout);
        panelConfigurationLayout.setHorizontalGroup(
            panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigurationLayout.createSequentialGroup()
                .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfigurationLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(lblTextFieldPathApp))
                    .addGroup(panelConfigurationLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lblTextFieldArgFileApp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldArgFileApp, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(lblTextFieldArgDirApp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldArgDirApp, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(checkInstanceApp, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(107, Short.MAX_VALUE))
            .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConfigurationLayout.createSequentialGroup()
                    .addGap(39, 39, 39)
                    .addComponent(lblTextFieldNameApp)
                    .addGap(171, 171, 171)
                    .addComponent(lblTextFieldExtensionsApp)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(textFieldExtensionsApp, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(75, 75, 75))
                .addGroup(panelConfigurationLayout.createSequentialGroup()
                    .addGap(80, 80, 80)
                    .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelConfigurationLayout.createSequentialGroup()
                            .addComponent(textFieldNameApp, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelConfigurationLayout.createSequentialGroup()
                            .addComponent(textFieldPathApp, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnBrowsePath1, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)))
                    .addGap(14, 14, 14)))
        );
        panelConfigurationLayout.setVerticalGroup(
            panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfigurationLayout.createSequentialGroup()
                .addContainerGap(194, Short.MAX_VALUE)
                .addComponent(lblTextFieldPathApp)
                .addGap(11, 11, 11)
                .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldArgFileApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTextFieldArgDirApp)
                        .addComponent(lblTextFieldArgFileApp))
                    .addComponent(textFieldArgDirApp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkInstanceApp))
                .addGap(174, 174, 174))
            .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConfigurationLayout.createSequentialGroup()
                    .addGap(164, 164, 164)
                    .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTextFieldNameApp)
                        .addComponent(textFieldNameApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTextFieldExtensionsApp)
                        .addComponent(textFieldExtensionsApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(textFieldPathApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBrowsePath1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(206, Short.MAX_VALUE)))
        );

        jTabbedPane.addTab("Configuration", panelConfiguration);

        tableControls.setCellSelectionEnabled(true);
        tableControls.setRowHeight(32);
        tableControls.setSelectionBackground(new java.awt.Color(204, 255, 204));
        tableControls.setTableHeader(null);
        scrollPaneTableControls.setViewportView(tableControls);

        listImages.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listImages.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        jScrollPane1.setViewportView(listImages);

        lblHelpIcons.setText("Drag'n'drop the icons on the table below to add a Control");

        lblHelpTable.setText("Select a Control in the table to edit his shortcut(s)");

        javax.swing.GroupLayout panelControlsLayout = new javax.swing.GroupLayout(panelControls);
        panelControls.setLayout(panelControlsLayout);
        panelControlsLayout.setHorizontalGroup(
            panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(scrollPaneTableControls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .addComponent(lblHelpIcons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelControlKeys, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHelpTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelControlsLayout.setVerticalGroup(
            panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblHelpIcons)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblHelpTable)
                .addGap(3, 3, 3)
                .addComponent(scrollPaneTableControls, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelControlKeys, javax.swing.GroupLayout.PREFERRED_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Controls", panelControls);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(455, Short.MAX_VALUE)
                .addComponent(btnEditAppSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditAppCancel)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(448, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnEditAppCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditAppSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 41, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditAppCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAppCancelActionPerformed
        //TODO il panel edit non scrive sul modello, ma il panel dei controls si
		listApps.clearSelection();
    }//GEN-LAST:event_btnEditAppCancelActionPerformed

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		UIUtils.setEnabledRecursive(panelConfiguration, enabled);
		listImages.setEnabled(enabled);
		tableControls.setEnabled(enabled);
		lblHelpIcons.setEnabled(enabled);
		lblHelpTable.setEnabled(enabled);
		if (!enabled){
			UIUtils.setEnabledRecursive(panelControlKeys, false);
		}
	}
	
	
	
    private void btnEditAppSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAppSaveActionPerformed
		int selectedIndex = listApps.getSelectedIndex();
        App app = selectedIndex == -1 ? null : listAppsModel.getElementAt(selectedIndex);
        if (app != null){
            app.setName(textFieldNameApp.getText());
            //app.setDescription(textFieldDescriptionCategory.getText());
            app.setExtensions(new HashSet<>(Arrays.asList(textFieldExtensionsApp.getText().split(","))));
            app.setForceOneInstance(checkInstanceApp.isSelected());
            app.setArgumentsFile(textFieldArgFileApp.getText());
            app.setArgumentsDir(textFieldArgDirApp.getText());
            app.setPath(textFieldPathApp.getText());
			//TODO il panel edit non scrive sul modello, ma il panel dei controls si
			app.getControlsManager().getControls().clear();
			for (int i = 0; i < tableControls.getRowCount(); i++) {
				for (int j = 0; j < tableControls.getColumnCount(); j++) {
					Control c = (Control) tableControls.getModel().getValueAt(i, j);
					if (c != null){
						c.clean();
						if (!c.isEmpty()){
							try {
								c.validate();
							} catch (IllegalArgumentException e){
								//TODO 
							}
							app.getControlsManager().addControl(c);
						}
					}
				}
			}
        }
        listApps.clearSelection();
    }//GEN-LAST:event_btnEditAppSaveActionPerformed

    private void btnBrowsePath1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowsePath1ActionPerformed
        JFileChooser fc = MainWindow.getFc();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
			textFieldPathApp.setText(file.getPath());
        }
    }//GEN-LAST:event_btnBrowsePath1ActionPerformed

    private void textFieldNameAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldNameAppActionPerformed
        modified = false;
    }//GEN-LAST:event_textFieldNameAppActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowsePath1;
    private javax.swing.JButton btnEditAppCancel;
    private javax.swing.JButton btnEditAppSave;
    private javax.swing.JCheckBox checkInstanceApp;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel lblHelpIcons;
    private javax.swing.JLabel lblHelpTable;
    private javax.swing.JLabel lblTextFieldArgDirApp;
    private javax.swing.JLabel lblTextFieldArgFileApp;
    private javax.swing.JLabel lblTextFieldExtensionsApp;
    private javax.swing.JLabel lblTextFieldNameApp;
    private javax.swing.JLabel lblTextFieldPathApp;
    private javax.swing.JList listImages;
    private javax.swing.JPanel panelConfiguration;
    private org.zooper.becuz.restmote.ui.appcontrols.PanelControl panelControlKeys;
    private javax.swing.JPanel panelControls;
    private javax.swing.JScrollPane scrollPaneTableControls;
    private javax.swing.JTable tableControls;
    private javax.swing.JTextField textFieldArgDirApp;
    private javax.swing.JTextField textFieldArgFileApp;
    private javax.swing.JTextField textFieldExtensionsApp;
    private javax.swing.JTextField textFieldNameApp;
    private javax.swing.JTextField textFieldPathApp;
    // End of variables declaration//GEN-END:variables
}
