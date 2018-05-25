package com.storepoints.etws.oauth.common;

public class DuplicateOauthException extends Exception {

	//TODO Rename this to DuplicateOAuthException to maintain consistency
	
	private static final long serialVersionUID = 1L;

	public DuplicateOauthException() {
		super();
	}
	
	public DuplicateOauthException(String mesg){
		super(mesg);
	}
	
}
