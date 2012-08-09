package att.android.network;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import att.android.bean.Music_Song;
import att.android.util.ParseJSONMusic;

public class Music_SearchSongNetwork implements Runnable {

	private Handler mHandler;
	private String mSongListUrl;
	private JSONObject json;
	private ArrayList<Music_Song> mSongList;
	private String key;

	public Music_SearchSongNetwork(Handler h, String key) {
		this.mHandler = h;
		this.key = key;
	}

	public void run() {
		this.mSongListUrl = URLProvider.getSearchData(1, key, "", 0, 50);
		try {
			json = JSONProvider.readJsonFromUrl(mSongListUrl);
			mSongList = new ParseJSONMusic().parseJSONHotMusic(json);
			Message msg = new Message();
			msg.what = 1;
			msg.obj = mSongList;
			mHandler.sendMessage(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
