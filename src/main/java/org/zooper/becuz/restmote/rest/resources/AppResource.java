package org.zooper.becuz.restmote.rest.resources;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.rest.exceptions.NotAcceptableException;
import org.zooper.becuz.restmote.rest.exceptions.NotFoundException;
import org.zooper.becuz.restmote.rest.exceptions.ServerException;

/**
 * 
 * GET		/apps							//get Collection<App>
 * GET		/apps/winamp					//get App
 * GET		/apps/ext/mp3					//get App
 * GET		/apps/pid/1234					//get App
 * 
 * DELETE	/apps/winamp 
 * DELETE   /apps/ext/mp3
 * 
 * POST		/apps/winamp/control/PAUSE								//send the command PAUSE to one instance of winamp
 * POST		/apps/winamp/k/c										//send the keyboard char to one instance of winamp
 * POST		/apps/ext/mp3/control/PAUSE								//whatever application is configured on the server to manage mp3 files
 * POST		/apps/pid/1234/control/PAUSE							//specific window
 * POST		/apps/control/PAUSE										//currently on focus
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
		log.severe("AppResource get");
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
		log.severe("AppResource get, name: " + appName);
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
		log.severe("AppResource get, extension: " + extension);
		return getAppBusiness().getByExtension(extension);
	}
	
	/**
	 * @return
	 */
	@GET
	@Path("/pid/{pid}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public App getByPid(
			@PathParam("pid") String pid){
		log.severe("AppResource get, pid: " + pid);
		ActiveApp activeApp = getActiveAppBusiness().getActiveAppByPid(pid, false);
		if (activeApp != null){
			return getAppBusiness().getByName(activeApp.getName());
		}
		return null;
	}
	
	//------------------Delete -------------------------
	
	/**
	 * @return
	 */
	@DELETE
	@Path("/{appName}")
	public void closeByName(
			@PathParam("appName") String appName){
		log.severe("AppResource delete, name: " + appName);
		try {
			getRemoteControlBusiness().closeMedia(getAppBusiness().getByName(appName));
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	/**
	 * @return
	 */
	@DELETE
	@Path("/ext/{extension}")
	public void deleteByExtension(
			@PathParam("extension") String extension){
		log.severe("AppResource delete, extension: " + extension);
		try {
			getRemoteControlBusiness().closeMedia(getByExtension(extension));
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	// ---------------- Control ------------------------
	
	/**
	 * Send the specified control to the first running instance of the app with name appName.
	 * On the server an App configured with that name must exists. 
	 * @param appName, example: "WINAMP"
	 * @param control, example: "PAUSE"
	 */
	@POST
	@Path("/{appName}/control/{control}")
	public void controlByAppName(
			@PathParam("appName") String appName, 
			@PathParam("control") String control){
		log.severe("controlByAppName appName: " + appName + ", control: " + control);
		try {
			getRemoteControlBusiness().control(appName, control, null);
		} catch (IllegalArgumentException e) {
			throw new NotAcceptableException(e.getMessage());
		} catch (Exception e) {
			log.severe(e.getMessage() + " " + e.getCause());
			throw new ServerException(e.getMessage());
		}
	}
	
	/**
	 * Send the specified control to the first running instance of the app with name appName.
	 * On the server an App configured with that name doesn't have to exists. 
	 * @param appName, example: "WINAMP"
	 * @param shortcut, example: 'c'
	 */
	@POST
	@Path("/{appName}/k/{shortcut}")
	public void controlByAppNameAndShortcut(
			@PathParam("appName") String appName, 
			@PathParam("shortcut") String shortcut){
		log.severe("controlByAppNameAndShortcut appName: " + appName + " shortcut: " + shortcut);
		try {
			getRemoteControlBusiness().control(appName, null, shortcut.charAt(0));
		} catch (Exception e) {
			log.severe(e.toString());
			throw new ServerException(e.getMessage());
		}
	}
	
	/**
	 * Send the specified control to the first running instance of the app that handles the specified file extension.
	 * On the server an App configured with that name must exists. 
	 * @param extension, example: "mp3"
	 * @param control, example: "PAUSE"
	 */
	@POST
	@Path("/ext/{extension}/control/{controlName}")
	public void controlByExtension(
			@PathParam("extension") String extension, 
			@PathParam("controlName") String controlName){
		log.severe("controlByExtension extension: " + extension + " control: " + controlName);
		MediaCategory mediaCategory = getMediaCategoryBusiness().getByExtension(extension);
		App app = null;
		if (mediaCategory != null){
			app = mediaCategory.getApp();
		}
		if (app == null){
			app = getAppBusiness().getByExtension(extension);
		}
		try {
			getRemoteControlBusiness().control(app, controlName, null);
		} catch (Exception e) {
			log.severe(e.toString());
			throw new ServerException(e.getMessage());
		}
	}
	
	/**
	 * Send the specified control to the application with the specified pid.
	 * On the server that configured App must exists. 
	 * @param pid
	 * @param control, example: "PAUSE"
	 * @see PcResource#listApps(boolean)
	 */
	@POST
	@Path("/pid/{pid}/control/{control}")
	public void controlByPid(
			@PathParam("pid") String pid, 
			@PathParam("control") String control){
		log.severe("controlByPid pid: " + pid + " control: " + control);
		try {
			getRemoteControlBusiness().controlByPid(pid, control, null);
		} catch (Exception e) {
			log.severe(e.toString());
			throw new ServerException(e.getMessage());
		}
	}
	
	/**
	 * Send the specified control to the application that has focus.
	 * On the server that configured App must exists.
	 * @param control, example: "PAUSE"
	 */
	@POST
	@Path("/control/{control}")
	public void control(@PathParam("control") String control){
		log.severe("control control: " + control);
		try {
			getRemoteControlBusiness().controlByPid(null, control, null);
		} catch (Exception e) {
			log.severe(e.toString());
			throw new ServerException(e.getMessage());
		}
	}
	
}
