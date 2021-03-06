package org.zooper.becuz.restmote.ui.panels;

import java.util.List;

import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.interfaces.Persistable;

/**
 *
 * @author bebo
 */
@SuppressWarnings("serial")
public class PanelCategories extends PanelPersistables {

	/**
	 * Creates new form PanelCategories
	 */
	public PanelCategories() {
		initComponents();
		panelEditCategory.setEnabled(false);
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
        panelEditCategory = new org.zooper.becuz.restmote.ui.panels.PanelEditCategory(panelListPersistable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelListPersistable, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEditCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelEditCategory, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelListPersistable, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.zooper.becuz.restmote.ui.panels.PanelEditCategory panelEditCategory;
    private org.zooper.becuz.restmote.ui.panels.PanelListPersistable panelListPersistable;
    // End of variables declaration//GEN-END:variables

	@Override
	public void copyToView() {
		listModel.clear();
		List<MediaCategory> mediaCategories = BusinessFactory.getMediaCategoryBusiness().getAll();
		if (mediaCategories != null){
			for(MediaCategory mediaCategory: mediaCategories){
				listModel.addElement(mediaCategory);
			}
		}
	}
	
	@Override
	public Persistable getNew() {
		return new MediaCategory("name");
	}

	@Override
	public void edit(Persistable p) {
		panelEditCategory.edit(p);
	}

	public PanelEditCategory getPanelEditCategories(){
		return panelEditCategory;
	}
	
	@Override
	public void save() {
		MediaCategoryBusiness mediaCategoryBusiness = BusinessFactory.getMediaCategoryBusiness();
        for (int i = 0; i < listModel.size(); i++) {
        	mediaCategoryBusiness.store(listModel.get(i));
		}
		for(Persistable p: panelListPersistable.getBin()){
			mediaCategoryBusiness.delete((MediaCategory)p);
		}
	}
	
}
