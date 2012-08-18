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
import android.widget.RelativeLayout;
import att.android.activity.MessengerFragmentActivity;
import att.android.adapter.ContactListAdapter;
import att.android.network.ReadFullContactListNetwork;

import com.example.multiapp.R;
import com.quickaction.popup.ActionItem;
import com.quickaction.popup.QuickAction;
import com.quickaction.popup.QuickAction.OnActionItemClickListener;

public class ContactFragment extends BaseMessengerFragment implements OnItemClickListener, OnClickListener {
	private ListView listContact;
	private ContactListAdapter mContactAdapter;
	private ArrayList<YahooUser> alYahooContact;
	private QuickAction mQAction;
	private Button btnSettting;
	private Animation amTranslate;
	private RelativeLayout mLayout,mPanel;
	private Button btnSearch, btnChangeStatus;
	private boolean isStatus = false;
	private ActionItem acShowOff, acInfo, acOnlyOn, acSearch;
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
	public void initVariables() {
		alYahooContact = new ArrayList<YahooUser>();
		mContactAdapter = new ContactListAdapter(this.getActivity(), 1,
				alYahooContact);
		mQAction = new QuickAction(this.getActivity());
		acShowOff = new ActionItem();
		acInfo = new ActionItem();
		acOnlyOn = new ActionItem();
		acSearch = new ActionItem();
		
	}

	@Override
	public void initViews() {
		btnSettting = (Button) this.getView().findViewById(R.id.btn_options);
		btnSettting.setOnClickListener(this);
		listContact = (ListView) this.getView().findViewById(
				R.id.listView_contactList);
		btnChangeStatus = (Button) this.getView().findViewById(R.id.btn_changestatus);
		btnSearch = (Button) this.getView().findViewById(R.id.btn_search);
		mPanel =  (RelativeLayout) this.getView().findViewById(R.id.button_panel);
		mLayout =  (RelativeLayout) this.getView().findViewById(R.id.layout_panel);
	}

	@Override
	public void initActions() {
		listContact.setOnItemClickListener(this);
		listContact.setAdapter(mContactAdapter);
		btnChangeStatus.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		amTranslate = AnimationUtils.loadAnimation(this.getActivity(), R.anim.translate_top);
		mPanel.setVisibility(View.INVISIBLE);
		mLayout.setVisibility(View.INVISIBLE);
		acShowOff.setTitle("Hiện Offline");
		acInfo.setTitle("Đổi status");
		acOnlyOn.setTitle("Hiện Online");
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
					mPanel.setVisibility(View.VISIBLE);
					btnChangeStatus.setVisibility(View.VISIBLE);
					btnSearch.setVisibility(View.GONE);
					break;
				case 3:
					mLayout.startAnimation(amTranslate);
					mLayout.setVisibility(View.VISIBLE);
					mPanel.setVisibility(View.VISIBLE);
					btnChangeStatus.setVisibility(View.GONE);
					btnSearch.setVisibility(View.VISIBLE);
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
		if(v == btnSearch){
			//TODO: bat su kien
			mLayout.setVisibility(View.INVISIBLE);
		}
		if(v == btnChangeStatus){
			
			mLayout.setVisibility(View.INVISIBLE);
		}
	}

}
