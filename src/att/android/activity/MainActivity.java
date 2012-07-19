package att.android.activity;

import com.example.multiapp.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
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
		Intent intLogin = new Intent(this, LoginActivity.class);
		tabLogin.setContent(intLogin);
		
		mTabHost.addTab(tabLogin);
	}
}
