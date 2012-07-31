package att.android.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.multiapp.R;


public class ChatJointActivity extends Activity{
	private TabHost chatJointTabHost;
	private ArrayList<TabSpec> arlTabspace;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_chat);
	
//		chatJointTabHost = (TabHost) this.findViewById(android.R.id.tabhost);
//		arlTabspace = new ArrayList<TabHost.TabSpec>();
	}

//	@Override
//	public void onResume() {
//		super.onResume();
//		newChatTab("test");
//	}
//
//	private void newChatTab(String nameFriend) {
//		TabSpec tabspec = chatJointTabHost.newTabSpec(nameFriend);
//		tabspec.setIndicator(nameFriend);
//		Intent i = new Intent(this, ChatTabFragment.class);
//		tabspec.setContent(i);
//
//		chatJointTabHost.addTab(tabspec);
//
//		arlTabspace.add(tabspec);
//	}
}
