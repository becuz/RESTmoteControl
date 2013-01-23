package org.zooper.becuz.restmote.ui.appcontrols;

import org.zooper.becuz.restmote.model.ControlInterface;

/**
 * Interface for {@link AppControlsTableModel} and {@link AppVisualControlsTableModel}
 * @author bebo
 *
 */
public interface HasControl {

	/**
	 * return the control at the specifed position
	 * @param row
	 * @param column
	 * @return
	 */
	public ControlInterface getControlAt(int row, int column);
	
	/**
	 * return row and column of the control, -1, -1 if not found
	 * @param c
	 * @return
	 */
	public int[] getControlPosition(ControlInterface c);
}
