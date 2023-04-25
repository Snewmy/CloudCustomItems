package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BiggerInfiniteSponge extends CustomItem{

    public static final Set<Player> placingBlocks = new HashSet<>();

    public BiggerInfiniteSponge(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.SPONGE, 1, Utils.chat("{#56eafa}&l► &7&oA bigger sponge that will instantly"), Utils.chat("&7&obe dried upon breaking.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lBigger Infinite Sponge &7&l☁")).addEnchants(Enchantment.MENDING, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("biggerinfinitesponge").toItemStack();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player p = event.getPlayer();
        if (placingBlocks.contains(p)) {
            return;
        }
        if (event.getItemInHand().getType().equals(Material.SPONGE)) {
            ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "biggerinfinitesponge");
            if (hand == null) {
                return;
            }
            placingBlocks.add(p);
            //Check if can build here
            Block b = event.getBlock();
            //Remove 14 x 14 water blocks
            for (Block block : getBlocks(event.getBlock(), 14)) {
                if (block != null && block.getType() != Material.AIR) {
                    if (block.getType() == Material.WATER) {
                        block.setType(Material.AIR);
                    }
                }
            }
            event.getBlockPlaced().setType(Material.SPONGE);
            event.getBlockPlaced().setMetadata("biggerinfinitesponge", new FixedMetadataValue(CustomItems.instance, p.getUniqueId()));
            placingBlocks.remove(p);
        }
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (event.getBlock().hasMetadata("biggerinfinitesponge")) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            ItemStack sponge = this.itemStack;
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), sponge);
            event.getBlock().removeMetadata("biggerinfinitesponge", CustomItems.instance);
        }
    }

    public ArrayList<Block> getBlocks(Block start, int radius) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
            for (double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
                for (double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++) {
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }
}
