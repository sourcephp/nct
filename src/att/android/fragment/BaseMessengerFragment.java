package att.android.fragment;

import java.util.ArrayList;

import org.openymsg.network.Session;
import org.openymsg.network.YahooGroup;
import org.openymsg.network.YahooUser;
import org.openymsg.network.event.SessionAdapter;
import org.openymsg.network.event.SessionEvent;
import org.openymsg.network.event.SessionListEvent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import att.android.model.Logger;
import att.android.model.YHandlerConstant;

public abstract class BaseMessengerFragment extends Fragment implements YHandlerConstant {
	private boolean didInit = false;
	public YMEventHandler sessionListener;
	public Session singletonSession = Session.getInstance();
	public static boolean isNeedUpdateFromRoster = false;
	private static final String TAG = "BaseMessengerFragment";
	public static Object LoadCompleteRosterLock = new Object();
	public static boolean isCompletedRoster = false;
	public static boolean isCompletedLoadData = false;
	public static Object LoadDataLock = new Object();
	public static Object LoadingData;
	public static Class currentClass;
	public static ArrayList<YahooUser> alYahooUser = new ArrayList<YahooUser>();
	protected static Object UpdateUILock = new Object();
	public static boolean settings_show_offlines = true;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i("MessengerFragment","onActivityCreated");
		setRetainInstance(true);
		if(didInit == false){
			didInit = true;
			initVariables();
		}
		initViews();
		initActions();
	}

	public abstract void initVariables();
	public abstract void initViews();
	public abstract void initActions();
	
	/**Override to handle incoming messages*/
	public void onMessageReceived(SessionEvent event){
		
	}
	
	/**Override if you need to load ContactList from Roster*/
	public void updateFullContactList() {
		
	}
	
	public Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
			case MESSAGE_RECEVICE:
				SessionEvent eventMESSAGE_RECEVICE = (SessionEvent) msg.obj;
				onMessageReceived(eventMESSAGE_RECEVICE);
				break;
			case RECEVICE_BUZZ:
				//TODO: Xu ly Buzz
				break;
			case UPDATE_YAHOO_USER_FROM_ROSTER:
				if (currentClass.equals(ContactFragment.class)) {
				    isNeedUpdateFromRoster  = false;
				    updateFullContactList();
				} else {
				    isNeedUpdateFromRoster = true;
				}
				break;
			case UPDATE_AVATAR:
				//TODO: xu ly Avatar
				break;
			case FRIEND_UPDATE_RECEVICE:
				//TODO: xu ly khi friend thay doi can cap nhat thay doi len UI
				break;
			}
		};
		
	};
	
	@SuppressLint("HandlerLeak")
	public class YMEventHandler extends SessionAdapter{

		@Override
		public void messageReceived(SessionEvent event) {
			super.messageReceived(event);
			Message message = new Message();
			message.obj = event;
			message.what = MESSAGE_RECEVICE;
			handler.sendMessage(message);
			Logger.e(TAG+"(YMEventHandler)", "messageReceived " + event.getFrom()
				    +": "+ event.getMessage());
		    }
		@Override
		public void listReceived(SessionListEvent event) {
			super.listReceived(event);
			Logger.e(TAG, "isCompletedLoadData = " + isCompletedLoadData);
		    if (!isCompletedLoadData) {
		    	 synchronized (LoadDataLock ) {
		    			isCompletedLoadData = true;
		    			LoadDataLock.notifyAll();
		    		    }
		    }
		}
		
	}

	
	
}
