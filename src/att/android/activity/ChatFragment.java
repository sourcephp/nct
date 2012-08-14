package att.android.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.openymsg.network.Session;
import org.openymsg.network.Status;
import org.openymsg.network.YahooUser;
import org.openymsg.network.event.SessionEvent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import att.android.model.OnYahooFragmentDataReceiver;
import att.android.model.YGeneralHandler;

import com.example.multiapp.R;

public class ChatFragment extends Fragment implements OnClickListener, OnYahooFragmentDataReceiver {
	private static final String TAG = "ChatFragment";
	private ImageView icon_status;
	private Button btn_back;
	private Button btn_send;
	private EditText edt_message;
	private ScrollView scrollView;
	private TextView friends_name;
	private Status actionSttFriends;
	private LayoutInflater inflater;
	private LinearLayout formchat;
	Session singletonSession = Session.getInstance();
	YGeneralHandler singletonSessionListener = YGeneralHandler.getInstance();
	private String YMuserID;
	private YahooUser YMuser;
	private static boolean isFirstMessage;
	ArrayList<String> alMsg = new ArrayList<String>();

	public static Fragment newInstance(Context context) {
		ChatFragment f = new ChatFragment();

		return f;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MessengerFragmentActivity) activity).setDataListener(this);
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
		isFirstMessage = true;
		alMsg.add("");
	}

	@Override
	public void onResume() {
		super.onResume();
		singletonSession.addSessionListener(singletonSessionListener);
	}

	public void onClick(View v) {
		if (v == btn_send) {
			String msg = edt_message.getText().toString();
			edt_message.setText("");
			sendMessageProcess(msg);
			
		}
		if (v == btn_back) {
			((MessengerFragmentActivity) this.getActivity()).startFragment(1);
		}

	}

	private void sendMessageProcess(String msg) {
		
		YMuserID = YMuser.getId();
		Log.i(TAG, YMuserID);
		
		try {
			singletonSession.sendMessage(YMuserID, msg);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		View layout = inflater.inflate(R.layout.chatbox_me, null);
		TextView me = (TextView) layout.findViewById(R.id.txt_chatbox_me);
		ImageView avt_me = (ImageView) layout.findViewById(R.id.real_avatar_me);
		YahooUser userMe = new YahooUser(singletonSession.getLoginID().getId());
		if (userMe.getDrawable() != null) {
			avt_me.setBackgroundDrawable(userMe.getDrawable());
		}
		me.setText(msg);
		formchat.addView(layout);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
		scrollView.focusSearch(ScrollView.FOCUS_DOWN);
	}

	private void receiveMessageProcess(SessionEvent event) {
		View layout = inflater.inflate(R.layout.chatbox_myfriend, null);
		TextView friends = (TextView) layout
				.findViewById(R.id.txt_chatbox_myfriend);
		ImageView avt_friends = (ImageView) layout
				.findViewById(R.id.real_avatar_friend);

		if (YMuser.getDrawable() != null) {
			avt_friends.setBackgroundDrawable(YMuser.getDrawable());
		}
		friends.setText(event.getMessage());
		formchat.addView(layout);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
		scrollView.focusSearch(ScrollView.FOCUS_DOWN);
		int distance = scrollView.getBottom();
		scrollView.scrollBy(0, distance);
		
		if (alMsg.size() <= 2) alMsg.add(event.getMessage());
		else alMsg.remove(0);
//		keepReceivingMessage(event);
	}

//	private void keepReceivingMessage(SessionEvent event) {
//		alMsg.add(event.getMessage());
//		saveMessage();
//		
//	}
//
//	private String saveMessage() {
//		// TODO Auto-generated method stub
//		String msg =null;
//		return alMsg.get(0);
//	}

	public void onDataParameterData(YahooUser yahooUsers) {
		YMuser = yahooUsers;
		SessionEvent event = new SessionEvent(new Object(), singletonSession
				.getLoginID().getId(), YMuser.getId(), alMsg.get(0));
		receiveMessageProcess(event);
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

