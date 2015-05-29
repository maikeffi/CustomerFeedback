package coreRunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class SupportFunctions {
	protected Properties prop = null;
	protected Logger logs;
	public String getToken(String item) throws Exception{
		/*
		 * this function reads the properties file and send the required token back to tokenizer 
		 */
		
		if (prop == null){ 
			prop = new Properties();
			try { 
						FileInputStream  ip = new FileInputStream(System.getProperty("user.dir")+"\\src\\supportFiles\\tokens.properties"); 
						prop.load(ip); 
				 
				}catch(Exception t){ 
				System.out.println("Error in reading properties file"); 
				} 

		}
		return prop.getProperty(item);
	}
	public List<String> loadInput(String fileName) throws IOException {
		/*
		 * This function loads the file reading it line by line and returns back a arraylist of strings 
		 * 
		 */
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
	
	public String getfilePath(String fl,String type){
		/*
		 * This function Gives back a file path were input are stored & output files are created 
		 */
		String filename = null;
		String cwd = null;
		String path = System.getProperty("user.dir");
		if(type=="Input"){ cwd = "\\src\\supportFiles\\"+fl;}
		if(type=="Output"){ cwd = "\\src\\outPutFiles\\"+fl;}
		filename = path + cwd;
			
		return filename;

	}
	
	public void writeOutput(List<String> result){
		
	/*
	 * This function writes the results obtained from sentence rating into a text file  
	 */
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try{
			 FileWriter fstream = new FileWriter(getfilePath("3Bugs_Out.txt","Output"));
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
	
	public List<String> splitByToken(String str, String token){
		/*
		 * this function splits a given string by obtained token and returns an arraylist of strings
		 */
		List<String> split = new ArrayList<String>();
		StringTokenizer multiTokenizer = new StringTokenizer(str,token);
		while (multiTokenizer.hasMoreTokens())
		{
			split.add(multiTokenizer.nextToken());
		}

		return split;
	}
	
	public boolean  getRating(List<String> list,String str){
		/*
		 * This function looks for given word in a list and returns a boolean value 
		 */
		boolean flag = false ;
		
		for (String dicStr: list){
			if (dicStr.trim().equalsIgnoreCase(str.trim())){
				flag = true;
				break;
			}
		}
		
		return flag;
		
	}
	public void log(String string) throws Exception{
		/*
		 * This function creates the log file for this application
		 */
		try{
			
			logs = Logger.getLogger("tokenlogger");
			logs.debug(string);
						
		}catch (Exception e){
			System.out.println("logging failed");
		}
	}
}
