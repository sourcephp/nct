package att.android.util;

import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;
import att.android.bean.Music_Song;

public class ParseJSONMusic {

	public ArrayList<Music_Song> parseJSONHotMusic(JSONObject jsonObj) {
		ArrayList<Music_Song> musicList = new ArrayList<Music_Song>();
		JSONTokener token = new JSONTokener(jsonObj.toString());
		JSONArray jsonArray;
		try {
			jsonArray = (JSONArray) jsonObj.getJSONArray("Items");
			int i = 0;
			while (i < jsonArray.length()) {
				JSONObject localJSONObject = jsonArray.getJSONObject(i);
				Music_Song song = new Music_Song();
				song.name = localJSONObject.getString("Name");
				song.singer = localJSONObject.getString("Singer");
				song.songKey = localJSONObject.getString("SongKey");
				song.streamURL = localJSONObject.getString("Url");
				musicList.add(song);
				i++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return musicList;

	}

	public static String parseLyric(JSONObject paramJSONObject) {
		String localLyric;
		if (paramJSONObject != null)
			;
		try {
			localLyric = paramJSONObject.getString("Lyric");
			return localLyric;
		} catch (JSONException localJSONException) {
			while (true) {
				localJSONException.printStackTrace();
				localLyric = null;
			}
		}
	}

}
