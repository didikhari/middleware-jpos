package com.codeinvocation.middlewar.client;

import java.util.Random;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.MUX;
import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;

public class IsoClientTest {

	public static void main(String[] args) throws Exception {		
		Q2 q2 = new Q2();
		q2.start();
		ISOUtil.sleep(5000);
		MUX mux = QMUX.getMUX("client-mux");
		ISOMsg isoMsg = new ISOMsg("0800");
		isoMsg.set(11, ISOUtil.getRandomDigits(new Random(), 6, 9));
		isoMsg.set(41, "T-112233");
		isoMsg.set(70, "301");
		ISOMsg response = mux.request(isoMsg, 30000);
		if (response != null) {
			String rc = response.getString(39);
			System.out.println("Echo Response Code "+rc);
		}
	}
}
