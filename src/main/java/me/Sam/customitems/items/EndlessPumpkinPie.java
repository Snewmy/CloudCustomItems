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

public class EndlessPumpkinPie extends CustomItem{
    public EndlessPumpkinPie(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.PUMPKIN_PIE, 1,
                Utils.chat("{#56eafa}&l► &7&oEvery person's dream.."),
                Utils.chat("&7&o(Pumpkin pie does not consume)"))
                .addEnchants(Enchantment.ARROW_KNOCKBACK, 1)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addPersistentDataString(name)
                .setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lEndless Pumpkin Pie &7&l☁"))
                .toItemStack();
    }

    @Override
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.PUMPKIN_PIE) {
            Player p = event.getPlayer();
            ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "endlesspumpkinpie");
            if (hand == null) {
                return;
            }
            event.setCancelled(true);
            p.setFoodLevel(p.getFoodLevel() + 8);
        }
    }
}
