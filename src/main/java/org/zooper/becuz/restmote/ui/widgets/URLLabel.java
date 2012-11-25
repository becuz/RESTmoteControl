package org.zooper.becuz.restmote.ui.widgets;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JLabel;

/**
 * 
 * @author ludovicianul
 */
public class URLLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	private static final String TEMPLATE = "<html><a href=\"URL\" >URL</a></html>";
	private String url;

	public URLLabel() {
		this("");
	}

	public URLLabel(String url) {
		super(TEMPLATE.replaceAll("URL", url));
		this.url = url;
		setForeground(Color.BLUE.darker());
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new URLOpenAdapter());
	}

	public void setURL(String url) {
		this.url = url;
	}

	private class URLOpenAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (Throwable t) {
					//
				}
			}
		}
	}
}