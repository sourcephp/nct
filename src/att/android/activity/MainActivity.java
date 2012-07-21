package att.android.activity;

import com.example.multiapp.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	private TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		
		TabSpec tabLogin = mTabHost.newTabSpec("tabLogin");
		tabLogin.setIndicator("Login");
		Intent itLogin = new Intent(this, LoginActivity.class);
		tabLogin.setContent(itLogin);
		
		TabSpec tabContactList = mTabHost.newTabSpec("tabContact");
		tabContactList.setIndicator("Contact List");
		Intent itContact = new Intent(this, ContactListActivity.class);
		tabContactList.setContent(itContact);
		
		TabSpec tabReadRss = mTabHost.newTabSpec("tabReadRss");
		tabReadRss.setIndicator("News");
		Intent itReadRss = new Intent(this, RssActivity.class);
		tabReadRss.setContent(itReadRss);
		
		mTabHost.addTab(tabLogin);
		mTabHost.addTab(tabContactList);
		mTabHost.addTab(tabReadRss);
		
		setBackgroundTab(mTabHost);
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

	        public void onTabChanged(String arg0) {
	        	setBackgroundTab(MainActivity.this.mTabHost);
	        }
	});
	}
	private void setBackgroundTab(TabHost mTabHost){
		for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
		{   
			mTabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_not_focus)); //unselected tab
		}
		
		mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_focus));
	}
}
