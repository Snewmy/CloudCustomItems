package me.Sam.customitems.Listeners;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.items.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;

import java.util.Map;

public class CauldronLevelChange implements Listener {

    @EventHandler
    public void onLevelChange(CauldronLevelChangeEvent event) {
        if (event.getReason() == CauldronLevelChangeEvent.ChangeReason.BUCKET_EMPTY) {
            for (Map.Entry<String, CustomItem> entry : CustomItems.customItems.entrySet()) {
                entry.getValue().onCauldronBucketEmpty(event);
            }
        }
    }
}
