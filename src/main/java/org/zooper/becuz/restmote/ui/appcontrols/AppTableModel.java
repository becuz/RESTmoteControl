package org.zooper.becuz.restmote.ui.appcontrols;

import javax.swing.table.AbstractTableModel;

import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlsManager;

@SuppressWarnings("serial")
public class AppTableModel extends AbstractTableModel{

    private Control[][] data;

    public AppTableModel() {
    	data = new Control[Control.MAX_NUM_ROWS][Control.MAX_NUM_COLS];
	}
    
	public void setData(ControlsManager controlsManager){
		data = new Control[Control.MAX_NUM_ROWS][Control.MAX_NUM_COLS];
		int delta = (Control.MAX_NUM_COLS-1)/2;
		if (controlsManager.getControls() != null){
			for(Control control: controlsManager.getControls()){
				data[control.getRow()-1][control.getPosition()+delta] = control;//(control == null ? "null" : control.getName());
			}
		}
		fireTableDataChanged();
	}
	
	public void clearData(){
		data = null;
		fireTableDataChanged();
	}
	
	public void setImageAt(String imgName, int rowIndex, int columnIndex){
		Object o = getValueAt(rowIndex, columnIndex);
		Control control = null;
		if (o == null){
			int delta = (Control.MAX_NUM_COLS-1)/2;
			control = Control.getControl(imgName, rowIndex+1, columnIndex - delta);
			setValueAt(control, rowIndex, columnIndex);
		} else {
			control = (Control) o;
			control.setName(imgName);
		}
		fireTableCellUpdated(rowIndex, columnIndex);
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
		return data == null ? 0 :Control.MAX_NUM_ROWS;
	}

	@Override
	public int getColumnCount() {
		return data == null ? 0 :Control.MAX_NUM_COLS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = (Control)aValue;
	}

}
