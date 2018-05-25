package com.storepoints.exception;

public class ApplicationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int errorCode;
	
    public ApplicationException() {
        super();
    }

    public ApplicationException(int errorCode, String errorMessage) {
    	this.errorCode=errorCode;
    	this.errorMessage=errorMessage;
    }
	
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private String errorMessage;
	
	

}
