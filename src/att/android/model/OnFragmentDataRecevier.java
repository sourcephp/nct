package att.android.model;

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
	public void onDataParameterData(ArrayList<Music_Song> listSong,
			int position, boolean flag);
}
