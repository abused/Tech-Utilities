package abused_master.techutilities.tiles.machine;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityEnergy;
import abused_master.techutilities.energy.EnergyStorage;
import abused_master.techutilities.energy.IEnergyItemHandler;
import abused_master.techutilities.energy.IEnergyReceiver;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.InventoryUtil;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

public class BlockEntityEnergyCharger extends BlockEntityEnergy implements IEnergyReceiver, SidedInventory, ILinkerHandler {

    public EnergyStorage storage = new EnergyStorage(50000);
    public DefaultedList<ItemStack> inventory = DefaultedList.create(2, ItemStack.EMPTY);
    public int chargePerTick = 50;

    public BlockEntityEnergyCharger() {
        super(ModBlockEntities.ENERGY_CHARGER);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.storage.readFromNBT(nbt);

        inventory = DefaultedList.create(2, ItemStack.EMPTY);
        InventoryUtil.deserialize(nbt, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        this.storage.writeEnergyToNBT(nbt);
        InventoryUtil.serialize(nbt, this.inventory);
        return nbt;
    }

    @Override
    public void tick() {
        if(!inventory.get(0).isEmpty() && inventory.get(0).getItem() instanceof IEnergyItemHandler) {
            IEnergyItemHandler energyItemHandler = (IEnergyItemHandler) inventory.get(0).getItem();
            if(isEnergyFull(energyItemHandler) && inventory.get(1).isEmpty()) {
                inventory.set(1, inventory.get(0));
                inventory.set(0, ItemStack.EMPTY);
            }else if(!isEnergyFull(energyItemHandler) && (storage.getEnergyStored() >= chargePerTick || storage.getEnergyStored() >= getChargeAmount(energyItemHandler))) {
                storage.extractEnergy(getChargeAmount(energyItemHandler) >= chargePerTick ? chargePerTick : getChargeAmount(energyItemHandler));
                energyItemHandler.receiveEnergy(getChargeAmount(energyItemHandler) >= chargePerTick ? chargePerTick : getChargeAmount(energyItemHandler));
            }
        }
    }

    public int getChargeAmount(IEnergyItemHandler energyItemHandler) {
        return energyItemHandler.getEnergyStorage().getEnergyCapacity() - energyItemHandler.getEnergyStorage().getEnergyStored();
    }

    public boolean isEnergyFull(IEnergyItemHandler energyItemHandler) {
        return energyItemHandler.getEnergyStorage().getEnergyStored() == energyItemHandler.getEnergyStorage().getEnergyCapacity();
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
        return InventoryUtil.removeStack(this.inventory, i);
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
    public void clearInv() {
        inventory.clear();
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public boolean receiveEnergy(int amount) {
        return handleEnergyReceive(storage, amount);
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
