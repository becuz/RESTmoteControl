package org.zooper.remosko.serverui;

/*
 * TrayIconDemo.java
 */

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

public class TrayApp implements ActionListener, ItemListener {
    
	private MainWindow mainWindow;
	
	//**********************************************
    
	private PopupMenu popup = new PopupMenu();
    private TrayIcon trayIcon = new TrayIcon(UIConstants.createImage("resources/remosko.png", "remosko!!", false));
    private SystemTray tray = SystemTray.getSystemTray();
    private CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
    private MenuItem aboutItem = new MenuItem("About");
    private MenuItem exitItem = new MenuItem("Exit");
	
    //**********************************************
    
    private MainWindow getMainWindow() {
		if (mainWindow == null){
			mainWindow = new MainWindow();
		}
    	return mainWindow;
	}

    public void createAndShowGUI() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        trayIcon.setImageAutoSize(true);
        
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb2);
        popup.addSeparator();
        popup.add(displayMenu);
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        
        trayIcon.addActionListener(this);
        aboutItem.addActionListener(this);
        exitItem.addActionListener(this);
        cb2.addItemListener(this);
        
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem)e.getSource();
                //TrayIcon.MessageType type = null;
                System.out.println(item.getLabel());
                if ("Error".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.ERROR;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an error message", TrayIcon.MessageType.ERROR);
                    
                } else if ("Warning".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);
                    
                } else if ("Info".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.INFO;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);
                    
                } else if ("None".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.NONE;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an ordinary message", TrayIcon.MessageType.NONE);
                }
            }
        };
        
        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);
    }

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == cb2){
			int cb2Id = e.getStateChange();
	        if (cb2Id == ItemEvent.SELECTED){
	            trayIcon.setToolTip("Sun TrayIcon");
	        } else {
	            trayIcon.setToolTip(null);
	        }
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == trayIcon){
//			getMainWindow().pack();
			getMainWindow().setVisible(!mainWindow.isVisible());
		} else if (e.getSource() == aboutItem){
			JOptionPane.showMessageDialog(null, "This dialog box is run from About");
		} else if (e.getSource() == exitItem){
			 tray.remove(trayIcon);
             System.exit(0);
		}
	}
}