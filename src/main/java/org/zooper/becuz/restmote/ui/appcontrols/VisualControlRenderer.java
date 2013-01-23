package org.zooper.becuz.restmote.ui.appcontrols;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.zooper.becuz.restmote.model.VisualControl;

@SuppressWarnings("serial")
public class VisualControlRenderer extends JLabel implements TableCellRenderer {

	public VisualControlRenderer() {
		setOpaque(true);
		setHorizontalAlignment(JLabel.CENTER);
//		setBorder(new BevelBorder(BevelBorder.LOWERED));
	}

	@Override
	public Component getTableCellRendererComponent(
			JTable table, 
			Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null){
			setText("");
			setIcon(null);
		} else {
			VisualControl control = (VisualControl) value;
			if (Boolean.TRUE.equals(control.getHideImg())){
				setText(control.getName());
				setIcon(null);
			} else {
				ImageIcon imageIcon = ImageList.getImageListModel().getImageIcon(control.getName().toLowerCase());
				setIcon(imageIcon);
				setText(imageIcon != null ? null : control.getName());
			}
		}
		if (isSelected){
			setBackground(new java.awt.Color(102, 255, 255));
			setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		} else {
			setBackground(null);
			setBorder(null);
		}
		return this;
	}
	
	
}
