package att.android.activity;

import org.openymsg.network.YahooUser;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import att.android.adapter.MessViewPagerAdapter;

import com.example.multiapp.R;

public class MessengerFragmentActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
	private ViewPager mPager;
	private MessViewPagerAdapter mPagerAdapter;
	
	private OnFragmentDataRecevier listener;
	private boolean haveData;
	private YahooUser data;

	

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_activity_messenger);
		initViewPager(3, 0xFFFFFFFF, 0xFF000000);
//		Intent intent = getIntent();
//		Bundle bundle = intent.getExtras().getBundle("YahooUser");
	}

	private void initViewPager(int pageCount, int backgroundColor, int textColor) {
		mPager = (ViewPager) findViewById(R.id.pager_mess);
		mPagerAdapter = new MessViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(0);
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
		Log.w("MessengerFragmentActivity", "fragment" + arg0);
		if (haveData) {
			haveData = false;
			listener.onDataParameterData(data);
		}
	}

	public void setDataListener(OnFragmentDataRecevier listener) {
		this.listener = listener;
	}

	public void sendData(YahooUser data) {
		this.data = data;
		haveData = true;
	}
}
