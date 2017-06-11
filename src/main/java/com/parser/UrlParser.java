package com.parser;
import java.io.IOException;

import org.jsoup.*;

import org.jsoup.nodes.Document;

public class UrlParser {

	//Validate URL passed throught the constructor
    public UrlParser(String url){
        validateUrl(url);
    }
      
    public Document validateUrl(String url){
    	Document doc=null;
        try {
            doc=Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            System.out.println("Something is wrong with your URL: "+url.toString());
            e.printStackTrace();
        }
        return doc;
    }   
}



	
	
