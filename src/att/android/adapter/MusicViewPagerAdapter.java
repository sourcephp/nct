package att.android.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import att.android.fragment.LyricFragment;
import att.android.fragment.MusicFragment;
import att.android.fragment.PlaylistFragment;

public class MusicViewPagerAdapter extends FragmentPagerAdapter {
	private Context mContext;
	private FragmentManager mFragmentManager;
	public MusicViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.mContext = context;
		this.mFragmentManager = fm;
	}

	@Override
	public Fragment getItem(int position) {
		StringBuilder builder = new StringBuilder(1024);
		builder.append("android:switcher:").append("");
		Fragment f = new Fragment();
		this.mFragmentManager.getClass();
		switch (position) {
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
