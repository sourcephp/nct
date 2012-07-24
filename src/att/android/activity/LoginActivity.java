package att.android.activity;

import java.io.IOException;

import org.openymsg.network.AccountLockedException;
import org.openymsg.network.FailedLoginException;
import org.openymsg.network.LoginRefusedException;
import org.openymsg.network.Session;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
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
	public YGeneralHandler sessionListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		edtxtUserName = (EditText) findViewById(R.id.edtxt_username);
		edtxtPass = (EditText) findViewById(R.id.edtxt_pass);
		chBoxSave = (CheckBox) findViewById(R.id.chbox_save);
		chBoxHide = (CheckBox) findViewById(R.id.chbox_hide);
		btnLogin = (Button) findViewById(R.id.btn_googleLogin);

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
			Toast.makeText(this, "You haven't entered UserName or Password",
					Toast.LENGTH_SHORT).show();
		} else {
//			new LoginNetwork(strUserName, strPass, bSave, bHide).start();
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
			Log.i("Login", "finish");
			Toast.makeText(this, "Login Successful",
					Toast.LENGTH_SHORT).show();
			switchTabInActivity(1);
		}
		
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	private void switchTabInActivity(int indexTabToSwitchTo) {
		MainActivity ParentActivity;
		ParentActivity =  (MainActivity) this.getParent();
		ParentActivity.switchTabSpecial(indexTabToSwitchTo);
	}
}
