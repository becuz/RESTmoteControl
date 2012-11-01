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

	private static String dbVersion = null;
	private static String version = "0.1b";

	public static String getDbVersion(){
		if (dbVersion == null){
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(Utils.getRootDir() + "version")));
		        dbVersion = br.readLine();
		        br.close();
			} catch (Exception e){}
		}
        return dbVersion;
	}
	
	public static String getVersion() {
		return version;
	}
	
	public RestmoteControl(boolean develop) throws Exception {
		new PopulateDb().createAndPopulate(develop);

		// to cache everything, let's do several getAll
//		PersistenceAbstract persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
//		persistenceAbstract.getAll(Settings.class);
//		persistenceAbstract.getAll(MediaCategory.class);
//		persistenceAbstract.getAll(App.class);

		// scan for medias
		PcControllerFactory.getPcController().rootScan();

		// start http listener
		Server server = new Server();
		server.start();

		if (!getVersion().equals(getDbVersion())){
			File f = new File(Utils.getRootDir() + "version");
			f.delete();
			FileWriter fstream = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(version);
			out.close();
		}
	}

}
