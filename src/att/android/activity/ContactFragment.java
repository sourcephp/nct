package att.android.activity;

import java.util.ArrayList;

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
import att.android.bean.Account;
import att.android.network.ReadContactListNetwork;

import com.example.multiapp.R;

public class ContactFragment extends Fragment implements OnItemClickListener {
	private ListView listContact;
	private ContactListAdapter mContactAdapter;
	private ArrayList<Account> arlAccContact;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			ArrayList<Account> rs = (ArrayList<Account>) msg.obj;
			for (Account itm : rs) {
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
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_contact_list,
				null);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		arlAccContact = new ArrayList<Account>();
		
		mContactAdapter = new ContactListAdapter(this.getActivity(),
				1, arlAccContact);
		listContact = (ListView) this.getView().findViewById(R.id.listView_contactList);
		listContact.setOnItemClickListener(this);
		listContact.setAdapter(mContactAdapter);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		mContactAdapter.getItem(position);
		Account item = mContactAdapter.getItem(position);
		String strUserName = item.getStrName();
		Intent i = new Intent(this.getActivity(), ChatJointFragment.class);
		i.putExtra("USERNAM", strUserName);
		startActivity(i);
	}

	@Override
	public void onResume() {
		super.onResume();
		ReadContactListNetwork readThread = new ReadContactListNetwork(mHandler);
		Thread thread = new Thread(readThread);
		thread.start();
	}
}
