package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ArrowBlaster extends CustomItem {

    public ArrowBlaster(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.STONE_HOE, 1, Utils.chat("{#56eafa}&l► &7&oFires arrows upon the"), Utils.chat("&7&ouse of right-click.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lArrow Blaster &7&l☁")).addPersistentDataString("arrowblaster").toItemStack();
    }

    @Override
    public void onInteractNoCancel(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            Player p = event.getPlayer();
            ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "arrowblaster");
            if (hand == null) {
                return;
            }
            Projectile arrow = p.launchProjectile(Arrow.class, p.getLocation().getDirection());
            ((Arrow)arrow).setPickupStatus(Arrow.PickupStatus.DISALLOWED);
            arrow.setVelocity(p.getLocation().getDirection().normalize().multiply(5));
        }
    }
}
