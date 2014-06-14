package org.zooper.becuz.restmote;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.ui.Tray;

/**
 * Entry point to start the server without user interface
 * @author bebo
 *
 *	TODO
 *	children should be sorted alpabetichally
 *
 *  the section with the controls is quiet big. (Expecially in music) users would mostly choose the media, no play with controls.
 *  therefore the secion visibility should be toggled easily somehow.
 *
 */
public class MainUI {

	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			UIManager.put("TextArea.disabledBackground", Color.GRAY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		final Tray tray = new Tray();
		Server.getInstance().addServerStatusListeners(tray);
		new RestmoteControl().run(true);
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	tray.createAndShowGUI();
            }
        });
	}

}
