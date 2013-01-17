package org.zooper.becuz.restmote.persistence.export;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.zooper.becuz.restmote.RestmoteControl;
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.conf.rest.RestFactory;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.persistence.PersistenceAbstract;
import org.zooper.becuz.restmote.persistence.PersistenceFactory;

public class ImportExport {

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	
	public ImportExport() {}
	
	/**
	 * 
	 * @param jsonFile
	 * @param justApps
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void importJsonFile(String jsonFile, boolean justApps) throws JsonParseException, JsonMappingException, IOException{
		File f = new File(jsonFile);
		importJson(FileUtils.readFileToString(f), justApps);
	}
	
	/**
	 * 
	 * @param json
	 * @param justApps
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void importJson(String json, boolean justApps) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper objectMapper = RestFactory.getJson().getContext(List.class);
		PersistenceAbstract persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
		if (justApps){
			List<App> apps = objectMapper.readValue(json, new TypeReference<List<App>>() {});
			persistenceAbstract.storeAll(apps);
		} else {
			Dump dump = objectMapper.readValue(json, new TypeReference<Dump>() {});
			persistenceAbstract.store(dump.getSettings());
			persistenceAbstract.storeAll(dump.getMediaCategories());
			persistenceAbstract.storeAll(dump.getApps());
			persistenceAbstract.storeAll(dump.getCommands());
		}
		persistenceAbstract.commit();
	}
	
	/**
	 * 
	 * @param justApps
	 * @return path of written file
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String exportJson(boolean justApps) throws JsonGenerationException, JsonMappingException, IOException{
		Dump dump = new Dump();
		if (!justApps){
			dump.setSettings(BusinessFactory.getSettingsBusiness().get());
			dump.setMediaCategories(BusinessFactory.getMediaCategoryBusiness().getAll());
			dump.setCommands(BusinessFactory.getCommandBusiness().getAll());
		}
		dump.setApps(BusinessFactory.getAppBusiness().getAll());
		ObjectMapper objectMapper = RestFactory.getJson().getContext(Dump.class);
		File f = new File(("export" 
				+ RestmoteControl.getInstalledVersion()
				+ simpleDateFormat.format(new Date()) 
				+ ".json"));
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(f, dump);
		return f.getAbsolutePath();
	}
	
}
