package att.android.activity;

import com.example.multiapp.R;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	private TabHost mTabHost;
	private TabSpec tabReadRss;
	private TabSpec tabWebView;
	private View tab;

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
		
		tabReadRss = mTabHost.newTabSpec("tabReadRss");
		tabReadRss.setIndicator("News");
		Intent itReadRss = new Intent(this, RssActivity.class);
		tabReadRss.setContent(itReadRss);
		
		tabWebView = mTabHost.newTabSpec("Web");
		tabWebView.setIndicator("Read News");
		Intent itWebView = new Intent(this, WebViewActivity.class);
		tabWebView.setContent(itWebView);
		
		mTabHost.addTab(tabLogin);
		mTabHost.addTab(tabContactList);
		mTabHost.addTab(tabReadRss);
		mTabHost.addTab(tabWebView);
		
//		tab = getTabHost().getTabWidget().getChildTabViewAt(3);
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
	public void switchTabSpecial(int tab){
        mTabHost.setCurrentTab(tab);
}
	public void changeTab(int tab){
		
	}
}
