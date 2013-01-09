package org.zooper.becuz.restmote.ui.appcontrols;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.zooper.becuz.restmote.model.Control;

@SuppressWarnings("serial")
public class AppControlsTableModel extends AbstractTableModel{

    private List<Control> data;

    public AppControlsTableModel() {
    	data = new ArrayList<Control>();
	}
    
	public void setData(Set<Control> controls){
		data = new ArrayList<Control>(controls);
		fireTableDataChanged();
	}
	
	public void clearData(){
		data.clear();
		fireTableDataChanged();
	}
	
	public Control getControlAt(int rowIndex){
		return data.get(rowIndex);
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0){
			return "Shortcut";
		}
		if (column == 1){
			return "Description";
		}
		return ""+column;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	@Override
	public int getRowCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Control c = data.get(rowIndex);
		if (columnIndex == 0){
			return PanelControlKeys.getKeysAsString(c, -1);
		}
		if (columnIndex == 1){
			return c.getDescription();
		}
		return "";
	}
	
	

}
