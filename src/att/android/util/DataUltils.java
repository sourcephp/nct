package att.android.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

public class DataUltils {
	
	private static final Object FOLDER_ROOT = "/att.team/";
	
	public static FileOutputStream saveFileToInternalStorage(Context context,
			String folderName, String fileName) {
		String path = new StringBuilder().append(folderName).append("_")
				.append(fileName).toString();
		try {
			FileOutputStream fos = context.openFileOutput(path,
					Context.MODE_PRIVATE);
			return fos;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static InputStream openFileFromInternal(Context context,
			String fileName) {
		try {
			FileInputStream fis = context.openFileInput(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			return bis;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean checkStoryFromInternalStorage(String folderStory,Context context) {
		String[] files = context.fileList();
		for(String fileName : files){
			if(fileName.startsWith(folderStory)){
				return true;
			}
		}
		return false;
	}
	
	public static void deleteFolder(String folder){
		StringBuilder builder = new StringBuilder();
		builder.append(Environment.getExternalStorageDirectory())
				.append(FOLDER_ROOT).append(folder);
		File file = new File(builder.toString());
		file.delete();
	}
}
