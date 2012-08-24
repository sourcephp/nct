package att.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import att.android.adapter.RssAdapter;
import att.android.bean.News;
import att.android.network.ReadRssNetwork;
import att.android.util.MyDialog;
import att.android.util.MyDialog.OnMyDialogListener;

import com.example.multiapp.R;
import com.quickaction.popup.ActionItem;
import com.quickaction.popup.QuickAction;
import com.quickaction.popup.QuickAction.OnActionItemClickListener;

public class RssActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private ListView mListView;
	private RssAdapter mNewsAdapter;
	private ArrayList<News> mNews;
	private String strUrl;
	private WebView mWebView;
	private Button btnBack;
	private Button btnWebName;
	
	private QuickAction mQuickAction;
	private boolean didInit = false;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			ArrayList<News> rs = (ArrayList<News>) msg.obj;
			for (News itm : rs) {
				mNewsAdapter.add(itm);
			}
			mNewsAdapter.notifyDataSetChanged();
			mListView.setOnItemClickListener(RssActivity.this);
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_rss);
		if (didInit == false) {
			didInit = true;
			initVariables(this);
		}
		initViews();
		initActions();

	}

	public void initVariables(Context context) {
		
		mNews = new ArrayList<News>();
		mNewsAdapter = new RssAdapter(this, R.id.titleNews, mNews);
		strUrl = "http://gamethu.vnexpress.net/rss/gt/diem-tin.rss";
		mQuickAction = new QuickAction(this);
	}

	public void initViews() {
		mListView = (ListView) this.findViewById(R.id.listNews);
		mWebView = (WebView) this.findViewById(R.id.webView);
		btnBack = (Button) this.findViewById(R.id.btn_webBack);
		btnWebName = (Button) this.findViewById(R.id.web_name);
	}

	public void initActions() {
		mNewsAdapter.notifyDataSetChanged();
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mNewsAdapter);

		mWebView.setVisibility(View.GONE);
		btnBack.setOnClickListener(this);
		btnBack.setVisibility(View.INVISIBLE);
		btnWebName.setOnClickListener(this);
		ActionItem action247= new ActionItem();
		action247.setTitle("Tin24/7");
		action247.setIcon(getResources().getDrawable(R.drawable.logo_tin247));
		ActionItem actionVnEx = new ActionItem();
		actionVnEx.setTitle("VnExpress.net");
		actionVnEx.setIcon(getResources().getDrawable(R.drawable.logo_vnexpress));
		ActionItem actionGame = new ActionItem();
		actionGame.setTitle("Gamethu.net");
		actionGame.setIcon(getResources().getDrawable(R.drawable.logo_gamethu));
		ActionItem action24h = new ActionItem();
		action24h.setTitle("24h.com.vn");
		action24h.setIcon(getResources().getDrawable(R.drawable.logo_24h));
		ActionItem actionBongDa = new ActionItem();
		actionBongDa.setTitle("Thethao.net");
		actionBongDa.setIcon(getResources().getDrawable(R.drawable.logo_thethao));
		ActionItem actionIt = new ActionItem();
		actionIt.setTitle("Congnghe.com");
		actionIt.setIcon(getResources().getDrawable(R.drawable.logo_congnghe));
		mQuickAction.addActionItem(actionGame);
		mQuickAction.addActionItem(actionVnEx);
		mQuickAction.addActionItem(action247);
		mQuickAction.addActionItem(action24h);
		mQuickAction.addActionItem(actionBongDa);
		mQuickAction.addActionItem(actionIt);
		mQuickAction
				.setOnActionItemClickListener(new OnActionItemClickListener() {

					public void onItemClick(QuickAction source, int pos,
							int actionId) {
						switch (pos) {
						case 0:
							strUrl = "http://gamethu.vnexpress.net/rss/gt/diem-tin.rss";
							break;
						case 1:
							strUrl = "http://vnexpress.net/rss/gl/trang-chu.rss";
							break;
						case 2:
							strUrl = "http://www.tin247.com/rss/0.rss";
							break;
						case 3:
							strUrl = "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss";
							break;
						case 4:
							strUrl = "http://thethao.vnexpress.net/rss/tin-moi-nhat.rss";
							break;
						case 5:
							strUrl = "http://feeds.thongtincongnghe.com/ttcn?format=xml";
							break;
						}
						btnWebName
								.setText(source.getActionItem(pos).getTitle());
						mNewsAdapter.clear();
						getDataNetwork(strUrl);
					}
				});
		getDataNetwork(strUrl);
	}

	public void onClick(View v) {
		if (v == btnBack) {
			btnBack.setVisibility(View.INVISIBLE);
			mWebView.setVisibility(View.GONE);
		}
		if (v == btnWebName) {
			mQuickAction.show(v);
		}
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

	public void onNothingSelected(AdapterView<?> arg0) {

	}
private void getDataNetwork(String url){
	ReadRssNetwork mConnectNetwork = new ReadRssNetwork(mHandler, url);
	Thread thread = new Thread(mConnectNetwork);
	thread.start();
}
@Override
public void onBackPressed() {
	MyDialog mDialog = new MyDialog(this, R.drawable.trash);
	mDialog.setOnMyDialogListener(new OnMyDialogListener() {
		
		public void onItemClick(boolean isOk) {
			if(isOk){
				RssActivity.this.finish();
			}
			
		}
	});
	mDialog.show();
//	super.onBackPressed();
}
}
