package org.zooper.becuz.restmote.ui.appcontrols;

import java.util.ArrayList;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlInterface;
import org.zooper.becuz.restmote.model.KeysEvent;
import org.zooper.becuz.restmote.model.VisualControl;
import org.zooper.becuz.restmote.persistence.export.ImportExport;

@SuppressWarnings("serial")
public class AppVisualControlsTableModel extends AbstractTableModel implements HasControl{

    private VisualControl[][] data;

    public AppVisualControlsTableModel() {
    	data = new VisualControl[VisualControl.MAX_NUM_ROWS][VisualControl.MAX_NUM_COLS];
	}
    
	public void setData(Set<VisualControl> controls){
		data = new VisualControl[VisualControl.MAX_NUM_ROWS][VisualControl.MAX_NUM_COLS];
		int delta = (VisualControl.MAX_NUM_COLS-1)/2;
		if (controls != null){
			for(VisualControl control: controls){
				data[control.getRow()-1][control.getColumn()+delta] = control;//(control == null ? "null" : control.getName());
			}
		}
		fireTableDataChanged();
	}
	
	public void clearData(){
		data = null;
		fireTableDataChanged();
	}
	
	public boolean setImageAt(String imgName, int rowIndex, int columnIndex){
		Object o = getValueAt(rowIndex, columnIndex);
		VisualControl control;
		if (o == null){
			return false;
		}
		control = (VisualControl) o;
		control.setName(imgName);
		control.setHideImg(false);
		fireTableCellUpdated(rowIndex, columnIndex);
		return true;
	}
	
	@Override
	public String getColumnName(int column) {
		return ""+column;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Control.class;
	}
	
	@Override
	public int getRowCount() {
		return data == null ? 0 :VisualControl.MAX_NUM_ROWS;
	}

	@Override
	public int getColumnCount() {
		return data == null ? 0 :VisualControl.MAX_NUM_COLS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data == null ? null : data[rowIndex][columnIndex];
	}
	
	@Override
	public ControlInterface getControlAt(int row, int column) {
		return (ControlInterface) getValueAt(row, column);
	}
	
	/**
	 * Set the control at a specified location
	 * @param control
	 * @param rowIndex
	 * @param columnIndex 
	 */
	public void setControlAt(Control control, int rowIndex, int columnIndex) {
		VisualControl visualControl = (VisualControl) getValueAt(rowIndex, columnIndex);
		if (visualControl == null){
			int delta = (VisualControl.MAX_NUM_COLS-1)/2;
			String keys = ImportExport.getStringFromControlKeys(new ArrayList<KeysEvent>(control.getKeysEvents()));
			visualControl = new VisualControl(keys, rowIndex+1, columnIndex - delta);
			visualControl.setText(keys);
			visualControl.setHideImg(true);
			setValueAt(visualControl, rowIndex, columnIndex);
		}
		visualControl.setControl(control);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = (VisualControl)aValue;
		fireTableCellUpdated(rowIndex, columnIndex);
	}


}
