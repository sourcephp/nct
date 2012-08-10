package att.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.multiapp.R;


public class ChatFragment extends Fragment{
	private ListView listMessage;
	
	public static Fragment newInstance(Context context) {
		ChatFragment f = new ChatFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.formchat,
				null);
		return root;
	}
	
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

