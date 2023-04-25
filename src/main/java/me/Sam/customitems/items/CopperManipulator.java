package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class CopperManipulator extends CustomItem{
    public CopperManipulator(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.BLAZE_ROD, 1, Utils.chat("{#56eafa}&l► &7&oOxidizes or de-oxidizes copper"), Utils.chat("&7&oblocks with the use of right-click"), Utils.chat("&7&oand sneaking + right-click."), Utils.chat("&7&o(Works with slabs, and stairs)")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lCopper Manipulator &7&l☁")).addEnchants(Enchantment.DAMAGE_UNDEAD, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("coppermanipulator").toItemStack();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack hand = player.getInventory().getItem(event.getHand());
            if (hand == null || hand.getType() == Material.AIR) {
                return;
            }
            ItemMeta im = hand.getItemMeta();
            if (!im.getPersistentDataContainer().has(CustomItems.instance.nameKey, PersistentDataType.STRING)) {
                return;
            }
            String key = im.getPersistentDataContainer().get(CustomItems.instance.nameKey, PersistentDataType.STRING);
            Block block = event.getClickedBlock();
            if (key.equalsIgnoreCase("coppermanipulator")) {
                BlockBreakEvent fakeEvent = new BlockBreakEvent(block, player);
                Bukkit.getPluginManager().callEvent(fakeEvent);
                if (fakeEvent.isCancelled()) {
                    return; // fake event cancelled
                }
                if (!player.isSneaking()) {
                    copperCheck(block);
                } else {
                    sneakingCopperCheck(block);
                }
            }
        }
    }

    public void copperCheck(Block block) {
        switch (block.getType()) {
            case COPPER_BLOCK:
                block.setType(Material.EXPOSED_COPPER);
                break;
            case EXPOSED_COPPER:
                block.setType(Material.WEATHERED_COPPER);
                break;
            case WEATHERED_COPPER:
                block.setType(Material.OXIDIZED_COPPER);
                break;
            case WAXED_COPPER_BLOCK:
                block.setType(Material.WAXED_EXPOSED_COPPER);
                break;
            case WAXED_EXPOSED_COPPER:
                block.setType(Material.WAXED_WEATHERED_COPPER);
                break;
            case WAXED_WEATHERED_COPPER:
                block.setType(Material.WAXED_OXIDIZED_COPPER);
                break;
            case CUT_COPPER:
                block.setType(Material.EXPOSED_CUT_COPPER);
                break;
            case EXPOSED_CUT_COPPER:
                block.setType(Material.WEATHERED_CUT_COPPER);
                break;
            case WEATHERED_CUT_COPPER:
                block.setType(Material.OXIDIZED_CUT_COPPER);
                break;
            case WAXED_CUT_COPPER:
                block.setType(Material.WAXED_EXPOSED_CUT_COPPER);
                break;
            case WAXED_EXPOSED_CUT_COPPER:
                block.setType(Material.WAXED_WEATHERED_CUT_COPPER);
                break;
            case WAXED_WEATHERED_CUT_COPPER:
                block.setType(Material.WAXED_OXIDIZED_CUT_COPPER);
                break;
        }
        if (block.getBlockData() instanceof Slab) {
            Slab.Type slabType = ((Slab) block.getBlockData()).getType();
            switch (block.getType()) {
                case CUT_COPPER_SLAB:
                    block.setType(Material.EXPOSED_CUT_COPPER_SLAB);
                    break;
                case EXPOSED_CUT_COPPER_SLAB:
                    block.setType(Material.WEATHERED_CUT_COPPER_SLAB);
                    break;
                case WEATHERED_CUT_COPPER_SLAB:
                    block.setType(Material.OXIDIZED_CUT_COPPER_SLAB);
                    break;
                case WAXED_CUT_COPPER_SLAB:
                    block.setType(Material.WAXED_EXPOSED_CUT_COPPER_SLAB);
                    break;
                case WAXED_EXPOSED_CUT_COPPER_SLAB:
                    block.setType(Material.WAXED_WEATHERED_CUT_COPPER_SLAB);
                    break;
                case WAXED_WEATHERED_CUT_COPPER_SLAB:
                    block.setType(Material.WAXED_OXIDIZED_CUT_COPPER_SLAB);
                    break;
            }
            Slab slabData = (Slab) block.getBlockData();
            slabData.setType(slabType);
            block.setBlockData(slabData);
        }
        if (block.getBlockData() instanceof Stairs) {
            BlockFace facing = ((Stairs) block.getBlockData()).getFacing();
            Stairs.Shape shape = ((Stairs) block.getBlockData()).getShape();
            Bisected.Half half = ((Stairs) block.getBlockData()).getHalf();
            switch (block.getType()) {
                case CUT_COPPER_STAIRS:
                    block.setType(Material.EXPOSED_CUT_COPPER_STAIRS);
                    break;
                case EXPOSED_CUT_COPPER_STAIRS:
                    block.setType(Material.WEATHERED_CUT_COPPER_STAIRS);
                    break;
                case WEATHERED_CUT_COPPER_STAIRS:
                    block.setType(Material.OXIDIZED_CUT_COPPER_STAIRS);
                    break;
                case WAXED_CUT_COPPER_STAIRS:
                    block.setType(Material.WAXED_EXPOSED_CUT_COPPER_STAIRS);
                    break;
                case WAXED_EXPOSED_CUT_COPPER_STAIRS:
                    block.setType(Material.WAXED_WEATHERED_CUT_COPPER_STAIRS);
                    break;
                case WAXED_WEATHERED_CUT_COPPER_STAIRS:
                    block.setType(Material.WAXED_OXIDIZED_CUT_COPPER_STAIRS);
                    break;
            }
            Stairs stairsData = (Stairs) block.getBlockData();
            stairsData.setFacing(facing);
            stairsData.setShape(shape);
            stairsData.setHalf(half);
            block.setBlockData(stairsData);
        }
    }

    public void sneakingCopperCheck(Block block) {
        switch (block.getType()) {
            case EXPOSED_COPPER:
                block.setType(Material.COPPER_BLOCK);
                break;
            case WEATHERED_COPPER:
                block.setType(Material.EXPOSED_COPPER);
                break;
            case OXIDIZED_COPPER:
                block.setType(Material.WEATHERED_COPPER);
                break;

            case WAXED_EXPOSED_COPPER:
                block.setType(Material.WAXED_COPPER_BLOCK);
                break;
            case WAXED_WEATHERED_COPPER:
                block.setType(Material.WAXED_EXPOSED_COPPER);
                break;
            case WAXED_OXIDIZED_COPPER:
                block.setType(Material.WAXED_WEATHERED_COPPER);
                break;

            case EXPOSED_CUT_COPPER:
                block.setType(Material.CUT_COPPER);
                break;
            case WEATHERED_CUT_COPPER:
                block.setType(Material.EXPOSED_CUT_COPPER);
                break;
            case OXIDIZED_CUT_COPPER:
                block.setType(Material.WEATHERED_CUT_COPPER);
                break;

            case WAXED_EXPOSED_CUT_COPPER:
                block.setType(Material.WAXED_CUT_COPPER);
                break;
            case WAXED_WEATHERED_CUT_COPPER:
                block.setType(Material.WAXED_EXPOSED_CUT_COPPER);
                break;
            case WAXED_OXIDIZED_CUT_COPPER:
                block.setType(Material.WAXED_WEATHERED_CUT_COPPER);
                break;
        }
        if (block.getBlockData() instanceof Slab) {
            Slab.Type slabType = ((Slab) block.getBlockData()).getType();
            switch (block.getType()) {
                case EXPOSED_CUT_COPPER_SLAB:
                    block.setType(Material.CUT_COPPER_SLAB);
                    break;
                case WEATHERED_CUT_COPPER_SLAB:
                    block.setType(Material.EXPOSED_CUT_COPPER_SLAB);
                    break;
                case OXIDIZED_CUT_COPPER_SLAB:
                    block.setType(Material.WEATHERED_CUT_COPPER_SLAB);
                    break;

                case WAXED_EXPOSED_CUT_COPPER_SLAB:
                    block.setType(Material.WAXED_CUT_COPPER_SLAB);
                    break;
                case WAXED_WEATHERED_CUT_COPPER_SLAB:
                    block.setType(Material.WAXED_EXPOSED_CUT_COPPER_SLAB);
                    break;
                case WAXED_OXIDIZED_CUT_COPPER_SLAB:
                    block.setType(Material.WAXED_WEATHERED_CUT_COPPER_SLAB);
                    break;
            }
            Slab slabData = (Slab) block.getBlockData();
            slabData.setType(slabType);
            block.setBlockData(slabData);
        }
        if (block.getBlockData() instanceof Stairs) {
            BlockFace facing = ((Stairs) block.getBlockData()).getFacing();
            Stairs.Shape shape = ((Stairs) block.getBlockData()).getShape();
            switch (block.getType()) {
                case EXPOSED_CUT_COPPER_STAIRS:
                    block.setType(Material.CUT_COPPER_STAIRS);
                    break;
                case WEATHERED_CUT_COPPER_STAIRS:
                    block.setType(Material.EXPOSED_CUT_COPPER_STAIRS);
                    break;
                case OXIDIZED_CUT_COPPER_STAIRS:
                    block.setType(Material.WEATHERED_CUT_COPPER_STAIRS);
                    break;

                case WAXED_EXPOSED_CUT_COPPER_STAIRS:
                    block.setType(Material.WAXED_CUT_COPPER_STAIRS);
                    break;
                case WAXED_WEATHERED_CUT_COPPER_STAIRS:
                    block.setType(Material.WAXED_EXPOSED_CUT_COPPER_STAIRS);
                    break;
                case WAXED_OXIDIZED_CUT_COPPER_STAIRS:
                    block.setType(Material.WAXED_WEATHERED_CUT_COPPER_STAIRS);
                    break;
            }
            Stairs stairsData = (Stairs) block.getBlockData();
            stairsData.setFacing(facing);
            stairsData.setShape(shape);
            block.setBlockData(stairsData);
        }
    }
}
