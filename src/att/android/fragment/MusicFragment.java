package att.android.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ListView;
import att.android.activity.MusicFragmentActivity;
import att.android.adapter.Music_HotSongAdapter;
import att.android.adapter.Music_SearchSongAdapter;
import att.android.bean.Music_Song;
import att.android.model.OnFragmentDataRecevier;
import att.android.network.Music_SearchSongNetwork;
import att.android.network.Music_SongListNetwork;

import com.example.multiapp.R;

public class MusicFragment extends BaseFragment implements OnItemClickListener,
		OnClickListener, OnFragmentDataRecevier {

	private ListView mListView;
	private ArrayList<Music_Song> mSongList;
	private ArrayList<Music_Song> mSearchSongList;
	private Music_HotSongAdapter mHotSongAdapter;
	private Music_SearchSongAdapter mSearchSongAdapter;
	private Music_SongListNetwork mSongListNetwork;
	private Button mBtnSearch;
	private Button mBtnBack;
	private EditText eKeySearch;
	private boolean check1 = true;
	private boolean check2 = true;
	private static boolean changeAdapter = false;
	private Music_SearchSongNetwork mSearchSongNetwork;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			ArrayList<Music_Song> rs = (ArrayList<Music_Song>) msg.obj;
			for (Music_Song itm : rs) {
				mHotSongAdapter.add(itm);
//				Log.i("TestMusic","Dang load data");
			}
			mHotSongAdapter.notifyDataSetChanged();
//			Log.i("TestMusic","Da nhan het");
			mListView.setOnItemClickListener(MusicFragment.this);
		}
	};

	private Handler mSearchHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			ArrayList<Music_Song> rs = (ArrayList<Music_Song>) msg.obj;
			for (Music_Song itm : rs) {
				mSearchSongAdapter.add(itm);
			}
			mSearchSongAdapter.notifyDataSetChanged();
			mListView.setOnItemClickListener(MusicFragment.this);
		}
	};

	public static Fragment newInstance(Context context) {
		MusicFragment f = new MusicFragment();

		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MusicFragmentActivity) activity).setDataListener2(this);
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
		if (!changeAdapter) {
			if (!check1) {
				startFragment(position);
			} else {

				startFragment1(mSongList, position, false);
			}
			check1 = false;
		} else {
			if (!check2) {
				startFragment(position);
			} else {

				startFragment1(mSearchSongList, position, false);
			}
			check2 = false;
		}
	}

	@Override
	public void initVariables() {
		mSongList = new ArrayList<Music_Song>();
		mSearchSongList = new ArrayList<Music_Song>();
		mHotSongAdapter = new Music_HotSongAdapter(getActivity(),
				R.id.tv_songName, mSongList);
		mSongListNetwork = new Music_SongListNetwork(mHandler);

	}

	@Override
	public void initViews() {
		mListView = (ListView) this.getView().findViewById(R.id.list_music);
		mBtnSearch = (Button) this.getView()
				.findViewById(R.id.btn_search_Music);
		mBtnBack = (Button) this.getView().findViewById(R.id.btn_hot_list);
		eKeySearch = (EditText) this.getView().findViewById(
				R.id.edtxt_search_music);
	}

	@Override
	public void initActions() {
		mBtnSearch.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
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

	private void getSearchSong() {
		Thread t = new Thread(mSearchSongNetwork);
		t.start();
	}

	public void startFragment(int index) {
		((MusicFragmentActivity) this.getActivity()).sendData(index);
		((MusicFragmentActivity) this.getActivity()).startFragment(1);
	}

	public void startFragment1(ArrayList<Music_Song> item, int position,
			boolean bool) {
		((MusicFragmentActivity) this.getActivity()).sendData(item, position,
				bool);
		((MusicFragmentActivity) this.getActivity()).startFragment(1);
	}

	public void onClick(View v) {
		if (v == mBtnSearch && (!eKeySearch.getText().toString().equals(""))) {

			mSearchSongAdapter = new Music_SearchSongAdapter(getActivity(),
					R.id.tv_songName, mSearchSongList);
			mSearchSongNetwork = new Music_SearchSongNetwork(mSearchHandler,
					eKeySearch.getText().toString());
			getSearchSong();

			mListView.setAdapter(mSearchSongAdapter);
			mSearchSongList.clear();
			changeAdapter = true;
		}
		if (v == mBtnBack && changeAdapter == true) {
			mListView.setAdapter(mHotSongAdapter);
			changeAdapter = false;
			check1 = true;
		}

	}

	public void onDataParameterData(int index) {

	}

	public void onDataParameterData(ArrayList<Music_Song> listSong,
			int position, boolean bool) {

		check1 = check2 = bool;
	}

}
