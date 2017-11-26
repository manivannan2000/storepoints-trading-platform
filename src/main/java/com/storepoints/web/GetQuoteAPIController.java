package com.storepoints.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetQuoteAPIController {
	
	
	private static double chgClosePrcn_counter_BOLD=0.0;
	
	private static double chgClosePrcn_counter_GBTC=0.0;
	
	private static double chgClosePrcn_counter_MNTA=0.0;
	
	private static int counter_MNTA=0;
	
	
	
	private static Map<Integer, Double> prcnMapMNTA= new HashMap<Integer, Double>();
	
	
	static {
		
		prcnMapMNTA.put(1, -4.96);
		
		prcnMapMNTA.put(2,-4.96); 
		prcnMapMNTA.put(3,-4.96); 
		prcnMapMNTA.put(4,-4.96); 
		prcnMapMNTA.put(5,-4.96); 
		prcnMapMNTA.put(6,-1.42); 
		prcnMapMNTA.put(7,-0.35); 
		prcnMapMNTA.put(8,-3.55); 
		prcnMapMNTA.put(9,-3.55); 
		prcnMapMNTA.put(10,-3.55); 
		prcnMapMNTA.put(11,-3.55); 
		prcnMapMNTA.put(12,-2.95); 
		prcnMapMNTA.put(13,-2.95); 
		prcnMapMNTA.put(14,-3.48); 
		prcnMapMNTA.put(15,-3.48); 
		prcnMapMNTA.put(16,-1.77); 
		prcnMapMNTA.put(17,-2.41); 
		prcnMapMNTA.put(18,-3.19); 
		prcnMapMNTA.put(19,-3.55); 
		prcnMapMNTA.put(20,-3.19); 
		prcnMapMNTA.put(21,-3.19); 
		prcnMapMNTA.put(22,-2.84); 
		prcnMapMNTA.put(23,-2.84); 
		prcnMapMNTA.put(24,-2.84); 
		prcnMapMNTA.put(25,-2.84); 
		prcnMapMNTA.put(26,-2.48); 
		prcnMapMNTA.put(27,-2.55); 
		prcnMapMNTA.put(28,-2.13); 
		prcnMapMNTA.put(29,-2.13); 
		prcnMapMNTA.put(30,-2.13); 
		prcnMapMNTA.put(31,-2.84); 
		prcnMapMNTA.put(32,-2.84); 
		prcnMapMNTA.put(33,-2.84); 
		prcnMapMNTA.put(34,-2.84); 
		prcnMapMNTA.put(35,-2.84); 
		prcnMapMNTA.put(36,-1.77);
		prcnMapMNTA.put(37,-1.77); 
		prcnMapMNTA.put(38,-1.06); 
		prcnMapMNTA.put(39,-1.06); 
		prcnMapMNTA.put(40,-3.48); 
		prcnMapMNTA.put(41,-3.48); 
		prcnMapMNTA.put(42,-3.19); 
		prcnMapMNTA.put(43,-3.19); 
		prcnMapMNTA.put(44,-3.19); 
		prcnMapMNTA.put(45,-3.19); 
		prcnMapMNTA.put(46,-2.84); 
		prcnMapMNTA.put(47,-3.14); 
		prcnMapMNTA.put(48,-2.84); 
		prcnMapMNTA.put(49,-2.84); 
		prcnMapMNTA.put(50,-3.55); 
		prcnMapMNTA.put(51,-3.55); 
		prcnMapMNTA.put(52,-3.55); 
		prcnMapMNTA.put(53,-3.55); 
		prcnMapMNTA.put(54,-3.72); 
		prcnMapMNTA.put(55,-3.72); 
		prcnMapMNTA.put(56,-2.84); 
		prcnMapMNTA.put(57,-2.84); 
		prcnMapMNTA.put(58,-2.84); 
		prcnMapMNTA.put(59,-2.84); 
		prcnMapMNTA.put(60,-3.55); 
		prcnMapMNTA.put(61,-3.55); 
//		prcnMapMNTA.put(1,-4.43); 
//		prcnMapMNTA.put(1,-4.43); 
//		prcnMapMNTA.put(1,-3.55); 
//		prcnMapMNTA.put(1,-3.55); 
//		prcnMapMNTA.put(1,-4.26); 
//		prcnMapMNTA.put(1,-4.26); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-5.03); 
//		prcnMapMNTA.put(1,-5.03); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.79); 
//		prcnMapMNTA.put(1,-4.79); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-6.38); 
//		prcnMapMNTA.put(1,-6.38); 
//		prcnMapMNTA.put(1,-6.38); 
//		prcnMapMNTA.put(1,-6.38); 
//		prcnMapMNTA.put(1,-6.38); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.38); 
//		prcnMapMNTA.put(1,-6.38); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-5.98); 
//		prcnMapMNTA.put(1,-5.98); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-5.86); 
//		prcnMapMNTA.put(1,-5.86); 
//		prcnMapMNTA.put(1,-3.9); 
//		prcnMapMNTA.put(1,-3.9); 
//		prcnMapMNTA.put(1,-3.72); 
//		prcnMapMNTA.put(1,-3.72); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.89); 
//		prcnMapMNTA.put(1,-5.89); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-5.14); 
//		prcnMapMNTA.put(1,-5.5); 
//		prcnMapMNTA.put(1,-5.5); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.53); 
//		prcnMapMNTA.put(1,-5.53); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.5); 
//		prcnMapMNTA.put(1,-5.5); 
//		prcnMapMNTA.put(1,-5.46); 
//		prcnMapMNTA.put(1,-5.46); 
//		prcnMapMNTA.put(1,-5.46); 
//		prcnMapMNTA.put(1,-5.46); 
//		prcnMapMNTA.put(1,-5.46); 
//		prcnMapMNTA.put(1,-5.46); 
//		prcnMapMNTA.put(1,-5.5); 
//		prcnMapMNTA.put(1,-5.5); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-5.85 );
//		prcnMapMNTA.put(1,-5.67 );
//		prcnMapMNTA.put(1,-5.67 );
//		prcnMapMNTA.put(1,-5.67 );
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-5.79); 
//		prcnMapMNTA.put(1,-5.79); 
//		prcnMapMNTA.put(1,-5.79); 
//		prcnMapMNTA.put(1,-5.79); 
//		prcnMapMNTA.put(1,-5.79); 
//		prcnMapMNTA.put(1,-5.79); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-4.96); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.32); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-5.67); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-6.03); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-5.85); 
//		prcnMapMNTA.put(1,-6.03);  
//		prcnMapMNTA.put(1,-6.03);  
//		prcnMapMNTA.put(1,-5.67);  
//		prcnMapMNTA.put(1,-5.85);  
//		prcnMapMNTA.put(1,-5.85);  
//		prcnMapMNTA.put(1,-5.85);  
//		prcnMapMNTA.put(1,-5.85);  
//		prcnMapMNTA.put(1,-5.85);  
//		prcnMapMNTA.put(1,-6.21);  
//		prcnMapMNTA.put(1,-6.21);  
//		prcnMapMNTA.put(1,-6.74);  
//		prcnMapMNTA.put(1,-6.74);  
//		prcnMapMNTA.put(1,-6.74);  
//		prcnMapMNTA.put(1,-6.74);  
//		prcnMapMNTA.put(1,-6.74);  
//		prcnMapMNTA.put(1,-6.74 );  
//		prcnMapMNTA.put(1,-6.74 ); 
//		prcnMapMNTA.put(1,-6.74 ); 
//		prcnMapMNTA.put(1,-7.27 );			
//		prcnMapMNTA.put(1,-7.27 );			
//		prcnMapMNTA.put(1,-7.27 );			
//		prcnMapMNTA.put(1,-7.27 );			
//		prcnMapMNTA.put(1,-7.98 );			
//		prcnMapMNTA.put(1,-7.98 );			
//		prcnMapMNTA.put(1,-7.62 );			
//		prcnMapMNTA.put(1,-7.62 );			
//		prcnMapMNTA.put(1,-7.45 			);
//		prcnMapMNTA.put(1,-7.45 			);
//		prcnMapMNTA.put(1,-7.45 			);
//		prcnMapMNTA.put(1,-7.45 			);
//		prcnMapMNTA.put(1,-7.45 			);
//		prcnMapMNTA.put(1,-7.45 			);
//		prcnMapMNTA.put(1,-7.27 			);
//		prcnMapMNTA.put(1,-7.27 			);
//		prcnMapMNTA.put(1,-7.8 			);
//		prcnMapMNTA.put(1,-7.8 			);
//		prcnMapMNTA.put(1,-7.8 			);
//		prcnMapMNTA.put(1,-7.8 			);
//		prcnMapMNTA.put(1,-7.98 			);
//		prcnMapMNTA.put(1,-7.98 			);
//		prcnMapMNTA.put(1,-7.98 			);
//		prcnMapMNTA.put(1,-7.98 			);
//		prcnMapMNTA.put(1,-7.45 			);
//		prcnMapMNTA.put(1,-7.45 			);
//		prcnMapMNTA.put(1,-7.16 			);
//		prcnMapMNTA.put(1,-7.16 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-6.74 			);
//		prcnMapMNTA.put(1,-6.74 			);
//		prcnMapMNTA.put(1,-7.74 			);
//		prcnMapMNTA.put(1,-7.74 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-7.27 			);
//		prcnMapMNTA.put(1,-7.27 			);
//		prcnMapMNTA.put(1,-7.27 			);
//		prcnMapMNTA.put(1,-7.27 			);
//		prcnMapMNTA.put(1,-6.74 			);
//		prcnMapMNTA.put(1,-6.74 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.05 			);
//		prcnMapMNTA.put(1,-7.05 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-7.09 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.91 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.56 			);
//		prcnMapMNTA.put(1,-6.56 			);
//		prcnMapMNTA.put(1,-6.74 			);
//		prcnMapMNTA.put(1,-6.74 			);
//		prcnMapMNTA.put(1,-6.74 			);
//		prcnMapMNTA.put(1,-6.74 			);
//		prcnMapMNTA.put(1,-6.28 			);
//		prcnMapMNTA.put(1,-6.28 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.41 			);
//		prcnMapMNTA.put(1,-6.41 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.38 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.21 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-6.03 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.5 			);
//		prcnMapMNTA.put(1,-5.5 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.5 			);
//		prcnMapMNTA.put(1,-5.5 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.32 			);
//		prcnMapMNTA.put(1,-5.4 			);
//		prcnMapMNTA.put(1,-5.4 			);
//		prcnMapMNTA.put(1,-5.4 			);
//		prcnMapMNTA.put(1,-5.32 			);
//		prcnMapMNTA.put(1,-5.32 			);
//		prcnMapMNTA.put(1,-5.5 			);
//		prcnMapMNTA.put(1,-5.5 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.5 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.77 			);
//		prcnMapMNTA.put(1,-5.77 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.75 			);
//		prcnMapMNTA.put(1,-5.75 			);
//		prcnMapMNTA.put(1,-5.85 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.5 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 			);
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.67 );			
//		prcnMapMNTA.put(1,-5.85 );			
//		prcnMapMNTA.put(1,-5.85 );			
//		prcnMapMNTA.put(1,-5.85 );			
//		prcnMapMNTA.put(1,-5.85 );			
//		prcnMapMNTA.put(1,-6.74 );			
//		prcnMapMNTA.put(1,-6.74 );			
//		prcnMapMNTA.put(1,-6.91 );			
//		prcnMapMNTA.put(1,-6.91 );			
//		prcnMapMNTA.put(1,-6.74 );			
//		prcnMapMNTA.put(1,-6.74 );			
//		prcnMapMNTA.put(1,-6.81 );			
//		prcnMapMNTA.put(1,-6.81 );			
//		prcnMapMNTA.put(1,-6.74 );			
//		prcnMapMNTA.put(1,-6.74 );			
//		prcnMapMNTA.put(1,-6.97 );			
//		prcnMapMNTA.put(1,-6.97 );			
//		prcnMapMNTA.put(1,-6.87 );			
//		prcnMapMNTA.put(1,-6.87 );			
//		prcnMapMNTA.put(1,-7.09 );			
//		prcnMapMNTA.put(1,-7.09 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.45 );			
//		prcnMapMNTA.put(1,-7.45 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.45 );			
//		prcnMapMNTA.put(1,-7.45 );			
//		prcnMapMNTA.put(1,-7.45 );			
//		prcnMapMNTA.put(1,-7.45 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.8 );			
//		prcnMapMNTA.put(1,-7.98); 			
//		prcnMapMNTA.put(1,-7.98); 			
//		prcnMapMNTA.put(1,-7.98); 			
//		prcnMapMNTA.put(1,-7.98); 			
//		prcnMapMNTA.put(1,-7.81); 			
		prcnMapMNTA.put(398,-7.81); 			

		
	}
	
	
	
	
	private static double bid_counter=632.5;
	private static double ask_counter=633.5;
	
	private static int counter=0;
	
	
	private static String getBOLD_QuoteResponse(){
		return "<QuoteResponse>"
				+"<QuoteData>"
				+"  <all>"
			      +"<adjNonAdjFlag>false</adjNonAdjFlag>"
			      +"<annualDividend>0.0</annualDividend>"
			      +"<ask>"+ask_counter+"</ask>"
			      +"<askExchange>V</askExchange>"
			      +"<askSize>1</askSize>"
			      +"<askTime>08:22:29 EDT 09-18-2017</askTime>"
			      +"<bid>"+bid_counter+"</bid>"
			      +"<bidExchange>V</bidExchange>"
			      +"<bidSize>1</bidSize>"
			      +"<bidTime>08:22:29 EDT 09-18-2017</bidTime>"
			      +"<chgClose>5.0</chgClose>"
			      +"<chgClosePrcn>"+chgClosePrcn_counter_BOLD+"</chgClosePrcn>"
			      +"<companyName>Audentes Therapeutics Inc</companyName>"
			      +"<daysToExpiration>0</daysToExpiration>"
			      +"<dirLast>D</dirLast>"
			      +"<dividend>0.0</dividend>"
			      +"<eps>-2.0</eps>"
			      +"<estEarnings>0.0</estEarnings>"
			      +"<exDivDate></exDivDate>"
			      +"<exchgLastTrade>NASDAQ BB Foreign</exchgLastTrade>"
			      +"<fsi> </fsi>"
			      +"<high>0.0</high>"
			      +"<high52>33.43</high52>"
			      +"<highAsk>24.98</highAsk>"
			      +"<highBid>33.43</highBid>"
			      +"<lastTrade>33.43</lastTrade>"
			      +"<low>0.0</low>"
			      +"<low52>13.13</low52>"
			      +"<lowAsk>25.52</lowAsk>"
			      +"<lowBid>24.94</lowBid>"
			      +"<numTrades>0</numTrades>"
			      +"<open>0.0</open>"
			      +"<openInterest>0</openInterest>"
			      +"<optionStyle></optionStyle>"
			      +"<optionUnderlier></optionUnderlier>"
			      +"<prevClose>24.98</prevClose>"
			      +"<prevDayVolume>161301</prevDayVolume>"
			      +"<primaryExchange>u </primaryExchange>"
			      +"<symbolDesc>Audentes Therapeutics Inc</symbolDesc>"
			      +"<todayClose>0.0</todayClose>"
			      +"<totalVolume>0</totalVolume>"
			      +"<upc>0</upc>"
			      +"<volume10Day>122579</volume10Day>"
			    +"</all>"
			    +"<dateTime>16:26:23 EDT 09-18-2017</dateTime>"
			    +"<product>"
			    +"  <symbol>BOLD</symbol>"
			    +"  <type>EQ</type>"
			    +"  <exchange>u </exchange>"
			    +"</product>"
			  +"</QuoteData>"
			  + "</QuoteResponse>";
	}
	
	
	private static String getGBTC_QuoteResponse(){
		return "<QuoteResponse>"
				+"<QuoteData>"
				+"  <all>"
			      +"<adjNonAdjFlag>false</adjNonAdjFlag>"
			      +"<annualDividend>0.0</annualDividend>"
			      +"<ask>"+ask_counter+"</ask>"
			      +"<askExchange>V</askExchange>"
			      +"<askSize>1</askSize>"
			      +"<askTime>08:22:29 EDT 09-18-2017</askTime>"
			      +"<bid>"+bid_counter+"</bid>"
			      +"<bidExchange>V</bidExchange>"
			      +"<bidSize>1</bidSize>"
			      +"<bidTime>08:22:29 EDT 09-18-2017</bidTime>"
			      +"<chgClose>5.0</chgClose>"
			      +"<chgClosePrcn>"+chgClosePrcn_counter_GBTC+"</chgClosePrcn>"
			      +"<companyName>BITCOIN INVT TR</companyName>"
			      +"<daysToExpiration>0</daysToExpiration>"
			      +"<dirLast>D</dirLast>"
			      +"<dividend>0.0</dividend>"
			      +"<eps>-2.0</eps>"
			      +"<estEarnings>0.0</estEarnings>"
			      +"<exDivDate></exDivDate>"
			      +"<exchgLastTrade>NASDAQ BB Foreign</exchgLastTrade>"
			      +"<fsi> </fsi>"
			      +"<high>0.0</high>"
			      +"<high52>1064.95</high52>"
			      +"<highAsk>700.0</highAsk>"
			      +"<highBid>630.5</highBid>"
			      +"<lastTrade>630.01</lastTrade>"
			      +"<low>0.0</low>"
			      +"<low52>86.0</low52>"
			      +"<lowAsk>630.01</lowAsk>"
			      +"<lowBid>525.0</lowBid>"
			      +"<numTrades>0</numTrades>"
			      +"<open>0.0</open>"
			      +"<openInterest>0</openInterest>"
			      +"<optionStyle></optionStyle>"
			      +"<optionUnderlier></optionUnderlier>"
			      +"<prevClose>630.005</prevClose>"
			      +"<prevDayVolume>161301</prevDayVolume>"
			      +"<primaryExchange>u </primaryExchange>"
			      +"<symbolDesc>BITCOIN INVT TR</symbolDesc>"
			      +"<todayClose>0.0</todayClose>"
			      +"<totalVolume>0</totalVolume>"
			      +"<upc>0</upc>"
			      +"<volume10Day>122579</volume10Day>"
			    +"</all>"
			    +"<dateTime>16:26:23 EDT 09-18-2017</dateTime>"
			    +"<product>"
			    +"  <symbol>GBTC</symbol>"
			    +"  <type>EQ</type>"
			    +"  <exchange>u </exchange>"
			    +"</product>"
			  +"</QuoteData>"
			  + "</QuoteResponse>";
	}	
	
	
	private static String getMNTA_QuoteResponse(){
		return "<QuoteResponse>"
				+"<QuoteData>"
				+"  <all>"
			      +"<adjNonAdjFlag>false</adjNonAdjFlag>"
			      +"<annualDividend>0.0</annualDividend>"
			      +"<ask>"+ask_counter+"</ask>"
			      +"<askExchange>V</askExchange>"
			      +"<askSize>1</askSize>"
			      +"<askTime>08:22:29 EDT 09-18-2017</askTime>"
			      +"<bid>"+bid_counter+"</bid>"
			      +"<bidExchange>V</bidExchange>"
			      +"<bidSize>1</bidSize>"
			      +"<bidTime>08:22:29 EDT 09-18-2017</bidTime>"
			      +"<chgClose>5.0</chgClose>"
			      +"<chgClosePrcn>"+chgClosePrcn_counter_MNTA+"</chgClosePrcn>"
			      +"<companyName>MNTA</companyName>"
			      +"<daysToExpiration>0</daysToExpiration>"
			      +"<dirLast>D</dirLast>"
			      +"<dividend>0.0</dividend>"
			      +"<eps>-2.0</eps>"
			      +"<estEarnings>0.0</estEarnings>"
			      +"<exDivDate></exDivDate>"
			      +"<exchgLastTrade>NASDAQ BB Foreign</exchgLastTrade>"
			      +"<fsi> </fsi>"
			      +"<high>0.0</high>"
			      +"<high52>13.65</high52>"
			      +"<highAsk>13.65</highAsk>"
			      +"<highBid>13.65</highBid>"
			      +"<lastTrade>13.65</lastTrade>"
			      +"<low>0.0</low>"
			      +"<low52>13.65</low52>"
			      +"<lowAsk>13.65</lowAsk>"
			      +"<lowBid>13.65</lowBid>"
			      +"<numTrades>0</numTrades>"
			      +"<open>0.0</open>"
			      +"<openInterest>0</openInterest>"
			      +"<optionStyle></optionStyle>"
			      +"<optionUnderlier></optionUnderlier>"
			      +"<prevClose>630.005</prevClose>"
			      +"<prevDayVolume>161301</prevDayVolume>"
			      +"<primaryExchange>u </primaryExchange>"
			      +"<symbolDesc>MNTA</symbolDesc>"
			      +"<todayClose>0.0</todayClose>"
			      +"<totalVolume>0</totalVolume>"
			      +"<upc>0</upc>"
			      +"<volume10Day>122579</volume10Day>"
			    +"</all>"
			    +"<dateTime>16:26:23 EDT 09-18-2017</dateTime>"
			    +"<product>"
			    +"  <symbol>MNTA</symbol>"
			    +"  <type>EQ</type>"
			    +"  <exchange>u </exchange>"
			    +"</product>"
			  +"</QuoteData>"
			  + "</QuoteResponse>";
	}		
	
	
    @RequestMapping("/market/rest/quote/GBTC")
    public String accountsList() {
    	
    	// Negative trend
//    	if(chgClosePrcn_counter<=15.0d)
//    		chgClosePrcn_counter=chgClosePrcn_counter-1.0d;
//    	else {
//    		chgClosePrcn_counter=Math.abs(chgClosePrcn_counter)+1.0d;
//    	}
//    	
//    	if(chgClosePrcn_counter<= -6.0){
//    		bid_counter= bid_counter-5.0;
//    		ask_counter= ask_counter-5.0;
//    	}
    	
    	// Positive trend    	
//    	chgClosePrcn_counter_GBTC=chgClosePrcn_counter_GBTC+1.0D;
//    	
//    	if(chgClosePrcn_counter_GBTC>= 6.0D){
//    		bid_counter= bid_counter+5.0;
//    		ask_counter= ask_counter+5.0;
//    	}
    	
    	//Positive and Negative trend both
    	if(++counter<10){
    		if(counter%2==0)
    			chgClosePrcn_counter_GBTC=chgClosePrcn_counter_GBTC-2.75D;
    		else
    			chgClosePrcn_counter_GBTC=chgClosePrcn_counter_GBTC+1.0D;
    	}
    	else{
    		if(counter%2==0)
    			chgClosePrcn_counter_GBTC=chgClosePrcn_counter_GBTC+1.0D;
    		else
    			chgClosePrcn_counter_GBTC=chgClosePrcn_counter_GBTC-2.75D;
    	}
    	
    	if(chgClosePrcn_counter_GBTC>= 6.0D && chgClosePrcn_counter_GBTC<=10.0d && counter<30){
    		bid_counter= bid_counter+5.0D;
    		ask_counter= ask_counter+5.0D;
    	} else {
    		bid_counter= bid_counter-5.0D;
    		ask_counter= ask_counter-5.0D;
    		
    	}
    	
    	
    	
    	
    	return getGBTC_QuoteResponse();
    }
    
    
    @RequestMapping("/market/rest/quote/BOLD")
    public String getQuoteResponseForBOLD() {
    	
    	
    	// Positive trend    	
    	chgClosePrcn_counter_BOLD=chgClosePrcn_counter_BOLD+1.0D;
    	
    	if(chgClosePrcn_counter_BOLD>= 6.0D){
    		bid_counter= bid_counter+5.0;
    		ask_counter= ask_counter+5.0;
    	}
    	
    	
    	return getBOLD_QuoteResponse();
    }
    
    @RequestMapping("/market/rest/quote/MNTA")
    public String getQuoteResponseForMNTA() {
    	
    	counter_MNTA=counter_MNTA+1;
    	
    	
    	// Positive trend    	
    	chgClosePrcn_counter_MNTA=prcnMapMNTA.get(counter_MNTA);
    	
    	return getMNTA_QuoteResponse();
    }    
    

}
