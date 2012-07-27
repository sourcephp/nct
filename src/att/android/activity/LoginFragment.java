package att.android.activity;

import java.io.IOException;

import org.openymsg.network.AccountLockedException;
import org.openymsg.network.FailedLoginException;
import org.openymsg.network.LoginRefusedException;
import org.openymsg.network.Session;

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
import att.android.model.YGeneralHandler;

import com.example.multiapp.R;

public class LoginFragment extends Fragment implements OnItemClickListener,
		OnClickListener {
	private static final String TAG = "LoginActivity";
	private EditText edtxtUserName;
	private EditText edtxtPass;
	private CheckBox chBoxSave;
	private CheckBox chBoxHide;
	private Button btnLogin;
	public YGeneralHandler sessionListener;

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

		edtxtUserName = (EditText) getView().findViewById(R.id.edtxt_username);
		edtxtPass = (EditText) getView().findViewById(R.id.edtxt_pass);
		chBoxSave = (CheckBox) getView().findViewById(R.id.chbox_save);
		chBoxHide = (CheckBox) getView().findViewById(R.id.chbox_hide);
		btnLogin = (Button) getView().findViewById(R.id.btn_googleLogin);

		btnLogin.setOnClickListener(this);

	}

	public void onClick(View v) {
		Session session = new Session();
		sessionListener = new YGeneralHandler();
		session.addSessionListener(sessionListener);
		String strUserName = edtxtUserName.getText().toString().trim();
		String strPass = edtxtPass.getText().toString().trim();
		boolean bSave = chBoxSave.hasSelection();
		boolean bHide = chBoxHide.hasSelection();

		if ("".equals(strUserName) || "".equals(strPass)) {
			Log.e("Toast.....", "khong nhan o day ????");
		} else {
			// new LoginNetwork(strUserName, strPass, bSave, bHide).start();
			Log.i(TAG, "Login start");
			try {
				session.login(strUserName, strPass);
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
			Log.e("......Toast", "khong thay o day ????");
		}

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
