package org.zooper.becuz.restmote.ui.widgets;

import javax.swing.JLabel;
import org.zooper.becuz.restmote.ui.UIUtils;

/**
 *
 * @author admin
 */
public class LblTooltip extends JLabel{

	public LblTooltip() {
		setIcon(UIUtils.ICON_TOOLTIP);
		setText("");
	}

}
