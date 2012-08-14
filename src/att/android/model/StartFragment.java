package att.android.model;

import java.util.ArrayList;

import org.openymsg.network.YahooUser;

import att.android.bean.Music_Song;

public interface StartFragment {

/**
 * 
 * @param i : destination fragment's position
 */
public void startFragment(int i);

/**This method for Music function implements
 * @param item : an Array of Music_Song (att.android.bean.Music_Song)
 * @param position : destination fragment's position*/
public void startFragment(ArrayList<Music_Song> item, int position,
		boolean flag);

/**This method for YahooMessenger function implements
 * @param yahooUser : a YahooUser object (org.openymsg.network.YahooUser)
 * @param position : destination fragment's position*/
public void startFragment(YahooUser yahooUser, int position);
}
