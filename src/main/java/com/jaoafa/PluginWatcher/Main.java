package com.jaoafa.PluginWatcher;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.PluginWatcher.Task.Task_Watcher;

public class Main extends JavaPlugin {
	public static String WebhookUrl = null;

	/**
	 * プラグインが起動したときに呼び出し
	 * @author mine_book000
	 * @since 2020/06/25
	 */
	@Override
	public void onEnable() {
		getLogger().info("PluginWatcher v0.0.1");

		FileConfiguration config = getConfig();
		if (config.contains("WebhookUrl")) {
			WebhookUrl = config.getString("WebhookUrl");
		} else {
			getLogger().warning("WebhookUrlが見つかりません。Discordへの通知は動作しません。");
		}

		new Task_Watcher().runTaskTimerAsynchronously(this, 0L, 12000L); // 10分毎
	}

}
