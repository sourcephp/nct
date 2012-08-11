package att.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneReceiver extends BroadcastReceiver {

	private OnIncommingCall listener;

	public PhoneReceiver(OnIncommingCall listener) {
		this.listener = listener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

		if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
			// Phone is ringing
			listener.onIncommingCall();

		} else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
			// Call received

		} else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
			// Call Dropped or rejected

		}
	}

	public interface OnIncommingCall {
		public void onIncommingCall();
	}
}