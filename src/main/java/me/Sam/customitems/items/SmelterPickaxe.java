package me.Sam.customitems.items;

import com.gmail.nossr50.datatypes.meta.BonusDropMeta;
import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.*;
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

public class SmelterPickaxe extends CustomItem {

    public static final Set<Player> breakingBlocks = new HashSet<>();
    public final static String BONUS_DROPS_METAKEY = "mcMMO: Double Drops";

    public SmelterPickaxe(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.DIAMOND_PICKAXE, 1, Utils.chat("{#56eafa}&l► &7&oAutomatically smelts"), Utils.chat("&7&oiron and gold ore upon"), Utils.chat("&7&obreaking.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lSmelter Pickaxe &7&l☁")).addPersistentDataString("smelterpickaxe").toItemStack();
    }

    @Override
    public void onBlockBreakLowest(BlockBreakEvent event) {
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
        if (key.equalsIgnoreCase("smelterpickaxe")) {
            if (b.getType() == Material.SPAWNER) {
                return;
            }
            Location loc = b.getLocation();
            breakingBlocks.add(p);
            BlockBreakEvent fakeEvent = new BlockBreakEvent(b, p);
            Bukkit.getPluginManager().callEvent(fakeEvent);
            if (fakeEvent.isCancelled()) {
                return;
            }
            if (b.getType() == Material.IRON_ORE) {
                if (!CustomItems.instance.mcmmo.getPlaceStore().isTrue(b.getState())) {
                    event.setCancelled(true);
                    dropIron(loc, event.getBlock().getDrops(hand));
                    for (ItemStack item : b.getDrops(hand)) {
                        if (b.getMetadata(BONUS_DROPS_METAKEY).size() > 0) {
                            BonusDropMeta bonusDropMeta = (BonusDropMeta) b.getMetadata(BONUS_DROPS_METAKEY).get(0);
                            int bonusCount = bonusDropMeta.asInt();
                            for (int i = 0; i < bonusCount; i++) {
                                b.getWorld().dropItemNaturally(b.getState().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
                            }
                        }
                    }
                    b.setType(Material.AIR);
                    p.playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 10.0F, 1.0F);
                    if(event.getBlock().hasMetadata(BONUS_DROPS_METAKEY))
                        event.getBlock().removeMetadata(BONUS_DROPS_METAKEY, CustomItems.instance.mcmmo);
                }
            } else if (b.getType() == Material.GOLD_ORE) {
                if (!CustomItems.instance.mcmmo.getPlaceStore().isTrue(b.getState())) {
                    event.setCancelled(true);
                    dropGold(loc, event.getBlock().getDrops(hand));
                    for (ItemStack item : b.getDrops(hand)) {
                        if (b.getMetadata(BONUS_DROPS_METAKEY).size() > 0) {
                            BonusDropMeta bonusDropMeta = (BonusDropMeta) b.getMetadata(BONUS_DROPS_METAKEY).get(0);
                            int bonusCount = bonusDropMeta.asInt();
                            for (int i = 0; i < bonusCount; i++) {
                                b.getWorld().dropItemNaturally(b.getState().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
                            }
                        }
                    }
                    b.setType(Material.AIR);
                    p.playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 10.0F, 1.0F);
                    if(event.getBlock().hasMetadata(BONUS_DROPS_METAKEY))
                        event.getBlock().removeMetadata(BONUS_DROPS_METAKEY, CustomItems.instance.mcmmo);
                }
            } else if (b.getType() == Material.DEEPSLATE_GOLD_ORE) {
                if (!CustomItems.instance.mcmmo.getPlaceStore().isTrue(b.getState())) {
                    event.setCancelled(true);
                    dropGold(loc, event.getBlock().getDrops(hand));
                    for (ItemStack item : b.getDrops(hand)) {
                        if (b.getMetadata(BONUS_DROPS_METAKEY).size() > 0) {
                            BonusDropMeta bonusDropMeta = (BonusDropMeta) b.getMetadata(BONUS_DROPS_METAKEY).get(0);
                            int bonusCount = bonusDropMeta.asInt();
                            for (int i = 0; i < bonusCount; i++) {
                                b.getWorld().dropItemNaturally(b.getState().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
                            }
                        }
                    }
                    b.setType(Material.AIR);
                    p.playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 10.0F, 1.0F);
                    if(event.getBlock().hasMetadata(BONUS_DROPS_METAKEY))
                        event.getBlock().removeMetadata(BONUS_DROPS_METAKEY, CustomItems.instance.mcmmo);
                }
            } else if (b.getType() == Material.DEEPSLATE_IRON_ORE) {
                if (!CustomItems.instance.mcmmo.getPlaceStore().isTrue(b.getState())) {
                    event.setCancelled(true);
                    dropIron(loc, event.getBlock().getDrops(hand));
                    for (ItemStack item : b.getDrops(hand)) {
                        if (b.getMetadata(BONUS_DROPS_METAKEY).size() > 0) {
                            BonusDropMeta bonusDropMeta = (BonusDropMeta) b.getMetadata(BONUS_DROPS_METAKEY).get(0);
                            int bonusCount = bonusDropMeta.asInt();
                            for (int i = 0; i < bonusCount; i++) {
                                b.getWorld().dropItemNaturally(b.getState().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
                            }
                        }
                    }
                    b.setType(Material.AIR);
                    p.playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 10.0F, 1.0F);
                    if(event.getBlock().hasMetadata(BONUS_DROPS_METAKEY))
                        event.getBlock().removeMetadata(BONUS_DROPS_METAKEY, CustomItems.instance.mcmmo);
                }
            }
            breakingBlocks.remove(p);
        }

    }

    public void dropIron(Location loc, Collection<ItemStack> items) {
        for (ItemStack item : items) {
            ItemStack ironingot = new ItemStack(Material.IRON_INGOT, item.getAmount());
            loc.getWorld().dropItemNaturally(loc, ironingot);
        }
    }

    public void dropGold(Location loc, Collection<ItemStack> items) {
        for (ItemStack item : items) {
            ItemStack goldingot = new ItemStack(Material.GOLD_INGOT, item.getAmount());
            loc.getWorld().dropItemNaturally(loc, goldingot);
        }
    }
}