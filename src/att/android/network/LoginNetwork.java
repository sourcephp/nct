package att.android.network;

import android.os.Handler;
import android.os.Message;

public class LoginNetwork extends Thread {
	private String strAcc, strPass;
	private boolean hide;
	private boolean ok = true;
	private Handler handler;

	public LoginNetwork(String strAcc, String strPass, boolean hide, Handler h) {
		this.strAcc = strAcc;
		this.strPass = strPass;
		this.hide = hide;
		this.handler = h;
	}

	@Override
	public void run() {
		// send to server and receive result
		if (ok) {
			Message msg = new Message();
			msg.obj = ok;
			handler.sendMessage(msg);
		}
	}
}
