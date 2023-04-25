package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ExplosiveBow extends CustomItem {
    public ExplosiveBow(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.BOW, 1, Utils.chat("{#56eafa}&l► &7&oCreates an explosion"), Utils.chat("&7&owhere the arrow hits.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lExplosive Bow &7&l☁")).addPersistentDataString("explosivebow").toItemStack();
    }

    @Override
    public void onProjectileHit(ProjectileHitEvent event) {
        try {
            if ((event.getEntity() instanceof Arrow)) {
                Arrow arrow = (Arrow) event.getEntity();
                if ((event.getEntity().getShooter() instanceof Player)) {
                    Player p = (Player) event.getEntity().getShooter();
                    if ((event.getHitBlock() != null)) {
                        Block block = event.getHitBlock();
                        Location loc = block.getLocation();
                        ItemStack bow = p.getItemInHand();
                        if (!p.getItemInHand().getItemMeta().getPersistentDataContainer().has(CustomItems.instance.nameKey, PersistentDataType.STRING)) {
                            return;
                        }
                        if (p.getItemInHand().getItemMeta().getPersistentDataContainer().get(CustomItems.instance.nameKey, PersistentDataType.STRING).equalsIgnoreCase("explosivebow")) {
                            if (playersNearby(arrow)) {
                                p.sendMessage(Utils.chat("&cCannot shoot an explosive near a player."));
                            } else {
                                int y = loc.getBlockY();
                                loc.setY(y + 1);
                                p.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 3.0F, false, false, p);
                            }
                        }
                    } else if ((event.getHitEntity() != null)) {
                        Entity entity = event.getHitEntity();
                        Location loc = entity.getLocation();
                        ItemStack bow = p.getItemInHand();
                        if (!p.getItemInHand().getItemMeta().getPersistentDataContainer().has(CustomItems.instance.nameKey, PersistentDataType.STRING)) {
                            return;
                        }
                        if (p.getItemInHand().getItemMeta().getPersistentDataContainer().get(CustomItems.instance.nameKey, PersistentDataType.STRING).equalsIgnoreCase("explosivebow")) {
                            if (playersNearby(arrow)) {
                                p.sendMessage(Utils.chat("&cCannot shoot an explosive near a player."));
                            } else {
                                int y = loc.getBlockY();
                                loc.setY(y + 1);
                                p.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 3.0F, false, false, p);
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public boolean playersNearby(Entity e) {
        for (Entity entity : e.getNearbyEntities(6.0D, 6.0D, 6.0D)) {
            if ((entity instanceof Player)) {
                return true;
            }
        }
        return false;
    }
}
