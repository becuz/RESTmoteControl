package org.zooper.becuz.restmote.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.model.transport.MediaRoot;

/**
 * GET  /mediaroots 											//return List<MediaRoot>	roots		
 * 
 * @author bebo
 */
@Path("/mediaroots")
public class MediaRootResource extends AbstractResource{

	private static final Logger log = Logger.getLogger(MediaRootResource.class.getName());
	
	//-------------------------------------------------------------------
	
	public MediaRootResource() {}
	
	//----------------------------- Get ---------------------------------
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	@JsonView({Views.Custom.class})
	public List<MediaRoot> getMediaRoots(){
		log.debug("getMediaRoots");
		BusinessFactory.getMediaBusiness().rootScan();
		return BusinessFactory.getMediaBusiness().getMediaRoots();
	}
	
	
}
