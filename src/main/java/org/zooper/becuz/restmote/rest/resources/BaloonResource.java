package org.zooper.becuz.restmote.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.Hackathon;
import org.zooper.becuz.restmote.Hackathon.Player;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.controller.mouses.Mouse;
import org.zooper.becuz.restmote.controller.mouses.Mouse.BTN_ACTION;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.transport.ActiveApp;
import org.zooper.becuz.restmote.model.transport.PcInfo;
import org.zooper.becuz.restmote.utils.Constants;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * APIs to control the pc.
 * 
 * GET /pc												//return PcInfo
 * 
 * POST		/pc/control/{control}						//send the command VOLMUTE to the pc
 * 
 * POST		/pc/keyboard/control/{control}				//es KBD_UP
 * POST		/pc/keyboard/{type}							//type on keyboard the string s
 * POST		/pc/keyboard			JSON String			//type on keyboard the string s
 * 
 * POST		/pc/mouse/control/{control}					//es MOUSE_LEFT
 * PUT		/pc/mouse/912x22
 * PUT		/pc/mouse/x22
 * PUT		/pc/mouse/912
 * PUT		/pc/mouse/32%x22%
 * PUT		/pc/mouse/-22
 * PUT		/pc/mouse/wheel/+12    	?
 * POST		/pc/mouse/buttons/1/click	?
 * POST		/pc/mouse/buttons/1/press	?
 * POST		/pc/mouse/buttons/1/release	?
 * 
 * 
 * @author bebo
 */
@Path("/baloon")
public class BaloonResource extends AbstractResource{

	private static final Logger log = Logger.getLogger(BaloonResource.class.getName());
	
	//-------------------------------------------------------------------
	
	public BaloonResource() {
		super();
	}
	
	//--------------------- Pc ----------------------------------------
	
	/**
	 * Type the argument string
	 * @param s
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public Player getPcInfo() {
		Player p = Hackathon.getInstance().addPlayer();
		return p;
	}
	
	/**
	 * @param mouseMoves
	 */
	@PUT
	@Path("/pos/{index}/{mouseMoves}")
	public void move(
			@PathParam("index") Integer index,
			@PathParam("mouseMoves") String mouseMoves) {
		log.info("mouseMoves mouseMoves: " + mouseMoves);
		String[] coordinates = mouseMoves.split("x");
		int xA = 0;
		int yA = 0;
		for (int i = 0; i < coordinates.length; i++) {
			if (Utils.isEmpty(coordinates[i])) continue;
			if (coordinates[i].startsWith("+") || coordinates[i].startsWith("-")){
				int amount = Integer.parseInt(coordinates[i]);
				if (coordinates.length == 1 || i == 0){ //w
					xA = amount;
				} else {
					yA += amount;
				}
			}
		}
		Hackathon.getInstance().movePlayer(index, xA, yA);
	}
	
	/**
	 * @param mouseMoves
	 */
	@POST
	@Path("/pos/{index}")
	public void click(
			@PathParam("index") Integer index) {
		Hackathon.getInstance().playerClick(index);
	}
	
	
}
