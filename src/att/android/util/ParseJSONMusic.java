package att.android.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;
import att.android.bean.Music_Song;

public class ParseJSONMusic {

	public ArrayList<Music_Song> parseJSON(JSONObject jsonObj) {
		ArrayList<Music_Song> musicList = new ArrayList<Music_Song>();
		JSONTokener token = new JSONTokener(jsonObj.toString());
		JSONArray jsonArray;
		try {
			while (true) {
				jsonObj = (JSONObject) token.nextValue();
				jsonArray = (JSONArray) jsonObj.getJSONArray("Items");
				int i = 0;
				if (i < jsonArray.length()) {
					JSONObject localJSONObject = jsonArray.getJSONObject(i);
					Music_Song song = new Music_Song();
					song.name = localJSONObject.getString("Name");
					song.singer = localJSONObject.getString("Singer");
					song.streamURL = localJSONObject.getString("Url");
					musicList.add(song);
					i++;
				}
				if (!jsonObj.get("GetMore").equals("yes"))
					break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return musicList;

	}
}
