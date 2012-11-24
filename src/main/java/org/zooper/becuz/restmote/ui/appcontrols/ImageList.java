package org.zooper.becuz.restmote.ui.appcontrols;

import java.awt.Component;
import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import org.zooper.becuz.restmote.ui.UIUtils;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * 
 * @author bebo
 */
@SuppressWarnings("serial")
public class ImageList extends JList<ImageIcon> {

	private static ImageListModel imageListModel = new ImageListModel();
	
	public ImageList(boolean readOnly) {
		setModel(imageListModel);
		setVisibleRowCount(0);
		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		setDragEnabled(true);
		//setDropMode(DropMode.ON_OR_INSERT);
		setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, false, false);
				setToolTipText(imageListModel.getName((ImageIcon)value));
				return this;
			}
		});
		if (!readOnly){
			setTransferHandler(new ImageListTransferHandler());
		}
	}

	public static ImageListModel getImageListModel(){
		return imageListModel;
	}
	
}
	

	

