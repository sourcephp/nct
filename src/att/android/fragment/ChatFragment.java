package att.android.fragment;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.openymsg.network.Status;
import org.openymsg.network.YahooUser;
import org.openymsg.network.event.SessionEvent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import att.android.activity.MessengerFragmentActivity;
import att.android.bean.Conversation;
import att.android.bean.Saying;
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
	private LayoutInflater inflater;
	private LinearLayout formchat;
	private YahooUser YMuser;
	private String urlGetAvatar = "http://img.msg.yahoo.com/avatar.php?yids=";
	private Conversation conversation;
	private Conversation conversation_temp;

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
			onSendMessage(msg, 0);
		}
		if (v == btn_back) {
			((MessengerFragmentActivity) this.getActivity()).startFragment(1);
		}

	}

	private void onSendMessage(String msg, int type) {
		
		String YMuserID_friend = YMuser.getId();
		String YMUserID_me = singletonSession.getLoginID().getId();
		View layout = inflater.inflate(R.layout.chatbox_me, null);
		TextView me = (TextView) layout.findViewById(R.id.txt_chatbox_me);
		SmartImageView avt_me = (SmartImageView) layout.findViewById(R.id.real_avatar_me);
		avt_me.setImageUrl(urlGetAvatar+YMUserID_me+"&format=jpg");
		
		if (type!=1) {
			try {
				singletonSession.sendMessage(YMuserID_friend, msg);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (type != 1) {
			InputMethodManager imm = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(btn_send.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
		
		if (type != 1) {
		    conversation.updateConversation(YMUserID_me, msg, false);
		}
		
		me.setText(msg);
		formchat.addView(layout);
		scrollView.fullScroll(ScrollView.FOCUS_DOWN);
		scrollView.focusSearch(ScrollView.FOCUS_DOWN);
		
	}
	
	public void onReceiveMessage(SessionEvent event, int type) {
		if (conversations.size() == 0) {
			isFirstMessage = true;
		}
		if(!isFirstMessage){
			View layout = inflater.inflate(R.layout.chatbox_myfriend, null);
			TextView friends = (TextView) layout
					.findViewById(R.id.txt_chatbox_myfriend);
			SmartImageView avt_friends = (SmartImageView) layout
					.findViewById(R.id.real_avatar_friend);
			avt_friends.setImageUrl(urlGetAvatar+YMuser.getId()+"&format=jpg");
			
			friends.setText(event.getMessage());
			formchat.addView(layout);
			scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			scrollView.focusSearch(ScrollView.FOCUS_DOWN);
			int distance = scrollView.getBottom();
			scrollView.scrollBy(0, distance);
			isFirstMessage = false;
		}
	}

	@SuppressWarnings("unchecked")
	public void loadDataFromConversation(Conversation conversation) {
		List<Saying> chat = conversation.getConversation();
		for (int i = 0; i < chat.size(); i++) {
			Saying aSaying = chat.get(i);
			boolean key = aSaying.isReceived();
			if (key) {
				SessionEvent event = new SessionEvent(new Object(), singletonSession.getLoginID().getId(),
						conversation.getconversationID(), aSaying.getText_chat());
				onReceiveMessage(event, 1);
			} else {
				onSendMessage(aSaying.getText_chat(), 1);
			}
		}
	}

	public void onDataParameterData(YahooUser yahooUsers, Conversation conversation_received) {
		YMuser = yahooUsers;
		conversation = conversation_received;
		friends_name.setText(YMuser.getId());
		if (YMuser.getStatus().compareTo(Status.AVAILABLE) == 0) {
		    icon_status.setBackgroundResource(R.drawable.ic_yahoo_online);
		} else if (YMuser.getStatus().compareTo(Status.OFFLINE) == 0) {
		    icon_status.setBackgroundResource(R.drawable.ic_yahoo_offline);
		} else if (YMuser.getStatus().compareTo(Status.BUSY) == 0) {
		    icon_status.setBackgroundResource(R.drawable.ic_yahoo_busy);
		}
		
		formchat.removeAllViews();
		if(conversation !=null){
			//TODO: Nhan conversation tu ContactFragment load len UI
			Logger.e(TAG, "Load Conversation");
			loadDataFromConversation(conversation);
		} else {
			Logger.e(TAG, "Create Conversation");
			conversation = new Conversation(YMuser.getId());
			conversations.add(conversation);
		}
			
	
		
	}
	
}

