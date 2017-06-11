package com.parser;

import java.io.IOException;

import org.jsoup.*;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Main Class/Entry Point
 *
 */
public class App 
{
    public UrlParser urlParser;
    public DocParser docParser;
    public static void main( String[] args ) throws Exception{
        
	System.out.println( "Parsing the web page..."+args[0]);
        App app = new App();
        app.startApp(args);

    }
    public void startApp(String[] args) throws Exception{
    	//System.out.println("args is "+args[0]);
    	String url = "";
    	if(args.length<=0) throw new Exception("Please provide the URL to parse the page");
    	else url = args[0];
    	
    	//"http://www.amazon.com/Cuisinart-CPT-122-Compact-2-Slice-Toaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster";
    	//String url = "http://blog.rei.com/camp/how-to-introduce-your-indoorsy-friend-to-the-outdoors/";
    	urlParser = new UrlParser(url);
    	
    	//Check if the URL is correct, else throw an exception
        Document doc = urlParser.validateUrl(url);
	Elements header = doc.select("h1");
	String paraContent = doc.select("p").first().text();
	String headerContent = header.text();
        String mainContent = doc.body().text();
        System.out.println(headerContent+ " "+ paraContent);
        //Parse the document content to get the important keywords
	docParser = new DocParser(mainContent, headerContent, paraContent);
	    
    }
}
