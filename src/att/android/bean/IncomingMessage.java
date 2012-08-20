package att.android.bean;

public class IncomingMessage {
	private String From;
	private String Message;
	
	private static IncomingMessage instance = null;
	protected IncomingMessage() {
	      // Exists only to defeat instantiation.
	   }
	   public static IncomingMessage getInstance() {
	      if(instance == null) {
	         instance = new IncomingMessage();
	      }
	      return instance;
	   }
	
	public void SetIncomingMessage(String from, String message) {
		From = from;
		Message = message;
	}
	
	public boolean isOnChat(){
		if(Message==null) {
			return false;
		}else return true;
	}

	public String getFrom() {
		return From;
	}

	public void setFrom(String from) {
		From = from;
	}


	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
	//TODO: after 30min, set the instance = null
	
}
