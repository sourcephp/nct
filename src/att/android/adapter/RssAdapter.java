package att.android.adapter;

import java.util.List;

import com.example.multiapp.R;
import com.loopj.android.image.SmartImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import att.android.bean.News;

public class RssAdapter extends ArrayAdapter<News> {
	private LayoutInflater mInflater;

	public RssAdapter(Context context, int textViewResourceId,
			List<News> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		News news = this.getItem(position);

		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.rowlayout, null);
			mHolder = new ViewHolder();

			mHolder.imgNews = (SmartImageView) convertView
					.findViewById(R.id.imgNews);
			mHolder.newsDate = (TextView) convertView
					.findViewById(R.id.datePost);
			mHolder.newsDest = (TextView) convertView
					.findViewById(R.id.destNews);
			mHolder.newsTittle = (TextView) convertView
					.findViewById(R.id.titleNews);

			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.imgNews.setImageUrl(news.getStrUrlImage());
		mHolder.newsDate.setText(news.getmDate());
		mHolder.newsDest.setText(news.getmDes());
		mHolder.newsTittle.setText(news.getmTitle());
		mHolder.strUrl = news.getmUrl();
		return convertView;

	}

	private class ViewHolder {
		private SmartImageView imgNews;
		private TextView newsTittle, newsDest, newsDate;
		private String strUrl;
	}
}
