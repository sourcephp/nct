package att.android.activity;

import java.util.ArrayList;

import org.openymsg.network.Session;
import org.openymsg.network.Status;
import org.openymsg.network.YahooUser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import att.android.bean.Music_Song;
import att.android.model.YGeneralHandler;
import att.android.util.StartFragment;

import com.example.multiapp.R;

public class ChatFragment extends Fragment implements OnClickListener,
		StartFragment, OnFragmentDataRecevier {
	private ImageView icon_status;
	private Button btn_back;
	private Button btn_send;
	private EditText edt_message;
	private ScrollView scrollView;
	private TextView friends_name;
	private Status actionSttFriends;
	private String YMuserID;
	private YahooUser YMuser;
	private LayoutInflater inflater;
	private LinearLayout formchat;
	Session instanceofSession = Session.getInstance();
	YGeneralHandler sessionListenerInstance = YGeneralHandler.getInstance();

	public static Fragment newInstance(Context context) {
		ChatFragment f = new ChatFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.formchat, null);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initComponent();
		if (true) {
			receiveMessageProcess();
		}
	}

	private void initComponent() {
		inflater = this.getLayoutInflater(getArguments());
		// sttFriends = user.getStatus();
		formchat = (LinearLayout) this.getView().findViewById(
				R.id.formchat_content);
		icon_status = (ImageView) this.getView().findViewById(
				R.id.formchat_icon_status);
		friends_name = (TextView) this.getView().findViewById(
				R.id.formchat_friends_name);
		btn_send = (Button) this.getView().findViewById(R.id.formchat_btn_send);
		btn_back = (Button) this.getView().findViewById(R.id.formchat_btn_back);
		edt_message = (EditText) this.getView().findViewById(
				R.id.txt_formchat_message);
		scrollView = (ScrollView) this.getView().findViewById(
				R.id.formchat_scrollbar);
		scrollView.setVerticalScrollBarEnabled(false);
		btn_send.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		friends_name.setText("Somefriends here");
	}

	@Override
	public void onResume() {
		super.onResume();
		instanceofSession.addSessionListener(sessionListenerInstance);
	}

	public void onClick(View v) {
		if (v == btn_send) {
			String msg = edt_message.getText().toString();
			sendMessageProcess(msg);
		}
		if (v == btn_back) {
			((StartFragment) getActivity()).startFragment(1);
		}

	}

	private void sendMessageProcess(String msg) {
		View layout = inflater.inflate(R.layout.chatbox_me, null);
		TextView me = (TextView) layout.findViewById(R.id.txt_chatbox_me);
		ImageView avt_me = (ImageView) layout.findViewById(R.id.real_avatar_me);
		me.setText(msg);
		formchat.addView(layout);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
		scrollView.focusSearch(ScrollView.FOCUS_DOWN);
	}

	private void receiveMessageProcess() {
		View layout = inflater.inflate(R.layout.chatbox_myfriend, null);
		TextView friends = (TextView) layout
				.findViewById(R.id.txt_chatbox_myfriend);
		ImageView avt_friends = (ImageView) layout
				.findViewById(R.id.real_avatar_friend);
		friends.setText("hello!");
		formchat.addView(layout);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
		scrollView.focusSearch(ScrollView.FOCUS_DOWN);
		int distance = scrollView.getBottom();
		scrollView.scrollBy(0, distance);
	}

	public void startFragment(int i) {
		// TODO Auto-generated method stub

	}

	public void onDataParameterData(int index) {
		// TODO Auto-generated method stub

	}

	public void onDataParameterData(ArrayList<Music_Song> listSong,
			int position, boolean bool) {
		// TODO Auto-generated method stub

	}

	public void startFragment1(ArrayList<Music_Song> item, int position,
			boolean bool) {
		// TODO Auto-generated method stub
		
	}

	public void startFragment2(ArrayList<YahooUser> item, int position,
			boolean bool) {
		// TODO Auto-generated method stub
		
	}

	public void onDataParameterData1(ArrayList<Music_Song> listSong,
			int position, boolean bool) {
		// TODO Auto-generated method stub
		
	}

	public void onDataParameterData2(ArrayList<YahooUser> alYahooUsers,
			int position, boolean bool) {
		// TODO Auto-generated method stub
		
	}

	
}

// chatJointTabHost = (TabHost) this.findViewById(android.R.id.tabhost);
// arlTabspace = new ArrayList<TabHost.TabSpec>();

// @Override
// public void onResume() {
// super.onResume();
// newChatTab("test");
// }
//
// private void newChatTab(String nameFriend) {
// TabSpec tabspec = chatJointTabHost.newTabSpec(nameFriend);
// tabspec.setIndicator(nameFriend);
// Intent i = new Intent(this, ChatTabFragment.class);
// tabspec.setContent(i);
//
// chatJointTabHost.addTab(tabspec);
//
// arlTabspace.add(tabspec);
// }

