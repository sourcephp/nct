package att.android.service;

import java.util.Iterator;

import org.openymsg.network.event.SessionAdapter;
import org.openymsg.network.event.SessionEvent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import att.android.bean.Conversation;
import att.android.fragment.BaseMessengerFragment;
import att.android.fragment.ChatFragment;
import att.android.model.Logger;

public class YHMessageService extends Service {
    Vibrator vibrator;

    @Override
    public void onCreate() {
	// TODO Auto-generated method stu
	super.onCreate();
	BaseMessengerFragment.singletonSession.addSessionListener(new YaDroidListener());
	vibrator = (Vibrator) YHMessageService.this.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	return mBinder;
    }

    /**
     * Class for clients to access. Because we know this service always runs in
     * the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
	public YHMessageService getService() {
	    return YHMessageService.this;
	}
    }

    // This is the object that receives interactions from clients. See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    private class YaDroidListener extends SessionAdapter {
	protected static final String TAG = "YaDroidService";

	@Override
		public void messageReceived(final SessionEvent event) {
			// TODO Auto-generated method stub
			if (BaseMessengerFragment.isSleeping == false) {
				return;
			}
			vibrator.vibrate(300);
			Thread thread = new Thread() {
				boolean isExist = false;
				String currentConversationID;

				public void run() {
					String message = event.getMessage();
					String userFrom = event.getFrom();
					String userTo = event.getTo();
					Logger.e(TAG, userFrom + ": " + message);
					if (BaseMessengerFragment.conversations.size() == 0) {
						BaseMessengerFragment.isFirstMessage = true;
						addConversation(userFrom, userTo, message, 2);
					} else {
						BaseMessengerFragment.isFirstMessage = false;
						currentConversationID = userFrom;
						for (Iterator<Conversation> iterator = BaseMessengerFragment.conversations
								.iterator(); iterator.hasNext();) {
							Conversation iconversation = (Conversation) iterator
									.next();
							String conversationID = iconversation
									.getconversationID();
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

						}
					}
				}
			};
			thread.start();
		}

	    public Conversation addConversation(String userFrom, String userTo, String message, int type) {
			Conversation conversation = new Conversation(userFrom, userTo, message, type);
			BaseMessengerFragment.conversations.add(conversation);
			return conversation;
		}

	@Override
	public void buzzReceived(SessionEvent event) {
	    // TODO Auto-generated method stub
	    super.buzzReceived(event);
//	    Toast.makeText(YaDroidService.this,
//		    "Message from Service = " + event.getMessage(),
//		    Toast.LENGTH_SHORT).show();
	}
    }
}
