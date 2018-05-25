package com.storepoints.etws.oauth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.storepoints.etws.oauth.common.OAuthParameter;


/**
 * This class has a list of OAuth Parameters that are 
 * needed for accessing protected resources endpoint(s)
 * 
 *
 */
public class AccessProtectedResourceParameters {
	
	private List<OAuthParameter> values = new ArrayList<OAuthParameter>();
	
	private final static AccessProtectedResourceParameters thisObj = new AccessProtectedResourceParameters();
	
	private AccessProtectedResourceParameters(){
		values.add(OAuthParameter.oauth_consumer_key);
		values.add(OAuthParameter.oauth_nonce);
		values.add(OAuthParameter.oauth_timestamp);
		values.add(OAuthParameter.oauth_signature);
		values.add(OAuthParameter.oauth_signature_method);
		values.add(OAuthParameter.oauth_token);
		values = Collections.unmodifiableList(values);
	}
	
	public static AccessProtectedResourceParameters getInstance(){
		return thisObj;
	}
	
	public List<OAuthParameter> getValues(){
		return values;
	}
}