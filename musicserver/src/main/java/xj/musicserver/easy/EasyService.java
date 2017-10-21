package xj.musicserver.easy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

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
            Log.i(TAG, "disConnect:  mes =" +mes);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(TAG,"onBind:   intent = "+intent.toString());
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
