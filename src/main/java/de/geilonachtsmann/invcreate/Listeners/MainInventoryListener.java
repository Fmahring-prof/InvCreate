package de.geilonachtsmann.invcreate.Listeners;

import de.geilonachtsmann.invcreate.CreateMenuPlugin;
import de.geilonachtsmann.invcreate.CustomInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class MainInventoryListener implements Listener {
    private final CreateMenuPlugin plugin;

    public MainInventoryListener(CreateMenuPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Handle inventory click events here
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // Handle inventory close events here
    }

    public void openMenu(Player player, String inventoryName) {
        FileConfiguration config = plugin.getInventoryConfig(inventoryName);

        if (config == null) {
            player.sendMessage(ChatColor.RED + "Inventory not found");
            return;
        }

        int rowSize = config.getInt("rowSize");
        Inventory inventory = Bukkit.createInventory(new CustomInventoryHolder(null, inventoryName), rowSize * 9, inventoryName);

        // Populate inventory with items from config file here

        player.openInventory(inventory);
    }
}

