package com.storepoints.tp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class InputFileProcessorUtil {
	
	private String inputFileName;
	
	
	public InputFileProcessorUtil(String inputFileName){
		this.inputFileName=inputFileName;
	}
	
	
	
	
	public void processInputFile() throws FileNotFoundException, IOException{
        File inputFile = new File(inputFileName);
        
        File outputFile = new File(inputFileName+".out");
        
		FileWriter fw = new FileWriter(outputFile);
		BufferedWriter bw = new BufferedWriter(fw);;        
        
        if(inputFile.exists()){
            try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                for(String line; (line = br.readLine()) != null; ) {
                	bw.write(line.startsWith("*")?"#"+line+"\n":line+"\n");
                }
            }finally {

    			try {

    				if (bw != null)
    					bw.close();

    				if (fw != null)
    					fw.close();

    			} catch (IOException ex) {

    				ex.printStackTrace();

    			}

    		}
        }   

	}
	

	public static void main(String[] args) {
		
		InputFileProcessorUtil inputFileProcessorUtil= new InputFileProcessorUtil(args[0]);
		
		try {
			inputFileProcessorUtil.processInputFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
