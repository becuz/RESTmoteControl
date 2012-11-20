package org.zooper.becuz.restmote.ui.appcontrols;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.ui.panels.PanelEditApps;

/**
 * 
 * @author bebo
 */
public class ControlSelectionListener implements ListSelectionListener {
	
	private int previousSelectedColumn = -1;
	private int previousSelectedRow = -1;
	
	private PanelEditApps panelEditApps;

	public ControlSelectionListener(PanelEditApps panelEditApps) {
		this.panelEditApps = panelEditApps;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		JTable table = panelEditApps.getTableControls();
		int selectedColumn = table.getSelectedColumn();
		int selectedRow = table.getSelectedRow();
		
		if (selectedColumn != previousSelectedColumn || selectedRow != previousSelectedRow){
			Control control = null;
			if (selectedColumn > -1 && selectedRow > -1){
				control = (Control) table.getModel().getValueAt(selectedRow, selectedColumn);	
			}
			panelEditApps.editControl(control);
			previousSelectedColumn = selectedColumn;
			previousSelectedRow = selectedRow;
		}

	}

}
