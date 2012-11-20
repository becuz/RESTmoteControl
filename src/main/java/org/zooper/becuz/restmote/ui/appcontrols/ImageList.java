package org.zooper.becuz.restmote.ui.appcontrols;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

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
