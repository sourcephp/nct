package att.android.adapter;

import java.util.ArrayList;

import org.openymsg.network.Status;
import org.openymsg.network.YahooUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
			mHolder.realAvatar = (ImageView) convertView
					.findViewById(R.id.imgView_RealAvatar);
			mHolder.userID = (TextView) convertView
					.findViewById(R.id.txtView_username);
			mHolder.statusMessage = (TextView) convertView
					.findViewById(R.id.txtView_StatusMessage);
			mHolder.YMstatus_icon = (ImageView) convertView
					.findViewById(R.id.imgView_ActionStatus);
			mHolder.imgView_Arrow = (ImageView) convertView
					.findViewById(R.id.imgView_arrow);
			mHolder.view_separate = convertView.findViewById(R.id.view_separate);
			
			convertView.setTag(mHolder);
		} else {
			mHolder = (viewHolder) convertView.getTag();
			mHolder.realAvatar.setBackgroundResource(R.drawable.friendlist_avatar);
		}
		YahooUser item = this.getItem(position);
			String customActionStatus = item.getCustomStatus();
			String customStatusMessage = item.getCustomStatusMessage();
			
			//TODO: set latest chat if users have incoming messages
			//TODO: set VISIBLE for view_saparate and imgView_Arrow if users have incoming messages or GONE if not
			if(item.getStatus().compareTo(Status.AVAILABLE)==0){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_online);
				mHolder.statusMessage.setText("");
			}
			else if(item.getStatus().compareTo(Status.BUSY)==0){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_busy);
				mHolder.statusMessage.setText("");
			}
			else if(item.getStatus().compareTo(Status.INVISIBLE)==0){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_offline);
				mHolder.statusMessage.setText("");
			}
			else if(item.getStatus().compareTo(Status.CUSTOM)==0 && "1".equalsIgnoreCase(customActionStatus)){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_busy);
				mHolder.statusMessage.setText(customStatusMessage);
			}
			else if(item.getStatus().compareTo(Status.CUSTOM)==0 && "0".equalsIgnoreCase(customActionStatus)){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_online);
				mHolder.statusMessage.setText(customStatusMessage);
			}
			
			lastName = item.getLastName();
			firstName = item.getFirstName();
			if ("null".equalsIgnoreCase(lastName) || lastName == null)
				lastName = "";
			if ("null".equalsIgnoreCase(firstName) || firstName == null)
				firstName = "";
			String YMuserid = item.getId();
			if ("".equalsIgnoreCase(firstName+lastName)) {
				mHolder.userID.setText(YMuserid);
			} else {
				mHolder.userID.setText(firstName + lastName);
			}
			
			if (item.getDrawable() != null) {
				mHolder.realAvatar.setBackgroundDrawable(item.getDrawable());
			}
			
		
		return convertView;
	}
	
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return super.getFilter();
	}

	private class viewHolder {
		private ImageView realAvatar, YMstatus_icon, imgView_Arrow;
		private View view_separate;
		private TextView userID, statusMessage;

	}
}
