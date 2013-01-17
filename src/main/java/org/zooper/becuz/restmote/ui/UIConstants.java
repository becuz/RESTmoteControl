package org.zooper.becuz.restmote.ui;

import org.zooper.becuz.restmote.utils.Constants;


public class UIConstants {

	public static final String TEXT_ABOUT = "<html>Res(t)mote Control rocks.<br/>http://restmote.com</html>";
	public static final String TEXT_ERROR_SERVER_FROM_TRAY = "There was a problem in controlling the server";
	
	public static final String TRAY_TOOLTIP = "Res(T)mote Control";
	public static final String ERR_NO_INET = "Please specify a network interface";
	
	public static final String TOOLTIP_MENU_IMPORTALL = "Reset the database to defaults configuration";
	public static final String TOOLTIP_MENU_IMPORTAPPS = "Load all apps from file";
	public static final String TOOLTIP_MENU_EXPORTALL = "Exports all the database in a single file";
	
	public static final String TOOLTIP_STNGS_ALLINT = "Check if you want the server to listen in all available interfaces";
	public static final String TOOLTIP_STNGS_BTNREFRESH_INTERFACES = "Refresh the net interfaces list";
	public static final String TOOLTIP_STNGS_NAME = "Name that will be displayed in the client";
	public static final String TOOLTIP_STNGS_NAMEROOT = "Name that will be displayed in the header of the client";
	public static final String TOOLTIP_STNGS_SCANDEPTH = "How deep in the tree structure of the filesystem the search should go. High values slows down the process";
	public static final String TOOLTIP_STNGS_ICONTHEME = "Set of icons displayed in the client";
	
	
	public static final String TOOLTIP_CATEGORY_ACTIVE = "Active categories will appear on the clients";
	public static final String TOOLTIP_CATEGORY_NAME = "Name for this category";
	public static final String TOOLTIP_CATEGORY_EXTENSIONS = "Comma separated list of extensions that this category will filter";
	public static final String TOOLTIP_CATEGORY_DESCRIPTION = "Free description";
	public static final String TOOLTIP_CATEGORY_APP = "A specific app to open the files of this category. If blank files will be opened with the default system applications";
	
	public static final String TOOLTIP_APP_NAME = "Name of the application";
	public static final String TOOLTIP_APP_EXTENSIONS = "<html>Comma separated list of extensions that this application is able to open.<br/>"
			+ "This advanced setting will allows particular clients to control this application without the need to know his details</html>";
	public static final String TOOLTIP_APP_WINDOW = "Ensure your application is running and select it from the list";
	public static final String TOOLTIP_APP_PATH = "Full path of the application";
	public static final String TOOLTIP_APP_ARGFILE = Constants.APP_ARGUMENT_FILE + " is file to open. Add command line parameters if you wish";
	public static final String TOOLTIP_APP_ARGDIR = "Leave blank if this app doesn't support directory opening";
	public static final String TOOLTIP_APP_ONEINSTANCE = "Mark if you don't want more instances of this app open (and if the app doesn't already allow this itself)";
	public static final String TOOLTIP_APP_BROWSE = "Browse and choose the executable";
	public static final String TOOLTIP_APP_REFRESH = "Refresh the list of running applications";
	
	//public static final String TOOLTIP_APP_CONTROLS = "Edit the shortcuts";
	//public static final String TOOLTIP_APP_CONTROLS_DELETE = "Delete selected Control";
	public static final String TOOLTIP_APP_CONTROLS_RIGHT = "Add";
	//public static final String TOOLTIP_APP_CONTROLS_TEST = "Test the configuration.";
	
	public static final String TOOLTIP_COMMAND_NAME = "Name of the command";
	public static final String TOOLTIP_COMMAND_DESCRIPTION = "Free description of the command";
	public static final String TOOLTIP_COMMAND_COMMAND = "Command to execute by the shell";
	
	public static final String ERROR_COMMAND_EMPTY_TITLE = "Command empty";
	public static final String ERROR_COMMAND_EMPTY_BODY = "Please specify a command";
	public static final String ERROR_COMMAND_EXCEPTION_TITLE = "Exception caught";
	public static final String ERROR_COMMAND_EXCEPTION_BODY = "<html>The following error occured:<br/>$ERR.<br/><b>Please check your command</b></html>";
	
	public static final String ERROR_IMPORTEXPORT_EXCEPTION_TITLE = "Error";
	public static final String ERROR_IMPORTEXPORT_EXCEPTION_BODY = "<html>The following error occured:<br/>$ERR.<br/><b>Sorry :(</b></html>";
}
