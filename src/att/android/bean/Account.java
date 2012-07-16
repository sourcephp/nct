package att.android.bean;

import android.graphics.Bitmap;

public class Account {
	private String strName;
	private int intStatus;
	private Bitmap bitmapAvata;

	public Account(String strName, int intStatus, Bitmap bitmapAvata) {
		super();
		this.strName = strName;
		this.intStatus = intStatus;
		this.bitmapAvata = bitmapAvata;
	}

	public Account() {
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public int getIntStatus() {
		return intStatus;
	}

	public void setIntStatus(int intStatus) {
		this.intStatus = intStatus;
	}

	public Bitmap getBitmapAvata() {
		return bitmapAvata;
	}

	public void setBitmapAvata(Bitmap bitmapAvata) {
		this.bitmapAvata = bitmapAvata;
	}
}
