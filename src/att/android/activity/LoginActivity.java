package att.android.activity;

import java.io.IOException;

import org.openymsg.network.AccountLockedException;
import org.openymsg.network.FailedLoginException;
import org.openymsg.network.LoginRefusedException;
import org.openymsg.network.Session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import att.android.model.YGeneralHandler;

import com.example.multiapp.R;

public class LoginActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private static final String TAG = "LoginActivity";
	private EditText edtxtUserName;
	private EditText edtxtPass;
	private CheckBox chBoxSave;
	private CheckBox chBoxHide;
	private Button btnLogin;
	private Button btnBack;
	public YGeneralHandler sessionListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		edtxtUserName = (EditText) this.findViewById(R.id.edtxt_username);
		edtxtPass = (EditText) this.findViewById(R.id.edtxt_pass);
		chBoxSave = (CheckBox) this.findViewById(R.id.chbox_save);
		chBoxHide = (CheckBox) this.findViewById(R.id.chbox_hide);
		btnLogin = (Button) this.findViewById(R.id.btn_googleLogin);
		btnLogin.setOnClickListener(this);
		btnBack = (Button) this.findViewById(R.id.btn_login_back);
		btnBack.setOnClickListener(this);
	}

	public void onClick(View v) {
		if(v == btnLogin){
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
		if(v == btnBack){
			Intent i = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(i);
		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
