package org.zooper.becuz.restmote.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.zooper.becuz.restmote.model.interfaces.Persistable;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * A control identifies an action to perform on a {@link App} through a series of keyboard shortcuts specified in {@link #keysEvents}.
 * {@link #row}, {@link #position}, {@link #imgSrc} can be used by a client to paint a full control widget for an {@link App}  
 * @author bebo
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Control implements Persistable{

	public static final int MAX_NUM_ROWS = 4;
	public static final int MAX_NUM_COLS = 5; //odd number is nicer!
	
	public static enum ControlDefaultTypeApp {
		PLAY, PAUSE, STOP,
		PREV, NEXT, FORWARD, BACKWARD, 
		FULLSCREEN, 
		VOLUP, VOLDOWN, VOLMUTE,
		MENU
	}
	
	public static enum ControlDefaultTypeKeyboard {
		KBD_LEFT, KBD_RIGHT, KBD_UP, KBD_DOWN, KBD_ENTER
		//KBD_TAB, KBD_ANTITAB,  KBD_ESC, KBD_ALT
	}
	
	public static enum ControlDefaultTypeMouse {
		MOUSE_LEFT, MOUSE_RIGHT, MOUSE_UP, MOUSE_DOWN,
		MOUSE_CENTER,
		MOUSE_CLICK1, MOUSE_CLICK3,
		MOUSE_WHEELUP, MOUSE_WHEELDOWN
	}
	
	/**
	 * pk
	 */
	@JsonIgnore
	private Long id;
	
	/**
	 * code name, ex: KBD_TAB
	 */
	private String name;
	
	/**
	 * textual description of the control, ex: "pauses the application"
	 */
	private String description;
	
	/**
	 * mapped KeysEvent
	 */
	@JsonIgnore
	private SortedSet<KeysEvent> keysEvents;
	
	/**
	 * For custom controls
	 */
	private String imgSrc;
	
	/**
	 * 0 = centered. -2,-1,0,1,2
	 */
	private Integer position;
	
	/**
	 * row of the control 
	 */
	private Integer row;
	
	/**
	 * If false no image is displayed, {@link #text} instead
	 */
	private Boolean hideImg;
	
	/**
	 * Human readable text for this control, ex. "Esc","Enter" 
	 */
	//private String text;
	
	public Control() {
	}

	private Control(String name, Integer row, Integer position) {
		this();
		this.name = name;
		this.row = row;
		this.position = position;
	}

	public static Control getControl(ControlDefaultTypeMouse c, Integer row, Integer position){
		return getControl(c.toString().toLowerCase(), null, row, position);
	}
	
	public static Control getControl(String name, Integer row, Integer position){
		return getControl(name, null, row, position);
	}
	
	public static Control getControl(ControlDefaultTypeKeyboard c, Integer key, Integer row, Integer position){
		return getControl(c.toString().toLowerCase(), 1, key, row, position);
	}
	
	public static Control getControl(String name, Integer key, Integer row, Integer position){
		return getControl(name, 1, key, row, position);
	}
	
	public static Control getControl(ControlDefaultTypeApp c, Integer key, Integer row, Integer position){
		return getControl(c, 1, key, row, position);
	}
	
	public static Control getControl(ControlDefaultTypeApp c, Integer repeat, Integer key, Integer row, Integer position){
		return getControl(c.toString().toLowerCase(), repeat, key, row, position);
	}
	
	public static Control getControl(String name, Integer repeat, Integer key, Integer row, Integer position){
		Set<Integer> keysInner = new HashSet<Integer>();
		if (key != null){
			keysInner.add(key);
		}
		return getControl(name, repeat, keysInner, row, position);
	}
	
	public static Control getControl(ControlDefaultTypeApp c, Integer repeat, Set<Integer> keys, Integer row, Integer position){
		return getControl(c.toString().toLowerCase(), repeat, keys, row, position);
	}
	
	public static Control getControl(String name, Integer repeat, Set<Integer> keys, Integer row, Integer position){
		List<Set<Integer>> sequenceKeys = new ArrayList<Set<Integer>>();
		sequenceKeys.add(keys);
		return getControlMultiCommand(name, Collections.singletonList(repeat), sequenceKeys, row, position);
	}
	
	public static Control getControlMultiCommand(String name, List<Integer> repeat, List<Set<Integer>> keys, Integer row, Integer position){
		Control c = new Control(name, row, position);
		int i = 0;
		for(Set<Integer> k: keys){
			KeysEvent keysEvent = new KeysEvent(k, repeat.get(i++));
			c.addKeysEvent(keysEvent);
		}
		c.validate();
		return c;
	}
	
	@Override
	public void validate() throws IllegalArgumentException {
		clean();
		if (Utils.isEmpty(name)){
			throw new IllegalArgumentException("Control has no name");
		}
		if (row == null || row < 1 || row > MAX_NUM_ROWS){
			throw new IllegalArgumentException("Control " + name + ". Row can't be null and has to be between 1 and 4");
		}
		int m = (MAX_NUM_COLS-1)/2;
		if (position == null || position < -m || position > m){
			throw new IllegalArgumentException("Control " + name + ". Position can't be null and has to be between -" + m + " and " + m);
		}
//		if (!name.startsWith("MOUSE") && isEmpty()){ //mouse controls have no keys
//			throw new IllegalArgumentException("Control without shortcuts is pretty useless");
//		}
	}
	
	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Control)) return false;
        final Control control = (Control) other;
        if (!control.getName().equals(getName())) return false;
        return true;
    }

	@Override
    public int hashCode() {
		int hash = 7;
        hash = 31 * hash + getName().hashCode();
        return hash;
    }
	
	public void clean(){
		if (keysEvents != null){
			Iterator<KeysEvent> it = keysEvents.iterator();
			while (it.hasNext()) {
				KeysEvent keysEvent = it.next();
				keysEvent.clean();
				if (keysEvent.isEmpty()){
					it.remove();
				}
			}
		}
	}
	
	public boolean isEmpty(){
		return keysEvents == null || keysEvents.isEmpty();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SortedSet<KeysEvent> getKeysEvents() {
		if (keysEvents == null){
			keysEvents = new TreeSet<KeysEvent>();
		}
		return keysEvents;
	}

	public void setKeysEvents(SortedSet<KeysEvent> keysEvent) {
		this.keysEvents = keysEvent;
	}
	
	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean getHideImg() {
		return hideImg;
	}

	public void setHideImg(Boolean useImg) {
		this.hideImg = useImg;
	}
	
//	@Override
//	public int compareTo(Control o) {
//		return this.getLogicOrder().compareTo(o.getLogicOrder());
//	}

//	public String getText() {
//		return text;
//	}
//
//	public void setText(String text) {
//		this.text = text;
//	}

	public void addKeysEvent(KeysEvent keysEvent) {
		keysEvent.setLogicOrder(getKeysEvents().size());
		getKeysEvents().add(keysEvent);
	}
	
	public void removeKeysEvent(KeysEvent keysEvent) {
		getKeysEvents().remove(keysEvent);
		int i = 0;
		for(KeysEvent c: getKeysEvents()){
			c.setLogicOrder(i++);
		}
	}
	
//	public void setposition(position position) {
//		this.position = position.toString();
//	}
//
//	public void setRow(Row row) {
//		this.row = row.toString();
//	}
	
}


