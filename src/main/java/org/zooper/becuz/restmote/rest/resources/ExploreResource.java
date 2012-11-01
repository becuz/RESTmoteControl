package org.zooper.becuz.restmote.rest.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.zooper.becuz.restmote.controller.PcControllerFactory;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.model.transport.Media;
import org.zooper.becuz.restmote.rest.exceptions.NotAcceptableException;
import org.zooper.becuz.restmote.rest.exceptions.ServerException;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * APIs to work with files on the remote pc, so far just to browse and/or open them.
 *
 * GET  /medias/Music												//return List<Media>
 * GET  /medias/Music?path=path&depth=2&filter=*filter*				//return List<Media>
 * GET  /medias?path=path&ext=mp3-m3u&depth=2&filter=*filter*		//return List<Media>	ext and depth are optional
 *
 * TODO not sure these calls for open a file are good. Maybe they need a fixed path with literal "open/"
 * POST /medias								JSON String path		//open file
 * POST	/medias?path=path											//open file
 * POST /medias/{appName}					JSON String path		//open file with the specified app
 * POST	/medias/{appName}?path=path									//open file with the specified app
 * 
 * @author bebo
 *
 */
@Path("/medias")
public class ExploreResource extends AbstractResource {

	private static final Logger log = Logger.getLogger(ExploreResource.class.getName());
	
	//-------------------------------------------------------------------
	
	public ExploreResource() {
		super();
	}
	
	//----------------------------- Get ---------------------------------
		
	/**
	 * 
	 * @param filePath
	 * @param extensionsString
	 * @param depth
	 * @param filter
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public List<Media> get(
			@QueryParam("path") String filePath, 
			@QueryParam("extensions") List<String> extensions,
			@QueryParam("depth") Integer depth,
			@QueryParam("filter") String filter
			//@QueryParam("mode") String mode TODO flat or tree
			){
		if (Utils.isEmpty(filePath)){
			throw new NotAcceptableException("path is mandatory");
		}
		log.severe("get path: " + filePath + ", extensions: " + extensions + ", depth: " + depth + ", filter: " + filter);
		return PcControllerFactory.getPcController().getMedia(
				filePath, 
				extensions, 
				depth,
				filter);
	}
		
	/**
	 * 
	 * @param filePath
	 * @param extensions allowed extension
	 * @param depth 
	 * @param filter regex
	 * @return
	 */
	@GET
	@Path("/{mediaCategoryName}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public List<Media> getByMediaCategory(
			@PathParam("mediaCategoryName") String mediaCategoryName,
			@QueryParam("path") String filePath, 
			@QueryParam("depth") Integer depth,
			@QueryParam("filter") String filter
			//@QueryParam("mode") String mode TODO flat or tree
			){
		log.severe("get path: " + filePath + ", mediaCategoryName: " + mediaCategoryName + ", depth: " + depth + ", filter: " + filter);
		if (Utils.isEmpty(filePath)){
			return PcControllerFactory.getPcController().getMedia(getMediaBusiness().getMediaRootByName(mediaCategoryName)); 
		}
		MediaCategory mediaCategory = null;
		if (!Utils.isEmpty(mediaCategoryName)){
			mediaCategory = getMediaCategoryBusiness().getByName(mediaCategoryName);
		}
		return PcControllerFactory.getPcController().getMedia(
				filePath, 
				mediaCategory == null ? null : new ArrayList<String>(mediaCategory.getExtensions()), 
				depth,
				filter);
	}
	
	//----------------------------- Open ---------------------------------
	
	/**
	 * Open the file on the server
	 * @param mediaId
	 * @param app optional argument to specified the opening app (mandatory if the file to open is a directory)
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public void open(String path){
		openFile(null, path);
	}
	
	/**
	 * 
	 * @param appName
	 * @param path
	 */
	@POST
	@Path("/{appName}")
	@Consumes({ MediaType.APPLICATION_JSON + "; charset=utf-8" })
	public void open(
			@PathParam("appName") String appName,
			String path){
		openFile(appName, path);
	}
	
	/**
	 * 
	 * @param path
	 */
	@POST
	public void openFile(
			@QueryParam("path") String path){
		openFile(null, path);
	}
	
	/**
	 * Open the file on the server
	 * @param pathFile
	 * @param appName optional argument to specified the opening app (mandatory if the file to open is a directory)
	 */
	@POST
	@Path("/{appName}")
	public void openFile(
			@PathParam("appName") String appName,
			@QueryParam("path") String pathFile){
		try {
			log.severe("openFile pathFile:" + pathFile + ", appName:" + appName);
			getRemoteControlBusiness().openFile(pathFile, getAppBusiness().getByName(appName));
		} catch (Exception e) {
			log.severe(e.getMessage() + " " + e.getCause());
			throw new ServerException(e.getMessage());
		}
	}
	
	
}
