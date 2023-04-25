package me.Sam.customitems.Listeners;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.items.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Map;

public class ItemConsume implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onItemConsume(PlayerItemConsumeEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onPlayerConsume(event);
        }
    }
}
