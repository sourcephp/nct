package att.android.activity;

import java.util.ArrayList;

import org.openymsg.network.YahooUser;

import att.android.bean.Music_Song;

public interface OnFragmentDataRecevier {
	
	/**
	 * 
	 * @param index : data receiving fragment's position
	 */
	public void onDataParameterData(int index);
	
	/**This method for music function implements
	 * @param listSong : an Array of Music_Song (att.android.bean.Music_Song)
	 * @param position : data receiving fragment's position*/
	public void onDataParameterData1(ArrayList<Music_Song> listSong,
			int position, boolean flag);
	
	/**This method for YahooMessenger function implements
	 * @param alYahooUsers : an Array of YahooUser (org.openymsg.network.YahooUser)
	 * @param position : data receiving fragment's position*/
	public void onDataParameterData2(ArrayList<YahooUser> alYahooUsers,
			int position, boolean flag);
}
