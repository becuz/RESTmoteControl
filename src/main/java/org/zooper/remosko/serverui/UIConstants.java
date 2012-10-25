package org.zooper.remosko.serverui;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

public class UIConstants {

	public static ImageIcon createImageIcon(String path, String description, boolean checkPresence){
		return createImageIcon(path, description, null, null, checkPresence);
	}
	
	public static ImageIcon createImageIcon(String path, String description, Integer w, Integer h, boolean checkPresence){
        String absolutePath = path;
        if (checkPresence){
			File f = new File(path);
	        if (!f.exists()) {
	            System.err.println("Resource not found: " + path);
	            return null;
	        }
	        absolutePath = f.getAbsolutePath();
        }
        ImageIcon imageIcon = new ImageIcon(absolutePath, description);
        if (w != null && h != null){
        	imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH));
        }
        return imageIcon;
        
    }
	
	public static Image createImage(String path, String description, boolean checkPresence){
		return createImageIcon(path, description, null, null, checkPresence).getImage();
    }
	
}
