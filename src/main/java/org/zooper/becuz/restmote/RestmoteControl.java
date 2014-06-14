package org.zooper.becuz.restmote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.zooper.becuz.restmote.business.MediaBusiness;
import org.zooper.becuz.restmote.business.SettingsBusiness;
import org.zooper.becuz.restmote.http.Server;
import org.zooper.becuz.restmote.model.Settings;
import org.zooper.becuz.restmote.utils.PopulateDb;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * 
 * @author bebo
 *
 */
public class RestmoteControl {

	/**
	 * Version currently "installed" in the machine.
	 * It's retrieved through the string written in the file /version
	 */
	private static String installedVersion = null;
	
	/**
	 * Version of the runtime system. Has to be upgraded when the are updates in the persistence model layer. 
	 */
	private static String version = "0.037b";

	/**
	 * @return the version currently installed in the machine, accordingly to what is written in the file /version
	 */
	public static String getInstalledVersion(){
		if (installedVersion == null){
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(Utils.getRootDir() + "version")));
		        installedVersion = br.readLine();
		        br.close();
			} catch (Exception e){}
		}
        return installedVersion;
	}
	
	/**
	 * @return {@link #version}
	 */
	public static String getVersion() {
		return version;
	}
	
	public RestmoteControl() {}
	
	/**
	 * 
	 * @param forcePopulateDb
	 * @throws Exception
	 */
	public void run(boolean forcePopulateDb) throws Exception {
		new PopulateDb().createAndPopulate(forcePopulateDb);

		// to cache everything, let's do several getAll
//		PersistenceAbstract persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
//		persistenceAbstract.getAll(Settings.class);
//		persistenceAbstract.getAll(MediaCategory.class);
//		persistenceAbstract.getAll(App.class);

		// scan for medias
		new MediaBusiness().rootScan(); //TODO it slows down the UI. Should be done in a more smart way

		// start http listener
		SettingsBusiness settingsBusiness = new SettingsBusiness(); 
		Settings settings = settingsBusiness.get();
		if (Boolean.TRUE.equals(settings.getRunAllNetInterfaces())){
			Server.getInstance().startAll(settings.getServerPort());
		} else {
			Server.getInstance().start(settings.getPreferredServerInetName(), settings.getServerPort());
		}
		
		if (!getVersion().equals(getInstalledVersion())){
			File f = new File(Utils.getRootDir() + "version");
			f.delete();
			FileWriter fstream = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(version);
			out.close();
		}
	}

}
