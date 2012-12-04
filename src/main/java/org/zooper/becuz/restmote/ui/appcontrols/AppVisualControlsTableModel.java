package org.zooper.becuz.restmote.ui.appcontrols;

import javax.swing.table.AbstractTableModel;

import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlsManager;
import org.zooper.becuz.restmote.model.KeysEvent;
import org.zooper.becuz.restmote.model.VisualControl;

@SuppressWarnings("serial")
public class AppVisualControlsTableModel extends AbstractTableModel{

    private VisualControl[][] data;

    public AppVisualControlsTableModel() {
    	data = new VisualControl[VisualControl.MAX_NUM_ROWS][VisualControl.MAX_NUM_COLS];
	}
    
	public void setData(ControlsManager<VisualControl> controlsManager){
		data = new VisualControl[VisualControl.MAX_NUM_ROWS][VisualControl.MAX_NUM_COLS];
		int delta = (VisualControl.MAX_NUM_COLS-1)/2;
		if (controlsManager.getControls() != null){
			for(VisualControl control: controlsManager.getControls()){
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
		VisualControl control;
		if (o == null){
			return;
		} else {
			control = (VisualControl) o;
			control.setName(imgName);
		}
		control.setHideImg(false);
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	//REFACTORCONTROL
//	public VisualControl createDefaultControlAt(String name, int rowIndex, int columnIndex, boolean add){
//		int delta = (VisualControl.MAX_NUM_COLS-1)/2;
//		Control control = Control.getControl(name, rowIndex+1, columnIndex - delta);
//		control.addKeysEvent(new KeysEvent());
//		if (add){
//			setValueAt(control, rowIndex, columnIndex);
//		}
//		return control;
//	}
	
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
		return data[rowIndex][columnIndex];
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = (VisualControl)aValue;
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
