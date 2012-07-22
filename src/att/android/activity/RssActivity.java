package att.android.activity;

import java.util.ArrayList;
import com.example.multiapp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import att.android.adapter.RssAdapter;
import att.android.bean.News;
import att.android.network.ReadRssNetwork;

public class RssActivity extends Activity implements OnItemClickListener, OnClickListener {
	private ListView mListView;
	private RssAdapter mNewsAdapter;
	private ArrayList<News> mNews;
	private TextView txtViewRssWeb;
	private String strUrl;
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
		strUrl = "http://www.tinhte.vn/rss/";
		btnChangRss = (Button) this.findViewById(R.id.btn_change_rss);
		btnChangRss.setOnClickListener(this);
		txtViewRssWeb = (TextView) this.findViewById(R.id.txtview_rss_web);
		txtViewRssWeb.setText(strUrl);
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
		ReadRssNetwork dataThread = new ReadRssNetwork(mHandler, strUrl);
		Thread thread = new Thread(dataThread);
		thread.start();
	}

	public void onClick(View v) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("đã chuyển sang VNExpress");
		builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.show();
		strUrl = "http://vnexpress.net/rss/gl/trang-chu.rss";
		txtViewRssWeb.setText(strUrl);
		mNewsAdapter.clear();
		onResume();
	}
}
