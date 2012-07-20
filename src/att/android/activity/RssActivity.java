package att.android.activity;

import java.util.ArrayList;
import com.example.multiapp.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import att.android.adapter.RssAdapter;
import att.android.bean.News;
import att.android.network.ReadRssNetwork;

public class RssActivity extends Activity implements OnItemClickListener {
	private ListView mListView;
	private RssAdapter mNewsAdapter;
	private ArrayList<News> mNews;
	public static Bitmap bitmapSample;
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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_rss);
		mNews = new ArrayList<News>();
		mNewsAdapter = new RssAdapter(this.getApplicationContext(),
				R.id.titleNews, mNews);
		mListView = (ListView) findViewById(R.id.listNews);
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mNewsAdapter);

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		mNewsAdapter.getItem(position);
		News item = mNewsAdapter.getItem(position);
		String strUrl = item.getmUrl();
		Intent mIntent = new Intent(this, WebViewActivity.class);
		mIntent.putExtra("ITEM", strUrl);
		startActivity(mIntent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ReadRssNetwork dataThread = new ReadRssNetwork(mHandler);
		Thread thread = new Thread(dataThread);
		thread.start();
	}
}
