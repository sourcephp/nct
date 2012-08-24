package att.android.fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openymsg.network.AccountLockedException;
import org.openymsg.network.FailedLoginException;
import org.openymsg.network.LoginRefusedException;
import org.openymsg.network.SessionState;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import att.android.util.DataUltils;

import com.example.multiapp.R;

public class LoginFragment extends BaseMessengerFragment implements
		OnClickListener {
	private static final String TAG = "LoginFragment";
	// ko hieu tai sao phai static moi chay duoc???
	private static EditText edtxtUserName;
	private static EditText edtxtPass;
	private CheckBox chBoxSave;
	private CheckBox chBoxHide;
	private Button btnLogin;
	private CheckAcc checkAcc;
	
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
		if(DataUltils.checkStoryFromInternalStorage("account.yahoo_id.bin", getActivity())){
			String temp = getFile("account.yahoo_id.bin");
			String[] acc = temp.split(",");
			edtxtUserName.setText(acc[0]);
			edtxtPass.setText(acc[1]);
		}
	}
	private String getFile(String filePath){
		InputStream is = DataUltils.openFileFromInternal(getActivity(), filePath);
		Log.e("path----", filePath);
		int i;
		byte byt[] = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			for(; (i = is.read(byt))!= -1; ){
				baos.write(byt,0,i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte bis[] = Base64.decode(baos.toByteArray(), Base64.DEFAULT);
		return new String(bis);
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
			String strUserName = edtxtUserName.getText().toString().trim();
			String strPass = edtxtPass.getText().toString().trim();
			String temp = strUserName + ","+strPass;
			if(chBoxSave.isChecked()){
			FileOutputStream fos = DataUltils.saveFileToInternalStorage(getActivity(), "account.yahoo", "id.bin");
			byte[] byt = Base64.encode(temp.getBytes(), Base64.DEFAULT);
			try {
				fos.write(byt);
				fos.flush();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			if ("".equals(strUserName) || "".equals(strPass)) {
				Toast.makeText(getActivity(),
						"Ten tai khoan va password khong duoc de trong!",
						Toast.LENGTH_LONG).show();
			} else{
				checkAcc = new CheckAcc();
				checkAcc.execute(strUserName, strPass);
			}
		}
	}

	private class CheckAcc extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean idTrue = true;
			String strUserName = params[0];
			String strPass = params[1];
			try {
				singletonSession.login(strUserName, strPass);
				if(chBoxHide.isChecked()){
					singletonSession
					.setStatus(org.openymsg.network.Status.INVISIBLE);
				}else
				singletonSession
						.setStatus(org.openymsg.network.Status.AVAILABLE);
			} catch (AccountLockedException e) {
				idTrue = false;
				publishProgress("Tai khoan da bi khoa!");
				e.printStackTrace();
			} catch (IllegalStateException e) {
				idTrue = false;
				publishProgress("Thong tin dang nhap ko dung!");
				e.printStackTrace();
			} catch (LoginRefusedException e) {
				idTrue = false;
				Log.d(TAG, "IO error");
				publishProgress("Thong tin dang nhap ko dung!");
				e.printStackTrace();
			} catch (FailedLoginException e) {
				idTrue = false;
				publishProgress("Thong tin dang nhap ko dung!");
				e.printStackTrace();
			} catch (IOException e) {
				idTrue = false;
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
			return idTrue;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			Toast.makeText(getActivity(), values[0], Toast.LENGTH_LONG).show();
			this.cancel(true);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			((MessengerFragmentActivity) LoginFragment.this.getActivity())
					.startFragment(1);
		}

	}
}
