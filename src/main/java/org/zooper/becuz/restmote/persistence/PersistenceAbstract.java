package org.zooper.becuz.restmote.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.model.interfaces.Editable;
import org.zooper.becuz.restmote.model.interfaces.Persistable;
import org.zooper.becuz.restmote.model.transport.MediaRoot;

public abstract class PersistenceAbstract {

	protected static final Logger log = Logger.getLogger(PersistenceAbstract.class.getName());
	
	/**
	 * Medias are currently not persisted..
	 */
	protected List<MediaRoot> mediaRoots = new ArrayList<MediaRoot>(); 
	
	/**
	 * Cache
	 */
	protected Map<Class, Map<Long, Persistable>> cache = new HashMap<Class, Map<Long,Persistable>>();
	
	/**
	 * True if all defined instance of Class are currently in cache
	 */
	protected Map<Class, Boolean> cacheContainsAllOfAKind = new HashMap<Class, Boolean>();
	
	/**
	 * Default Constructor
	 */
	protected PersistenceAbstract() {
	}
	
	public List<MediaRoot> getMediaRoots() {
		return mediaRoots;
	}

	public void addMediaRoot(MediaRoot m) {
		mediaRoots.add(m);
	}

//	public void removeMediaRoot(MediaRoot m) {
//		mediaRoots.remove(m);
//	}
	
	public void clearMediaRoots() {
		mediaRoots.clear();
	}
	
	protected void printCache(){
		System.out.println("***cacheContainsAllOfAKind");
		for(Class c: cacheContainsAllOfAKind.keySet()){
			System.out.println(c + ": " + cacheContainsAllOfAKind.get(c));
		}
		System.out.println("***cache");
		for(Class c: cache.keySet()){
			System.out.println(c);
			Map<Long, Persistable> m = cache.get(c);
			for(Persistable p: m.values()){
				System.out.println(p);
			}
		}
	}
	
	/**
	 * Clear all cache
	 */
	public void clearCache(){
		cache.clear();
		cacheContainsAllOfAKind.clear();
	}
	
	/**
	 * Add in cache
	 * @param p
	 */
	public void addInCache(Persistable p){
		if (p == null){
			throw new IllegalArgumentException("Trying to add null object in cache");
		}
		Map<Long, Persistable> thisClassCache = cache.get(p.getClass());
		if (thisClassCache == null){
			thisClassCache = new HashMap<Long, Persistable>();
			cache.put(p.getClass(), thisClassCache);
		}
		thisClassCache.put(p.getId(), p);
	}
	
	/**
	 * Add all defined {@link Persistable} instances of clazz in cache
	 * @param clazz
	 * @param l
	 * @see #cacheContainsAllOfAKind
	 */
	public void addAllOfAKindInCache(Class clazz, List<Persistable> l) {
		Map<Long, Persistable> allCachedThisKind = cache.get(clazz);
		if (allCachedThisKind != null){
			allCachedThisKind.clear();
		}
		for(Persistable p: l){
			addInCache(p);
		}
		cacheContainsAllOfAKind.put(clazz, true);
	}
	
	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Persistable getFromCache(Class clazz, Long id){
		Map<Long, Persistable> thisClassCache = cache.get(clazz);
		if (thisClassCache != null){
			return thisClassCache.get(id);
		}
		return null;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 */
	public Persistable getFromCacheByName(Class clazz, String name){
		Map<Long, Persistable> thisClassCache = cache.get(clazz);
		if (thisClassCache != null){
			for (Persistable p: cache.get(clazz).values()){
				if (((Editable)p).getName().equals(name)){
					return p;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param p
	 */
	public void removeFromCache(Persistable p){
		Map<Long, Persistable> thisClassCache = cache.get(p.getClass());
		if (thisClassCache != null){
			thisClassCache.remove(p.getId());
		}
	}
	
	/**
	 * 
	 */
	public abstract void beginTransaction();
	
	/**
	 * 
	 */
	public abstract void commit();
	
	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public abstract Persistable getById(Class clazz, Long id);
	
	/**
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 */
	public abstract Persistable getByName(Class clazz, String name);
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public abstract List<Persistable> getAll(Class clazz);
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public abstract Persistable store(Persistable p);

	/**
	 * 
	 * @param p
	 */
	public abstract void delete(Persistable p);

	
}
