package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class InfiniteEmptyBucket extends CustomItem{
    public InfiniteEmptyBucket(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.BUCKET, 1, Utils.chat("{#56eafa}&l► &7&oInfinite use liquid scooper.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lInfinite Empty Bucket &7&l☁")).addEnchants(Enchantment.ARROW_DAMAGE, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("infiniteemptybucket").toItemStack();
    }

    @Override
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player p = event.getPlayer();
        ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "infiniteemptybucket");
        if (hand == null) {
            return;
        }
        Block b = event.getBlock();
        event.setCancelled(true);
        b.setType(Material.AIR);
        CustomItems.instance.coreApi.logRemoval(p.getName(), b.getLocation(), b.getType(), b.getBlockData());
    }
}
