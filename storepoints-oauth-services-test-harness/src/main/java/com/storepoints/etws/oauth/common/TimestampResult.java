package com.storepoints.etws.oauth.common;

/**
 * This enum holds the respones for nonce/timestamp errors
 * 
 * 
 */
public enum TimestampResult {

	TOO_EARLY("Timestamp too early"), TOO_LATE("Timestamp too late"), INVALID(
			"Timestamp Invalid"), OK("");

	private String string;

	private TimestampResult(String string) {
		this.string = string;
	}

	public String getValue(){
		return string;
	}
}
