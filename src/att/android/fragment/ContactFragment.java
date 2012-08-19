package att.android.fragment;

import java.util.ArrayList;

import org.openymsg.network.YahooUser;
import org.openymsg.network.event.SessionEvent;

import android.annotation.SuppressLint;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import att.android.activity.MessengerFragmentActivity;
import att.android.adapter.FullContactListAdapter;
import att.android.network.LoadFullContactListNetwork;

import com.example.multiapp.R;
import com.quickaction.popup.ActionItem;
import com.quickaction.popup.QuickAction;
import com.quickaction.popup.QuickAction.OnActionItemClickListener;

public class ContactFragment extends BaseMessengerFragment implements OnItemClickListener, OnClickListener {
	private ListView listContact;
	private FullContactListAdapter mContactAdapter;
	private ArrayList<YahooUser> alYahooContact;
	private LoadFullContactListNetwork mLoadContact; 
	private QuickAction mQAction;
	private Button btnSettting;
	private Animation amTranslateDown;
	private Animation amTranslateUp;
	private RelativeLayout mLayout;
	private Button btnEnter;
	private ActionItem acShowOff, acInfo, acOnlyOn, acSearch, acAdd;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			ArrayList<YahooUser> rs = (ArrayList<YahooUser>) msg.obj;
			for (YahooUser itm : rs) {
				mContactAdapter.add(itm);
			}
			mContactAdapter.notifyDataSetChanged();
			listContact.setOnItemClickListener(ContactFragment.this);
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
	
	@SuppressLint("HandlerLeak")
	@Override
	public void initVariables() {
		alYahooContact = new ArrayList<YahooUser>();
		mContactAdapter = new FullContactListAdapter(this.getActivity(), 1,
				alYahooContact);
		mLoadContact = new LoadFullContactListNetwork(mHandler);
		mQAction = new QuickAction(this.getActivity());
		acShowOff = new ActionItem();
		acInfo = new ActionItem();
		acOnlyOn = new ActionItem();
		acSearch = new ActionItem();
		acAdd = new ActionItem();
		amTranslateUp = AnimationUtils.loadAnimation(this.getActivity(), R.anim.translate_move_up);
	}

	@Override
	public void initViews() {
		btnSettting = (Button) this.getView().findViewById(R.id.btn_options);
		btnSettting.setOnClickListener(this);
		listContact = (ListView) this.getView().findViewById(
				R.id.listView_contactList);
		btnEnter = (Button) this.getView().findViewById(R.id.btn_search);
		mLayout =  (RelativeLayout) this.getView().findViewById(R.id.layout_panel);
	}

	@Override
	public void initActions() {
		listContact.setAdapter(mContactAdapter);
		btnEnter.setOnClickListener(this);
		amTranslateDown = AnimationUtils.loadAnimation(this.getActivity(), R.anim.translate_move_down);
		acShowOff.setTitle("Hiện Offline");
		acInfo.setTitle("Đổi status");
		acOnlyOn.setTitle("Hiện Online");
		acSearch.setTitle("Tìm kiếm bạn bè");
		acAdd.setTitle("Thêm bạn");
		mQAction.addActionItem(acShowOff);
		mQAction.addActionItem(acOnlyOn);
		mQAction.addActionItem(acInfo);
		mQAction.addActionItem(acSearch);
		mQAction.addActionItem(acAdd);
		mQAction.setOnActionItemClickListener(new OnActionItemClickListener() {
			
			public void onItemClick(QuickAction source, int pos, int actionId) {
				switch (pos) {
				case 0:
					//TODO: bat su kien cho nut hien offline
					break;
				case 1:
					//TODO: bat su kien cho nut hien online
					break;
				case 2:
					mLayout.startAnimation(amTranslateDown);
					mLayout.setVisibility(View.VISIBLE);
					break;
				case 3:
					mLayout.startAnimation(amTranslateDown);
					mLayout.setVisibility(View.VISIBLE);
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
		mLayout.setVisibility(View.INVISIBLE);
		Thread thread = new Thread(mLoadContact);
		thread.start();
	}
	
	public void onClick(View v) {
		if(v == btnSettting){
			mQAction.show(v);
		}
		if(v == btnEnter){
			//TODO: bat su kien cho nut doi status hoac search
			mLayout.startAnimation(amTranslateUp);
			mLayout.setVisibility(View.INVISIBLE);
		}
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

	@Override
	public void onMessageReceived(SessionEvent event) {
		super.onMessageReceived(event);
	
	}

}
