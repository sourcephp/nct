package att.android.fragment;

import java.io.IOException;

import org.openymsg.network.AccountLockedException;
import org.openymsg.network.FailedLoginException;
import org.openymsg.network.LoginRefusedException;
import org.openymsg.network.SessionState;
import org.openymsg.network.Status;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import att.android.activity.MessengerFragmentActivity;
import att.android.model.Logger;

import com.example.multiapp.R;

public class LoginFragment extends BaseMessengerFragment implements
		OnClickListener, Runnable {
	private static final String TAG = "LoginFragment";
	// ko hieu tai sao phai static moi chay duoc???
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
		edtxtUserName = (EditText) this.getView().findViewById(
				R.id.edtxt_username);
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
		singletonSession.removeSessionListener(sessionListener);
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
			//
//			((MessengerFragmentActivity) this.getActivity()).startFragment(1);
		}

	}

	public void run() {

		String strUserName = edtxtUserName.getText().toString().trim();
		String strPass = edtxtPass.getText().toString().trim();
		Handler handler = new Handler(Looper.getMainLooper());

		// boolean bSave = chBoxSave.hasSelection();
		// boolean bHide = chBoxHide.hasSelection();

		if ("".equals(strUserName) || "".equals(strPass)) {
			handler.post(new Runnable() {
			    public void run()
			    {
			    	Toast.makeText(getActivity(), "Ten tai khoan va password khong duoc de trong!", Toast.LENGTH_LONG).show();
			    }
			});
			
		} else {
			Log.i(TAG, "Login start");
			try {
				singletonSession.login(strUserName, strPass);
				singletonSession.setStatus(Status.AVAILABLE);
			} catch (AccountLockedException e) {
				handler.post(new Runnable() {
				    public void run()
				    {
				    	Toast.makeText(getActivity(), "Tai khoan da bi khoa!", Toast.LENGTH_LONG).show();
				    }
				});
				
				e.printStackTrace();
			} catch (IllegalStateException e) {
				handler.post(new Runnable() {
				    public void run()
				    {
				    	Toast.makeText(getActivity(), "Thong tin dang nhap ko dung!", Toast.LENGTH_LONG).show();
				    }
				});
				e.printStackTrace();
			} catch (LoginRefusedException e) {
				handler.post(new Runnable() {
				    public void run()
				    {
				    	Toast.makeText(getActivity(), "Thong tin dang nhap ko dung!", Toast.LENGTH_LONG).show();
				    }
				});
				e.printStackTrace();
			} catch (FailedLoginException e) {
				handler.post(new Runnable() {
				    public void run()
				    {
				    	Toast.makeText(getActivity(), "Thong tin dang nhap ko dung!", Toast.LENGTH_LONG).show();
				    }
				});
				e.printStackTrace();
			} catch (IOException e) {
				Log.d(TAG, "IO error");
				e.printStackTrace();
			}
			if (singletonSession.getSessionStatus().compareTo(
					SessionState.LOGGED_ON) == 0) {
				// checkSession();
				if (!isCompletedLoadData) {
					Logger.e(TAG, "Login loading data....");
					synchronized (LoadDataLock) {
						try {
							LoadDataLock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Logger.e(TAG, "Login data loaded");
				}
			}
		}
	}

}
