package org.zooper.becuz.restmote.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.zooper.becuz.restmote.conf.rest.Views;

public class VisualControl implements ControlInterface{

	public static final int MAX_NUM_COLS = 5; //odd number is nicer
	public static final int MAX_NUM_ROWS = 4;
	
	/**
	 * pk
	 */
	@JsonView(Views.All.class)
	private Long id;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * TODO
	 */
	@JsonIgnore
	private Control control;
	
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
	private String text;
	
	public VisualControl() {}
	
	public VisualControl(String name, Integer row, Integer position) {
		this();
		this.name = name;
		this.row = row;
		this.position = position;
	}
	
	public VisualControl(Control control, Integer row, Integer position) {
		this(control.getName(), row, position);
		this.control = control;
	}
	
	@Override
	public void validate() throws IllegalArgumentException {
		if (row == null || row < 1 || row > MAX_NUM_ROWS){
			throw new IllegalArgumentException("VisualControl of " + getName() + ". Row can't be null and has to be between 1 and 4");
		}
		int m = (MAX_NUM_COLS-1)/2;
		if (position == null || position < -m || position > m){
			throw new IllegalArgumentException("VisualControl of " + getName() + ". Position can't be null and has to be between -" + m + " and " + m);
		}
	}
	
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean getHideImg() {
		return hideImg;
	}

	public void setHideImg(Boolean useImg) {
		this.hideImg = useImg;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	@JsonView(Views.All.class)
	public Long getControlIdReference() {
		return getControl().getId();
	}


}
