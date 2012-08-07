package att.android.activity;

import java.util.ArrayList;

import com.example.multiapp.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import att.android.adapter.Music_HotSongAdapter;
import att.android.bean.Music_Song;
import att.android.network.Music_SongListNetwork;

public class MusicFragment extends BaseFragment implements OnItemClickListener {

	private ListView mListView;
	private ArrayList<Music_Song> mSongList;
	private Music_HotSongAdapter mHotSongAdapter;
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

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		mHotSongAdapter.getItem(position);
		final Music_Song item = mHotSongAdapter.getItem(position);

		startFragment(item);
	}

	@Override
	public void initVariables() {
		mSongList = new ArrayList<Music_Song>();
		mHotSongAdapter = new Music_HotSongAdapter(getActivity(),
				R.id.tv_songName, mSongList);
		mSongListNetwork = new Music_SongListNetwork(mHandler);

	}

	@Override
	public void initViews() {
		mListView = (ListView) this.getView().findViewById(R.id.list_music);
	}

	@Override
	public void initActions() {
		mListView.setOnItemClickListener(this);
		mListView.setAdapter(mHotSongAdapter);
		if (mHotSongAdapter.isEmpty()) {
			getSongs();
		}
	}

	private void getSongs() {
		Thread t = new Thread(mSongListNetwork);
		t.start();
	}

	public void startFragment(Music_Song item) {
		((MusicFragmentActivity) this.getActivity()).sendData(item);
		((MusicFragmentActivity) this.getActivity()).startFragment(1);
	}
}
