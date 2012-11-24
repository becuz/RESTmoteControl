package org.zooper.becuz.restmote.ui.appcontrols;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import org.zooper.becuz.restmote.ui.UIUtils;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * Model of all client icons
 * TODO the ones in system/ are ignored
 * @author bebo
 */
@SuppressWarnings("serial")
public class ImageListModel extends DefaultListModel<ImageIcon>{

	private static final int SIZE_ICON = 32;
	
	/**
	 * img name (no path, no extension) => ImageIcon
	 */
	private Map<String, ImageIcon> imgs = new LinkedHashMap<>();
	
	/**
	 * Setting the theme reload the img from clint/images/{theme}/
	 * @param theme 
	 */
	public void setTheme(String theme) {
		clear();
		imgs.clear();
		File f = new File(Utils.getThemeDir(theme));		
		FilenameFilter filenameFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".png");
			}
		};
		for (File imgFile : f.listFiles(filenameFilter)){
			try {
				ImageIcon imgIcon = UIUtils.createImageIcon(imgFile.toURI().toURL(), SIZE_ICON, SIZE_ICON);
				String name = imgFile.getName().substring(0, imgFile.getName().lastIndexOf('.')); 
				addElement(imgIcon);
				imgs.put(name.toLowerCase(), imgIcon);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getName(ImageIcon imageIcon){
		for(Entry<String, ImageIcon> e: imgs.entrySet()){
			if (e.getValue().equals(imageIcon)){
				return e.getKey();
			}
		}
		return null;
	}
	
	public ImageIcon getImageIcon(String name){
		return imgs.get(name.toLowerCase());
	}
	
}
