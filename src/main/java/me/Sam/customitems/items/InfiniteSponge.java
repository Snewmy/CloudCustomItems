package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class InfiniteSponge extends CustomItem{
    public InfiniteSponge(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.SPONGE, 1, Utils.chat("{#56eafa}&l► &7&oA sponge that will instantly"), Utils.chat("&7&obe dried upon breaking.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lInfinite Sponge &7&l☁")).addEnchants(Enchantment.MENDING, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("infinitesponge").toItemStack();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (event.getItemInHand().getType().equals(Material.SPONGE)) {
            ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "infinitesponge");
            if (hand == null) {
                return;
            }
            event.getBlockPlaced().setType(Material.SPONGE);
            event.getBlockPlaced().setMetadata("infinitesponge", new FixedMetadataValue(CustomItems.instance, p.getUniqueId()));
        }
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (event.getBlock().hasMetadata("infinitesponge")) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            ItemStack sponge = this.itemStack;
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), sponge);
            event.getBlock().removeMetadata("infinitesponge", CustomItems.instance);
        }
    }
}
