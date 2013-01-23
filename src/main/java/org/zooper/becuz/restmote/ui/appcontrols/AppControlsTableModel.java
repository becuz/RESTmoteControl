package org.zooper.becuz.restmote.ui.appcontrols;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlInterface;
import org.zooper.becuz.restmote.model.KeysEvent;
import org.zooper.becuz.restmote.persistence.export.ImportExport;

@SuppressWarnings("serial")
public class AppControlsTableModel extends AbstractTableModel implements HasControl{

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
	
	@Override
	public ControlInterface getControlAt(int rowIndex, int colIndex){
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
			return ImportExport.getStringFromControlKeys(new ArrayList<KeysEvent>(c.getKeysEvents()), -1);
		}
		if (columnIndex == 1){
			return c.getDescription();
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Control c = data.get(rowIndex);
		if (columnIndex == 1){
			c.setDescription((String) aValue);
		} else {}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	public void addControl(){
		Control c = new Control("name");
		c.addKeysEvent(new KeysEvent(65));
		data.add(c);
		fireTableRowsInserted(data.size()-1, data.size()-1);
	}
	
	public Control removeControl(int index){
		if (index > -1 && index < data.size()){
			Control control = data.remove(index);
			fireTableRowsDeleted(index, index);
			return control;
		}
		return null;
	}

}
