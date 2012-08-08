package att.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import att.android.bean.Music_Song;

import com.example.multiapp.R;

public class Music_SearchSongAdapter extends ArrayAdapter<Music_Song> {
	public Music_SearchSongAdapter(Context context, int textViewResourceId,
			List<Music_Song> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private LayoutInflater mInflater;

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
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.songName.setText(mSong.getNameSong());
		mHolder.singer.setText(mSong.getSinger());
		mHolder.streamUrl = mSong.getStreamUrl();
		return convertView;
	}

	private class ViewHolder {
		private TextView songName;
		private TextView singer;
		private String streamUrl;
	}
}
