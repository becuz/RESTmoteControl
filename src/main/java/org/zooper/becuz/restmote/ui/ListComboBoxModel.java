package org.zooper.becuz.restmote.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import org.zooper.becuz.restmote.model.App;

public class ListComboBoxModel<E> extends AbstractListModel<E>
		implements ComboBoxModel<E> {

	private static final long serialVersionUID = 1L;

	private E selectedItem;

	private List<E> list;

	public ListComboBoxModel() {
		list = new ArrayList<E>();
	}
	
//	public ListComboBoxModel(List<E> list) {
//		this.list = list;
//	}
//	
//	public ListComboBoxModel(Collection<E> col) {
//		this.list = new ArrayList<E>(col);
//	}

	public void addAll(Collection<E> list){
        if (this.list == null){
        	this.list = new ArrayList<E>();
        }
        this.list.addAll(list);
    }
	
	public void clear(){
        if (this.list != null){
        	this.list.clear();
        }
    }
	
    public void insertElementAt(E e, int i){
        list.add(i, e);
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

	public void addElement(E e) {
		this.list.add(e);
		
	}

}
