package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EndlessTorches extends CustomItem{
    public EndlessTorches(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.TORCH, 1, Utils.chat("{#56eafa}&l► &7&oAllows for the infinite"), Utils.chat("&7&oplacement of torches.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lEndless Torches &7&l☁")).addEnchants(Enchantment.DURABILITY, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("endlesstorches").toItemStack();
    }
    @Override
    public void onBlockPlaceHighest(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        final Block b = event.getBlockPlaced();
        final Material btype = b.getType();
        final BlockData bdata = b.getBlockData();
        if (b.getType() == Material.TORCH || b.getType() == Material.WALL_TORCH) {
            ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "endlesstorches");
            if (hand == null) {
                return;
            }
            event.setCancelled(true);
            new BukkitRunnable() {
                public void run() {
                    final Block newblock = b.getLocation().getBlock();
                    newblock.setType(btype);
                    newblock.setBlockData(bdata);
                }
            }.runTaskLater(CustomItems.instance, 1);
        }
    }
}
