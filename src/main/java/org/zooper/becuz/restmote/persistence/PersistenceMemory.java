package org.zooper.becuz.restmote.persistence;

import java.util.Collection;
import java.util.HashSet;

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
	public Object getById(Class clazz, Long id) {
		return getFromCache(clazz, id);
	}

	@Override
	public Object getByName(Class clazz, String name) {
		return getFromCacheByName(clazz, name);
	}

	@Override
	public Collection getAll(Class clazz) {
		if (cache.containsKey(clazz)){
			return cache.get(clazz).values();
		}
		return new HashSet<Persistable>();
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
