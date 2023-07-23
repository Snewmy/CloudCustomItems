package me.Sam.customitems.commandshit;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.Locale;
import me.Sam.customitems.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("givecustomitem")) {
                if (!sender.hasPermission("cloudcustomitems.givecustomitem")) {
                    sender.sendMessage(Utils.chat(Locale.instance.get("nopermission")));
                    return false;
                }
                if (args.length < 1) {
                    sender.sendMessage(Utils.chat(Locale.instance.get("notenoughargs")));
                    return false;
                }
                String customItemName = args[0];
                if (!CustomItems.customItems.containsKey(customItemName)) {
                    sender.sendMessage(Utils.chat(Locale.instance.get("notvaliditem")));
                    return false;
                }
                ItemStack customItem = CustomItems.customItems.get(customItemName).getItemStack();
                String playerName = args.length == 1 ? sender.getName() : args[1];
                if (Bukkit.getPlayer(playerName) == null) {
                    sender.sendMessage(Utils.chat(Locale.instance.get("notvalidplayer")));
                    return false;
                }
                Player receiver = Bukkit.getPlayer(playerName);
                Map<Integer, ItemStack> leftoverItems = receiver.getInventory().addItem(customItem);
                if (!leftoverItems.isEmpty()) {
                    receiver.getWorld().dropItemNaturally(receiver.getLocation(), customItem);
                }
                receiver.sendMessage(Utils.chat(Locale.instance.get("receivemessage").replace("%itemname%", customItem.getItemMeta().getDisplayName())));
                sender.sendMessage(Utils.chat(Locale.instance.get("gavemessage").replace("%receivername%", receiver.getName()).replace("%itemname%", customItem.getItemMeta().getDisplayName())));
        }
        return false;
    }
}
