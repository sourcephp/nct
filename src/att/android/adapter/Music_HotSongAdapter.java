package att.android.adapter;

import java.util.List;

import com.example.multiapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import att.android.bean.Music_Song;

public class Music_HotSongAdapter extends ArrayAdapter<Music_Song> {

	private LayoutInflater mInflater;

	public Music_HotSongAdapter(Context context, int textViewResourceId,
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
			convertView = mInflater
					.inflate(R.layout.music_hotsong_layout, null);
			mHolder = new ViewHolder();
			mHolder.songName = (TextView) convertView
					.findViewById(R.id.tv_songName);
			mHolder.singer = (TextView) convertView
					.findViewById(R.id.tv_singer);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.songName.setText(mSong.getNameSong());
		mHolder.singer.setText(mSong.getSinger());
		return convertView;
	}

	private class ViewHolder {
		private TextView songName;
		private TextView singer;
	}
}
