package att.android.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class MaskPopup {
	private final PopupWindow window;
	private View mLayout;
	private View root;


	public MaskPopup(Activity context, int layoutResID, View root) {
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflator.inflate(layoutResID, null);

		window = new PopupWindow(view.getContext());
		this.mLayout = view;
		this.root = root;

		this.window.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					MaskPopup.this.window.dismiss();
					return true;
				}
				return false;
			}
		});
	}


	private void preShow() {
		this.window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		this.window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		this.window.setBackgroundDrawable(new BitmapDrawable());
		this.window.setTouchable(true);
		this.window.setFocusable(true);
		this.window.setOutsideTouchable(true);
		if (this.mLayout != null)
			this.window.setContentView(this.mLayout);
	}


	public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
		this.window.setOnDismissListener(listener);
	}

	public void showMask() {
		this.preShow();
		 this.window.showAsDropDown(root, 0, 0);
	}
}
