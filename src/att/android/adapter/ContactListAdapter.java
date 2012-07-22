package att.android.adapter;

import java.util.ArrayList;

import com.example.multiapp.R;
import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import att.android.bean.Account;

public class ContactListAdapter extends ArrayAdapter<Account> {
	private LayoutInflater mInflater;

	public ContactListAdapter(Context context, int textViewResourceId,
			ArrayList<Account> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder mHolder;
		Account acc = this.getItem(position);
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
		mHolder.imgAccount.setImageUrl("https://docs.google.com/open?id=0BxqXERuaU046SGxCN1lPanZFa2s");
		mHolder.strUsername.setText(acc.getStrName());
		mHolder.strStatus.setText(acc.getStrStatus());
		return convertView;
	}

	private class viewHolder {
		private SmartImageView imgAccount;
		private TextView strUsername, strStatus;

	}
}
