package att.android.network;

import java.util.ArrayList;

import org.openymsg.network.Session;
import org.openymsg.roster.Roster;

import android.os.Handler;
import android.os.Message;
import att.android.bean.Account;

public class ReadFullContactListNetwork implements Runnable{
	//TODO: Change something in here later
	private Handler mHandler;
public ReadFullContactListNetwork(Handler mHandler) {
	this.mHandler = mHandler;
}
public void run() {
	Account obj = new Account("Tester", "Dang thu nghiem", 1);
	ArrayList<Account> result = new ArrayList<Account>();
	result.add(obj);
	Message  msg = new Message();
	msg.what = 1;
	msg.obj = result;
	mHandler.sendMessage(msg);
}

}
