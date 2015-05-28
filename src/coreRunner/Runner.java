package coreRunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException  {
		// TODO Auto-generated method stub
		
		
		List<String> input = loadInput(getfilePath("inputsample.txt"));
		List<String> positive = loadInput(getfilePath("positive.txt"));
		List<String> negative = loadInput(getfilePath("negative.txt"));
		List<String> frustration = loadInput(getfilePath("frustration.txt"));
		List<String> happiness = loadInput(getfilePath("happiness.txt"));
		List<String> negiMulti = loadInput(getfilePath("negativeMuliplier.txt"));
		List<String> sentence = new ArrayList<String>();
		List<String> subject = new ArrayList<String>();
		List<String> words = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		int run = 0 ;
		int nextMultiplier = 1;
		boolean negiWord = false;
		
		/*Set<String> positive = loadDictionary("C:\\Users\\Administrator\\Desktop\\Hackathon\\CustomerFeedback\\src\\coreRunner\\positive.txt");
	    Set<String> negative = loadDictionary("C:\\Users\\Administrator\\Desktop\\Hackathon\\CustomerFeedback\\src\\coreRunner\\negative.txt");
	    Set<String> emotion= loadDictionary("C:\\Users\\Administrator\\Desktop\\Hackathon\\CustomerFeedback\\src\\coreRunner\\emotion.txt");*/
	    
	    for (String str: input){
	    	int rate = 0;
	    	//System.out.println(str);
	    	sentence = splitByToken(str,".");
	    	//System.out.println(sentence.size());
	    	
	    	for (String sentStr: sentence){
	    		//System.out.println(sentStr);
	    		subject = splitByToken(sentStr,",!");
	    		
	    		List<String> sentRate = new ArrayList<String>();
	    		for (String subStr: subject){
	    			String subRate = "";
	    			System.out.println(subStr);
	    			words = splitByToken(subStr," ");
	    			
	    			for(String wrdStr: words){
	    				
	    				if (run==2){
    						negiWord =false;
    						nextMultiplier = 1;
    					}
	    				
    					if(getRating(negiMulti,wrdStr) & !negiWord){
	    					
    						negiWord = true;
    						run = 0;
	    				}
    					
    					if (negiWord && run ==0 ){
    						nextMultiplier = 0;
    					}
    					if (negiWord && run ==1 ){
    						nextMultiplier = -1;
    					}
    					
	    				
	    				if (getRating(positive,wrdStr)){
	    					rate = rate + (1*nextMultiplier);
	    					
	    					if(nextMultiplier ==1){ subRate = subRate + "+1|";}
	    					if(nextMultiplier ==-1){ subRate = subRate + "-1|";}
	    					if(nextMultiplier ==0){ subRate = subRate + "0|";}
	    					if (negiWord){
	    						run++;
	    						//System.out.println(run);
	    						}
	    				}else if(getRating(happiness,wrdStr)){
	    					rate = rate +(3*nextMultiplier);
	    					
	    					if(nextMultiplier ==1){ subRate = subRate + "+3|";}
	    					if(nextMultiplier ==-1){ subRate = subRate + "-3|";}
	    					if(nextMultiplier ==0){ subRate = subRate + "0|";}
	    					
	    					if (negiWord){
	    						run++;
	    						//System.out.println(run);
	    						}
	    				}else if(getRating(negative,wrdStr)){
	    					rate = rate +(-1*nextMultiplier);
	    					//subRate = subRate + "-1|";
	    					if(nextMultiplier ==-1){ subRate = subRate + "+1|";}
	    					if(nextMultiplier ==1){ subRate = subRate + "-1|";}
	    					if(nextMultiplier ==0){ subRate = subRate + "0|";}
	    					if (negiWord){
	    						run++;
	    						//System.out.println(run);
	    						}
	    				}else if(getRating(frustration,wrdStr)){
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
	    				
	    				/*if(positive.contains(wrdStr)){
	    					
	    					rate = "1";
	    					subRate = subRate + rate;	    					
	    				}else if(negative.contains(wrdStr)){
	    					rate = "-1";
	    					subRate = subRate + rate;
	    				}else {
	    					rate = "0";
	    					subRate = subRate + rate;
	    				}*/
	    				
	    				//System.out.println(wrdStr+"||"+rate);
	    			}
	    			//System.out.println("--Sub brk--");
	    			sentRate.add(subRate);
	    			System.out.println(subRate);
	    		}
	    		/*for (String rte:sentRate){
	    			if ( rte.length() > 0 ){
	    			    	System.out.println(rte);
	    			}

	    			
	    		}*/
	    		//System.out.println("--Sent brk--");
	    		
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
	    writeOutput(result);
	}
	
	/*public static Set<String> loadDictionary(String fileName) throws IOException {
	    Set<String> words = new HashSet<String>();
	    File file = new File(fileName);
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	    Scanner sc = new Scanner(br);
	    while (sc.hasNext()) {
	        words.add(sc.next());
	    }
	    br.close();
	    return words;
	}*/
	public static List<String> loadInput(String fileName) throws IOException {
		List<String> lines = new ArrayList<String>();
		
		BufferedReader in = null;
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            in = new BufferedReader(fr);
            String str;
            while ((str = in.readLine()) != null) {
            	lines.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
            fr.close();
        }

		
		return lines;
	}
	
	public static List<String> splitByToken(String str, String token){
		List<String> split = new ArrayList<String>();
		StringTokenizer multiTokenizer = new StringTokenizer(str,token);
		while (multiTokenizer.hasMoreTokens())
		{
			split.add(multiTokenizer.nextToken());
		}

		return split;
	}
	
	public static boolean  getRating(List<String> list,String str){
		boolean flag = false ;
		
		for (String dicStr: list){
			if (dicStr.trim().equalsIgnoreCase(str.trim())){
				flag = true;
				break;
			}
		}
		
		return flag;
		
	}
	
	public static String getfilePath(String fl){
		String filename = null;
		String path = System.getProperty("user.dir");
		String cwd = "\\src\\coreRunner\\"+fl;
		filename = path + cwd;
			
		return filename;

	}
	
	public static void writeOutput(List<String> result){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try{
			 FileWriter fstream = new FileWriter(getfilePath("FeedBackOutput"+timeStamp+".txt"));
			 BufferedWriter out = new BufferedWriter(fstream);
			 for (String str:result){
			 out.write(str);
			 out.newLine();
			 }
			 out.close();
			 }catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
			 }

		
	} 
}
