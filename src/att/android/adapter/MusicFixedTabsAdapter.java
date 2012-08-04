package att.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.astuetz.viewpager.extensions.TabsAdapter;
import com.astuetz.viewpager.extensions.ViewPagerTabButton;
import com.example.multiapp.R;

public class MusicFixedTabsAdapter implements TabsAdapter{
private Activity mContext;
private String[] strName = {"host", "lyric", "playlist"};
public MusicFixedTabsAdapter(Activity ctx){
	this.mContext = ctx;
}
	public View getView(int position) {
		ViewPagerTabButton tab;

		LayoutInflater inflater = mContext.getLayoutInflater();
		tab = (ViewPagerTabButton) inflater.inflate(R.layout.music_tab_fixed, null);

		if (position < strName.length)
			tab.setText(strName[position]);

		return tab;
	}

}
