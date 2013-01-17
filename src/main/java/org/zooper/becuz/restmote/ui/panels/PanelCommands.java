/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zooper.becuz.restmote.ui.panels;

import java.util.List;

import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.model.Command;
import org.zooper.becuz.restmote.model.interfaces.Persistable;

/**
 *
 * @author bebo
 */
@SuppressWarnings("serial")
public class PanelCommands extends PanelPersistables {

	/**
	 * Creates new form PanelCommands
	 */
	public PanelCommands() {
		initComponents();
		panelEditCommand.setEnabled(false);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelListPersistable = new org.zooper.becuz.restmote.ui.panels.PanelListPersistable(this);
        panelEditCommand = new org.zooper.becuz.restmote.ui.panels.PanelEditCommand(panelListPersistable);

        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(806, 535));

        panelEditCommand.setPreferredSize(new java.awt.Dimension(414, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelListPersistable, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditCommand, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelListPersistable, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                    .addComponent(panelEditCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.zooper.becuz.restmote.ui.panels.PanelEditCommand panelEditCommand;
    private org.zooper.becuz.restmote.ui.panels.PanelListPersistable panelListPersistable;
    // End of variables declaration//GEN-END:variables

	@Override
	public void copyToView() {
		listModel.clear();
		List<Command> commands = BusinessFactory.getCommandBusiness().getAll();
		if (commands != null){
			for(Command command: commands){
				listModel.addElement(command);
			}
		}
	}

	@Override
	public void save() {
        for (int i = 0; i < listModel.size(); i++) {
        	BusinessFactory.getCommandBusiness().store(listModel.get(i));
		}
		for(Persistable p: panelListPersistable.getBin()){
			BusinessFactory.getCommandBusiness().delete((Command)p);
		}
	}

	@Override
	public Persistable getNew() {
		return new Command("new command", "new command", "");
	}

	@Override
	public void edit(Persistable p) {
		panelEditCommand.edit(p);
	}
}
