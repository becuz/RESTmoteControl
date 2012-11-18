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

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.http.ServerStatusListener;

public class TrayApp implements ActionListener, ServerStatusListener {
    
	protected static final Logger log = Logger.getLogger(TrayApp.class.getName());
	
	//**********************************************
    
	private MainWindow mainWindow;
	
	private PopupMenu popup;
    
	private TrayIcon trayIcon;
    
	private SystemTray tray;
    
	private MenuItem aboutItem;
	private MenuItem settingsItem;
	private MenuItem exitItem;
	private MenuItem startStopItem;
	private MenuItem restartItem;
	
    //**********************************************
    
	private MainWindow getMainWindow(boolean create) {
		if (create && mainWindow == null){
			mainWindow = new MainWindow();
		}
		return mainWindow;
	}
	
	private PopupMenu getPopup() {
    	if (popup == null){
    		popup = new PopupMenu();
    		Menu displayMenu = new Menu("Server");
            startStopItem = new MenuItem("Stop");
            restartItem = new MenuItem("Restart");
            displayMenu.add(startStopItem);
            displayMenu.add(restartItem);
            
            aboutItem = new MenuItem("About");
        	settingsItem = new MenuItem("Settings");
        	exitItem = new MenuItem("Exit");
            
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(settingsItem);
            popup.addSeparator();
           // popup.add(displayMenu);
            popup.add(exitItem);
    	}
		return popup;
	}

	public TrayIcon getTrayIcon() {
		if (trayIcon == null){
			trayIcon = new TrayIcon(
		    		UIUtils.createImage(MainWindow.class.getResource("images/remosko.png"), null, false));
			trayIcon.setImageAutoSize(true);
	        trayIcon.setPopupMenu(getPopup());
	        trayIcon.setToolTip(UIConstants.TRAY_TOOLTIP);
		}
		return trayIcon;
	}

	public void createAndShowGUI() {
		getMainWindow(true).setVisible(true);
		
        if (!SystemTray.isSupported()) {
        	log.warn("SystemTray is not supported");
        	JOptionPane.showMessageDialog(null, "SystemTray is not supported on your system!");
            //getMainWindow(true).setVisible(true);
        	return;
        }
        
        try {
        	tray = SystemTray.getSystemTray();
            tray.add(getTrayIcon());
        } catch (AWTException e) {
        	String error = "TrayIcon could not be added.";
        	log.error(error);
        	JOptionPane.showMessageDialog(null, error);
            //getMainWindow(true).setVisible(true);
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
			getMainWindow(true).setVisible(!mainWindow.isVisible());
		} else if (e.getSource() == aboutItem){
			JOptionPane.showMessageDialog(null, "Res(t)mote Control rocks. That's it.");
		} else if (e.getSource() == exitItem){
			 tray.remove(trayIcon);
             System.exit(0);
		}
	}

	@Override
	public void statusChanged(boolean running) {
		MainWindow mainWindow = getMainWindow(false);
		if (mainWindow != null){
			mainWindow.updateViewStatusServer();
		}
		
	}
}