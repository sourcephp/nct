package att.android.adapter;

import java.util.List;

import com.example.multiapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import att.android.bean.Music_Song;

public class Music_MyPlaylistAdapter extends ArrayAdapter<Music_Song> {
	private LayoutInflater mInflater;

	public Music_MyPlaylistAdapter(Context context, int textViewResourceId,
			List<Music_Song> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		Music_Song mSong = this.getItem(position);
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.music_playlist_layout,
					null);
			mHolder = new ViewHolder();
			mHolder.songName = (TextView) convertView
					.findViewById(R.id.tv_songName);
			mHolder.singer = (TextView) convertView
					.findViewById(R.id.tv_singer);
			convertView.setTag(mHolder);
			mHolder.btnCheck = (CheckBox) convertView
					.findViewById(R.id.btn_checked);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.songKey = mSong.songKey;
		mHolder.songName.setText(mSong.getNameSong());
		mHolder.singer.setText(mSong.getSinger());
		mHolder.streamUrl = mSong.getStreamUrl();
		mHolder.check = 1;
		return convertView;
	}

	private class ViewHolder {
		private String songKey;
		private TextView songName;
		private TextView singer;
		private String streamUrl;
		private int check;
		private CheckBox btnCheck;
	}
}
