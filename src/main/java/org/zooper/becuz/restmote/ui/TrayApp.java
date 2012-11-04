package org.zooper.becuz.restmote.ui;

/*
 * TrayIconDemo.java
 */

import java.awt.AWTException;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class TrayApp implements ActionListener {
    
	protected static final Logger log = Logger.getLogger(TrayApp.class.getName());
	
	//**********************************************
    
	private MainWindow mainWindow;
	
	private PopupMenu popup;
    
	private TrayIcon trayIcon;
    
	private SystemTray tray;
    
	private MenuItem aboutItem;
	
	private MenuItem settingsItem;
    
	private MenuItem exitItem;
	
    //**********************************************
    
	public MainWindow getMainWindow() {
		if (mainWindow == null){
			mainWindow = new MainWindow();
		}
		return mainWindow;
	}
	
    public PopupMenu getPopup() {
    	if (popup == null){
    		popup = new PopupMenu();
    		Menu displayMenu = new Menu("Display");
            MenuItem errorItem = new MenuItem("Error");
            MenuItem warningItem = new MenuItem("Warning");
            MenuItem infoItem = new MenuItem("Info");
            MenuItem noneItem = new MenuItem("None");
            displayMenu.add(errorItem);
            displayMenu.add(warningItem);
            displayMenu.add(infoItem);
            displayMenu.add(noneItem);
            
            aboutItem = new MenuItem("About");
        	settingsItem = new MenuItem("Settings");
        	exitItem = new MenuItem("Exit");
            
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(settingsItem);
//            popup.addSeparator();
//            popup.add(displayMenu);
            popup.add(exitItem);
    	}
		return popup;
	}

	public TrayIcon getTrayIcon() {
		if (trayIcon == null){
			trayIcon = new TrayIcon(
		    		UIUtils.createImage(
		    				MainWindow.class.getResource("images/remosko.png"), "remosko!!", false));
		}
		return trayIcon;
	}

	public void createAndShowGUI() {
		getMainWindow().setVisible(true);
		
        if (!SystemTray.isSupported()) {
        	log.severe("SystemTray is not supported");
        	JOptionPane.showMessageDialog(null, "SystemTray is not supported on your system!");
            //getMainWindow().setVisible(true);
        	return;
        }
        
        getTrayIcon().setImageAutoSize(true);
        
        trayIcon.setPopupMenu(getPopup());
        trayIcon.setToolTip(UIConstants.TRAY_TOOLTIP);
        
        try {
        	tray = SystemTray.getSystemTray();
            tray.add(trayIcon);
        } catch (AWTException e) {
        	String error = "TrayIcon could not be added.";
        	log.severe(error);
        	JOptionPane.showMessageDialog(null, error);
            getMainWindow().setVisible(true);
            return;
        }
        
        trayIcon.addActionListener(this);
        aboutItem.addActionListener(this);
        exitItem.addActionListener(this);
        
//        ActionListener listener = new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                MenuItem item = (MenuItem)e.getSource();
//                //TrayIcon.MessageType type = null;
//                System.out.println(item.getLabel());
//                if ("Error".equals(item.getLabel())) {
//                    //type = TrayIcon.MessageType.ERROR;
//                    trayIcon.displayMessage("Sun TrayIcon Demo",
//                            "This is an error message", TrayIcon.MessageType.ERROR);
//                    
//                } else if ("Warning".equals(item.getLabel())) {
//                    //type = TrayIcon.MessageType.WARNING;
//                    trayIcon.displayMessage("Sun TrayIcon Demo",
//                            "This is a warning message", TrayIcon.MessageType.WARNING);
//                    
//                } else if ("Info".equals(item.getLabel())) {
//                    //type = TrayIcon.MessageType.INFO;
//                    trayIcon.displayMessage("Sun TrayIcon Demo",
//                            "This is an info message", TrayIcon.MessageType.INFO);
//                    
//                } else if ("None".equals(item.getLabel())) {
//                    //type = TrayIcon.MessageType.NONE;
//                    trayIcon.displayMessage("Sun TrayIcon Demo",
//                            "This is an ordinary message", TrayIcon.MessageType.NONE);
//                }
//            }
//        };
//        
//        errorItem.addActionListener(listener);
//        warningItem.addActionListener(listener);
//        infoItem.addActionListener(listener);
//        noneItem.addActionListener(listener);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == trayIcon || e.getSource() == settingsItem){
			getMainWindow().setVisible(!mainWindow.isVisible());
		} else if (e.getSource() == aboutItem){
			JOptionPane.showMessageDialog(null, "Res(t)mote Control rocks. That's it.");
		} else if (e.getSource() == exitItem){
			 tray.remove(trayIcon);
             System.exit(0);
		}
	}
}