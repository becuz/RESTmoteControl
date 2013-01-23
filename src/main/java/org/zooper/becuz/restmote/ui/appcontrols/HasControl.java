package org.zooper.becuz.restmote.ui.appcontrols;

import org.zooper.becuz.restmote.model.ControlInterface;

public interface HasControl {

	public ControlInterface getControlAt(int row, int column);
	
}
