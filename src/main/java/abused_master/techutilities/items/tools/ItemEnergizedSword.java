package abused_master.techutilities.items.tools;

import abused_master.abusedlib.energy.EnergyStorage;
import abused_master.abusedlib.energy.IEnergyItemHandler;
import abused_master.techutilities.TechUtilities;
import net.minecraft.client.item.TooltipOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemEnergizedSword extends SwordItem implements IEnergyItemHandler {

    public EnergyStorage storage = new EnergyStorage(25000);

    public ItemEnergizedSword() {
        super(CustomToolMaterials.ENERGIZED_SWORD, 12, 0, new Settings().stackSize(1).itemGroup(TechUtilities.modItemGroup));
    }

    @Override
    public void onEntityTick(ItemStack itemStack_1, World world_1, Entity entity_1, int int_1, boolean boolean_1) {
    }

    @Override
    public boolean onEntityDamaged(ItemStack itemStack_1, LivingEntity livingEntity_1, LivingEntity livingEntity_2) {
        extractEnergy(500);
        return true;
    }

    @Override
    public float getWeaponDamage() {
        ItemStack sword = new ItemStack(this);
        if(sword.getTag() != null) {
            storage.readFromNBT(sword.getTag());
        }
        return storage.getEnergyCapacity() - storage.getEnergyStored();
    }

    @Override
    public boolean canDamage() {
        ItemStack sword = new ItemStack(this);
        if(sword.getTag() != null) {
            storage.readFromNBT(sword.getTag());
        }
        return storage.getEnergyStored() > 0;
    }

    @Override
    public boolean receiveEnergy(int amount) {
        if (canReceive(storage, amount)) {
            storage.recieveEnergy(amount);
            ItemStack sword = new ItemStack(this);
            if(sword.getTag() == null) {
                sword.setTag(new CompoundTag());
            }

            storage.writeEnergyToNBT(sword.getTag());
            return true;
        }

        return false;
    }

    @Override
    public boolean extractEnergy(int amount) {
        if(storage.getEnergyStored() >= amount) {
            storage.extractEnergy(amount);
            ItemStack sword = new ItemStack(this);
            if(sword.getTag() == null) {
                sword.setTag(new CompoundTag());
            }

            storage.writeEnergyToNBT(sword.getTag());
            return true;
        }

        return false;
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        ItemStack sword = new ItemStack(this);
        if(sword.getTag() != null) {
            storage.readFromNBT(sword.getTag());
        }
        return storage;
    }

    public boolean canReceive(EnergyStorage storage, int amount) {
        return (storage.getEnergyCapacity() - storage.getEnergyStored()) >= amount;
    }

    @Override
    public void buildTooltip(ItemStack stack, @Nullable World world, List<TextComponent> list, TooltipOptions tooltipOptions) {
        list.add(new StringTextComponent("Energy: " + storage.getEnergyStored() + " / " + storage.getEnergyCapacity() + " PE"));
    }
}
