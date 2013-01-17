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
import org.zooper.becuz.restmote.business.SettingsBusiness;
import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.http.ServerStatusListener;
import org.zooper.becuz.restmote.model.Settings;

public class Tray implements ActionListener, ServerStatusListener {

	protected static final Logger log = Logger.getLogger(Tray.class.getName());

	// **********************************************

	private MainWindow mainWindow;

	private PopupMenu popup;

	private TrayIcon trayIcon;

	private SystemTray tray;

	private MenuItem aboutItem;
	private MenuItem settingsItem;
	private MenuItem exitItem;
	private MenuItem startStopItem;
//	private MenuItem restartItem;

	// **********************************************

	private MainWindow getMainWindow(boolean create) {
		if (create && mainWindow == null) {
			mainWindow = new MainWindow();
		}
		return mainWindow;
	}

	private PopupMenu getPopup() {
		if (popup == null) {
			popup = new PopupMenu();
			Menu displayMenu = new Menu("Server");
			startStopItem = new MenuItem(Server.getInstance().isRunning() ? "Stop" : "Start");
			//restartItem = new MenuItem("Restart");
			displayMenu.add(startStopItem);
			//displayMenu.add(restartItem);

			aboutItem = new MenuItem("About");
			settingsItem = new MenuItem("Open");
			exitItem = new MenuItem("Exit");

			aboutItem.addActionListener(this);
			startStopItem.addActionListener(this);
			settingsItem.addActionListener(this);
			exitItem.addActionListener(this);

			popup.add(aboutItem);
			popup.addSeparator();
			popup.add(settingsItem);
			popup.addSeparator();
			popup.add(displayMenu);
			popup.add(exitItem);
		}
		return popup;
	}

	public TrayIcon getTrayIcon() {
		if (trayIcon == null) {
			trayIcon = new TrayIcon(UIUtils.createImage(
					MainWindow.class.getResource("images/remosko.png"), null,
					false));
			trayIcon.setImageAutoSize(true);
			trayIcon.setPopupMenu(getPopup());
			trayIcon.setToolTip(UIConstants.TRAY_TOOLTIP);
		}
		return trayIcon;
	}

	public void createAndShowGUI() {

		if (!SystemTray.isSupported()) {
			log.warn("SystemTray is not supported");
			JOptionPane.showMessageDialog(null,
					"SystemTray is not supported on your system!");
			getMainWindow(true).setVisible(true);
			return;
		}

		try {
			tray = SystemTray.getSystemTray();
			tray.add(getTrayIcon());
			trayIcon.addActionListener(this);
		} catch (AWTException e) {
			String error = "TrayIcon could not be added.";
			log.error(error);
			JOptionPane.showMessageDialog(null, error);
			getMainWindow(true).setVisible(true);
			return;
		}

		//to show the window as default, enable this
		getMainWindow(true).setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == trayIcon || e.getSource() == settingsItem) {
			getMainWindow(true).setVisible(!mainWindow.isVisible());
		} else if (e.getSource() == aboutItem) {
			JOptionPane.showMessageDialog(null, UIConstants.TEXT_ABOUT);
		} else if (e.getSource() == startStopItem) {
			MainWindow mainWindow = getMainWindow(false);
			if (mainWindow != null) {
				mainWindow.toggleServer();
			} else {
				try {
					Settings settings = new SettingsBusiness().get();
					Server.getInstance().toggleAll(settings.getServerPort());//(settings.getPreferredServerInetName(), settings.getServerPort());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, UIConstants.TEXT_ERROR_SERVER_FROM_TRAY);
				}
			}
		} else if (e.getSource() == exitItem) {
			tray.remove(trayIcon);
			System.exit(0);
		}
	}

	@Override
	public void serverStatusChanged(boolean running) {
		if (startStopItem != null){
			startStopItem.setLabel(running ? "Stop" : "Start");
		}
		if (mainWindow != null) {
			mainWindow.updateViewStatusServer();
		}
	}
}