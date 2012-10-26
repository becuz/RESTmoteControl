package org.zooper.remosko.business.interfaces;

import java.util.Collection;

import org.zooper.remosko.model.interfaces.Persistable;

/**
 * 
 */
public class BusinessModelAbstract<E extends Persistable> extends BusinessAbstract {

	private Class<E> type;

	protected BusinessModelAbstract(Class<E> type) { 
		this.type = type;  
	}
	
	public E get(Long id){
		return (E) persistenceAbstract.getById(type, id);
	}
	
	public E getByName(String name){
		return (E) persistenceAbstract.getByName(type, name);
	}
	
	public Collection<E> getAll(){
		return (Collection<E>) persistenceAbstract.getAll(type);
	}

	public Persistable store(Persistable p){
		persistenceAbstract.beginTransaction();
		p = persistenceAbstract.store(p);
		persistenceAbstract.commit();
		return p;
	}

	public void delete(Persistable p){
		persistenceAbstract.beginTransaction();
		persistenceAbstract.delete(p);
		persistenceAbstract.commit();
	}
	
}
