package att.android.activity;

import java.io.IOException;
import java.util.ArrayList;

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
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import att.android.bean.Music_Song;
import att.android.network.Music_LyricNetwork;

import com.example.multiapp.R;

public class LyricFragment extends BaseFragment implements
		OnFragmentDataRecevier, OnClickListener, OnSeekBarChangeListener {

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
				} else {
					mplay.start();
				}
			}

			if (v == mBtnNext && instanceIndex < mSongList.size()) {
				instanceIndex++;
				doManyTimes(mSongList.get(instanceIndex));
				changeRunMusic();

			}
			if (v == mBtnPre && instanceIndex >= 1) {
				instanceIndex--;
				doManyTimes(mSongList.get(instanceIndex));
				changeRunMusic();

			}
		}

	}

	public void onDataParameterData(Music_Song songInfo) {
		Log.w("songinfo", songInfo.name);
		instanceIndex = 1;
		doManyTimes(songInfo);
		changeRunMusic();
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

	// private void seekChange(View v) {
	// if (mplay.isPlaying()) {
	// SeekBar sb = (SeekBar) v;
	// mplay.seekTo(sb.getProgress());
	// }
	// }

	private class RunMusic extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... in) {
			try {

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
			if (mplay.isPlaying()) {
				currentTime = mplay.getCurrentPosition();
			} else if (currentTime > (mplay.getDuration() - 500)) {
				if (isRepeat) {
					mplay.seekTo(0);
					mplay.start();
				} else {
					instanceIndex++;
					doManyTimes(mSongList.get(instanceIndex));
					changeRunMusic();
				}
			}
			mSeekBar.setProgress(currentTime);
			if (mLyric.equals("")) {
				txtLyric.setText("Hiện chưa có lời cho bài hát này");
			} else {
				txtLyric.setText(mLyric);
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}
	}

	public void onDataParameterData(ArrayList<Music_Song> listSong, int position) {
		instanceIndex = position;
		mSongList = listSong;
		doManyTimes(mSongList.get(position));
		changeRunMusic();

	}

	public void onDataParameterData(int index) {
		instanceIndex = index;
		doManyTimes(mSongList.get(index));
		changeRunMusic();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mplay.stop();
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
}
