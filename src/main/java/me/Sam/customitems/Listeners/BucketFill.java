package me.Sam.customitems.Listeners;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.items.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

import java.util.Map;

public class BucketFill implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onBucketFill(PlayerBucketFillEvent event) {
        for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
            entry.getValue().onBucketFill(event);
        }
    }
}
