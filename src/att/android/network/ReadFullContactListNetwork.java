package att.android.network;

import java.util.ArrayList;
import java.util.Iterator;

import org.openymsg.network.Session;
import org.openymsg.network.YahooUser;
import org.openymsg.roster.Roster;

import android.os.Handler;
import android.os.Message;
import att.android.model.YGeneralHandler;

public class ReadFullContactListNetwork implements Runnable{
	private Handler mHandler;
	Session instanceofSession = Session.getInstance();
	YGeneralHandler sessionListenerInstance = YGeneralHandler.getInstance();
public ReadFullContactListNetwork(Handler mHandler) {
	this.mHandler = mHandler;
}
public void run() {
	
	instanceofSession.addSessionListener(sessionListenerInstance);
	
	Roster roster = instanceofSession.getRoster();
	ArrayList<YahooUser> alYahooUser = new ArrayList<YahooUser>();
	Iterator<YahooUser> iterator = roster.iterator();
	while(iterator.hasNext()){
		YahooUser yahooUser = iterator.next();
		alYahooUser.add(yahooUser);
	}
	
	Message  msg = new Message();
	msg.what = 1;
	msg.obj = alYahooUser;
	mHandler.sendMessage(msg);
}

}
