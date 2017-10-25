package xj.musicclient.async;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import xj.musicclient.LogUtil;
import xj.musicclient.R;
import xj.musicserver.async.IAsyncService;
import xj.musicserver.async.IResultListener;

import static xj.musicclient.ParceableDemoActivity.XJ_MUSICSERVER;

public class AysncDemoActivity extends AppCompatActivity {

    private static final String TAG = "EasyDemoActivity";
    private static final String ACTION = "xj.musicserver.async.IAsyncService";

    private IAsyncService mIAsyncService;


    ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIAsyncService = IAsyncService.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIAsyncService =null;
        }
    };

    IResultListener.Stub mIResultListener=new IResultListener.Stub() {
        @Override
        public void onSuccess(String mes) throws RemoteException {
            LogUtil.i(TAG,"onSuccess:   mes="+mes);
        }

        @Override
        public void onError(String errorMes) throws RemoteException {
             LogUtil.i(TAG,"onError:  errorMes = "+errorMes);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aysnc_demo);
    }

    public void  onButtonClick(View v) throws RemoteException {
        switch (v.getId()){
            case R.id.btn_contact:
                LogUtil.i(TAG,"onButtonClick:   btn_contact=");
                if(mIAsyncService !=null){
                    mIAsyncService.getDate(" getDate ",mIResultListener);
                }

                break;
            case R.id.btn_start_service:
                LogUtil.i(TAG,"onButtonClick:   btn_start_service=");
                Intent intent = new Intent(ACTION);
                intent.setPackage(XJ_MUSICSERVER);
                bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_service:
                if(mIAsyncService !=null){

                    unbindService(mServiceConnection);
                    mIAsyncService=null;

                }
                break;
        }
    }
}
