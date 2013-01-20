package org.zooper.becuz.restmote.model;

import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.model.interfaces.Persistable;

/**
 * Keyboard {@link #keys} used in a {@link Control}
 * @author bebo
 *
 */
public class KeysEvent implements Comparable<KeysEvent>, Persistable{

	/**
	 * pk
	 */
	private Long id;
	
	/**
	 * order for {@link Comparable}
	 */
	private Integer logicOrder;
	
	/**
	 * {@link KeyEvent} codes to simulate keyboard keys press
	 */
	private Set<Integer> keys;
	
	/**
	 * {@link #keys} are pressed repeat times 
	 */
	private Integer repeat;
	
	public KeysEvent() {
	}
	
	public KeysEvent(Integer key) {
		this(key, 1);
	}
	
	public KeysEvent(Integer key, Integer repeat) {
		this(Collections.singleton(key), repeat);
	}
	
	public KeysEvent(Set<Integer> keys, Integer repeat) {
		this();
		this.keys = keys;
		this.repeat = repeat;
	}
	
	@Override
	public void validate() throws IllegalArgumentException {}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof KeysEvent)) return false;
        final KeysEvent keysEvent = (KeysEvent) other;
        if (!keysEvent.getKeys().equals(getKeys())) return false;
        //if (!keysEvent.getLogicOrder().equals(getLogicOrder())) return false;
        return true;
    }

	@Override
    public int hashCode() {
		int hash = 7;
        //hash = 31 * hash + getLogicOrder().hashCode();
        hash = 31 * hash + getKeys().hashCode();
        return hash;
    }

	@Override
	public int compareTo(KeysEvent o) {
		return this.getLogicOrder().compareTo(o.getLogicOrder());
	}
	
	//TODO
	public void clean(){
		if (keys != null){
			Iterator<Integer> it = keys.iterator();
			while (it.hasNext()) {
				Integer i = it.next();
				if (i == null){
					it.remove();
				}
			}
		}
	}
	
	@JsonView(Views.Extreme.class)
	public boolean isEmpty(){
		return keys == null || keys.isEmpty();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRepeat() {
		return repeat;
	}
	public void setRepeat(Integer repeat) {
		this.repeat = repeat;
	}
	public Set<Integer> getKeys() {
		if (keys == null){
			keys = new HashSet<Integer>();
		}
		return keys;
	}
	public void setKeys(Set<Integer> keys) {
		this.keys = keys;
	}
	public Integer getLogicOrder() {
		return logicOrder;
	}
	public void setLogicOrder(Integer logicOrder) {
		this.logicOrder = logicOrder;
	}
}
