package com.storepoints.etrade.api;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * a simple program to get Access token and token secret.
 * 
 * 
 */
public class OAuthAccessToken extends ApiBase {

    private  String verifier;
	private  String requestToken;
	private  String requestTokenSecret;
	private  String accessToken;
	private  String accessTokenSecret;
	
    public OAuthAccessToken(String requestToken,String requestTokenSecret, String verifier) {
    	this.requestToken=requestToken;
    	this.requestTokenSecret=requestTokenSecret;
    	this.verifier=verifier;
    }
    


    
    
    public  void accessToken() throws ClientProtocolException,
    IOException, URISyntaxException, InvalidKeyException,
    NoSuchAlgorithmException {
    	
    	String accessTokenResponse=null;
    	
        HttpClient httpclient = new DefaultHttpClient();
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        // These params should ordered in key
//        qparams.add(new BasicNameValuePair("oauth_callback", "oob"));
        qparams.add(new BasicNameValuePair("oauth_consumer_key", getConsumerKey()));
        qparams.add(new BasicNameValuePair("oauth_nonce", ""
                + (int) (Math.random() * 100000000)));
        qparams.add(new BasicNameValuePair("oauth_signature_method",
                "HMAC-SHA1"));
        qparams.add(new BasicNameValuePair("oauth_timestamp", ""
                + (System.currentTimeMillis() / 1000)));
//        qparams.add(new BasicNameValuePair("oauth_version", "1.0"));
        qparams.add(new BasicNameValuePair("oauth_token", getRequestToken()));
        qparams.add(new BasicNameValuePair("oauth_verifier", getVerifier()));
        
        // generate the oauth_signature
        String signature = getSignature("GET", URLEncoder.encode(
        		getServerHostProtocol()+"://"+getOAuthServerHostConfig()+"/oauth/access_token", ENC),
                URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC), false);

        // add it to params list
        qparams.add(new BasicNameValuePair("oauth_signature", signature));

        // generate URI which lead to access_token and token_secret.
        URI uri = URIUtils.createURI(getServerHostProtocol(), getOAuthServerHostConfig(), -1,
                "/oauth/access_token",
                URLEncodedUtils.format(qparams, ENC), null);

        System.out.println("Get Token and Token Secrect from:"
                + uri.toString());

        HttpGet httpget = new HttpGet(uri);
        // output the response content.
        System.out.println("Token and Token Secrect:");

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            int len;
            byte[] tmp = new byte[2048];
            while ((len = instream.read(tmp)) != -1) {
                accessTokenResponse=new String(tmp, 0, len, ENC);
            }
        }
        
        
        System.out.println(accessTokenResponse);
        
        setAccessToken(accessTokenResponse.substring(12,accessTokenResponse.indexOf("&")));
        
        setAccessTokenSecret(accessTokenResponse.substring(accessTokenResponse.indexOf("oauth_token_secret=")+19));

    }

    
    public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

    public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}


	public String getRequestTokenSecret() {
		return requestTokenSecret;
	}


	public void setRequestTokenSecret(String requestTokenSecret) {
		this.requestTokenSecret = requestTokenSecret;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}


	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	@Override
	protected String getTokenSecret() {
		return this.requestTokenSecret;
	}

}
