package att.android.activity;

import java.io.IOException;

import java.util.ArrayList;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
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
import att.android.network.Music_SongListNetWork;

import com.example.multiapp.R;

public class MusicFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	private ListView mListView;
	private ArrayList<Music_Song> mSongList;
	private Music_HotSongAdapter mHotSongAdapter;
	private String streamUrl;
	private Button mBtnPlay;
	private boolean isPlaying = false;
	private MediaPlayer mplay;
	private TextView songName;
	private SeekBar mSeekBar;
	private Music_SongListNetWork mSongListNetwork;
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
		mHotSongAdapter.clear();
		mSongListNetwork = new Music_SongListNetWork(mHandler);
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

	private String duration = "";

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		mplay.reset();
		mSeekBar.setProgress(0);
		mHotSongAdapter.getItem(position);
		final Music_Song item = mHotSongAdapter.getItem(position);
		streamUrl = item.streamURL;
		Thread t = new Thread() {
			public void run() {
				try {

					mplay.setDataSource(streamUrl);
					mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mplay.prepare();
					mplay.start();
					isPlaying = true;
					// TODO Auto-generated method stub

				} catch (IllegalArgumentException e) {
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
		};
		t.start();

		/*
		 * Log.i("time", duration); long dur = Long.parseLong(duration); String
		 * seconds = String.valueOf((dur % 60000) / 1000); String minutes =
		 * String.valueOf(dur / 60000);
		 */

		/*
		 * MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
		 * metaRetriever.setDataSource(streamUrl); duration = metaRetriever
		 * .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		 */

		mSeekBar.setMax(27234);
		mSeekBar.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				seekChange(v);
				return false;
			}
		});

		startPlayProgressUpdater();

		songName.setText(item.name + " --- " + item.singer);

	}

	private final Handler handler = new Handler();

	public void startPlayProgressUpdater() {
		mSeekBar.setProgress(mplay.getCurrentPosition());

		if (mplay.isPlaying()) {
			Runnable notification = new Runnable() {
				public void run() {
					startPlayProgressUpdater();
				}
			};
			handler.postDelayed(notification, 1000);
		} else {
			mplay.pause();
		}
	}

	private void seekChange(View v) {
		if (mplay.isPlaying()) {
			SeekBar sb = (SeekBar) v;
			mplay.seekTo(sb.getProgress());
		}
	}
}
