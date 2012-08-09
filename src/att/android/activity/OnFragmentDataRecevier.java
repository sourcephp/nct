package att.android.activity;

import java.util.ArrayList;

import att.android.bean.Music_Song;

public interface OnFragmentDataRecevier {

	public void onDataParameterData(int index);

	public void onDataParameterData(ArrayList<Music_Song> listSong,
			int position, boolean bool);
}
