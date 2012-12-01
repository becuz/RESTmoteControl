package org.zooper.becuz.restmote.rest.resources;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.rest.exceptions.NotAcceptableException;
import org.zooper.becuz.restmote.rest.exceptions.ServerException;

/**
 * 
 * GET		/activeapps?refresh=true							//get List<ActiveApp>
 * GET		/activeapps/handle/1234?refresh=true				//get ActiveApp
 * GET	    /activeapps/winamp?refresh=true						//get ActiveApp by App.name
 * 
 * POST		/activeapps/handle/1234/focus						//put the handle application on focus
 * POST		/activeapps/next									//put the focus on the next ActiveApp
 * 
 * DELETE	/activeapps/handle/1234								//close the application by single handle
 * DELETE	/activeapps/					JSON List<String>	//close the applications by handles
 * DELETE	/activeapps/winamp 									//close the ActiveApp by App#name
 * DELETE   /activeapps/ext/mp3									//close the ActiveApp by App#extension
 * 
 * POST		/activeapps/winamp/control/PAUSE					//send the command PAUSE to one instance of winamp (App#name)
 * POST		/activeapps/winamp/k/c								//send the keyboard char to one instance of winamp (App#name)
 * POST		/activeapps/ext/mp3/control/PAUSE					//whatever application is configured on the server to manage mp3 files (App#extension)
 * POST		/activeapps/handle/1234/control/PAUSE				//specific window (ActiveApp#handle)
 * POST		/activeapps/control/PAUSE							//currently on focus
 * 
 * 
 * @author bebo
 *
 */
@Path("/activeapps")
public class ActiveAppResource extends AbstractResource {

	private static final Logger log = Logger.getLogger(ActiveAppResource.class.getName());
	
	//-------------------------------------------------------------------
	
	public ActiveAppResource() {
		super();
	}
	
	//----------------------------------------------------------------
	
	/**
	 * Return the running applications
	 * @param refresh rebuild the list
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public List<ActiveApp> get(
			@QueryParam("refresh") String refresh){
		log.info("ActiveAppResource get refresh: " + refresh);
		return getActiveAppBusiness().getActiveApps("true".equals(refresh));
	}
	
	/**
	 * @param refresh rebuild the list
	 * @return
	 */
	@GET
	@Path("/handle/{handle}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public ActiveApp getActiveApp(
			@PathParam("handle") String handle,
			@QueryParam("refresh") String refresh){
		log.info("ActiveAppResource getActiveApp handle " + handle + ", refresh: " + refresh);
		return getActiveAppBusiness().getActiveAppByHandle(handle, "true".equals(refresh));
	}
	
	/**
	 * @param appName {@link App#name}
	 * @param refresh rebuild the list
	 * @return
	 */
	@GET
	@Path("{appName}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public List<ActiveApp> getActiveAppsByAppName(
			@PathParam("appName") String appName,
			@QueryParam("refresh") String refresh){
		log.info("ActiveAppResource getActiveApp appName " + appName + ", refresh: " + refresh);
		return getActiveAppBusiness().getActiveAppsByAppName(appName, "true".equals(refresh));
	}
	
	/**
	 * Close the running applications with the specified handles
	 * @param handles
	 * @see #listApps(boolean)
	 */
	@DELETE
	@Path("/handle/{handle}")
	@Consumes({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public void killApps(@PathParam("handle") String handle){
		log.info("killApps handle: " + handle);
		try {
			getActiveAppBusiness().killActiveApps(Collections.singletonList(handle));
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	/**
	 * Close the running applications with the specified handles
	 * @param handles
	 * @see #listApps(boolean)
	 */
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public void killApps(List<String> handles){
		log.info("killApps handles: " + handles);
		try {
			getActiveAppBusiness().killActiveApps(handles);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	/**
	 * @return
	 */
	@DELETE
	@Path("/{appName}")
	public void closeByName(
			@PathParam("appName") String appName){
		log.info("AppResource delete, name: " + appName);
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
	public void closeByExtension(
			@PathParam("extension") String extension){
		log.info("AppResource delete, extension: " + extension);
		try {
			getRemoteControlBusiness().closeMedia(getAppBusiness().getByExtension(extension));
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
		log.info("controlByAppName appName: " + appName + ", control: " + control);
		try {
			getRemoteControlBusiness().control(appName, control, null);
		} catch (IllegalArgumentException e) {
			throw new NotAcceptableException(e.getMessage());
		} catch (Exception e) {
			log.info(e.getMessage() + " " + e.getCause());
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
		log.info("controlByAppNameAndShortcut appName: " + appName + " shortcut: " + shortcut);
		try {
			getRemoteControlBusiness().control(appName, null, shortcut.charAt(0));
		} catch (Exception e) {
			log.error(e.toString());
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
		log.info("controlByExtension extension: " + extension + " control: " + controlName);
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
			log.error(e.toString());
			throw new ServerException(e.getMessage());
		}
	}
	
	/**
	 * Send the specified control to the application with the specified handle.
	 * On the server that configured App must exists. 
	 * @param handle
	 * @param control, example: "PAUSE"
	 * @see PcResource#listApps(boolean)
	 */
	@POST
	@Path("/handle/{handle}/control/{control}")
	public void controlByHandle(
			@PathParam("handle") String handle, 
			@PathParam("control") String control){
		log.info("controlByHandle handle: " + handle + " control: " + control);
		try {
			getRemoteControlBusiness().controlByHandle(handle, control, null);
		} catch (Exception e) {
			log.error(e.toString());
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
		log.info("control control: " + control);
		try {
			getRemoteControlBusiness().controlByHandle(null, control, null);
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServerException(e.getMessage());
		}
	}
	
	/**
	 * Gives the focus and bring to front the application with the specified handle
	 * @param handle
	 */
	@POST
	@Path("/handle/{handle}/focus")
	public void focus(@PathParam("handle") String handle){
		log.info("focus handle: " + handle);
		try {
			getActiveAppBusiness().focusActiveApp(handle);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	@POST
	@Path("/next")
	public ActiveApp focusNext(){
		log.info("focus next");
		try {
			return getActiveAppBusiness().next();
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
}	
