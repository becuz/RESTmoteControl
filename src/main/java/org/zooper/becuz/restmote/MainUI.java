package org.zooper.becuz.restmote;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.zooper.becuz.restmote.ui.TrayApp;

public class MainUI {

	public static void main(String[] args) throws Exception {
		new RestmoteControl(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new TrayApp().createAndShowGUI();
            }
        });
                
	}

}
