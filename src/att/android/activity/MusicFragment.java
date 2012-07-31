package att.android.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.multiapp.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MusicFragment extends Fragment implements OnClickListener {

	private Button mBtnPlay;

	public static Fragment newInstance(Context context) {
		MusicFragment f = new MusicFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.music_fragment,
				null);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mBtnPlay = (Button) this.getView().findViewById(R.id.btn_play);
		mBtnPlay.setOnClickListener(this);
	}

	private String text;
	private MediaPlayer mplay;

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == mBtnPlay) {
			String url = getHotSongs(0, 5);
			try {
				JSONObject json = readJsonFromUrl(url);
				text = parseJSON(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mplay = new MediaPlayer();
			try {
				mplay.setDataSource(text);
				mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mplay.prepare();
				mplay.start();

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	// method lấy JSONOject từ URL cho trc
	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			Log.i("readJSONfromURL", json.toString());
			return json;
		} finally {
			is.close();
		}
	}

	/*
	 * public static void main(String[] args) throws IOException, JSONException
	 * { JSONObject json =
	 * readJsonFromUrl("https://graph.facebook.com/19292868552");
	 * System.out.println(json.toString()); System.out.println(json.get("id"));
	 * }
	 */

	// method trả về URL
	public static String getHotSongs(int paramInt1, int paramInt2) {
		String str2 = "69f54e21b5a3e77b4b91c95ac1a2c37e", str1;
		while (true) {
			try {
				String str3 = new StringBuilder(
						String.valueOf(new StringBuilder(
								String.valueOf(new StringBuilder(
										String.valueOf(new StringBuilder(
												String.valueOf(new StringBuilder(
														String.valueOf("http://api.m.nhaccuatui.com/v4/api/song?"))
														.append("secretkey=nct@mobile_service")
														.toString())).append(
												"&action=getHotSong")
												.toString()))
										.append("&pageindex=")
										.append(String.valueOf(paramInt1))
										.toString())).append("&pagesize=")
								.append(String.valueOf(paramInt2)).toString()))
						+ "&token=" + str2;
				str1 = str3;
				Log.i("getHotSongs", str3);
				return str1;
			} catch (Exception localException) {
				localException.printStackTrace();
			}
			str1 = null;
		}
	}

	public String parseJSON(JSONObject jsonObj) {
		JSONTokener token = new JSONTokener(jsonObj.toString());
		JSONObject object;
		JSONArray array;
		String songId = null;
		try {
			object = (JSONObject) token.nextValue();
			/*
			 * String getMore = object.getString("GetMore"); //key "GetMore" co
			 * value kieu int int totalRecords = object.getInt("TotalRecords");
			 * //key "TotalRecords" co value kieu int
			 */
			array = (JSONArray) object.getJSONArray("Items");// key "Items" co
																// value la mot
																// mang cac doi
																// tuong JSON
																// cung cau truc
			JSONObject item1 = array.getJSONObject(0);
			songId = item1.getString("Url");
			Log.i("parseJSON", songId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return songId;

	}
}
