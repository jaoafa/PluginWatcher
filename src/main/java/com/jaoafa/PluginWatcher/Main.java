package com.jaoafa.PluginWatcher;

import com.jaoafa.PluginWatcher.Event.JoinEvent;
import com.jaoafa.PluginWatcher.Task.Task_Watcher;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static String WebhookUrl = null;
    public static boolean pluginDisabled = false;

    /**
     * プラグインが起動したときに呼び出し
     *
     * @author mine_book000
     * @since 2020/06/25
     */
    @Override
    public void onEnable() {
        getLogger().info("PluginWatcher v" + this.getDescription().getVersion());

        FileConfiguration config = getConfig();
        if (config.contains("WebhookUrl")) {
            WebhookUrl = config.getString("WebhookUrl");
        } else {
            getLogger().warning("WebhookUrlが見つかりません。Discordへの通知は動作しません。");
        }

        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        new Task_Watcher().runTaskTimerAsynchronously(this, 0L, 12000L); // 10分毎
    }

}
