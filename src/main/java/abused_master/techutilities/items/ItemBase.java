package abused_master.techutilities.items;

import abused_master.techutilities.TechUtilities;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase() {
        super(new Settings().itemGroup(TechUtilities.modItemGroup));
    }
}
