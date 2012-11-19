package org.zooper.becuz.restmote.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class UIUtils {

	public static ImageIcon createImageIcon(URL url, Integer w, Integer h){
		return createImageIcon(url, null, w, h);
	}
	
	public static ImageIcon createImageIcon(URL url, String description, Integer w, Integer h){
        ImageIcon imageIcon = new ImageIcon(url, description);
        if (w != null && h != null){
        	imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH));
        }
        return imageIcon;
    }
	
	public static Image createImage(URL url, String description, boolean checkPresence){
		return createImageIcon(url, description, null, null).getImage();
    }
	
	public static void setEnabledRecursive(Component c, boolean enabled){
		if (c instanceof Container) {
			Container containter = (Container) c;
			for (Component c2: containter.getComponents()){
				c2.setEnabled(enabled);
				setEnabledRecursive(c2, enabled);
			}
		}
	}

}
