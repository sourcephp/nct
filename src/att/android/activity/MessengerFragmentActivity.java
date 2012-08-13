package att.android.activity;

import java.util.ArrayList;

import org.openymsg.network.YahooUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import att.android.adapter.MessViewPagerAdapter;
import att.android.bean.Music_Song;
import att.android.network.ReadFullContactListNetwork;
import att.android.util.StartFragment;

import com.example.multiapp.R;

public class MessengerFragmentActivity extends FragmentActivity implements
		StartFragment, ViewPager.OnPageChangeListener {
	private ViewPager mPager;
	private MessViewPagerAdapter mPagerAdapter;
	
	private OnFragmentDataRecevier listener;
	private boolean sended;
	private boolean haveData;
	private int index;
	private int position;
	private ArrayList<YahooUser> data;

	

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
		// TODO Auto-generated method stub

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public void onPageSelected(int arg0) {
		Log.w("MessengerFragmentActivity", "fragment" + arg0);
		if (haveData) {
			haveData = false;
			if (test) {
				listener.onDataParameterData2(data, position, flag);
			} else {
				listener.onDataParameterData(index);
			}
		}
	}

	public void setDataListener(OnFragmentDataRecevier listener) {
		this.listener = listener;
	}

	private boolean test;
	private boolean flag = false;

	public void sendData(int index) {
		test = false;
		this.index = index;
		haveData = true;
	}

	public void sendData(ArrayList<YahooUser> data, int position, boolean flag) {
		test = true;
		this.data = data;
		this.position = position;
		this.flag = flag;
		haveData = true;
	}

	public void startFragment1(ArrayList<Music_Song> item, int position,
			boolean bool) {
		// TODO Auto-generated method stub
		
	}

	public void startFragment2(ArrayList<YahooUser> item, int position,
			boolean bool) {
		// TODO Auto-generated method stub
		
	}
}
