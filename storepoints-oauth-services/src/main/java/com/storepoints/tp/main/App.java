package com.storepoints.tp.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.storepoints.etrade.api.ApiBase;
import com.storepoints.etrade.api.OAuthAccessToken;
import com.storepoints.etrade.api.OAuthRequestToken;
import com.storepoints.etrade.threads.ApiMainRunnable;

public class App {
	
	private static final Log log = LogFactory.getLog(App.class);

    public static void main(String[] args) throws ClientProtocolException,
    IOException, URISyntaxException, InvalidKeyException,
    NoSuchAlgorithmException {
    	
        ExecutorService executorService = Executors.newSingleThreadExecutor();
    	
    	OAuthRequestToken oAuthRequestToken= new OAuthRequestToken();
    	
    	oAuthRequestToken.requestToken();
		
    	String requestToken=oAuthRequestToken.getRequestToken();
    	String requestTokenSecret=oAuthRequestToken.getRequestTokenSecret();
        
        log.info("Auth URL: https://us.etrade.com/e/t/etws/authorize?key="+ApiBase.getConsumerKey()+"&token="+requestToken);
        
//        Scanner sc = new Scanner(System.in);
//        
//        log.info("Enter verifier:");
//        String verifier = sc.nextLine();
        
        
        String verifier=null;
        
        File verifierFile = new File(args[1]);
        
        while(true){
        	
            if(verifierFile.exists()){
                try(BufferedReader br = new BufferedReader(new FileReader(verifierFile))) {
                    for(String line; (line = br.readLine()) != null; ) {
                    	verifier=line;
                    }
                }
                if(verifier!=null){
                	verifierFile.delete();
                	break;
                }
            } else{
            	log.info("Waiting on verifier file....");
            	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        
                
        
        
        OAuthAccessToken oAuthAccessToken = new OAuthAccessToken( requestToken,requestTokenSecret,  verifier); 
        
        oAuthAccessToken.accessToken();
        
      String accessToken = oAuthAccessToken.getAccessToken();
      String accessTokenSecret=oAuthAccessToken.getAccessTokenSecret();
      
      ApiMainRunnable apiMainRunnable = new ApiMainRunnable(accessToken, accessTokenSecret, args[0]);
        
      executorService.execute(apiMainRunnable);
      
      executorService.shutdown();

    }
    
    private static String getInputDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		return dateFormat.format(date); 
    }	
    	

}
