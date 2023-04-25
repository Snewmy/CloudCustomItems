package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class LavaRod extends CustomItem{
    public LavaRod(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.BLAZE_ROD, 1, Utils.chat("{#56eafa}&l► &7&oWhile held in main or off hand,"), Utils.chat("&7&oprevents all fire damage.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lLava Rod &7&l☁")).addEnchants(Enchantment.MENDING, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("lavarod").toItemStack();
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if ((event.getEntity() instanceof Player)) {
            Player p = (Player) event.getEntity();
            ItemStack hand = CustomItems.instance.handCheck(p, "lavarod");
            if (hand == null) {
                return;
            }
            if (event.getCause().equals(EntityDamageEvent.DamageCause.LAVA) || (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE)) || (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK))) {
                event.setCancelled(true);
            }
        }
    }
}
