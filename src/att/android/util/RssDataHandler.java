package att.android.util;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import att.android.bean.News;
import att.android.network.ImageNetwork;


public class RssDataHandler extends DefaultHandler {

	// booleans that check whether it's in a specific tag or not
	// this holds the data
	private News news;
	private ArrayList<News> datas;

	private boolean parsingItem;

	private StringBuilder builder;

	private int startStrInfo;
	private String strInfo;
	private int endInfo;
	private int indexPlace;
	private int strInfoLength;
	private ArrayList<String> arlStrInfo = new ArrayList<String>();
	private String strTemp;
	private int startSrcIma, endSrcIma;
	private ImageNetwork mImaNet = new ImageNetwork();

	/**
	 * Returns the data object
	 * 
	 * @return
	 */
	public ArrayList<News> getData() {
		return datas;
	}

	/**
	 * This gets called when the xml document is first opened
	 * 
	 * @throws SAXException
	 */
	@Override
	public void startDocument() throws SAXException {
		datas = new ArrayList<News>();
		builder = new StringBuilder();
	}

	/**
	 * Called when it's finished handling the document
	 * 
	 * @throws SAXException
	 */
	@Override
	public void endDocument() throws SAXException {

	}

	/**
	 * This gets called at the start of an element. Here we're also setting the
	 * booleans to true if it's at that specific tag. (so we know where we are)
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName
	 * @param atts
	 * @throws SAXException
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals("item")) {
			news = new News();
			parsingItem = true;
		} 
	}

	/**
	 * Called at the end of the element. Setting the booleans to false, so we
	 * know that we've just left that tag.
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName
	 * @throws SAXException
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("item")) {
			parsingItem = false;
			datas.add(news);
		} else if (localName.equals("pubDate") && parsingItem) {
			news.setmDate(builder.toString().trim());
		} else if (localName.equals("title") && parsingItem) {
			news.setmTitle("\t"
					+ builder.toString().trim().replace("&quot;", "\""));
		} else if (localName.equals("description") && parsingItem) {
			strInfo = builder.toString();
			startSrcIma = strInfo.indexOf("src=");
			if (startSrcIma != -1) {
				endSrcIma = strInfo.indexOf("\"", startSrcIma + 6);
				news.setStrUrlImage(strInfo.substring(
						startSrcIma + 5, endSrcIma));
				
			}
//			startStrInfo = strInfo.indexOf(">(");
//			endInfo = strInfo.indexOf("<", startStrInfo + 5);
//			strTemp = strInfo.substring(startStrInfo, endInfo);
			
			
			strInfoLength = strInfo.length();
			for (int indexPlace = 0; indexPlace < strInfoLength;) {
				startStrInfo = strInfo.indexOf(">", indexPlace);
				endInfo = strInfo.indexOf("<", startStrInfo);
				if (endInfo == -1)
					endInfo = strInfoLength;
				strTemp = strInfo.substring(startStrInfo + 1, endInfo);
				if (null != strTemp)
					arlStrInfo.add(strTemp);
				indexPlace = endInfo;
			}
			strTemp = "";
			for (indexPlace = 0; indexPlace < arlStrInfo.size(); indexPlace++) {
				strTemp = strTemp.concat(arlStrInfo.get(indexPlace));
			}
			arlStrInfo.clear();
			
			news.setmDes("\t" + strTemp.replace("&quot;", "\""));
		} else if (localName.equals("link") && parsingItem) {
			news.setmUrl(builder.toString());
		}
		builder.setLength(0);
	}

	/**
	 * Calling when we're within an element. Here we're checking to see if there
	 * is any content in the tags that we're interested in and populating it in
	 * the Config object.
	 * 
	 * @param ch
	 * @param start
	 * @param length
	 */
	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}
}