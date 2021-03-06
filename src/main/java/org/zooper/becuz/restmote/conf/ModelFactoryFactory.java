package org.zooper.becuz.restmote.conf;

import org.zooper.becuz.restmote.exceptions.NotYetImplementedException;
import org.zooper.becuz.restmote.utils.Utils;

public class ModelFactoryFactory {

	private static ModelFactoryAbstract modelFactoryAbstract;
	
	public static ModelFactoryAbstract getModelFactoryAbstract(){
		if (modelFactoryAbstract == null){
			switch (Utils.getOs()) {
			case WINDOWS:
				modelFactoryAbstract = new ModelFactoryWindows(); 
				break;
			case MAC:
				modelFactoryAbstract = new ModelFactoryMac();
				break;
			case LINUX:
				modelFactoryAbstract = new ModelFactoryLinux();
				break;
			default:
				throw new NotYetImplementedException("ModelFactory " + Utils.getOs() + " not implemented");
			}
		}
		return modelFactoryAbstract;
	}
	
	
}
