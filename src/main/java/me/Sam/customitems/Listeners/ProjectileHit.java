package me.Sam.customitems.Listeners;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.items.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Map;

public class ProjectileHit implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onProjectileHit(event);
        }
    }
}
