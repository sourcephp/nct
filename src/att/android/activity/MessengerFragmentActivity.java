package att.android.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import att.android.adapter.MessFixedTabsAdapter;
import att.android.adapter.MessViewPagerAdapter;

import com.astuetz.viewpager.extensions.FixedTabsView;
import com.astuetz.viewpager.extensions.TabsAdapter;
import com.example.multiapp.R;

public class MessengerFragmentActivity extends FragmentActivity {
	private ViewPager mPager;
	private FixedTabsView mFixedTabs;

	private MessViewPagerAdapter mPagerAdapter;
	private TabsAdapter mFixedTabsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_activity_messenger);
		initViewPager(3, 0xFFFFFFFF, 0xFF000000);
		mFixedTabs = (FixedTabsView) findViewById(R.id.fixed_tabs_mess);
		mFixedTabsAdapter = new MessFixedTabsAdapter(this);
		mFixedTabs.setAdapter(mFixedTabsAdapter);
		mFixedTabs.setViewPager(mPager);
		
	}

	private void initViewPager(int pageCount, int backgroundColor, int textColor) {
		mPager = (ViewPager) findViewById(R.id.pager_mess);
		mPagerAdapter = new MessViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(0);
		mPager.setPageMargin(1);
	}
}
