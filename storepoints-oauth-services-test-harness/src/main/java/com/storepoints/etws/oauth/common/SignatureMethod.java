package com.storepoints.etws.oauth.common;

/**
 * This enum holds the supported signature methods.
 *
 */
public enum SignatureMethod {
	
	HMAC_SHA1("HMAC-SHA1"), RSA_SHA1("RSA-SHA1");

	private String val;
	
	private SignatureMethod(String value){
		val = value;
	}
	
	public String getValue(){
		return val;
	}
}
