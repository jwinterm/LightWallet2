package com.jw.lightwallet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class CreateWallet {
	
	String			name;
	String			pw;

	
	public CreateWallet(String name, String pw) {
		
		this.name 	= name;
		this.pw 	= pw;
		
    	System.out.println("Starting creator");
	    String s = null;
	    System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
	    
	        try {
	        		            
	            Process tr = Runtime.getRuntime().exec("E:\\src\\lightWallet\\simplewallet.exe --generate-new-wallet jj --password jj");
	            Writer wr = new OutputStreamWriter( tr.getOutputStream() );
	            BufferedReader rd = new BufferedReader( new InputStreamReader( tr.getInputStream() ) );
	            System.out.println("Here is the standard output of the command:\n");
	            while (true) {
	            	String str = rd.readLine();
		            System.out.println( str );
		            if (str.contains("Japanese")) {
		            	break;
		            }
	            }
	            wr.write( "0\n" );
	            wr.flush();
	            String str = rd.readLine();
	            System.out.println( str );
	            System.out.println("Here is the standard output of the command:\n");
	            while ((s = rd.readLine()) != null) {
	                System.out.println(s);
	            }	            
	            
	            wr.write( "exit\n" );
	            wr.flush();
	            /* 
	            // run the Unix "ps -ef" command
	            // using the Runtime exec method:
	            Process p = Runtime.getRuntime().exec("E:\\src\\lightWallet\\simplewallet.exe --generate-new-wallet jj --password jj");
	             
	            BufferedReader stdInput = new BufferedReader(new
	                 InputStreamReader(p.getInputStream()));
	 
	            BufferedReader stdError = new BufferedReader(new
	                 InputStreamReader(p.getErrorStream()));
	            
	 
	            // read the output from the command
	            System.out.println("Here is the standard output of the command:\n");
	            while ((s = stdInput.readLine()) != null) {
	                System.out.println(s);
	            }
	             
	            // read any errors from the attempted command
	            System.out.println("Here is the standard error of the command (if any):\n");
	            while ((s = stdError.readLine()) != null) {
	                System.out.println(s);
	            }*/
	            
	            
	             
	            System.exit(0);
	        }
	        catch (IOException e) {
	            System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	            System.exit(-1);
	        }
	}

}
