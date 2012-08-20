package att.android.network;

import android.os.Handler;
import att.android.fragment.BaseMessengerFragment;
import att.android.model.YHandlerConstant;

public class RosterHandler extends Thread implements YHandlerConstant{
	Handler handler;

	public RosterHandler(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
	}

	public void run() {
		if (!BaseMessengerFragment.isCompletedRoster) {
			try {
				synchronized (BaseMessengerFragment.LoadCompleteRosterLock) {
					BaseMessengerFragment.LoadCompleteRosterLock.wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		handler.sendEmptyMessage(UPDATE_YAHOO_USER_FROM_ROSTER);
	}

}
