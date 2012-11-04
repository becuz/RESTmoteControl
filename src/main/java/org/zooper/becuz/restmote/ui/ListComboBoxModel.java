package org.zooper.becuz.restmote.ui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ListComboBoxModel<E> extends AbstractListModel<E>
		implements ComboBoxModel<E> {

	private static final long serialVersionUID = 1L;

	private E selectedItem;

	private List<E> list;

	public ListComboBoxModel(List<E> arrayList) {
		list = arrayList;
	}

	public E getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Object newValue) {
		selectedItem = (E) newValue;
	}

	public int getSize() {
		return list.size();
	}

	public E getElementAt(int i) {
		return list.get(i);
	}
	
	public List<E> getAll() {
		return list;
	}

}
