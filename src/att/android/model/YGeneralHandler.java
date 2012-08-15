package att.android.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.openymsg.network.Session;
import org.openymsg.network.YahooGroup;
import org.openymsg.network.YahooUser;
import org.openymsg.network.event.SessionAdapter;
import org.openymsg.network.event.SessionChatEvent;
import org.openymsg.network.event.SessionEvent;
import org.openymsg.network.event.SessionFriendEvent;
import org.openymsg.network.event.SessionListEvent;
import org.openymsg.roster.Roster;

import android.content.Context;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;

import att.android.model.Logger;

public class YGeneralHandler extends SessionAdapter {
	
	
	
	public static YGeneralHandler sessionListener = null;
	
	protected YGeneralHandler(){
		
	}
	
	public synchronized static YGeneralHandler getInstance(){
		if(sessionListener == null) {
			sessionListener = new YGeneralHandler();
	      }
		return sessionListener;
		
	}
	
}