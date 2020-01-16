package com.qihoo360.loader2;

import com.qihoo360.replugin.component.service.server.IPluginServiceServer;

/**
 * @author RePlugin Team
 */
interface IPluginClient {

    // Plugin, process may be redundant, used temporarily, and may be optimized later
    String allocActivityContainer(String plugin, int process, String target, in Intent intent);

    // The parameter plugin is used to handle multi-plug-in single-process situations
    IBinder queryBinder(String plugin, String binder);

    void releaseBinder();

    oneway void sendIntent(in Intent intent);

    void sendIntentSync(in Intent intent);

    int sumActivities();

    IPluginServiceServer fetchServiceServer();

    /**
     * Plug-in receives broadcast
     *
     * @param plugin The plug-in name
     * @param receiver Receiver name
     * @param Intent broadcast's Intent data
     */
    void onReceive(String plugin, String receiver, in Intent intent);

    /**
     * Dump is the Service information that is started by the plug-in framework.
     */
    String dumpServices();

    /**
     * The detailed Activity pit map stored in the dump plug-in framework
     */
    String dumpActivities();
}