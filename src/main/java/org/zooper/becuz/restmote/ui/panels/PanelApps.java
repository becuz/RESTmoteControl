package org.zooper.becuz.restmote.ui.panels;

import java.util.List;

import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.interfaces.Persistable;

/**
 *
 * @author bebo
 */
public class PanelApps extends PanelPersistables{

	/**
	 * Creates new form PanelApps
	 */
	public PanelApps() {
		initComponents();
		panelEditApp.setEnabled(false);
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
        panelEditApp = new org.zooper.becuz.restmote.ui.panels.PanelEditApp(panelListPersistable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelListPersistable, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditApp, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelListPersistable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEditApp, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.zooper.becuz.restmote.ui.panels.PanelEditApp panelEditApp;
    private org.zooper.becuz.restmote.ui.panels.PanelListPersistable panelListPersistable;
    // End of variables declaration//GEN-END:variables

	@Override
	public void copyToView() {
		listModel.clear();
		List<App> apps = BusinessFactory.getAppBusiness().getAll();
		if (apps != null){
			for(App app: apps){
				listModel.addElement(app);
			}
		}
	}

	@Override
	public Persistable getNew() {
		return new App("name");
	}

	@Override
	public void edit(Persistable p) {
		panelEditApp.edit((App)p);
	}

	@Override
	public void save() {
		AppBusiness appBusiness = BusinessFactory.getAppBusiness();
        for (int i = 0; i < listModel.size(); i++) {
        	appBusiness.store(listModel.get(i));
		}
		
		for(Persistable p: panelListPersistable.getBin()){
			appBusiness.delete((App)p);
		}
	}
}
