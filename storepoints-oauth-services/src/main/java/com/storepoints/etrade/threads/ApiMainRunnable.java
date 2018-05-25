package com.storepoints.etrade.threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.storepoints.etrade.api.AccountsList;
import com.storepoints.etrade.api.ApiBase;
import com.storepoints.etrade.api.GetOrderList;
import com.storepoints.etrade.dto.GetQuoteParams;
import com.storepoints.etrade.research.MarketResearch;

public class ApiMainRunnable implements Runnable {
	
	private static final Log log = LogFactory.getLog(ApiMainRunnable.class);
	
	private  String accessToken;
	private  String accessTokenSecret;
	
	private String inputFileName;
	
    private static Map<String,String> threadMap= new ConcurrentHashMap<String,String>();
    
    private boolean taskComplete;
	
	
	public ApiMainRunnable(String accessToken, String accessTokenSecret, String inputFileName){
		this.accessToken=accessToken;
		this.accessTokenSecret=accessTokenSecret;
		
		this.inputFileName= inputFileName;
	}
	

	@Override
	public void run() {
		
        ExecutorService executorService = Executors.newFixedThreadPool(500);
        
        
        while(!isTaskComplete()) {
        	
            try{
            	
                AccountsList accountsList=new AccountsList(accessToken, accessTokenSecret);
                
                accountsList.accountsList();
    		
    	        File inputFile = new File(inputFileName);
    	        
    	        if(inputFile.exists()){
    	            try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
    	                for(String line; (line = br.readLine()) != null; ) {
    	                	
    	                	if(threadMap.containsKey(line)){
    	                		log.info("Thread  already started for input line:"+threadMap.get(line));
    	                	} else{
    	                		
    	                		if(line.startsWith("O")){
    	                			
        	                		String lineData[]=line.split(",");
        	                		
        	                		String underlier=lineData[1];
        	                		
        	                		LocalDate currentDate = LocalDate.now();
        	                		
//        	                		for(int i=0;i<5;i++){
        	                			
        	                			GetOptionChainsRunnable optionChainsRunnable=	new GetOptionChainsRunnable( accessToken,  accessTokenSecret,  "CALLPUT", currentDate.getMonthValue(), currentDate.getYear(),  underlier);
        	                			executorService.execute(optionChainsRunnable);
//        	                		}
    	                			
    	                		}else if(line.startsWith("#") || line.startsWith("*") || line.startsWith(getInputDate())){
        	                	
        	                		boolean marketResearch= line.startsWith("#");
        	                		String originalLine=line;
        	                		if(marketResearch)
        	                			line=line.substring(1);
        	                		
        	                		String lineData[]=line.split(",");
        	                		
        	                		String quote=lineData[1];
        	                		int quantity=Integer.parseInt(lineData[2]);
        	                		
        	                		double chgClosePrcnLimit=Double.parseDouble(lineData[3]);
        	                		String earnCalTime=lineData[4];
        	                		
        	                		LocalTime thisTime=LocalTime.now();
        	                		boolean wildCardQuote=false;
        	                		if("*".equals(lineData[0]) || (thisTime.getHour()>=4 && thisTime.getHour()<13 && earnCalTime.equals("AM")) ||
        	                		   (thisTime.getHour()>=13 && thisTime.getMinute()>=5 && earnCalTime.equals("PM"))){
        	                			wildCardQuote="*".equals(lineData[0]) ?true:false;
        	                			
        	                			
        	                			GetQuoteParams quoteParams= new GetQuoteParams();
        	                			quoteParams.setAccessToken(accessToken);
        	                			quoteParams.setAccessTokenSecret(accessTokenSecret);
        	                			quoteParams.setAccountId(accountsList.getAccountId());
        	                			quoteParams.setQuotesList(quote);
        	                			quoteParams.setDetailFlag("ALL");
        	                			quoteParams.setQuantity(quantity);
        	                			quoteParams.setChgClosePrcnLimit(chgClosePrcnLimit);
        	                			quoteParams.setWildCardQuote(wildCardQuote);
        	                			quoteParams.setMarketResearch(marketResearch);
        	                			
        	                    		GetQuoteRunnable getQuoteRunnable = new GetQuoteRunnable(quoteParams);
        	
        	                    		if(wildCardQuote)
        	                    			Thread.sleep(3000);
        	                			
        	                    		executorService.execute(getQuoteRunnable);
        	                			
        	                    		if(wildCardQuote)
        	                    			Thread.sleep(3000);

                	                	threadMap.put(marketResearch?originalLine:line,line);
        	                		}
        	                	} 
    	                	}
    	                }
    	            }
    	        }
    	        
    	        
				Thread.sleep(60000);

				// @ TODO 	Commented out due to the reason Market orders are applied as try out in GetQuote::placeEquityOrder. Revisit and put back Limit orders and prepare for Order edits using list orders.
//				GetOrderList getOrderList= new GetOrderList(accessToken, accessTokenSecret, accountsList.getAccountId());
//				
//				getOrderList.orderList();
				
				if(ApiBase.isLastMinutesBeforeAfterHoursSessionCloseTime()){
					MarketResearch.getInstance().logTrendingQuotes();
				}
					

            }catch(ClientProtocolException excp){
            	excp.printStackTrace();
            }catch(InvalidKeyException excp){
            	excp.printStackTrace();
            }catch(URISyntaxException excp){
            	excp.printStackTrace();
            } catch(IOException excp){
            	excp.printStackTrace();
            } catch(NoSuchAlgorithmException excp){
            	excp.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally{
				LocalTime thisTime=LocalTime.now();
				if(thisTime.getHour()==17){
					setTaskComplete(true);
				}
			}
        }
		
		
        executorService.shutdown();		

	}
	
	
    private static String getInputDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		return dateFormat.format(date); 
    }


	public boolean isTaskComplete() {
		return taskComplete;
	}


	public void setTaskComplete(boolean taskComplete) {
		this.taskComplete = taskComplete;
	}	
	

}
