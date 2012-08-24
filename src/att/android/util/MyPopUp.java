package att.android.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.multiapp.R;

public class MyPopUp extends Dialog implements android.view.View.OnClickListener {
	private Button btnSave;
	private Button btnAdd;
	private ImageView imgThumb;
	private OnMyPopupListener mListener;
	
		public MyPopUp(Context context) {
		super(context);

			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.my_popup);
			getWindow().setBackgroundDrawableResource(R.drawable.popup);
//			imgThumb = (ImageView) this.findViewById(R.id.img_thumb);
//			imgThumb.setBackgroundResource(thumb);
			btnSave = (Button) this.findViewById(R.id.btn_save);
			btnSave.setOnClickListener(this);
			btnAdd = (Button) this.findViewById(R.id.btn_love);
			btnAdd.setOnClickListener(this);
		}

		public void onClick(View v) {
			if(v == btnSave){
				mListener.onItemClick("save");
				this.dismiss();
			}
			if(v == btnAdd){
				mListener.onItemClick("add");
				this.dismiss();
			}
		}
		public interface OnMyPopupListener {
			public abstract void onItemClick(String action);
		}

		public void setOnMyPopupListener(OnMyPopupListener listener) {
			mListener = listener;
		}
	}
