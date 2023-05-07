package de.geilonachtsmann.invcreate;

import de.geilonachtsmann.invcreate.Listeners.MainInventoryListener;
import de.geilonachtsmann.invcreate.commands.CreateMenuCommand;
import de.geilonachtsmann.invcreate.commands.OpenMenuCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateMenuPlugin extends JavaPlugin {
    private File inventoryFolder;

    @Override
    public void onEnable() {
        inventoryFolder = new File(getDataFolder(), "inventories");
        inventoryFolder.mkdirs();
        getCommand("createmenu").setExecutor(new CreateMenuCommand(this));
        getCommand("openmenu").setExecutor(new OpenMenuCommand(this));
        getServer().getPluginManager().registerEvents(new MainInventoryListener(this), this);
    }

    public FileConfiguration getInventoryConfig(String inventoryName) {
        File file = new File(inventoryFolder, inventoryName + ".yml");

        if (!file.exists()) {
            return null;
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public List<String> getInventoryNames() {
        List<String> inventoryNames = new ArrayList<>();
        File[] inventoryFiles = inventoryFolder.listFiles();

        if (inventoryFiles == null) {
            return inventoryNames;
        }

        for (File file : inventoryFiles) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                inventoryNames.add(file.getName().substring(0, file.getName().length() - 4));
            }
        }

        return inventoryNames;
    }

    public File getInventoryFolder() {
        File inventoryFolder = new File(this.getDataFolder(), "inventories");
        if (!inventoryFolder.exists()) {
            inventoryFolder.mkdirs();
        }
        return inventoryFolder;
    }

}



