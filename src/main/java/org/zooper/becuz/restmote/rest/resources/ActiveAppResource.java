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
import org.zooper.becuz.restmote.model.transport.ActiveApp;

/**
 * 
 * TODO GET	/activeapps/{name}?refresh=true						//name is an App name
 * 
 * GET		/activeapps?refresh=true							//get List<ActiveApp>
 * GET		/activeapps/{handle}?refresh=true					//get ActiveApp
 * 
 * POST		/activeapps/{handle}/focus							//put the handle application on focus
 * 
 * DELETE	/activeapps/{handle}								//close the application by single handle
 * DELETE	/activeapps/					JSON List<String>	//close the applications by handles
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
	 * Return the running applications
	 * @param refresh rebuild the list
	 * @return
	 */
	@GET
	@Path("{handle}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public ActiveApp getActiveApp(
			@PathParam("handle") String handle,
			@QueryParam("refresh") String refresh){
		log.info("ActiveAppResource getActiveApp handle " + handle + ", refresh: " + refresh);
		return getActiveAppBusiness().getActiveAppByHandle(handle, "true".equals(refresh));
	}
	
	/**
	 * Close the running applications with the specified handles
	 * @param handles
	 * @see #listApps(boolean)
	 */
	@DELETE
	@Path("{handle}")
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
	 * Gives the focus and bring to front the application with the specified handle
	 * @param handle
	 */
	@POST
	@Path("{handle}/focus")
	public void focus(@PathParam("handle") String handle){
		log.info("focus handle: " + handle);
		try {
			getActiveAppBusiness().focusActiveApp(handle);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
}
