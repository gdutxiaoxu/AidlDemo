// IPlayListener.aidl
package xj.musicserver;

// Declare any non-default types here with import statements

import xj.musicserver.MusicInfo;

interface IPlayListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onError(int code);
    void onSuccess(int code,out MusicInfo musicInfo);


 }
