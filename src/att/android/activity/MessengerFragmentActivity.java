package att.android.activity;

import com.example.multiapp.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import att.android.adapter.MessViewPagerAdapter;
import att.android.util.StartFragment;


public class MessengerFragmentActivity extends FragmentActivity implements StartFragment{
	private ViewPager mPager;

	private MessViewPagerAdapter mPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_activity_messenger);
		initViewPager(3, 0xFFFFFFFF, 0xFF000000);
		
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
}
