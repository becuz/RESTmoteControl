package org.zooper.becuz.restmote;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.ui.TrayApp;

public class MainUI {

	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		final TrayApp trayApp = new TrayApp();
		Server.getInstance().addServerStatusListeners(trayApp);
		new RestmoteControl().run(false);
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	trayApp.createAndShowGUI();
            }
        });
	}

}
