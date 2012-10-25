package org.zooper.remosko.controller;

import java.util.logging.Logger;

import org.zooper.remosko.utils.Utils;
import org.zooper.remosko.utils.Utils.OS;

public class PcControllerFactory {

	protected static final Logger log = Logger.getLogger(PcControllerFactory.class.getName());
	
	private static PcControllerAbstract pcControllerAbstract;
	
	public static PcControllerAbstract getPcController(){
		if (pcControllerAbstract == null){
			OS os = Utils.getOs();
			if (os == OS.WINDOWS){
				pcControllerAbstract = new PcControllerWindows();
			} else if (os == OS.MAC){
				pcControllerAbstract = new PcControllerMac();
//			} else if (os == OS.LINUX){
//				pcControllerAbstract = new PcControllerLinux();
			}
		}
		return pcControllerAbstract;
	}
	
}
