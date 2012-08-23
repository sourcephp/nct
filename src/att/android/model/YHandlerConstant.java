package att.android.model;


public interface YHandlerConstant {    
    final int LOGIN 						= 1; //Action Login  
    final int LOGOUT 						= 2; //Action Logout
    final int SEND_MESSAGE					= 3; //Action SendMessage default. Send message to other person. Not room
    final int SEND_MESSAGE_WITH_IDENTITY			= 4; //Action SendMessage with alias.
    final int SEND_BUZZ						= 5; // Action send Buzz	
    final int SEND_BUZZ_WITH_IDENTITY				= 6; //Action sendBuz with Identity
    final int SET_STATUS 					= 7;// Sets the status of the Yahoo user.
    final int SET_STATUS_CUSTOM 				= 8;//Sets the status of the Yahoo user using custom status.
    final int LIST 						= 9;
    final int BUZZ_RECEIVED					= 10;
    final int MESSAGE_RECEIVED				= 11;
    final int FRIEND_UPDATE_RECEIVED				= 12;
    final int NOTIFY_CHANGE_CONVERSATION_LIST					= 13;
    
    final int ACTION_ADD_CONVERSATION					= 14;
    final int ACTION_REMOVE_CONVERSATION				= 15;
    
    final int UPDATE_LIST_CURRENT				= 16;
    static final String STR_SETTING = "SETTINGS REFERENCES";
    final int UPDATE_AVATAR					=17;
    final int DISMISS_DIALOG = 600;
    final int UPDATE_YAHOO_USER_FROM_ROSTER = 601;
}
