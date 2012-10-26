package org.zooper.remosko.model;

import java.util.Collections;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.zooper.remosko.model.interfaces.Persistable;

/**
 * Keyboard {@link #keys} used in a {@link Control}
 * @author bebo
 *
 */
public class KeysEvent implements Comparable<KeysEvent>, Persistable{

	/**
	 * 
	 */
	@JsonIgnore
	private Long id;
	
	/**
	 * 
	 */
	private Integer logicOrder;
	
	/**
	 * 
	 */
	private Set<Integer> keys;
	
	/**
	 * 
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
