package com.codeinvocation.middleware.jpos;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.util.LogEvent;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;

import com.codeinvocation.middleware.constant.MTI;
import com.codeinvocation.middleware.constant.NetworkManagementCode;
import com.codeinvocation.middleware.constant.ProcessingCode;
import com.codeinvocation.middleware.handler.EchoMessageHandler;
import com.codeinvocation.middleware.handler.IMessageHandler;

public class MiddlewareRequestListener implements ISORequestListener, LogSource {

	private Logger logger;
	private String realm;
	
	@Override
	public boolean process(ISOSource source, ISOMsg requestMsg) {
		LogEvent evt = new LogEvent(this, realm);
		try {
			IMessageHandler messageHandler = getMessageHandler(requestMsg);
			ISOMsg responseMsg = messageHandler.handle(requestMsg, evt);
			if (responseMsg != null) {
				source.send(responseMsg);
			}
		} catch (Exception e) {
			evt.addMessage(e);
			
		} finally {
			Logger.log(evt);
		}
		return false;
	}

	private IMessageHandler getMessageHandler(ISOMsg requestMsg) throws Exception {
		
		IMessageHandler messageHandler = null;
		
		String type = requestMsg.getMTI();
		String processingCode = requestMsg.getString(3);
		String networkManagementCode = requestMsg.getString(70);
		
		if (MTI.NETWORK_MANAGEMENT.getString().equals(type)) {
			if (NetworkManagementCode.ECHO.val.equals(networkManagementCode)) {
				messageHandler = EchoMessageHandler.getInstance();
			}
			else if (NetworkManagementCode.SIGN_ON.val.equals(networkManagementCode)) {
				// TODO Sign On
			}
			else if (NetworkManagementCode.SIGN_OFF.val.equals(networkManagementCode)) {
				// TODO Sign Off
			}			
		}
		else if (MTI.TRANSACTIONAL.getString().equals(type)) {
			if (ProcessingCode.INQUIRY.val.equals(processingCode)) {
				// TODO Inquiry
			}
			if (ProcessingCode.PAYMENT.val.equals(processingCode)) {
				// TODO Payment
			}
		}
		else if (MTI.ADVICE.getString().equals(type)) {
			// TODO Payment Advice
		}
		else if (MTI.ADVICE_REPEAT.getString().equals(type)) {
			// TODO Payment Advice Repeat
		}
		else if (MTI.REVERSE.getString().equals(type)) {
			// TODO Payment Reversal
		}
		return messageHandler;
	}

	@Override
	public void setLogger(Logger logger, String realm) {
		this.logger = logger;
		this.realm = realm;
	}

	@Override
	public String getRealm() {
		return this.realm;
	}

	@Override
	public Logger getLogger() {
		return this.logger;
	}
}
