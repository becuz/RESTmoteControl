package org.zooper.becuz.restmote;

/**
 * Entry point to start the server without user interface
 * @author bebo
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		new RestmoteControl().run(true);
	}
}