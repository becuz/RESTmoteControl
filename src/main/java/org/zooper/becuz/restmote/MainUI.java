package org.zooper.becuz.restmote;

import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.ui.Tray;

public class MainUI {

	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		final Tray tray = new Tray();
		Server.getInstance().addServerStatusListeners(tray);
		new RestmoteControl().run(false);
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	tray.createAndShowGUI();
            }
        });
        //ToolTipManager.sharedInstance().setInitialDelay(0);

	}

}
