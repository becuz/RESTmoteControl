package org.zooper.becuz.restmote.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;


@Path("/test")
public class TestResource extends AbstractResource{

	private static final Logger log = Logger.getLogger(TestResource.class.getName());
	
	public TestResource() {
		super();
	}
	
	@GET
	@Path("/examplejsonp")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public String examplejsonp(@QueryParam("callback") @DefaultValue("callback") String callback) {
		return callback + "({\"lastUpdated\":1348646809181,\"name\":\"Remorkissim!\",\"nameRoot\":\"My medias\"})";
	}
	//function ajax_exampleJSONP() {
	//	$.getJSON(remoteUrl + 'test/examplejsonp?callback=?', null, function(data) {
	//		for(i in data) {
	//			alert(i + " " + data[i]);
	//        }
	//	});
	//}
  
	
	
	@PUT
	@Path("/pars/{p}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	@Consumes({"application/x-www-form-urlencoded; charset=UTF-8"})
	public String pars(
//			@QueryParam("path") String path
//			,@QueryParam("l") String l
			@PathParam("p") String path
			) {
		log.info("pars "  + path);
		return "";
	}
	
}
