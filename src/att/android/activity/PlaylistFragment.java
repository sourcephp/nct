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

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import att.android.activity.MusicFragmentActivity.MyAlertDialogFragment;
import att.android.adapter.Music_MyPlaylistAdapter;
import att.android.bean.Music_Song;
import att.android.model.OnFragmentDataRecevier;
import att.android.util.MyDialog;
import att.android.util.MyDialog.OnMyDialogListener;

import com.example.multiapp.R;

public class PlaylistFragment extends BaseFragment implements OnClickListener,
		OnItemClickListener, OnFragmentDataRecevier {

	private ListView mListView;
	private Music_MyPlaylistAdapter playlistAdapter;
	private ArrayList<Music_Song> mPlaylist;
	private Button mBtnTrash;
	private final static String NOTES = "notes.txt";
	private ReadingSongInfo mReadingSongInfo;
	private File file;
	private LinearLayout mLayoutDel;
	private Button btnDel;
	private Button btnDelAll;
	private Button btnCancel;
	private Animation amRightToLeft;
	private Animation amLeftToRight;
	private MyDialog mMyDialog;
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
	private boolean delAll;

	public static Fragment newInstance(Context context) {
		PlaylistFragment f = new PlaylistFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(
				R.layout.playlist_fragment, null);
		return root;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MusicFragmentActivity) activity).setDataListener1(this);
	}

	@Override
	public void initVariables() {
		mMyDialog = new MyDialog(this.getActivity());
		file = getActivity().getFileStreamPath(NOTES);
		mPlaylist = new ArrayList<Music_Song>();
		playlistAdapter = new Music_MyPlaylistAdapter(getActivity(), 1,
				mPlaylist);
		amLeftToRight = AnimationUtils.loadAnimation(this.getActivity(),
				R.anim.translate_left_to_right);
		amRightToLeft = AnimationUtils.loadAnimation(this.getActivity(),
				R.anim.translate_right_to_left);
	}

	@Override
	public void initViews() {
		mListView = (ListView) this.getView().findViewById(R.id.listView1);
		mBtnTrash = (Button) this.getView().findViewById(R.id.btn_trash);
		mLayoutDel = (LinearLayout) this.getView().findViewById(
				R.id.layout_delete);
		btnDelAll = (Button) this.getView().findViewById(R.id.btn_del_all);
		btnDel = (Button) this.getView().findViewById(R.id.btn_del);
		btnCancel = (Button) this.getView().findViewById(R.id.btn_cancel);
	}

	@Override
	public void initActions() {
		mBtnTrash.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
		mLayoutDel.setVisibility(View.INVISIBLE);
		btnDel.setOnClickListener(this);
		btnDelAll.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		mMyDialog.setOnMyDialogListener(new OnMyDialogListener() {

			public void onItemClick(boolean isOk) {
				if (isOk) {
					if (delAll) {
						work2();
					} else {
						work1();
					}
				}
			}
		});
	}

	public void onClick(View v) {
		if (file.exists()) {
			if (v == mBtnTrash) {
				playlistAdapter.showCheckBox(mPlaylist);
				playlistAdapter.notifyDataSetChanged();
				mLayoutDel.startAnimation(amRightToLeft);
				mLayoutDel.setVisibility(View.VISIBLE);
				mListView.setAdapter(playlistAdapter);
			}
		}
		if (v == btnCancel) {

			mLayoutDel.startAnimation(amLeftToRight);
			mLayoutDel.setVisibility(View.INVISIBLE);
			playlistAdapter.hideCheckBox(mPlaylist);
			playlistAdapter.notifyDataSetChanged();
			mListView.setAdapter(playlistAdapter);
		}
		if (v == btnDel) {
			mMyDialog.show();
			delAll = false;
		}

		if (v == btnDelAll) {
			mMyDialog.show();
			delAll = true;
		}

	}

	private void work1() {
		String str = "";
		for (int i = 0; i < mPlaylist.size(); i++) {
			if (!playlistAdapter.getItem(i).isSelected()) {
				if (str.equals("")) {
					str = mPlaylist.get(i).name + "╥" + mPlaylist.get(i).singer
							+ "╥" + mPlaylist.get(i).songKey + "╥"
							+ mPlaylist.get(i).streamURL;
				} else {
					str += mPlaylist.get(i).name + "╥"
							+ mPlaylist.get(i).singer + "╥"
							+ mPlaylist.get(i).songKey + "╥"
							+ mPlaylist.get(i).streamURL;
				}
				str += "\n";
			}
		}

		mPlaylist.clear();
		if (!str.equals("")) {
			try {

				OutputStreamWriter out = new OutputStreamWriter(getActivity()
						.openFileOutput(NOTES, 0));
				out.write(str);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			file.delete();
		}
		getList();
		mListView.setAdapter(playlistAdapter);
		mLayoutDel.startAnimation(amLeftToRight);
		mLayoutDel.setVisibility(View.INVISIBLE);
		playlistAdapter.hideCheckBox(mPlaylist);
		playlistAdapter.notifyDataSetChanged();
		mListView.setAdapter(playlistAdapter);
	}

	private void work2() {
		mPlaylist.clear();
		file.delete();
		mListView.setAdapter(playlistAdapter);
		mLayoutDel.startAnimation(amLeftToRight);
		mLayoutDel.setVisibility(View.INVISIBLE);
		playlistAdapter.hideCheckBox(mPlaylist);
		playlistAdapter.notifyDataSetChanged();
		mListView.setAdapter(playlistAdapter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
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
