package org.zooper.remosko.rest.resources;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.logging.Logger;

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

import org.zooper.remosko.controller.PcControllerFactory;
import org.zooper.remosko.controller.mouses.Mouse;
import org.zooper.remosko.controller.mouses.Mouse.BTN_ACTION;
import org.zooper.remosko.model.Control;
import org.zooper.remosko.model.transport.ActiveApp;
import org.zooper.remosko.model.transport.PcInfo;
import org.zooper.remosko.utils.Constants;

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
 * PUT		/pc/mouse/wheel/+912x-22	?
 * POST		/pc/mouse/buttons/1/click	?
 * POST		/pc/mouse/buttons/1/hold	?
 * POST		/pc/mouse/buttons/1/release	?
 * 
 * TODO resource volume/
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
		log.severe("getPcInfo");
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
			log.severe("pcControl control: " + control);
			if ("volmute".equals(control)){
				getRemoteControlBusiness().mute();	
			} else if (control.startsWith("vol-")){
				getRemoteControlBusiness().setVolume(Integer.parseInt(control.substring(4)));	
			} else if ("suspend".equals(control)){
				getRemoteControlBusiness().suspend();	
			} else if ("poweroff".equals(control)){
				getRemoteControlBusiness().poweroff();
			} else if ("nextapp".equals(control)){ //TODO put in ActiveAppResource
				ActiveApp activeApp = getActiveAppBusiness().next();
				return Response.status(Response.Status.CREATED).entity(activeApp).build();
			} else {
				log.severe("pccontrol " + control + " not implemented!");
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
		log.severe("mouseMoves controlName: " + controlName);
		try {
			Control control = PcControllerFactory.getPcController().getMouseControlsManager().getControl(controlName);
			if (control != null){
				int step = 8;
				int stepWheel = 10;
				PointerInfo a = MouseInfo.getPointerInfo();
				Point b = a.getLocation();
				int x = (int) b.getX();
				int y = (int) b.getY();
				Mouse mouse = PcControllerFactory.getPcController().getMouse();
				if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_CENTER.toString())){
					mouse.mouseMove("50%x50%");
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_LEFT.toString())){
					mouse.mouseMove("-"+Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_RIGHT.toString())){
					mouse.mouseMove("+"+Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_UP.toString())){
					mouse.mouseMove("x-"+Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_DOWN.toString())){
					mouse.mouseMove("x+"+Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_CLICK1.toString())){
					mouse.mouseButtons(1, BTN_ACTION.CLICK);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_CLICK3.toString())){
					mouse.mouseButtons(3, BTN_ACTION.CLICK);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_WHEELUP.toString())){
					mouse.mouseWheel(-Constants.DEFAULT_MOUSE_DELTA_MOVE);
				} else if (control.getName().equals(Control.ControlDefaultTypeMouse.MOUSE_WHEELDOWN.toString())){
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
		log.severe("mouseMoves mouseMoves: " + mouseMoves);
		try {
			PcControllerFactory.getPcController().getMouse().mouseMove(mouseMoves);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
		}
	}
	
	
	@PUT
	@Path("/mouse/wheel/{mouseWheel}")
	public void mouseWheel(@PathParam("mouseWheel") Integer mouseWheel) {
		log.severe("mouseWheel mouseWheel: " + mouseWheel);
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
		log.severe("mouseWheel button: " + button + ", action: " + action);
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
		log.severe("keyboardControl controlName: " + controlName);
		try {
			getRemoteControlBusiness().control(PcControllerFactory.getPcController().getKeyboardControlsManager().getControl(controlName));
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
		log.severe("typeStringJSON s: " + s);
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
		log.severe("typeString type: " + type);
		keyboardType(type);
	}
	
	
}
