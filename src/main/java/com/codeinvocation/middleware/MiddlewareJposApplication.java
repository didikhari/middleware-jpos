package com.codeinvocation.middleware;

import org.jpos.q2.Q2;

public class MiddlewareJposApplication {

	public static void main(String[] args) {
		Q2 q2 = new Q2();
		q2.start();
	}
}
