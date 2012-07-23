package att.android.activity;

import java.util.ArrayList;

import com.example.multiapp.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ChatJointActivity extends TabActivity implements OnClickListener {
	private Button btnMoveLogin;
	private Button btnMoveContact;
	private Button btnMoveRss;
	private TabHost chatJointTabHost;
	private ArrayList<TabSpec> arlTabspace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		btnMoveLogin = (Button) this.findViewById(R.id.btn_move_Login);
		btnMoveLogin.setOnClickListener(this);
		btnMoveContact = (Button) this.findViewById(R.id.btn_move_contact);
		btnMoveContact.setOnClickListener(this);
		btnMoveRss = (Button) this.findViewById(R.id.btn_move_rss);
		btnMoveRss.setOnClickListener(this);

		chatJointTabHost = (TabHost) this.findViewById(android.R.id.tabhost);
		arlTabspace = new ArrayList<TabHost.TabSpec>();
	}

	@Override
	protected void onResume() {
		super.onResume();
		newChatTab("test");
	}

	// method on click bat su kien khi click chuot v�o 3 button o tren
	// sau do goi den activity MainActivity roi truyen cho n� gia tri kem theo
	// tuong ung voi cac nut l� 1, 2, 3
	public void onClick(View v) {
		Intent i = new Intent(ChatJointActivity.this, MainActivity.class);
		if (v == btnMoveLogin) {
			switchTabInActivity(0);
		} else if (v == btnMoveContact) {
			switchTabInActivity(1);
		} else if (v == btnMoveRss) {
			switchTabInActivity(2);
		}
		startActivity(i);
	}

	private void newChatTab(String nameFriend) {
		TabSpec tabspec = chatJointTabHost.newTabSpec(nameFriend);
		tabspec.setIndicator(nameFriend);
		Intent i = new Intent(ChatJointActivity.this, ChatTabActivity.class);
		tabspec.setContent(i);

		chatJointTabHost.addTab(tabspec);

		arlTabspace.add(tabspec);
	}
	private void switchTabInActivity(int indexTabToSwitchTo) {
		
	}
}
