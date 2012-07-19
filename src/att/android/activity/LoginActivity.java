package att.android.activity;

import com.example.multiapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class LoginActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private EditText edtxtUserName;
	private EditText edtxtPass;
	private CheckBox chBoxSave;
	private CheckBox chBoxHide;
	private Button btnLogin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		edtxtUserName = (EditText) findViewById(R.id.edtxt_username);
		edtxtPass = (EditText) findViewById(R.id.edtxt_pass);
		chBoxSave = (CheckBox) findViewById(R.id.chbox_save);
		chBoxHide = (CheckBox) findViewById(R.id.chbox_hide);
		btnLogin = (Button) findViewById(R.id.btn_googleLogin);

	}

	public void onClick(View v) {
		
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

}
