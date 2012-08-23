package att.android.bean;

public class Saying {
	private String user;

	private String text_chat;
	private boolean isReceived;

	public Saying(String user, String textChat, boolean isRecevice) {
		this.user = user;
		text_chat = textChat;
		this.isReceived = isRecevice;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText_chat() {
		return text_chat;
	}

	public void setText_chat(String textChat) {
		text_chat = textChat;
	}

	public boolean isReceived() {
		return isReceived;
	}

	public void setReceived(boolean isReceived) {
		this.isReceived = isReceived;
	}
}
