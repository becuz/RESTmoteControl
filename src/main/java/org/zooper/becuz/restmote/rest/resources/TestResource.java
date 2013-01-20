package org.zooper.becuz.restmote.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;


@Path("/test")
public class TestResource extends AbstractResource{

	private static final Logger log = Logger.getLogger(TestResource.class.getName());
	
	public TestResource() {
		super();
	}
	
	@GET
	@Path("/ping")
	@Produces({ MediaType.TEXT_PLAIN})
	public String ping() {
		log.info("ping!");
		return "pong";
	}
	
//	/**
//	 * $.getJSON(remoteUrl + 'test/examplejsonp?callback=?', null, function(data) {
//	 *		for(i in data) {
//	 *			alert(i + " " + data[i]);
//	 *        }
//	 *	});
//	 * @param callback
//	 * @return
//	 */
//	@GET
//	@Path("/examplejsonp")
//	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
//	public String examplejsonp(@QueryParam("callback") @DefaultValue("callback") String callback) {
//		return callback + "({\"lastUpdated\":1348646809181,\"name\":\"Remorkissim!\",\"nameRoot\":\"My medias\"})";
//	}
  
	
}
