package de.geilonachtsmann.invcreate.commands;

import de.geilonachtsmann.invcreate.CreateMenuPlugin;
import de.geilonachtsmann.invcreate.CustomInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

public class CreateMenuCommand implements CommandExecutor {
    private CreateMenuPlugin plugin;

    public CreateMenuCommand(CreateMenuPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /createmenu <inventoryName> <rowSize>");
            return true;
        }

        String inventoryName = args[0];
        int rowSize;

        try {
            rowSize = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid row size. Please enter a number between 1 and 6.");
            return true;
        }

        if (rowSize < 1 || rowSize > 6) {
            sender.sendMessage(ChatColor.RED + "Invalid row size. Please enter a number between 1 and 6.");
            return true;
        }

        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(new CustomInventoryHolder(null, inventoryName), rowSize * 9, inventoryName);
        player.openInventory(inventory);

        createInventoryConfig(inventoryName, rowSize);

        sender.sendMessage(ChatColor.GREEN + "Successfully created inventory " + inventoryName + " with " + rowSize + " rows.");

        return true;
    }

    private void createInventoryConfig(String inventoryName, int rowSize) {
        File file = new File(plugin.getDataFolder(), "inventories/" + inventoryName + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("rowSize", rowSize);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



