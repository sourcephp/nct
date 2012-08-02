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

public class Music_SongListNetWork implements Runnable {

	private String mSongListUrl;
	private JSONObject json;
	ArrayList<Music_Song> mSongList;
	private Handler mHandler;

	public Music_SongListNetWork(Handler mHandler) {
		this.mHandler = mHandler;
	}

	public void run() {
		this.mSongListUrl = new URLProvider().getHotSongs(0, 66);
		try {
			json = new JSONProvider().readJsonFromUrl(mSongListUrl);
			mSongList = new ParseJSONMusic().parseJSON(json);
			Message msg = new Message();
			msg.what = 1;
			msg.obj = mSongList;
			mHandler.sendMessage(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
