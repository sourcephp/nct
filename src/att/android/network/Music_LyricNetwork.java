package att.android.network;

import java.io.IOException;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import att.android.util.ParseJSONMusic;

public class Music_LyricNetwork extends Thread {

	private Handler mHandler;
	private String mSongKey;

	public Music_LyricNetwork(Handler h, String k) {
		mHandler = h;
		mSongKey = k;
	}

	@Override
	public void run() {
		String url = URLProvider.getLyric(mSongKey);
		try {
			JSONObject json = JSONProvider.readJsonFromUrl(url);
			String lyric = ParseJSONMusic.parseLyric(json);

			Message msg = new Message();
			msg.obj = tokenizerString(lyric);
			mHandler.sendMessage(msg);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String tokenizerString(String str) {
		String lyric = str.replace("<br />", "\n");

		return lyric;

	}
}
