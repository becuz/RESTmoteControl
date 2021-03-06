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
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.controller.mouses.Mouse;
import org.zooper.becuz.restmote.controller.mouses.Mouse.BTN_ACTION;
import org.zooper.becuz.restmote.model.Control;
import org.zooper.becuz.restmote.model.VisualControl;
import org.zooper.becuz.restmote.model.transport.PcInfo;
import org.zooper.becuz.restmote.utils.Constants;


/**
 * APIs to control the pc.
 * 
 * GET /pc												//return PcInfo
 * 
 * POST		/pc/control/poweroff
 * POST		/pc/control/suspend						
 * 
 * POST		/pc/control/vol/toggle						//toggle mute
 * POST		/pc/control/vol/10							//set the volume 0-100
 * 
 * POST		/pc/keyboard/control/{control}				//es KBD_UP
 * POST		/pc/keyboard/{type}							//type on keyboard the string s
 * POST		/pc/keyboard			JSON String			//type on keyboard the string s
 * 
 * POST		/pc/mouse/control/{control}  				//es MOUSE_LEFT
 * PUT		/pc/mouse/912x22
 * PUT		/pc/mouse/x22
 * PUT		/pc/mouse/912
 * PUT		/pc/mouse/32%x22%
 * PUT		/pc/mouse/+22x+0
 * PUT		/pc/mouse/wheel/+12    	?
 * POST		/pc/mouse/buttons/1/click	?
 * POST		/pc/mouse/buttons/1/press	?
 * POST		/pc/mouse/buttons/1/release	?
 * 
 * @author bebo
 * @see Control
 */
@Path("/pc")
public class PcResource extends AbstractResource{

	private static final Logger log = Logger.getLogger(PcResource.class.getName());
	
	//-------------------------------------------------------------------
	
	public PcResource() {
		super();
	}
	
	//--------------------- Pc ----------------------------------------
	
	/**
	 * Type the argument string
	 * @param s
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public PcInfo getPcInfo() {
		log.info("getPcInfo");
		int[][] screensSizes = PcControllerFactory.getPcController().getScreenSizes();
		PcInfo pcInfo = new PcInfo();
		pcInfo.setScreenSizes(screensSizes);
		return pcInfo;
	}
	
	/**
	 * Send specific pc controls.
	 * <ul>
	 * <li> "volmute" mute the pc</li>
	 * <li> "vol-13" between 0-100</li>
	 * <li> "suspend" </li>
	 * <li> "poweroff" </li>
	 * <li> "nextapp" gives the focus and bring to front the next application</li>
	 * </ul> 
	 * @param control, example: "poweroff"
	 * @return
	 */
	@POST
	@Path("/control/{control}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public Response pcControl(@PathParam("control") String control){
		try {
			log.info("pcControl control: " + control);
			if ("suspend".equals(control)){
				BusinessFactory.getRemoteControlBusiness().suspend();	
			} else if ("poweroff".equals(control)){
				BusinessFactory.getRemoteControlBusiness().poweroff();
			} else {
				log.warn("pccontrol " + control + " not implemented!");
			}
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	@POST
	@Path("/vol/{action}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public Response vol(@PathParam("action") String action){
		try{
			if ("toggle".equals(action)){
				BusinessFactory.getRemoteControlBusiness().toggleMute();	
			} else {
				try {
					int volume = Integer.parseInt(action);
					BusinessFactory.getRemoteControlBusiness().setVolume(volume);
				} catch (NumberFormatException e){}
			}
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	//-------------------------------------------- mouse ---------------------
	
	/**
	 * @param controlName
	 */
	@POST
	@Path("/mouse/control/{controlName}")
	public void mouseControl(@PathParam("controlName") String controlName) {
		log.info("mouseMoves controlName: " + controlName);
		try {
			VisualControl control = PcControllerFactory.getPcController().getMouseVisualControlsManager().getControl(controlName);
			if (control != null){
				Mouse mouse = PcControllerFactory.getPcController().getMouse();
				if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_CENTER.toString().toLowerCase())){
					mouse.mouseMove("50%x50%");
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_LEFT.toString().toLowerCase())){
					mouse.mouseMove("-"+Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_RIGHT.toString().toLowerCase())){
					mouse.mouseMove("+"+Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_UP.toString().toLowerCase())){
					mouse.mouseMove("x-"+Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_DOWN.toString().toLowerCase())){
					mouse.mouseMove("x+"+Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_CLICK1.toString().toLowerCase())){
					mouse.mouseButtons(1, BTN_ACTION.CLICK);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_CLICK3.toString().toLowerCase())){
					mouse.mouseButtons(3, BTN_ACTION.CLICK);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_WHEELUP.toString().toLowerCase())){
					mouse.mouseWheel(-Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_WHEELDOWN.toString().toLowerCase())){
					mouse.mouseWheel(Constants.DEFAULT_MOUSE_DELTA_MOVE);
				}
			}
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	/**
	 * @param mouseMoves
	 */
	@PUT
	@Path("/mouse/{mouseMoves}")
	public void mouseMovements(@PathParam("mouseMoves") String mouseMoves) {
		log.info("mouseMoves mouseMoves: " + mouseMoves);
		try {
			PcControllerFactory.getPcController().getMouse().mouseMove(mouseMoves);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	
	@PUT
	@Path("/mouse/wheel/{mouseWheel}")
	public void mouseWheel(@PathParam("mouseWheel") Integer mouseWheel) {
		log.info("mouseWheel mouseWheel: " + mouseWheel);
		try {
			PcControllerFactory.getPcController().getMouse().mouseWheel(mouseWheel);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	@POST
	@Path("/mouse/buttons/{btn}/{action}")
	public void mouseButtons(
			@PathParam("btn") Integer button,
			@PathParam("action") String action) {
		log.info("mouseWheel button: " + button + ", action: " + action);
		BTN_ACTION btnAction = BTN_ACTION.valueOf(action.toUpperCase());
		try {
			PcControllerFactory.getPcController().getMouse().mouseButtons(button, btnAction);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	//------------------------------------ keyboard -----------------------------------
	
	/**
	 * Type the argument string
	 * @param s
	 */
	@POST
	@Path("/keyboard/control/{controlName}")
	public void keyboardControl(@PathParam("controlName") String controlName) {
		log.info("keyboardControl controlName: " + controlName);
		try {
			Control control = PcControllerFactory.getPcController().getKbdVisualControlsManager().getControl(controlName).getControl();
			BusinessFactory.getRemoteControlBusiness().control(control);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	/**
	 * Type the argument string
	 * @param s
	 */
	@POST
	@Path("/keyboard")
	@Consumes({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public void keyboardTypeJSON(String s) {
		log.info("typeStringJSON s: " + s);
		try {
			PcControllerFactory.getPcController().getKeyboard().type(s);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	/**
	 * Type the argument string
	 * @param s
	 */
	@POST
	@Path("/keyboard/{type}")
	public void keyboardType(@PathParam("type") String type) {
		log.info("typeString type: " + type);
		keyboardTypeJSON(type);
	}
	
	
}
