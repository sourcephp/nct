package att.android.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


import android.os.Handler;
import android.os.Message;
import att.android.bean.News;
import att.android.util.ParseXMLRss;

public class ReadRssNetwork implements Runnable {
	private String mUrl;
	private Handler mHandler;
	private InputStream mIs;
	public ReadRssNetwork(Handler mHandler) {
		this.mHandler = mHandler;
		this.mUrl = "http://www.tinhte.vn/rss/";
//		this.mUrl = "http://vnexpress.net/rss/gl/trang-chu.rss";
//		this.mUrl = "http://news.google.com.vn/news?pz=1&cf=all&ned=vi_vn&hl=vi&output=rss";
	}

	
	public void run() {
		URL url;
		try {
			url = new URL(mUrl);
			URLConnection ucon = url.openConnection();
			ucon.connect();
			mIs = url.openStream();
			ArrayList<News> result = ParseXMLRss.getDataFromXML(mIs);
			Message  msg = new Message();
			msg.what = 1;
			msg.obj = result;
			mHandler.sendMessage(msg);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
