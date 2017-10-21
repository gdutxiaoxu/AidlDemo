package xj.musicserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author meitu.xujun  on 2017/10/16
 * @version 0.1
 */
//下面是自定义的一个MusicInfo子类，实现了Parcelable，为的是可以将整个MusicInfo的ArrayList在Activity和Service中传送，=_=!!,但其实不用
public class MusicInfo implements Parcelable {
    private long id;
    private String title;
    private String album;
    private int duration;
    private long size;
    private String artist;
    private String url;
    private String displayName;

    public MusicInfo(long id, String title, String album, int duration, long size, String artist,
                     String url, String displayName) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.duration = duration;
        this.size = size;
        this.artist = artist;
        this.url = url;
        this.displayName = displayName;
    }

    public MusicInfo(){

    }


    protected MusicInfo(Parcel in) {
        id = in.readLong();
        title = in.readString();
        album = in.readString();
        duration = in.readInt();
        size = in.readLong();
        artist = in.readString();
        url = in.readString();
        displayName = in.readString();
    }

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel in) {
            return new MusicInfo(in);
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };

    public MusicInfo(long id, String title) {
        this.id=id;
        this.title=title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }



    @Override
    public String toString() {
        return "MusicInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", artist='" + artist + '\'' +
                ", url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(album);
        dest.writeInt(duration);
        dest.writeLong(size);
        dest.writeString(artist);
        dest.writeString(url);
        dest.writeString(displayName);
    }

    public void readFromParcel(Parcel reply) {
        id=reply.readLong();
        title=reply.readString();
        album=reply.readString();
        duration=reply.readInt();
        size=reply.readLong();
        artist=reply.readString();
        url=reply.readString();
        displayName=reply.readString();

    }
}
