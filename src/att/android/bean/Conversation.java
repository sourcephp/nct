package att.android.bean;

public class Conversation {
	public String From;
	public String To;
	public String Message;
	public boolean isReceived;
	
	public Conversation(String from, String to, String message,
			boolean isReceived) {
		From = from;
		To = to;
		Message = message;
		this.isReceived = isReceived;
	}
	
}
