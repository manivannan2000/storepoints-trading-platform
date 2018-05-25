package com.storepoints.etws.oauth.common;
/**
 * This exception is thrown if the scheme is unsupported.
 * 
 *
 */
public class UnsupportedURLSchemeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public UnsupportedURLSchemeException(String scheme) {
		super(scheme + " is not supported");
	}

}
