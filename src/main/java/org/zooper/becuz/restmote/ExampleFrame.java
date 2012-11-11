package org.zooper.becuz.restmote;

import java.awt.BorderLayout;
import java.awt.Frame;

public class ExampleFrame extends Frame {
	
	public ExampleFrame() throws Exception {
		super("Embedded PApplet");
		setSize(1124, 868);
		setLayout(new BorderLayout());
		add(Hackathon.getInstance(), BorderLayout.CENTER);
		Hackathon.getInstance().init();
		// important to call this whenever embedding a PApplet.
		// It ensures that the animation thread is started and
		// that other internal variables are properly set.
		
//		((Hackathon)embed).createEllipse(100, 100, 20, 20);
//		((Hackathon)embed).setI(50);
		Hackathon.getInstance().repaint();
	}
}