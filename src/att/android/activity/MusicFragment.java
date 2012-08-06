package att.android.activity;

import java.io.IOException;
import java.util.ArrayList;

import com.example.multiapp.R;

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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import att.android.adapter.Music_HotSongAdapter;
import att.android.bean.Music_Song;
import att.android.network.Music_LyricNetwork;
import att.android.network.Music_SongListNetwork;


public class MusicFragment extends BaseFragment implements OnClickListener,
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
	private RelativeLayout mMusicControler;
	private Music_SongListNetwork mSongListNetwork;
	private Button btnMusicBack;
	private Button btnLyric;
	private TextView txtLyric;
	//xoa cai dong comment nay di
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
	public void onResume() {
		super.onResume();
	}

	public void onClick(View v) {

		if (v == mBtnPlay) {
			if (isPlaying) {
				mplay.pause();
				isPlaying = false;
			} else {
				mplay.start();
				int i = mplay.getCurrentPosition();
				mSeekBar.setProgress(i);
				isPlaying = true;
			}
		}
		if (v == btnMusicBack) {
			mMusicControler.setVisibility(View.INVISIBLE);
			mListView.setEnabled(true);
		}
		if (v == btnLyric) {
			mMusicControler.setVisibility(View.VISIBLE);
			mListView.setEnabled(false);
		}
	}

	private String mSongName;
	private int count = 0;

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		mMusicControler.setVisibility(View.VISIBLE);
		mListView.setEnabled(false);
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
				super.handleMessage(msg);
				mLyric = (String) msg.obj;
			}
		};

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
			mPlayMusic.execute();
		} else {
			mPlayMusic.cancel(true);
			playMusic();
		}
	}

	private void playMusic() {

		mPlayMusic = new RunMusic();
		mPlayMusic.execute();
	}

	private void seekChange(View v) {
		if (mplay.isPlaying()) {
			SeekBar sb = (SeekBar) v;
			mplay.seekTo(sb.getProgress());
		}
	}

	private class RunMusic extends AsyncTask<Integer,Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... in) {
			try {

				mplay.setDataSource(streamUrl);
				mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mplay.prepare();
				mplay.start();
				
				Log.i("time", "" + mplay.getDuration());
				for (int i = 0, n = mplay.getDuration(); i < n;) {
					publishProgress();
					
					i = mplay.getCurrentPosition();
					Log.i("time", ""+mplay.getCurrentPosition());
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
			super.onCancelled();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mSeekBar.setMax(mplay.getDuration());
			mSeekBar.setProgress(mplay.getCurrentPosition());
			txtLyric.setText(mLyric);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}
	}

	@Override
	public void initVariables() {
		mSongList = new ArrayList<Music_Song>();
		mHotSongAdapter = new Music_HotSongAdapter(getActivity(),
				R.id.tv_songName, mSongList);
		mplay = new MediaPlayer();
		mSongListNetwork = new Music_SongListNetwork(mHandler);
	}

	@Override
	public void initViews() {
		mBtnPlay = (Button) this.getView().findViewById(R.id.btn_play);
		songName = (TextView) this.getView().findViewById(R.id.txt_song_name);
		mSeekBar = (SeekBar) this.getView().findViewById(R.id.seekBar1);
		mListView = (ListView) this.getView().findViewById(R.id.list_music);
		mMusicControler = (RelativeLayout) this.getView().findViewById(
				R.id.layout_lyric);
		mMusicControler.setVisibility(View.INVISIBLE);

		btnMusicBack = (Button) this.getView().findViewById(R.id.btn_add_music);
		btnLyric = (Button) this.getView().findViewById(R.id.btn_music);
		txtLyric = (TextView) this.getView().findViewById(R.id.txt_lyric);
	}

	@Override
	public void initActions() {
		mSeekBar.setProgress(0);
		mBtnPlay.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mHotSongAdapter);
		btnMusicBack.setOnClickListener(this);
		btnLyric.setOnClickListener(this);
		songName.setText(mSongName);
		songName.setSelected(true);
		if(mHotSongAdapter.isEmpty()){
			getSongs();
		}
	}

	private void getSongs() {
		Thread t = new Thread(mSongListNetwork);
		t.start();
	}
	public void startFragment(String a){
		((MusicFragmentActivity)this.getActivity()).sendData("Test Data");
		((MusicFragmentActivity)this.getActivity()).startFragment(1);
	}
}
