package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class InfiniteHoneyBottle extends CustomItem{
    public InfiniteHoneyBottle(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.HONEY_BOTTLE, 1, Utils.chat("{#56eafa}&l► &7&oInfinite consumption honey"), Utils.chat("&7&obottle.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lInfinite Honey Bottle &7&l☁")).addEnchants(Enchantment.ARROW_DAMAGE, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("infinitehoneybottle").toItemStack();
    }

    @Override
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.HONEY_BOTTLE) {
            Player p = event.getPlayer();
            ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "infinitehoneybottle");
            if (hand == null) {
                return;
            }
            event.setCancelled(true);
            p.setFoodLevel(p.getFoodLevel() + 6);
            p.sendMessage(Utils.chat("&7You can feel the moisture running down your throat. mmmMMMMMmmm."));
            if (p.hasPotionEffect(PotionEffectType.POISON)) {
                p.removePotionEffect(PotionEffectType.POISON);
            }
        }
    }
}
