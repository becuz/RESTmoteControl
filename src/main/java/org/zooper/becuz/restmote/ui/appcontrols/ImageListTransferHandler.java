package org.zooper.becuz.restmote.ui.appcontrols;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class ImageListTransferHandler extends TransferHandler {

	public int getSourceActions(JComponent source) {
		return COPY;
	}

	protected Transferable createTransferable(JComponent source) {
		JList<?> list = (JList<?>) source;
		int index = list.getSelectedIndex();
		if (index < 0)
			return null;
		ImageIcon icon = (ImageIcon) list.getModel().getElementAt(index);
		String nameImg = ((ImageListModel) list.getModel()).getName(icon);
		return new NameTransferable(nameImg);
	}
}

class NameTransferable implements Transferable {
	
	private String name;
	
	public NameTransferable(String name) {
		this.name = name;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { DataFlavor.getTextPlainUnicodeFlavor()};
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(DataFlavor.getTextPlainUnicodeFlavor());
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException {
		if (flavor.equals(DataFlavor.getTextPlainUnicodeFlavor())) {
			return name;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}
}
