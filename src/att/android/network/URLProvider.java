package att.android.network;

import android.util.Log;

public class URLProvider {

	public static String getHotSongs(int paramInt1, int paramInt2) {
		String str1;
		String str2 = "69f54e21b5a3e77b4b91c95ac1a2c37e";

		while (true) {
			try {
				String str3 = new StringBuilder(
						String.valueOf(new StringBuilder(
								String.valueOf(new StringBuilder(
										String.valueOf(new StringBuilder(
												String.valueOf(new StringBuilder(
														String.valueOf("http://api.m.nhaccuatui.com/v4/api/song?"))
														.append("secretkey=nct@mobile_service")
														.toString())).append(
												"&action=getHotSong")
												.toString()))
										.append("&pageindex=")
										.append(String.valueOf(paramInt1))
										.toString())).append("&pagesize=")
								.append(String.valueOf(paramInt2)).toString()))
						+ "&token=" + str2;
				str1 = str3;
				return str1;
			} catch (Exception localException) {
				localException.printStackTrace();
			}
			str1 = null;
		}
	}

	public static String getLyric(String paramString) {
		String str1 = null;
		String str2 = "2b35e58a7833856f6f5988184f2e2bd2";
		if ("http://api.m.nhaccuatui.com/v4/api/song?" != null)
			while (true) {
				try {
					String str3 = new StringBuilder(
							String.valueOf(new StringBuilder(
									String.valueOf(new StringBuilder(
											String.valueOf(new StringBuilder(
													String.valueOf("http://api.m.nhaccuatui.com/v4/api/song?"))
													.append("secretkey=nct@mobile_service")
													.toString())).append(
											"&action=getSongLyric").toString()))
									.append("&songkey=").toString())).append(
							paramString.replace(" ", "%20")).toString()
							+ "&token=" + str2;
					str1 = str3;
					break;
				} catch (Exception localException) {
					localException.printStackTrace();
				}
				str1 = null;
			}
		return str1;

	}

}
