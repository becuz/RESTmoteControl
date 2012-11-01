package org.zooper.becuz.restmote.ui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RemoskoUI {

	public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        final TrayApp trayApp = new TrayApp();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                trayApp.createAndShowGUI();
            }
        });
    }
	
}
