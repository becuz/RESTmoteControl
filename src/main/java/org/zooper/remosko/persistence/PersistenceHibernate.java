package org.zooper.remosko.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.zooper.remosko.model.App;
import org.zooper.remosko.model.MediaCategory;
import org.zooper.remosko.model.Settings;
import org.zooper.remosko.model.interfaces.Persistable;
import org.zooper.remosko.persistence.hibernate.HibernateUtil;

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
			log.severe(e.getMessage() + " " + e.getCause());
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
			p = (Persistable) session.load(clazz, id);
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
				    "select a from " + clazz.getSimpleName() + " as a where a.name = :name")
				    .setString("name", name)
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
	public Collection<Persistable> getAll(Class clazz){
		if (cacheContainsAllOfAKind.containsKey(clazz) && cacheContainsAllOfAKind.get(clazz).equals(Boolean.TRUE)){ 
			Map<Long, Persistable> persistables = cache.get(clazz); 
			return persistables == null ? null : persistables.values();
		}
		boolean sessionWasOpen = sessionIsOpen(); 
		if (!sessionWasOpen){
			beginTransaction();
		}
		List<Persistable> l =  session.createQuery(
			    "from " + clazz.getSimpleName()).list();
		addAllOfAKindInCache(clazz, l);
		if (!sessionWasOpen){
			session.close();
		}
		return l;
	}

	@Override
	public Persistable store(Persistable p) {
		session.saveOrUpdate(p);
		addInCache(p);
		return p;
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
