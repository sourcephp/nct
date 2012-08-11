package att.android.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.openymsg.network.AccountLockedException;
import org.openymsg.network.FailedLoginException;
import org.openymsg.network.LoginRefusedException;
import org.openymsg.network.Session;
import org.openymsg.network.Status;
import org.openymsg.network.YahooUser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import att.android.bean.Music_Song;
import att.android.model.Logger;
import att.android.model.YGeneralHandler;
import att.android.util.StartFragment;

import com.example.multiapp.R;

public class LoginFragment extends Fragment implements OnItemClickListener,
		OnClickListener, Runnable, StartFragment {
	private static final String TAG = "LoginFragment";
	//ko hieu tai sao phai static moi chay duoc???
	private static EditText edtxtUserName;
	private static EditText edtxtPass;
	private CheckBox chBoxSave;
	private CheckBox chBoxHide;
	private Button btnLogin;
	Session instanceofSession = Session.getInstance();
	YGeneralHandler sessionListenerInstance = YGeneralHandler.getInstance();
//	WindowManager.LayoutParams lpWindow;
//	public ProgressDialog waitDialog;
//	public Context context;


	public static Fragment newInstance(Context context) {
		LoginFragment f = new LoginFragment();

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_login,
				null);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		edtxtUserName = (EditText) this.getView().findViewById(R.id.edtxt_username);
		edtxtPass = (EditText) this.getView().findViewById(R.id.edtxt_pass);
		chBoxSave = (CheckBox) this.getView().findViewById(R.id.chbox_save);
		chBoxHide = (CheckBox) this.getView().findViewById(R.id.chbox_hide);
		btnLogin = (Button) this.getView().findViewById(R.id.btn_googleLogin);
		btnLogin.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v == btnLogin) {
		
//			showWaitDialog();
			
			new Thread(new LoginFragment()).start();
			((StartFragment)getActivity()).startFragment(1);
		}
				
			
			
			
		
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
//	public void showWaitDialog() {
//		lpWindow = new WindowManager.LayoutParams();
//		lpWindow.flags = WindowManager.LayoutParams.FLAG_DITHER;
//		lpWindow.dimAmount = 0.1f;
//		lpWindow.alpha = 0.5f;
//		waitDialog = new ProgressDialog(context);
//		waitDialog.getWindow().setAttributes(lpWindow);
//		waitDialog = ProgressDialog.show(context, "", "Loading...", true);
//	    }

	public void run() {
		
		instanceofSession.addSessionListener(sessionListenerInstance);	
		
		String strUserName = edtxtUserName.getText().toString().trim();
		Log.i("Username", strUserName);
		String strPass = edtxtPass.getText().toString().trim();
		Log.i("Password", strPass);
		
//		boolean bSave = chBoxSave.hasSelection();
//		boolean bHide = chBoxHide.hasSelection();

//		String strUserName="tommy_t9195";
//		String strPass="xfile91yh";
		
		if ("".equals(strUserName) || "".equals(strPass)) {
		} else {
			Log.i(TAG, "Login start");
			try {
				instanceofSession.login(strUserName, strPass);
				instanceofSession.setStatus(Status.AVAILABLE);
			} catch (AccountLockedException e) {
				Log.d(TAG, "Account has locked!");
				e.printStackTrace();
			} catch (IllegalStateException e) {
				Log.d(TAG, "login false");
				e.printStackTrace();
			} catch (LoginRefusedException e) {
				Log.d(TAG, "login false");
				e.printStackTrace();
			} catch (FailedLoginException e) {
				Log.d(TAG, "Login false");
				e.printStackTrace();
			} catch (IOException e) {
				Log.d(TAG, "IO error");
				e.printStackTrace();
			}
			Log.i("TAG", "Login finish");
		}
	}

	public void startFragment(int i) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		instanceofSession.removeSessionListener(sessionListenerInstance);
		Logger.e(TAG, "onPause");
	}

	public void startFragment1(ArrayList<Music_Song> item, int position,
			boolean bool) {
		// TODO Auto-generated method stub
		
	}

	public void startFragment2(ArrayList<YahooUser> item, int position,
			boolean bool) {
		// TODO Auto-generated method stub
		
	}
}
