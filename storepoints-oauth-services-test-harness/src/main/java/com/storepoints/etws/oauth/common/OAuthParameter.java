package com.storepoints.etws.oauth.common;

public enum OAuthParameter {

	/**
	 * realm is added so that the realm can be pulled out along with the other oauth parameters while processing the
	 * oauth schemed authorization header. Please note that realm is not supposed to be an outh parameter.
	 * MP: Added oauth_callback per the new spec
	 * MP: Added oauth_verifier per the new spec
	 */
	oauth_consumer_key,oauth_token,oauth_signature_method, oauth_signature, oauth_timestamp, oauth_nonce, oauth_version,realm, oauth_callback, oauth_verifier,xoauth_module,xoauth_response_format;


	/**
	 * This method will help check if the given string is a valid oauth parameter or not.
	 * Again, as noted above realm is not a valid oauth parameter but we want to extract it during the header processing
	 * without adding any inconsistency. 
	 * 
	 * @param someParam
	 * @return
	 */
	public static boolean isOAuthParameter(String someParam){
		try{
			return (someParam!=null && valueOf(someParam)!=null);
		}catch(Exception e){
			//no point logging this exception - just return false
		}
		return false;
	}
}