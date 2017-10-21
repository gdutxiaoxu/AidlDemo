// IMyAidlInterface.aidl
package xj.musicserver;

// Declare any non-default types here with import statements

import xj.musicserver.IPlayListener;

interface IPlayService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void play(String name,IPlayListener iPlayListener);

}
