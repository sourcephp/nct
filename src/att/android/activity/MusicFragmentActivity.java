package att.android.activity;

import java.util.ArrayList;

import com.example.multiapp.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import att.android.adapter.MusicViewPagerAdapter;
import att.android.bean.Music_Song;
import att.android.util.StartFragment;

public class MusicFragmentActivity extends FragmentActivity implements
		StartFragment, ViewPager.OnPageChangeListener {
	private ViewPager mPager;
	private MusicViewPagerAdapter mPagerAdapter;
	private ArrayList<Music_Song> data;
	private OnFragmentDataRecevier listener;
	private boolean sended;
	private boolean haveData;
	private int index;
	private int position;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_activity_music);
		initViewPager(0);
	}

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

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageSelected(int arg0) {
		Log.i("MusicFragmentAcitivty", "position = " + arg0);
		if (haveData) {
			haveData = false;
			if (check) {
				listener.onDataParameterData(index);
			} else {
				listener.onDataParameterData(data, position);
			}
		}
	}

	public void setDataListener(OnFragmentDataRecevier listener) {
		this.listener = listener;
	}

	private boolean check;

	public void sendData(int index) {
		check = true;
		this.index = index;
		haveData = true;
	}

	public void sendData(ArrayList<Music_Song> data, int position) {
		check = false;
		this.data = data;
		this.position = position;
		haveData = true;
	}
}
