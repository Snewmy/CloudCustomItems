package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class UnbreakableShears extends CustomItem {
    public UnbreakableShears(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.SHEARS, 1, Utils.chat("{#56eafa}&l► &7&oA pair of shears that will"), Utils.chat("&7&onever break.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lUnbreakable Shears &7&l☁")).addEnchants(Enchantment.DURABILITY, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("unbreakableshears").toItemStack();
    }

    @Override
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        Player p = event.getPlayer();
        ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "unbreakableshears");
        if (hand == null) {
            return;
        }
        event.setCancelled(true);
    }
}
