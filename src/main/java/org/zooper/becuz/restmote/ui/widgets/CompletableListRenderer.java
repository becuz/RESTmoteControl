package org.zooper.becuz.restmote.ui.widgets;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.zooper.becuz.restmote.model.interfaces.Completable;
import org.zooper.becuz.restmote.model.interfaces.Editable;
import org.zooper.becuz.restmote.ui.MainWindow;

@SuppressWarnings("serial")
public class CompletableListRenderer extends JLabel implements ListCellRenderer<Completable>{

	private static final ImageIcon ICON_OK = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/accept.png"));
	private static final ImageIcon ICON_WARNING = new ImageIcon(MainWindow.class.getResource("/org/zooper/becuz/restmote/ui/images/16/warning.png"));
	
	public CompletableListRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Completable> list,
			Completable value, int index, boolean isSelected, boolean cellHasFocus) {
		try {
			value.isComplete();
			setIcon(ICON_OK);
			setToolTipText(null);
		} catch (IllegalArgumentException e){
			setIcon(ICON_WARNING);
			String tooltip = "<html>";
			for(String error: e.getMessage().split("\n")){
				tooltip += "<br/>" + error;
			}
			tooltip += "</html>";
			setToolTipText(tooltip);
		}
		
		setText(((Editable)value).getName());
	    if(isSelected){
	    	setForeground(list.getSelectionForeground());
			setBackground(list.getSelectionBackground());
		}
		else {
			setForeground(list.getForeground());
	    	setBackground(Color.WHITE);
		}
		return this;
	}

}
