package att.android.fragment;

import org.openymsg.network.Session;
import org.openymsg.network.event.SessionAdapter;
import org.openymsg.network.event.SessionEvent;

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
	private static final String TAG = "BaseMessengerFragment";
	
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
	
	public Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
			case MESSAGE_RECEVICE:
				SessionEvent eventMESSAGE_RECEVICE = (SessionEvent) msg.obj;
				onMessageReceived(eventMESSAGE_RECEVICE);
				break;
			case RECEVICE_BUZZ:
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
	}

	
	
}
