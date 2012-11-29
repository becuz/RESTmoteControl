/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zooper.becuz.restmote.ui.panels;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import org.zooper.becuz.restmote.business.ActiveAppBusiness;
import org.zooper.becuz.restmote.http.InetAddr;
import org.zooper.becuz.restmote.http.Server;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
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
	
	/**
	 * Reference to the listAppsModel
	 */
	private DefaultListModel<App> listAppsModel;
	
	/**
     * Swing list model for {@link #comboInetNames} 
     */
    private DefaultComboBoxModel<String> listWindowsModel = new DefaultComboBoxModel<String>();
	
	/**
     *  
     */
    private AppControlsTableModel appTableModel = new AppControlsTableModel();
	
	/**
	 * 
	 * @param listApps
	 * @param listAppsModel 
	 */
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
		buildListWindowsModel();
	}
	
	/**
	 * Populate listInetNamesModel
	 * @param selectInetName 
	 */
	private void buildListWindowsModel(){
		listWindowsModel.removeAllElements();
		List<ActiveApp> activeApps = new ActiveAppBusiness().getActiveApps(true);
		for(ActiveApp activeApp: activeApps){
			listWindowsModel.addElement(activeApp.getName());
			//TODO listWindowsModel.setSelectedItem("");
		}
	}
	
	private void setSelectedWindow(String windowName){
		if (windowName != null){
			if (listWindowsModel.getIndexOf(windowName) == -1){
				listWindowsModel.addElement(windowName);
			}
		}
		listWindowsModel.setSelectedItem(windowName);
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
		setSelectedWindow(app == null ? null : app.getWindowName());
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
        comboWindowName = new javax.swing.JComboBox();
        lblComboWindowName = new javax.swing.JLabel();
        btnRefreshWindows = new javax.swing.JButton();
        panelControls = new javax.swing.JPanel();
        scrollPaneTableControls = new javax.swing.JScrollPane();
        tableControls = new ControlsTable();
        tableControls.setModel(appTableModel);
        jScrollPane1 = new javax.swing.JScrollPane();
        listImages = new ImageList(false);
        panelControlKeys = new org.zooper.becuz.restmote.ui.appcontrols.PanelControl(tableControls);
        lblHelpIcons = new javax.swing.JLabel();
        lblHelpTable = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Edit"));

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

        lblTextFieldNameApp.setFont(lblTextFieldNameApp.getFont().deriveFont(lblTextFieldNameApp.getFont().getStyle() | java.awt.Font.BOLD));
        lblTextFieldNameApp.setText("Name:");
        lblTextFieldNameApp.setToolTipText(UIConstants.TOOLTIP_APP_NAME);

        textFieldNameApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNameAppActionPerformed(evt);
            }
        });

        lblTextFieldExtensionsApp.setText("Extensions");
        lblTextFieldExtensionsApp.setToolTipText(UIConstants.TOOLTIP_APP_EXTENSIONS);

        textFieldExtensionsApp.setToolTipText("Comma separated list of extensions. Example: .mp3, .m3u, .ogg");

        checkInstanceApp.setText("One Instance");

        lblTextFieldArgFileApp.setFont(lblTextFieldArgFileApp.getFont().deriveFont(lblTextFieldArgFileApp.getFont().getStyle() | java.awt.Font.BOLD));
        lblTextFieldArgFileApp.setText("Arg file:");
        lblTextFieldArgFileApp.setToolTipText(UIConstants.TOOLTIP_APP_ARGFILE);

        lblTextFieldArgDirApp.setText("Arg dir");
        lblTextFieldArgDirApp.setToolTipText(UIConstants.TOOLTIP_APP_ARGDIR);

        lblTextFieldPathApp.setFont(lblTextFieldPathApp.getFont().deriveFont(lblTextFieldPathApp.getFont().getStyle() | java.awt.Font.BOLD));
        lblTextFieldPathApp.setText("Path:");
        lblTextFieldPathApp.setToolTipText(UIConstants.TOOLTIP_APP_PATH);

        textFieldPathApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPathAppActionPerformed(evt);
            }
        });

        btnBrowsePath1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/folder.png"))); // NOI18N
        btnBrowsePath1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowsePath1ActionPerformed(evt);
            }
        });

        comboWindowName.setModel(listWindowsModel);

        lblComboWindowName.setFont(lblComboWindowName.getFont().deriveFont(lblComboWindowName.getFont().getStyle() | java.awt.Font.BOLD));
        lblComboWindowName.setText("Window:");
        lblComboWindowName.setToolTipText(UIConstants.TOOLTIP_APP_WINDOW);

        btnRefreshWindows.setIcon(UIUtils.ICON_REFRESH);
        btnRefreshWindows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshWindowsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelConfigurationLayout = new javax.swing.GroupLayout(panelConfiguration);
        panelConfiguration.setLayout(panelConfigurationLayout);
        panelConfigurationLayout.setHorizontalGroup(
            panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigurationLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTextFieldArgFileApp)
                    .addComponent(lblTextFieldPathApp)
                    .addComponent(lblTextFieldNameApp)
                    .addComponent(lblComboWindowName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboWindowName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelConfigurationLayout.createSequentialGroup()
                        .addComponent(textFieldArgFileApp, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTextFieldArgDirApp)
                        .addGap(18, 18, 18)
                        .addComponent(textFieldArgDirApp, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkInstanceApp))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfigurationLayout.createSequentialGroup()
                        .addComponent(textFieldNameApp, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTextFieldExtensionsApp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldExtensionsApp, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textFieldPathApp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBrowsePath1, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(btnRefreshWindows, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        panelConfigurationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBrowsePath1, btnRefreshWindows});

        panelConfigurationLayout.setVerticalGroup(
            panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigurationLayout.createSequentialGroup()
                .addContainerGap(148, Short.MAX_VALUE)
                .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldNameApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTextFieldNameApp)
                    .addComponent(lblTextFieldExtensionsApp)
                    .addComponent(textFieldExtensionsApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboWindowName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComboWindowName)
                    .addComponent(btnRefreshWindows))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBrowsePath1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldPathApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTextFieldPathApp)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldArgFileApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTextFieldArgFileApp)
                    .addComponent(lblTextFieldArgDirApp)
                    .addComponent(textFieldArgDirApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkInstanceApp))
                .addGap(195, 195, 195))
        );

        panelConfigurationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBrowsePath1, btnRefreshWindows});

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
                    .addComponent(scrollPaneTableControls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addComponent(lblHelpIcons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelControlKeys, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHelpTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelControlsLayout.setVerticalGroup(
            panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlsLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(lblHelpIcons)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblHelpTable)
                .addGap(3, 3, 3)
                .addComponent(scrollPaneTableControls, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelControlKeys, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Controls", panelControls);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(493, Short.MAX_VALUE)
                .addComponent(btnEditAppSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditAppCancel)
                .addGap(6, 6, 6))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 447, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditAppCancel)
                    .addComponent(btnEditAppSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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

    private void textFieldPathAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPathAppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldPathAppActionPerformed

    private void btnRefreshWindowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshWindowsActionPerformed
        buildListWindowsModel();
		int selectedIndex = listApps.getSelectedIndex();
		setSelectedWindow(listAppsModel.getElementAt(selectedIndex).getWindowName());
    }//GEN-LAST:event_btnRefreshWindowsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowsePath1;
    private javax.swing.JButton btnEditAppCancel;
    private javax.swing.JButton btnEditAppSave;
    private javax.swing.JButton btnRefreshWindows;
    private javax.swing.JCheckBox checkInstanceApp;
    private javax.swing.JComboBox comboWindowName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel lblComboWindowName;
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
