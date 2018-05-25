package com.storepoints.etws.oauth;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.security.SecureRandom;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.log4j.Logger;

import com.storepoints.etws.oauth.common.DuplicateOauthException;
import com.storepoints.etws.oauth.common.OAuthCodec;
import com.storepoints.etws.oauth.common.OAuthParameter;
import com.storepoints.etws.oauth.common.SupportedSchemes;
import com.storepoints.etws.oauth.common.UnsupportedURLSchemeException;

/**
 * This class operates on Maps and Strings instead of the usual HttpServletRequest.
 * This lets us do unit tests, as well as possibly release this class as a helper for 
 * any Java clients we may release.
 * 
 * @author mvummidi
 *
 */
public class OAuthSignatureUtils {

	
	private static String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        private static String ACCOUNTS_MODULE="accounts";
        private static String ORDERS_MODULE="order";
        private static String USER_MODULE="user";
        private static String MARKET_MODULE="market";
        private static String REST="rest";
        private static String SANDBOX="sandbox";

	private static Logger logger = Logger.getLogger(OAuthSignatureUtils.class);
	
	/**
	 * This method returns a Map of key-values after processing all the Authorization headers.
	 * It will pull out the parameters, and decode (and unquote) them per the OAuth Spec.
	 * 
	 * The method identified an OAuth Authorization header by the scheme, which is case insensitive "OAuth"
	 * For example (realm is optional)
	 * 
	 * OAuth realm="https://api.etrade.com",oauth_consumer_key="xxx",oauth_token="token",oauth_version="1.0"
	 * 
	 * @param headers
	 * @return
	 */

	public static Map<OAuthParameter,String> parseOAuthParametersFromHeaders(Enumeration<String> headers) throws DuplicateOauthException{
		Map<OAuthParameter,String> map = new HashMap<OAuthParameter,String>();
		while(headers!=null && headers.hasMoreElements()){
			map.putAll(parseOAuthParametersFromHeader(headers.nextElement()));
		}
		return map;
	}

	/**
	 * This method is used to return the module name for uri with format using from API2.0
	 * @param uri
	 * @return
	 */
         public static String getModule(String uri)
         {
		String module = null;
		
		String[] uriTokens = null;
		
		if(uri!=null && uri.contains("/"))
		{
			uriTokens = uri.split("/");
			int tokenCount = uriTokens.length;
			logger.info("Token count = "+tokenCount);
			
			if(uri.indexOf(REST)!=-1)
			{
				module = uriTokens[1];
			}
			else if(tokenCount>=3 )
			{
                                for(String token:uriTokens)
                                {
                                	logger.info("Token="+token);
                                }
				if(uri.contains(ORDERS_MODULE))
				{
					module = ORDERS_MODULE;
				}
				else if(uri.contains(ACCOUNTS_MODULE))
				{
					module = ACCOUNTS_MODULE;
				}
				else if(uri.contains(USER_MODULE))
				{
					module = USER_MODULE;
				}
				else if(uri.contains(MARKET_MODULE))
				{
					module = MARKET_MODULE;
				}
			}
		}
		logger.info("Module="+module);
		return module;
        }

	
	/**
	 * This method returns a map of oauth parameters and their values given a string (like Authorization header).
	 * The method only returns a populated map if the header is an OAuth header (scheme = OAuth).
	 * @param paramString
	 * @return An unmodifiable map with the key-value pairs (decoded)
	 * @see OAuthParameter
	 * @throws DuplicateOauthException
	 */
	private static Map<OAuthParameter,String> parseOAuthParametersFromHeader(String paramString) throws DuplicateOauthException{
		final Map<OAuthParameter,String> map = new HashMap<OAuthParameter,String>();
		//check if the header is an oauth authorization header, we check with "oauth" followed by a space
		logger.info("Parsing oauth parameters from " + paramString);
		if(StringUtils.isEmpty(paramString) || !paramString.trim().toUpperCase().startsWith("OAUTH "))
			return map;
		//if this is oauth scheme, we need to remove the scheme name
		String nonScheme = paramString.trim().substring(6,paramString.length());
		logger.info("After removing scheme " + nonScheme.trim());
		String[] values = StringUtils.split(nonScheme.trim(),','); //remove the leading and trailing spaces if any
		for(String value:values){
			String[] keyValue = StringUtils.split(value,'=');
			if(keyValue.length==2 && OAuthParameter.isOAuthParameter(keyValue[0].trim())){
				if(map.containsKey(OAuthParameter.valueOf(keyValue[0].trim()))) 
					throw new DuplicateOauthException(keyValue[0].trim());
				//validate if the parameter exists!
				try {
					String key = OAuthCodec.oauthDecode(keyValue[0].trim());
					String val = OAuthCodec.oauthDecode(stripQuotes(keyValue[1]));
					map.put(OAuthParameter.valueOf(key),val);
					logger.debug("Added " + key + " and " + value + " to the parameter map");
				} catch (DecoderException e) {
					e.printStackTrace();
					logger.error(e,e);
				}
				
			}
		}
		return map;
	}
	
	
	/**
	 * This method accepts a map, more like a HTTPRequest Parameter Map and returns the OAuth fields from it.
	 * @param parameterMap
	 * @return
	 * @throws DuplicateOauthException if any oauth-parameters are found as duplicates
	 *
	 */
	public static Map<OAuthParameter,String> parseOAuthParametersFromRequestMap(Map<String,String[]> parameterMap) throws DuplicateOauthException{
		Map<OAuthParameter,String> oauthMap = new HashMap<OAuthParameter,String>();
		logger.info("Parsing OAuth Parameters from request map " + parameterMap);
		if(parameterMap==null || parameterMap.isEmpty()) return oauthMap;
		for(String name:parameterMap.keySet()){
			/*if(name.equalsIgnoreCase("consumerkey")) {
				//Hack for notification...
				name = OAuthParameter.oauth_consumer_key.toString();
			}*/
			logger.info("Request Param " + name + " value " + parameterMap.get(name)[0]);
			String[] values = parameterMap.get(name);
			if(OAuthParameter.isOAuthParameter(name)){
				if(values.length>1) throw new DuplicateOauthException(name);
				else
					try {
						String value = values[0];
						if(value.indexOf("%") > 0) {
							//Which means decode the paramaters
							logger.info("Inside Decode");
							oauthMap.put(OAuthParameter.valueOf(OAuthCodec.oauthDecode(name)), OAuthCodec.oauthDecode(values[0]));
						} else { 
							//do not decode as its already decoded in the incoming request
							logger.info("No Decoding");
							oauthMap.put(OAuthParameter.valueOf(name), values[0]);
						}
					} catch (DecoderException e) {
						//nothing we can do about it, ignore the key/value
						logger.error(e,e);
					}
			}
		}
		
		return Collections.unmodifiableMap(oauthMap);
	}	
	
	
	
	/**
	 * All the oauth parameters from the header are quoted, but we need to remove the quotes for signing and other checks.
	 * This is a safe method, in that it returns the original value if it cannot figure out if the string
	 * is quoted.
	 * 
	 * @param string
	 * @return
	 */
	public static String stripQuotes(String string){
		if(StringUtils.isNotBlank(string) && string.startsWith("\"") && string.endsWith("\"")){
			 return string.substring(1, string.length()-1);
		}
		//if we cannot figure out what to do, return it untouched
		return string;
	}

	
	/**
	 * This method will return the normalized parameter string given the request map and the enumeration of
	 * Authorization headers
	 * This method implements section 9.1.1 and 9.1.2 of the 1.0 Spec
	 * Request parameters are collected, sorted and concatenated into a normalized string
	 * This method has been tested with http://oauth.pbwiki.com/TestCases section 9.1.1 test cases
	 * Please note that the method returns an OAuth-encoded string, so you do not need to re-encode it.
	 * @param requestMap A map from the HttpServletRequest
	 * @param authorizationHeaders An enumeration of all authorization headers for the request. The API will filter
	 * out non-OAuth Authorization headers.
	 * @return The normalized request parameter string as specified in Spec Section 9.1 {unecoded}
	 * 
	 */

	public static String getNormalizedParamString(Map<String,String[]> requestMap, Enumeration<String> authorizationHeaders) throws DuplicateOauthException{
		
		Map<String,String[]> collectedParams = new HashMap<String,String[]>();
		Map<OAuthParameter,String> oauthParameters = new HashMap<OAuthParameter,String>();
		try {
			oauthParameters.putAll(parseOAuthParametersFromHeaders(authorizationHeaders));
		} catch (DuplicateOauthException e1) {
			logger.error(e1,e1);
			throw e1;
		}
		
		//if this map contains the realm, we got to remove that
		oauthParameters.remove(OAuthParameter.realm);
		//the oauthParameters map has the OAuth HTTP Authorization, if present. 
		//Convert that map to string
		if(!oauthParameters.isEmpty()) //cant be null anyway
			collectedParams.putAll(convertOAuthMapToStringArrayMap(oauthParameters));
		//next, get params from the request body - ignore the multipart body
		//get from the querystring if not null
		collectedParams.putAll(requestMap);
		//remove the oauth_signature parameter
		logger.info("Removing the signature parameter from the map");
		collectedParams.remove(OAuthParameter.oauth_signature.name());
		//now we encode the names and values, and sort them
		TreeMap<String,String[]> sortedMap = new TreeMap<String,String[]>();
		for(String key:collectedParams.keySet()){
			//the value can be null, in which case we do not want any associated array here
			String[] encodedValues = (collectedParams.get(key)!=null) ? new String[collectedParams.get(key).length] : new String[0];
			//encode keys, and sort the array
			for(int i=0; i< encodedValues.length;i++){
				encodedValues[i] = OAuthCodec.oauthEncode(collectedParams.get(key)[i]);
				logger.info("Encoded values are "+encodedValues[i]);
			}
			Arrays.sort(encodedValues);
			sortedMap.put(OAuthCodec.oauthEncode(key), encodedValues);
		}
		//now we generate a string for the map in key=value&key1=value1 format by concatenating the values
		StringBuilder normalizedRequest = new StringBuilder();
		for(String key: sortedMap.keySet()){
			//we need to handle the case if the value is null 
			if(sortedMap.get(key)==null || sortedMap.get(key).length==0){
				normalizedRequest.append(key+"=&");
			}
			for(String value: sortedMap.get(key)){
				//this for loop will not execute if the value is null or empty
				normalizedRequest.append(key+"="+value+"&");
			}
		}
		//remove the extra & at the end
		String normalizedParamString = normalizedRequest.toString().substring(0,normalizedRequest.length()-1);
		logger.info("Normalized Parameter String is " + normalizedParamString);
		return normalizedParamString;
	}
	
	/**
	 * This method will return the base string for the request, which is used in signing.
	 * This method implements Section 9.1 of the spec 1.0
	 * @param normalizedParamString from getNormalizedParamString (unencoded)
	 * @param httpMethod The HTTP method used to make the call
	 * @param requestUrl The request URL obtained by request.getRequestURL()
	 * @param hostHeader The HTTP header value for Host, to comply with the spec.
	 * @return Signature base string 
	 */
	public static String getSignatureBaseString(String normalizedParamString, String httpMethod, String requestUrl, String hostHeader)
	throws MalformedURLException, UnsupportedURLSchemeException{
		//uppercase the httpMethod
		//TODO Validate against a list of supported HTTP methods
		if(StringUtils.isEmpty(httpMethod)) throw new IllegalArgumentException("Unsupported HTTP Method " + httpMethod);
		httpMethod = httpMethod.toUpperCase();
		//now figure out the URL
		URL url = new URL(requestUrl);
		//get the scheme - http or https
		String scheme = url.getProtocol();
		if(!SupportedSchemes.isSupportedScheme(scheme)) throw new UnsupportedURLSchemeException(scheme);
		//the scheme needs to be lowercase
		scheme = scheme.toLowerCase();
		//now figure out the port
		int port =  url.getPort();
		String host = url.getHost();
		//According to the spec, the values must match the ones in the host, if present
		if(StringUtils.isNotBlank(hostHeader)){
			//parse the header to check the values against the URL
			logger.info("Host header is not empty, using the header");
			//header format is <host>[:]<port>
			String[] hostPart = StringUtils.split(hostHeader,":");
			//if array size is > 1, we got the port
			String headerHost = hostPart[0];
			int headerPort = -1;
			if(hostPart.length==2)
				headerPort = Integer.parseInt(hostPart[1]);
			logger.info("After processing host header: host is " + host + " port is " + port);
			//do the checks here
			//host first
			if(!headerHost.equals(host))
				host = headerHost; //default to using header host if they are not same
			if(headerPort!=port){
				port = headerPort; //default to using the header port
			}
		}
		//check if the port is the scheme default port. If yes, we do not include it in the signature
		port = (port==url.getDefaultPort()) ? -1:url.getPort();
		if(port==-1){
			logger.info("Default port, not including in signature string");
		}

		//now we create the signature base string
		StringBuilder baseString = new StringBuilder();
		baseString.append(scheme+"://");
		baseString.append(host.toLowerCase());
		if(port!=-1){
			baseString.append(":"+port);
		}
		//take care of the path
		String path = url.getPath();
		if(StringUtils.isNotEmpty(path))
			baseString.append(path);
		logger.info("Section 9.1.2 Request URL is " + baseString.toString());
		//OAuth encode this string
		String baseStringUri = OAuthCodec.oauthEncode(baseString.toString()); 
		logger.info("The base string uri after encoding is " + baseStringUri);
		//OAuth encode the normalizedParamString
		logger.info("The param string before encoding is " + normalizedParamString);
		String normalizedEncodedParamString = OAuthCodec.oauthEncode(normalizedParamString);
		logger.info("The param string after encoding is " + normalizedEncodedParamString);
		String signatureBaseString = httpMethod+"&"+baseStringUri+"&"+normalizedEncodedParamString;
		logger.info("The signature base string is " + signatureBaseString);
		return signatureBaseString.toString();
	}
	
	/**
	 * This method will return a signature string (base64 encoded) to be checked against the passed signature string
	 * by the validator. It uses HMAC-SHA1 algorithm.
	 * @param signatureBaseString, which is encoded per the Spec section 9.1
	 * @param sharedSecret, the consumer secret, unecoded
	 * @param tokenSecret, the token secret, if available
	 * @return
	 */
	public static String getHMACSHA1Signature(String signatureBaseString, String sharedSecret, String tokenSecret){
		//TODO Throw a generic exception to wrap the ones below
		String key = StringUtils.isEmpty(tokenSecret) ? OAuthCodec.oauthEncode(sharedSecret)+"&" : 
			OAuthCodec.oauthEncode(sharedSecret)+"&"+OAuthCodec.oauthEncode(tokenSecret);
		//if token secret is empty, do not include it in the key
		logger.info("Key is " + key); 
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),HMAC_SHA1_ALGORITHM);
		// get an hmac_sha1 Mac instance and initialize with the signing key
		Mac mac = null;
		try {
			mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e,e);
			throw new IllegalArgumentException(e);
		}
		try {
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			logger.error(e,e);
			throw new IllegalArgumentException(e);
		}
		// compute the hmac on the signatureBaseString
		byte[] raw = mac.doFinal(signatureBaseString.getBytes());
		// base64-encode the hmac
		String result = new String(Base64.encodeBase64(raw));
		logger.info("The signature string via HMAC-SHA1 is " + result);
		return result;
	}
	
	
	/**
	 * This method will return the signature using the RSA-SHA1 algorithm
	 * 
	 * @param signatureBaseString The signature base string
	 * @param publicKey The public key of the consumer
	 * @return The RSA-SHA1 Signature string (base64 encoded)
	 */
	public static String getRSASHA1Signature(String signatureBaseString, String publicKey){
		//TODO Implement this
		throw new IllegalArgumentException("RSA-SHA1 Not supported yet");
	}
	
	
	private static Map<String,String[]> convertOAuthMapToStringArrayMap(Map<OAuthParameter,String> oauthMap){
		if(oauthMap==null) return null;
		Map<String,String[]> stringMap = new HashMap<String,String[]>(oauthMap.size());
		for(OAuthParameter parameter:oauthMap.keySet()){
			stringMap.put(parameter.name(), new String[]{oauthMap.get(parameter)});
		}
		return stringMap;
	}
	
	public static String sign(String url, String httpMethod, Map<String, String[]> parameters, String oauth_consumer_key, String oauth_consumer_secret, String oauth_access_token, String oauth_token_secret, String oauth_callback) {
		
		return sign(url, httpMethod, parameters, oauth_consumer_key, oauth_consumer_secret, oauth_access_token, oauth_token_secret, oauth_callback, null);
	}
	
	public static String sign(String url, String httpMethod, Map<String, String[]> parameters, String oauth_consumer_key, String oauth_consumer_secret, String oauth_access_token, String oauth_token_secret, String oauth_callback, String oauth_verifier) {
		String oauth_timestamp = "" + System.currentTimeMillis() / 1000;
		SecureRandom secureRand = new SecureRandom();
		//Random generator = new Random();
		//long generatedNo = generator.nextLong();
		long generatedNo = secureRand.nextLong();
		String oauth_nonce = new String(Base64.encodeBase64((""+generatedNo).getBytes()));
		//String oauth_nonce = new String(Base64.encodeBase64(oauth_timestamp
		//		.getBytes()));
		logger.info("OAuth Nonce is "+oauth_nonce);
		String oauth_signature_method = "HMAC-SHA1";
		HashMap<String, String[]> requestMap = new HashMap<String, String[]>();
		requestMap.put("oauth_consumer_key",
				new String[] { oauth_consumer_key });
		requestMap.put("oauth_timestamp", new String[] { oauth_timestamp });
		requestMap.put("oauth_nonce", new String[] { oauth_nonce });
		requestMap.put("oauth_signature_method",
				new String[] { oauth_signature_method });
		//encode the token as it is decoded in the argument
		requestMap.put("oauth_token",new String[]{oauth_access_token});
		if(parameters != null && parameters.size() > 0) 
			requestMap.putAll(parameters);

		if(oauth_callback != null && oauth_callback.length() > 0)
			requestMap.put("oauth_callback", new String[] {oauth_callback});
		
		if(oauth_verifier != null && oauth_verifier.length() > 0)
			requestMap.put("oauth_verifier", new String[] {oauth_verifier});
		
		String normalizedParamString = null;
		try {
			normalizedParamString = OAuthSignatureUtils.getNormalizedParamString(requestMap, null);
		} catch (DuplicateOauthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Normalized Parameter String is "
				+ normalizedParamString);
		String signatureBaseString = null;
		try {
			signatureBaseString = OAuthSignatureUtils
					.getSignatureBaseString(normalizedParamString, httpMethod, url ,null);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedURLSchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Signature Base String is " + signatureBaseString);
		String signature = OAuthSignatureUtils.getHMACSHA1Signature(
				signatureBaseString, oauth_consumer_secret, oauth_token_secret);
		requestMap.put("oauth_signature", new String[] { signature });
		String authHeader = generateHeader(requestMap);
		logger.info("Header is "+ authHeader);
		return authHeader;
	}
	
	private static String generateHeader(HashMap<String, String[]> requestMap) {
		String header = "OAuth realm=\"\"";
		for(String key: requestMap.keySet()) {
			if(key.startsWith("oauth_")) {
				header += "," + OAuthCodec.oauthEncode(key) + "=\"" + OAuthCodec.oauthEncode(requestMap.get(key)[0]) +"\"";
			}
		}
		return header;
	}
}