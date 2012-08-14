package att.android.activity;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.openymsg.network.YahooUser;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import att.android.bean.Music_Song;
import att.android.model.OnFragmentDataRecevier;
import att.android.network.JSONProvider;
import att.android.network.Music_LyricNetwork;
import att.android.network.URLProvider;
import att.android.receiver.PhoneReceiver;
import att.android.receiver.PhoneReceiver.OnIncommingCall;
import att.android.util.ParseJSONMusic;

import com.example.multiapp.R;

public class LyricFragment extends BaseFragment implements
		OnFragmentDataRecevier, OnClickListener, OnSeekBarChangeListener,
		OnIncommingCall {

	private String streamUrl;
	private String mLyric = "";
	private ArrayList<Music_Song> mSongList;
	private Button mBtnPlay;
	private Button mBtnRepeat;
	private boolean isRepeat;
	private MediaPlayer mplay;
	private TextView songName;
	private SeekBar mSeekBar;
	private TextView txtLyric;
	private int currentTime;
	private View mBtnPre;
	private View mBtnNext;
	private String mSongName = "Hiện chưa có bài hát nào được chọn";
	private int instanceIndex = -1;
	private RunMusic mPlayMusic;
	private int count = 1;
	private View mBtnAddSong;
	private String urlLyric;
	private boolean isPause;
	private JSONObject json;
	private PhoneReceiver broadcast;
	private String mSongKey;
	private final static String NOTES = "notes.txt";

	public static Fragment newInstance(Context context) {
		LyricFragment f = new LyricFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.lyric_fragment,
				null);
		return root;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MusicFragmentActivity) activity).setDataListener(this);
	}

	@Override
	public void initVariables() {
		mplay = new MediaPlayer();
		currentTime = 0;
		isRepeat = false;
	}

	@Override
	public void initViews() {
		mBtnAddSong = this.getView().findViewById(R.id.btn_love_song);
		mBtnPre = this.getView().findViewById(R.id.btn_backward);
		mBtnNext = this.getView().findViewById(R.id.btn_forward);
		mBtnRepeat = (Button) this.getView().findViewById(R.id.btn_repeat);
		mBtnPlay = (Button) this.getView().findViewById(R.id.btn_play);
		songName = (TextView) this.getView().findViewById(R.id.txt_song_name);
		mSeekBar = (SeekBar) this.getView().findViewById(R.id.seekBar1);
		txtLyric = (TextView) this.getView().findViewById(R.id.txt_lyric);
	}

	@Override
	public void initActions() {
		mBtnAddSong.setOnClickListener(this);
		mBtnPre.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);
		mBtnRepeat.setOnClickListener(this);
		mSeekBar.setProgress(0);
		mSeekBar.setEnabled(false);
		mSeekBar.setOnSeekBarChangeListener(this);
		mBtnPlay.setOnClickListener(this);
		songName.setText(mSongName);
		songName.setSelected(true);
	}

	public void turnOnBroadcast() {
		broadcast = new PhoneReceiver(this);
		IntentFilter filter = new IntentFilter(
				TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		getActivity().registerReceiver(broadcast, filter);
	}

	@Override
	public void onResume() {
		super.onResume();
		turnOnBroadcast();
	}

	@Override
	public void onStop() {
		super.onStop();
		getActivity().unregisterReceiver(broadcast);
	}

	public void onClick(View v) {
		if (v == mBtnRepeat) {
			if (isRepeat) {
				isRepeat = false;
				mBtnRepeat.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_repeat_off));
			} else {
				isRepeat = true;
				mBtnRepeat.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_repeat_on));
			}
		} else if (count > 1) {

			if (v == mBtnPlay) {
				if (mplay.isPlaying()) {
					mplay.pause();
					mBtnPlay.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_play));
					isPause = true;
				} else {
					mplay.start();
					mBtnPlay.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.btn_pause));
					isPause = false;
				}
			}

			if (v == mBtnNext && (instanceIndex < (mSongList.size() - 1))
					&& mSongList.size() > 0) {
				mBtnNext.setEnabled(false);
				mBtnPre.setEnabled(false);
				instanceIndex++;
				doManyTimes(mSongList.get(instanceIndex));
				changeRunMusic();

			}
			if (v == mBtnPre && instanceIndex >= 1 && mSongList.size() > 0) {
				instanceIndex--;
				mBtnNext.setEnabled(false);
				mBtnPre.setEnabled(false);
				doManyTimes(mSongList.get(instanceIndex));
				changeRunMusic();

			}
		}
		if (v == mBtnAddSong) {
			try {
				File file = getActivity().getFileStreamPath(NOTES);
				StringBuilder buf = new StringBuilder("");
				if (file.exists()) {
					InputStream in = getActivity().openFileInput(NOTES);

					if (in != null) {
						InputStreamReader tmp = new InputStreamReader(in);
						BufferedReader reader = new BufferedReader(tmp);
						String str1;

						while ((str1 = reader.readLine()) != null) {
							buf.append(str1 + "\n");
						}

						in.close();
					}
				}

				OutputStreamWriter out = new OutputStreamWriter(getActivity()
						.openFileOutput(NOTES, 0));
				String str2 = buf + mSongList.get(instanceIndex).name + "Ã¢â€¢Â¥"
						+ mSongList.get(instanceIndex).singer + "Ã¢â€¢Â¥"
						+ mSongList.get(instanceIndex).songKey + "Ã¢â€¢Â¥"
						+ mSongList.get(instanceIndex).streamURL;

				out.write(str2);
				out.close();
			} catch (Throwable t) {
				Log.w("Exc", "Exception: " + t.toString());
			}
			Toast.makeText(getActivity(), "Added ", Toast.LENGTH_LONG).show();
		}

	}

	private void changeRunMusic() {
		if (1 == count) {
			mPlayMusic = new RunMusic();
			mSeekBar.setEnabled(true);
			mPlayMusic.execute();
			count++;
		} else {
			mPlayMusic.cancel(true);
			playMusic();
		}
	}

	private void playMusic() {

		mPlayMusic = new RunMusic();
		mPlayMusic.execute();
	}

	private void doManyTimes(Music_Song item) {

		mplay.reset();
		mSeekBar.setProgress(0);
		streamUrl = item.streamURL;
		mSongKey = item.songKey;
		mSongName = item.name + " --- " + item.singer;
		songName.setText(mSongName);
	}

	private class RunMusic extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... in) {
			try {
				urlLyric = URLProvider.getLyric(mSongKey);
				json = null;
				try {
					json = JSONProvider.readJsonFromUrl(urlLyric);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				mLyric = ParseJSONMusic.parseLyric(json);
				mLyric = mLyric.replace("<br />", "\n");
				mplay.setDataSource(streamUrl);
				mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mplay.prepare();
				mplay.start();

				Log.i("time", "" + mplay.getDuration());

				for (; 0 < 1;) {
					publishProgress();
					// Log.i("time", "" + mplay.getCurrentPosition()
					// + "------------" + mplay.getDuration());
					if (isCancelled())
						break;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Log.i("doIn", "het vong for");

			} catch (IllegalArgumentException e) {
				Log.e("LyricFragment", "IllegalArgumentException");
			} catch (IllegalStateException e) {
				Log.e("LyricFragment", "IllegalStateException");
			} catch (IOException e) {
				Log.e("LyricFragment", "IOException");
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			if (!mBtnNext.isEnabled()) {
				mBtnNext.setEnabled(true);
				mBtnPre.setEnabled(true);
			}
			mSeekBar.setMax(mplay.getDuration());
			if (mplay.isPlaying()) {
				currentTime = mplay.getCurrentPosition();
			} else if (!isPause) {
				if (isRepeat) {
					mplay.seekTo(0);
					mplay.start();
				} else if (instanceIndex < (mSongList.size() - 1)) {
					instanceIndex++;
					doManyTimes(mSongList.get(instanceIndex));
					changeRunMusic();
				}
			}
			mSeekBar.setProgress(currentTime);
			if (mLyric.equals("")) {
				txtLyric.setText("Hiá»‡n chÆ°a cÃ³ lá»�i cho bÃ i hÃ¡t nÃ y");
			} else {
				txtLyric.setText(mLyric);
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}
	}

	public void onDataParameterData(int index) {
		instanceIndex = index;
		doManyTimes(mSongList.get(index));
		changeRunMusic();
	}

	public void onDataParameterData(ArrayList<Music_Song> listSong,
			int position, boolean bool) {
		instanceIndex = position;
		mSongList = listSong;
		doManyTimes(mSongList.get(position));
		changeRunMusic();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (null != mplay) {
			mplay.stop();
		}
		mPlayMusic.cancel(true);
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (fromUser) {
			mplay.seekTo(progress);
		}

	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	public void onIncommingCall() {
		if (mplay.isPlaying()) {
			mplay.pause();
			isPause = true;
		}
	}

}
