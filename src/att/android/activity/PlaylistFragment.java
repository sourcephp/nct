package att.android.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.example.multiapp.R;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView.Tokenizer;
import att.android.adapter.Music_MyPlaylistAdapter;
import att.android.bean.Music_Song;

public class PlaylistFragment extends BaseFragment implements OnClickListener,
		OnItemClickListener, OnFragmentDataRecevier {

	private View mBtnUpdate;
	private ListView mListView;
	private Music_MyPlaylistAdapter playlistAdapter;
	ArrayList<Music_Song> mPlaylist;
	private View mBtnDelete;
	private final static String NOTES = "notes.txt";
	private ReadingSongInfo mReadingSongInfo;
	private File file;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			ArrayList<Music_Song> rs = (ArrayList<Music_Song>) msg.obj;
			for (Music_Song itm : rs) {
				playlistAdapter.add(itm);
			}
			playlistAdapter.notifyDataSetChanged();
			mListView.setOnItemClickListener(PlaylistFragment.this);
		};
	};

	public static Fragment newInstance(Context context) {
		PlaylistFragment f = new PlaylistFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(
				R.layout.playlist_fragment, null);
		Log.e("PlaylistFragment", "onCreateView");
		return root;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MusicFragmentActivity) activity).setDataListener1(this);
	}

	@Override
	public void initVariables() {
		file = getActivity().getFileStreamPath(NOTES);
		mPlaylist = new ArrayList<Music_Song>();
		playlistAdapter = new Music_MyPlaylistAdapter(getActivity(), 1,
				mPlaylist);
	}

	@Override
	public void initViews() {
		mBtnUpdate = this.getView().findViewById(R.id.btn_update);
		mListView = (ListView) this.getView().findViewById(R.id.listView1);
		mBtnDelete = this.getView().findViewById(R.id.btn_del);
	}

	@Override
	public void initActions() {
		mBtnDelete.setOnClickListener(this);
		mBtnUpdate.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
	}

	public void onClick(View v) {
		if (v == mBtnDelete) {
			if (file.exists()) {
				mPlaylist.clear();
				file.delete();
				mListView.clearAnimation();
				mListView.setAdapter(playlistAdapter);
			}
		}

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		startFragment(mPlaylist, position, true);

	}

	private void getList() {
		mPlaylist.clear();
		mReadingSongInfo = new ReadingSongInfo(mHandler);
		Thread t = new Thread(mReadingSongInfo);
		t.start();
	}

	public void startFragment(ArrayList<Music_Song> item, int position,
			boolean bool) {
		((MusicFragmentActivity) this.getActivity()).sendData(item, position,
				bool);
		((MusicFragmentActivity) this.getActivity()).startFragment(1);
	}

	private class ReadingSongInfo implements Runnable {

		private Handler mHandler;
		private ArrayList<Music_Song> list;

		public ReadingSongInfo(Handler h) {
			mHandler = h;
		}

		public void run() {
			list = new ArrayList<Music_Song>();
			File file = ((MusicFragmentActivity) getActivity())
					.getFileStreamPath(NOTES);
			if (file.exists()) {
				InputStream in;
				try {
					in = getActivity().openFileInput(NOTES);
					if (in != null) {
						InputStreamReader tmp = new InputStreamReader(in);
						BufferedReader reader = new BufferedReader(tmp);
						String str1;
						String str[] = new String[4];

						while ((str1 = reader.readLine()) != null) {
							int i = 0;
							Music_Song song = new Music_Song();
							StringTokenizer token = new StringTokenizer(str1,
									"╥");
							while (token.hasMoreTokens()) {
								str[i] = token.nextToken();
								i++;
							}
							song.name = str[0];
							song.singer = str[1];
							song.songKey = str[2];
							song.streamURL = str[3];

							list.add(song);
						}

						in.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			Message msg = new Message();
			msg.obj = list;
			mHandler.sendMessage(msg);

		}

	}

	public void onDataParameterData(int index) {
		if (file.exists()) {
			getList();
			mListView.setAdapter(playlistAdapter);
		}
	}

	public void onDataParameterData(ArrayList<Music_Song> listSong,
			int position, boolean bool) {

	}

}
