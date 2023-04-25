package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class BottomlessPowderSnow extends CustomItem{
    public BottomlessPowderSnow(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.POWDER_SNOW_BUCKET, 1,
                Utils.chat("{#56eafa}&l► &7&oThe snow just keeps coming!"),
                Utils.chat("&7&oreminds me of Canada..."))
                .addEnchants(Enchantment.ARROW_KNOCKBACK, 1)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addPersistentDataString(name)
                .setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lBottomless Powder Snow &7&l☁"))
                .toItemStack();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.POWDER_SNOW || event.getBlock().getType() == Material.POWDER_SNOW_BUCKET) {
            Player player = event.getPlayer();
            ItemStack hand = CustomItems.instance.handCheck(player, "bottomlesspowdersnow");
            if (hand == null) {
                return;
            }
            event.setCancelled(true);
        }
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack hand = CustomItems.instance.handCheck(player, this.getName());
            if (hand != null) {
                Block block = event.getClickedBlock().getRelative(event.getBlockFace());
                BlockBreakEvent breakevent = new BlockBreakEvent(block, player);
                CustomItems.instance.getServer().getPluginManager().callEvent(breakevent);
                if (breakevent.isCancelled()) {
                    return;
                }
                block.setType(Material.POWDER_SNOW);
                CustomItems.instance.coreApi.logPlacement(player.getName(), block.getLocation(), block.getType(), block.getBlockData());
                event.setCancelled(true);
            }
        }
    }
}
