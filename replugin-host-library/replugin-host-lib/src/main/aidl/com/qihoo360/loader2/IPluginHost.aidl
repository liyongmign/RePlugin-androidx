package com.qihoo360.loader2;

import android.content.IntentFilter;
import android.content.Intent;

import com.qihoo360.loader2.IPluginClient;
import com.qihoo360.loader2.PluginBinderInfo;
import com.qihoo360.replugin.model.PluginInfo;

import com.qihoo360.replugin.component.service.server.IPluginServiceServer;

import com.qihoo360.replugin.packages.IPluginManagerServer;

/**
 * @author RePlugin Team
 */
interface IPluginHost {

    void installBinder(String name, in IBinder binder);

    IBinder fetchBinder(String name);

    long fetchPersistentCookie();

    IPluginClient startPluginProcess(String plugin, int process, inout PluginBinderInfo info);

    String attachPluginProcess(String process, int index, in IBinder binder, String def);

    List<PluginInfo> listPlugins();

    void regActivity(int index, String plugin, String container, String activity);

    void unregActivity(int index, String plugin, String container, String activity);

    void regService(int index, String plugin, String service);

    void unregService(int index, String plugin, String service);

    void regPluginBinder(in PluginBinderInfo info, IBinder binder);

    void unregPluginBinder(in PluginBinderInfo info, IBinder binder);

    /**
    * Register all statically declared receivers under a plug-in to the resident process.
    */
    void regReceiver(String plugin, in Map receiverFilterMap);

    void unregReceiver();

    /**
     * Plug-in receives broadcast
     *
     * @param plugin The plug-in name
     * @param receiver Receiver name
     * @param Intent Broadcast's Intent data
     */
    void onReceive(String plugin, String receiver, in Intent intent);

    int sumBinders(int index);

    void updatePluginInfo(in PluginInfo info);

    PluginInfo pluginDownloaded(String path);

    boolean pluginUninstalled(in PluginInfo info);

    boolean pluginExtracted(String path);

    oneway void sendIntent2Process(String target, in Intent intent);

    oneway void sendIntent2Plugin(String target, in Intent intent);

    void sendIntent2ProcessSync(String target, in Intent intent);

    void sendIntent2PluginSync(String target, in Intent intent);

    boolean isProcessAlive(String name);

    IBinder queryPluginBinder(String plugin, String binder);

    /**
     * Query for the match Receivers in all plug-ins based on Inent.
     */
    List queryPluginsReceiverList(in Intent intent);

    /**
     * Get the "new Service management solution" Service on the Server side.
     * Added by Jiongxuan Zhang
     */
    IPluginServiceServer fetchServiceServer();

    /**
     * Get the plug-in service for the IPluginManagerServer (used for the pure APK scheme)
     * Added by Jiongxuan Zhang
     */
    IPluginManagerServer fetchManagerServer();

    /**
     * According to taskAffinity, which group of taskAffinity should be selected to judge.
     *
     * Since taskAffinity is a cross-process property, we want to keep the taskAffinityGroup data in the resident process.
     */
    int getTaskAffinityGroupIndex(String taskAffinity);

    /**
     * Get the PID by the process name.
     */
    int getPidByProcessName(String processName);

    /**
     * Get the process name by PID.
     */
    String getProcessNameByPid(int pid);

    /**
     * Dump detailed runtime information.
     */
    String dump();
}