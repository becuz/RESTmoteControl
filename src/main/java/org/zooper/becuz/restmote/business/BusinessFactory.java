package org.zooper.becuz.restmote.business;

public class BusinessFactory {

	private static SettingsBusiness settingsBusiness = new SettingsBusiness();
	private static MediaBusiness mediaBusiness = new MediaBusiness();
	private static MediaCategoryBusiness mediaCategoryBusiness = new MediaCategoryBusiness();
	private static ActiveAppBusiness activeAppBusiness = new ActiveAppBusiness();
	private static AppBusiness appBusiness = new AppBusiness();
	private static RemoteControlBusiness remoteControlBusiness = new RemoteControlBusiness();
	private static CommandBusiness commandBusiness = new CommandBusiness();
	
	public static SettingsBusiness getSettingsBusiness() {
		return settingsBusiness;
	}

	public static MediaBusiness getMediaBusiness() {
		return mediaBusiness;
	}

	public static MediaCategoryBusiness getMediaCategoryBusiness() {
		return mediaCategoryBusiness;
	}

	public static ActiveAppBusiness getActiveAppBusiness() {
		return activeAppBusiness;
	}

	public static AppBusiness getAppBusiness() {
		return appBusiness;
	}

	public static RemoteControlBusiness getRemoteControlBusiness() {
		return remoteControlBusiness;
	}
	
	public static CommandBusiness getCommandBusiness() {
		return commandBusiness;
	}
	
}
