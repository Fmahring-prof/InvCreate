package de.geilonachtsmann.invcreate;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CustomInventoryHolder implements InventoryHolder {
    private final Inventory inventory;
    private final String inventoryName;

    public CustomInventoryHolder(Inventory inventory, String inventoryName) {
        this.inventory = inventory;
        this.inventoryName = inventoryName;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}


