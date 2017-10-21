package xj.musicclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import xj.musicserver.IPlayListener;
import xj.musicserver.IPlayService;
import xj.musicserver.MusicInfo;

public class ParceableDemoActivity extends AppCompatActivity {

    public static final String ACTION = "xj.musicserver.IPlayService";
    public static final String XJ_MUSICSERVER = "xj.musicserver";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parceable_demo);

    }

    private IPlayService mIPlayService;
    ServiceConnection mServiceConnection=new ServiceConnection() {



        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIPlayService = IPlayService.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    IPlayListener.Stub mPlayListener=new IPlayListener.Stub(){

        @Override
        public void onError(int code) throws RemoteException {
            LogUtil.i(TAG,"onError:   code = "+code);
        }

        @Override
        public void onSuccess(int code, MusicInfo musicInfo) throws RemoteException {
            LogUtil.i(TAG,"onSuccess:   code = "+code);
        }
    };

    public void  onButtonClick(View v) throws RemoteException {
        switch (v.getId()){
            case R.id.btn_contact:
                LogUtil.i(TAG,"onButtonClick:   btn_contact=");
                if(mIPlayService!=null){
                    mIPlayService.play("play", mPlayListener);
                }
                break;
            case R.id.btn_start_service:
                LogUtil.i(TAG,"onButtonClick:   btn_start_service=");
                Intent intent = new Intent(ACTION);
                intent.setPackage(XJ_MUSICSERVER);
                bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_service:
                break;
        }
    }


}
