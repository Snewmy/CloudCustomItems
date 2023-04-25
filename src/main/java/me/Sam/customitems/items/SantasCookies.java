package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class SantasCookies extends CustomItem {
    public SantasCookies(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.COOKIE, 1,
                Utils.chat("{#56eafa}&l► &7&oSanta needs help with"),
                Utils.chat("&7&oeating the endless amounts"),
                Utils.chat("&7&oof cookies from every house!"),
                Utils.chat("&7&o(Cookie does not consume)"))
                .addEnchants(Enchantment.ARROW_KNOCKBACK, 1)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addPersistentDataString(name)
                .setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lSanta's Cookie &7&l☁"))
                .toItemStack();
    }

    @Override
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.COOKIE) {
            Player p = event.getPlayer();
            ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), this.getName());
            if (hand == null) {
                return;
            }
            event.setCancelled(true);
            p.setFoodLevel(p.getFoodLevel() + 2);
        }
    }
}
