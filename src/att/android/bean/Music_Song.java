package att.android.bean;

import java.io.Serializable;

import android.widget.CheckBox;
import att.android.model.BaseModel;

public class Music_Song extends BaseModel implements Serializable {

	public String name;
	public String singer;
	public String songID;
	public String songKey;
	public String streamURL = "";
	public boolean check;
	private boolean hidden;
	
	public Music_Song() {
		check = false;
		hidden = true;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public Music_Song(String songName, String singer) {
		this.name = songName;
		this.singer = singer;
	}

	public void setNameSong(String name) {
		this.name = name;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getNameSong() {
		return this.name;
	}

	public String getSinger() {
		return this.singer;
	}

	public String getStreamUrl() {
		return streamURL;
	}

	public boolean isSelected() {
		return check;
	}

	public void setSelected(boolean checked) {
		check = checked;
	}
}
