package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class TeleportationBow extends CustomItem {
    public TeleportationBow(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.BOW, 1, Utils.chat("{#56eafa}&l► &7&oTeleports the user to"), Utils.chat("&7&othe arrow's landing location.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lTeleport Bow &7&l☁")).addPersistentDataString("teleportbow").toItemStack();
    }

    @Override
    public void onProjectileHit(ProjectileHitEvent event) {
        if (((event.getEntity() instanceof Arrow)) &&
                ((event.getEntity().getShooter() instanceof Player))) {
            Player p = (Player) event.getEntity().getShooter();
            if ((event.getHitBlock() != null)) {
                Block block = event.getHitBlock();
                Location loc = block.getLocation();

                ItemStack bow = p.getItemInHand();
                if (!bow.hasItemMeta()) {
                    return;
                }
                if (!p.getItemInHand().getItemMeta().getPersistentDataContainer().has(CustomItems.instance.nameKey, PersistentDataType.STRING)) {
                    return;
                }
                if (p.getItemInHand().getItemMeta().getPersistentDataContainer().get(CustomItems.instance.nameKey, PersistentDataType.STRING).equalsIgnoreCase("teleportbow")) {
                    int y = loc.getBlockY();
                    if (loc.getBlock().getType() == Material.BARRIER) {
                        p.sendMessage(Utils.chat("&7Its against the rules to go outside of barriered areas you know."));
                        return;
                        //loc.setY(y - 1);
                    } else {
                        loc.setY(y + 1);
                    }
                    p.teleport(loc);
                    p.getWorld().spawnParticle(Particle.PORTAL, loc, 50);
                    p.playSound(loc, Sound.BLOCK_PORTAL_TRAVEL, 10.0F, 10.0F);
                }
            }
        }
    }
}
