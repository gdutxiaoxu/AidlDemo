package xj.musicserver;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

/**
 * @author meitu.xujun  on 2017/10/16
 * @version 0.1
 */
public class PlayService extends Service {

    private static final String TAG = "PlayService";

    //Uri，指向external的database
    private Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    private String[] projection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
    };

    private String where =  "mime_type like 'audio/%' and bucket_display_name <> 'audio' and is_music > 0 ";

    IPlayService.Stub mIPlayService=new IPlayService.Stub() {
        @Override
        public void play(String name, final IPlayListener iPlayListener) throws RemoteException {
            MusicTask musicTask = new MusicTask(getApplicationContext(), name, "");
            musicTask.setIResultListener(new MusicTask.IResultListener() {
                @Override
                public void onSuccess(MusicInfo musicInfo) {
                    try {
                        iPlayListener.onSuccess(0,musicInfo);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(int code, MusicInfo musicInfo) {
                    try {
                        iPlayListener.onError(0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            musicTask.execute();
        }
    };



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(TAG, "onBind: intent = " +intent.toString());
        return mIPlayService;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "onCreate: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
