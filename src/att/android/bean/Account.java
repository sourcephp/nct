package att.android.bean;

import android.graphics.Bitmap;

public class Account {
	private String strName;
	private String strStatus;
	private int intStatus;
	private Bitmap bitmapAvata;

	public Account(String strName, String strStatus, int intStatus) {
		super();
		this.strName = strName;
		this.strStatus = strStatus;
		this.intStatus = intStatus;
		this.bitmapAvata = bitmapAvata;
	}

	public Account() {
		this.strName = "Tester";
		this.strStatus = "dang thu nghiem hehe";
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
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
