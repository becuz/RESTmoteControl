package org.zooper.becuz.restmote.ui.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelStatus extends JPanel{

	public PanelStatus() {
		setLayout(new GridLayout(1, 1));
		JLabel filler = new JLabel("PanelStatus");
		filler.setHorizontalAlignment(JLabel.CENTER);
		add(filler);
	}
	
}
