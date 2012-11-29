/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zooper.becuz.restmote.ui.panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import org.zooper.becuz.restmote.model.interfaces.Persistable;
import org.zooper.becuz.restmote.ui.widgets.CompletableListRenderer;

/**
 *
 * @author admin
 */
public class PanelListPersistable extends javax.swing.JPanel {

	
	private PanelPersistables panelPersistables;
	
	private DefaultListModel<Persistable> listModel;
	
	/**
	 * 
	 */
	private List<Persistable> bin = new ArrayList<Persistable>();
	
	public PanelListPersistable(){
		initComponents();
	}
	
	/**
	 * Creates new form PanelListPersistable
	 */
	public PanelListPersistable(PanelPersistables panelPersistables) {
		this();
		this.panelPersistables = panelPersistables;
		this.listModel = panelPersistables.getListModel();
		list.setModel(listModel);
		list.setCellRenderer(new CompletableListRenderer());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDelete = new javax.swing.JButton();
        scrollPaneList = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        btnAdd = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("List"));
        setPreferredSize(new java.awt.Dimension(236, 515));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listValueChanged(evt);
            }
        });
        scrollPaneList.setViewportView(list);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/zooper/becuz/restmote/ui/images/16/add.png"))); // NOI18N
        btnAdd.setText("New");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneList, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 30, Short.MAX_VALUE)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneList, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedIndex = list.getSelectedIndex();
        Persistable p = selectedIndex == -1 ? null : listModel.getElementAt(selectedIndex);
        bin.add(p);
        listModel.remove(selectedIndex);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void listValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listValueChanged
        if (evt.getValueIsAdjusting()) {
			return;
		}
		int selectedIndex = list.getSelectedIndex();
        btnDelete.setEnabled(selectedIndex > -1);
        Persistable p = selectedIndex == -1 ? null : listModel.getElementAt(selectedIndex);
        panelPersistables.edit(p);
    }//GEN-LAST:event_listValueChanged

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        listModel.insertElementAt(panelPersistables.getNew(), 0);
        list.setSelectedIndex(0);
    }//GEN-LAST:event_btnAddActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JList list;
    private javax.swing.JScrollPane scrollPaneList;
    // End of variables declaration//GEN-END:variables

	public List<Persistable> getBin() {
		return bin;
	}

	void clearSelection() {
		list.clearSelection();
	}

	Persistable getSelectedItem() {
		int selectedIndex = list.getSelectedIndex();
        return selectedIndex == -1 ? null : listModel.getElementAt(selectedIndex);
	}
}
