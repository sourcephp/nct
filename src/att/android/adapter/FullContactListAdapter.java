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

public class FullContactListAdapter extends ArrayAdapter<YahooUser> implements Filterable{
	private LayoutInflater mInflater;
	private String lastName;
	private String firstName;
	private String urlGetAvatar = "http://img.msg.yahoo.com/avatar.php?yids=";

	public FullContactListAdapter(Context context, int textViewResourceId,
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
			mHolder.sub_panel = (RelativeLayout) convertView.findViewById(R.id.sub_panel);
			mHolder.realAvatar = (SmartImageView) convertView
					.findViewById(R.id.imgView_RealAvatar);
			mHolder.userID = (TextView) convertView
					.findViewById(R.id.txtView_username);
			mHolder.statusMessage = (TextView) convertView
					.findViewById(R.id.txtView_StatusMessage);
			mHolder.YMstatus_icon = (ImageView) convertView
					.findViewById(R.id.imgView_ActionStatus);
			mHolder.imgView_Arrow =  (ImageView) convertView
					.findViewById(R.id.imgView_arrow);
			mHolder.view_separate = convertView.findViewById(R.id.view_separate);
			mHolder.incomingMsg = (TextView) convertView.findViewById(R.id.notification);
			
			convertView.setTag(mHolder);
		} else {
			mHolder = (viewHolder) convertView.getTag();
		}
		YahooUser item = this.getItem(position);
			String customActionStatus = item.getCustomStatus();
			String customStatusMessage = item.getCustomStatusMessage();
			boolean isLoadAvatar = false;
			
			//TODO: set latest chat if users have incoming messages
			if(item.isOnChat()==true){
				mHolder.sub_panel.setVisibility(View.VISIBLE);
			} else{
				mHolder.sub_panel.setVisibility(View.GONE);
			}
			//TODO: set VISIBLE for view_saparate and imgView_Arrow if users have incoming messages or GONE if not
			if(item.getStatus().compareTo(Status.AVAILABLE)==0){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_online);
				mHolder.statusMessage.setText("");
				isLoadAvatar = true;
			}
			else if(item.getStatus().compareTo(Status.BUSY)==0){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_busy);
				mHolder.statusMessage.setText("");
				isLoadAvatar = true;
			}
			else if(item.getStatus().compareTo(Status.INVISIBLE)==0){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_offline);
				mHolder.statusMessage.setText("");
				isLoadAvatar = false;
			}
			else if(item.getStatus().compareTo(Status.CUSTOM)==0 && "1".equalsIgnoreCase(customActionStatus)){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_busy);
				mHolder.statusMessage.setText(customStatusMessage);
				isLoadAvatar = true;
			}
			else if(item.getStatus().compareTo(Status.CUSTOM)==0 && "0".equalsIgnoreCase(customActionStatus)){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_online);
				mHolder.statusMessage.setText(customStatusMessage);
				isLoadAvatar = true;
			} 
			else if (item.getStatus().compareTo(Status.OFFLINE)==0){
				mHolder.YMstatus_icon.setBackgroundResource(R.drawable.ic_yahoo_offline);
				mHolder.statusMessage.setText("");
				isLoadAvatar = false;
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
			if (isLoadAvatar) {
					mHolder.realAvatar.setImageUrl(urlGetAvatar + item.getId() + "&format=jpg");
			} else {
				mHolder.realAvatar.setBackgroundResource(R.drawable.friendlist_avatar);
			}
			
			
		
		return convertView;
	}
	

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return super.getFilter();
	}

	private class viewHolder {
		private SmartImageView realAvatar;
		private ImageView YMstatus_icon,imgView_Arrow;
		private View view_separate;
		private TextView userID, statusMessage, incomingMsg;
		private RelativeLayout sub_panel;

	}
}
