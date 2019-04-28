package abused_master.techutilities.utils;

import nerdhub.cardinalenergy.impl.ItemEnergyStorage;
import net.minecraft.item.ItemStack;

public class ItemHelper {

    public static void updateItemDurability(ItemStack stack, ItemEnergyStorage storage) {
        stack.setDamage(storage.getEnergyCapacity(stack) - storage.getEnergyStored(stack));
    }
}
