package com.qihoo360.replugin;

/**
 * Binder acquirers that can be used in cases where IBinder is loaded lazily.
 * <p>
 * Currently used for:
 * <p>
 * * RePlugin.registerGlobalBinderDelayed
 *
 * @author RePlugin Team
 */
interface IBinderGetter {

    /**
     * Get the IBinder object
     */
    IBinder get();
}
