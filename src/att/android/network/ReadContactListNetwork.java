package att.android.network;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;
import att.android.bean.Account;
import att.android.bean.News;
import att.android.util.ParseXMLRss;

public class ReadContactListNetwork implements Runnable{
	private Handler mHandler;
public ReadContactListNetwork(Handler mHandler) {
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
