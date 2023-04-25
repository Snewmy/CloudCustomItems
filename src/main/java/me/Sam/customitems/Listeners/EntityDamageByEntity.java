package me.Sam.customitems.Listeners;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.items.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;

public class EntityDamageByEntity implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEntityDmgByEntity(EntityDamageByEntityEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onEntityDamageByEntity(event);
        }
    }
}
