package att.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneReceiver extends BroadcastReceiver {

	private OnIncommingCall listenerStartCall;
	private OnRejectingCall listenerEndCall;

	public PhoneReceiver(OnIncommingCall listenerIncomming,OnRejectingCall listenerReject) {
		this.listenerStartCall = listenerIncomming;
		this.listenerEndCall = listenerReject;
	}


	@Override
	public void onReceive(Context context, Intent intent) {

		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

		if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
			// Phone is ringing
			listenerStartCall.onIncommingCall();

		} else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
			// Call received

		} else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
			// Call Dropped or rejected
			listenerEndCall.onRejectingCall();
		}
	}

	public interface OnIncommingCall {
		public void onIncommingCall();
	}

	public interface OnRejectingCall {
		public void onRejectingCall();
	}
}