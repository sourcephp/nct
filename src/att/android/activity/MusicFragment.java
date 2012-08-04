package att.android.activity;

import java.io.IOException;

import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import att.android.adapter.Music_HotSongAdapter;
import att.android.bean.Music_Song;
import att.android.network.Music_LyricNetwork;
import att.android.network.Music_SongListNetwork;

import com.example.multiapp.R;

public class MusicFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	private ListView mListView;
	private ArrayList<Music_Song> mSongList;
	private Music_HotSongAdapter mHotSongAdapter;
	private String streamUrl;
	private String mLyric = "";
	private Button mBtnPlay;
	private boolean isPlaying = false;
	private MediaPlayer mplay;
	private TextView songName;
	private SeekBar mSeekBar;
	private Music_SongListNetwork mSongListNetwork;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			ArrayList<Music_Song> rs = (ArrayList<Music_Song>) msg.obj;
			for (Music_Song itm : rs) {
				mHotSongAdapter.add(itm);
			}
			mHotSongAdapter.notifyDataSetChanged();
			mListView.setOnItemClickListener(MusicFragment.this);
		}
	};
	private RunMusic mPlayMusic;

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
		super.onActivityCreated(savedInstanceState);
		mBtnPlay = (Button) this.getView().findViewById(R.id.btn_play);
		songName = (TextView) this.getView().findViewById(R.id.txt_song_name);
		mSeekBar = (SeekBar) this.getView().findViewById(R.id.seekBar1);
		mSeekBar.setProgress(0);
		mBtnPlay.setOnClickListener(this);

		mSongList = new ArrayList<Music_Song>();
		mHotSongAdapter = new Music_HotSongAdapter(getActivity(),
				R.id.tv_songName, mSongList);
		mListView = (ListView) this.getView().findViewById(R.id.list_music);
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mHotSongAdapter);
		mplay = new MediaPlayer();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		songName.setText(mSongName);
		mSongListNetwork = new Music_SongListNetwork(mHandler);
		Thread t = new Thread(mSongListNetwork);
		t.start();
	}

	public void onClick(View v) {

		if (v == mBtnPlay) {
			if (isPlaying) {
				mplay.pause();
				mBtnPlay.setBackgroundDrawable(this.getResources().getDrawable(
						R.drawable.media_pause));
				isPlaying = false;
			} else {
				mplay.start();
				mBtnPlay.setBackgroundDrawable(this.getResources().getDrawable(
						R.drawable.media_start));
				isPlaying = true;
			}
		}
	}
	private String mSongName;
	private int count= 0;

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		mplay.reset();
		mSeekBar.setProgress(0);
		mHotSongAdapter.getItem(position);
		final Music_Song item = mHotSongAdapter.getItem(position);
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
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				mLyric = (String) msg.obj;
			}
		};
		Log.i("lyric", mLyric);

		Music_LyricNetwork lyric = new Music_LyricNetwork(h, item.songKey);
		lyric.start();
		mSongName = item.name + " --- " + item.singer;
		songName.setText(mSongName);

		count++;
		Log.i("onItemClick", "" + count);
		changeRunMusic();
	}

	private void changeRunMusic() {
		if (1 == count) {
			mPlayMusic = new RunMusic();
			mPlayMusic.execute(mplay);
		} else {
			mPlayMusic.cancel(true);
			playMusic();
		}
	}

	private void playMusic() {

		mPlayMusic = new RunMusic();
		mPlayMusic.execute(mplay);
	}

	private void seekChange(View v) {
		if (mplay.isPlaying()) {
			SeekBar sb = (SeekBar) v;
			mplay.seekTo(sb.getProgress());
		}
	}

	private class RunMusic extends AsyncTask<MediaPlayer, Integer, Integer> {

		@Override
		protected Integer doInBackground(MediaPlayer... mplay) {
			try {

				mplay[0].setDataSource(streamUrl);
				mplay[0].setAudioStreamType(AudioManager.STREAM_MUSIC);
				mplay[0].prepare();
				mplay[0].start();
				isPlaying = true;
				MusicFragment.this.mSeekBar.setMax(mplay[0].getDuration());
				Log.i("time", "" + mplay[0].getDuration());
				for (int i = 0, n = mplay[0].getDuration(); i < n;) {
					publishProgress(i);
					i = mplay[0].getCurrentPosition();
					if (isCancelled())
						break;

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

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
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			MusicFragment.this.mSeekBar.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}
	}
}
