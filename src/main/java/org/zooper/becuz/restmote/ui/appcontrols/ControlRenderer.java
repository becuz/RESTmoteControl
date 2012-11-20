package org.zooper.becuz.restmote.ui.appcontrols;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.zooper.becuz.restmote.model.Control;

@SuppressWarnings("serial")
public class ControlRenderer extends JLabel implements TableCellRenderer {

	private ImageListModel imageListModel;
	
	public ControlRenderer(ImageListModel imageListModel) {
		this.imageListModel = imageListModel;
		setHorizontalAlignment(JLabel.CENTER);
//		setBorder(new BevelBorder(BevelBorder.LOWERED));
//		setPreferredSize(new Dimension(40, 16));
//		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//		setOpaque(true);
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
			Control control = (Control) value;
			if (Boolean.TRUE.equals(control.getHideImg())){
				setText(control.getText());
				setIcon(null);
			} else {
				//setIcon(getImageIcon(control.getName()));
				setIcon(imageListModel.getImageIcon(control.getName().toLowerCase()));
			}
		}
		if (isSelected){
			setBackground(new java.awt.Color(204, 255, 255));
			setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		} else {
			setBackground(null);
			setBorder(null);
		}
		return this;
	}
	
	
}
