package org.zooper.becuz.restmote.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.zooper.becuz.restmote.model.interfaces.Persistable;

public class PersistenceMemory extends PersistenceAbstract {

	/**
	 * 
	 */
	protected PersistenceMemory() {
		super();
	}
	
	@Override
	public void beginTransaction() {
	}

	@Override
	public void commit() {
	}

	@Override
	public Persistable getById(Class clazz, Long id) {
		return getFromCache(clazz, id);
	}

	@Override
	public Persistable getByName(Class clazz, String name) {
		return getFromCacheByName(clazz, name);
	}

	@Override
	public List<Persistable> getAll(Class clazz) {
		if (cache.containsKey(clazz)){
			return new ArrayList<Persistable>(cache.get(clazz).values());
		}
		return new ArrayList<Persistable>();
	}

	@Override
	public Persistable store(Persistable p) {
		addInCache(p);
		return p;
	}

	@Override
	public void delete(Persistable p) {
		removeFromCache(p);
	}
	
	@Override
	public void addInCache(Persistable p) {
		if (p.getId() == null){
			long id = cache.get(p.getClass()) == null ? 0 : cache.get(p.getClass()).values().size()+1;
			p.setId(id);
		}
		super.addInCache(p);
	}
	
//	public static List<MediaRoot> getMediaRoots() {
//	return mediaRoots;
//}
//
//public static List<MediaRoot> update(List<MediaRoot> lclMediaRoots) {
//	mediaRoots = lclMediaRoots;
//	return getMediaRoots();
//}
	

}
