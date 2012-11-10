package org.zooper.becuz.restmote.ui.oop;

import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.business.MediaCategoryBusiness;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.interfaces.Persistable;
import org.zooper.becuz.restmote.ui.MainWindow;
import org.zooper.becuz.restmote.utils.Utils;

public class JPanelListMediaCategories extends JPList{

	public JPanelListMediaCategories(){
		super();
	}
	
	public JPanelListMediaCategories(MainWindow mainWindow, MediaCategoryBusiness appBusiness) {
		super(mainWindow, appBusiness);
	}

	@Override
	protected void edit(Persistable p) {
		MediaCategory mediaCategory = (MediaCategory) p;
		mainWindow.getTextFieldNameCategory().setText(mediaCategory == null ? "" : mediaCategory.getName());
		mainWindow.getTextFieldDescriptionCategory().setText(mediaCategory == null ? "" : mediaCategory.getDescription());
		mainWindow.getTextFieldExtensionsCategory().setText(mediaCategory == null ? "" : Utils.join(mediaCategory.getExtensions(), ","));
	}

	@Override
	protected Persistable create() {
		return new MediaCategory("name");
	}

}
