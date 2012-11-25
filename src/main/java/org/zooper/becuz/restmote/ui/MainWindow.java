package org.zooper.becuz.restmote.ui;

import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

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
import org.zooper.becuz.restmote.ui.appcontrols.ImageList;
import org.zooper.becuz.restmote.ui.model.ListComboBoxModel;
import org.zooper.becuz.restmote.ui.panels.PanelEditCategories;
import org.zooper.becuz.restmote.ui.widgets.CompletableListRenderer;
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
    private DefaultListModel<MediaCategory> listMediaCategoriesModel;
    
	 /**
     * Swing list model for {@link #listApps} 
     */
    private DefaultListModel<App> listAppsModel;
    
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
		listMediaCategoriesModel = new DefaultListModel<MediaCategory>();
    	listPathsModel = new DefaultListModel<String>();
		listIconThemesModel = new DefaultComboBoxModel<String>();
		listAppsModel = new DefaultListModel<App>();
		listAppsModel.addListDataListener(new ListDataListener() {
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
				panelEditCategories.setModelAppData(listAppsModel.elements());
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				panelEditCategories.setModelAppData(listAppsModel.elements());
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
				panelEditCategories.setModelAppData(listAppsModel.elements());
			}
		});
		
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

	private void buildListInetNamesModel(String selectInetName){
		listInetNamesModel.clear();
		listInetNamesModel.addAll(Server.getInstance().getLocalInetAddresses());
		if (!Utils.isEmpty(selectInetName)){
    		for(InetAddr inetAddr: listInetNamesModel.getAll()){
    			if(inetAddr.getInetName().equals(selectInetName)){
    				listInetNamesModel.setSelectedItem(inetAddr);
    				break;
    			}
    		}
    	}
	}
	
    /**
     * Builds the swing models
     */
	private void copyToView(){
		logger.info("copyToView()");
		
		listPathsModel.clear();
		listMediaCategoriesModel.clear();
		listAppsModel.clear();
		
		Settings settings = PcControllerFactory.getPcController().getSettingsBusiness().get();
		buildListInetNamesModel(settings.getServerInetName());
    	
		listIconThemesModel.setSelectedItem(settings.getIconControlsTheme());
		
		if (settings.getPaths() != null){
	    	for(String path: settings.getPaths()){
	    		listPathsModel.addElement(path);
	    	}
		}
		
		List<MediaCategory> mediaCategories = PcControllerFactory.getPcController().getMediaCategoryBusiness().getAll();
		if (mediaCategories != null){
			for(MediaCategory mediaCategory: mediaCategories){
				listMediaCategoriesModel.addElement(mediaCategory);
			}
		}
		
		List<App> apps = PcControllerFactory.getPcController().getAppBusiness().getAll();
		if (apps != null){
			for(App app: apps){
				listAppsModel.addElement(app);
			}
		}
		
		textFieldPort.setText(""+settings.getServerPort());
        textFieldName.setText(settings.getName());
        textFieldNameRoot.setText(settings.getNameRoot());
        textFieldScanDepth.setText(""+settings.getScanDepth());
	}
	
	/**
	 * called after {@link #initComponents()}
	 */
	private void postInitComponents(){
		panelEditCategories.setEnabled(false);
		panelEditApps.setEnabled(false);
		listApps.setCellRenderer(new CompletableListRenderer());
		listCategories.setCellRenderer(new CompletableListRenderer());
		lblQrCode.setHorizontalAlignment(JLabel.CENTER);
		lblServerUrl.setHorizontalAlignment(JLabel.CENTER);
		updateViewStatusServer();
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
        lblTooltip1 = new org.zooper.becuz.restmote.ui.widgets.LblTooltip();
        panelApps = new javax.swing.JPanel();
        lblAppsSummary = new javax.swing.JLabel();
        panelAppsPnlList = new javax.swing.JPanel();
        btnDeleteApp = new javax.swing.JButton();
        scrollPaneListApps = new javax.swing.JScrollPane();
        listApps = new javax.swing.JList();
        btnAddApp = new javax.swing.JButton();
        panelEditApps = new org.zooper.becuz.restmote.ui.panels.PanelEditApps(listApps, listAppsModel);
        panelCategories = new javax.swing.JPanel();
        panelCategoriesPnlList = new javax.swing.JPanel();
        lblListCategories = new javax.swing.JLabel();
        btnDeleteCategory = new javax.swing.JButton();
        scrollPaneListCategories = new javax.swing.JScrollPane();
        listCategories = new javax.swing.JList();
        btnAddCategory = new javax.swing.JButton();
        lblCategoriesSummary = new javax.swing.JLabel();
        panelEditCategories = new PanelEditCategories(listCategories, listMediaCategoriesModel);
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

        textFieldPort.addActionListener(formListener);

        lblStatusServer.setText("Checking server status..");

        btnToggleServer.setIcon(ICON_PLAY);
        btnToggleServer.addMouseListener(formListener);
        btnToggleServer.addActionListener(formListener);

        lblServerUrl.setText("server URL");

        btnRefreshInetNames.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/refresh.png"))); // NOI18N
        btnRefreshInetNames.setFocusPainted(false);
        btnRefreshInetNames.addActionListener(formListener);

        lblQrCode.setText("qrCode");

        javax.swing.GroupLayout panelSettingsPnlServerLayout = new javax.swing.GroupLayout(panelSettingsPnlServer);
        panelSettingsPnlServer.setLayout(panelSettingsPnlServerLayout);
        panelSettingsPnlServerLayout.setHorizontalGroup(
            panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblServerUrl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblQrCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSettingsPnlServerLayout.createSequentialGroup()
                        .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comboInetNames, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                                .addGap(0, 101, Short.MAX_VALUE)
                                .addComponent(lblComboPort)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefreshInetNames, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSettingsPnlServerLayout.createSequentialGroup()
                        .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblComboInetNames)
                            .addComponent(btnToggleServer, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStatusServer, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSettingsPnlServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComboPort))
                .addGap(46, 46, 46)
                .addComponent(lblStatusServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnToggleServer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblQrCode, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblServerUrl)
                .addGap(26, 26, 26))
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

        lblPaths.setText("Choose the directories that the system will monitor for medias");

        lblTextFieldScanDepth.setLabelFor(lblTextFieldScanDepth);
        lblTextFieldScanDepth.setText("ScanDepth");

        btnBrowsePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/folder.png"))); // NOI18N
        btnBrowsePath.addActionListener(formListener);

        lblComboIconTheme.setLabelFor(comboIconTheme);
        lblComboIconTheme.setText("Icon Theme");

        comboIconTheme.setModel(listIconThemesModel);
        comboIconTheme.addActionListener(formListener);

        listImages.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listImages.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        jScrollPane1.setViewportView(listImages);

        lblIconsCredits.setText("credits");

        lblTooltip1.setToolTipText(UIConstants.TOOLTIP_STNGS_NAME);

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
                                    .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                                        .addComponent(btnBrowsePath, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAddPath, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                    .addComponent(btnDeletePath, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
                            .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTextFieldName)
                                    .addComponent(lblTextFieldNameRoot)
                                    .addComponent(lblTextFieldScanDepth)
                                    .addComponent(lblComboIconTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                                        .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(textFieldNameRoot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                            .addComponent(textFieldScanDepth, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(textFieldName))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTooltip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(comboIconTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSettingsPnlGeneralLayout.createSequentialGroup()
                        .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblIconsCredits, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPaths, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
        );
        panelSettingsPnlGeneralLayout.setVerticalGroup(
            panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsPnlGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldName)
                    .addComponent(textFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTooltip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldNameRoot)
                    .addComponent(textFieldNameRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldScanDepth)
                    .addComponent(textFieldScanDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(panelSettingsPnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComboIconTheme)
                    .addComponent(comboIconTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIconsCredits)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
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

        lblAppsSummary.setText("Edit one existing app, or create a new one. ");

        panelAppsPnlList.setBorder(javax.swing.BorderFactory.createTitledBorder("List"));

        btnDeleteApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/delete.png"))); // NOI18N
        btnDeleteApp.setEnabled(false);
        btnDeleteApp.addActionListener(formListener);

        listApps.setModel(listAppsModel);
        listApps.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listApps.addListSelectionListener(formListener);
        scrollPaneListApps.setViewportView(listApps);

        btnAddApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/add.png"))); // NOI18N
        btnAddApp.setText("New");
        btnAddApp.addActionListener(formListener);

        javax.swing.GroupLayout panelAppsPnlListLayout = new javax.swing.GroupLayout(panelAppsPnlList);
        panelAppsPnlList.setLayout(panelAppsPnlListLayout);
        panelAppsPnlListLayout.setHorizontalGroup(
            panelAppsPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAppsPnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAppsPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneListApps, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAppsPnlListLayout.createSequentialGroup()
                        .addGap(0, 26, Short.MAX_VALUE)
                        .addComponent(btnDeleteApp, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnAddApp, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelAppsPnlListLayout.setVerticalGroup(
            panelAppsPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAppsPnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneListApps, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAppsPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteApp)
                    .addComponent(btnAddApp))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelAppsLayout = new javax.swing.GroupLayout(panelApps);
        panelApps.setLayout(panelAppsLayout);
        panelAppsLayout.setHorizontalGroup(
            panelAppsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAppsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAppsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAppsSummary)
                    .addComponent(panelAppsPnlList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panelEditApps, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAppsLayout.setVerticalGroup(
            panelAppsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAppsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAppsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelEditApps, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addGroup(panelAppsLayout.createSequentialGroup()
                        .addComponent(lblAppsSummary)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelAppsPnlList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        panelTabs.addTab("Apps", new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/apps.png")), panelApps); // NOI18N

        panelCategoriesPnlList.setBorder(javax.swing.BorderFactory.createTitledBorder("List"));
        panelCategoriesPnlList.setOpaque(false);
        panelCategoriesPnlList.setPreferredSize(new java.awt.Dimension(284, 379));
        panelCategoriesPnlList.setRequestFocusEnabled(false);

        lblListCategories.setText("<html>Edit one category from below <br/>or create a new one:</html>");

        btnDeleteCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/delete.png"))); // NOI18N
        btnDeleteCategory.setEnabled(false);
        btnDeleteCategory.addActionListener(formListener);

        listCategories.setModel(listMediaCategoriesModel);
        listCategories.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listCategories.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
                    .addGroup(panelCategoriesPnlListLayout.createSequentialGroup()
                        .addComponent(lblListCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCategoriesPnlListLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelCategoriesPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrollPaneListCategories, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panelCategoriesPnlListLayout.setVerticalGroup(
            panelCategoriesPnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCategoriesPnlListLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblListCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddCategory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPaneListCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addComponent(panelEditCategories, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE))
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
                    .addComponent(panelEditCategories, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                    .addComponent(panelCategoriesPnlList, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelTabs.addTab("Categories", new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/categories.png")), panelCategories); // NOI18N

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
            else if (evt.getSource() == textFieldPort) {
                MainWindow.this.textFieldPortActionPerformed(evt);
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
            else if (evt.getSource() == btnDeleteApp) {
                MainWindow.this.btnDeleteAppActionPerformed(evt);
            }
            else if (evt.getSource() == btnAddApp) {
                MainWindow.this.btnAddAppActionPerformed(evt);
            }
            else if (evt.getSource() == btnDeleteCategory) {
                MainWindow.this.btnDeleteCategoryActionPerformed(evt);
            }
            else if (evt.getSource() == btnAddCategory) {
                MainWindow.this.btnAddCategoryActionPerformed(evt);
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
            else if (evt.getSource() == listApps) {
                MainWindow.this.listAppsValueChanged(evt);
            }
            else if (evt.getSource() == listCategories) {
                MainWindow.this.listCategoriesValueChanged(evt);
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
			boolean serverRunning = Server.getInstance().isRunning();
            if (serverRunning){
            	lblStatusServer.setText("Http Server is running!");
                btnToggleServer.setText("Stop");
				btnToggleServer.setIcon(ICON_STOP);
            } else {
            	lblStatusServer.setText("Http Server is not running");
                btnToggleServer.setText("Start");                
				btnToggleServer.setIcon(ICON_PLAY);
            }
            refreshLblServerUrl(Server.getInstance().getClientUrl());
            lblServerUrl.setVisible(serverRunning);
            lblQrCode.setVisible(serverRunning);
        } catch (Exception ex) {
            logger.error(ex);
        }
	}
	
    private void btnToggleServerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnToggleServerMousePressed
        
    }//GEN-LAST:event_btnToggleServerMousePressed

    private void menuFileExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileExitMousePressed
        System.exit(0);
    }//GEN-LAST:event_menuFileExitMousePressed

    private void refreshLblServerUrl(String url){
//        InetAddr inetAddr = listInetNamesModel.getSelectedItem();
//        String url = inetAddr == null ? "" : "http://" + inetAddr.getIp() + ":" + ((IntTextField)textFieldPort).getValue() + "/client/index.html";
        lblServerUrl.setText(url);
        ((URLLabel)lblServerUrl).setURL(url);
		ByteArrayOutputStream out = QRCode.from(url).to(ImageType.PNG).withSize(220, 220).stream();
		ImageIcon imageIcon = new ImageIcon(out.toByteArray());
		lblQrCode.setIcon(imageIcon);
		lblQrCode.setText("");
    }
    
    private void comboInetNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInetNamesActionPerformed
        //refreshLblServerUrl();
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
        for (int i = 0; i < listMediaCategoriesModel.size(); i++) {
        	mediaCategoryBusiness.store(listMediaCategoriesModel.get(i));
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

    private void btnAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoryActionPerformed
        MediaCategory mediaCategory = new MediaCategory("name");
        listMediaCategoriesModel.insertElementAt(mediaCategory, 0);
        listCategories.setSelectedIndex(0);
    }//GEN-LAST:event_btnAddCategoryActionPerformed

    private void btnBrowsePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowsePathActionPerformed
        getFc().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
			textFieldPath.setText(file.getPath());
        }
    }//GEN-LAST:event_btnBrowsePathActionPerformed

    private void listCategoriesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listCategoriesValueChanged
    	int selectedIndex = listCategories.getSelectedIndex();
    	if (evt.getValueIsAdjusting() == false) {
            btnDeleteCategory.setEnabled(selectedIndex > -1);
			panelEditCategories.setEnabled(selectedIndex > -1);
			//if (selectedIndex == -1){
				//btnEditCategoryCancel.setEnabled(false);
				//btnEditCategorySave.setEnabled(false);
			//}
        }
        MediaCategory mediaCategory = selectedIndex == -1 ? null : listMediaCategoriesModel.getElementAt(selectedIndex);
        panelEditCategories.editMediaCategory(mediaCategory);
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
			panelEditApps.setEnabled(selectedIndex > -1);
        }
        App app = selectedIndex == -1 ? null : listAppsModel.getElementAt(selectedIndex);
        panelEditApps.editApp(app);
    }//GEN-LAST:event_listAppsValueChanged

    private void btnAddAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAppActionPerformed
        App app = new App("name");
        listAppsModel.insertElementAt(app, 0);
        listApps.setSelectedIndex(0);
    }//GEN-LAST:event_btnAddAppActionPerformed

    private void btnDeleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCategoryActionPerformed
        int selectedIndex = listCategories.getSelectedIndex();
        MediaCategory mediaCategory = selectedIndex == -1 ? null : listMediaCategoriesModel.getElementAt(selectedIndex);
        binMediaCateogories.add(mediaCategory);
		listMediaCategoriesModel.remove(selectedIndex);
    }//GEN-LAST:event_btnDeleteCategoryActionPerformed

    private void comboIconThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboIconThemeActionPerformed
    	changedIconTheme();
    }//GEN-LAST:event_comboIconThemeActionPerformed

    private void textFieldPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldPortActionPerformed

    private void btnRefreshInetNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshInetNamesActionPerformed
        buildListInetNamesModel(listInetNamesModel.getSelectedItem() == null ? null : listInetNamesModel.getSelectedItem().getInetName());
    }//GEN-LAST:event_btnRefreshInetNamesActionPerformed

	private void changedIconTheme(){
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
    private javax.swing.JButton btnAddApp;
    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnAddPath;
    private javax.swing.JButton btnBrowsePath;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeleteApp;
    private javax.swing.JButton btnDeleteCategory;
    private javax.swing.JButton btnDeletePath;
    private javax.swing.JButton btnRefreshInetNames;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnToggleServer;
    private javax.swing.JComboBox comboIconTheme;
    private javax.swing.JComboBox comboInetNames;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAppsSummary;
    private javax.swing.JLabel lblCategoriesSummary;
    private javax.swing.JLabel lblComboIconTheme;
    private javax.swing.JLabel lblComboInetNames;
    private javax.swing.JLabel lblComboPort;
    private javax.swing.JLabel lblIconsCredits;
    private javax.swing.JLabel lblListCategories;
    private javax.swing.JLabel lblPaths;
    private javax.swing.JLabel lblQrCode;
    private javax.swing.JLabel lblServerUrl;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusServer;
    private javax.swing.JLabel lblTextFieldName;
    private javax.swing.JLabel lblTextFieldNameRoot;
    private javax.swing.JLabel lblTextFieldScanDepth;
    private org.zooper.becuz.restmote.ui.widgets.LblTooltip lblTooltip1;
    private javax.swing.JList listApps;
    private javax.swing.JList listCategories;
    private javax.swing.JList listImages;
    private javax.swing.JList listPaths;
    private javax.swing.JMenu menuAbout;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JPanel panelApps;
    private javax.swing.JPanel panelAppsPnlList;
    private javax.swing.JPanel panelCategories;
    private javax.swing.JPanel panelCategoriesPnlList;
    private org.zooper.becuz.restmote.ui.panels.PanelEditApps panelEditApps;
    private org.zooper.becuz.restmote.ui.panels.PanelEditCategories panelEditCategories;
    private javax.swing.JPanel panelSettings;
    private javax.swing.JPanel panelSettingsPnlGeneral;
    private javax.swing.JPanel panelSettingsPnlServer;
    private javax.swing.JTabbedPane panelTabs;
    private javax.swing.JScrollPane scrollPaneListApps;
    private javax.swing.JScrollPane scrollPaneListCategories;
    private javax.swing.JScrollPane scrollPaneListPaths;
    private javax.swing.JTextField textFieldName;
    private javax.swing.JTextField textFieldNameRoot;
    private javax.swing.JTextField textFieldPath;
    private javax.swing.JTextField textFieldPort;
    private javax.swing.JTextField textFieldScanDepth;
    // End of variables declaration//GEN-END:variables

    
}
