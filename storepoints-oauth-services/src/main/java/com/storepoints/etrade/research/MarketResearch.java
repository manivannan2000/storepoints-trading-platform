package com.storepoints.etrade.research;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.storepoints.ds.MarketResearchTree;
import com.storepoints.etrade.model.MarketResearchQuote;

public class MarketResearch {
	
	public final static MarketResearch INSTANCE= new MarketResearch();

	private static final Log log = LogFactory.getLog(MarketResearch.class);
	
	
    private static Map<String,MarketResearchTree> marketResearchMap= new ConcurrentHashMap<String, MarketResearchTree>();
    
	private MarketResearch() {
        // Exists only to defeat instantiation.
     }
	
	public static MarketResearch getInstance(){
		return INSTANCE;
	}
	
	
	public void addQuote(String quote, double chgClosePrcn){
		MarketResearchQuote marketResearchQuote= new MarketResearchQuote(quote,  chgClosePrcn);
		
		MarketResearchTree msTree=null;
		if(marketResearchMap.containsKey(quote)){
			msTree=marketResearchMap.get(quote);
		}else {
			msTree= new MarketResearchTree();
		}
		msTree.addToTree(marketResearchQuote);
		marketResearchMap.put(quote, msTree);
	}
	
	public void logTrendingQuotes(){
		TreeSet<MarketResearchQuote> mrTrendingTreeSet=new TreeSet<MarketResearchQuote>();
		
		marketResearchMap.entrySet().stream().forEach((entry) -> {
		    String quote = entry.getKey();
		    MarketResearchTree mrTree = entry.getValue();
		    MarketResearchQuote mrQuoteFirst=new MarketResearchQuote(quote,mrTree.getFirst().getChgClosePrcn());
		    mrTrendingTreeSet.add(mrQuoteFirst);
		    MarketResearchQuote mrQuoteLast=new MarketResearchQuote(quote,mrTree.getLast().getChgClosePrcn());
		    mrTrendingTreeSet.add(mrQuoteLast);
		});

		StringBuilder sb= new StringBuilder();
		
		sb.append("Quote\tPercent trended\n");
		
		for(MarketResearchQuote marketResearchQuote:mrTrendingTreeSet ){
			
			sb.append(marketResearchQuote.getQuote()+"\t"+marketResearchQuote.getChgClosePrcn()+"\n");
			
		}
		
		log.info(sb.toString());
		
	}

}
