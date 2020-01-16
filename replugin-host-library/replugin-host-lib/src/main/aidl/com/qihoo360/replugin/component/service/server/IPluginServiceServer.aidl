package com.qihoo360.replugin.component.service.server;

import android.content.ComponentName;
import android.os.Messenger;

import com.qihoo360.loader2.mgr.IServiceConnection;

/**
 * Responsible for the Server side of service scheduling, provision, is the service provider, one of the core classes.
 *
 * @hide Internal use of framework.
 * @author RePlugin Team
 */
interface IPluginServiceServer {
    ComponentName startService(in Intent intent, in Messenger client);
    int stopService(in Intent intent, in Messenger client);

    int bindService(in Intent intent, in IServiceConnection conn, int flags, in Messenger client);
    boolean unbindService(in IServiceConnection conn);

    String dump();
}