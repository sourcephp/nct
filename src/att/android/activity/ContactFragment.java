package att.android.activity;

import java.util.ArrayList;

import org.openymsg.network.YahooUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import att.android.adapter.ContactListAdapter;
import att.android.bean.Music_Song;
import att.android.model.StartFragment;
import att.android.network.ReadFullContactListNetwork;

import com.example.multiapp.R;

public class ContactFragment extends Fragment implements OnItemClickListener {
	private ListView listContact;
	private ContactListAdapter mContactAdapter;
	private ArrayList<YahooUser> alYahooContact;
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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		alYahooContact = new ArrayList<YahooUser>();

		mContactAdapter = new ContactListAdapter(this.getActivity(), 1,
				alYahooContact);
		listContact = (ListView) this.getView().findViewById(
				R.id.listView_contactList);
		listContact.setOnItemClickListener(this);
		listContact.setAdapter(mContactAdapter);

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// cach truc tiep:
		// Intent intent = new Intent(arg1.getContext(),
		// MessengerFragmentActivity.class);
		// ContactFragment fragment = new ContactFragment();
		// Bundle bundle = new Bundle();
		// bundle.putSerializable("YahooUser", alYahooContact.get(position));
		// fragment.setArguments(bundle);
		// intent.putExtra("YahooUser", bundle);
		// cach qua FragmentActivity
		startFragment(alYahooContact.get(position));
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

}
