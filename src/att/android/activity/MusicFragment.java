package att.android.activity;

import java.util.ArrayList;

import com.example.multiapp.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import att.android.adapter.Music_HotSongAdapter;
import att.android.bean.Music_Song;

public class MusicFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	private ListView mListView;
	private Music_HotSongAdapter mHotSongAdapter;
	private String streamUrl;
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

	private Button mBtnPlay, mBtnPause;

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
		mBtnPause = (Button) this.getView().findViewById(R.id.btn_pause);

		mBtnPlay.setOnClickListener(this);
		mBtnPause.setOnClickListener(this);
	}

	private String text;
	private MediaPlayer mplay;

	public void onClick(View v) {

		if (v == mBtnPlay) {

		} else if (v == mBtnPause) {

		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
