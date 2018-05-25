package com.storepoints.etws.oauth.common;
/**
 * This enum holds the enumeration of supported protocol schemes
 *
 */
public enum SupportedSchemes {

	HTTP,HTTPS;
	
	public static boolean isSupportedScheme(String scheme){
		try{
			return (valueOf(scheme.toUpperCase())!=null);
		}catch(Exception ex){
			return false;
		}
	}
	
}
