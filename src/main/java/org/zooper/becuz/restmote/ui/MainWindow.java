package org.zooper.becuz.restmote.ui;

import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileFilter;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.business.SettingsBusiness;
import org.zooper.becuz.restmote.http.InetAddr;
import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.persistence.export.ImportExport;
import org.zooper.becuz.restmote.ui.appcontrols.ImageList;
import org.zooper.becuz.restmote.ui.widgets.IntTextField;
import org.zooper.becuz.restmote.ui.widgets.URLLabel;
import org.zooper.becuz.restmote.utils.Utils;

/**
 *
 * @author bebo
 */
@SuppressWarnings("serial")
public class MainWindow extends javax.swing.JFrame {

	private static final ImageIcon ICON_PLAY = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/control_play.png"));
	private static final ImageIcon ICON_STOP = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/control_stop.png"));
	
    private Logger logger = Logger.getLogger(MainWindow.class.getName());
    
	/**
	 * File chooser used in several points
	 */
	private static JFileChooser fc;

    /**
     * Swing list model for {@link #listPaths} 
     */
    private DefaultListModel<String> listPathsModel = new DefaultListModel<String>();
    
    /**
     * Swing list model for {@link #comboInetNames} 
     */
    private DefaultComboBoxModel<InetAddr> listInetNamesModel = new DefaultComboBoxModel<InetAddr>();
	
	/**
     * Swing list model for {@link #comboInetNames} 
     */
    private DefaultComboBoxModel<String> listIconThemesModel = new DefaultComboBoxModel<String>();
    
   
	
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
		
		initComponents();
		postInitComponents();
		
		File f = new File(Utils.getThemeDir(""));
		File[] children = f.listFiles();
		if (children != null){
			for(File child: children){
				if (child.isDirectory()){
					String dirIcon = child.getPath().substring(child.getPath().lastIndexOf(System.getProperty("file.separator"))+1);
					listIconThemesModel.addElement(dirIcon);
				}
			}
		}
		copyToView();
    }

    /**
     * Builds the swing models
     */
	private void copyToView(){
		logger.info("copyToView()");
		
		panelApps.copyToView();
		panelCategories.copyToView();
		panelCommands.copyToView();
		
		listPathsModel.clear();
		
		Settings settings = BusinessFactory.getSettingsBusiness().get();
		buildListInetNamesModel(settings.getPreferredServerInetName());
    	
		listIconThemesModel.setSelectedItem(settings.getIconControlsTheme());
		
		if (settings.getPaths() != null){
	    	for(String path: settings.getPaths()){
	    		listPathsModel.addElement(path);
	    	}
		}
		
		textFieldPort.setText(""+settings.getServerPort());
        textFieldName.setText(settings.getName());
        textFieldNameRoot.setText(settings.getNameRoot());
        textFieldScanDepth.setText(""+settings.getScanDepth());
		jCheckBoxRunAllInterfaces.setSelected(Boolean.TRUE.equals(settings.getRunAllNetInterfaces()));
	}
	
	/**
	 * called after {@link #initComponents()}
	 */
	private void postInitComponents(){
		updateViewStatusServer();
		panelApps.getListModel().addListDataListener(new ListDataListener() {
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
				panelCategories.getPanelEditCategories().setModelAppData(panelApps.getListModel().elements());
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				panelCategories.getPanelEditCategories().setModelAppData(panelApps.getListModel().elements());
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
				panelCategories.getPanelEditCategories().setModelAppData(panelApps.getListModel().elements());
			}
		});
	}
	
	/**
	 * Populate listInetNamesModel
	 * @param selectInetName 
	 */
	private void buildListInetNamesModel(String selectInetName){
		listInetNamesModel.removeAllElements();
		for(InetAddr inetAddr: Server.getInstance().getLocalInetAddresses()){
			listInetNamesModel.addElement(inetAddr);
			if (!Utils.isEmpty(selectInetName)){
				if(inetAddr.getInetName().equals(selectInetName)){
					listInetNamesModel.setSelectedItem(inetAddr);
				}
			}
		}
    	
	}
	
	public static JFileChooser getFc() {
		if (fc == null){
			fc = new JFileChooser();
		}
		return fc;
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
        lblQrCode = new javax.swing.JLabel();
        jCheckBoxRunAllInterfaces = new javax.swing.JCheckBox();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        listImages = new ImageList(true);
        lblIconsCredits = new javax.swing.JLabel();
        panelApps = new org.zooper.becuz.restmote.ui.panels.PanelApps();
        panelCategories = new org.zooper.becuz.restmote.ui.panels.PanelCategories();
        panelCommands = new org.zooper.becuz.restmote.ui.panels.PanelCommands();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileExit = new javax.swing.JMenuItem();
        menuAbout = new javax.swing.JMenu();
        menuAdvanced = new javax.swing.JMenu();
        menuImport = new javax.swing.JMenu();
        menuImportAll = new javax.swing.JMenuItem();
        menuImportApps = new javax.swing.JMenuItem();
        menuExport = new javax.swing.JMenu();
        menuExportAll = new javax.swing.JMenuItem();
        menuExportApps = new javax.swing.JMenuItem();

        FormListener formListener = new FormListener();

        setTitle("Restmote Control");
        setMinimumSize(new java.awt.Dimension(900, 700));
        setPreferredSize(new java.awt.Dimension(900, 700));

        panelSettingsPnlServer.setBorder(javax.swing.BorderFactory.createTitledBorder("Server"));

        lblComboInetNames.setText("Interfaces:");

        comboInetNames.setModel(listInetNamesModel);
        comboInetNames.addActionListener(formListener);

        lblComboPort.setText("Port:");

        lblStatusServer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusServer.setText("Checking server status..");

        btnToggleServer.setIcon(ICON_PLAY);
        btnToggleServer.addMouseListener(formListener);
        btnToggleServer.addActionListener(formListener);

        lblServerUrl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblServerUrl.setText("server URL");

        btnRefreshInetNames.setIcon(UIUtils.ICON_REFRESH);
        btnRefreshInetNames.setToolTipText(UIConstants.TOOLTIP_STNGS_BTNREFRESH_INTERFACES);
        btnRefreshInetNames.setFocusPainted(false);
        btnRefreshInetNames.addActionListener(formListener);

        lblQrCode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQrCode.setText("qrCode");

        jCheckBoxRunAllInterfaces.setText("Run in all interfaces");
        jCheckBoxRunAllInterfaces.setToolTipText(UIConstants.TOOLTIP_STNGS_ALLINT);

        javax.swing.GroupLayout panelSettingsPnlServerLayout = new javax.swing.GroupLayout(panelSettingsPnlServer);
        panelSettingsPnlServer.setLayout(panelSettingsPnlServerLayout);
        panelSettingsPnlServerLayout.setHorizontalGroup(
            panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblServerUrl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblQrCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                        .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comboInetNames, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                                        .addComponent(lblComboPort)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblStatusServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnToggleServer, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(btnRefreshInetNames))
                    .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                        .addComponent(lblComboInetNames)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxRunAllInterfaces)))
                .addContainerGap())
        );
        panelSettingsPnlServerLayout.setVerticalGroup(
            panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComboInetNames)
                    .addComponent(jCheckBoxRunAllInterfaces))
                .addGap(4, 4, 4)
                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRefreshInetNames, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(comboInetNames))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComboPort))
                .addGap(46, 46, 46)
                .addComponent(lblStatusServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnToggleServer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(lblQrCode, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblServerUrl)
                .addGap(26, 26, 26))
        );

        panelSettingsPnlGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));

        lblTextFieldName.setLabelFor(lblTextFieldName);
        lblTextFieldName.setText("Name:");
        lblTextFieldName.setToolTipText(UIConstants.TOOLTIP_STNGS_NAME);

        lblTextFieldNameRoot.setLabelFor(textFieldNameRoot);
        lblTextFieldNameRoot.setText("NameRoot:");
        lblTextFieldNameRoot.setToolTipText(UIConstants.TOOLTIP_STNGS_NAMEROOT);

        listPaths.setModel(listPathsModel);
        listPaths.addListSelectionListener(formListener);
        scrollPaneListPaths.setViewportView(listPaths);

        btnAddPath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/add.png"))); // NOI18N
        btnAddPath.setToolTipText("Add");
        btnAddPath.addActionListener(formListener);

        btnDeletePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/delete.png"))); // NOI18N
        btnDeletePath.setToolTipText("Delete");
        btnDeletePath.setEnabled(false);
        btnDeletePath.addActionListener(formListener);

        lblPaths.setText("Choose the directories that the system will monitor for medias:");

        lblTextFieldScanDepth.setLabelFor(lblTextFieldScanDepth);
        lblTextFieldScanDepth.setText("ScanDepth:");
        lblTextFieldScanDepth.setToolTipText(UIConstants.TOOLTIP_STNGS_SCANDEPTH);

        btnBrowsePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/folder.png"))); // NOI18N
        btnBrowsePath.setToolTipText("Browse");
        btnBrowsePath.addActionListener(formListener);

        lblComboIconTheme.setLabelFor(comboIconTheme);
        lblComboIconTheme.setText("Icon Theme:");
        lblComboIconTheme.setToolTipText(UIConstants.TOOLTIP_STNGS_ICONTHEME);

        comboIconTheme.setModel(listIconThemesModel);
        comboIconTheme.addActionListener(formListener);

        listImages.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listImages.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        jScrollPane1.setViewportView(listImages);

        lblIconsCredits.setText("credits");

        javax.swing.GroupLayout panelSettingsPnlGeneralLayout = new javax.swing.GroupLayout(panelSettingsPnlGeneral);
        panelSettingsPnlGeneral.setLayout(panelSettingsPnlGeneralLayout);
        panelSettingsPnlGeneralLayout.setHorizontalGroup(
            panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIconsCredits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPaths, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                        .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneListPaths, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                            .addComponent(textFieldPath))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                                .addComponent(btnBrowsePath)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddPath, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnDeletePath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                        .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTextFieldScanDepth)
                            .addComponent(lblTextFieldNameRoot)
                            .addComponent(lblTextFieldName)
                            .addComponent(lblComboIconTheme))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(textFieldNameRoot, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(textFieldScanDepth, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(textFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboIconTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        panelSettingsPnlGeneralLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddPath, btnBrowsePath});

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboIconTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComboIconTheme))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIconsCredits)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(lblPaths)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBrowsePath)
                    .addComponent(textFieldPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneListPaths, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeletePath, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelSettingsPnlGeneralLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddPath, btnBrowsePath, btnDeletePath});

        javax.swing.GroupLayout panelSettingsLayout = new javax.swing.GroupLayout(panelSettings);
        panelSettings.setLayout(panelSettingsLayout);
        panelSettingsLayout.setHorizontalGroup(
            panelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSettingsPnlServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSettingsPnlGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelSettingsLayout.setVerticalGroup(
            panelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSettingsPnlServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelSettingsPnlGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelTabs.addTab("Settings", new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/wrench.png")), panelSettings); // NOI18N
        panelTabs.addTab("Apps", new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/apps.png")), panelApps); // NOI18N
        panelTabs.addTab("Categories", new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/categories.png")), panelCategories); // NOI18N
        panelTabs.addTab("Commands", new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/commands.png")), panelCommands); // NOI18N

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

        menuFile.setText("File");

        menuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menuFileExit.setText("Exit");
        menuFileExit.addMouseListener(formListener);
        menuFileExit.addActionListener(formListener);
        menuFile.add(menuFileExit);

        menuBar.add(menuFile);

        menuAbout.setText("About");
        menuBar.add(menuAbout);

        menuAdvanced.setText("Advanced");

        menuImport.setText("Import");

        menuImportAll.setText("Import all...");
        menuImportAll.setToolTipText(UIConstants.TOOLTIP_MENU_EXPORTALL);
        menuImportAll.addActionListener(formListener);
        menuImport.add(menuImportAll);

        menuImportApps.setText("Import apps...");
        menuImportApps.setToolTipText(UIConstants.TOOLTIP_MENU_EXPORTALL);
        menuImportApps.setEnabled(false);
        menuImportApps.addActionListener(formListener);
        menuImport.add(menuImportApps);

        menuAdvanced.add(menuImport);

        menuExport.setText("Export");

        menuExportAll.setText("Export all");
        menuExportAll.setToolTipText(UIConstants.TOOLTIP_MENU_EXPORTALL);
        menuExportAll.addActionListener(formListener);
        menuExport.add(menuExportAll);

        menuExportApps.setText("Export apps");
        menuExportApps.setToolTipText(UIConstants.TOOLTIP_MENU_EXPORTALL);
        menuExportApps.setEnabled(false);
        menuExportApps.addActionListener(formListener);
        menuExport.add(menuExportApps);

        menuAdvanced.add(menuExport);

        menuBar.add(menuAdvanced);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addContainerGap())
            .addComponent(panelTabs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnSave});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
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
            else if (evt.getSource() == btnRefreshInetNames) {
                MainWindow.this.btnRefreshInetNamesActionPerformed(evt);
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
            else if (evt.getSource() == comboIconTheme) {
                MainWindow.this.comboIconThemeActionPerformed(evt);
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
            else if (evt.getSource() == menuImportAll) {
                MainWindow.this.menuImportAllActionPerformed(evt);
            }
            else if (evt.getSource() == menuImportApps) {
                MainWindow.this.menuImportAppsActionPerformed(evt);
            }
            else if (evt.getSource() == menuExportAll) {
                MainWindow.this.menuExportAllActionPerformed(evt);
            }
            else if (evt.getSource() == menuExportApps) {
                MainWindow.this.menuExportAppsActionPerformed(evt);
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
        }
    }// </editor-fold>//GEN-END:initComponents

    private void menuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuFileExitActionPerformed

    public void btnToggleServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToggleServerActionPerformed
    	toggleServer();
    }//GEN-LAST:event_btnToggleServerActionPerformed
  
    public void toggleServer(){
    	try {
    		if (jCheckBoxRunAllInterfaces.isSelected()){
    			Server.getInstance().toggleAll(((IntTextField)textFieldPort).getValue());	
    		} else {
    			Server.getInstance().toggle(((InetAddr)listInetNamesModel.getSelectedItem()).getInetName(), ((IntTextField)textFieldPort).getValue());
    		}
			updateViewStatusServer();
		} catch (Exception e) {
			logger.error("", e);
		}
    }
    
	private void setStatus(String message){
		lblStatus.setText(message);
	}
	
	private void clearStatus(){
		lblStatus.setText("");
	}
	
	public void updateViewStatusServer(){
		try {
			boolean serverRunning = Server.getInstance().isRunning();
            if (serverRunning){
            	lblStatusServer.setText("Http Server is running!");
                btnToggleServer.setText("Stop");
				btnToggleServer.setIcon(ICON_STOP);
				changedSelectedInetName();
            } else {
            	lblStatusServer.setText("Http Server is not running");
                btnToggleServer.setText("Start");                
				btnToggleServer.setIcon(ICON_PLAY);
            }
            lblServerUrl.setVisible(serverRunning);
            lblQrCode.setVisible(serverRunning);
        } catch (Exception ex) {
            logger.error(ex);
        }
	}
	
    private void btnToggleServerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnToggleServerMousePressed
        
    }//GEN-LAST:event_btnToggleServerMousePressed

    private void menuFileExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileExitMousePressed
        //System.exit(0);
    }//GEN-LAST:event_menuFileExitMousePressed

    private void showServerUrl(String url){
//        InetAddr inetAddr = listInetNamesModel.getSelectedItem();
//        String url = inetAddr == null ? "" : "http://" + inetAddr.getIp() + ":" + ((IntTextField)textFieldPort).getValue() + "/client/index.html";
        lblServerUrl.setText(url);
        ((URLLabel)lblServerUrl).setURL(url);
		ByteArrayOutputStream out = QRCode.from(url).to(ImageType.PNG).withSize(220, 220).stream();
		ImageIcon imageIcon = new ImageIcon(out.toByteArray());
		lblQrCode.setIcon(imageIcon);
		lblQrCode.setText("");
    }
    
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
        SettingsBusiness settingsBusiness = BusinessFactory.getSettingsBusiness();
        Settings settings = settingsBusiness.get();
        settings.setServerPort(Integer.parseInt(textFieldPort.getText()));
        InetAddr inetAddr = (InetAddr) listInetNamesModel.getSelectedItem();
        settings.setPreferredServerInetName(inetAddr.getInetName());
        settings.setServerLastIp(inetAddr.getIp());
		settings.setRunAllNetInterfaces(jCheckBoxRunAllInterfaces.isSelected());
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
		
		panelApps.save();
		panelCategories.save();
		panelCommands.save();
		
        setVisible(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBrowsePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowsePathActionPerformed
        getFc().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
			textFieldPath.setText(file.getPath());
        }
    }//GEN-LAST:event_btnBrowsePathActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false);
		textFieldPath.setText("");
		copyToView();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void comboIconThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboIconThemeActionPerformed
    	changedSelectedIconTheme();
    }//GEN-LAST:event_comboIconThemeActionPerformed

    private void btnRefreshInetNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshInetNamesActionPerformed
        buildListInetNamesModel(listInetNamesModel.getSelectedItem() == null ? null : ((InetAddr)listInetNamesModel.getSelectedItem()).getInetName());
    }//GEN-LAST:event_btnRefreshInetNamesActionPerformed

    private void comboInetNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInetNamesActionPerformed
        changedSelectedInetName();
    }//GEN-LAST:event_comboInetNamesActionPerformed

	private void callExport(boolean justApps){
		ImportExport importExport = new ImportExport();
		try {
			String pathFile = importExport.exportJson(justApps);
			JOptionPane.showMessageDialog(this, "Database exported to " + pathFile);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
						UIConstants.ERROR_IMPORTEXPORT_EXCEPTION_BODY.replace("$ERR", ex.getMessage()), 
						UIConstants.ERROR_IMPORTEXPORT_EXCEPTION_TITLE,
						JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void callImport(boolean justApps){
		int n = JOptionPane.showConfirmDialog(
			this,
			justApps ? UIConstants.IMPORTAPPS_CONFIRM_BODY : UIConstants.IMPORTALL_CONFIRM_BODY,
			UIConstants.IMPORT_CONFIRM_TITLE,
			JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION){
			JFileChooser fc = MainWindow.getFc();
			fc.setCurrentDirectory(new File(".")); //new File(new File(".").getCanonicalPath());
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fc.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					return "json".equals(Utils.getFileExtension(f));
				}

				@Override
				public String getDescription() {
					return "JSON Restmote files";
				}
			});
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				ImportExport importExport = new ImportExport();
				try {
					importExport.importJsonFile(file.getAbsolutePath(), justApps);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this,
								UIConstants.ERROR_IMPORTEXPORT_EXCEPTION_BODY.replace("$ERR", ex.getMessage()), 
								UIConstants.ERROR_IMPORTEXPORT_EXCEPTION_TITLE,
								JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
    private void menuExportAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportAllActionPerformed
        callExport(false);
    }//GEN-LAST:event_menuExportAllActionPerformed

    private void menuExportAppsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportAppsActionPerformed
        callExport(true);
    }//GEN-LAST:event_menuExportAppsActionPerformed

    private void menuImportAppsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuImportAppsActionPerformed
        callImport(true);
    }//GEN-LAST:event_menuImportAppsActionPerformed

    private void menuImportAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuImportAllActionPerformed
        callImport(false);
    }//GEN-LAST:event_menuImportAllActionPerformed

	private void changedSelectedInetName(){
		if (Server.getInstance().isRunning()){
			int index = listInetNamesModel.getIndexOf(listInetNamesModel.getSelectedItem());
			if (index > -1){
				showServerUrl(Server.getInstance().getClientUrl(index));
			}
		}
	}
	
	private void changedSelectedIconTheme(){
		String theme = (String)listIconThemesModel.getSelectedItem();
		ImageList.getImageListModel().setTheme(theme);
		File creditsFile = new File(Utils.getThemeDir(theme) + "/credits");
		if (creditsFile.exists()){
			lblIconsCredits.setText("Credits: " + Utils.getContents(creditsFile));
		} else {
			lblIconsCredits.setText("");
		}
	}
    
    
    



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPath;
    private javax.swing.JButton btnBrowsePath;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeletePath;
    private javax.swing.JButton btnRefreshInetNames;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnToggleServer;
    private javax.swing.JComboBox comboIconTheme;
    private javax.swing.JComboBox comboInetNames;
    private javax.swing.JCheckBox jCheckBoxRunAllInterfaces;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblComboIconTheme;
    private javax.swing.JLabel lblComboInetNames;
    private javax.swing.JLabel lblComboPort;
    private javax.swing.JLabel lblIconsCredits;
    private javax.swing.JLabel lblPaths;
    private javax.swing.JLabel lblQrCode;
    private javax.swing.JLabel lblServerUrl;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusServer;
    private javax.swing.JLabel lblTextFieldName;
    private javax.swing.JLabel lblTextFieldNameRoot;
    private javax.swing.JLabel lblTextFieldScanDepth;
    private javax.swing.JList listImages;
    private javax.swing.JList listPaths;
    private javax.swing.JMenu menuAbout;
    private javax.swing.JMenu menuAdvanced;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuExport;
    private javax.swing.JMenuItem menuExportAll;
    private javax.swing.JMenuItem menuExportApps;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JMenu menuImport;
    private javax.swing.JMenuItem menuImportAll;
    private javax.swing.JMenuItem menuImportApps;
    private org.zooper.becuz.restmote.ui.panels.PanelApps panelApps;
    private org.zooper.becuz.restmote.ui.panels.PanelCategories panelCategories;
    private org.zooper.becuz.restmote.ui.panels.PanelCommands panelCommands;
    private javax.swing.JPanel panelSettings;
    private javax.swing.JPanel panelSettingsPnlGeneral;
    private javax.swing.JPanel panelSettingsPnlServer;
    private javax.swing.JTabbedPane panelTabs;
    private javax.swing.JScrollPane scrollPaneListPaths;
    private javax.swing.JTextField textFieldName;
    private javax.swing.JTextField textFieldNameRoot;
    private javax.swing.JTextField textFieldPath;
    private javax.swing.JTextField textFieldPort;
    private javax.swing.JTextField textFieldScanDepth;
    // End of variables declaration//GEN-END:variables

    
}
