package de.geilonachtsmann.temp;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CreateMenuPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("createMenu")) {
            if (args.length != 2) {
                sender.sendMessage("Usage: /createMenu inventoryName rowsize");
                return false;
            }

            String inventoryName = args[0];
            int rowSize;
            try {
                rowSize = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid row size: " + args[1]);
                return false;
            }

            File configFile = new File(getDataFolder(), inventoryName + ".yml");
            if (configFile.exists()) {
                sender.sendMessage("Inventory already exists with name: " + inventoryName);
                return false;
            }

            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            config.set("rows", rowSize);
            try {
                config.save(configFile);
                sender.sendMessage("Inventory created with name: " + inventoryName + " and row size: " + rowSize);
                return true;
            } catch (IOException e) {
                sender.sendMessage("Error saving inventory config file: " + e.getMessage());
                return false;
            }
        }

        return false;
    }
}


