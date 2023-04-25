package me.Sam.customitems.items;

import com.gmail.nossr50.datatypes.meta.BonusDropMeta;
import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GlassShovel extends CustomItem{

    public static final Set<Player> breakingBlocks = new HashSet<>();
    public final static String BONUS_DROPS_METAKEY = "mcMMO: Double Drops";

    public GlassShovel(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.DIAMOND_SHOVEL, 1, Utils.chat("{#56eafa}&l► &7&oInstantly turns sand"), Utils.chat("&7&ointo glass upon breaking.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lGlass Shovel &7&l☁")).addPersistentDataString("glassshovel").toItemStack();
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
        if (key.equalsIgnoreCase("glassshovel")) {
            breakingBlocks.add(p);
            if (b.getType().equals(Material.SAND)) {
                BlockBreakEvent fakeEvent = new BlockBreakEvent(b, p);
                CustomItems.instance.getServer().getPluginManager().callEvent(fakeEvent);
                if (fakeEvent.isCancelled()) {
                    return;
                }
                Location loc = b.getLocation();
                event.setCancelled(true);
                dropGlass(loc, event.getBlock().getDrops());
                for (ItemStack item : b.getDrops(p.getInventory().getItemInMainHand())) {
                    if (b.getMetadata(BONUS_DROPS_METAKEY).size() > 0) {
                        BonusDropMeta bonusDropMeta = (BonusDropMeta) b.getMetadata(BONUS_DROPS_METAKEY).get(0);
                        int bonusCount = bonusDropMeta.asInt();
                        for (int i = 0; i < bonusCount; i++) {
                            b.getWorld().dropItemNaturally(b.getState().getLocation(), new ItemStack(Material.GLASS, 1));
                        }
                    }
                }
                b.setType(Material.AIR);
            }
            breakingBlocks.remove(p);
        }
    }

    public void dropGlass(Location loc, Collection<ItemStack> items) {
        for (ItemStack item : items) {
            if (item.getType().equals(Material.SAND)) {
                ItemStack glass = new ItemStack(Material.GLASS, item.getAmount());
                loc.getWorld().dropItemNaturally(loc, glass);
            }
        }
    }
}
