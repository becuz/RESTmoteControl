package org.zooper.becuz.restmote.ui;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class UIConstants {

	
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
	
}
