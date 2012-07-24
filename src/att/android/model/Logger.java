package att.android.model;

import android.util.Log;

public class Logger {
	static boolean isLog = true;
	public static void e(String TAG,String mess){
		if(isLog)
			Log.e(TAG,mess);
	}
	public static void w(String TAG,String mess){
		if(isLog)
			Log.w(TAG,mess);
	}
}
