package abused_master.techutilities.items;

import abused_master.abusedlib.items.ItemBase;
import abused_master.techutilities.TechUtilities;

public enum EnumResourceItems {
    FLOW_INGOT,
    FLOW_DUST,
    COAL_DUST,
    DIAMOND_DUST,
    IRON_DUST,
    GOLD_DUST;

    private ItemBase itemResource;

    EnumResourceItems() {
        this.itemResource = new ItemBase(getName(), TechUtilities.modItemGroup);
    }

    public String getName() {
        return this.toString().toLowerCase();
    }

    public ItemBase getItemIngot() {
        return itemResource;
    }
}
