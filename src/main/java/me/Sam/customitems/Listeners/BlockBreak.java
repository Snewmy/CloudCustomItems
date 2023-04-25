package me.Sam.customitems.Listeners;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.items.CustomItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class BlockBreak implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onBlockBreak(event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreakMonitor(BlockBreakEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onBlockBreakMonitor(event);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreakLowest(BlockBreakEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onBlockBreakLowest(event);
        }
    }
}
