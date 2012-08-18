package att.android.fragment;

import java.util.ArrayList;

import org.openymsg.network.YahooUser;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import att.android.activity.MessengerFragmentActivity;
import att.android.adapter.ContactListAdapter;
import att.android.network.ReadFullContactListNetwork;

import com.example.multiapp.R;
import com.quickaction.popup.ActionItem;
import com.quickaction.popup.QuickAction;
import com.quickaction.popup.QuickAction.OnActionItemClickListener;

public class ContactFragment extends Fragment implements OnItemClickListener, OnClickListener {
	private ListView listContact;
	private ContactListAdapter mContactAdapter;
	private ArrayList<YahooUser> alYahooContact;
	private QuickAction mQAction;
	private Button btnSettting;
	private Animation amTranslate;
	private LinearLayout mLayout;
	private Button btnEnter;
	private boolean isStatus = false;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			ArrayList<YahooUser> rs = (ArrayList<YahooUser>) msg.obj;
			for (YahooUser itm : rs) {
				mContactAdapter.add(itm);
			}
			mContactAdapter.notifyDataSetChanged();
		};
	};

	public static Fragment newInstance(Context context) {
		ContactFragment f = new ContactFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(
				R.layout.activity_contact_list, null);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		alYahooContact = new ArrayList<YahooUser>();
		btnSettting = (Button) this.getView().findViewById(R.id.btn_options);
		btnSettting.setOnClickListener(this);
		mContactAdapter = new ContactListAdapter(this.getActivity(), 1,
				alYahooContact);
		listContact = (ListView) this.getView().findViewById(
				R.id.listView_contactList);
		listContact.setOnItemClickListener(this);
		listContact.setAdapter(mContactAdapter);
		btnEnter = (Button) this.getView().findViewById(R.id.btn_search);
		btnEnter.setOnClickListener(this);
		amTranslate = AnimationUtils.loadAnimation(this.getActivity(), R.anim.translate_top);
		mLayout = (LinearLayout) this.getView().findViewById(R.id.layout_panel);
		mLayout.setVisibility(View.INVISIBLE);
		mQAction = new QuickAction(this.getActivity());
		ActionItem acShowOff = new ActionItem();
		acShowOff.setTitle("Hiện Offline");
		ActionItem acInfo = new ActionItem();
		acInfo.setTitle("Đổi status");
		ActionItem acOnlyOn = new ActionItem();
		acOnlyOn.setTitle("Hiện Online");
		ActionItem acSearch = new ActionItem();
		acSearch.setTitle("Tìm kiếm");
		mQAction.addActionItem(acShowOff);
		mQAction.addActionItem(acOnlyOn);
		mQAction.addActionItem(acInfo);
		mQAction.addActionItem(acSearch);
		
		mQAction.setOnActionItemClickListener(new OnActionItemClickListener() {
			
			public void onItemClick(QuickAction source, int pos, int actionId) {
				switch (pos) {
				case 0:
					// bat su kien khi ch onnut hien offline o day
					break;
				case 1:
					//bat su kien khi chon nut hien online o day
					break;
				case 2:
					mLayout.startAnimation(amTranslate);
					mLayout.setVisibility(View.VISIBLE);
					isStatus = true;
					break;
				case 3:
					mLayout.startAnimation(amTranslate);
					mLayout.setVisibility(View.VISIBLE);
					isStatus = false;
					break;
				}
			}
		});
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		startFragment(alYahooContact.get(position));
		Log.e("ContactFragment", alYahooContact.get(position).getId());
	}

	@Override
	public void onResume() {
		super.onResume();
		ReadFullContactListNetwork readThread = new ReadFullContactListNetwork(
				mHandler);
		Thread thread = new Thread(readThread);
		thread.start();
	}

	public void startFragment(YahooUser item) {
		((MessengerFragmentActivity) this.getActivity()).sendData(item);
		((MessengerFragmentActivity) this.getActivity()).startFragment(2);
	}
	@Override
	public void onPause() {
		super.onPause();
		mContactAdapter.clear();
	}

	public void onClick(View v) {
		if(v == btnSettting){
			mQAction.show(v);
		}
		if(v == btnEnter){
			if(isStatus){
				//bat su kien cho nut enter(hien tai là cai kinh lup) khi chon status
			}else{
				//bat su kien cho nut enter(hien tai là cai kinh lup) khi chon search
			}
			mLayout.setVisibility(View.INVISIBLE);
		}
	}
}
