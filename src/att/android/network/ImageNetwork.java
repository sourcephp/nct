package att.android.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageNetwork {
	private Bitmap mBitmap;
	private InputStream mIS;

	public Bitmap getBitmap(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			mIS = connection.getInputStream();
			mBitmap = BitmapFactory.decodeStream(mIS);

		} catch (IOException e) {
			e.printStackTrace();
			mBitmap = null;
		}
		return mBitmap;
	}

}
