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

public class SupportFunctions {
	protected Properties prop = null;
	public String getTokan(String item) throws Exception{
		
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
		String filename = null;
		String cwd = null;
		String path = System.getProperty("user.dir");
		if(type=="Input"){ cwd = "\\src\\supportFiles\\"+fl;}
		if(type=="Output"){ cwd = "\\src\\outPutFiles\\"+fl;}
		filename = path + cwd;
			
		return filename;

	}
	
	public void writeOutput(List<String> result){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try{
			 FileWriter fstream = new FileWriter(getfilePath("3BugsOutput"+timeStamp+".txt","Output"));
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
		List<String> split = new ArrayList<String>();
		StringTokenizer multiTokenizer = new StringTokenizer(str,token);
		while (multiTokenizer.hasMoreTokens())
		{
			split.add(multiTokenizer.nextToken());
		}

		return split;
	}
	
	public boolean  getRating(List<String> list,String str){
		boolean flag = false ;
		
		for (String dicStr: list){
			if (dicStr.trim().equalsIgnoreCase(str.trim())){
				flag = true;
				break;
			}
		}
		
		return flag;
		
	}
}
