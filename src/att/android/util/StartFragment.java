package att.android.util;

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
public void startFragment1(ArrayList<Music_Song> item, int position,
		boolean bool);

/**This method for YahooMessenger function implements
 * @param item : an Array of YahooUser (org.openymsg.network.YahooUser)
 * @param position : destination fragment's position*/
public void startFragment2(ArrayList<YahooUser> item, int position,
		boolean bool);
}
