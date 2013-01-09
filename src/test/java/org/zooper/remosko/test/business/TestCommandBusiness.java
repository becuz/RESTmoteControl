/**
 * 
 */
package org.zooper.remosko.test.business;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.zooper.becuz.restmote.business.CommandBusiness;
import org.zooper.becuz.restmote.model.Command;
import org.zooper.remosko.test.TestAbstract;

/**
 * @author bebo
 *
 */
public class TestCommandBusiness extends TestAbstract{

	private CommandBusiness business = new CommandBusiness();
	
	@Test
	public void testBasicOps() {
		int size = business.getAll().size();
		assertTrue(size > 0);
		Command command = new Command("name", "p");
		business.store(command);
		assertTrue(business.getAll().size() == size+1);
		business.delete(command);
		assertTrue(business.getAll().size() == size);
		command = business.getByName(commandDefault.getName());
		assertTrue(command.getName().equals(commandDefault.getName()));
		assertTrue(command.getCommand().equals(commandDefault.getCommand()));
		command = business.getByName("");
		assertNull(command);
	}
	

}
