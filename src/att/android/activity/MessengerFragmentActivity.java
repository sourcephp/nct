package att.android.activity;

import org.openymsg.network.YahooUser;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import att.android.adapter.MessViewPagerAdapter;
import att.android.bean.Conversation;
import att.android.model.OnYahooFragmentDataReceiver;
import att.android.util.MyDialog;
import att.android.util.MyDialog.OnMyDialogListener;

import com.example.multiapp.R;

public class MessengerFragmentActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
	private ViewPager mPager;
	private MessViewPagerAdapter mPagerAdapter;
	
	private OnYahooFragmentDataReceiver listener;
	private boolean haveData;
	private YahooUser data;
	private Conversation conversation_temp;

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_messenger);
		initViewPager(0, 0xFFFFFFFF, 0xFF000000);
//		Intent intent = getIntent();
//		Bundle bundle = intent.getExtras().getBundle("YahooUser");
	}

	private void initViewPager(int pageCount, int backgroundColor, int textColor) {
		mPager = (ViewPager) findViewById(R.id.pager_mess);
		mPagerAdapter = new MessViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(this);
		mPager.setCurrentItem(pageCount);
		mPager.setPageMargin(1);
	}

	public void startFragment(int i) {
		mPager.setCurrentItem(i);
	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageSelected(int arg0) {
		Log.e("MessengerFragmentActivity", "fragment" + arg0);
		if (haveData) {
			haveData = false;
			listener.onDataParameterData(data, conversation_temp);
			Log.e("MessengerFragmentActivity", data.getId());
		}
	}

	public void setDataListener(OnYahooFragmentDataReceiver listener) {
		this.listener = listener;
	}

	public void sendData(YahooUser data, Conversation conversation_temp) {
		this.data = data;
		this.conversation_temp = conversation_temp;
		haveData = true;
	}
	@Override
	public void onBackPressed() {
		MyDialog mDialog = new MyDialog(this, R.drawable.thumb_exit);
		mDialog.setOnMyDialogListener(new OnMyDialogListener() {
			
			public void onItemClick(boolean isOk) {
				if(isOk){
					MessengerFragmentActivity.this.finish();
				}
				
			}
		});
		mDialog.show();
	}
}
