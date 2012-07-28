package att.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.astuetz.viewpager.extensions.TabsAdapter;
import com.astuetz.viewpager.extensions.ViewPagerTabButton;
import com.example.multiapp.R;

public class FixedTabsAdapter implements TabsAdapter {

	private Activity mContext;

	private int[] iconTab = {R.drawable.custom_contact_tab, R.drawable.custom_rss_tab,R.drawable.custom_tab_music,R.drawable.custom_chat_tab};

	public FixedTabsAdapter(Activity ctx) {
		this.mContext = ctx;
	}

	public View getView(int position) {
		ViewPagerTabButton tab;

		LayoutInflater inflater = mContext.getLayoutInflater();
		tab = (ViewPagerTabButton) inflater.inflate(R.layout.tab_fixed, null);

		if (position < iconTab.length)
			tab.setBackgroundDrawable(mContext.getResources().getDrawable(iconTab[position]));

		return tab;
	}

}
