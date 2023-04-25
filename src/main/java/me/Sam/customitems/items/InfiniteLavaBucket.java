package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InfiniteLavaBucket extends CustomItem{
    public InfiniteLavaBucket(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.LAVA_BUCKET, 1, Utils.chat("{#56eafa}&l► &7&oInfinitely places lava without"), Utils.chat("&7&oconsumption of the lava bucket.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lInfinite Lava Bucket &7&l☁")).addEnchants(Enchantment.DURABILITY, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("infinitelavabucket").toItemStack();
    }

    @Override
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player p = event.getPlayer();
        ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "infinitelavabucket");
        if (hand == null) {
            return;
        }
        Block lavablock = event.getBlockClicked().getRelative(event.getBlockFace());
        BlockBreakEvent breakevent = new BlockBreakEvent(lavablock, p);
        CustomItems.instance.getServer().getPluginManager().callEvent(breakevent);
        if (breakevent.isCancelled()) {
            return;
        }
        event.setCancelled(true);
        lavablock.setType(Material.LAVA);
        CustomItems.instance.coreApi.logPlacement(p.getName(), lavablock.getLocation(), lavablock.getType(), lavablock.getBlockData());
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.FURNACE || event.getInventory().getType() == InventoryType.BLAST_FURNACE) {
            if (event.getCurrentItem() != null) {
                ItemStack itemStack = event.getCurrentItem();
                if (itemStack.hasItemMeta()) {
                    PersistentDataContainer dataContainer = itemStack.getItemMeta().getPersistentDataContainer();
                    if (dataContainer.has(CustomItems.instance.nameKey, PersistentDataType.STRING)) {
                        if (dataContainer.get(CustomItems.instance.nameKey, PersistentDataType.STRING).equalsIgnoreCase("infinitelavabucket")) {
                            event.setCancelled(true);
                            event.getWhoClicked().sendMessage(Utils.chat("&cYou may not put an infinite lava bucket in a furnace!"));
                        }
                    }
                }
            }
        }
    }
}
