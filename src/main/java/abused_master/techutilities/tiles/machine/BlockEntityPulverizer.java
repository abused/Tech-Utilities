package abused_master.techutilities.tiles.machine;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.registry.PulverizerRecipes;
import abused_master.techutilities.tiles.BlockEntityEnergy;
import abused_master.techutilities.utils.energy.EnergyStorage;
import abused_master.techutilities.utils.energy.IEnergyReceiver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.InventoryUtil;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

public class BlockEntityPulverizer extends BlockEntityEnergy implements IEnergyReceiver, SidedInventory {

    public EnergyStorage storage = new EnergyStorage(50000);
    public DefaultedList<ItemStack> inventory = DefaultedList.create(3, ItemStack.EMPTY);
    private int upgradeTier = 1;
    private int pulverizeTime = 0;
    private int baseEnergyUsage = 400;

    public BlockEntityPulverizer() {
        super(ModBlockEntities.PULVERIZER);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.upgradeTier = nbt.getInt("upgradeTier");
        this.pulverizeTime = nbt.getInt("pulverizeTime");
        this.storage.readFromNBT(nbt);

        inventory = DefaultedList.create(3, ItemStack.EMPTY);
        InventoryUtil.deserialize(nbt, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        nbt.putInt("upgradeTier", upgradeTier);
        nbt.putInt("pulverizeTime", this.pulverizeTime);
        this.storage.writeEnergyToNBT(nbt);
        InventoryUtil.serialize(nbt, this.inventory);
        return nbt;
    }

    @Override
    public void tick() {
        if(canRun()) {
            pulverizeTime++;
            if(pulverizeTime >= getTotalPulverizeTime()) {
                pulverizeTime = 0;
                pulverizeItem();
                world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        }else if(!canRun() && pulverizeTime > 0) {
            pulverizeTime = 0;
        }
    }

    public boolean canRun() {
        PulverizerRecipes.PulverizerRecipe recipe = PulverizerRecipes.INSTANCE.getOutputRecipe(inventory.get(0));
        if(inventory.get(0).isEmpty() || recipe == null || storage.getEnergyStored() < getEnergyUsage()) {
            return false;
        }else if(!inventory.get(1).isEmpty()) {
            if(recipe.getOutput().getItem() != inventory.get(1).getItem() || (inventory.get(1).getAmount() + recipe.getOutputAmount()) > 64) {
                return false;
            }
        }else if(!inventory.get(2).isEmpty() && !recipe.getOutput().isEmpty()) {
            if(recipe.getRandomDrop().getItem() != inventory.get(2).getItem() || (inventory.get(2).getAmount() + recipe.getRandomDropAmoumt()) > 64) {
                return false;
            }
        }

        return true;
    }

    public void pulverizeItem() {
        PulverizerRecipes.PulverizerRecipe recipe = PulverizerRecipes.INSTANCE.getOutputRecipe(inventory.get(0));
        if(inventory.get(1).isEmpty()) {
            inventory.set(1, recipe.getOutput());
            inventory.get(1).addAmount(recipe.getOutputAmount() - 1);
        }else {
            inventory.get(1).addAmount(recipe.getOutputAmount());
        }

        if(!recipe.getRandomDrop().isEmpty()) {
            float chance = world.getRandom().nextFloat() * 100;
            if(chance <= recipe.getPercentageDrop()) {
                if (inventory.get(2).isEmpty()) {
                    inventory.set(2, recipe.getRandomDrop());
                    inventory.get(2).addAmount(recipe.getRandomDropAmoumt() - 1);
                } else {
                    inventory.get(2).addAmount(recipe.getRandomDropAmoumt());
                }
            }
        }

        inventory.get(0).subtractAmount(1);
        storage.extractEnergy(getEnergyUsage());
    }

    public int getEnergyUsage() {
        return baseEnergyUsage * upgradeTier;
    }

    public int getPulverizeTime() {
        return pulverizeTime;
    }

    public int getTotalPulverizeTime() {
        return 120 / this.upgradeTier;
    }

    @Override
    public int[] getInvAvailableSlots(Direction direction) {
        return new int[] {0, 1, 2};
    }

    @Override
    public boolean canInsertInvStack(int i, ItemStack itemStack, @Nullable Direction direction) {
        return i != 1 && i != 2;
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
        return handleEnergyReceive(storage, amount);
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }
}
