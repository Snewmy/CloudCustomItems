package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import net.minecraft.world.level.block.CauldronBlock;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class InfiniteWaterBucket extends CustomItem {
    public InfiniteWaterBucket(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.WATER_BUCKET, 1, Utils.chat("{#56eafa}&l► &7&oInfinitely places water without"), Utils.chat("&7&oconsumption of the water bucket.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lInfinite Water Bucket &7&l☁")).addEnchants(Enchantment.DURABILITY, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("infinitewaterbucket").toItemStack();
    }

    @Override
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player p = event.getPlayer();
        ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "infinitewaterbucket");
        if (hand == null) {
            return;
        }
        if (p.getWorld().getEnvironment() == World.Environment.NETHER) {
            p.sendMessage(Utils.chat("&cNaughty naughty..."));
            event.setCancelled(true);
            return;
        }
        Block waterblock = event.getBlockClicked().getRelative(event.getBlockFace());
        BlockBreakEvent breakevent = new BlockBreakEvent(waterblock, p);
        CustomItems.instance.getServer().getPluginManager().callEvent(breakevent);
        if (breakevent.isCancelled()) {
            return;
        }
        event.setCancelled(true);
        if (event.getBlockClicked().getBlockData() instanceof Slab) {
            Slab slab = (Slab) event.getBlockClicked().getBlockData();
            slab.setWaterlogged(true);
            event.getBlockClicked().setBlockData(slab);
            return;
        } else if (waterblock.getBlockData() instanceof Slab) {
            Slab slab = (Slab) waterblock.getBlockData();
            slab.setWaterlogged(true);
            waterblock.setBlockData(slab);
            return;
        } else if (event.getBlockClicked().getBlockData() instanceof Stairs) {
            Stairs stairs = (Stairs) event.getBlockClicked().getBlockData();
            stairs.setWaterlogged(true);
            event.getBlockClicked().setBlockData(stairs);
            return;
        } else if (waterblock.getBlockData() instanceof Stairs) {
            Stairs stairs = (Stairs) waterblock.getBlockData();
            stairs.setWaterlogged(true);
            waterblock.setBlockData(stairs);
            return;
        }
        if (waterblock.getBlockData() instanceof Waterlogged) {
            Waterlogged waterlogged = (Waterlogged) waterblock.getBlockData();
            waterlogged.setWaterlogged(true);
            waterblock.setBlockData(waterlogged);
        } else {
            waterblock.setType(Material.WATER);
            CustomItems.instance.coreApi.logPlacement(p.getName(), waterblock.getLocation(), waterblock.getType(), waterblock.getBlockData());
        }
    }

    @Override
    public void onCauldronBucketEmpty(CauldronLevelChangeEvent event) {
        Player p = (Player) event.getEntity();
        ItemStack hand = CustomItems.instance.handCheck(p, "infinitewaterbucket");
        if (hand == null) {
            return;
        }
        Block cauldronBlock = event.getBlock();
        if (cauldronBlock.getType() == Material.CAULDRON) {
            cauldronBlock.setType(Material.WATER_CAULDRON);
        }
        BlockBreakEvent breakevent = new BlockBreakEvent(cauldronBlock, p);
        CustomItems.instance.getServer().getPluginManager().callEvent(breakevent);
        if (breakevent.isCancelled()) {
            return;
        }
        Levelled cauldronData = (Levelled) cauldronBlock.getBlockData();
        cauldronData.setLevel(cauldronData.getMaximumLevel());
        cauldronBlock.setBlockData(cauldronData);
        event.setCancelled(true);
    }

    @Override
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "infinitewaterbucket");
        if (hand == null) {
            return;
        }
        event.setCancelled(true);
    }
}
