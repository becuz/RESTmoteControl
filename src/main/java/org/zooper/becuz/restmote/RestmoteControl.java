package org.zooper.becuz.restmote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.utils.PopulateDb;
import org.zooper.becuz.restmote.utils.Utils;

public class RestmoteControl {

	/**
	 * Version currently installed in the machine.
	 * It's retrieved through the string written in the vile /version
	 */
	private static String dbVersion = null;
	
	/**
	 * Version of the runtime system.
	 */
	private static String version = "0.1b";

	/**
	 * @return the version currently installed in the machine, accordingly to what is written in the file /version
	 */
	public static String getDbVersion(){
		if (dbVersion == null){
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(Utils.getRestmoteRootDirAbsolutePath() + "version")));
		        dbVersion = br.readLine();
		        br.close();
			} catch (Exception e){}
		}
        return dbVersion;
	}
	
	/**
	 * @return {@link #version}
	 */
	public static String getVersion() {
		return version;
	}
	
	/**
	 * 
	 * @param develop
	 * @throws Exception
	 */
	public RestmoteControl(boolean develop) throws Exception {
		new PopulateDb().createAndPopulate(develop);

		// to cache everything, let's do several getAll
//		PersistenceAbstract persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
//		persistenceAbstract.getAll(Settings.class);
//		persistenceAbstract.getAll(MediaCategory.class);
//		persistenceAbstract.getAll(App.class);

		// scan for medias
		PcControllerFactory.getPcController().rootScan(); //TODO it slows down the UI. Should be done in a more smart way

		// start http listener
		Server server = Server.getInstance();
		server.start();

		if (!getVersion().equals(getDbVersion())){
			File f = new File(Utils.getRestmoteRootDirAbsolutePath() + "version");
			f.delete();
			FileWriter fstream = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(version);
			out.close();
		}
	}

}
