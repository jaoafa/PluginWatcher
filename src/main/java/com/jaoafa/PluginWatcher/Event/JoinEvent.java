package com.jaoafa.PluginWatcher.Event;

import com.jaoafa.PluginWatcher.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.Arrays;
import java.util.List;

public class JoinEvent implements Listener {
    List<String> allowUUIDs = Arrays.asList(
        "5799296a-d1ec-4252-93bd-440bb9caa65c", // X4Z
        "39cf878b-ef0b-44fc-a2c6-de3d540a4728", // Hirotaisou2012
        "32ff7cdc-a1b4-450a-aa7e-6af75fe8c37c", // mine_book000
        "7008531a-539b-4dfc-8b81-7b267d18dd0a", // X9Z
        "0bdc0219-f3c3-4d73-a4df-1d8bd088f419", // X5Z
        "22ab15d6-2e88-4fdd-91c0-e459f0f804f8", // Ekusas83
        "26728d53-add7-46d1-97c3-0a25bc6607f5", // kohonayoshi
        "0ad34a33-3ca4-4c86-84f3-a4591920b06a",  // MinHero_exp
        "13976d72-1389-4332-818e-9cecad363b12" // yuuaHP
    );

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void OnPreJoin(AsyncPlayerPreLoginEvent event) {
        if (!Main.pluginDisabled) {
            return;
        }
        if (allowUUIDs.contains(event.getUniqueId().toString())) {
            return;
        }
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.join(JoinConfiguration.noSeparators(),
            Component.text("[PluginWatcher] 500 Internal Server Error", NamedTextColor.RED),
            Component.newline(),
            Component.newline(),
            Component.text("プラグインの一部が動作していないため、サービスを提供することが出来ません。", NamedTextColor.GREEN),
            Component.newline(),
            Component.text("公式Discordサーバ内#supportにて運営にご連絡ください。", NamedTextColor.GREEN)));
    }
}
