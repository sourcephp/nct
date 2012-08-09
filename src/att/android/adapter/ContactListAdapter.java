package att.android.adapter;

import java.util.ArrayList;

import org.openymsg.network.YahooUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.multiapp.R;
import com.loopj.android.image.SmartImageView;

public class ContactListAdapter extends ArrayAdapter<YahooUser> implements Filterable{
	private LayoutInflater mInflater;
	private String lastName;
	private String firstName;

	public ContactListAdapter(Context context, int textViewResourceId,
			ArrayList<YahooUser> yahooUsers) {
		super(context, textViewResourceId, yahooUsers);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder mHolder;
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.contact_layout_listview,
					null);
			mHolder = new viewHolder();
			mHolder.imgAccount = (SmartImageView) convertView
					.findViewById(R.id.imgView_account);
			mHolder.strUsername = (TextView) convertView
					.findViewById(R.id.txtView_username);
			mHolder.strStatus = (TextView) convertView
					.findViewById(R.id.txtView_status);
			convertView.setTag(mHolder);
		} else {
			mHolder = (viewHolder) convertView.getTag();
		}
		YahooUser item = this.getItem(position);
			String customStatus = item.getCustomStatus();
			String customStatusMsg = item.getCustomStatusMessage();
			mHolder.strStatus.setText(customStatusMsg);
			//TODO: Get Avatar later
			//TODO: change display follow status later
			lastName = item.getLastName();
			firstName = item.getFirstName();
			if ("null".equalsIgnoreCase(lastName) || lastName == null)
				lastName = "";
			if ("null".equalsIgnoreCase(firstName) || firstName == null)
				firstName = "";
			String YMid = item.getId();
			if ("".equalsIgnoreCase(firstName+lastName)) {
				mHolder.strUsername.setText(YMid);
			} else {
				mHolder.strUsername.setText(firstName + lastName);
			}
		
		mHolder.imgAccount.setImageUrl("https://docs.google.com/open?id=0BxqXERuaU046SGxCN1lPanZFa2s");
//		mHolder.strUsername.setText(acc.getStrName());
//		mHolder.strStatus.setText(acc.getStrStatus());
		return convertView;
	}
	
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return super.getFilter();
	}

	private class viewHolder {
		private SmartImageView imgAccount;
		private TextView strUsername, strStatus;

	}
}
