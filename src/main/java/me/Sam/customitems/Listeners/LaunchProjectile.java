package me.Sam.customitems.Listeners;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import me.Sam.customitems.CustomItems;
import me.Sam.customitems.items.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Map;

public class LaunchProjectile implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onProjectileLaunch(PlayerLaunchProjectileEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onLaunchProjectile(event);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onProjectileLaunchMonitor(PlayerLaunchProjectileEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onLaunchProjectileMonitor(event);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onProjectileLaunchLowest(PlayerLaunchProjectileEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onLaunchProjectileLowest(event);
        }
    }
}
