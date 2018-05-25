package com.storepoints.etws.oauth.common;

import java.io.UnsupportedEncodingException;
import java.util.BitSet;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;

public class OAuthCodec extends URLCodec {

	//From the spec: unreserved = ALPHA, DIGIT, '-', '.', '_', '~'

	protected static final BitSet unreserved = (BitSet) URLCodec.WWW_FORM_URL.clone();
	static {
		unreserved.clear('*');
		unreserved.clear(' ');
		unreserved.set('~');
	}

	/**
	 * Private constructor (instance methods not accessible).
	 */
	private OAuthCodec() {
	}

	/**
	 * Encode the specified value.
	 * 
	 * @param value
	 *            The value to encode.
	 * @return The encoded value.
	 */
	public static String oauthEncode(String value) {
	    if (value == null) {
	      return "";
	    }
	    try {
	      return new String(URLCodec.encodeUrl(unreserved, value.getBytes("UTF-8")), "US-ASCII");
	    }
	    catch (UnsupportedEncodingException e) {
	      throw new RuntimeException(e);
	    }
	  }

	/**
	 * Decode the specified value.
	 * 
	 * @param value
	 *            The value to decode.
	 * @return The decoded value.
	 */
	public static String oauthDecode(String value) throws DecoderException {
		if (value == null) {
			return "";
		}

		try {
			return new String(URLCodec.decodeUrl(value.getBytes("US-ASCII")),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}