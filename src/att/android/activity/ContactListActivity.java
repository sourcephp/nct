package att.android.activity;

import java.util.ArrayList;

import com.example.multiapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import att.android.adapter.ContactListAdapter;
import att.android.bean.Account;
import att.android.network.ReadContactListNetwork;

public class ContactListActivity extends Activity implements OnItemClickListener{
	private ListView listContact;
	private ContactListAdapter mContactAdapter;
	private ArrayList<Account> arlAccContact;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			@SuppressWarnings("unchecked")
			ArrayList<Account> rs = (ArrayList<Account>) msg.obj;
			for(Account itm : rs){
				mContactAdapter.add(itm);
			}
			mContactAdapter.notifyDataSetChanged();
			listContact.setOnItemClickListener(ContactListActivity.this);
		};
	};
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_contact_list);
	arlAccContact = new ArrayList<Account>();
	mContactAdapter = new ContactListAdapter(this.getApplicationContext(), 1, arlAccContact);
	listContact = (ListView) this.findViewById(R.id.listView_contactList);
	listContact.setOnItemClickListener(this);
	listContact.setAdapter(mContactAdapter);
}
public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
	mContactAdapter.getItem(position);
	Account item = mContactAdapter.getItem(position);
	String strUserName = item.getStrName();
	Intent i = new Intent(ContactListActivity.this, ChatJointActivity.class);
	i.putExtra("USERNAM", strUserName);
	startActivity(i);
}
@Override
protected void onResume() {
	super.onResume();
	ReadContactListNetwork readThread = new ReadContactListNetwork(mHandler);
	Thread thread = new Thread(readThread);
	thread.start();
}
}
