package att.android.activity;

import java.util.ArrayList;

import com.example.multiapp.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import att.android.adapter.MusicViewPagerAdapter;
import att.android.bean.Music_Song;
import att.android.model.OnFragmentDataRecevier;

public class MusicFragmentActivity extends FragmentActivity implements
		ViewPager.OnPageChangeListener {
	private ViewPager mPager;
	private MusicViewPagerAdapter mPagerAdapter;
	private ArrayList<Music_Song> data;
	private OnFragmentDataRecevier listener;
	private boolean haveData;
	private int index;
	private int position;
	private OnFragmentDataRecevier listener2;
	private OnFragmentDataRecevier listener1;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_activity_music);
		initViewPager(0);
	}

	private void initViewPager(int i) {
		mPager = (ViewPager) findViewById(R.id.pager_music);
		mPagerAdapter = new MusicViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(this);
		mPager.setCurrentItem(i);
		mPager.setPageMargin(1);
	}

	public void startFragment(int i) {
		mPager.setCurrentItem(i);

	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageSelected(int arg0) {
		if (arg0 == 2) {
			listener1.onDataParameterData(0);
		}
		if (haveData) {
			haveData = false;
			if (test) {
				listener.onDataParameterData(data, position, bool);
				if (bool) {
					listener2.onDataParameterData(null, arg0, bool);
				}
			} else {
				listener.onDataParameterData(index);
			}
		}
	}

	public void setDataListener1(OnFragmentDataRecevier listener) {
		this.listener1 = listener;
	}

	public void setDataListener(OnFragmentDataRecevier listener) {
		this.listener = listener;
	}

	public void setDataListener2(OnFragmentDataRecevier listener) {
		this.listener2 = listener;
	}

	private boolean test;
	private boolean bool = false;
	private int ok = -1;

	public void sendData(int index) {
		test = false;
		this.index = index;
		haveData = true;
	}

	public void sendData(ArrayList<Music_Song> data, int position, boolean bool) {
		test = true;
		this.data = data;
		this.position = position;
		this.bool = bool;
		haveData = true;
	}

	public void doPositiveClick(int ok) {
		this.ok  = ok;
		ok = 1;
	}
	public static class MyAlertDialogFragment extends DialogFragment {

		public static MyAlertDialogFragment newInstance(int title, int i) {
			MyAlertDialogFragment frag = new MyAlertDialogFragment();
			Bundle args = new Bundle();
			args.putInt("title", title);
			Bundle args0 = new Bundle();
			args0.putInt("int", i);
			frag.setArguments(args);
			frag.setArguments(args0);
			return frag;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			int title = getArguments().getInt("title");
			final int i = getArguments().getInt("int");

			return new AlertDialog.Builder(getActivity())
					.setTitle(title)
					.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									((MusicFragmentActivity) getActivity())
											.doPositiveClick(i);
								}
							})
					.setNegativeButton(R.string.alert_dialog_cancel,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).create();
		}

	}
}
