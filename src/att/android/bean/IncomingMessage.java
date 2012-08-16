package att.android.bean;

public class IncomingMessage {
	String YMuserID;
	String msg;
	public IncomingMessage(String yMuserID, String msg) {
		super();
		YMuserID = yMuserID;
		this.msg = msg;
	}
	public String getYMuserID() {
		return YMuserID;
	}
	public void setYMuserID(String yMuserID) {
		YMuserID = yMuserID;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
