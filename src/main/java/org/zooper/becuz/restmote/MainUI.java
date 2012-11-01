package org.zooper.becuz.restmote;

import org.zooper.becuz.restmote.ui.NewJFrame;

public class MainUI {

	public static void main(String[] args) throws Exception {
		new RestmoteControl(false);
//		try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
	}

}
