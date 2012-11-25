/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zooper.becuz.restmote.ui.panels;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.ui.UIUtils;
import org.zooper.becuz.restmote.utils.Utils;

/**
 *
 * @author bebo
 */
@SuppressWarnings("serial")
public class PanelEditCategories extends javax.swing.JPanel {

	private JList listCategories;
	private DefaultListModel<MediaCategory> listMediaCategoriesModel;
	private DefaultComboBoxModel<App> comboAppsModel = new DefaultComboBoxModel<App>();
	
	
	public PanelEditCategories(
			JList listCategories,
			DefaultListModel<MediaCategory> listMediaCategoriesModel){
		this();
		this.listCategories = listCategories;
		this.listMediaCategoriesModel = listMediaCategoriesModel;
	}
	
	public void setModelAppData(Enumeration<App> apps){
		App prevSelectedApp = (App) comboAppsModel.getSelectedItem(); 
		comboAppsModel.removeAllElements();
		while (apps.hasMoreElements()) {
			App app = (App) apps.nextElement();
			comboAppsModel.addElement(app);
		}
		if (prevSelectedApp != null){
			comboAppsModel.setSelectedItem(prevSelectedApp);
		}
	}

	/**
	 * Creates new form PanelEditCategories
	 */
	public PanelEditCategories() {
		initComponents();
	}
	
	public void editMediaCategory(MediaCategory mediaCategory){
        textFieldNameCategory.setText(mediaCategory == null ? "" : mediaCategory.getName());
        textFieldDescriptionCategory.setText(mediaCategory == null ? "" : mediaCategory.getDescription());
        textFieldExtensionsCategory.setText(mediaCategory == null ? "" : Utils.join(mediaCategory.getExtensions(), ","));
		comboCategoryApp.setSelectedItem(mediaCategory == null ? null : mediaCategory.getApp());
    }

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		UIUtils.setEnabledRecursive(this, enabled);
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTextFieldNameCategory = new javax.swing.JLabel();
        textFieldNameCategory = new javax.swing.JTextField();
        lblTextFieldDescriptionCategory = new javax.swing.JLabel();
        textFieldDescriptionCategory = new javax.swing.JTextField();
        lblTextFieldExtensionsCategory = new javax.swing.JLabel();
        textFieldExtensionsCategory = new javax.swing.JTextField();
        btnEditCategoryCancel = new javax.swing.JButton();
        btnEditCategorySave = new javax.swing.JButton();
        comboCategoryApp = new javax.swing.JComboBox<App>();
        lblCategoryApp = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Edit"));

        lblTextFieldNameCategory.setFont(lblTextFieldNameCategory.getFont().deriveFont(lblTextFieldNameCategory.getFont().getStyle() | java.awt.Font.BOLD));
        lblTextFieldNameCategory.setText("Name");

        lblTextFieldDescriptionCategory.setText("Description");

        lblTextFieldExtensionsCategory.setFont(lblTextFieldExtensionsCategory.getFont().deriveFont(lblTextFieldExtensionsCategory.getFont().getStyle() | java.awt.Font.BOLD));
        lblTextFieldExtensionsCategory.setText("Extensions");

        btnEditCategoryCancel.setText("Cancel");
        btnEditCategoryCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCategoryCancelActionPerformed(evt);
            }
        });

        btnEditCategorySave.setText("Save");
        btnEditCategorySave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCategorySaveActionPerformed(evt);
            }
        });

        comboCategoryApp.setModel(comboAppsModel);

        lblCategoryApp.setText("App");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 224, Short.MAX_VALUE)
                        .addComponent(btnEditCategorySave)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditCategoryCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextFieldNameCategory, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTextFieldExtensionsCategory)
                            .addComponent(lblTextFieldDescriptionCategory, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCategoryApp, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldDescriptionCategory)
                            .addComponent(textFieldExtensionsCategory)
                            .addComponent(textFieldNameCategory)
                            .addComponent(comboCategoryApp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldNameCategory)
                    .addComponent(textFieldNameCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextFieldExtensionsCategory)
                    .addComponent(textFieldExtensionsCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldDescriptionCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTextFieldDescriptionCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCategoryApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCategoryApp))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditCategorySave)
                    .addComponent(btnEditCategoryCancel))
                .addContainerGap(126, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditCategoryCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCategoryCancelActionPerformed
        listCategories.clearSelection();
    }//GEN-LAST:event_btnEditCategoryCancelActionPerformed

    private void btnEditCategorySaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCategorySaveActionPerformed
        int selectedIndex = listCategories.getSelectedIndex();
        MediaCategory mediaCategory = selectedIndex == -1 ? null : listMediaCategoriesModel.getElementAt(selectedIndex);
        if (mediaCategory != null){
            mediaCategory.setName(textFieldNameCategory.getText());
            mediaCategory.setDescription(textFieldDescriptionCategory.getText());
            mediaCategory.setExtensions(new HashSet<>(Arrays.asList(textFieldExtensionsCategory.getText().split(","))));
			mediaCategory.setApp(comboAppsModel.getSelectedItem() == null ? null : (App) comboAppsModel.getSelectedItem());
        }
        listCategories.clearSelection();
    }//GEN-LAST:event_btnEditCategorySaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditCategoryCancel;
    private javax.swing.JButton btnEditCategorySave;
    private javax.swing.JComboBox comboCategoryApp;
    private javax.swing.JLabel lblCategoryApp;
    private javax.swing.JLabel lblTextFieldDescriptionCategory;
    private javax.swing.JLabel lblTextFieldExtensionsCategory;
    private javax.swing.JLabel lblTextFieldNameCategory;
    private javax.swing.JTextField textFieldDescriptionCategory;
    private javax.swing.JTextField textFieldExtensionsCategory;
    private javax.swing.JTextField textFieldNameCategory;
    // End of variables declaration//GEN-END:variables
}
