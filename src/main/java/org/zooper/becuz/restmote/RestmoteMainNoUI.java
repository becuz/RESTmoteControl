package org.zooper.becuz.restmote;

/**
 * Entry point to start the server without user interface
 * @author bebo
 *
 */
public class RestmoteMainNoUI {

	public static void main(String[] args) throws Exception {
		boolean forcePopulateDb = false;
		if (args != null && args.length > 0){
			forcePopulateDb = Boolean.parseBoolean(args[0]);
		}
		new RestmoteControl().run(forcePopulateDb);
	}
}