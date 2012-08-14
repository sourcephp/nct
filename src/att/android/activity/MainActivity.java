package att.android.activity;


import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.multiapp.R;

public class MainActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost mTabHost = getTabHost();
        
        TabSpec tabMess = mTabHost.newTabSpec("messenger");
        tabMess.setIndicator("",getResources().getDrawable(R.drawable.custom_btn_login)).setContent(new Intent(this, MessengerFragmentActivity.class));
        mTabHost.addTab(tabMess);
        
        TabSpec tabSong = mTabHost.newTabSpec("songs");
        tabSong.setIndicator("", getResources().getDrawable(R.drawable.custom_tab_music)).setContent(new Intent(this, MusicFragmentActivity.class));
        mTabHost.addTab(tabSong);
        
        TabSpec tabRss = mTabHost.newTabSpec("rss");
        tabRss.setIndicator("", getResources().getDrawable(R.drawable.custom_rss_tab)).setContent(new Intent(this, RssActivity.class));
        mTabHost.addTab(tabRss);
       
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	Log.e("an nut back", "a aj   k  j j  kk   k kk ");
    	showExitDialog();
    }
public void showExitDialog(){
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			this);
		alertDialogBuilder
			.setMessage("Click yes to exit!")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					MainActivity.this.finish();
				}
			  })
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			});
			alertDialogBuilder.show();
}
}