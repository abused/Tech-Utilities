package abused_master.techutilities.tiles;

import abused_master.techutilities.registry.ModTiles;
import abused_master.techutilities.utils.CacheMapHolder;
import abused_master.techutilities.utils.energy.EnergyStorage;
import abused_master.techutilities.utils.energy.IEnergyReceiver;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.InventoryUtil;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

//TODO ADD UPGRADES
public class TileEntityEnergyFurnace extends TileEntityBase implements SidedInventory, IEnergyReceiver {

    public EnergyStorage storage = new EnergyStorage(50000);
    public DefaultedList<ItemStack> inventory = DefaultedList.create(2, ItemStack.EMPTY);
    private int upgradeTier = 1;
    private int smeltTime = 0;
    private int baseEnergyUsage = 400;

    public TileEntityEnergyFurnace() {
        super(ModTiles.ENERGY_FURNACE);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.upgradeTier = nbt.getInt("upgradeTier");
        this.smeltTime = nbt.getInt("smeltTime");
        this.storage.readFromNBT(nbt);

        inventory = DefaultedList.create(2, ItemStack.EMPTY);
        InventoryUtil.deserialize(nbt, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        nbt.putInt("upgradeTier", upgradeTier);
        nbt.putInt("smeltTime", this.smeltTime);
        this.storage.writeEnergyToNBT(nbt);
        InventoryUtil.serialize(nbt, this.inventory);
        return nbt;
    }

    @Override
    public void tick() {
        if(canRun()) {
            smeltTime++;
            if(smeltTime >= this.getTotalSmeltTime()) {
                smeltTime = 0;
                this.smeltItem();
                BlockState state = world.getBlockState(pos);
                world.updateListeners(pos, state, state, 3);
            }
        }else if (!canRun() && smeltTime > 0) {
            smeltTime = 0;
        }
    }

    public ItemStack getOutputStack() {
        if(!inventory.get(0).isEmpty()) {
            return CacheMapHolder.INSTANCE.getOutputStack(inventory.get(0));
        }

        return ItemStack.EMPTY;
    }

    public boolean canRun() {
        ItemStack output = getOutputStack();
        if(inventory.get(0).isEmpty() || output.isEmpty() || inventory.get(1).getAmount() > 64 || storage.getEnergyStored() < getEnergyUsage()) {
            return false;
        }else if(!inventory.get(1).isEmpty()) {
            if (output.getItem() != inventory.get(1).getItem()) {
                return false;
            }
        }

        return true;
    }

    public void smeltItem() {
        ItemStack output = getOutputStack();
        if(!output.isEmpty()) {
            if (inventory.get(1).isEmpty()) {
                inventory.set(1, output);
            } else {
                inventory.get(1).addAmount(1);
            }

            inventory.get(0).subtractAmount(1);
            storage.extractEnergy(getEnergyUsage());
        }
    }

    public int getEnergyUsage() {
        return baseEnergyUsage * upgradeTier;
    }

    public int getSmeltTime() {
        return smeltTime;
    }

    public int getTotalSmeltTime() {
        return 120 / this.upgradeTier;
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
    public boolean receiveEnergy(int amount) {
        if(canReceive(amount)) {
            storage.recieveEnergy(amount);
            return true;
        }

        return false;
    }

    public boolean canReceive(int amount) {
        return (storage.getEnergyCapacity() - storage.getEnergyStored()) >= amount;
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }
}
