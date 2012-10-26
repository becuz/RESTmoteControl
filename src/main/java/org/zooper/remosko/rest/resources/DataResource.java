package org.zooper.remosko.rest.resources;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.zooper.remosko.controller.PcControllerFactory;
import org.zooper.remosko.model.transport.Data;

/**
 * 
 * GET /data
 * 
 * @author bebo
 * @see Data
 */
@Path("/data")
public class DataResource extends AbstractResource{

	private static final Logger log = Logger.getLogger(DataResource.class.getName());
	
	public DataResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public Data get() {
		log.severe("Data get");
		Data d = new Data();
		d.setSettings(getSettingsBusiness().get());
		d.setMediaRoots(getMediaBusiness().getMediaRoots());
		d.setKeyboardControlsManager(PcControllerFactory.getPcController().getKeyboardControlsManager());
		d.setMouseControlsManager(PcControllerFactory.getPcController().getMouseControlsManager());
		return d;
	}
	
}
