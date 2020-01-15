package com.qihoo360.replugin;

/**
 * Binder getter, use in getting IBinder delay.
 * <p>
 * current apply:
 * <p>
 * * RePlugin.registerGlobalBinderDelayed
 *
 * @author RePlugin Team
 */
interface IBinderGetter {

    /**
     * Get IBinder object.
     */
    IBinder get();
}
