package org.zooper.becuz.restmote.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.model.transport.Data;

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
	@JsonView({Views.Custom.class})
	public Data get() {
		log.info("Data get");
		Data d = new Data();
		d.setSettings(BusinessFactory.getSettingsBusiness().get());
		d.setMediaRoots(BusinessFactory.getMediaBusiness().getMediaRoots());
		d.setKeyboardVisualControls(PcControllerFactory.getPcController().getKbdVisualControlsManager().getControls());
		d.setMouseVisualControls(PcControllerFactory.getPcController().getMouseVisualControlsManager().getControls());
		return d;
	}
	
}
