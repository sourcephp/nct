package att.android.util;

import com.example.multiapp.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MyDialog extends Dialog implements android.view.View.OnClickListener {
	private Button btnOk;
	private Button btnCancel;
	private OnMyDialogListener mListener;
	public MyDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_dialog);
		btnOk = (Button) this.findViewById(R.id.btn_ok_dialog);
		btnOk.setOnClickListener(this);
		btnCancel = (Button) this.findViewById(R.id.btn_cancel_dialog);
		btnCancel.setOnClickListener(this);
	}

	public void onClick(View v) {
		if(v == btnOk){
			mListener.onItemClick(true);
			this.dismiss();
		}
		if(v == btnCancel){
			mListener.onItemClick(false);
			this.dismiss();
		}
	}
	public interface OnMyDialogListener {
		public abstract void onItemClick(boolean isOk);
	}

	public void setOnMyDialogListener(OnMyDialogListener listener) {
		mListener = listener;
	}
}
