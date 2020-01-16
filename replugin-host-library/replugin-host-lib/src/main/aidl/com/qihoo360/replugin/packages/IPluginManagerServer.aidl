package com.qihoo360.replugin.packages;

import com.qihoo360.replugin.model.PluginInfo;
import com.qihoo360.replugin.packages.PluginRunningList;

/**
 * Plug-in manager.Used to control the installation, uninstallation, acquisition, and so on of plug-ins.Runs in a resident process.
 * <p>
 * Supplement: managers related to plug-in interaction and runtime mechanism are in IPluginHost.
 *
 * @author RePlugin Team
 */
interface IPluginManagerServer {

    /**
     * Install a plug-in
     * <p>
     * Note: if the old plugin (p - n) in the beginning, you should use IPluginHost pluginDownloaded method.
     *
     * @return PluginInfo object for the installed plug-in
     */
    PluginInfo install(String path);

    /**
     * Uninstall a plug-in
     * <p>
     * Note: only for "pure APK" plug-in scenarios.
     *
     * @param info Plugin information.
     * @return Did you successfully uninstall the plug-in?
     */
    boolean uninstall(in PluginInfo info);

    /**
     * Load the list of plug-ins for later use
     * <p>
     * TODO only returns the "new plugin" for use by PmBase.Will merge in the future
     *
     * @return The list of PluginInfo
     */
    List<PluginInfo> load();

    /**
     * Update the list of all plug-ins
     *
     * @return The list of PluginInfo
     */
    List<PluginInfo> updateAll();

    /**
     * Set the isUsed state and notify all processes of updates.
     *
     * @param pluginName The plugin name
     * @param used Has it been used?
     */
    void updateUsed(String pluginName, boolean used);

    /**
     * Gets a list of running plug-ins
     *
     * @return List of running plug-ins
     */
    PluginRunningList getRunningPlugins();

    /**
     * Is the plug-in running?
     *
     * @param pluginName The plugin name
     * @param process Specifies the name of the process. Null means check all.
     * @return Has it been used?
     */
    boolean isPluginRunning(String pluginName, String process);

    /**
     * When the process starts, synchronize the running plug-in state to the Server side
     *
     * @param list List of running plug-ins
     */
    void syncRunningPlugins(in PluginRunningList list);

    /**
     * When the process starts, synchronize the running plug-in state to the Server side
     *
     * @param processName The process of
     * @param pluginName The name of the running plug-in
     */
    void addToRunningPlugins(String processName, int pid, String pluginName);

    /**
     * Gets a list of process names that are running the plug-in
     *
     * @param pluginName The plug-in name to query for
     * @return List of process names that are running this plug-in.It must not be Null.
     */
    String[] getRunningProcessesByPlugin(String pluginName);
}
