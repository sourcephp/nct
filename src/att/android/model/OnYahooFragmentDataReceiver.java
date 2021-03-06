package att.android.model;

import org.openymsg.network.YahooUser;

import att.android.bean.Conversation;

public interface OnYahooFragmentDataReceiver {

	
	/**This method for YahooMessenger function implements
	 * @param alYahooUsers : an Array of YahooUser (org.openymsg.network.YahooUser)
	 * @param position : data receiving fragment's position*/
	public void onDataParameterData(YahooUser yahooUsers, Conversation conversation_received);
}
