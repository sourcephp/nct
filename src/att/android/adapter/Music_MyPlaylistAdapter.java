package att.android.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import att.android.bean.Music_Song;

import com.example.multiapp.R;

public class Music_MyPlaylistAdapter extends ArrayAdapter<Music_Song> {
	private LayoutInflater mInflater;

	public Music_MyPlaylistAdapter(Context context, int textViewResourceId,
			List<Music_Song> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list = new ArrayList<CheckBox>();
	}

	private ArrayList<CheckBox> list;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Music_Song mSong = this.getItem(position);
		final ViewHolder mHolder;
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.music_playlist_layout,
					null);
			mHolder = new ViewHolder();
			mHolder.songName = (TextView) convertView
					.findViewById(R.id.tv_songName);
			mHolder.singer = (TextView) convertView
					.findViewById(R.id.tv_singer);
			mHolder.cbCheck = (CheckBox) convertView
					.findViewById(R.id.cb_checked);
			mHolder.cbCheck
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							mSong.setSelected(mHolder.cbCheck.isChecked());
						}
					});

			convertView.setTag(mHolder);
			list.add(mHolder.cbCheck);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.songKey = mSong.songKey;
		mHolder.songName.setText(mSong.getNameSong());
		mHolder.singer.setText(mSong.getSinger());
		mHolder.streamUrl = mSong.getStreamUrl();
		mHolder.cbCheck.setChecked(mSong.isSelected());

		return convertView;
	}

	private class ViewHolder {
		private String songKey;
		private TextView songName;
		private TextView singer;
		private String streamUrl;
		private CheckBox cbCheck;
	}

	public void showCheckBox() {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setVisibility(View.VISIBLE);
			Log.e("PlaylistAdapter", "i");
		}
	}
}
