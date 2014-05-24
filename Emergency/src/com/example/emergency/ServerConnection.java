package com.example.emergency;

public class ServerConnection {

	//String dyndns = "http://lmattano.noip.me:8443/";
	String local = "http://10.0.0.4/";
	String dyndns = "http://10.0.0.4/";
	
	public String getServerAdr(String server) {
		
		if(server.equals("dyndns")) {
			return dyndns;
		} if(server.equals("local")) {
			return local;
		}
		return null;
	}
}
