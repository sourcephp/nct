package att.android.activity;

import java.util.ArrayList;

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


public class ChatJointFragment extends Fragment{
	private TabHost chatJointTabHost;
	private ArrayList<TabSpec> arlTabspace;

	public static Fragment newInstance(Context context) {
		ChatJointFragment f = new ChatJointFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_chat,
				null);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		chatJointTabHost = (TabHost) this.getView().findViewById(android.R.id.tabhost);
		arlTabspace = new ArrayList<TabHost.TabSpec>();
	}

	@Override
	public void onResume() {
		super.onResume();
		newChatTab("test");
	}

	private void newChatTab(String nameFriend) {
		TabSpec tabspec = chatJointTabHost.newTabSpec(nameFriend);
		tabspec.setIndicator(nameFriend);
		Intent i = new Intent(this.getActivity(), ChatTabFragment.class);
		tabspec.setContent(i);

		chatJointTabHost.addTab(tabspec);

		arlTabspace.add(tabspec);
	}
}
