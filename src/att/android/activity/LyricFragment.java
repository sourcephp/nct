package att.android.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import att.android.bean.Music_Song;
import att.android.network.Music_LyricNetwork;

import com.example.multiapp.R;

public class LyricFragment extends BaseFragment implements
		OnFragmentDataRecevier, OnClickListener {

	private String streamUrl;
	private String mLyric = "";
	private Button mBtnPlay;
	private boolean isPlaying = true;
	private MediaPlayer mplay;
	private TextView songName;
	private SeekBar mSeekBar;
	private Button btnMusicBack;
	private Button btnLyric;
	private TextView txtLyric;
	private int currentTime;
	private View mBtnPre;
	private View mBtnNext;
	private CharSequence mSongName;
	private int instanceIndex = -1;
	private RunMusic mPlayMusic;
	private int count = 1;

	public static Fragment newInstance(Context context) {
		LyricFragment f = new LyricFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.lyric_fragment,
				null);
		Log.w("LyriFragment", "onCreateView");
		return root;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MusicFragmentActivity) activity).setDataListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("LyricFragment", "onCreate");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("LyricFragment", "onResume");
	}

	@Override
	public void initVariables() {
		mplay = new MediaPlayer();
		currentTime = 0;

	}

	@Override
	public void initViews() {
		mBtnPre = this.getView().findViewById(R.id.btn_backward);
		mBtnNext = this.getView().findViewById(R.id.btn_forward);
		mBtnPlay = (Button) this.getView().findViewById(R.id.btn_play);
		songName = (TextView) this.getView().findViewById(R.id.txt_song_name);
		mSeekBar = (SeekBar) this.getView().findViewById(R.id.seekBar1);

		btnLyric = (Button) this.getView().findViewById(R.id.btn_music);
		txtLyric = (TextView) this.getView().findViewById(R.id.txt_lyric);
	}

	@Override
	public void initActions() {
		mBtnPre.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);
		mSeekBar.setProgress(0);
		mBtnPlay.setOnClickListener(this);
		songName.setText(mSongName);
		songName.setSelected(true);
	}

	public void onClick(View v) {

		if (v == mBtnPlay) {
			if (isPlaying) {
				mplay.pause();
				isPlaying = false;
			} else {
				mplay.start();
				isPlaying = true;
			}
		}
		if (v == mBtnNext && instanceIndex < 65) {
			instanceIndex++;

		}
		if (v == mBtnPre && instanceIndex >= 1) {
			instanceIndex--;
		}
	}

	public void onDataParameterData(Music_Song songInfo) {
		Log.w("songinfo", songInfo.name);
		doManyTimes(songInfo);
		changeRunMusic();
	}

	private void changeRunMusic() {
		if (1 == count) {
			mPlayMusic = new RunMusic();
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
		mSeekBar.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				seekChange(v);
				return false;
			}
		});
		Handler h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				mLyric = (String) msg.obj;
			}
		};

		Music_LyricNetwork lyric = new Music_LyricNetwork(h, item.songKey);
		lyric.start();
		mSongName = item.name + " --- " + item.singer;
		songName.setText(mSongName);
	}

	private void seekChange(View v) {
		if (mplay.isPlaying()) {
			SeekBar sb = (SeekBar) v;
			mplay.seekTo(sb.getProgress());
		}
	}

	private class RunMusic extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... in) {
			try {

				mplay.setDataSource(streamUrl);
				mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mplay.prepare();
				mplay.start();

				Log.i("time", "" + mplay.getDuration());

				for (int i = 0, n = mplay.getDuration() - 1000; i < n;) {

					publishProgress();
					i = mplay.getCurrentPosition();
					Log.i("time", "" + mplay.getCurrentPosition()
							+ "------------" + mplay.getDuration());
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
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
			mSeekBar.setMax(mplay.getDuration());
			if (isPlaying) {
				currentTime = mplay.getCurrentPosition();
			}
			mSeekBar.setProgress(currentTime);
			txtLyric.setText(mLyric);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}
	}

}
