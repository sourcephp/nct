package att.android.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.openymsg.network.Session;
import org.openymsg.network.YahooUser;
import org.openymsg.roster.Roster;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import att.android.fragment.BaseMessengerFragment;
import att.android.model.YHandlerConstant;

public class ManageYMUserHasAvatarNetwork extends Thread implements YHandlerConstant {
	private String urlGetAvatar = "http://img.msg.yahoo.com/avatar.php?yids=";
	private Handler handler;
	private Roster roster;

	public ManageYMUserHasAvatarNetwork(Handler handler, Roster roster) {
		this.handler = handler;
		this.roster = roster;
	}

	public void run() {
		int index = 0;
		BaseMessengerFragment.alYahooUser.clear();
		for (Iterator<YahooUser> iterator = roster.iterator(); iterator
				.hasNext();) {
			YahooUser yahooUser = (YahooUser) iterator.next();
			urlGetAvatar = urlGetAvatar + yahooUser.getId() + "&format=jpg";
			try {
				InputStream is = (InputStream) fetch(urlGetAvatar);
				Drawable d = Drawable.createFromStream(is, "src" + index);
				index++;
				yahooUser.setDrawable(d);
				BaseMessengerFragment.alYahooUser.add(yahooUser);
				urlGetAvatar = "http://img.msg.yahoo.com/avatar.php?yids=";
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		handler.sendEmptyMessage(UPDATE_AVATAR);
	}

	public Object fetch(String address) throws MalformedURLException,
			IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}

}