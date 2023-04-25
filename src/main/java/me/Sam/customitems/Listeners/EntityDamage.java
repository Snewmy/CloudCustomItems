package me.Sam.customitems.Listeners;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.items.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Map;

public class EntityDamage implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onEntityDamage(event);
        }
    }
}
