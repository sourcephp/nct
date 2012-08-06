package att.android.activity;

import com.example.multiapp.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import att.android.bean.Music_Song;


public class LyricFragment extends BaseFragment implements OnFragmentDataRecevier {
	public static Fragment newInstance(Context context) {
		LyricFragment f = new LyricFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.lyric_fragment,
				null);
		return root;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MusicFragmentActivity)activity).setDataListener(this);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("LyricFragment","onCreate");
	}
	@Override
	public void onResume() {
		super.onResume();
		Log.i("LyricFragment","onResume");
	}
	@Override
	public void initVariables() {
		
	}

	@Override
	public void initViews() {
		
	}

	@Override
	public void initActions() {
		
	}

	public void onDataParameterData(String a) {
		Log.i("LyricFragment","Data recevier = " + a);
	}
}
