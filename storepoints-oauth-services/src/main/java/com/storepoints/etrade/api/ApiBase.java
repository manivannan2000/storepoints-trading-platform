package com.storepoints.etrade.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.Properties;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ApiBase {
	
	private static final Log log = LogFactory.getLog(ApiBase.class);
	
	protected static final String HMAC_SHA1 = "HmacSHA1";

	protected static final String ENC = "UTF-8";

	protected static Base64 base64 = new Base64();
	
    protected static Properties config= new Properties();
    
    protected static int WAIT_COUNTER=10;
    
//    protected static double TREND_FLAT_VALUE=5.0D;
    
//    protected static long  WILD_CARD_QUOTE_SLEEP_TIME_INIT=180000;

//    protected static long  WILD_CARD_QUOTE_SLEEP_TIME_ACTIVE=30000;
    
    
	protected abstract  String getTokenSecret();
    
    static{
//    	String filename = "config.properties";
    	String filename = System.getProperty("test.harness")!=null?"config_test_harness.properties":"config_main.properties";
    	
    	InputStream input = ApiBase.class.getClassLoader().getResourceAsStream(filename);
    	
 		if(input==null){
 			log.info("Sorry, unable to find property file: " + filename);
 		}
    	
    	try {
			config.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    
    public static Long getWILD_CARD_QUOTE_SLEEP_TIME_INIT(){
    	return Long.parseLong(config.getProperty("WILD_CARD_QUOTE_SLEEP_TIME_INIT"));
    }
    
    
    public static Long getWILD_CARD_QUOTE_SLEEP_TIME_ACTIVE(){
    	return Long.parseLong(config.getProperty("WILD_CARD_QUOTE_SLEEP_TIME_ACTIVE"));
    }
    
    
    public static String getConsumerKey(){
    	return config.getProperty("consumer.key");
    }
    
    public static String getSecret(){
    	return config.getProperty("secret");
    }
    
    public static String getServerHostConfig(){
    	return config.getProperty("server.host");
    }
    
    public static String getServerHostProtocol(){
    	return config.getProperty("server.host.protocol");
    }
    
    
    public static String getOAuthServerHostConfig(){
    	return config.getProperty("oauth.server.host");
    }
    
    public static String getOAuthServerHostProtocol(){
    	return config.getProperty("oauth.server.host.protocol");
    }
    
    
    public static double getDoubleDecimalFormat(double input){
		DecimalFormat df2 = new DecimalFormat(".##");
		return Double.parseDouble(df2.format(input));
    }
    
    public static boolean isOrderClosingRequiredConfig(){
    	return Boolean.parseBoolean(config.getProperty("order.closing.required"));
    }
    
    
    public static Long getOrderClosingMinutes(){
    	return Long.valueOf(config.getProperty("order.closing.minutes"));
    }
    
    public static Double getOrderClosingFlatRate(){
    	return Double.valueOf(config.getProperty("order.closing.flat.rate"));
    }
    
    public static int getOrderDailyLimit(){
    	return Integer.parseInt(config.getProperty("order.daily.limit"));
    }
    
    
    
    public static int getRegularSessionCloseHour(){
    	return Integer.parseInt(config.getProperty("market.regular.session.close.hour"));
    }
    
    
    public static boolean isRegularSessionCloseTime(){
        LocalTime thisTime=LocalTime.now();
        
        int regularSessionCloseHour=getRegularSessionCloseHour();

		return (thisTime.getHour()==12 && thisTime.getMinute()>=45 && thisTime.getHour()<regularSessionCloseHour);
    }
    
    public static boolean isAfterHoursSessionCloseTime(){
        LocalTime thisTime=LocalTime.now();
        return thisTime.getHour()==17;
    }
    
    
    public static boolean isLastMinutesBeforeAfterHoursSessionCloseTime(){
        LocalTime thisTime=LocalTime.now();
        
        return (thisTime.getHour()==16 && thisTime.getMinute()>=55 && thisTime.getHour()<17);
    }
    
    
    
    
    public static boolean isRegularSessionTime(){
        LocalTime thisTime=LocalTime.now();
        
        int regularSessionCloseHour=getRegularSessionCloseHour();

		return (thisTime.getHour()==5 && thisTime.getMinute()>=10 && thisTime.getHour()<regularSessionCloseHour) ||(thisTime.getHour()>=6 && thisTime.getHour()<regularSessionCloseHour);

    }
    
    
    public static int getFavorableOrderPlaceMaxHour(){
    	return Integer.parseInt(config.getProperty("market.extended.session.favorable.order.place.max.hour"));
    }
    
    
    
    /**
     * 
     * @param url
     *            the url for "request_token" URLEncoded.
     * @param params
     *            parameters string, URLEncoded.
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    protected  String getSignature(String method, String url, String params, boolean requestToken)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {
    	
        /**
         * base has three parts, they are connected by "&": 1) protocol 2) URL
         * (need to be URLEncoded) 3) Parameter List (need to be URLEncoded).
         */
        StringBuilder base = new StringBuilder();
        base.append(method+"&");
        base.append(url);
        base.append("&");
        base.append(params);
    	String keyBytesStr=null;

        
        if(requestToken){
            keyBytesStr=getSecret() + "&";
        }else {
            String secret=new String((getSecret() + "&").getBytes(ENC), "US-ASCII");
            String tokenSecret=new String((getTokenSecret()).getBytes(ENC), "US-ASCII");
            keyBytesStr=secret+tokenSecret;
        }
        
        // yea, don't ask me why, it is needed to append a "&" to the end of
        // secret key.
        byte[] keyBytes = (keyBytesStr).getBytes(ENC);

        SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);

        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(key);

        // encode it, base64 it, change it to string and return.
        return new String(base64.encode(mac.doFinal(base.toString().getBytes(
                ENC))), ENC).trim();
    }

}

