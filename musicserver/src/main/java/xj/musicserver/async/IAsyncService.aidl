// IAsyncService.aidl
package xj.musicserver.async;

// Declare any non-default types here with import statements
import xj.musicserver.async.IResultListener;
interface IAsyncService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void getDate(String name,IResultListener iResultListener);
}
