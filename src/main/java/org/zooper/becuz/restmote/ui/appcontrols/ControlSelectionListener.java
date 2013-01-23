package org.zooper.becuz.restmote.ui.appcontrols;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.zooper.becuz.restmote.model.ControlInterface;

/**
 * Listener for change selection and cell updates for table
 */
public class ControlSelectionListener implements ListSelectionListener, TableModelListener {

	private JTable table;
	private EditControl editControl;
	
	public ControlSelectionListener(JTable table, EditControl editControl) {
		super();
		this.table = table;
		this.editControl = editControl;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		ControlInterface controlSelected = null;
		if (e.getValueIsAdjusting()) {
			return;
		}
		int selectedColumn = table.getSelectedColumn();
		int selectedRow = table.getSelectedRow();
		if (selectedColumn > -1 && selectedRow > -1){
			controlSelected = ((HasControl)table.getModel()).getControlAt(selectedRow, selectedColumn);
		}
		editControl.setControl(controlSelected);
	}
	
	/**
	 * TODO it should be called after PanelEditApp does a tableModel.setData(app.getControlsManager().getControls());
	 * like in PanelVisualControl
	 * 
	 * It's not, caused in PanelEditApp, it's created before doing app.setModel
	 * 
	 * @param e 
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		ControlInterface controlSelected = null;
		int selectedColumn = table.getSelectedColumn();
		int selectedRow = table.getSelectedRow();
		if (selectedColumn > -1 && selectedRow > -1){
			ControlInterface value = ((HasControl)table.getModel()).getControlAt(selectedRow, selectedColumn);
			if (value != null){
				controlSelected = value;
			}
		}
		editControl.setControl(controlSelected);
	}
}