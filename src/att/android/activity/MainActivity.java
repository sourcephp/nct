package att.android.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.example.multiapp.R;

public class MainActivity extends TabActivity {
	private TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);

		TabSpec tabLogin = mTabHost.newTabSpec("tabLogin");
		tabLogin.setIndicator("", getResources().getDrawable(R.drawable.custom_login_tab));
		Intent itLogin = new Intent(this, LoginActivity.class);
		tabLogin.setContent(itLogin);

		TabSpec tabContactList = mTabHost.newTabSpec("tabContact");
		tabContactList.setIndicator("", getResources().getDrawable(R.drawable.custom_contact_tab));
		Intent itContact = new Intent(this, ContactListActivity.class);
		tabContactList.setContent(itContact);

		TabSpec tabReadRss = mTabHost.newTabSpec("tabReadRss");
		tabReadRss.setIndicator("", getResources().getDrawable(R.drawable.custom_rss_tab));
		Intent itReadRss = new Intent(this, RssActivity.class);
		tabReadRss.setContent(itReadRss);

		TabSpec tabChatJoint = mTabHost.newTabSpec("chat");
		tabChatJoint.setIndicator("", getResources().getDrawable(R.drawable.custom_chat_tab));
		Intent itChatJoint = new Intent(this, ChatJointActivity.class);
		tabChatJoint.setContent(itChatJoint);

		mTabHost.addTab(tabLogin);
		mTabHost.addTab(tabContactList);
		mTabHost.addTab(tabChatJoint);
		mTabHost.addTab(tabReadRss);

		setBackgroundTab(mTabHost);
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String arg0) {
				setBackgroundTab(MainActivity.this.mTabHost);
			}
		});
	}

	private void setBackgroundTab(TabHost mTabHost) {
//		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
//			mTabHost.getTabWidget()
//					.getChildAt(i)
//					.setBackgroundDrawable(
//							getResources()
//									.getDrawable(R.drawable.tab_not_focus));
//		}
//
//		mTabHost.getTabWidget()
//				.getChildAt(mTabHost.getCurrentTab())
//				.setBackgroundDrawable(
//						getResources().getDrawable(R.drawable.tab_focus));
	}

	public void switchTabSpecial(int tab) {
		mTabHost.setCurrentTab(tab);
	}

	public void changeTab(int tab) {

	}
}
