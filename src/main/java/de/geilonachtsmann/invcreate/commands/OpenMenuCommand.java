package de.geilonachtsmann.invcreate.commands;

import de.geilonachtsmann.invcreate.CreateMenuPlugin;
import de.geilonachtsmann.invcreate.CustomInventoryHolder;
import de.geilonachtsmann.invcreate.Listeners.MainInventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OpenMenuCommand implements TabExecutor {
    private CreateMenuPlugin plugin;

    public OpenMenuCommand(CreateMenuPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /openmenu <inventoryName>");
            return true;
        }

        String inventoryName = args[0];
        File inventoryFile = new File(plugin.getInventoryFolder(), inventoryName + ".yml");

        if (!inventoryFile.exists()) {
            sender.sendMessage(ChatColor.RED + "Inventory not found.");
            return true;
        }

        YamlConfiguration inventoryConfig = YamlConfiguration.loadConfiguration(inventoryFile);
        int rows = inventoryConfig.getInt("rows");

        CustomInventoryHolder holder = new CustomInventoryHolder(null, inventoryName);
        Inventory inventory = Bukkit.createInventory(holder, rows * 9, inventoryName);

        for (String key : inventoryConfig.getKeys(false)) {
            if (!key.startsWith("slot")) {
                continue;
            }

            int slot = Integer.parseInt(key.substring(4));
            ItemStack item = inventoryConfig.getItemStack(key);

            inventory.setItem(slot, item);
        }

        Player player = (Player) sender;
        player.openInventory(inventory);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return plugin.getInventoryNames().stream()
                    .filter(name -> name.startsWith(args[0]))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}



