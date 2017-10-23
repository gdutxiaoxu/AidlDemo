// IResultListener.aidl
package xj.musicserver.async;

// Declare any non-default types here with import statements

interface IResultListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onSuccess(String mes);
    void onError(String errorMes);
}
