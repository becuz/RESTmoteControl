package org.zooper.becuz.restmote;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.ui.Tray;

/**
 * Entry point to start the server without user interface
 * @author bebo
 */
public class RestmoteMain {

	public static void main(String[] args) throws Exception {
		boolean ui = false;
		boolean forcePopulateDb = false;
		if (args != null && args.length > 0){
			ui = Boolean.parseBoolean(args[1]);
			if (args.length > 1) {
				forcePopulateDb = Boolean.parseBoolean(args[1]);
			}
		}
		if (ui){
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	//			UIManager.put("TextArea.disabledBackground", Color.GRAY);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
			final Tray tray = new Tray();
			Server.getInstance().addServerStatusListeners(tray);
			new RestmoteControl().run(forcePopulateDb);
			SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	tray.createAndShowGUI();
	            }
	        });
		} else {
			new RestmoteControl().run(forcePopulateDb);
		}
	}

}
