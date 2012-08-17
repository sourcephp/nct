package att.android.network;

import java.util.ArrayList;
import java.util.Iterator;

import org.openymsg.network.YahooUser;
import org.openymsg.roster.Roster;

import android.os.Handler;
import android.os.Message;
import att.android.fragment.BaseMessengerFragment;

public class ReadFullContactListNetwork extends BaseMessengerFragment implements Runnable {
	private Handler mHandler;

	public ReadFullContactListNetwork(Handler mHandler) {
		this.mHandler = mHandler;
		sessionListener = new YMEventHandler();
	}

	public void run() {

		singletonSession.addSessionListener(sessionListener);

		Roster roster = singletonSession.getRoster();
		ArrayList<YahooUser> alYahooUser = new ArrayList<YahooUser>();
		Iterator<YahooUser> iterator = roster.iterator();
		while (iterator.hasNext()) {
			YahooUser yahooUser = iterator.next();
			alYahooUser.add(yahooUser);
		}

		Message msg = new Message();
		msg.what = 1;
		msg.obj = alYahooUser;
		mHandler.sendMessage(msg);
	}

	@Override
	public void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initActions() {
		// TODO Auto-generated method stub
		
	}

}
