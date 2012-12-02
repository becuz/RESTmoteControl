package org.zooper.becuz.restmote.rest.resources;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.rest.exceptions.NotFoundException;

/**
 * 
 * GET		/apps							//get Collection<App>
 * GET		/apps/winamp					//get App
 * GET		/apps/ext/mp3					//get App
 * GET		/apps/handle/1234				//get App
 * 
 * @author bebo
 *
 */
@Path("/apps")
public class AppResource extends AbstractResource {

	private static final Logger log = Logger.getLogger(AppResource.class.getName());
	
	//-------------------------------------------------------------------
	
	public AppResource() {
		super();
	}
	
	//----------------------------- Get ---------------------------------
	
	/**
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public Collection<App> get(){
		log.info("AppResource get");
		return getAppBusiness().getAll();
	}
	
	/**
	 * @return
	 */
	@GET
	@Path("/{appName}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public App getByName(
			@PathParam("appName") String appName){
		log.info("AppResource get, name: " + appName);
		App app = getAppBusiness().getByName(appName);
		if (app == null){ 
			throw new NotFoundException("App " + appName + " not found");
		}
		return app;
	}
	
	/**
	 * @return
	 */
	@GET
	@Path("/ext/{extension}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public App getByExtension(
			@PathParam("extension") String extension){
		log.info("AppResource get, extension: " + extension);
		return getAppBusiness().getRunningByExtension(extension);
	}
	
	/**
	 * @return
	 */
	@GET
	@Path("/handle/{handle}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public App getByHandle(
			@PathParam("handle") String handle){
		log.info("AppResource get, handle: " + handle);
		ActiveApp activeApp = getActiveAppBusiness().getActiveAppByHandle(handle, false);
		if (activeApp != null){
			return getAppBusiness().getByName(activeApp.getName());
		}
		return null;
	}
	
	
}
