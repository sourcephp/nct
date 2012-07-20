package att.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.util.Log;
import att.android.bean.News;

public class ParseXMLRss {
	public static ArrayList<News> getDataFromXML(InputStream is){
		  // sax stuff 
		ArrayList<News> result = new ArrayList<News>();
		  try { 
		    SAXParserFactory spf = SAXParserFactory.newInstance(); 
		    SAXParser sp = spf.newSAXParser(); 
		    XMLReader xr = sp.getXMLReader(); 
		    RssDataHandler dataHandler = new RssDataHandler(); 
		    xr.setContentHandler(dataHandler); 
		 
		    xr.parse(new InputSource(is)); 
		 
		    result = dataHandler.getData();
		 
		  } catch(ParserConfigurationException pce) { 
		    Log.e("SAX XML", "sax parse error", pce); 
		  } catch(SAXException se) { 
		    Log.e("SAX XML", "sax error", se); 
		  } catch(IOException ioe) { 
		    Log.e("SAX XML", "sax parse io error", ioe); 
		  } 
		  return result;
	}
}
