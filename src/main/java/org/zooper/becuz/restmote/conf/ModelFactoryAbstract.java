package org.zooper.becuz.restmote.conf;

import org.zooper.becuz.restmote.model.App;
import org.zooper.becuz.restmote.model.MediaCategory;
import org.zooper.becuz.restmote.utils.PopulateDb;

/**
 * Return instances of model objects used in tests and by {@link PopulateDb} 
 * @author bebo
 *
 */
public abstract class ModelFactoryAbstract {

	protected MediaCategory mediaCategoryMovies;
	protected MediaCategory mediaCategoryMusic;
	protected MediaCategory mediaCategoryPics;
	protected MediaCategory mediaCategoryRoot;
	protected App appMovies;
	protected App appMusic;
	protected App appPics;
	
	
	public abstract App getAppMovies();
	public abstract App getAppMusic();
	public abstract App getAppPics();
	public abstract Character getAppMusicPauseChar();
	
	public MediaCategory getMediaCategoryMovies(){
		if (mediaCategoryMovies == null){
			mediaCategoryMovies = new MediaCategory("Movies");
			mediaCategoryMovies.setApp(getAppMovies());
			mediaCategoryMovies.addExtension("avi");
			mediaCategoryMovies.addExtension("mkv");
			mediaCategoryMovies.addExtension("mpg");
			mediaCategoryMovies.addExtension("mp4");
		}
		return mediaCategoryMovies;
	}

	public MediaCategory getMediaCategoryMusic(){
		if (mediaCategoryMusic == null){
			mediaCategoryMusic = new MediaCategory("Music");
			mediaCategoryMusic.addExtension("mp3");
			mediaCategoryMusic.setApp(getAppMusic());
		}
		return mediaCategoryMusic;
	}

	public MediaCategory getMediaCategoryPics(){
		if (mediaCategoryPics == null){
			mediaCategoryPics = new MediaCategory("Pics");
			mediaCategoryPics.addExtension("jpeg");
			mediaCategoryPics.addExtension("jpg");
			mediaCategoryPics.addExtension("gif");
			mediaCategoryPics.addExtension("png");
			mediaCategoryPics.setApp(getAppPics());
		}
		return mediaCategoryPics;
	}
	
	public MediaCategory getMediaCategoryRoot(){
		if (mediaCategoryRoot == null){
			mediaCategoryRoot = new MediaCategory(MediaCategory.ROOT_NAME);
		}
		return mediaCategoryRoot;
	}
	
}
