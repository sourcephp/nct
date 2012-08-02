package att.android.activity;

import java.util.ArrayList;

import com.example.multiapp.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import att.android.adapter.RssAdapter;
import att.android.bean.News;
import att.android.network.ReadRssNetwork;

public class RssFragment extends Fragment implements OnItemClickListener,
		OnClickListener {
	private ListView mListView;
	private RssAdapter mNewsAdapter;
	private ArrayList<News> mNews;
	private TextView txtViewRssWeb;
	private String strUrl;
	private WebView mWebView;
	private Button btnBack;
	private final CharSequence[] items = { "Tinhte.vn", "VNExpress.net",
			"Gamethu.vnexpress.net", "24h.com.vn" };
	private Button btnChangRss;
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
			mListView.setOnItemClickListener(RssFragment.this);
		}
	};
	private ReadRssNetwork dataThread;
	private Thread thread;

	/** Called when the activity is first created. */
	public static Fragment newInstance(Context context) {
		RssFragment f = new RssFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(
				R.layout.activity_read_rss, null);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mNews = new ArrayList<News>();
		mNewsAdapter = new RssAdapter(this.getActivity(), R.id.titleNews, mNews);
		mListView = (ListView) this.getView().findViewById(R.id.listNews);
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mNewsAdapter);

		mWebView = (WebView) this.getView().findViewById(R.id.webView);
		mWebView.setVisibility(View.GONE);
		strUrl = "http://www.tinhte.vn/rss/";
		btnBack = (Button) this.getView().findViewById(R.id.btn_webBack);
		btnBack.setOnClickListener(this);
		btnBack.setVisibility(View.INVISIBLE);
		btnChangRss = (Button) this.getView().findViewById(R.id.btn_change_rss);
		btnChangRss.setOnClickListener(this);
		txtViewRssWeb = (TextView) this.getView().findViewById(
				R.id.txtview_rss_web);
		txtViewRssWeb.setText(items[0]);

	}

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

	@Override
	public void onResume() {
		super.onResume();
		dataThread = new ReadRssNetwork(mHandler, strUrl);
		thread = new Thread(dataThread);
		thread.start();
	}

	public void onClick(View v) {
		if (v == btnChangRss) {
			AlertDialog.Builder builder = new Builder(this.getActivity());
			builder.setTitle("Chọn nguồn tin");
			builder.setItems(items, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					if (items[which].equals("Tinhte.vn")) {
						RssFragment.this.strUrl = "http://www.tinhte.vn/rss/";
					} else if (items[which].equals("VNExpress.net")) {
						RssFragment.this.strUrl = "http://vnexpress.net/rss/gl/trang-chu.rss";
					} else if (items[which].equals("Gamethu.vnexpress.net")) {
						RssFragment.this.strUrl = "http://gamethu.vnexpress.net/rss/gt/diem-tin.rss";
					} else if (items[which].equals("24h.com.vn")) {
						RssFragment.this.strUrl = "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss";
					}

					txtViewRssWeb.setText(items[which]);
					mNewsAdapter.clear();
					onResume();
				}
			});
			builder.show();
		} else if (v == btnBack) {
			mWebView.setVisibility(View.INVISIBLE);
			btnBack.setVisibility(View.INVISIBLE);
		}
	}
}
