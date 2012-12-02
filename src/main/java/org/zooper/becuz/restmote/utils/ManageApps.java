package org.zooper.becuz.restmote.utils;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.zooper.becuz.restmote.business.AppBusiness;
import org.zooper.becuz.restmote.conf.rest.RestFactory;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.model.App;

public class ManageApps {

	public static void main(String[] args) {
		ManageApps.exportApps();
	}
	
	public static String exportApps(){
		try {
			AppBusiness appBusiness = new AppBusiness();
			List<App> apps = appBusiness.getAll();
			ObjectMapper objectMapper = RestFactory.getJson().getContext(List.class);
			String body = objectMapper.writerWithView(Views.All.class).writeValueAsString(apps);
//			String body = objectMapper.writeValueAsString(apps);
			System.out.println(body);
			return body;
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void importApps(String json){
		try {
			ObjectMapper objectMapper = RestFactory.getJson().getContext(List.class);
			List<App> apps = objectMapper.readValue(json, new TypeReference<List<App>>() {});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
