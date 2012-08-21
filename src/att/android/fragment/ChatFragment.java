package att.android.fragment;

import java.io.IOException;

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
import att.android.activity.MessengerFragmentActivity;
import att.android.model.Logger;
import att.android.model.OnYahooFragmentDataReceiver;

import com.example.multiapp.R;
import com.loopj.android.image.SmartImageView;

public class ChatFragment extends BaseMessengerFragment implements OnClickListener, OnYahooFragmentDataReceiver {
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
	private String YMuserID;
	private YahooUser YMuser;

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
	public void initVariables() {
		sessionListener = new YMEventHandler();
	}

	@Override
	public void initViews() {
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
	}

	@Override
	public void initActions() {
		btn_send.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		scrollView.setVerticalScrollBarEnabled(false);
	}

	@Override
	public void onResume() {
		super.onResume();
		singletonSession.addSessionListener(sessionListener);
	}

	public void onClick(View v) {
		if (v == btn_send) {
			String msg = edt_message.getText().toString();
			edt_message.setText("");
			sendMessage(msg);
		}
		if (v == btn_back) {
			((MessengerFragmentActivity) this.getActivity()).startFragment(1);
		}

	}

	private void sendMessage(String msg) {
		
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
		SmartImageView avt_me = (SmartImageView) layout.findViewById(R.id.real_avatar_me);
		avt_me.setImageUrl("http://img.msg.yahoo.com/avatar.php?yids="+singletonSession.getLoginID().getId()+"&format=jpg");
		me.setText(msg);
		formchat.addView(layout);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
		scrollView.focusSearch(ScrollView.FOCUS_DOWN);
	}
	
	@Override
	public void onMessageReceived(SessionEvent event) {
		super.onMessageReceived(event);
		
		Logger.e(TAG, event.getFrom()+": "+event.getMessage());
		
		View layout = inflater.inflate(R.layout.chatbox_myfriend, null);
		TextView friends = (TextView) layout
				.findViewById(R.id.txt_chatbox_myfriend);
		ImageView avt_friends = (ImageView) layout
				.findViewById(R.id.real_avatar_friend);
		if (null != YMuser.getDrawable()) {
			avt_friends.setBackgroundDrawable(YMuser.getDrawable());
		}
		friends.setText(event.getMessage());
		formchat.addView(layout);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
		scrollView.focusSearch(ScrollView.FOCUS_DOWN);
		int distance = scrollView.getBottom();
		scrollView.scrollBy(0, distance);
	}

	public void onDataParameterData(YahooUser yahooUsers) {
		YMuser = yahooUsers;
		friends_name.setText(YMuser.getId());
	}
	
}

