package att.android.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import att.android.fragment.ChatFragment;
import att.android.fragment.ContactFragment;
import att.android.fragment.LoginFragment;

public class MessViewPagerAdapter extends FragmentPagerAdapter {
	private Context mContext;

	public MessViewPagerAdapter(Context mContext, FragmentManager fm) {
		super(fm);
		this.mContext = mContext;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment f = new Fragment();
		switch (position) {

		case 0:
			f = LoginFragment.newInstance(mContext);
			break;
		case 1:
			f = ContactFragment.newInstance(mContext);
			break;

		case 2:
			f = ChatFragment.newInstance(mContext);
			break;

		}
		return f;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
