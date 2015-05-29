package coreRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Runner {
	
	public static SupportFunctions func =new SupportFunctions();
	
	public static void main(String[] args) throws IOException  {
		// TODO Auto-generated method stub
		
		
		List<String> input = func.loadInput(func.getfilePath("inputsample.txt","Input"));
		List<String> positive = func.loadInput(func.getfilePath("positive.txt","Input"));
		List<String> negative = func.loadInput(func.getfilePath("negative.txt","Input"));
		List<String> frustration = func.loadInput(func.getfilePath("frustration.txt","Input"));
		List<String> happiness = func.loadInput(func.getfilePath("happiness.txt","Input"));
		List<String> negiMulti = func.loadInput(func.getfilePath("negativeMuliplier.txt","Input"));
		List<String> sentence = new ArrayList<String>();
		List<String> subject = new ArrayList<String>();
		List<String> words = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		int run = 0 ;
		int nextMultiplier = 1;
		boolean negiWord = false;
		
		
	    
	    for (String str: input){
	    	int rate = 0;
	    	
	    	sentence = func.splitByToken(str,".");
	    	
	    	
	    	for (String sentStr: sentence){
	    	
	    		subject = func.splitByToken(sentStr,",!");
	    		
	    		List<String> sentRate = new ArrayList<String>();
	    		for (String subStr: subject){
	    			String subRate = "";
	    			System.out.println(subStr);
	    			words = func.splitByToken(subStr," ");
	    			
	    			for(String wrdStr: words){
	    				
	    				if (run==2){
    						negiWord =false;
    						nextMultiplier = 1;
    					}
	    				
    					if(func.getRating(negiMulti,wrdStr) & !negiWord){
	    					
    						negiWord = true;
    						run = 0;
	    				}
    					
    					if (negiWord && run ==0 ){
    						nextMultiplier = 0;
    					}
    					if (negiWord && run ==1 ){
    						nextMultiplier = -1;
    					}
    					
	    				
	    				if (func.getRating(positive,wrdStr)){
	    					rate = rate + (1*nextMultiplier);
	    					
	    					if(nextMultiplier ==1){ subRate = subRate + "+1|";}
	    					if(nextMultiplier ==-1){ subRate = subRate + "-1|";}
	    					if(nextMultiplier ==0){ subRate = subRate + "0|";}
	    					if (negiWord){
	    						run++;
	    						//System.out.println(run);
	    						}
	    				}else if(func.getRating(happiness,wrdStr)){
	    					rate = rate +(3*nextMultiplier);
	    					
	    					if(nextMultiplier ==1){ subRate = subRate + "+3|";}
	    					if(nextMultiplier ==-1){ subRate = subRate + "-3|";}
	    					if(nextMultiplier ==0){ subRate = subRate + "0|";}
	    					
	    					if (negiWord){
	    						run++;
	    						//System.out.println(run);
	    						}
	    				}else if(func.getRating(negative,wrdStr)){
	    					rate = rate +(-1*nextMultiplier);
	    					//subRate = subRate + "-1|";
	    					if(nextMultiplier ==-1){ subRate = subRate + "+1|";}
	    					if(nextMultiplier ==1){ subRate = subRate + "-1|";}
	    					if(nextMultiplier ==0){ subRate = subRate + "0|";}
	    					if (negiWord){
	    						run++;
	    						//System.out.println(run);
	    						}
	    				}else if(func.getRating(frustration,wrdStr)){
	    					rate = rate +(-3*nextMultiplier);
	    					//subRate = subRate + "-3|";
	    					if(nextMultiplier ==1){ subRate = subRate + "-3|";}
	    					if(nextMultiplier ==-1){ subRate = subRate + "+3|";}
	    					if(nextMultiplier ==0){ subRate = subRate + "0|";}
	    					if (negiWord){
	    						run++;
	    						//System.out.println(run);
	    						}
	    				}else {
	    					rate = rate +(0*nextMultiplier);
	    					subRate = subRate + "0|";
	    					if (negiWord){
	    						run++;
	    						//System.out.println(run);
	    						}
	    				}
	    				
	    				
	    			}
	    			//System.out.println("--Sub brk--");
	    			sentRate.add(subRate);
	    			System.out.println(subRate);
	    		}
	    		
	    		
	    	}
	    	System.out.println("--Line rating : "+ rate);
	    	if (rate > 0){result.add("Positive");};    	
	    	if (rate < 0){result.add("Negative");};
	    	if (rate == 0){result.add("Neutral");};
	    	System.out.println("--Line brk--");
	    }
	    
	    for (String rest : result){
	    	System.out.println(rest);
	    }
	    func.writeOutput(result);
	}
	
	
	
}
