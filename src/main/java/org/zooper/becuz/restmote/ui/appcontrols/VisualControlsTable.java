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

import javax.swing.JTable;

/**
 * 
 * @author bebo
 */
@SuppressWarnings("serial")
public class VisualControlsTable extends JTable implements DropTargetListener {
	
	@SuppressWarnings("unused")
	private DropTarget target;

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
			String imgName = (String) t.getTransferData(d[0]);
			((AppVisualControlsTableModel)getModel()).setImageAt(imgName, row, col);
			changeSelection(row, col, false, false);
		} catch (UnsupportedFlavorException ex) {
			Logger.getLogger(VisualControlsTable.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(VisualControlsTable.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
