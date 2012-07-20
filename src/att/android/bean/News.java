package att.android.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

public class News implements Serializable {
	private String mTitle;
	private String mDes;
	private String mUrl;
	private String mDate;
	private Bitmap mBitmap;
	private String strUrlImage;
	public News(String mTitle, String mDes, String mUrl, String mDate, Bitmap mBitMap) {
		super();
		this.mTitle = mTitle;
		this.mDes = mDes;
		this.mUrl = mUrl;
		this.mDate = mDate;
		this.mBitmap = mBitMap;
	}
	public News(){
	}
	public void setStrUrlImage(String strUrlImage){
		this.strUrlImage = strUrlImage;
	}
	public String getStrUrlImage(){
		return this.strUrlImage;
	}
	public void setBitmap(Bitmap mBitmap){
		this.mBitmap = mBitmap;
	}
	public Bitmap getBitmap(){
		return mBitmap;
	}
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getmDes() {
		return mDes;
	}
	public void setmDes(String mDes) {
		this.mDes = mDes;
	}
	public String getmUrl() {
		return mUrl;
	}
	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	public String getmDate() {
		return mDate;
	}
	public void setmDate(String mDate) {
		this.mDate = mDate;
	}
}
