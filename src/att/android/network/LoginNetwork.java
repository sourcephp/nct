package att.android.network;

public class LoginNetwork extends Thread {
	
	private String strAcc, strPass;
	boolean save, hide;
	public LoginNetwork(String strAcc, String strPass, boolean save,
			boolean hide) {
		this.strAcc = strAcc;
		this.strPass = strPass;
		this.save = save;
		this.hide = hide;
	}

	@Override
	public void run() {
		//send to server and receive result
		//then send the result to LoginActivity
	}
}
