package com.codeinvocation.middleware.handler;

import org.jpos.iso.ISOMsg;
import org.jpos.util.LogEvent;

public class EchoMessageHandler implements IMessageHandler {

	private EchoMessageHandler() {}
	private static final EchoMessageHandler INSTANCE = new EchoMessageHandler();
	public static EchoMessageHandler getInstance() {
		return INSTANCE;
	}
	
	@Override
	public ISOMsg handle(ISOMsg reqMsg, LogEvent evt) throws Exception {
		ISOMsg respMsg = (ISOMsg) reqMsg.clone();
		respMsg.setResponseMTI();
		
		try {
			evt.addMessage("Echo Handler Start");
			respMsg.set(39, "00");
			return respMsg;
			
		} catch (Exception e) {
			evt.addMessage(e);
			return null;
			
		} finally {
			evt.addMessage("Echo Handler Done");
		}
	}

}
