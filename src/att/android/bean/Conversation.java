package att.android.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openymsg.network.YahooUser;

import att.android.fragment.BaseMessengerFragment;
import att.android.fragment.ContactFragment;

public class Conversation {
	private boolean isRead = false;
	private String conversationID;
	private boolean isGuest = false; // Lac danh
	private Date timeCreate;
	private List<Saying> conversation = Collections
			.synchronizedList(new ArrayList<Saying>());
	
	/**type = 0 -> Create Conversation<br>
	 * type = 1 -> Message is sent by Me <br>
	 * type = 2 -> Message is sent by Friend<br>
	 */
	public Conversation(String userFrom, String userTo, String message, int type) {
		// TODO Auto-generated constructor stub
		if (type == 1) {
			conversationID = userTo;
			updateConversation(userFrom, message, false);
		} else if (type == 2) {
			conversationID = userFrom;
			updateConversation(userFrom, message, true);
		} else {
			conversationID = userTo;
		}
		YahooUser yahooUser = null;
		if (ContactFragment.mContactAdapter != null) {
			yahooUser = getYahooUser(conversationID);
		}
		if (yahooUser == null) {
			isGuest = true;
		}
	}

	public Conversation(String conversationID) {
		this.conversationID = conversationID;
		YahooUser yahooUser = null;
		if (ContactFragment.mContactAdapter != null) {
			yahooUser = getYahooUser(conversationID);
		}
		if (yahooUser == null) {
			isGuest = true;
		}
	}

	public void updateConversation(String user, String message,
			boolean isRecevice) {
		Saying aSaying = new Saying(user, message, isRecevice);
		conversation.add(aSaying);
	}

	public String getconversationID() {
		return conversationID;
	}

	@SuppressWarnings("unchecked")
	public List getConversation() {
		return conversation;
	}

	private YahooUser getYahooUser(String userID) {
		synchronized (BaseMessengerFragment.UpdateUILock) {
			int length = ContactFragment.mContactAdapter.getCount();
			YahooUser user = null;
			for (int i = 0; i < length; i++) {
				Object obj = ContactFragment.mContactAdapter.getItem(i);
				if (obj instanceof YahooUser) {
					YahooUser tmpUser = (YahooUser) obj;
					String id = tmpUser.getId();
					if (userID.equalsIgnoreCase(id)) {
						user = tmpUser;
						break;
					}
				}
			}
			return user;
		}
	}

	public String getLastest() {
		String text = "";
		int length = conversation.size();
		for (int i = length - 1; i >= 0; i--) {
			Saying aSaying = (Saying) conversation.get(i);
			if (aSaying.isReceived()) {
				text = aSaying.getText_chat();
				break;
			}
		}
		return text;
	}

	public boolean isGuest() {
		return isGuest;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}
