package org.zooper.becuz.restmote.ui.model;

import javax.swing.table.AbstractTableModel;

import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlsManager;

public class AppTableModel extends AbstractTableModel{

    private String[][] data;

    public AppTableModel() {
    	data = new String[Control.MAX_NUM_ROWS][Control.MAX_NUM_COLS];
	}
    
	public void setData(ControlsManager controlsManager){
		data = new String[Control.MAX_NUM_ROWS][Control.MAX_NUM_COLS];
		int delta = (Control.MAX_NUM_COLS-1)/2;
		for(Control control: controlsManager.getControls()){
			data[control.getRow()][control.getPosition()+delta] = (control == null ? "null" : control.getName());
		}
		fireTableDataChanged();
	}
	
	public void clearData(){
		data = null;
		fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int column) {
		return ""+column;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
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

}
