package org.zooper.becuz.restmote.business.interfaces;

import java.util.Collection;
import java.util.List;

import org.zooper.becuz.restmote.model.interfaces.Persistable;

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
	
	public List<E> getAll(){
		return (List<E>) persistenceAbstract.getAll(type);
	}

	public E store(Persistable p){
		persistenceAbstract.beginTransaction();
		p = persistenceAbstract.store(p);
		persistenceAbstract.commit();
		return (E) p;
	}

	public void delete(Persistable p){
		persistenceAbstract.beginTransaction();
		persistenceAbstract.delete(p);
		persistenceAbstract.commit();
	}
	
}
