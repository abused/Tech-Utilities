package abused_master.techutilities.items;

import abused_master.abusedlib.items.ItemBase;
import abused_master.techutilities.TechUtilities;

public enum EnumResourceItems {
    //Ingots
    COPPER_INGOT,
    TIN_INGOT,
    LEAD_INGOT,
    SILVER_INGOT,
    NICKEL_INGOT,
    PLATINUM_INGOT,
    ELECTRUM_INGOT,
    INVAR_INGOT,

    //Dusts
    COPPER_DUST,
    TIN_DUST,
    LEAD_DUST,
    SILVER_DUST,
    NICKEL_DUST,
    PLATINUM_DUST,
    ELECTRUM_DUST,
    COAL_DUST,
    DIAMOND_DUST,
    IRON_DUST,
    GOLD_DUST,
    INVAR_DUST;

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
