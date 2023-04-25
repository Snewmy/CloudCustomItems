package me.Sam.customitems.Listeners;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.Utils;
import me.Sam.customitems.items.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class RenameListener implements Listener {

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().startsWith("/rename") || event.getMessage().startsWith("/itemname") || event.getMessage().startsWith("/cmi itemname") || event.getMessage().startsWith("/cmi rename")) {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                return;
            }
            ItemStack hand = player.getInventory().getItemInMainHand();
            ItemMeta im = hand.getItemMeta();
            if (im.getPersistentDataContainer().has(CustomItems.instance.nameKey, PersistentDataType.STRING)) {
                player.sendMessage(Utils.chat("&cYou may not rename this item!"));
                event.setCancelled(true);
            }
        }

    }
}
