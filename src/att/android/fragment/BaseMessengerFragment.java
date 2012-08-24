package att.android.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openymsg.network.Session;
import org.openymsg.network.Status;
import org.openymsg.network.YahooUser;
import org.openymsg.network.event.SessionAdapter;
import org.openymsg.network.event.SessionEvent;
import org.openymsg.network.event.SessionFriendEvent;
import org.openymsg.network.event.SessionListEvent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import att.android.bean.Conversation;
import att.android.model.Logger;
import att.android.model.YHandlerConstant;

public abstract class BaseMessengerFragment extends Fragment implements
		YHandlerConstant {
	private boolean didInit = false;
	private static final String TAG = "BaseMessengerFragment";
	public static Class currentClass;

	public YMEventHandler sessionListener;
	public static Session singletonSession = new Session();

	// some locks below to synchronize data flow in somewhere
	public static Object LoadCompleteRosterLock = new Object();
	public static Object LoadDataLock = new Object();
	public static Object LoadingData;
	public static Object UpdateUILock = new Object();

	// Flag to check condition to do something
	public static boolean settings_show_offlines = false;//change later
	public static boolean isNeedUpdateFromRoster = false;
	public static boolean isCompletedRoster = false;
	public static boolean isCompletedLoadData = false;
	public static boolean isFirstMessage = false;

	public static ArrayList<YahooUser> alYahooUser = new ArrayList<YahooUser>();
	public static Set<Conversation> conversations = Collections.synchronizedSet(new HashSet<Conversation>());

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i("MessengerFragment", "onActivityCreated");
		setRetainInstance(true);
		if (didInit == false) {
			didInit = true;
			initVariables();
		}
		initViews();
		initActions();
	}

	public abstract void initVariables();

	public abstract void initViews();

	public abstract void initActions();

	/** Override if you need to load ContactList from Roster */
	public void updateFullContactList() {

	}
	
	public void loadContactToList() {
		
	}
	

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_RECEIVED:
				SessionEvent eventMESSAGE_RECEVICE = (SessionEvent) msg.obj;
				onReceiveMessage(eventMESSAGE_RECEVICE, 0);
				break;
			case BUZZ_RECEIVED:
				// TODO: Xu ly Buzz
				break;
			case UPDATE_YAHOO_USER_FROM_ROSTER:
				if (currentClass.equals(ContactFragment.class)) {
					isNeedUpdateFromRoster = false;
					updateFullContactList();
				} else {
					isNeedUpdateFromRoster = true;
				}
				break;
			case FRIEND_UPDATE_RECEIVED:
//				Logger.e(TAG, "thay doi trang thai");
				SessionFriendEvent event = (SessionFriendEvent) msg.obj;
//				ContactFragment.mContactAdapter.clear();
//				updateFullContactList();
				if (!currentClass.equals(ContactFragment.class)) {
				    ContactFragment.isFriendUpdate = true;
				}
				onReceiveFriendUpdate(event);
				break;
			}
		}
	};
	
	/**When users have incoming message, add the message to the conversation*/
	public void onReceiveMessage(final SessionEvent event, int type) {
		Thread thread = new Thread() {
			String currentConversationID;
			boolean isExist = false;

			public void run() {
				String message = event.getMessage();
				String userFrom = event.getFrom();
				String userTo = event.getTo();
				Logger.e(TAG, userFrom + ": " + message);
				
				if (conversations.size() == 0) {
					isFirstMessage = true;
					addConversation(userFrom, userTo, message, 2);
				} else {
					isFirstMessage = false;
					currentConversationID = userFrom;
					for (Iterator<Conversation> iterator = conversations
							.iterator(); iterator.hasNext();) {
						Conversation iconversation = (Conversation) iterator.next();
						String conversationID = iconversation.getconversationID();
						if (currentConversationID.equalsIgnoreCase(conversationID)) {
							iconversation.updateConversation(conversationID, message, true);
							iconversation.setRead(false);
							isExist = true;
							break;
						}
					}
					if (!isExist) {
						addConversation(userFrom, userTo, message, 2);
					} else {
//						checkVibrator(userFrom);
					}
				}
			}
		};
		thread.start();
	}

	public void onReceiveFriendUpdate(SessionFriendEvent event) {

		if (currentClass.equals(ContactFragment.class) == true) {
			// Kiem tra xem user cap nhat co ton tai trong adapter khong
			int index = 0;
			int length = ContactFragment.mContactAdapter.getCount();
			for (int i = 0; i < length; i++) {
				YahooUser user = ContactFragment.mContactAdapter.getItem(i);
				String id = user.getId();
				if (event.getUser().getId().equalsIgnoreCase(id)) {
					index = i;
					break;

				}
			}
			// Neu ton tai trong adapter thi kiem tra dieu kien on/off
			synchronized (UpdateUILock) {
				if (index != 0) {
					YahooUser user = (YahooUser) ContactFragment.mContactAdapter
							.getItem(index);
					Status userUpdate = event.getUser().getStatus();
					// Neu ton tai va dang de dieu kien la on
					if (!settings_show_offlines) {
						// Kiem tra tiep xem user nay on hay off
						if (userUpdate.compareTo(Status.AVAILABLE) == 0
								|| userUpdate.compareTo(Status.BUSY) == 0
								|| userUpdate.compareTo(Status.CUSTOM) == 0) {
							user.update(userUpdate);
							user.setCustom(event.getUser()
									.getCustomStatusMessage(), event.getUser()
									.getCustomStatus());
							ContactFragment.mContactAdapter
									.notifyDataSetChanged();
						} else {
							ContactFragment.mContactAdapter.remove(user);
							ContactFragment.mContactAdapter
									.setNotifyOnChange(true);
						}
					} else {
						user.update(userUpdate);
						user.setCustom(
								event.getUser().getCustomStatusMessage(), event
										.getUser().getCustomStatus());
						ContactFragment.mContactAdapter.notifyDataSetChanged();
					}
				} else {
					ContactFragment.mContactAdapter.clear();
					ContactFragment.mContactAdapter.setNotifyOnChange(true);
					loadContactToList();
				}
			}
		}
	}

	

	public Conversation addConversation(String userFrom, String userTo, String message, int type) {
		Conversation conversation = new Conversation(userFrom, userTo, message, type);
		conversations.add(conversation);
		return conversation;
	}

	@SuppressLint("HandlerLeak")
	public class YMEventHandler extends SessionAdapter {

		@Override
		public void messageReceived(SessionEvent event) {
			super.messageReceived(event);
			Message message = new Message();
			message.obj = event;
			message.what = MESSAGE_RECEIVED;
			handler.sendMessage(message);
//			Logger.e(TAG + "(YMEventHandler)","messageReceived " + event.getFrom() + ": "+ event.getMessage());
			
		}

		@Override
		public void listReceived(SessionListEvent event) {
			super.listReceived(event);
			Logger.e(TAG, "isCompletedLoadData = " + isCompletedLoadData);
			if (!isCompletedLoadData) {
				synchronized (LoadDataLock) {
					isCompletedLoadData = true;
					LoadDataLock.notifyAll();
				}
			}
		}
		
		@Override
		public void friendsUpdateReceived(SessionFriendEvent event) {
			super.friendsUpdateReceived(event);
			Message message = new Message();
		    message.obj = event;
		    message.what = FRIEND_UPDATE_RECEIVED;
		    handler.sendMessage(message);
		}

	}


}
