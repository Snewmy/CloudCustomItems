package me.Sam.customitems.items;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class InfiniteBoneMeal extends CustomItem{
    public InfiniteBoneMeal(String name) {
        super(name);
        this.itemStack = new ItemBuilder(Material.BONE_MEAL, 1, Utils.chat("{#56eafa}&l► &7&oInfinite use bonemeal.")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lInfinite Bone Meal &7&l☁")).addEnchants(Enchantment.ARROW_DAMAGE, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addPersistentDataString("infinitebonemeal").toItemStack();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack hand = CustomItems.instance.handCheck(event.getPlayer(), "infinitebonemeal");
            if (hand != null) {
                event.setCancelled(true);
            } else {
                return;
            }
            event.getClickedBlock().applyBoneMeal(event.getBlockFace());
        }
    }

}
