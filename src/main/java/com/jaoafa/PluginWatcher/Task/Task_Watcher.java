package com.jaoafa.PluginWatcher.Task;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.jaoafa.PluginWatcher.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Task_Watcher extends BukkitRunnable {
	@Override
	public void run() {
		int enabledCount = 0;
		int disabledCount = 0;
		List<String> disabledPlugins = new ArrayList<>();
		for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			if (plugin.isEnabled()) {
				enabledCount += 1;
			} else {
				disabledCount += 1;
				disabledPlugins.add(plugin.getName());
			}
		}
		if (disabledCount != 0) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.isOp()) {
					return;
				}
				player.sendMessage("[PluginWatcher] " + ChatColor.RED
						+ "プラグインの一部が停止しています！運営は運営向けに示されているガイドラインに沿って対応してください。運営がいない場合はDiscordを通じ早急に運営に連絡してください。");
			}
			System.out.println("[PluginWatcher] Enabled: " + enabledCount + " / Disabled: " + disabledCount);
			System.out.println("[PluginWatcher] Disabled Plugins: " + String.join(", ", disabledPlugins));
			Main.pluginDisabled = true;

			sendMessage("<@221991565567066112> __**[PluginWatcher]**__ プラグインの一部が停止中: "
					+ String.join(", ", disabledPlugins));
		} else if (Main.pluginDisabled) {
			Main.pluginDisabled = false;
		}
	}

	private void sendMessage(String text) {
		if (Main.WebhookUrl == null) {
			return;
		}
		WebhookClient client = new WebhookClientBuilder(Main.WebhookUrl).build();
		WebhookMessageBuilder builder = new WebhookMessageBuilder()
				.setUsername("PluginWatcher")
				.setContent(text);

		WebhookMessage message = builder.build();
		client.send(message).exceptionally(v -> {
			System.out.println("Failed to send message: " + text + " (" + v.getMessage() + ")");
			return null;
		});
		client.close();
	}
}
