package org.zooper.becuz.restmote.ui.appcontrols;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.ui.panels.PanelEditApp;

/**
 * {@link TransferHandler} for {@link Control}.
 * It's used to drag'n'drop a {@link Control} from {@link PanelEditApp#tableControls} to {@link PanelEditApp#tableVisualControls}
 * @author bebo
 */
@SuppressWarnings("serial")
public class ControlTransferHandler extends TransferHandler {

	public int getSourceActions(JComponent source) {
		return COPY;
	}

	protected Transferable createTransferable(JComponent source) {
		JTable t = (JTable) source;
		int index = t.getSelectedRow();
		if (index < 0)
			return null;
		Control control = (Control) ((HasControl)t.getModel()).getControlAt(index, -1);
		return new ControlTransferable(control);
	}
}

class ControlTransferable implements Transferable {
	//application/x-java-jvm-local-objectref; class=org.zooper.becuz.restmote.model.Control
	String s = DataFlavor.javaJVMLocalObjectMimeType +"; class=" + Control.class.getName() + "";
	
	private Control control;
	
	public ControlTransferable(Control control) {
		this.control = control;
	}

	public DataFlavor[] getTransferDataFlavors() {
		try {
			return new DataFlavor[] { new DataFlavor(s)};
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.getMimeType().equals(s);
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException {
		if (flavor.getMimeType().equals(s)) {
			return control;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}
}
