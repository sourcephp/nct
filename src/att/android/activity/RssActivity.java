package att.android.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import att.android.adapter.RssAdapter;
import att.android.bean.News;
import att.android.network.ReadRssNetwork;

import com.example.multiapp.R;

public class RssActivity extends Activity implements OnItemClickListener,
		OnItemSelectedListener {
	private ListView mListView;
	private RssAdapter mNewsAdapter;
	private ArrayList<News> mNews;
	private String strUrl;
	private WebView mWebView;
	private Spinner mSpinner;
	private Button btnBack;
	ArrayAdapter<CharSequence> adapterSpinner;
	private final CharSequence[] items = { "Tinhte.vn", "VNExpress.net",
			"Gamethu.vnexpress.net", "24h.com.vn" };
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			ArrayList<News> rs = (ArrayList<News>) msg.obj;
			for (News itm : rs) {
				mNewsAdapter.add(itm);
			}
			mNewsAdapter.notifyDataSetChanged();
			mListView.setOnItemClickListener(RssActivity.this);
		}
	};
	private ReadRssNetwork dataThread;
	private Thread thread;
	private boolean didInit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_rss);
		if(didInit  == false){
			didInit = true;
			initVariables();
		}
		initViews();
		initActions();

	}

//	@Override
//	public void onResume() {
//		super.onResume();
//		dataThread = new ReadRssNetwork(mHandler, strUrl);
//		thread = new Thread(dataThread);
//		thread.start();
//	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		mNewsAdapter.getItem(position);
		News item = mNewsAdapter.getItem(position);
		String strUrl = item.getmUrl();
		mWebView.setVisibility(View.VISIBLE);
		btnBack.setVisibility(View.VISIBLE);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		mWebView.loadUrl(strUrl);
	}

	// public void onClick(View v) {
	// if (v == btnChangRss) {
	// AlertDialog.Builder builder = new Builder(this);
	// builder.setTitle("Chọn nguồn tin");
	// builder.setItems(items, new DialogInterface.OnClickListener() {
	//
	// public void onClick(DialogInterface dialog, int which) {
	// if (items[which].equals("Tinhte.vn")) {
	// RssFragmentActivity.this.strUrl = "http://www.tinhte.vn/rss/";
	// } else if (items[which].equals("VNExpress.net")) {
	// RssFragmentActivity.this.strUrl =
	// "http://vnexpress.net/rss/gl/trang-chu.rss";
	// } else if (items[which].equals("Gamethu.vnexpress.net")) {
	// RssFragmentActivity.this.strUrl =
	// "http://gamethu.vnexpress.net/rss/gt/diem-tin.rss";
	// } else if (items[which].equals("24h.com.vn")) {
	// RssFragmentActivity.this.strUrl =
	// "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss";
	// }
	//
	// txtViewRssWeb.setText(items[which]);
	// mNewsAdapter.clear();
	// onResume();
	// }
	// });
	// builder.show();
	// } else if (v == btnBack) {
	// mWebView.setVisibility(View.INVISIBLE);
	// btnBack.setVisibility(View.INVISIBLE);
	// }
	// }

	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		switch (pos) {
		case 0:
			strUrl = "http://www.tinhte.vn/rss/";
			break;
		case 1:
			strUrl = "http://vnexpress.net/rss/gl/trang-chu.rss";
			break;
		case 2:
			strUrl = "http://gamethu.vnexpress.net/rss/gt/diem-tin.rss";
			break;
		case 3:
			strUrl = "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss";
			break;

		}
		mNewsAdapter.clear();
		startAsyncTask(strUrl);
	}

	public void onNothingSelected(AdapterView<?> arg0) {

	}


	public void initVariables() {
		mNews = new ArrayList<News>();
		mNewsAdapter = new RssAdapter(this, R.id.titleNews, mNews);
		strUrl = "http://www.tinhte.vn/rss/";
		adapterSpinner = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item, items);

	}


	public void initViews() {
		mListView = (ListView) this.findViewById(R.id.listNews);
		mWebView = (WebView) this.findViewById(R.id.webView);
		btnBack = (Button) this.findViewById(R.id.btn_webBack);
		mSpinner = (Spinner) this.findViewById(R.id.spinner_change_rss);
	}

	public void initActions() {
		mNewsAdapter.notifyDataSetChanged();
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mNewsAdapter);
		mWebView.setVisibility(View.GONE);
		btnBack.setVisibility(View.INVISIBLE);
		adapterSpinner
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(adapterSpinner);

		mSpinner.setOnItemSelectedListener(this);
		startAsyncTask(strUrl);
	}
	public void startAsyncTask(String url){
		dataThread = new ReadRssNetwork(mHandler, url);
		thread = new Thread(dataThread);
		thread.start();
	}
	
		
	
}
