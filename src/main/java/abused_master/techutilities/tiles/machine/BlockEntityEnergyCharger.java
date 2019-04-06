package abused_master.techutilities.tiles.machine;

import abused_master.abusedlib.tiles.BlockEntityBase;
import abused_master.techutilities.items.EnumResourceItems;
import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.registry.ModItems;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import nerdhub.cardinalenergy.api.IEnergyHandler;
import nerdhub.cardinalenergy.api.IEnergyItemHandler;
import nerdhub.cardinalenergy.impl.EnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

public class BlockEntityEnergyCharger extends BlockEntityBase implements IEnergyHandler, SidedInventory, ILinkerHandler {

    public EnergyStorage storage = new EnergyStorage(50000);
    public DefaultedList<ItemStack> inventory = DefaultedList.create(2, ItemStack.EMPTY);
    public int chargePerTick = 50;

    public BlockEntityEnergyCharger() {
        super(ModBlockEntities.ENERGY_CHARGER);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.storage.readEnergyFromTag(nbt);

        inventory = DefaultedList.create(2, ItemStack.EMPTY);
        Inventories.fromTag(nbt, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        this.storage.writeEnergyToTag(nbt);
        Inventories.toTag(nbt, this.inventory);
        return nbt;
    }

    @Override
    public void tick() {
        if (!inventory.get(0).isEmpty() && inventory.get(0).getItem() instanceof IEnergyItemHandler) {
            ItemStack stack = inventory.get(0);
            IEnergyItemHandler energyItemHandler = (IEnergyItemHandler) stack.getItem();

            if(isEnergyFull(energyItemHandler, stack)) {
                inventory.set(0, ItemStack.EMPTY);
                if(stack.getItem() == ModItems.STEEL_INGOT) {
                    inventory.set(1, new ItemStack(EnumResourceItems.ENERGIZED_STEEL_INGOT.getItemIngot()));
                }else {
                    inventory.set(1, stack);
                }
            }else {
               if(storage.canExtract(chargePerTick)) {
                   storage.extractEnergy(energyItemHandler.getEnergyStorage().receiveEnergy(stack, chargePerTick));
               }
            }
        }
    }

    public boolean isEnergyFull(IEnergyItemHandler energyItemHandler, ItemStack stack) {
        return energyItemHandler.getEnergyStorage().getEnergyStored(stack) == energyItemHandler.getEnergyStorage().getEnergyCapacity(stack);
    }

    @Override
    public int[] getInvAvailableSlots(Direction direction) {
        return new int[] {0, 1};
    }

    @Override
    public boolean canInsertInvStack(int i, ItemStack itemStack, @Nullable Direction direction) {
        return i != 1;
    }

    @Override
    public boolean canExtractInvStack(int i, ItemStack itemStack, Direction direction) {
        return i != 0;
    }

    @Override
    public int getInvSize() {
        return inventory.size();
    }

    @Override
    public boolean isInvEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getInvStack(int i) {
        return inventory.get(i);
    }

    @Override
    public ItemStack takeInvStack(int i, int i1) {
        return inventory.get(i);
    }

    @Override
    public ItemStack removeInvStack(int i) {
        return Inventories.removeStack(this.inventory, i);
    }

    @Override
    public void setInvStack(int i, ItemStack itemStack) {
        inventory.set(i, itemStack);
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public EnergyStorage getEnergyStorage(Direction direction) {
        return storage;
    }

    @Override
    public void link(PlayerEntity player, CompoundTag tag) {
        if(!world.isClient) {
            if (tag.containsKey("collectorPos")) {
                tag.remove("collectorPos");
            }
            tag.put("blockPos", TagHelper.serializeBlockPos(pos));
            player.addChatMessage(new StringTextComponent("Saved block position!"), true);
        }
    }
}
