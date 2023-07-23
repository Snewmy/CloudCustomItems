package me.Sam.customitems.commandshit;

import me.Sam.customitems.CustomItems;
import me.Sam.customitems.Locale;
import me.Sam.customitems.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CustomItemsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("customitems")) {
            if (!sender.hasPermission("cloudcustomitems.admin")) {
                sender.sendMessage(Utils.chat(Locale.instance.get("nopermission")));
                return false;
            }
            if (args.length < 1) {
                sender.sendMessage(Utils.chat(Locale.instance.get("notenoughargsreload")));
                return false;
            } else if (args[0].equalsIgnoreCase("reload")) {
                long currentTime = System.currentTimeMillis();
                CustomItems.instance.messages = YamlConfiguration.loadConfiguration(CustomItems.instance.messagesFile);
                sender.sendMessage(Utils.chat(Locale.instance.get("reloadmessage").replace("%time%", System.currentTimeMillis() - currentTime + "")));
                return false;
            }
        }
        return false;
    }
}
