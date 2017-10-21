package xj.musicserver.async;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

public class AsyncService extends Service {

    static Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public AsyncService() {
    }

    IAsyncService.Stub mIBinder = new IAsyncService.Stub() {
        @Override
        public void getDate(String name, final IResultListener iResultListener) throws
                RemoteException {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handleResult(iResultListener);

                }
            }).start();
        }
    };

    private void handleResult(final IResultListener iResultListener) {
        if (System.currentTimeMillis() % 2 == 0) {
            mHander.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        iResultListener.onSuccess(" success");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            mHander.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        iResultListener.onError(" error");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
       return mIBinder;
    }
}
