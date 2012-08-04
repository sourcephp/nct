package att.android.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import att.android.adapter.MusicFixedTabsAdapter;
import att.android.adapter.MusicViewPagerAdapter;

import com.astuetz.viewpager.extensions.FixedTabsView;
import com.astuetz.viewpager.extensions.TabsAdapter;
import com.example.multiapp.R;

public class MusicFragmentActivity extends FragmentActivity {
	private ViewPager mPager;
	private FixedTabsView mFixedTabs;

	private MusicViewPagerAdapter mPagerAdapter;
	private TabsAdapter mFixedTabsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_activity_music);
		initViewPager();
		mFixedTabs = (FixedTabsView) findViewById(R.id.fixed_tabs_music);
		mFixedTabsAdapter = new MusicFixedTabsAdapter(this);
		mFixedTabs.setAdapter(mFixedTabsAdapter);
		mFixedTabs.setViewPager(mPager);
		
	}

	private void initViewPager() {
		mPager = (ViewPager) findViewById(R.id.pager_music);
		mPagerAdapter = new MusicViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(0);
		mPager.setPageMargin(1);
	}
}

