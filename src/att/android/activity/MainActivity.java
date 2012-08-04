package att.android.activity;

import com.example.multiapp.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost mTabHost = getTabHost();
        
        TabSpec tabMess = mTabHost.newTabSpec("messenger");
        tabMess.setIndicator("messenger").setContent(new Intent(this, MessengerFragmentActivity.class));
        mTabHost.addTab(tabMess);
        
        TabSpec tabSong = mTabHost.newTabSpec("songs");
        tabSong.setIndicator("songs").setContent(new Intent(this, MusicFragmentActivity.class));
        mTabHost.addTab(tabSong);
        
        TabSpec tabRss = mTabHost.newTabSpec("rss");
        tabRss.setIndicator("news").setContent(new Intent(this, RssFragmentActivity.class));
        mTabHost.addTab(tabRss);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	

}