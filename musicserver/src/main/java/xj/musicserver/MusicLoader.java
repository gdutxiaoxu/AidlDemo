package xj.musicserver;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;;

/**
 * @author meitu.xujun  on 2017/10/16
 * @version 0.1
 */
public class MusicLoader {

    private static final String TAG = "MusicLoader";

    private static List<MusicInfo> musicList = new ArrayList<MusicInfo>();

    private static MusicLoader musicLoader;

    private static ContentResolver contentResolver;
    //Uri，指向external的database
    private Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    //projection：选择的列; where：过滤条件; sortOrder：排序。
    private String[] projection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.SIZE,
            Media.TITLE
    };
//    private String where =  "mime_type in ('audio/mpeg','audio/x-ms-wma') and bucket_display_name <> 'audio' and is_music > 0 " ;
    private String where =  "mime_type like 'audio/%' " ;
    private String sortOrder = Media.DATA;

    public static MusicLoader instance(ContentResolver pContentResolver){
        if(musicLoader == null){
            contentResolver = pContentResolver;
            musicLoader = new MusicLoader();
        }
        return musicLoader;
    }

    private MusicLoader(){                                                                                                             //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中
        Cursor cursor = contentResolver.query(contentUri, projection, where, null, sortOrder);
        if(cursor == null){
            Log.i(TAG," Music Loader cursor == null.");
        }else if(!cursor.moveToFirst()){
            Log.i(TAG," Music Loader cursor.moveToFirst() returns false.");
        }else{
            int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
            int albumCol = cursor.getColumnIndex(Media.ALBUM);
            int idCol = cursor.getColumnIndex(Media._ID);
            int durationCol = cursor.getColumnIndex(Media.DURATION);
            int sizeCol = cursor.getColumnIndex(Media.SIZE);
            int artistCol = cursor.getColumnIndex(Media.ARTIST);
            int urlCol = cursor.getColumnIndex(Media.DATA);
            int titleCol = cursor.getColumnIndex(Media.TITLE);
            while(cursor.moveToNext()){
                MusicInfo musicInfo = getMusicInfo(cursor, displayNameCol, albumCol, idCol,
                        durationCol, sizeCol, artistCol, urlCol,titleCol);
                musicList.add(musicInfo);

            }
        }
    }

    @NonNull
    private MusicInfo getMusicInfo(Cursor cursor, int displayNameCol, int albumCol, int idCol,
                                   int durationCol, int sizeCol, int artistCol, int urlCol, int
                                               titleCol) {
        String displayName = cursor.getString(displayNameCol);
        String album = cursor.getString(albumCol);
        long id = cursor.getLong(idCol);
        int duration = cursor.getInt(durationCol);
        long size = cursor.getLong(sizeCol);
        String artist = cursor.getString(artistCol);
        String url = cursor.getString(urlCol);
        String title=cursor.getString(titleCol);

        MusicInfo musicInfo = new MusicInfo(id, title);
        musicInfo.setAlbum(album);
        musicInfo.setDuration(duration);
        musicInfo.setSize(size);
        musicInfo.setArtist(artist);
        musicInfo.setUrl(url);
        musicInfo.setDisplayName(displayName);
        return musicInfo;
    }

    public List<MusicInfo> getMusicList(){
        return musicList;
    }

    public Uri getMusicUriById(long id){
        Uri uri = ContentUris.withAppendedId(contentUri, id);
        return uri;
    }

}
