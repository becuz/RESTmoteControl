package org.zooper.becuz.restmote.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.zooper.becuz.restmote.model.interfaces.Persistable;
import org.zooper.becuz.restmote.persistence.hibernate.HibernateUtil;

public class PersistenceHibernate extends PersistenceAbstract{

	protected static final Logger log = Logger.getLogger(PersistenceHibernate.class.getName());
	
	private Session session;
	
	@Override
	public void beginTransaction() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
	}

	@Override
	public void commit() {
		try {
			session.getTransaction().commit();
		} catch (Exception e){
			log.error(e.getMessage() + " " + e.getCause());
			e.printStackTrace();
		}
	}
	
	@Override
	public Persistable getById(Class clazz, Long id){
		Persistable p = getFromCache(clazz, id);
		if (p == null){
			boolean sessionWasOpen = sessionIsOpen(); 
			if (!sessionWasOpen){
				beginTransaction();
			}
			//p = (Persistable) session.load(clazz, id); //doesn't do lazy loading
		    p = (Persistable) session.createQuery(
				    "select a from " + clazz.getSimpleName() + " as a where a.id = :id")
				    .setLong("id", id)
				    .uniqueResult();
			if (p != null){
				addInCache(p);
			}
			if (!sessionWasOpen){
				session.close();
			}
		}
		return p;
	}
	
	@Override
	public Persistable getByName(Class clazz, String name){
		Persistable p = getFromCacheByName(clazz, name);
		if (p == null){
			boolean sessionWasOpen = sessionIsOpen(); 
			if (!sessionWasOpen){
				beginTransaction();
			}
			p = (Persistable) session.createQuery(
				    "select a from " + clazz.getSimpleName() + " as a where lower(a.name) = :name")
				    .setString("name", name.toLowerCase())
				    .uniqueResult();
			if (p != null){
				addInCache(p);
			}
			if (!sessionWasOpen){
				session.close();
			}
		}
		return p;
	}
	
	@Override
	public List<Persistable> getAll(Class clazz){
//		if (cacheContainsAllOfAKind.containsKey(clazz) && cacheContainsAllOfAKind.get(clazz).equals(Boolean.TRUE)){ 
//			Map<Long, Persistable> persistables = cache.get(clazz); 
//			return persistables == null ? null : new ArrayList<Persistable>(persistables.values());
//		}
		boolean sessionWasOpen = sessionIsOpen(); 
		if (!sessionWasOpen){
			beginTransaction();
		}
		List<Persistable> l =  session.createQuery(
			    "from " + clazz.getSimpleName()).list();
//		addAllOfAKindInCache(clazz, l);
		if (!sessionWasOpen){
			session.close();
		}
		return l;
	}

	@Override
	public Persistable save(Persistable p) {
		return store(p, false, true);
	}
	
	@Override
	public Persistable store(Persistable p) {
		return store(p, true, true);
	}
	
	private Persistable store(Persistable p, boolean saveOrUpdate, boolean addInCache) {
		if (saveOrUpdate){
			session.saveOrUpdate(p);
		} else {
			session.save(p);
		}
		if (addInCache){
			addInCache(p);
		}
		return p;
	}
	
	@Override
	public void storeAll(List<? extends Persistable> persistables) {
		for(Persistable p: persistables){
			store(p, true, false);
		}
	}
	
	@Override
	public void saveAll(List<? extends Persistable> persistables) {
		for(Persistable p: persistables){
			store(p, false, false);
		}
	}
	
	@Override
	public void delete(Persistable p) {
		session.delete(p);
		removeFromCache(p);
	}
	
	private boolean sessionIsOpen(){
		return session != null && session.isOpen();
	}

}
