package com.storepoints.ds;

import java.util.Set;
import java.util.TreeSet;

import com.storepoints.etrade.model.MarketResearchQuote;

public class MarketResearchTree {
	
	private TreeSet<MarketResearchQuote> mrTreeSet=new TreeSet<MarketResearchQuote>();  

	private int maxSize; // size of Tree

	public MarketResearchTree(){
		maxSize=200;
	}
	
	public void addToTree(MarketResearchQuote marketResearchQuote){
		if(mrTreeSet.size()>=maxSize){
			return;
		}
		mrTreeSet.add(marketResearchQuote);
	}
	
	public MarketResearchQuote getFirst(){
		return mrTreeSet.first();
	}
	
	public MarketResearchQuote getLast(){
		return mrTreeSet.last();
	}
}
