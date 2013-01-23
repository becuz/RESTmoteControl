package org.zooper.becuz.restmote.ui.appcontrols;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import javax.swing.JTable;

import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.VisualControl;
import org.zooper.becuz.restmote.ui.UIConstants;

/**
 * 
 * @author bebo
 */
@SuppressWarnings("serial")
public class VisualControlsTable extends JTable implements DropTargetListener {
	
	private Logger logger = Logger.getLogger(VisualControlsTable.class.getName());
	
	@SuppressWarnings("unused")
	private DropTarget target; //a marker field, used to notify the system that it supports drag'n'drop

	public VisualControlsTable() {
		target = new DropTarget(this, this);
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		Point p = dtde.getLocation();
		Transferable t = dtde.getTransferable();
		DataFlavor[] d = t.getTransferDataFlavors();
		int row = rowAtPoint(p);
		int col = columnAtPoint(p);
		try {
			if (d[0].equals(DataFlavor.getTextPlainUnicodeFlavor())){
				String imgName = (String) t.getTransferData(d[0]);
				if (((AppVisualControlsTableModel)getModel()).setImageAt(imgName, row, col)){
					changeSelection(row, col, false, false);
				} else {
					JOptionPane.showMessageDialog(this,
						UIConstants.ERROR_DRAGDROP_IMG_CONTROL_BODY, 
						UIConstants.ERROR_DRAGDROP_IMG_CONTROL_TITLE,
						JOptionPane.ERROR_MESSAGE);
				}
			} else {//if (d[0].getMimeType().equals(DataFlavor.javaSerializedObjectMimeType)){
				Control control = (Control) t.getTransferData(d[0]);
				((AppVisualControlsTableModel)getModel()).setControlAt(control, row, col);
			}
		} catch (UnsupportedFlavorException ex) {
			logger.log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}

	}

}
