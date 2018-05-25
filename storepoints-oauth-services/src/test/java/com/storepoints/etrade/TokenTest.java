package com.storepoints.etrade;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.BitSet;

import org.apache.commons.codec.net.URLCodec;
import org.junit.Before;
import org.junit.Test;

public class TokenTest  extends URLCodec{
	
    private static String secret = "382a2974b70d463be481275719dcd511";
    
    String tokenSecret=null;

    String requestTokenSecret=null;
    
    String requestTokenSecretEnc=null;
    
    
    private static final String ENC = "UTF-8";
    
	protected static final BitSet unreserved = (BitSet) URLCodec.WWW_FORM_URL.clone();
	
	static {
		unreserved.clear('*');
		unreserved.clear(' ');
		unreserved.set('~');
	}
    
    

	
	@Before
	public void setup() throws UnsupportedEncodingException {
		tokenSecret=new String((secret + "&").getBytes(ENC), "US-ASCII");
		setRequestTokenSecret("ABC123");
		requestTokenSecretEnc=new String((getRequestTokenSecret()).getBytes(ENC), "US-ASCII");
	}
	

	
	@Test
	public void test() throws UnsupportedEncodingException {
		
		String key = new String(URLCodec.encodeUrl(unreserved, secret.getBytes("UTF-8")), "US-ASCII")+"&"+ new String(URLCodec.encodeUrl(unreserved, getRequestTokenSecret().getBytes("UTF-8")), "US-ASCII");
		assertTrue(key.equals(tokenSecret+requestTokenSecretEnc));

	}



	public String getRequestTokenSecret() {
		return requestTokenSecret;
	}



	public void setRequestTokenSecret(String requstTokenSecret) {
		this.requestTokenSecret = requstTokenSecret;
	}
}
