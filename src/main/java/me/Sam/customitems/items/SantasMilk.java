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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SantasMilk extends CustomItem {
    public SantasMilk(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.MILK_BUCKET, 1,
                Utils.chat("{#56eafa}&l► &7&oSanta needs help with"),
                Utils.chat("&7&odrinking all of the milk"),
                Utils.chat("&7&ofrom every house!"),
                Utils.chat("&7&o(Milk does not consume)"))
                .addEnchants(Enchantment.ARROW_KNOCKBACK, 1)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addPersistentDataString(name)
                .setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lSanta's Milk &7&l☁"))
                .toItemStack();
    }

    @Override
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.MILK_BUCKET) {
            Player p = event.getPlayer();
            ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "santasmilk");
            if (hand == null) {
                return;
            }
            event.setCancelled(true);
            for (PotionEffect potionEffect : p.getActivePotionEffects()) {
                p.removePotionEffect(potionEffect.getType());
            }
        }
    }
}
