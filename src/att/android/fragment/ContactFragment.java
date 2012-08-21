package att.android.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.openymsg.network.Status;
import org.openymsg.network.YahooUser;
import org.openymsg.network.event.SessionEvent;
import org.openymsg.roster.Roster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import att.android.activity.MessengerFragmentActivity;
import att.android.adapter.FullContactListAdapter;
import att.android.network.ManageYMUserHasAvatarNetwork;
import att.android.network.RosterHandler;

import com.example.multiapp.R;
import com.quickaction.popup.ActionItem;
import com.quickaction.popup.QuickAction;
import com.quickaction.popup.QuickAction.OnActionItemClickListener;

public class ContactFragment extends BaseMessengerFragment implements
		OnItemClickListener, OnClickListener {
	
	private ListView listContact;
	private FullContactListAdapter mContactAdapter;
	private QuickAction mQAction;
	private Button btnSettting;
	private Animation amTranslateDown;
	private Animation amTranslateUp;
	private RelativeLayout mLayout;
	private Button btnEnter;
	private EditText edtGeneral;
	private ActionItem acShowOff, acInfo, acOnlyOn, acSearch, acAdd, acLogout;
	
	//define some function for only one button
	protected static int button;
	protected static final int CHANGESTATUS = 2;
	protected static final int FINDFRIEND = 3;
	protected static final int ADDFRIEND = 4;
	protected static final int LOGOUT = 5;
	
	TextWatcher watcher = new TextWatcher() {
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			mContactAdapter.getFilter().filter(s);
		}
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub
		}
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
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
		mContactAdapter = new FullContactListAdapter(this.getActivity(), 1,
				new ArrayList<YahooUser>());
		sessionListener = new YMEventHandler();
		mQAction = new QuickAction(this.getActivity());
		acShowOff = new ActionItem();
		acInfo = new ActionItem();
		acOnlyOn = new ActionItem();
		acSearch = new ActionItem();
		acAdd = new ActionItem();
		acLogout = new ActionItem();
		amTranslateUp = AnimationUtils.loadAnimation(this.getActivity(),
				R.anim.translate_move_up);
	}

	@Override
	public void initViews() {
		btnSettting = (Button) this.getView().findViewById(R.id.btn_options);
		btnSettting.setOnClickListener(this);
		listContact = (ListView) this.getView().findViewById(
				R.id.listView_contactList);
		btnEnter = (Button) this.getView().findViewById(R.id.btn_search);
		mLayout = (RelativeLayout) this.getView().findViewById(
				R.id.layout_panel);
		edtGeneral = (EditText) this.getView().findViewById(R.id.edtxt_General);
	}

	@Override
	public void initActions() {
		listContact.setAdapter(mContactAdapter);
		listContact.setOnItemClickListener(this);
		btnEnter.setOnClickListener(this);
		amTranslateDown = AnimationUtils.loadAnimation(this.getActivity(),
				R.anim.translate_move_down);
		acShowOff.setTitle("Hiện Offline");
		acInfo.setTitle("Đổi status");
		acOnlyOn.setTitle("Hiện Online");
		acSearch.setTitle("Tìm kiếm bạn bè");
		acAdd.setTitle("Thêm bạn");
		acLogout.setTitle("Logout");
		mQAction.addActionItem(acShowOff);
		mQAction.addActionItem(acOnlyOn);
		mQAction.addActionItem(acInfo);
		mQAction.addActionItem(acSearch);
		mQAction.addActionItem(acAdd);
		mQAction.addActionItem(acLogout);
		mQAction.setOnActionItemClickListener(new OnActionItemClickListener() {

			public void onItemClick(QuickAction source, int pos, int actionId) {
				switch (pos) {
				case 0: //show offline
					if(settings_show_offlines == false) {
						settings_show_offlines = true;
						mContactAdapter.clear();
						loadDataTolist();
					}else if(settings_show_offlines == true){
						mContactAdapter.clear();
						loadDataTolist();
					}
					break;
				case 1: //show online only
					if(settings_show_offlines == true) {
						settings_show_offlines = false;
						mContactAdapter.clear();
						loadDataTolist();
					}else if(settings_show_offlines == false){
						mContactAdapter.clear();
						loadDataTolist();
					}
					break;
				case 2: //Change status
					mLayout.startAnimation(amTranslateDown);
					mLayout.setVisibility(View.VISIBLE);
					button = CHANGESTATUS;
//					if (edtGeneral.getText().toString().equals("")==false) edtGeneral.setText("");
					break;
				case 3: //Find friend
					mLayout.startAnimation(amTranslateDown);
					mLayout.setVisibility(View.VISIBLE);
					button = FINDFRIEND;
					listContact.setTextFilterEnabled(true);
					edtGeneral.addTextChangedListener(watcher);
					if (edtGeneral.getText().toString().equals("")==false) edtGeneral.setText("");
					break;
				case 4: //add friend
					mLayout.startAnimation(amTranslateDown);
					mLayout.setVisibility(View.VISIBLE);
					button = ADDFRIEND;
//					if (edtGeneral.getText().toString().equals("")==false) edtGeneral.setText("");
					break;
				case 5: //Logout
					try {
						singletonSession.logout();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mContactAdapter.clear();
					Toast.makeText(getActivity(),"Thoat khoi Yahoo tu day!", Toast.LENGTH_LONG).show();
					break;
				}
				
			}
		});
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		startFragment(mContactAdapter.getItem(position));
		Log.e("ContactFragment", alYahooUser.get(position).getId());
	}

	@Override
	public void onResume() {
		super.onResume();
		// Search and update avatar always invisible
		mLayout.setVisibility(View.INVISIBLE);

		singletonSession.addSessionListener(sessionListener);

		currentClass = this.getClass();

		new RosterHandler(handler).start();
		if (isNeedUpdateFromRoster) {
			isNeedUpdateFromRoster = false;
			updateFullContactList();
		}

		if (isNeedUpdateAvatar) {
			updateAvatar();
		}
	}

	public void onClick(View v) {
		if (v == btnSettting) {
			mQAction.show(v);
		}
		if (v == btnEnter) {
			mLayout.startAnimation(amTranslateUp);
			mLayout.setVisibility(View.INVISIBLE);
			if (button == CHANGESTATUS) {
				// TODO: bat su kien
			} else if (button == FINDFRIEND) {
				edtGeneral.removeTextChangedListener(watcher);
				listContact.setTextFilterEnabled(false);
			} else if (button == ADDFRIEND) {
				//TODO: add friend kho. Lam sau!
			}
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

	@Override
	public void updateFullContactList() {
		super.updateFullContactList();
		handler.post(new Runnable() {
			public void run() {
				Roster roster = singletonSession.getRoster();
				synchronized (BaseMessengerFragment.UpdateUILock) {
					if (mContactAdapter == null) {
						try {
							BaseMessengerFragment.UpdateUILock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					getContactfrom(roster);
				}
				mContactAdapter.clear();
				loadDataTolist();
				new ManageYMUserHasAvatarNetwork(handler).start();
			}

		});
	}

	private void getContactfrom(Roster roster) {
		alYahooUser.clear();
		if (alYahooUser.size() == 0) {
			for (Iterator<YahooUser> i = roster.iterator(); i.hasNext();) {
				YahooUser user = i.next();
				alYahooUser.add(user);

			}
		}
	}

	protected void loadDataTolist() {
		synchronized (UpdateUILock) {
			boolean isAdd = false;
			if (!settings_show_offlines) {
				for (int j = 0; j < alYahooUser.size(); j++) {
					YahooUser yahooUser = alYahooUser.get(j);
					if (yahooUser.getStatus().compareTo(Status.AVAILABLE) == 0) {
						isAdd = true;
					} else if (yahooUser.getStatus().compareTo(Status.CUSTOM) == 0) {
						isAdd = true;
					} else if (yahooUser.getStatus().compareTo(Status.BUSY) == 0) {
						isAdd = true;
					}
					if (isAdd) {
						mContactAdapter.add(yahooUser);
						mContactAdapter.setNotifyOnChange(true);
						isAdd = false;
					}
				}
			} else {
				for (int j = 0; j < alYahooUser.size(); j++) {
					YahooUser yahooUser = alYahooUser.get(j);
					mContactAdapter.add(yahooUser);
					mContactAdapter.setNotifyOnChange(true);
					isAdd = false;
				}
			}
			UpdateUILock.notifyAll();
		}
	}

	@Override
	public void updateAvatar() {
		super.updateAvatar();
		isNeedUpdateAvatar = false;
		handler.post(new Runnable() {
			public void run() {
				synchronized (UpdateUILock) {
					mContactAdapter.clear();
					mContactAdapter.setNotifyOnChange(true);
					loadDataTolist();
				}
			}
		});
	}

}
