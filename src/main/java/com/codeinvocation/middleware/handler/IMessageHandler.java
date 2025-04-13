package com.codeinvocation.middleware.handler;

import org.jpos.iso.ISOMsg;
import org.jpos.util.LogEvent;

public interface IMessageHandler {

	public ISOMsg handle(ISOMsg reqMsg, LogEvent evt) throws Exception;
}
