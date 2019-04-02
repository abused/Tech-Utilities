package abused_master.techutilities.items.tools;

import abused_master.techutilities.TechUtilities;
import nerdhub.cardinalenergy.api.IEnergyItemHandler;
import nerdhub.cardinalenergy.impl.ItemEnergyStorage;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemEnergizedSword extends SwordItem implements IEnergyItemHandler {

    public ItemEnergyStorage storage = new ItemEnergyStorage(25000);

    public ItemEnergizedSword() {
        super(CustomToolMaterials.ENERGIZED_SWORD, 12, 0, new Settings().stackSize(1).itemGroup(TechUtilities.modItemGroup).durability(25000));
    }

    @Override
    public boolean onEntityDamaged(ItemStack stack, LivingEntity livingEntity, LivingEntity damagedEntity) {
        if (storage.getEnergyStored(stack) >= 500) {
            storage.extractEnergy(stack, 500);
            return true;
        }

        return false;
    }

    @Override
    public boolean canDamage() {
        return getDurability() >= 500;
    }

    @Override
    public ItemEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    @Override
    public void buildTooltip(ItemStack stack, @Nullable World world, List<TextComponent> list, TooltipContext tooltipOptions) {
        list.add(new StringTextComponent("Energy: " + storage.getEnergyStored(stack) + " / " + storage.getEnergyCapacity(stack) + " CE"));
    }
}
