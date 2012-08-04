package att.android.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import att.android.activity.LyricFragment;
import att.android.activity.MusicFragment;
import att.android.activity.PlaylistFragment;

public class MusicViewPagerAdapter extends FragmentPagerAdapter {
	private Context mContext;

	public MusicViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.mContext = context;
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment f = new Fragment();
		switch (arg0) {
		case 0:
			f = MusicFragment.newInstance(mContext);
			break;
		case 1:
			f = LyricFragment.newInstance(mContext);
			break;
		case 2:
			f = PlaylistFragment.newInstance(mContext);
			break;
		default:
			break;
		}
		return f;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
