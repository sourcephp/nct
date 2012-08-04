package att.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.astuetz.viewpager.extensions.TabsAdapter;
import com.astuetz.viewpager.extensions.ViewPagerTabButton;
import com.example.multiapp.R;

public class MessFixedTabsAdapter implements TabsAdapter {

	private Activity mContext;

	private int[] iconTab = {R.drawable.custom_btn_login, R.drawable.custom_contact_tab,R.drawable.custom_chat_tab};

	public MessFixedTabsAdapter(Activity ctx) {
		this.mContext = ctx;
	}

	public View getView(int position) {
		ViewPagerTabButton tab;

		LayoutInflater inflater = mContext.getLayoutInflater();
		tab = (ViewPagerTabButton) inflater.inflate(R.layout.mess_tab_fixed, null);

		if (position < iconTab.length)
			tab.setBackgroundDrawable(mContext.getResources().getDrawable(iconTab[position]));

		return tab;
	}

}
