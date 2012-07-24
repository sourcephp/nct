package att.android.activity;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.multiapp.R;

public class ChatJointActivity extends TabActivity{
	private TabHost chatJointTabHost;
	private ArrayList<TabSpec> arlTabspace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
	
		chatJointTabHost = (TabHost) this.findViewById(android.R.id.tabhost);
		arlTabspace = new ArrayList<TabHost.TabSpec>();
	}

	@Override
	protected void onResume() {
		super.onResume();
		newChatTab("test");
	}

	private void newChatTab(String nameFriend) {
		TabSpec tabspec = chatJointTabHost.newTabSpec(nameFriend);
		tabspec.setIndicator(nameFriend);
		Intent i = new Intent(ChatJointActivity.this, ChatTabActivity.class);
		tabspec.setContent(i);

		chatJointTabHost.addTab(tabspec);

		arlTabspace.add(tabspec);
	}
	private void switchTabInActivity(int indexTabToSwitchTo) {
		
	}
}
