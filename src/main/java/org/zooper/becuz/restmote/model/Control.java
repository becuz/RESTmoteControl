package org.zooper.becuz.restmote.model;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.conf.rest.Views;
import org.zooper.becuz.restmote.utils.Utils;

/**
 * A control identifies an action to perform on a {@link App} through a series of keyboard shortcuts specified in {@link #keysEvents}.
 * {@link #row}, {@link #position}, {@link #imgSrc} can be used by a client to paint a full control widget for an {@link App}  
 * @author bebo
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Control implements ControlInterface {

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
	@JsonView(Views.All.class)
	private Long id;
	
	/**
	 * code name, ex: KBD_TAB
	 */
	private String name;
	
	/**
	 * textual description of the control, ex: "pauses the application"
	 */
	@JsonView(Views.All.class)
	private String description;
	
	/**
	 * mapped KeysEvent
	 */
	@JsonView(Views.All.class)
	private SortedSet<KeysEvent> keysEvents;
	
	/**
	 */
	@JsonView(Views.Extreme.class)
	private ControlCategory controlCategory;

	@JsonView(Views.All.class)
	private Long controlCategoryIdRef;
	
	public Control() {
	}

	public Control(String name) {
		this();
		this.name = name;
	}
	
	public Control(String name, Integer key) {
		this(name);
		addKeysEvent(new KeysEvent(key));
	}

	
	
	@Override
	public void validate() throws IllegalArgumentException {
		clean();
		if (Utils.isEmpty(name)){
			throw new IllegalArgumentException("Control has no name");
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
		if (control.getName() != null && !control.getName().equals(getName())) return false;
        return true;
    }

	@Override
    public int hashCode() {
		int hash = 7;
		if (getKeysEvents() != null){
			hash = 31 * hash + getKeysEvents().hashCode();
		}
		if (getDescription() != null){
			hash = 31 * hash + getDescription().hashCode();
		}
        return hash;
    }
	
	//TODO
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
	
	@JsonView(Views.Extreme.class)
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
//	@Override
//	public int compareTo(Control o) {
//		return this.getLogicOrder().compareTo(o.getLogicOrder());
//	}

	public ControlCategory getControlCategory() {
		return controlCategory;
	}

	public void setControlCategory(ControlCategory controlCategory) {
		this.controlCategory = controlCategory;
	}
	
	public Long getControlCategoryIdRef() {
		if (getControlCategory() != null){
			setControlCategoryIdRef(getControlCategory().getId());
		}
		return controlCategoryIdRef;
	}
	
	public void setControlCategoryIdRef(Long idRef) {
		this.controlCategoryIdRef = idRef;
	}

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


