package org.zooper.becuz.restmote.model.transport;

public class PcInfo {

//	private Integer[] screensWidth;
//	private Integer[] screensHeight;
	
	private int[][] screenSizes;
	
	public PcInfo() {}

	public int[][] getScreenSizes() {
		return screenSizes;
	}

	public void setScreenSizes(int[][] screenSizes) {
		this.screenSizes = screenSizes;
	}
	
}
