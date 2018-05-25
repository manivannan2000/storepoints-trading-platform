package com.storepoints.etws.oauth;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;


//import com.storepoints.etws.oauth.AccessToken;
//import com.storepoints.etws.oauth.AuthorizeTokenParameters;
//import com.storepoints.etws.oauth.ConsumerApp;
//import com.storepoints.etws.oauth.ConsumerKey;
//import com.storepoints.etws.oauth.ConsumerManager;
//import com.storepoints.etws.oauth.ConsumerModule;
import com.storepoints.etws.oauth.common.DuplicateOauthException;
//import com.storepoints.etws.oauth.GetAccessTokenParameters;
//import com.storepoints.etws.oauth.GetRequestTokenParameters;
//import com.storepoints.etws.oauth.HttpMethod;
//import com.storepoints.etws.oauth.ModuleMetaData;
//import com.storepoints.etws.oauth.ModuleStatus;
//import com.storepoints.etws.oauth.NoDataFoundException;
//import com.storepoints.etws.oauth.NoSuchConsumerException;
//import com.storepoints.etws.oauth.NoSuchTokenException;
import com.storepoints.etws.oauth.common.OAuthParameter;
//import com.storepoints.etws.oauth.OAuthRequestType;
import com.storepoints.etws.oauth.OAuthValidator;
//import com.storepoints.etws.oauth.RequestToken;
//import com.storepoints.etws.oauth.RevokeAccessTokenParameters;
import com.storepoints.etws.oauth.common.SignatureMethod;
//import com.storepoints.etws.oauth.TimestampNonce;
//import com.storepoints.etws.oauth.TimestampNonceException;
import com.storepoints.etws.oauth.common.TimestampResult;
//import com.storepoints.etws.oauth.Token;
//import com.storepoints.etws.oauth.TokenResult;
//import com.storepoints.etws.oauth.TokenStatus;
import com.storepoints.etws.oauth.common.UnsupportedURLSchemeException;

public class OAuthValidator {
	/**
	 * Singleton Validator.
	 */
	private static OAuthValidator validator = null;

	/**
	 * Used to lock the instantiation
	 */
	private final static Object object = new Object();
	
	private final Logger logger = LogManager.getLogger(this.getClass().getName());


	/**
	 * Noargs, private constructor
	 */
	private OAuthValidator() {
		// noargs
	}

	/**
	 * Instantiate the OAuth Validator - thread safe.
	 * 
	 * @return
	 */
	public static OAuthValidator getInstance() {

		if (validator == null) {
			synchronized (object) {
				if (validator == null)
					validator = new OAuthValidator();
			}
		}
		return validator;
	}

	/**
	 * This method makes sure the request is within the window (in milliseconds)
	 * of server time in UTC. The Window is configured via oauth.xml
	 * configuration for the filter.
	 * 
	 * @param timestamp
	 *            from the oauth request
	 * @param window
	 *            in seconds
	 * @return
	 */
	public TimestampResult validateTimestamp(String timestamp, long window) {
		try {
			// if timestamp is on oauth parameter mode, it starts and ends with
			// " that we need to get rid of
			timestamp = OAuthSignatureUtils.stripQuotes(timestamp);
			long ts = Long.parseLong(timestamp);
			if (ts < 0)
				return TimestampResult.INVALID; // handle negative numbers
			long currentTime = System.currentTimeMillis() / 1000;
			if (ts == currentTime)
				return TimestampResult.OK;
			if (ts > currentTime) {
				// chance that it'd be too early
				// check if within the window
				if ((ts - currentTime) >= window)
					return TimestampResult.TOO_EARLY;
			}
			if (ts < currentTime) {
				// chance that it'd be too late
				// check if within the window
				if ((currentTime - ts) >= window)
					return TimestampResult.TOO_LATE;
			}
			return TimestampResult.OK;
		} catch (Exception ex) {
			ex.printStackTrace();
			return TimestampResult.INVALID;
		}
	}

	public boolean validateAccessTokenExpiry(long createDate, long window) {
		try {
			long currentTime = System.currentTimeMillis() / 1000;
			if (currentTime - createDate < window) {
				// Access Token is still alive
				return true;
			} else {
				// Access Token is expired
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean validateConsumerKeyExpiry(long expireDateMillis) {
		return validateAccessTokenExpiry(expireDateMillis,23);
	}
	

//        public Date getTokenTTL(String consumer_key, String oauth_token) throws NoSuchTokenException {
//           try {
//              AccessToken token = AccessTokenManager.getInstance().getAccessToken(consumer_key,oauth_token);
//              TokenStatus status = token.getStatus();
//              System.out.println("Status is :"+status.toString());
//              if (status == TokenStatus.INVALIDATED || status == TokenStatus.REVOKED) {
//                 throw new NoSuchTokenException("Invalid Token");
//              }
//	      // Check Expiry of Access Token. Expires as defined
//	      // in oauth.xml
//              long createtokenTime = token.getCreateTime();
//	      int ttl = 0;
//   	      try {
//                 ConsumerKey consumer = validateConsumer(consumer_key);
//	         ttl = consumer.getAccessTokenTtl().intValue();
//              } catch(Exception e) {
//  	         logger.info("ACCESS_TOKEN_TTL IN DB is null");
// 	      }
//	      logger.info("TTl from DB is " + ttl);
//	      if (ttl == 0) {
//                 ttl = 24;
//                 logger.info("TTl from properties file is " + ttl);
//              }
//              Date validUntil = getAccessTokenValidUntil(createtokenTime, ttl);
//              logger.info("Token Valid Until " + validUntil);
//              return validUntil;
//           } catch(NoSuchTokenException e) {
//              e.printStackTrace();
//              throw e;
//           } catch(Exception e) {
//              throw new NoSuchTokenException("Invalid Token");
//           }
//        }
	public Date getAccessTokenValidUntil(long createDate, int hrsToExpire) {
                Calendar cal = null;
		try {
			Date d = new Date(createDate);
			Date currentDate = new Date();
			logger.info("Create Date is "+d.toString());
			cal = new GregorianCalendar();
			cal.setTime(d);
			if(hrsToExpire < 24) {
				cal.add(Calendar.HOUR_OF_DAY, hrsToExpire); 
			} else {
				cal.set(Calendar.HOUR_OF_DAY, hrsToExpire-1);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				cal.set(Calendar.MILLISECOND, 0);
			}
			logger.info("Expire Date is "+cal.getTime().toString());
		} catch(Exception e) {
			logger.error("Caught Exception");
		}
                return cal.getTime();
	}
	//This function checks for midnight expiry.
	public boolean validateAccessTokenExpiry(long createDate, int hrsToExpire) {
		try {
			Date d = new Date(createDate);
			Date currentDate = new Date();
			logger.info("Create Date is "+d.toString());
			Calendar cal = new GregorianCalendar();
			cal.setTime(d);
			if(hrsToExpire < 24) {
				cal.add(Calendar.HOUR_OF_DAY, hrsToExpire); 
			} else {
				cal.set(Calendar.HOUR_OF_DAY, hrsToExpire-1);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				cal.set(Calendar.MILLISECOND, 0);
			}
			logger.info("Expire Date is "+cal.getTime().toString());
			if(currentDate.before(cal.getTime())) {
				return false;
			} else {
				return true;
			}
		} catch(Exception e) {
			return false;
		}
	}

	public boolean validateAccessTokenIdleTime(long window, long lastAccessTime) {
		try {
			long currentTime = System.currentTimeMillis()/1000;
			lastAccessTime = lastAccessTime/1000;
			logger.info("Current Time is " + currentTime);
			logger.info("Last Access Time is " + lastAccessTime);
			logger.info("Window is " + window);
			if (lastAccessTime > 0) {
				if (currentTime - lastAccessTime < window) {
					// Request is within 2hr window from previous request
					return true;
				} else {
					// Request time has elapsed
					return false;
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method will validate the TimestampNonce. It follows the algorithm
	 * documented in
	 * https://wiki.corp.etradegrp.com/index.php/OAuth_Architecture // This could further be simplified using the key as
	 * consumerkey+ts+nonce.  Do a select if record exist with this key its not valid else we insert into DB
	 * #Timestamp_Nonce_Handling
	 * 
	 * @param consumerKey
	 * @param oauthToken
	 * @param oauthTimestamp
	 *            in seconds
	 * @param oauthNonce
	 * @param ipAddress
	 * @return true or false, depending on the validity of the request.
	 */
	/*
	public boolean validateTimestampNonce(String consumerKey,
			String oauthToken, long oauthTimestamp, String oauthNonce,
			String ipAddress) {
		Collection<TimestampNonce> nonces = TimestampNonceManager.getInstance()
				.selectTimestampNonce(consumerKey, oauthToken, oauthTimestamp);
		// if nonces is null or empty, we insert a new record
		if (nonces == null || nonces.size() == 0) {
			TimestampNonce nonce = new TimestampNonce();
			nonce.setConsumerKey(consumerKey);
			nonce.setIpAddress(ipAddress);
			nonce.setOauthNonce(oauthNonce);
			nonce.setOauthToken(oauthToken);
			nonce.setServerTimestamp(System.currentTimeMillis() / 1000); // in
																			// seconds
			TimestampNonceManager.getInstance().insertTimestampNonce(nonce);
			return true;
		} else {
			// more than zero results, this means either the request is late or
			// same
			for (TimestampNonce nonce : nonces) {
				if (nonce.getOauthTimestamp() > oauthTimestamp) {
					return false;
				}
			}
			// check for equals now - if we are here that means none of the
			// nonces are greater, they have to be equal
			for (TimestampNonce nonce : nonces) {
				if (nonce.getOauthTimestamp() == oauthTimestamp) {
					// check the nonces, if
				}
			}
		}
		return false;
	}
	*/
//	public boolean validateTimestampNonce(String consumerKey, String oauthToken, String oauthTimestamp, String oauthNonce, String ipAddress) {
//		long ts = Long.parseLong(oauthTimestamp);
//		List<TimestampNonce> nonces = null;
//		try {
//			nonces = TimestampNonceManager.getInstance().selectTimestampNonce(consumerKey, ts, oauthNonce);
//		} catch(TimestampNonceException e) {
//			return false;
//		}
//		if(nonces.size() > 0) {
//			//this is an error scenario. 
//			//There should not be any record with consumerKey+oauthToken+oauthTimestamp+oauthNonce
//			return false;
//		} else {
//			//Insert the record into DB
//			TimestampNonce nonce = new TimestampNonce();
//			nonce.setConsumerKey(consumerKey);
//			nonce.setIpAddress(ipAddress);
//			nonce.setOauthNonce(oauthNonce);
//			nonce.setOauthToken(oauthToken);
//			nonce.setOauthTimestamp(ts);
//			nonce.setServerTimestamp(System.currentTimeMillis() / 1000); // in seconds
//			try {
//				TimestampNonceManager.getInstance().insertTimestampNonce(nonce);
//			} catch(TimestampNonceException e) {
//				return false;
//			}
//			return true;
//		}
//	}
	
//	public boolean validateTimestampNonce(String consumerKey, String oauthTimestamp, String oauthNonce, String ipAddress) {
//		return validateTimestampNonce(consumerKey,null,oauthTimestamp,oauthNonce,ipAddress);
//	}

	/**
	 * This is the facade to HMAC-SHA1 and RSA-SHA1 signature verification
	 * methods. For RSA-SHA1, replace consumerSecret with the consumer's public
	 * key
	 * 
	 * @param method
	 *            The signature method
	 * @param oauthSignature
	 *            The signature that came with the request
	 * @param consumerSecret
	 *            consumerSecret for HMAC-SHA1, public key for RSA-SHA1
	 * @param tokenSecret
	 *            leave it blank/null for RSA-SHA1 or for get_request_token
	 *            requests
	 * @param request
	 *            The HTTP Servlet Request
	 * @return true or false, depending on the validation result
	 */
	public boolean validateSignature(SignatureMethod method,
			String oauthSignature, String consumerSecret, String tokenSecret,
			HttpServletRequest request) {
		if (method == SignatureMethod.HMAC_SHA1)
			return validateHMACSHA1Signature(oauthSignature, consumerSecret,
					tokenSecret, request);
		if (method == SignatureMethod.RSA_SHA1)
			return validateRSASHA1Signature(oauthSignature, consumerSecret,
					request);
		// return false if all else fails..
		return false;
	}

	/**
	 * This method will validate the verifier that comes with get_access_token
	 * request. Alternatively, we could have not persisted the verifier and had
	 * the signature check handle this, but this would have then broken the
	 * original signature method with introduction of a client provided argument
	 * that is missing on the server side. The only parameters not provided by
	 * the client are the secrets, and in order to keep things simple let us not
	 * mix verifier with that. Additional cost is the db storage of the
	 * verifier, and a lookup for verification check.
	 * 
	 * @param consumerKey
	 * @param requestToken
	 * @param verifier
	 * @return
	 */
//	public boolean validateOAuthVerifier(String consumerKey,
//			String requestToken, String verifier) {
//
//		try {
//			RequestToken token = RequestTokenManager.getInstance()
//					.getRequestToken(consumerKey, requestToken);
//			if (token == null)
//				return false;
//			else
//				return token.getVerifier().equals(verifier);
//		} catch (Throwable e) {
//			logger.error(e, e);
//		}
//		return false;
//
//	}

	/**
	 * Thie method uses HMAC-SHA1 algorithm to validate the signature
	 * 
	 * @param oauthSignature
	 * @param consumerSecret
	 * @param tokenSecret
	 * @param request
	 * @return
	 */
	private boolean validateHMACSHA1Signature(String oauthSignature,
			String consumerSecret, String tokenSecret,
			HttpServletRequest request) {
		try {
			// step 1 We get the normalized parameter string for this request
			// (Spec 9.1.1)
			String normalizedRequestParameterString = OAuthSignatureUtils
					.getNormalizedParamString(request.getParameterMap(),
							request.getHeaders("Authorization"));
			// step 2 we get the signature base string
			System.out.println("Authorization header is "+request.getHeaders("Authorization"));
			System.out.println("Validator :Normalized parameter string "+normalizedRequestParameterString);
			System.out.println("Method is "+request.getMethod());
			System.out.println("Request url is "+request.getRequestURL().toString());
			System.out.println("Host header is "+request.getHeader("HOST"));
			String signatureBaseString = OAuthSignatureUtils
					.getSignatureBaseString(normalizedRequestParameterString,
							request.getMethod(), request.getRequestURL()
									.toString(), request.getHeader("Host"));
			System.out.println("Signature Base String is "+signatureBaseString);
			// the above 2 steps were needed regardless of the algorithm.
			// next we branch out based on the algorithm
			String signature = OAuthSignatureUtils.getHMACSHA1Signature(
					signatureBaseString, consumerSecret, tokenSecret);
			System.out.println("Server calculated the signature as " + signature
					+ " against the consumer provided " + oauthSignature);
			System.out.println("============Inside Validator ===========");
			System.out.println("OAuth Signature is "+oauthSignature);
			System.out.println("Calculated OAuth Signature is "+signature);
			return signature.equals(oauthSignature);
		} catch (MalformedURLException e) {
			// TODO throw a proper message here
			logger.error(e, e);
			return false;
		} catch (UnsupportedURLSchemeException e) {
			// TODO throw a proper message here
			logger.error(e, e);
			return false;
		} catch (DuplicateOauthException e) {
			// TODO Add a meaningful message here
			logger.error(e, e);
			return false;
		}
	}

	/**
	 * This method validates the signature using RSA-SHA1 algorithm
	 * 
	 * @param oauthSignature
	 * @param publicKey
	 * @param request
	 * @return
	 */
	private boolean validateRSASHA1Signature(String oauthSignature,
			String publicKey, HttpServletRequest request) {
		throw new IllegalArgumentException("RSA-SHA1 Not supported yet!");
	}

	/**
	 * The method to validate a consumer based on the key. This will be
	 * delegated to the ConsumerManager, which may implement caching if needed
	 * for this information.
	 * 
	 * @see ConsumerManager
	 * @param consumerKey
	 * @return Consumer - the consumer object, null if not found
	 */
//	public ConsumerKey validateConsumer(String consumerKey) {
//		logger.info("Validating consumer " + consumerKey);
//		ConsumerKey consumer;
//		consumerKey = OAuthSignatureUtils.stripQuotes(consumerKey);
//		try {
//			consumer = ConsumerManager.getInstance()
//					.getConsumerKey(consumerKey);
//			return consumer;
//		} catch (NoSuchConsumerException e) {
//			logger.error(e, e);
//                        return null;
//		}
//	}
//	
//	public ConsumerApp getConsumerApp(int consumerId) {
//		ConsumerApp app = null;
//		try {
//			app = ConsumerManager.getInstance().getConsumerApp(consumerId);
//		} catch(NoSuchConsumerException e) {
//			logger.error(e.getMessage());
//		}
//		return app;
//	}
//	
//	public boolean validateConsumerModule(int consumerId, String module) {
//		logger.info("Module in Validator is "+module);
//		List<ConsumerModule> modules = null;
//		try {
//			modules = ConsumerManager.getInstance().getConsumerModules(consumerId);
//			for(ConsumerModule mod: modules) {
//				//Get the module Id
//				int moduleId = mod.getModuleId();
//				logger.info("Modude Id is "+moduleId);
//				//Check if this module Id is same as the parameter
//				//Convert module into int
//				try {
//					ModuleMetaData md = ModuleMetaDataManager.getInstance().getModuleMetaData(module);
//					int md_id = md.getModule_id();
//					logger.info("MD_ID is "+md_id);
//					if(md_id == moduleId) {
//						logger.info("Both are same");
//						return true;
//					}
//				} catch (NoDataFoundException e) {
//					return false;
//				}
//			}
//		} catch(NoSuchConsumerException e) {
//		   logger.error(e,e);
//		}
//		return false;
//	}
//
//	/**
//	 * This method validates the incoming request token with the authorize_token
//	 * request. The token must be issues within the last n minutes, it must not
//	 * have been used before i.e. its state must be "created". The method will
//	 * also mark the state of the request token as "verified". If the token
//	 * state is anything but created, this means this call has been sent again,
//	 * where it will be rejected.
//	 * 
//	 * This follows the "Use RT only once" security measure.
//	 * 
//	 * @param requestToken
//	 * @return TokenResult
//	 */
//	public TokenResult validateRequestTokenForAuthorization(
//			String requestToken, String ttl) {
//		try {
//			int timeToLive = 300; // 5 minutes default
//			if (StringUtils.isNotEmpty(ttl) && StringUtils.isNumeric(ttl)) {
//				timeToLive = Integer.parseInt(ttl.trim());
//			}
//			RequestToken token = RequestTokenManager.getInstance()
//					.getRequestToken(requestToken);
//			// since this is authorization attempt, we make sure the state is
//			// nothing but "created"
//			if (token.getStatus() != TokenStatus.CREATED) {
//				return TokenResult.AUTH_ALREADY_ATTEMPTED;
//			}
//			// next we check if the token has not expired
//			// TODO: Put a time limit value here, we are using 300 seconds = 5
//			// mins
//			if ((System.currentTimeMillis() - token.getCreateTime()) / 1000 > timeToLive) {
//				return TokenResult.TOKEN_EXPIRED;
//			}
//			// all is good, its OK - here we mark the token as attempted auth
//			token.setStatus(TokenStatus.AUTH_REQUESTED);
//			token.setAuthorizationRequestTime(System.currentTimeMillis());
//			RequestTokenManager.getInstance().updateRequestToken(token);
//			return TokenResult.TOKEN_OK;
//		} catch (NoSuchTokenException e) {
//			logger.error(e, e);
//			// this is an invalid token
//			return TokenResult.INVALID_TOKEN;
//		}
//	}
//
//	/**
//	 * This method will validate, and return the Request Toke that is a part of
//	 * the get access token request. It needs to return the valid token because
//	 * a signature has to be performed on it It is merely a pass through to the
//	 * RequestTokenManager, except that it does a time-check
//	 * 
//	 * @param requestToken
//	 * @param consumerKey
//	 * @param ttl
//	 * @return
//	 */
//	public Token validateAndReturnRequestTokenForAccessToken(
//			String consumerKey, String requestToken, String ttl) {
//		int timeToLive = 300; // 5 minutes default
//		if (StringUtils.isNotEmpty(ttl) && StringUtils.isNumeric(ttl)) {
//			timeToLive = Integer.parseInt(ttl.trim());
//		}
//		try {
//			RequestToken token = RequestTokenManager.getInstance()
//					.getRequestToken(consumerKey, requestToken);
//			if (((System.currentTimeMillis() / 1000) - (token.getCreateTime() / 1000)) > timeToLive) {
//				return null; // TODO: How to differentiate between an invalid
//								// token and an expired token?
//			}
//			return token;
//		} catch (NoSuchTokenException e) {
//			logger.error(e, e);
//			return null; // no such token in the system
//		}
//	}
//
//	/**
//	 * This method will return the SignatureMethod enum value corresponding to
//	 * the string that is passed in the incoming request.
//	 * 
//	 * @param signatureMethod
//	 *            The oauth_signature_method string
//	 * @param supportedMethods
//	 *            String array of supported Signature Methods, like HMAC_SHA1
//	 *            and RSA_SHA1
//	 * @return The SignatureMethod enumeration value, null if invalid
//	 */
	public SignatureMethod validateSignatureMethod(String signatureMethod,
			String[] supportedMethods) {
		logger.info("Validating signature method " + signatureMethod);
		try {
			Collection<String> supportedMethodsCollection = Arrays
					.asList(supportedMethods);
			// loop through the signature methods to get a value, and make sure
			// its supported
			// and same as the method passed
			for (SignatureMethod method : SignatureMethod.values()) {
				if (supportedMethodsCollection.contains(method.name())
						&& method.getValue().equals(signatureMethod))
					return method;
			}
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return null;
	}

//	/**
//	 * This method will return the set of fields that are required, but missing
//	 * for that request. The caller can then provide those field names in the
//	 * response as an error message.
//	 * 
//	 * @param type
//	 * @param suppliedParams
//	 * @see AccessProtectedResourceParameters
//	 * @see GetRequestTokenParameters
//	 * @see OAuthRequestType
//	 * @return A list of missing, required parameters for the given request type
//	 */
//	public Collection<OAuthParameter> validateOAuthFieldsForRequest(
//			OAuthRequestType type, Collection<OAuthParameter> suppliedParams) {
//		logger.info("Validating fields for request" + type + " with params "
//				+ suppliedParams);
//		switch (type) {
//		case ACCESS_PROTECTED_RESOURCES:
//			return validateRequestForProtectedResource(suppliedParams);
//		case GET_ACCESS_TOKEN:
//			return validateRequestForGetAccessToken(suppliedParams);
//		case AUTHORIZE_TOKEN:
//			return validateRequestForAuthorizeToken(suppliedParams);
//		case GET_REQUEST_TOKEN:
//			return validateRequestForGetRequestToken(suppliedParams);
//		case REVOKE_ACCESS_TOKEN:
//			return validateRequestForRevokeAccessToken(suppliedParams);
//		case GENERATE_VERIFIER:
//			return validateRequestForAuthorizeToken(suppliedParams);
//		case RENEW_ACCESS_TOKEN:
//			return validateRequestForProtectedResource(suppliedParams);
//		default: // strictest check
//			return validateRequestForProtectedResource(suppliedParams);
//		}
//	}
//
//	/**
//	 * Method to return a list of parameters that are required but missing from
//	 * the get request token request
//	 * 
//	 * @param params
//	 * @return
//	 */
//	private Collection<OAuthParameter> validateRequestForGetRequestToken(
//			Collection<OAuthParameter> params) {
//		return (Collection<OAuthParameter>) CollectionUtils.subtract(
//				GetRequestTokenParameters.getInstance().getValues(), params);
//	}
//
//	/**
//	 * Method to return a list of parameters that are required but missing from
//	 * the authorize token request
//	 * 
//	 * @param params
//	 * @return
//	 */
//	private Collection<OAuthParameter> validateRequestForAuthorizeToken(
//			Collection<OAuthParameter> params) {
//		return (Collection<OAuthParameter>) CollectionUtils.subtract(
//				AuthorizeTokenParameters.getInstance().getValues(), params);
//
//	}
//
//	/**
//	 * Method to return a list of parameters that are required but missing from
//	 * the get access token request
//	 * 
//	 * @param params
//	 * @return
//	 */
//	private Collection<OAuthParameter> validateRequestForGetAccessToken(
//			Collection<OAuthParameter> params) {
//		return (Collection<OAuthParameter>) CollectionUtils.subtract(
//				GetAccessTokenParameters.getInstance().getValues(), params);
//	}
//
//	/**
//	 * Method to return a list of parameters that are required but missing from
//	 * the revoke access token request
//	 * 
//	 * @param params
//	 * @return
//	 */
//	private Collection<OAuthParameter> validateRequestForRevokeAccessToken(
//			Collection<OAuthParameter> params) {
//		return (Collection<OAuthParameter>) CollectionUtils.subtract(
//				RevokeAccessTokenParameters.getInstance().getValues(), params);
//	}
//
//	/**
//	 * Method to return a list of parameters that are required but missing from
//	 * the access protected resource request
//	 * 
//	 * @param params
//	 * @return
//	 */
//	private Collection<OAuthParameter> validateRequestForProtectedResource(
//			Collection<OAuthParameter> params) {
//		return (Collection<OAuthParameter>) CollectionUtils.subtract(
//				AccessProtectedResourceParameters.getInstance().getValues(),
//				params);
//	}
//
//	public boolean validateModuleStatus(String module) {
//		try {
//			ModuleMetaData md = ModuleMetaDataManager.getInstance().getModuleMetaData(module);
//			ModuleStatus mStatus = md.getStatus();
//			logger.info("Status is "+mStatus.toString());
//			if(mStatus == ModuleStatus.ACTIVE) {
//				logger.info("Both are same");
//				return true;
//			}
//		} catch (NoDataFoundException e) {
//			return false;
//		}
//		return false;
//	}
//
//	public boolean validateContentType(String contentType) {
//		if(StringUtils.isEmpty(contentType)) {
//			return false;
//		} else if(!contentType.equals("application/x-www-form-urlencoded")) {
//			return false;
//		}
//		return true;
//	}
//
//	public boolean validateRealm(String realm, Properties properties) {
//		if(StringUtils.isNotBlank(realm) && !realm.equals(properties.getProperty("realm"))) {
//			return false;
//		}
//		return true;
//	}
//
//	public boolean validateVersion(Map<OAuthParameter, String> oauthParameterMap, Properties properties) {
//		String oauth_version = null;
//		if(oauthParameterMap.containsKey(OAuthParameter.oauth_version)) {
//			oauth_version = oauthParameterMap.get(OAuthParameter.oauth_version);
//			if(!oauth_version.equalsIgnoreCase(properties.getProperty("oauth_version"))) {
//				return false;
//			}
//		} 
//		return true;
//	}
//
//	public boolean validateGetRequestTokenParameters(
//			Map<OAuthParameter, String> oauthParameterMap) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public boolean validateGetAccessTokenParameters(
//			Map<OAuthParameter, String> oauthParameterMap) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public boolean validateAuthorizeTokenParameters(
//			Map<OAuthParameter, String> oauthParameterMap) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public boolean validateAuthorizeTokenMethod(HttpMethod method) {
//		if(method == HttpMethod.GET)
//			return true;
//		else 
//			return false;
//	}

	public boolean validateSignatureMethod(
			Map<OAuthParameter, String> oauthParameterMap, Properties properties) {
		String[] supportedSignatureMethods = StringUtils.split((String) properties
				.get("oauth_signature_methods"), ',');
		SignatureMethod method = validator.validateSignatureMethod(
				oauthParameterMap.get(OAuthParameter.oauth_signature_method),
				supportedSignatureMethods);
		if(method == null) {
			return false;
		}
		return true;
	}

//	public TimestampResult validateTimestamp(
//			Map<OAuthParameter, String> oauthParameterMap, Properties properties) {
//		TimestampResult tsResult = validator.validateTimestamp(
//				oauthParameterMap.get(OAuthParameter.oauth_timestamp),
//				Long.parseLong((String) properties
//						.get("allowable_timestamp_delta")));
//		return tsResult;
//	}
}