package org.zooper.becuz.restmote.business;

import org.apache.log4j.Logger;
import org.zooper.becuz.restmote.business.interfaces.BusinessModelAbstract;
import org.zooper.becuz.restmote.model.Command;

public class CommandBusiness extends BusinessModelAbstract<Command>{
	
	protected static final Logger log = Logger.getLogger(CommandBusiness.class.getName());

	public CommandBusiness() {
		super(Command.class);
	}
	
}
