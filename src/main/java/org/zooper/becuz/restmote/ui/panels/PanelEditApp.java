package org.zooper.becuz.restmote.ui.panels;

import java.awt.Rectangle;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;

import org.zooper.becuz.restmote.business.ActiveAppBusiness;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlsManager;
import org.zooper.becuz.restmote.model.VisualControl;
import org.zooper.becuz.restmote.model.VisualControlsManager;
import org.zooper.becuz.restmote.model.interfaces.Persistable;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.ui.MainWindow;
import org.zooper.becuz.restmote.ui.UIConstants;
import org.zooper.becuz.restmote.ui.UIUtils;
import org.zooper.becuz.restmote.ui.appcontrols.AppControlsTableModel;
import org.zooper.becuz.restmote.ui.appcontrols.AppVisualControlsTableModel;
import org.zooper.becuz.restmote.ui.appcontrols.ControlSelectionListener;
import org.zooper.becuz.restmote.ui.appcontrols.ControlTransferHandler;
import org.zooper.becuz.restmote.ui.appcontrols.HasControl;
import org.zooper.becuz.restmote.ui.appcontrols.ImageList;
import org.zooper.becuz.restmote.ui.appcontrols.VisualControlRenderer;
import org.zooper.becuz.restmote.utils.Utils;

/**
 *
 * @author bebo
 */
@SuppressWarnings("serial")
public class PanelEditApp extends PanelPersistable {

	private Logger logger = Logger.getLogger(PanelEditApp.class.getName());
	
	/**
     * Swing list model for {@link #comboInetNames} 
     */
    private DefaultComboBoxModel<String> listWindowsModel = new DefaultComboBoxModel<String>();
	
	/**
	 * Model for app.getControls()
	 */
	private AppControlsTableModel appControlsTableModel = new AppControlsTableModel();
	
	/**
     * Model for app.getVisualControls()  
     */
    private AppVisualControlsTableModel appVisualControlsTableModel = new AppVisualControlsTableModel();
	

	public PanelEditApp(PanelListPersistable panelListPersistable) {
		this();
		this.panelListPersistable = panelListPersistable;
	}
	
	/**
	 * Creates new form PanelEditAppttt
	 */
	public PanelEditApp() {
		initComponents();
		jComponentsToObserve = new JComponent[]{
				textFieldNameApp, textFieldExtensionsApp, checkInstanceApp,
				textFieldArgFileApp, textFieldArgDirApp, textFieldPathApp, comboWindowName};
		tableControls.setDragEnabled(true);
		tableControls.setTransferHandler(new ControlTransferHandler());
		ControlSelectionListener controlSelectionListener = new ControlSelectionListener(tableControls, panelControlKeys){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				super.valueChanged(e);
				btnDeleteControl.setEnabled(tableControls.getSelectedRowCount() > 0);
			};
			@Override
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				btnDeleteControl.setEnabled(tableControls.getSelectedRowCount() > 0);
			}
		};
		tableControls.getModel().addTableModelListener(controlSelectionListener);
		tableControls.getSelectionModel().addListSelectionListener(controlSelectionListener);
		tableControls.getColumnModel().getSelectionModel().addListSelectionListener(controlSelectionListener);
		tableVisualControls.setDefaultRenderer(Control.class, new VisualControlRenderer());
		tableVisualControls.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		controlSelectionListener = new ControlSelectionListener(tableVisualControls, panelVisualControl){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				super.valueChanged(e);
				if (e.getValueIsAdjusting()) {
					return;
				}
				int selectedColumn = tableVisualControls.getSelectedColumn();
				int selectedRow = tableVisualControls.getSelectedRow();
				if (selectedColumn > -1 && selectedRow > -1){
					VisualControl visualControl = 
							(VisualControl) ((HasControl)tableVisualControls.getModel()).getControlAt(selectedRow, selectedColumn);
					if (visualControl != null){
						Control control = visualControl.getControl();
						//TODO visualControl.getControl()) is null
						logger.info("Control retrieved from selected visualControl " + control);
						int[] pos = ((HasControl)tableControls.getModel()).getControlPosition(control);
						if (pos[0] != -1){
							Rectangle rect = tableControls.getCellRect(pos[0], 1, true);
							tableControls.scrollRectToVisible(rect);
							tableControls.changeSelection(pos[0], 0, false, false);
						}
					}
				}
			};
		};
		tableVisualControls.getModel().addTableModelListener(controlSelectionListener);
		tableVisualControls.getSelectionModel().addListSelectionListener(controlSelectionListener);
		tableVisualControls.getColumnModel().getSelectionModel().addListSelectionListener(controlSelectionListener);
		buildListWindowsModel();
		activateViewChangesListener();
	}
	
	/**
	 * Populate listInetNamesModel
	 * @param selectInetName 
	 */
	private void buildListWindowsModel(){
		listWindowsModel.removeAllElements();
		listWindowsModel.addElement("");
		List<ActiveApp> activeApps = new ActiveAppBusiness().getActiveApps(true);
		for(ActiveApp activeApp: activeApps){
			if (listWindowsModel.getIndexOf(activeApp.getName()) == -1){
				listWindowsModel.addElement(activeApp.getName());
			}
		}	
	}
	
	private void setSelectedWindow(String windowName){
		if (windowName != null){
			if (listWindowsModel.getIndexOf(windowName) == -1){
				listWindowsModel.addElement(windowName);
			}
		}
		listWindowsModel.setSelectedItem(windowName == null ? "" : windowName);
	}
	
	@Override
	public void edit(Persistable p){
		super.edit(p);
		App app = (App) p;
		if (app.getVisualControlsManager().getControls() != null && app.getVisualControlsManager().getControls().size() > 0){
			logger.info("First visualControl.getControl(): " + app.getVisualControlsManager().getControls().iterator().next().getControl());
		}
        textFieldNameApp.setText(app == null ? "" : app.getName());
        textFieldExtensionsApp.setText(app == null ? "" : Utils.join(app.getExtensions(), ","));
		checkInstanceApp.setSelected(app == null ? false : app.getForceOneInstance());
		textFieldArgFileApp.setText(app == null ? "" : app.getArgumentsFile());
		textFieldArgDirApp.setText(app == null ? "" : app.getArgumentsDir());
		textFieldPathApp.setText(app == null ? "" : app.getPath());
		if (app != null){
			appControlsTableModel.setData(app.getControlsManager().getControls());
			appVisualControlsTableModel.setData(app.getVisualControlsManager().getControls());
		} else {
			appControlsTableModel.clearData();
			appVisualControlsTableModel.clearData();
		}
		setSelectedWindow(app == null ? null : app.getWindowName());
		listenViewChanges = true;
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
        tableControls = new javax.swing.JTable();
        scrollPaneTableVisualControls = new javax.swing.JScrollPane();
        tableVisualControls = new org.zooper.becuz.restmote.ui.appcontrols.VisualControlsTable();
        tableVisualControls.setModel(appVisualControlsTableModel);
        scrollPaneListImages = new javax.swing.JScrollPane();
        listImages = new ImageList(false);
        panelControlKeys = new org.zooper.becuz.restmote.ui.appcontrols.PanelControlKeys(tableControls);
        lblHelpIcons = new javax.swing.JLabel();
        panelVisualControl = new org.zooper.becuz.restmote.ui.appcontrols.PanelVisualControl(tableVisualControls);
        btnDeleteControl = new javax.swing.JButton();
        btnAddControl = new javax.swing.JButton();

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

        lblTextFieldExtensionsApp.setText("Extensions");
        lblTextFieldExtensionsApp.setToolTipText(UIConstants.TOOLTIP_APP_EXTENSIONS);

        textFieldExtensionsApp.setToolTipText("Comma separated list of extensions. Example: .mp3, .m3u, .ogg");

        checkInstanceApp.setText("One Instance");
        checkInstanceApp.setToolTipText(UIConstants.TOOLTIP_APP_ONEINSTANCE);

        lblTextFieldArgFileApp.setFont(lblTextFieldArgFileApp.getFont().deriveFont(lblTextFieldArgFileApp.getFont().getStyle() | java.awt.Font.BOLD));
        lblTextFieldArgFileApp.setText("Arg file:");
        lblTextFieldArgFileApp.setToolTipText(UIConstants.TOOLTIP_APP_ARGFILE);

        lblTextFieldArgDirApp.setText("Arg dir");
        lblTextFieldArgDirApp.setToolTipText(UIConstants.TOOLTIP_APP_ARGDIR);

        lblTextFieldPathApp.setFont(lblTextFieldPathApp.getFont().deriveFont(lblTextFieldPathApp.getFont().getStyle() | java.awt.Font.BOLD));
        lblTextFieldPathApp.setText("Path:");
        lblTextFieldPathApp.setToolTipText(UIConstants.TOOLTIP_APP_PATH);

        btnBrowsePath1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/folder.png"))); // NOI18N
        btnBrowsePath1.setToolTipText(UIConstants.TOOLTIP_APP_BROWSE);
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
        btnRefreshWindows.setToolTipText(UIConstants.TOOLTIP_APP_REFRESH);
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
                .addContainerGap(189, Short.MAX_VALUE))
        );

        panelConfigurationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBrowsePath1, btnRefreshWindows});

        panelConfigurationLayout.setVerticalGroup(
            panelConfigurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfigurationLayout.createSequentialGroup()
                .addContainerGap(189, Short.MAX_VALUE)
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

        tableControls.setModel(appControlsTableModel);
        tableControls.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollPaneTableControls.setViewportView(tableControls);

        tableVisualControls.setCellSelectionEnabled(true);
        tableVisualControls.setRowHeight(32);
        tableVisualControls.setSelectionBackground(new java.awt.Color(204, 255, 204));
        tableVisualControls.setTableHeader(null);
        scrollPaneTableVisualControls.setViewportView(tableVisualControls);

        listImages.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listImages.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        scrollPaneListImages.setViewportView(listImages);

        lblHelpIcons.setText("Drag'n'drop in the cells below, firstly shortcuts from the upper table, and then icons from the right upper table.");

        btnDeleteControl.setIcon(UIUtils.ICON_DELETE);
        btnDeleteControl.setToolTipText(UIConstants.TOOLTIP_APP_CONTROLS_DELETE);
        btnDeleteControl.setEnabled(false);
        btnDeleteControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteControlActionPerformed(evt);
            }
        });

        btnAddControl.setIcon(UIUtils.ICON_ADD);
        btnAddControl.setToolTipText(UIConstants.TOOLTIP_APP_CONTROLS_ADD);
        btnAddControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddControlActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelControlsLayout = new javax.swing.GroupLayout(panelControls);
        panelControls.setLayout(panelControlsLayout);
        panelControlsLayout.setHorizontalGroup(
            panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelControlsLayout.createSequentialGroup()
                        .addComponent(scrollPaneTableControls)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddControl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDeleteControl, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(scrollPaneTableVisualControls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                    .addComponent(lblHelpIcons, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                    .addGroup(panelControlsLayout.createSequentialGroup()
                        .addComponent(panelControlKeys, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPaneListImages))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelControlsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelVisualControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelControlsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddControl, btnDeleteControl});

        panelControlsLayout.setVerticalGroup(
            panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelControlsLayout.createSequentialGroup()
                        .addComponent(btnAddControl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteControl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 89, Short.MAX_VALUE))
                    .addComponent(scrollPaneTableControls, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPaneListImages)
                    .addComponent(panelControlKeys, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addComponent(lblHelpIcons)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneTableVisualControls, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelVisualControl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        panelControlsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddControl, btnDeleteControl});

        jTabbedPane.addTab("Controls", panelControls);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(633, Short.MAX_VALUE)
                .addComponent(btnEditAppSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditAppCancel))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 523, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditAppCancel)
                    .addComponent(btnEditAppSave)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jTabbedPane)
                    .addGap(35, 35, 35)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditAppCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAppCancelActionPerformed
		panelListPersistable.clearSelection();
    }//GEN-LAST:event_btnEditAppCancelActionPerformed

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		UIUtils.setEnabledRecursive(panelConfiguration, enabled);
		listImages.setEnabled(enabled);
		tableVisualControls.setEnabled(enabled);
		lblHelpIcons.setEnabled(enabled);
	}
	
	
	
    private void btnEditAppSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAppSaveActionPerformed
        Persistable p = panelListPersistable.getSelectedItem();
		if (p != null){
            App app = (App) p;
			app.setName(textFieldNameApp.getText());
            //app.setDescription(textFieldDescriptionCategory.getText());
            app.setExtensions(new HashSet<>(Arrays.asList(textFieldExtensionsApp.getText().split(","))));
            app.setForceOneInstance(checkInstanceApp.isSelected());
            app.setArgumentsFile(textFieldArgFileApp.getText());
            app.setArgumentsDir(textFieldArgDirApp.getText());
            app.setPath(textFieldPathApp.getText());
			if (!Utils.isEmpty(app.getPath())){
				Map<String, String> env = System.getenv();
				for(String commonPath: PcControllerFactory.getPcController().getBinDefaultPaths()){
					if (commonPath.startsWith("%")){
						String s = commonPath.substring(1, commonPath.length()-1);
						for(String k: env.keySet()){
							if (k.equalsIgnoreCase(s.toUpperCase())){
								commonPath = env.get(k);
								break;
							}
						}
					}
					if (!Utils.isEmpty(commonPath)){
						if (app.getPath().startsWith(commonPath)){
							String relativePath = app.getPath().substring(commonPath.length());
							if (relativePath.startsWith(""+File.separatorChar)){
								relativePath = relativePath.substring(1);
							}
							app.setRelativePath(relativePath);
							break;
						}
					}
				}
			}
			app.setWindowName(comboWindowName.getSelectedItem().toString());
			ControlsManager controlsManager = app.getControlsManager();
			VisualControlsManager visualControlsManager = app.getVisualControlsManager();
			controlsManager.clear();
			visualControlsManager.clear();
			for (int i = 0; i < tableControls.getRowCount(); i++) {
				Control c = (Control) ((AppControlsTableModel)tableControls.getModel()).getControlAt(i, -1);
				if (c != null){
					c.clean();
					if (!c.isEmpty()){
						try {
							c.validate();
						} catch (IllegalArgumentException e){
							//TODO 
						}
						controlsManager.addControl(c);
					}
				}
			}
			for (int i = 0; i < tableVisualControls.getRowCount(); i++) {
				for (int j = 0; j < tableVisualControls.getColumnCount(); j++) {
					VisualControl c = (VisualControl) tableVisualControls.getModel().getValueAt(i, j);
					if (c != null){
						try {
							c.validate();
						} catch (IllegalArgumentException e){
							//TODO 
						}
						visualControlsManager.addControl(c);
					}
				}
			}
        }
        panelListPersistable.clearSelection();
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

    private void btnRefreshWindowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshWindowsActionPerformed
        buildListWindowsModel();
		setSelectedWindow(((App)panelListPersistable.getSelectedItem()).getWindowName());
    }//GEN-LAST:event_btnRefreshWindowsActionPerformed

    private void btnAddControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddControlActionPerformed
        appControlsTableModel.addControl();
		tableControls.changeSelection(appControlsTableModel.getRowCount()-1, 0, false, false);
    }//GEN-LAST:event_btnAddControlActionPerformed

    private void btnDeleteControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteControlActionPerformed
        //Control control = 
				appControlsTableModel.removeControl(tableControls.getSelectedRow());
		//Persistable p = panelListPersistable.getSelectedItem();
		//((App)p).getControlsManager().removeControl(control);
    }//GEN-LAST:event_btnDeleteControlActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddControl;
    private javax.swing.JButton btnBrowsePath1;
    private javax.swing.JButton btnDeleteControl;
    private javax.swing.JButton btnEditAppCancel;
    private javax.swing.JButton btnEditAppSave;
    private javax.swing.JButton btnRefreshWindows;
    private javax.swing.JCheckBox checkInstanceApp;
    private javax.swing.JComboBox comboWindowName;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel lblComboWindowName;
    private javax.swing.JLabel lblHelpIcons;
    private javax.swing.JLabel lblTextFieldArgDirApp;
    private javax.swing.JLabel lblTextFieldArgFileApp;
    private javax.swing.JLabel lblTextFieldExtensionsApp;
    private javax.swing.JLabel lblTextFieldNameApp;
    private javax.swing.JLabel lblTextFieldPathApp;
    private javax.swing.JList listImages;
    private javax.swing.JPanel panelConfiguration;
    private org.zooper.becuz.restmote.ui.appcontrols.PanelControlKeys panelControlKeys;
    private javax.swing.JPanel panelControls;
    private org.zooper.becuz.restmote.ui.appcontrols.PanelVisualControl panelVisualControl;
    private javax.swing.JScrollPane scrollPaneListImages;
    private javax.swing.JScrollPane scrollPaneTableControls;
    private javax.swing.JScrollPane scrollPaneTableVisualControls;
    private javax.swing.JTable tableControls;
    private javax.swing.JTable tableVisualControls;
    private javax.swing.JTextField textFieldArgDirApp;
    private javax.swing.JTextField textFieldArgFileApp;
    private javax.swing.JTextField textFieldExtensionsApp;
    private javax.swing.JTextField textFieldNameApp;
    private javax.swing.JTextField textFieldPathApp;
    // End of variables declaration//GEN-END:variables
}
