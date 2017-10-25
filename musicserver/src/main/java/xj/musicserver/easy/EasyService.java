package xj.musicserver.easy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import xj.musicserver.LogUtil;

public class EasyService extends Service {

    private static final String TAG = "EasyService";
    public EasyService() {
    }

    IEasyService.Stub mIBinder=new IEasyService.Stub() {
        @Override
        public void connect(String mes) throws RemoteException {
            LogUtil.i(TAG,"connect:   mes =" + mes);

        }

        @Override
        public void disConnect(String mes) throws RemoteException {
            LogUtil.i(TAG, "disConnect:  mes =" +mes);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(TAG,"onBind:   intent = "+intent.toString());
        return mIBinder;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtil.i(TAG,"onStart:   =");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.i(TAG,"onUnbind:   =");
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        LogUtil.i(TAG,"onDestroy:   =");
        super.onDestroy();
    }
}
