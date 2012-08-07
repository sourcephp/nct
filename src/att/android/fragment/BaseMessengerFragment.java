package att.android.fragment;

import org.openymsg.network.Session;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import att.android.model.YGeneralHandler;

public abstract class BaseMessengerFragment extends Fragment {
	private boolean didInit = false;
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i("MessengerFragment","onActivityCreated");
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
