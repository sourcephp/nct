package att.android.fragment;

import java.io.IOException;

import org.openymsg.network.AccountLockedException;
import org.openymsg.network.FailedLoginException;
import org.openymsg.network.LoginRefusedException;
import org.openymsg.network.Session;
import org.openymsg.network.Status;

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
import att.android.activity.MessengerFragmentActivity;
import att.android.model.Logger;

import com.example.multiapp.R;

public class LoginFragment extends BaseMessengerFragment implements OnClickListener, Runnable{
	private static final String TAG = "LoginFragment";
	//ko hieu tai sao phai static moi chay duoc???
	private static EditText edtxtUserName;
	private static EditText edtxtPass;
	private CheckBox chBoxSave;
	private CheckBox chBoxHide;
	private Button btnLogin;


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
	public void initVariables() {
		sessionListener = new YMEventHandler();
	}

	@Override
	public void initViews() {
		edtxtUserName = (EditText) this.getView().findViewById(R.id.edtxt_username);
		edtxtPass = (EditText) this.getView().findViewById(R.id.edtxt_pass);
		chBoxSave = (CheckBox) this.getView().findViewById(R.id.chbox_save);
		chBoxHide = (CheckBox) this.getView().findViewById(R.id.chbox_hide);
		btnLogin = (Button) this.getView().findViewById(R.id.btn_googleLogin);
	}

	@Override
	public void initActions() {
		btnLogin.setOnClickListener(this);		
	}
	
	@Override
	public void onPause() {
		super.onPause();
//		singletonSession.removeSessionListener(singletonSessionListener);
		Logger.e(TAG, "onPause");
	}
	@Override
	public void onResume() {
		super.onResume();
		singletonSession.addSessionListener(sessionListener);
	}

	public void onClick(View v) {
		if (v == btnLogin) {			
			new Thread(new LoginFragment()).start();
			((MessengerFragmentActivity) this.getActivity()).startFragment(1);
		}
					
	}

	public void run() {
		
		String strUserName = edtxtUserName.getText().toString().trim();
		String strPass = edtxtPass.getText().toString().trim();
		
//		boolean bSave = chBoxSave.hasSelection();
//		boolean bHide = chBoxHide.hasSelection();

//		String strUserName="justslim91";
//		String strPass="qwerty";
		
		if ("".equals(strUserName) || "".equals(strPass)) {
		} else {
			Log.i(TAG, "Login start");
			try {
				singletonSession.login(strUserName, strPass);
				singletonSession.setStatus(Status.AVAILABLE);
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
			Log.i("TAG", "Data is loading...");
		}
	}
	
}
