package org.zooper.becuz.restmote.persistence.export;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.ControlCategory;
import org.zooper.becuz.restmote.model.KeysEvent;
import org.zooper.becuz.restmote.persistence.PersistenceAbstract;
import org.zooper.becuz.restmote.persistence.PersistenceFactory;

/**
 * Manage the import and the export of the database and the apps
 * @author bebo
 *
 */
public class ImportExport {

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	
//	public static void main(String[] args) {
//		fillKeyMap();
//	}
	
	public ImportExport() {}
	
	/**
	 * @param c
	 * @param index
	 * @return string representation of {@link Control#getKeysEvents()}
	 */ 
	public static String getStringFromControlKeys(Control c, int index){
		List<KeysEvent> keysEventsList = new ArrayList<KeysEvent>(c.getKeysEvents());
		int from = index == -1 ? 0 : index;
		int to = index == -1 ? keysEventsList.size()-1 : index;
		String keysText = new String();
		for (int i = from; i <= to; i++) {
			if (i > from){
				keysText += ",";
			}
			KeysEvent currentKeysEvent = keysEventsList.get(i);
			if (currentKeysEvent.getKeys() != null){
				for(Integer key: currentKeysEvent.getKeys()){
					if (keysText.length() > 0){
						keysText += "+";
					}
					keysText += (KeyEvent.getKeyText(key));
				}
			}
		}
		return keysText;
	}
	
	/**
	 * @param s
	 * @return a {@link List<KeysEvent>} representation from a String
	 */
	public static List<KeysEvent> getKeysEventsFromString(String s){
		List<KeysEvent> keysEventsList = new ArrayList<KeysEvent>();
		String[] keysEvents = s.trim().split(",");
		for (int i = 0; i < keysEvents.length; i++) {
			KeysEvent keysEvent = new KeysEvent();
			Set<Integer> keys = new LinkedHashSet<Integer>();
			String[] keysS = keysEvents[i].split("\\+");
			for (int j = 0; j < keysS.length; j++) {
				//TODO
			}
			keysEvent.setKeys(keys);
		}
		return keysEventsList;
	}
	
//	public static final void fillKeyMap() {
//	    try {
//	        Field[] fields = KeyEvent.class.getDeclaredFields();
//	        for(Field f : fields) {
//	            if(f.getName().startsWith("VK_")) {
//	                int code = ((Integer)f.get(null)).intValue();
//	                System.out.println(f.getName() + "\t\t\t" + code);
//	            }
//	        }
//	    } catch(Exception ex) {}
//	}
	
	/**
	 * Import in the db the json content of the file at the specified location
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
	 * Import in the db the json content
	 * @param json
	 * @param justApps
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void importJson(String json, boolean justApps) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper objectMapper = RestFactory.getJson().getContext(List.class);
		PersistenceAbstract persistenceAbstract = PersistenceFactory.getPersistenceAbstract();
		persistenceAbstract.beginTransaction();
		if (justApps){
			List<App> apps = objectMapper.readValue(json, new TypeReference<List<App>>() {});
			for (App app: apps){
				for (ControlCategory controlCategory: app.getControlCategories()){
					persistenceAbstract.save(controlCategory);		
				}
			}
			persistenceAbstract.saveAll(apps);
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
	 * Export to json the db
	 * @param justApps
	 * @return path of written file
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String exportJson(boolean justApps) throws JsonGenerationException, JsonMappingException, IOException{
		String fileNamePrefix = null;
		Object objectToExport = null;
		if (justApps){
			fileNamePrefix = "apps";
			objectToExport = BusinessFactory.getAppBusiness().getAll();
		} else {
			Dump dump = new Dump();
			dump.setSettings(BusinessFactory.getSettingsBusiness().get());
			dump.setMediaCategories(BusinessFactory.getMediaCategoryBusiness().getAll());
			dump.setCommands(BusinessFactory.getCommandBusiness().getAll());
			dump.setApps(BusinessFactory.getAppBusiness().getAll());
			fileNamePrefix = "dump";
			objectToExport = dump;
		}
		ObjectMapper objectMapper = RestFactory.getJson().getContext(objectToExport.getClass());
		File f = new File((fileNamePrefix 
				+ RestmoteControl.getInstalledVersion()
				+ simpleDateFormat.format(new Date()) 
				+ ".json"));
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(f, objectToExport);
		return f.getAbsolutePath();
	}
	
}
