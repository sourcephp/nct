package att.android.activity;


import com.example.multiapp.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlaylistFragment extends BaseFragment {
	public static Fragment newInstance(Context context) {
		PlaylistFragment f = new PlaylistFragment();

		return f;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.playlist_fragment,
				null);
		return root;
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
}
