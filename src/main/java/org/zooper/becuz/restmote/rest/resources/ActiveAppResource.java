package org.zooper.becuz.restmote.rest.resources;

import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

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

import org.zooper.becuz.restmote.model.transport.ActiveApp;

/**
 * 
 * GET		/activeapps?refresh=true							//get List<ActiveApp>
 * GET		/activeapps/{pid}?refresh=true						//get ActiveApp
 * 
 * POST		/activeapps/{pid}/focus								//put the pid application on focus
 * 
 * DELETE	/activeapps/{pid}									//close the application by single pid
 * DELETE	/activeapps/					JSON List<String>	//close the applications by pids
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
	@Path("{pid}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public ActiveApp getActiveApp(
			@PathParam("pid") String pid,
			@QueryParam("refresh") String refresh){
		log.info("ActiveAppResource getActiveApp pid " + pid + ", refresh: " + refresh);
		return getActiveAppBusiness().getActiveAppByPid(pid, "true".equals(refresh));
	}
	
	/**
	 * Close the running applications with the specified pids
	 * @param pids
	 * @see #listApps(boolean)
	 */
	@DELETE
	@Path("{pid}")
	@Consumes({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public void killApps(@PathParam("pid") String pid){
		log.info("killApps pid: " + pid);
		try {
			getActiveAppBusiness().killActiveApps(Collections.singletonList(pid));
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	/**
	 * Close the running applications with the specified pids
	 * @param pids
	 * @see #listApps(boolean)
	 */
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public void killApps(List<String> pids){
		log.info("killApps pids: " + pids);
		try {
			getActiveAppBusiness().killActiveApps(pids);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	/**
	 * Gives the focus and bring to front the application with the specified pid
	 * @param pid
	 */
	@POST
	@Path("{pid}/focus")
	public void focus(@PathParam("pid") String pid){
		log.info("focus pid: " + pid);
		try {
			getActiveAppBusiness().focusActiveApp(pid);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
}
