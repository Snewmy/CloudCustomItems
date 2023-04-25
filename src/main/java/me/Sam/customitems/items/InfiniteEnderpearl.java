package me.Sam.customitems.items;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class InfiniteEnderpearl extends CustomItem{
    public InfiniteEnderpearl(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.ENDER_PEARL, 1, Utils.chat("{#56eafa}&l► &7&oInfinite use enderpearling.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lInfinite Enderpearl &7&l☁")).addEnchants(Enchantment.ARROW_DAMAGE, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("infiniteenderpearl").toItemStack();
    }

    @Override
    public void onLaunchProjectileMonitor(PlayerLaunchProjectileEvent event) {
        Projectile projectile = event.getProjectile();
        if (projectile.getType() == EntityType.ENDER_PEARL) {
            if (projectile instanceof EnderPearl enderPearl) {
                if (enderPearl.getShooter() instanceof Player) {
                    ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "infiniteenderpearl");
                    if (hand == null) {
                        return;
                    }
                    event.setShouldConsume(false);
                }
            }
        }
    }
}
