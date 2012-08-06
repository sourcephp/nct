package att.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

public abstract class BaseFragment extends Fragment {
	private boolean didInit = false;
	ViewPager pager;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i("LyricFragment","onActivityCreated");
		setRetainInstance(true);
		if(didInit == false){
			didInit = true;
			initVariables();
		}
		initViews();
		initActions();
		
			
	}
	public abstract void initVariables();
	public abstract void initViews();
	public abstract void initActions();
}
