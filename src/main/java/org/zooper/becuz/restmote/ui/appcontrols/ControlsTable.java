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
public class ControlsTable extends JTable implements DropTargetListener {
	
	private DropTarget target;

	public ControlsTable() {
		target = new DropTarget(this, this);
		// setTransferHandler(new MyTransferHandler());
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
			((AppTableModel)getModel()).setImageAt(imgName, row, col);
		} catch (UnsupportedFlavorException ex) {
			Logger.getLogger(ControlsTable.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ControlsTable.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

//	class MyTransferHandler extends TransferHandler {
//
//		// tests for a valid JButton DataFlavor
//		public boolean canImport(JComponent c, DataFlavor[] f) {
//			// DataFlavor temp = new DataFlavor(DnDButton.class, "JButton");
//			// for(DataFlavor d:f){
//			// if(d.equals(temp))
//			// return true;
//			//
//			// }
//			// return false;
//			return true;
//		}
//
//		// add the data into the JTable
//		public boolean importData(JComponent comp, Transferable t, Point p) {
//			int row = rowAtPoint(p);
//			int col = columnAtPoint(p);
//
//			// //if a value is in the JTable cell, do nothing
//			// if(model.getValueAt(row, col) != null) return false;
//			//
//			// try {
//			// DnDButton tempButton = (DnDButton)t.getTransferData(new
//			// DataFlavor(DnDButton.class, "JButton"));
//			// rows[row][col] = tempButton;
//			//
//			// } catch (UnsupportedFlavorException ex) {
//			// Logger.getLogger(DnDTable.class.getName()).log(Level.SEVERE,
//			// null, ex);
//			// } catch (IOException ex) {
//			// Logger.getLogger(DnDTable.class.getName()).log(Level.SEVERE,
//			// null, ex);
//			// }
//			return true;
//		}
//
//	}

}