package att.android.activity;

import com.example.multiapp.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import att.android.adapter.MusicViewPagerAdapter;
import att.android.util.StartFragment;


public class MusicFragmentActivity extends FragmentActivity implements
		StartFragment, ViewPager.OnPageChangeListener {
	private ViewPager mPager;
	private MusicViewPagerAdapter mPagerAdapter;
	private String data;
	private OnFragmentDataRecevier listener;
	private boolean sended;
	private boolean haveData;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_activity_music);
		initViewPager(0);
	}
	//xoa cai dong comment nay di
	private void initViewPager(int i) {
		mPager = (ViewPager) findViewById(R.id.pager_music);
		mPagerAdapter = new MusicViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(this);
		mPager.setCurrentItem(i);
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
		Log.i("MusicFragmentAcitivty", "position = " + arg0);
		if (haveData) {
			haveData = false;
			listener.onDataParameterData(data);
		}
	}

	public void setDataListener(OnFragmentDataRecevier listener) {
		this.listener = listener;
	}

	public void sendData(String data) {
		this.data = data;
		haveData = true;
	}
}
