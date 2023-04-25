package me.Sam.customitems.items;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class InfiniteSnowball extends CustomItem{
    public InfiniteSnowball(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.SNOWBALL, 1,
                Utils.chat("{#56eafa}&l► &7&oSnowball fight!!!"),
                Utils.chat("&7&odoes not consume"),
                Utils.chat("&7&oupon use!"))
                .addEnchants(Enchantment.ARROW_KNOCKBACK, 1)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addPersistentDataString(name)
                .setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lInfinite Snowball &7&l☁"))
                .toItemStack();
    }

    @Override
    public void onLaunchProjectileMonitor(PlayerLaunchProjectileEvent event) {
        Projectile projectile = event.getProjectile();
        if (projectile.getType() == EntityType.SNOWBALL) {
            if (projectile instanceof Snowball snowBall) {
                if (snowBall.getShooter() instanceof Player) {
                    ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "infinitesnowball");
                    if (hand == null) {
                        return;
                    }
                    event.setShouldConsume(false);
                }
            }
        }
    }
}
