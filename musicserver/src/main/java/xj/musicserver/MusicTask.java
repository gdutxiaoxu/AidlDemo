package xj.musicserver;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

/**
 * @author meitu.xujun  on 2017/10/17
 * @version 0.1
 */
public class MusicTask extends AsyncTask<Void,Void,Integer> {

    private static final String TAG = "MusicTask";

    private static final int RESULT_SUCUESS=0;
    private static final int RESULT_FAIL_MUSIC_NULL=1;
    private static final int RESULT_FAIL_NOT_FOUND_NAME=2;
    private static final int RESULT_FAIL_NOT_FOUND_ARTIST=3;


    IResultListener mIResultListener;


    private final Context mContext;
    private final String mName;
    private final String mArtist;

    //Uri，指向external的database
    private Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    private String[] projection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.TITLE
    };


    String where_title = "mime_type like 'audio/%'  and is_music > 0 and title like ?";
    String where_title_and_artist = "mime_type like 'audio/%' and is_music > 0 and title like ? and artist like ?";


    private String mResult;
    private ArrayList<MusicInfo> mMusicInfos;
    private MusicInfo mMusicInfo;

    public MusicTask(Context context, String name, String artist){
        mContext = context.getApplicationContext();
        mName = name;
        mArtist = artist;
    }


    @Override
    protected Integer doInBackground(Void... params) {
        LogUtil.i(TAG,"doInBackground:   mName="+mName +"  mArtist"+mArtist);

        mResult = "";
        ContentResolver contentResolver = mContext.getContentResolver();

        Cursor cursor;
        if (TextUtils.isEmpty(mArtist)) {
            cursor = contentResolver.query(contentUri, projection, where_title, new String[]{getFixName(mName)},null);
        }else{
            cursor=contentResolver.query(contentUri, projection,
                    where_title_and_artist, new String[]{getFixName(mName),getFixName(mArtist)},null);
            if(cursor==null || cursor.getCount()<=0){
                cursor = contentResolver.query(contentUri, projection,
                        where_title, new String[]{getFixName(mName)},null);
            }
        }
        if(cursor==null || cursor.getCount()<=0){
            return RESULT_FAIL_MUSIC_NULL;
        }

        int displayNameCol = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        int albumCol = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int idCol = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int durationCol = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int sizeCol = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
        int artistCol = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int urlCol = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int titleCol = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

        mMusicInfos = new ArrayList<>();
        String songName="";
        while (cursor.moveToNext()){
            songName = cursor.getString(titleCol);
            MusicInfo musicInfo = getMusicInfo(cursor, displayNameCol, albumCol, idCol,
                    durationCol, sizeCol, artistCol, urlCol,titleCol);
            mMusicInfos.add(musicInfo);
            if(songName.equals(mName)){
                mResult =mName;
                mMusicInfo=musicInfo;
                break;
            }

        }
        if(mMusicInfo==null){
            mMusicInfo =mMusicInfos.get(0);
        }
        return RESULT_SUCUESS;
    }

    @NonNull
    private String getFixName(String name) {
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        if(name.startsWith("%")){
            return name;
        }
        return "%"+name+"%";
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        Log.i(TAG, "onPostExecute: result =" +result);
        if(mIResultListener==null){
            return;
        }
        if(result==RESULT_SUCUESS){
            mIResultListener.onSuccess(mMusicInfo);
        }else{
            mIResultListener.onFail(result,mMusicInfo);
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
//        String title="";

        MusicInfo musicInfo = new MusicInfo(id, title);
        musicInfo.setAlbum(album);
        musicInfo.setDuration(duration);
        musicInfo.setSize(size);
        musicInfo.setArtist(artist);
        musicInfo.setUrl(url);
        musicInfo.setDisplayName(displayName);
        return musicInfo;
    }

    public void setIResultListener(IResultListener IResultListener) {
        mIResultListener = IResultListener;
    }

    public interface IResultListener{
        void onSuccess(MusicInfo musicInfo);
        void onFail(int code, MusicInfo musicInfo);
    }
}
