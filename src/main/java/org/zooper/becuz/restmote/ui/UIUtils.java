package org.zooper.becuz.restmote.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class UIUtils {

	public static final ImageIcon ICON_TOOLTIP = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/tooltip.png"));
	public static final ImageIcon ICON_REFRESH = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/refresh.png"));
	public static final ImageIcon ICON_DELETE = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/delete.png"));
	public static final ImageIcon ICON_ADD = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/add.png"));
	public static final ImageIcon ICON_EDIT = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/edit.png"));
	public static final ImageIcon ICON_LEFT = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/arrow_left.png"));
	public static final ImageIcon ICON_RIGHT = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/arrow_right.png"));
	
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
//		if(c.getClass().equals(JScrollPane.class)) {
//			for(Component scrollPaneC : ((JScrollPane)c).getComponents()) {
//				scrollPaneC.setEnabled(enabled);
//			}
//		}
	}

}
