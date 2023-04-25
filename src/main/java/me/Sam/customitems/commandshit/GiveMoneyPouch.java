package me.Sam.customitems.commandshit;

import me.Sam.customitems.ItemBuilder;
import me.Sam.customitems.Utils;
import me.Sam.customitems.items.MoneyPouch;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class GiveMoneyPouch implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("CloudCustomItems.givemoneypouch")) {
            sender.sendMessage(Utils.chat("&cNo Permission!"));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(Utils.chat("&cNot enough arguments! /givemoneypouch player amount"));
            return false;
        }
        String playerName = args[0];
        if (Bukkit.getPlayer(playerName) == null) {
            sender.sendMessage(Utils.chat("&cInvalid player!"));
            return false;
        }
        Player receiver = Bukkit.getPlayer(playerName);
        try {
            int money = Integer.parseInt(args[1]);
            String formattedMoney = NumberFormat.getNumberInstance(Locale.US).format(money);
            ItemStack moneyPouch = new ItemBuilder(MoneyPouch.POUCH_MATERIAL, 1, Utils.chat("{#56eafa}&l► &7Right-Click to receive {#56eafa}$" + formattedMoney + "!")).setDisplayName(Utils.chat("&7&l☁ {#56eafa}&lMoney Pouch &7&l☁")).addPersistentDataDouble(money).addCustomModelData(MoneyPouch.POUCH_MODEL_DATA).toItemStack();
            Map<Integer, ItemStack> leftoverItems = receiver.getInventory().addItem(moneyPouch);
            if (!leftoverItems.isEmpty()) {
                receiver.getWorld().dropItemNaturally(receiver.getLocation(), moneyPouch);
            }
            receiver.sendMessage(Utils.chat(Utils.prefix + "&7You received a Money Pouch worth {#56eafa}$" + formattedMoney + "!"));
            sender.sendMessage(Utils.chat(Utils.prefix + "&7You gave {#56eafa}" + receiver.getName() + " &7a Money Pouch worth {#56eafa}$" + formattedMoney + "!"));
        } catch (NumberFormatException e1) {
            sender.sendMessage(Utils.chat("&cThat is not a number!"));
            return false;
        }
        return false;
    }
}
