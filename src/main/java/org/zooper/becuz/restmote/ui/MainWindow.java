/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zooper.becuz.restmote.ui;

import org.zooper.becuz.restmote.ui.model.AppTableModel;
import org.zooper.becuz.restmote.ui.model.ListComboBoxModel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.business.SettingsBusiness;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.http.InetAddr;
import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.ui.widgets.IntTextField;
import org.zooper.becuz.restmote.ui.widgets.URLLabel;
import org.zooper.becuz.restmote.utils.Utils;

/**
 *
 * @author admin
 */
public class MainWindow extends javax.swing.JFrame {

    private Logger logger = Logger.getLogger(MainWindow.class.getName());
    
    /**
     * Swing list model for {@link #listPaths} 
     */
    private DefaultListModel<String> listPathsModel;
    
    /**
     * Swing list model for {@link #comboInetNames} 
     */
    private ListComboBoxModel<InetAddr> listInetNamesModel;
	
	/**
     * Swing list model for {@link #comboInetNames} 
     */
    private DefaultComboBoxModel<String> listIconThemesModel;
    
    /**
     * Swing list model for {@link #listCategories} 
     */
    private DefaultListModel<MediaCategory> listMediaCategoryModel;
    
	 /**
     * Swing list model for {@link #listApps} 
     */
    private DefaultListModel<App> listAppsModel;
    
    /**
     * 
     */
    private AppTableModel appTableModel = new AppTableModel();
	
	/**
	 * 
	 */
	private List<MediaCategory> binMediaCateogories = new ArrayList<MediaCategory>();
	
	/**
	 * 
	 */
	private List<App> binApps = new ArrayList<App>();
	
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
		listInetNamesModel = new ListComboBoxModel<InetAddr>();
		listMediaCategoryModel = new DefaultListModel<MediaCategory>();
    	listPathsModel = new DefaultListModel<String>();
		listIconThemesModel = new DefaultComboBoxModel<String>();
		listAppsModel = new DefaultListModel<App>();
		initComponents();
		textFieldPort.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				refreshLblServerUrl();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				refreshLblServerUrl();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				refreshLblServerUrl();		
			}
		});
		copyToView();
    }

	private void copyToView(){
		Settings settings = PcControllerFactory.getPcController().getSettingsBusiness().get();
		
		listInetNamesModel.clear();
		listPathsModel.clear();
		listIconThemesModel.removeAllElements();
		listMediaCategoryModel.clear();
		listAppsModel.clear();
		
		
		listInetNamesModel.addAll(Server.getInstance().getLocalInetAddresses());
		if (!Utils.isEmpty(settings.getServerInetName())){
    		for(InetAddr inetAddr: listInetNamesModel.getAll()){
    			if(inetAddr.getInetName().equals(settings.getServerInetName())){
    				listInetNamesModel.setSelectedItem(inetAddr);
    				break;
    			}
    		}
    	}
    	
    	for(String path: settings.getPaths()){
    		listPathsModel.addElement(path);
    	}
		
		File f = new File(Utils.getRestmoteDirClientImages());
		if (f.exists() && f.isDirectory()){
			File[] children = f.listFiles();
			if (children != null){
				for(File child: children){
					if (child.isDirectory()){
						String dirIcon = child.getPath().substring(child.getPath().lastIndexOf(System.getProperty("file.separator"))+1);
						listIconThemesModel.addElement(dirIcon);
						if (dirIcon.equals(settings.getIconControlsTheme())){
							listIconThemesModel.setSelectedItem(dirIcon);
						}
					}
				}
			}
		}
		
		for(MediaCategory mediaCategory: PcControllerFactory.getPcController().getMediaCategoryBusiness().getAll()){
			listMediaCategoryModel.addElement(mediaCategory);
		}
		
		for(App app: PcControllerFactory.getPcController().getAppBusiness().getAll()){
			listAppsModel.addElement(app);
		}
		
		textFieldPort.setText(""+settings.getServerPort());
        textFieldName.setText(settings.getName());
        textFieldNameRoot.setText(settings.getNameRoot());
        textFieldScanDepth.setText(""+settings.getScanDepth());
	}
	
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTabs = new javax.swing.JTabbedPane();
        panelSettings = new javax.swing.JPanel();
        panelSettingsPnlServer = new javax.swing.JPanel();
        lblComboInetNames = new javax.swing.JLabel();
        comboInetNames = new javax.swing.JComboBox();
        lblComboPort = new javax.swing.JLabel();
        textFieldPort = new IntTextField();
        lblStatusServer = new javax.swing.JLabel();
        btnToggleServer = new javax.swing.JButton();
        lblServerUrl = new URLLabel();
        btnRefreshInetNames = new javax.swing.JButton();
        panelSettingsPnlGeneral = new javax.swing.JPanel();
        lblTextFieldName = new javax.swing.JLabel();
        textFieldName = new javax.swing.JTextField();
        lblTextFieldNameRoot = new javax.swing.JLabel();
        textFieldNameRoot = new javax.swing.JTextField();
        scrollPaneListPaths = new javax.swing.JScrollPane();
        listPaths = new javax.swing.JList();
        btnAddPath = new javax.swing.JButton();
        textFieldPath = new javax.swing.JTextField();
        btnDeletePath = new javax.swing.JButton();
        lblPaths = new javax.swing.JLabel();
        lblTextFieldScanDepth = new javax.swing.JLabel();
        textFieldScanDepth = new IntTextField();
        btnBrowsePath = new javax.swing.JButton();
        lblComboIconTheme = new javax.swing.JLabel();
        comboIconTheme = new javax.swing.JComboBox();
        panelCategories = new javax.swing.JPanel();
        panelCategoriesPnlEdit = new javax.swing.JPanel();
        lblTextFieldNameCategory = new javax.swing.JLabel();
        textFieldNameCategory = new javax.swing.JTextField();
        lblTextFieldDescriptionCategory = new javax.swing.JLabel();
        textFieldDescriptionCategory = new javax.swing.JTextField();
        lblTextFieldExtensionsCategory = new javax.swing.JLabel();
        textFieldExtensionsCategory = new javax.swing.JTextField();
        btnEditCategoryCancel = new javax.swing.JButton();
        btnEditCategorySave = new javax.swing.JButton();
        panelCategoriesPnlList = new javax.swing.JPanel();
        lblListCategories = new javax.swing.JLabel();
        btnDeleteCategory = new javax.swing.JButton();
        scrollPaneListCategories = new javax.swing.JScrollPane();
        listCategories = new javax.swing.JList();
        btnAddCategory = new javax.swing.JButton();
        lblCategoriesSummary = new javax.swing.JLabel();
        panelApps = new javax.swing.JPanel();
        lblAppsSummary = new javax.swing.JLabel();
        panelAppsPnlList = new javax.swing.JPanel();
        lblListApps = new javax.swing.JLabel();
        btnDeleteApp = new javax.swing.JButton();
        scrollPaneListApps = new javax.swing.JScrollPane();
        listApps = new javax.swing.JList();
        btnAddApp = new javax.swing.JButton();
        panelAppsPnlEdit = new javax.swing.JPanel();
        lblTextFieldNameApp = new javax.swing.JLabel();
        textFieldNameApp = new javax.swing.JTextField();
        lblTextFieldDescriptionApp = new javax.swing.JLabel();
        textFieldDescriptionApp = new javax.swing.JTextField();
        lblTextFieldExtensionsApp = new javax.swing.JLabel();
        textFieldExtensionsApp = new javax.swing.JTextField();
        btnEditAppCancel = new javax.swing.JButton();
        btnEditAppSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        checkInstanceApp = new javax.swing.JCheckBox();
        lblTextFieldArgFileApp = new javax.swing.JLabel();
        textFieldArgFileApp = new javax.swing.JTextField();
        textFieldArgDirApp = new javax.swing.JTextField();
        lblTextFieldArgDirApp = new javax.swing.JLabel();
        btnEdirAppTest = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileExit = new javax.swing.JMenuItem();
        menuAbout = new javax.swing.JMenu();

        FormListener formListener = new FormListener();

        setTitle("Restmote Control");
        setResizable(false);

        panelSettingsPnlServer.setBorder(javax.swing.BorderFactory.createTitledBorder("Server"));

        lblComboInetNames.setText("Interface");

        comboInetNames.setModel(listInetNamesModel);
        comboInetNames.addActionListener(formListener);

        lblComboPort.setText("Port");

        lblStatusServer.setText("Checking server status..");

        btnToggleServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/user_green.png"))); // NOI18N
        btnToggleServer.addMouseListener(formListener);
        btnToggleServer.addActionListener(formListener);

        lblServerUrl.setText("lbl Server Url");

        btnRefreshInetNames.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/refresh.png"))); // NOI18N
        btnRefreshInetNames.setEnabled(false);

        javax.swing.GroupLayout panelSettingsPnlServerLayout = new javax.swing.GroupLayout(panelSettingsPnlServer);
        panelSettingsPnlServer.setLayout(panelSettingsPnlServerLayout);
        panelSettingsPnlServerLayout.setHorizontalGroup(
            panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatusServer, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(lblServerUrl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                        .addComponent(comboInetNames, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefreshInetNames, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                        .addComponent(lblComboPort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                        .addComponent(lblComboInetNames)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnToggleServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelSettingsPnlServerLayout.setVerticalGroup(
            panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblComboInetNames)
                .addGap(5, 5, 5)
                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboInetNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefreshInetNames, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComboPort))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblServerUrl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
                .addComponent(lblStatusServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnToggleServer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        panelSettingsPnlGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));

        lblTextFieldName.setLabelFor(lblTextFieldName);
        lblTextFieldName.setText("Name");

        lblTextFieldNameRoot.setLabelFor(textFieldNameRoot);
        lblTextFieldNameRoot.setText("NameRoot");

        listPaths.setModel(listPathsModel);
        listPaths.addListSelectionListener(formListener);
        scrollPaneListPaths.setViewportView(listPaths);

        btnAddPath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/add.png"))); // NOI18N
        btnAddPath.addActionListener(formListener);

        btnDeletePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/delete.png"))); // NOI18N
        btnDeletePath.setEnabled(false);
        btnDeletePath.addActionListener(formListener);

        lblPaths.setText("Here you can choose the directories that the system will monitor for medias");

        lblTextFieldScanDepth.setLabelFor(lblTextFieldScanDepth);
        lblTextFieldScanDepth.setText("ScanDepth");

        btnBrowsePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/folder.png"))); // NOI18N
        btnBrowsePath.addActionListener(formListener);

        lblComboIconTheme.setLabelFor(comboIconTheme);
        lblComboIconTheme.setText("Icon Theme");

        comboIconTheme.setModel(listIconThemesModel);

        javax.swing.GroupLayout panelSettingsPnlGeneralLayout = new javax.swing.GroupLayout(panelSettingsPnlGeneral);
        panelSettingsPnlGeneral.setLayout(panelSettingsPnlGeneralLayout);
        panelSettingsPnlGeneralLayout.setHorizontalGroup(
            panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                        .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(scrollPaneListPaths, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                    .addComponent(textFieldPath))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnDeletePath, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                                        .addComponent(btnBrowsePath, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnAddPath, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblComboIconTheme, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTextFieldName))
                                    .addComponent(lblTextFieldNameRoot)
                                    .addComponent(lblTextFieldScanDepth))
                                .addGap(18, 18, 18)
                                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textFieldNameRoot, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(textFieldScanDepth, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(comboIconTheme, 0, 140, Short.MAX_VALUE)
                                    .addComponent(textFieldName))
                                .addGap(231, 231, 231)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                        .addComponent(lblPaths, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelSettingsPnlGeneralLayout.setVerticalGroup(
            panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldName)
                    .addComponent(textFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldNameRoot)
                    .addComponent(textFieldNameRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldScanDepth)
                    .addComponent(textFieldScanDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComboIconTheme)
                    .addComponent(comboIconTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(lblPaths)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddPath, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBrowsePath, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneListPaths, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeletePath, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelSettingsLayout = new javax.swing.GroupLayout(panelSettings);
        panelSettings.setLayout(panelSettingsLayout);
        panelSettingsLayout.setHorizontalGroup(
            panelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSettingsPnlServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelSettingsPnlGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelSettingsLayout.setVerticalGroup(
            panelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSettingsPnlServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelSettingsPnlGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelTabs.addTab("Settings", new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/wrench.png")), panelSettings); // NOI18N

        panelCategoriesPnlEdit.setBorder(javax.swing.BorderFactory.createTitledBorder("Edit"));

        lblTextFieldNameCategory.setText("Name");

        lblTextFieldDescriptionCategory.setLabelFor(lblTextFieldDescriptionCategory);
        lblTextFieldDescriptionCategory.setText("Description");

        lblTextFieldExtensionsCategory.setText("Extensions");

        btnEditCategoryCancel.setText("Cancel");
        btnEditCategoryCancel.addActionListener(formListener);

        btnEditCategorySave.setText("Save");
        btnEditCategorySave.addActionListener(formListener);

        javax.swing.GroupLayout panelCategoriesPnlEditLayout = new javax.swing.GroupLayout(panelCategoriesPnlEdit);
        panelCategoriesPnlEdit.setLayout(panelCategoriesPnlEditLayout);
        panelCategoriesPnlEditLayout.setHorizontalGroup(
            panelCategoriesPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategoriesPnlEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCategoriesPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCategoriesPnlEditLayout.createSequentialGroup()
                        .addGroup(panelCategoriesPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextFieldDescriptionCategory)
                            .addComponent(lblTextFieldNameCategory, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTextFieldExtensionsCategory, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelCategoriesPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldDescriptionCategory)
                            .addComponent(textFieldExtensionsCategory)
                            .addComponent(textFieldNameCategory)))
                    .addGroup(panelCategoriesPnlEditLayout.createSequentialGroup()
                        .addGap(0, 323, Short.MAX_VALUE)
                        .addComponent(btnEditCategorySave)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditCategoryCancel)))
                .addContainerGap())
        );
        panelCategoriesPnlEditLayout.setVerticalGroup(
            panelCategoriesPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategoriesPnlEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCategoriesPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldNameCategory)
                    .addComponent(textFieldNameCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCategoriesPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldExtensionsCategory)
                    .addComponent(textFieldExtensionsCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCategoriesPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldDescriptionCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTextFieldDescriptionCategory))
                .addGap(18, 18, 18)
                .addGroup(panelCategoriesPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditCategorySave)
                    .addComponent(btnEditCategoryCancel))
                .addContainerGap(233, Short.MAX_VALUE))
        );

        panelCategoriesPnlList.setBorder(javax.swing.BorderFactory.createTitledBorder("List"));
        panelCategoriesPnlList.setOpaque(false);
        panelCategoriesPnlList.setPreferredSize(new java.awt.Dimension(284, 379));
        panelCategoriesPnlList.setRequestFocusEnabled(false);

        lblListCategories.setText("Edit one category from below or create a new one:");

        btnDeleteCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/delete.png"))); // NOI18N
        btnDeleteCategory.setEnabled(false);
        btnDeleteCategory.addActionListener(formListener);

        listCategories.setModel(listMediaCategoryModel);
        listCategories.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listCategories.addListSelectionListener(formListener);
        scrollPaneListCategories.setViewportView(listCategories);

        btnAddCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/add.png"))); // NOI18N
        btnAddCategory.addActionListener(formListener);

        javax.swing.GroupLayout panelCategoriesPnlListLayout = new javax.swing.GroupLayout(panelCategoriesPnlList);
        panelCategoriesPnlList.setLayout(panelCategoriesPnlListLayout);
        panelCategoriesPnlListLayout.setHorizontalGroup(
            panelCategoriesPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategoriesPnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCategoriesPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCategoriesPnlListLayout.createSequentialGroup()
                        .addComponent(lblListCategories)
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addComponent(scrollPaneListCategories, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCategoriesPnlListLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelCategoriesPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panelCategoriesPnlListLayout.setVerticalGroup(
            panelCategoriesPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCategoriesPnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblListCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddCategory)
                .addGap(19, 19, 19)
                .addComponent(scrollPaneListCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(btnDeleteCategory)
                .addContainerGap())
        );

        lblCategoriesSummary.setText("jLabel2");

        javax.swing.GroupLayout panelCategoriesLayout = new javax.swing.GroupLayout(panelCategories);
        panelCategories.setLayout(panelCategoriesLayout);
        panelCategoriesLayout.setHorizontalGroup(
            panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCategoriesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCategoriesLayout.createSequentialGroup()
                        .addComponent(panelCategoriesPnlList, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelCategoriesPnlEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelCategoriesLayout.createSequentialGroup()
                        .addComponent(lblCategoriesSummary)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCategoriesLayout.setVerticalGroup(
            panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategoriesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCategoriesSummary)
                .addGap(18, 18, 18)
                .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCategoriesPnlEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCategoriesPnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelTabs.addTab("Categories", new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/user_green.png")), panelCategories); // NOI18N

        lblAppsSummary.setText("jLabel2");

        panelAppsPnlList.setBorder(javax.swing.BorderFactory.createTitledBorder("List"));

        lblListApps.setText("Edit one app from below or create a new one:");
        lblListApps.setPreferredSize(new java.awt.Dimension(245, 14));

        btnDeleteApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/delete.png"))); // NOI18N
        btnDeleteApp.setEnabled(false);
        btnDeleteApp.addActionListener(formListener);

        listApps.setModel(listAppsModel);
        listApps.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listApps.addListSelectionListener(formListener);
        scrollPaneListApps.setViewportView(listApps);

        btnAddApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/add.png"))); // NOI18N
        btnAddApp.addActionListener(formListener);

        javax.swing.GroupLayout panelAppsPnlListLayout = new javax.swing.GroupLayout(panelAppsPnlList);
        panelAppsPnlList.setLayout(panelAppsPnlListLayout);
        panelAppsPnlListLayout.setHorizontalGroup(
            panelAppsPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAppsPnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAppsPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAppsPnlListLayout.createSequentialGroup()
                        .addComponent(lblListApps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAppsPnlListLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelAppsPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDeleteApp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddApp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scrollPaneListApps, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelAppsPnlListLayout.setVerticalGroup(
            panelAppsPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAppsPnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblListApps, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddApp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(scrollPaneListApps, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteApp)
                .addContainerGap())
        );

        panelAppsPnlEdit.setBorder(javax.swing.BorderFactory.createTitledBorder("Edit"));

        lblTextFieldNameApp.setText("Name");

        lblTextFieldDescriptionApp.setLabelFor(lblTextFieldDescriptionCategory);
        lblTextFieldDescriptionApp.setText("Description");
        lblTextFieldDescriptionApp.setEnabled(false);

        textFieldDescriptionApp.setEnabled(false);

        lblTextFieldExtensionsApp.setText("Extensions");

        btnEditAppCancel.setText("Cancel");
        btnEditAppCancel.addActionListener(formListener);

        btnEditAppSave.setText("Save");
        btnEditAppSave.addActionListener(formListener);

        jTable1.setModel(appTableModel);
        jScrollPane1.setViewportView(jTable1);

        checkInstanceApp.setText("One Instance");

        lblTextFieldArgFileApp.setText("Arg file");

        lblTextFieldArgDirApp.setText("Arg dir");

        btnEdirAppTest.setText("Test");

        javax.swing.GroupLayout panelAppsPnlEditLayout = new javax.swing.GroupLayout(panelAppsPnlEdit);
        panelAppsPnlEdit.setLayout(panelAppsPnlEditLayout);
        panelAppsPnlEditLayout.setHorizontalGroup(
            panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAppsPnlEditLayout.createSequentialGroup()
                .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAppsPnlEditLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextFieldDescriptionApp)
                            .addComponent(lblTextFieldNameApp, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTextFieldExtensionsApp, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldDescriptionApp)
                            .addComponent(textFieldExtensionsApp)
                            .addGroup(panelAppsPnlEditLayout.createSequentialGroup()
                                .addComponent(textFieldNameApp, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(checkInstanceApp, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAppsPnlEditLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblTextFieldArgFileApp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldArgFileApp, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTextFieldArgDirApp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldArgDirApp, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAppsPnlEditLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAppsPnlEditLayout.createSequentialGroup()
                                .addComponent(btnEdirAppTest)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditAppSave)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditAppCancel))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelAppsPnlEditLayout.setVerticalGroup(
            panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAppsPnlEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldNameApp)
                    .addComponent(textFieldNameApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkInstanceApp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldExtensionsApp)
                    .addComponent(textFieldExtensionsApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldDescriptionApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTextFieldDescriptionApp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTextFieldArgFileApp)
                        .addComponent(textFieldArgFileApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTextFieldArgDirApp))
                    .addComponent(textFieldArgDirApp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelAppsPnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditAppCancel)
                    .addComponent(btnEditAppSave)
                    .addComponent(btnEdirAppTest))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout panelAppsLayout = new javax.swing.GroupLayout(panelApps);
        panelApps.setLayout(panelAppsLayout);
        panelAppsLayout.setHorizontalGroup(
            panelAppsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAppsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAppsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAppsLayout.createSequentialGroup()
                        .addComponent(panelAppsPnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelAppsPnlEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAppsLayout.createSequentialGroup()
                        .addComponent(lblAppsSummary)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelAppsLayout.setVerticalGroup(
            panelAppsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAppsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAppsSummary)
                .addGap(18, 18, 18)
                .addGroup(panelAppsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelAppsPnlEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelAppsPnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelTabs.addTab("Apps", new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/user_orange.png")), panelApps); // NOI18N

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/accept.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setToolTipText("");
        btnSave.setIconTextGap(10);
        btnSave.addActionListener(formListener);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setToolTipText("");
        btnCancel.setIconTextGap(10);
        btnCancel.addActionListener(formListener);

        lblStatus.setText("Initializing..");

        menuFile.setText("File");

        menuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menuFileExit.setText("Exit");
        menuFileExit.addMouseListener(formListener);
        menuFileExit.addActionListener(formListener);
        menuFile.add(menuFileExit);

        menuBar.add(menuFile);

        menuAbout.setText("About");
        menuBar.add(menuAbout);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTabs)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTabs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave)
                    .addComponent(lblStatus))
                .addContainerGap())
        );

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.MouseListener, javax.swing.event.ListSelectionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == comboInetNames) {
                MainWindow.this.comboInetNamesActionPerformed(evt);
            }
            else if (evt.getSource() == btnToggleServer) {
                MainWindow.this.btnToggleServerActionPerformed(evt);
            }
            else if (evt.getSource() == btnAddPath) {
                MainWindow.this.btnAddPathActionPerformed(evt);
            }
            else if (evt.getSource() == btnDeletePath) {
                MainWindow.this.btnDeletePathActionPerformed(evt);
            }
            else if (evt.getSource() == btnBrowsePath) {
                MainWindow.this.btnBrowsePathActionPerformed(evt);
            }
            else if (evt.getSource() == btnEditCategoryCancel) {
                MainWindow.this.btnEditCategoryCancelActionPerformed(evt);
            }
            else if (evt.getSource() == btnEditCategorySave) {
                MainWindow.this.btnEditCategorySaveActionPerformed(evt);
            }
            else if (evt.getSource() == btnDeleteCategory) {
                MainWindow.this.btnDeleteCategoryActionPerformed(evt);
            }
            else if (evt.getSource() == btnAddCategory) {
                MainWindow.this.btnAddCategoryActionPerformed(evt);
            }
            else if (evt.getSource() == btnDeleteApp) {
                MainWindow.this.btnDeleteAppActionPerformed(evt);
            }
            else if (evt.getSource() == btnAddApp) {
                MainWindow.this.btnAddAppActionPerformed(evt);
            }
            else if (evt.getSource() == btnEditAppCancel) {
                MainWindow.this.btnEditAppCancelActionPerformed(evt);
            }
            else if (evt.getSource() == btnEditAppSave) {
                MainWindow.this.btnEditAppSaveActionPerformed(evt);
            }
            else if (evt.getSource() == btnSave) {
                MainWindow.this.btnSaveActionPerformed(evt);
            }
            else if (evt.getSource() == btnCancel) {
                MainWindow.this.btnCancelActionPerformed(evt);
            }
            else if (evt.getSource() == menuFileExit) {
                MainWindow.this.menuFileExitActionPerformed(evt);
            }
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
        }

        public void mouseEntered(java.awt.event.MouseEvent evt) {
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
        }

        public void mousePressed(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == btnToggleServer) {
                MainWindow.this.btnToggleServerMousePressed(evt);
            }
            else if (evt.getSource() == menuFileExit) {
                MainWindow.this.menuFileExitMousePressed(evt);
            }
        }

        public void mouseReleased(java.awt.event.MouseEvent evt) {
        }

        public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            if (evt.getSource() == listPaths) {
                MainWindow.this.listPathsValueChanged(evt);
            }
            else if (evt.getSource() == listCategories) {
                MainWindow.this.listCategoriesValueChanged(evt);
            }
            else if (evt.getSource() == listApps) {
                MainWindow.this.listAppsValueChanged(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void menuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuFileExitActionPerformed

    private void btnToggleServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToggleServerActionPerformed
    	try {
            if (Server.getInstance().isRunning()){
                Server.getInstance().stop();
            } else {
				if (listInetNamesModel.getSelectedItem() == null){
					lblStatus.setText(UIConstants.ERR_NO_INET);
				} else {
					Server.getInstance().start(listInetNamesModel.getSelectedItem().getInetName(), ((IntTextField)textFieldPort).getValue());
				}
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    	updateViewStatusServer();
    }//GEN-LAST:event_btnToggleServerActionPerformed
    
	private void setStatus(String message){
		lblStatus.setText(message);
	}
	
	private void clearStatus(){
		lblStatus.setText("");
	}
	
	public void updateViewStatusServer(){
		try {
            if (Server.getInstance().isRunning()){
            	lblStatusServer.setText("Http Server is running!");
                btnToggleServer.setText("Stop");
            } else {
            	lblStatusServer.setText("Http Server is not running");
                btnToggleServer.setText("Start");                
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
	}
	
    private void btnToggleServerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnToggleServerMousePressed
        
    }//GEN-LAST:event_btnToggleServerMousePressed

    private void menuFileExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileExitMousePressed
        System.exit(0);
    }//GEN-LAST:event_menuFileExitMousePressed

    public void refreshLblServerUrl(){
        InetAddr inetAddr = listInetNamesModel.getSelectedItem();
        String url = inetAddr == null ? "" : "http://" + inetAddr.getIp() + ":" + ((IntTextField)textFieldPort).getValue() + "/client/index.html";
        lblServerUrl.setText(url);
        ((URLLabel)lblServerUrl).setURL(url);
    }
    
    private void comboInetNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInetNamesActionPerformed
        refreshLblServerUrl();
    }//GEN-LAST:event_comboInetNamesActionPerformed

    private void listPathsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listPathsValueChanged
        if (evt.getValueIsAdjusting() == false) {
            btnDeletePath.setEnabled(listPaths.getSelectedIndex() != -1);
        }
    }//GEN-LAST:event_listPathsValueChanged

    private void btnDeletePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePathActionPerformed
        int[] indexes = listPaths.getSelectedIndices();
        for (int i = 0; i < indexes.length; i++) {
            listPathsModel.remove(indexes[i] - i);
        }
        btnDeletePath.setEnabled(false);
    }//GEN-LAST:event_btnDeletePathActionPerformed

    private void btnAddPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPathActionPerformed
       String name = textFieldPath.getText();
       if (name.equals("") || listPathsModel.contains(name)) {
            Toolkit.getDefaultToolkit().beep();
            textFieldPath.requestFocusInWindow();
            textFieldPath.selectAll();
            return;
       }
       int index = listPaths.getSelectedIndex() + 1;
       listPathsModel.insertElementAt(name, index);
       textFieldPath.requestFocusInWindow();
       textFieldPath.setText("");
       //listPaths.setSelectedIndex(index);
       //listPaths.ensureIndexIsVisible(index);
    }//GEN-LAST:event_btnAddPathActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        SettingsBusiness settingsBusiness = PcControllerFactory.getPcController().getSettingsBusiness();
        Settings settings = settingsBusiness.get();
        settings.setServerPort(Integer.parseInt(textFieldPort.getText()));
        InetAddr inetAddr = listInetNamesModel.getSelectedItem();
        settings.setServerInetName(inetAddr.getInetName());
        settings.setServerLastIp(inetAddr.getIp());
        Set<String> paths = new HashSet<String>(); 
        for (int i = 0; i < listPathsModel.size(); i++) {
            paths.add(listPathsModel.get(i));
        }
        settings.setPaths(paths);
        settings.setName(textFieldName.getText());
        settings.setNameRoot(textFieldNameRoot.getText());
        settings.setScanDepth(Integer.parseInt(textFieldScanDepth.getText()));
        settings.setTheme(listIconThemesModel.getSelectedItem().toString());
        settingsBusiness.store(settings);
		
        MediaCategoryBusiness mediaCategoryBusiness = PcControllerFactory.getPcController().getMediaCategoryBusiness();
        for (int i = 0; i < listMediaCategoryModel.size(); i++) {
        	mediaCategoryBusiness.store(listMediaCategoryModel.get(i));
		}
		
		AppBusiness appBusiness = PcControllerFactory.getPcController().getAppBusiness();
        for (int i = 0; i < listAppsModel.size(); i++) {
        	appBusiness.store(listAppsModel.get(i));
		}
		
		for(MediaCategory mediaCategory: binMediaCateogories){
			mediaCategoryBusiness.delete(mediaCategory);
		}
		for(App app: binApps){
			appBusiness.delete(app);
		}
		
        setVisible(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    
    
    private void panelTabsStateChanged(ChangeEvent evt) {
       logger.info("Not yet implemented");
    }

    private void listActiveAppsValueChanged(ListSelectionEvent evt) {
        logger.info("Not yet implemented");
    }

    private void editMediaCategory(MediaCategory mediaCategory){
        textFieldNameCategory.setText(mediaCategory == null ? "" : mediaCategory.getName());
        textFieldDescriptionCategory.setText(mediaCategory == null ? "" : mediaCategory.getDescription());
        textFieldExtensionsCategory.setText(mediaCategory == null ? "" : Utils.join(mediaCategory.getExtensions(), ","));
    }
	
	private void editApp(App app){
        textFieldNameApp.setText(app == null ? "" : app.getName());
        //textFieldDescriptionApp.setText(app == null ? "" : app.getDescription());
        textFieldExtensionsApp.setText(app == null ? "" : Utils.join(app.getExtensions(), ","));
		checkInstanceApp.setSelected(app == null ? false : app.isForceOneInstance());
		textFieldArgFileApp.setText(app == null ? "" : app.getArgumentsFile());
		textFieldArgDirApp.setText(app == null ? "" : app.getArgumentsDir());
		if (app != null){
			appTableModel.setData(app.getControlsManager());
		} else {
			appTableModel.clearData();
		}
    }
    
    private void btnAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoryActionPerformed
        MediaCategory mediaCategory = new MediaCategory("name");
        listMediaCategoryModel.insertElementAt(mediaCategory, 0);
        listCategories.setSelectedIndex(0);
    }//GEN-LAST:event_btnAddCategoryActionPerformed

    private void btnBrowsePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowsePathActionPerformed
        // TODO dire chooser dialog box
    }//GEN-LAST:event_btnBrowsePathActionPerformed

    private void btnEditCategoryCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCategoryCancelActionPerformed
        listCategories.clearSelection();
    }//GEN-LAST:event_btnEditCategoryCancelActionPerformed

    private void btnEditCategorySaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCategorySaveActionPerformed
    	int selectedIndex = listCategories.getSelectedIndex();
    	MediaCategory mediaCategory = selectedIndex == -1 ? null : listMediaCategoryModel.getElementAt(selectedIndex);
        if (mediaCategory != null){
            mediaCategory.setName(textFieldNameCategory.getText());
            mediaCategory.setDescription(textFieldDescriptionCategory.getText());
            mediaCategory.setExtensions(new HashSet<>(Arrays.asList(textFieldExtensionsCategory.getText().split(","))));
        }
		listCategories.clearSelection();
    }//GEN-LAST:event_btnEditCategorySaveActionPerformed

    private void listCategoriesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listCategoriesValueChanged
    	int selectedIndex = listCategories.getSelectedIndex();
    	if (evt.getValueIsAdjusting() == false) {
            btnDeleteCategory.setEnabled(selectedIndex > -1);
			if (selectedIndex == -1){
				//btnEditCategoryCancel.setEnabled(false);
				//btnEditCategorySave.setEnabled(false);
			}
        }
        MediaCategory mediaCategory = selectedIndex == -1 ? null : listMediaCategoryModel.getElementAt(selectedIndex);
        editMediaCategory(mediaCategory);
    }//GEN-LAST:event_listCategoriesValueChanged

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false);
		textFieldPath.setText("");
		copyToView();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAppActionPerformed
        int selectedIndex = listApps.getSelectedIndex();
        App app = selectedIndex == -1 ? null : listAppsModel.getElementAt(selectedIndex);
        binApps.add(app);
		listAppsModel.remove(selectedIndex);
    }//GEN-LAST:event_btnDeleteAppActionPerformed

    private void listAppsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listAppsValueChanged
        int selectedIndex = listApps.getSelectedIndex();
    	if (evt.getValueIsAdjusting() == false) {
            btnDeleteApp.setEnabled(selectedIndex > -1);
        }
        App app = selectedIndex == -1 ? null : listAppsModel.getElementAt(selectedIndex);
        editApp(app);
    }//GEN-LAST:event_listAppsValueChanged

    private void btnAddAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAppActionPerformed
        App app = new App("name");
        listAppsModel.insertElementAt(app, 0);
        listApps.setSelectedIndex(0);
    }//GEN-LAST:event_btnAddAppActionPerformed

    private void btnEditAppCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAppCancelActionPerformed
        listApps.clearSelection();
    }//GEN-LAST:event_btnEditAppCancelActionPerformed

    private void btnEditAppSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAppSaveActionPerformed
        int selectedIndex = listApps.getSelectedIndex();
    	App app = selectedIndex == -1 ? null : listAppsModel.getElementAt(selectedIndex);
        if (app != null){
            app.setName(textFieldNameCategory.getText());
            //app.setDescription(textFieldDescriptionCategory.getText());
            app.setExtensions(new HashSet<>(Arrays.asList(textFieldExtensionsCategory.getText().split(","))));
			app.setForceOneInstance(checkInstanceApp.isSelected());
			app.setArgumentsFile(textFieldArgFileApp.getText());
			app.setArgumentsDir(textFieldArgDirApp.getText());
        }
		listApps.clearSelection();
    }//GEN-LAST:event_btnEditAppSaveActionPerformed

    private void btnDeleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCategoryActionPerformed
        int selectedIndex = listCategories.getSelectedIndex();
        MediaCategory mediaCategory = selectedIndex == -1 ? null : listMediaCategoryModel.getElementAt(selectedIndex);
        binMediaCateogories.add(mediaCategory);
		listMediaCategoryModel.remove(selectedIndex);
    }//GEN-LAST:event_btnDeleteCategoryActionPerformed

    
    
    public javax.swing.JTextField getTextFieldDescriptionCategory() {
		return textFieldDescriptionCategory;
	}

	public javax.swing.JTextField getTextFieldExtensionsCategory() {
		return textFieldExtensionsCategory;
	}

	public javax.swing.JTextField getTextFieldNameCategory() {
		return textFieldNameCategory;
	}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddApp;
    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnAddPath;
    private javax.swing.JButton btnBrowsePath;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeleteApp;
    private javax.swing.JButton btnDeleteCategory;
    private javax.swing.JButton btnDeletePath;
    private javax.swing.JButton btnEdirAppTest;
    private javax.swing.JButton btnEditAppCancel;
    private javax.swing.JButton btnEditAppSave;
    private javax.swing.JButton btnEditCategoryCancel;
    private javax.swing.JButton btnEditCategorySave;
    private javax.swing.JButton btnRefreshInetNames;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnToggleServer;
    private javax.swing.JCheckBox checkInstanceApp;
    private javax.swing.JComboBox comboIconTheme;
    private javax.swing.JComboBox comboInetNames;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAppsSummary;
    private javax.swing.JLabel lblCategoriesSummary;
    private javax.swing.JLabel lblComboIconTheme;
    private javax.swing.JLabel lblComboInetNames;
    private javax.swing.JLabel lblComboPort;
    private javax.swing.JLabel lblListApps;
    private javax.swing.JLabel lblListCategories;
    private javax.swing.JLabel lblPaths;
    private javax.swing.JLabel lblServerUrl;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusServer;
    private javax.swing.JLabel lblTextFieldArgDirApp;
    private javax.swing.JLabel lblTextFieldArgFileApp;
    private javax.swing.JLabel lblTextFieldDescriptionApp;
    private javax.swing.JLabel lblTextFieldDescriptionCategory;
    private javax.swing.JLabel lblTextFieldExtensionsApp;
    private javax.swing.JLabel lblTextFieldExtensionsCategory;
    private javax.swing.JLabel lblTextFieldName;
    private javax.swing.JLabel lblTextFieldNameApp;
    private javax.swing.JLabel lblTextFieldNameCategory;
    private javax.swing.JLabel lblTextFieldNameRoot;
    private javax.swing.JLabel lblTextFieldScanDepth;
    private javax.swing.JList listApps;
    private javax.swing.JList listCategories;
    private javax.swing.JList listPaths;
    private javax.swing.JMenu menuAbout;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JPanel panelApps;
    private javax.swing.JPanel panelAppsPnlEdit;
    private javax.swing.JPanel panelAppsPnlList;
    private javax.swing.JPanel panelCategories;
    private javax.swing.JPanel panelCategoriesPnlEdit;
    private javax.swing.JPanel panelCategoriesPnlList;
    private javax.swing.JPanel panelSettings;
    private javax.swing.JPanel panelSettingsPnlGeneral;
    private javax.swing.JPanel panelSettingsPnlServer;
    private javax.swing.JTabbedPane panelTabs;
    private javax.swing.JScrollPane scrollPaneListApps;
    private javax.swing.JScrollPane scrollPaneListCategories;
    private javax.swing.JScrollPane scrollPaneListPaths;
    private javax.swing.JTextField textFieldArgDirApp;
    private javax.swing.JTextField textFieldArgFileApp;
    private javax.swing.JTextField textFieldDescriptionApp;
    private javax.swing.JTextField textFieldDescriptionCategory;
    private javax.swing.JTextField textFieldExtensionsApp;
    private javax.swing.JTextField textFieldExtensionsCategory;
    private javax.swing.JTextField textFieldName;
    private javax.swing.JTextField textFieldNameApp;
    private javax.swing.JTextField textFieldNameCategory;
    private javax.swing.JTextField textFieldNameRoot;
    private javax.swing.JTextField textFieldPath;
    private javax.swing.JTextField textFieldPort;
    private javax.swing.JTextField textFieldScanDepth;
    // End of variables declaration//GEN-END:variables

    
}
