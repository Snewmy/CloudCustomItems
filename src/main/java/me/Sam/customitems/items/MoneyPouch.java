package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyPouch implements Listener {

    public static final int POUCH_MODEL_DATA = 1;
    public static final Material POUCH_MATERIAL = Material.PAPER;


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack hand = player.getInventory().getItem(event.getHand());
            if (hand == null || hand.getType() == Material.AIR) {
                return;
            }
            ItemMeta im = hand.getItemMeta();
            if (!im.getPersistentDataContainer().has(CustomItems.instance.nameKey, PersistentDataType.DOUBLE)) {
                return;
            }
            double moneyAmount = im.getPersistentDataContainer().get(CustomItems.instance.nameKey, PersistentDataType.DOUBLE);
            String formattedMoney = NumberFormat.getNumberInstance(Locale.US).format(moneyAmount);
            player.sendMessage(Utils.chat(Utils.prefix + "&7You opened a Money Pouch and received {#56eafa}$" + formattedMoney + "!"));
            CustomItems.econ.depositPlayer(player, moneyAmount);
            player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            player.getInventory().remove(hand);
        }
    }
}
