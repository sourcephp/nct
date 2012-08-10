package att.android.adapter;

import java.util.List;

import org.openymsg.network.YahooUser;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ConversationAdapter extends ArrayAdapter<YahooUser> {

	public ConversationAdapter(Context context, int resource,
			int textViewResourceId, List<YahooUser> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

}
