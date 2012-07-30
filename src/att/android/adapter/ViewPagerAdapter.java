package att.android.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import att.android.activity.ChatJointFragment;
import att.android.activity.ContactFragment;
import att.android.activity.LoginActivity;
import att.android.activity.MusicFragment;
import att.android.activity.RssFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private Context mContext;

	public ViewPagerAdapter(Context mContext, FragmentManager fm) {
		super(fm);
		this.mContext = mContext;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment f = new Fragment();
		switch (position) {

		case 0:
			f = ContactFragment.newInstance(mContext);
			break;
		case 1:
			f = RssFragment.newInstance(mContext);
			break;

		case 2:
			f = MusicFragment.newInstance(mContext);
			break;
		case 3:
			f = ChatJointFragment.newInstance(mContext);
			break;

		}
		return f;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
