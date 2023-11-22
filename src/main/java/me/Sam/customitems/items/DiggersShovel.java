package me.Sam.customitems.items;

import com.destroystokyo.paper.block.TargetBlockInfo;
import com.gmail.nossr50.datatypes.meta.BonusDropMeta;
import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.TileState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public class DiggersShovel extends CustomItem{

    public static final Set<Player> breakingBlocks = new HashSet<>();
    public final static String BONUS_DROPS_METAKEY = "mcMMO: Double Drops";

    public DiggersShovel(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.DIAMOND_SHOVEL, 1, Utils.chat("{#56eafa}&l► &7&oBreaks in a 3x3 area"), Utils.chat("&7&oaround the block being"), Utils.chat("&7&obroken."), Utils.chat("&cWarning&7: &7&odoes not activate"), Utils.chat("&7&omcmmo or jobs on purpose")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lDigger's Shovel &7&l☁")).addPersistentDataString("diggersshovel").toItemStack();
    }

    @Override
    public void onBlockBreakMonitor(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if (p.getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        if (breakingBlocks.contains(p)) {
            return;
        }
        if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR) {
            return;
        }
        ItemStack hand = p.getInventory().getItemInMainHand();
        ItemMeta im = hand.getItemMeta();
        if (!im.getPersistentDataContainer().has(CustomItems.instance.nameKey, PersistentDataType.STRING)) {
            return;
        }
        String key = im.getPersistentDataContainer().get(CustomItems.instance.nameKey, PersistentDataType.STRING);
        Block b = event.getBlock();
        if (key.equalsIgnoreCase("diggersshovel")) {
            breakingBlocks.add(p);
            threeByThreeBreaking(event, p, b, hand);
            breakingBlocks.remove(p);
        }
    }

    public void threeByThreeBreaking(BlockBreakEvent e, Player p, Block b, ItemStack hand) {
        BlockFace direction = p.getTargetBlockFace(10, TargetBlockInfo.FluidMode.NEVER);
        int startX, startY, startZ;
        int endX, endY, endZ;

        if (direction == null) {
            return; // sanity check (should never happen)
        }

        // figure out how to loop the blocks (direction)
        switch (direction) {
            case NORTH:
            case SOUTH:
            default:
                startX = -1;
                endX = 1;
                startY = -1;
                endY = 1;
                startZ = 0;
                endZ = 0;
                break;
            case EAST:
            case WEST:
                startX = 0;
                endX = 0;
                startY = -1;
                endY = 1;
                startZ = -1;
                endZ = 1;
                break;
            case UP:
            case DOWN:
                startX = -1;
                endX = 1;
                startY = 0;
                endY = 0;
                startZ = -1;
                endZ = 1;
                break;
        }

        // loop the blocks
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Block blockToBreak = b.getRelative(x, y, z);
                    if (blockToBreak.getLocation().equals(b.getLocation())) {
                        continue;
                    }
                    breakBlock(e, p, blockToBreak, hand);
                }
            }
        }
    }

    public void breakBlock(BlockBreakEvent e, Player player, Block block, ItemStack tool) {
        if (!breakableBlock(block)) {
            return;
        }
        /*
        BlockBreakEvent fakeEvent = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(fakeEvent);
        if (fakeEvent.isCancelled()) {
            return; // fake event cancelled
        }
        handlemcMMODrops(block, tool);
        */
        block.breakNaturally(tool);
    }

    public void handlemcMMODrops(Block b, ItemStack hand) {
        for (ItemStack item : b.getDrops(hand)) {
            if (b.getState() instanceof TileState) {
                continue;
            }
            if (b.getMetadata(BONUS_DROPS_METAKEY).size() > 0) {
                BonusDropMeta bonusDropMeta = (BonusDropMeta) b.getMetadata(BONUS_DROPS_METAKEY).get(0);
                int bonusCount = bonusDropMeta.asInt();
                for (int i = 0; i < bonusCount; i++) {
                    b.getWorld().dropItemNaturally(b.getState().getLocation(), item);
                }
                if (b.hasMetadata(BONUS_DROPS_METAKEY)) {
                    b.removeMetadata(BONUS_DROPS_METAKEY, CustomItems.instance.mcmmo);
                }
            }
        }
    }

    public boolean breakableBlock(Block b) {
        if (b == null) {
            return false;
        }
        switch (b.getType()) {
            case BEDROCK:
            case AIR:
            case CAVE_AIR:
                return false;
        }
        return true;
    }
}
