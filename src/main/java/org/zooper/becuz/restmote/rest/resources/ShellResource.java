package org.zooper.becuz.restmote.rest.resources;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.business.BusinessFactory;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.model.Command;
import org.zooper.becuz.restmote.rest.exceptions.NotFoundException;
import org.zooper.becuz.restmote.rest.exceptions.ServerException;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * 
 * GET		/shell/commands					//get Collection<Command>
 * POST		/shell/commands/{name}			//run the command name
 * POST		/shell?c=c						//run the command command
 * POST		/shell		JSON String path	//run the command command
 * 
 * @author bebo
 *
 */
@Path("/shell")
public class ShellResource extends AbstractResource {

	private static final Logger log = Logger.getLogger(ShellResource.class.getName());
	
	//-------------------------------------------------------------------
	
	public ShellResource() {
		super();
	}
	
	//----------------------------- Get ---------------------------------
	
	/**
	 * @return
	 */
	@GET
	@Path("/commands")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	@JsonView({Views.Public.class})
	public Collection<Command> get(){
		log.info("AppResource get");
		return BusinessFactory.getCommandBusiness().getAll();
	}
	
	/**
	 * Execute the command associated with the persisted {@link Command} with the argument commandName
	 * @param commandName
	 */
	@POST
	@Path("/commands/{commandName}")
	public void runConfiguredCommand(
			@PathParam("commandName") String commandName){
		log.info("runConfiguredCommand commandName:" + commandName);
		Command command = BusinessFactory.getCommandBusiness().getByName(commandName);
		if (command == null){ 
			throw new NotFoundException("Command " + commandName + " not found");
		}
		try {
			PcControllerFactory.getPcController().execute(command.getCommand().trim());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new ServerException(e.getMessage());
		}
	}
	
	/**
	 * Execute the command specified as a parameter in the body
	 * @param command
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public void runCommand(
			String command){
		runCommandQuery(command);
	}
	
	/**
	 * Execute the command specified as a parameter in the query
	 * @param command
	 */
	@POST
	public void runCommandQuery(
			@QueryParam("command") String command){
		try {
			log.info("runCommand command:" + command);
			if (!Utils.isEmpty(command)){
				PcControllerFactory.getPcController().execute(command);
			}
		} catch (Exception e) {
			log.error(e.getMessage() + " " + e.getCause());
			throw new ServerException(e.getMessage());
		}
	}
	
	
}
