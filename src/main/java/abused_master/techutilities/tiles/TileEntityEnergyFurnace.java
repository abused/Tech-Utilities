package abused_master.techutilities.tiles;

import abused_master.techutilities.api.phase.EnergyStorage;
import abused_master.techutilities.registry.ModTiles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.smelting.SmeltingRecipe;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.InventoryUtil;
import net.minecraft.util.math.Direction;

public class TileEntityEnergyFurnace extends TileEntityEnergyBase implements SidedInventory {

    private DefaultedList<ItemStack> inventory;
    public EnergyStorage storage = new EnergyStorage(50000);
    private int upgradeTier = 1;
    private int smeltTime = 0;
    private int energyUsagePerSmelt = 400;

    public TileEntityEnergyFurnace() {
        super(ModTiles.ENERGY_FURNACE);
        inventory = DefaultedList.create(2, ItemStack.EMPTY);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.upgradeTier = nbt.getInt("upgradeTier");
        this.smeltTime = nbt.getInt("smeltTime");
        this.energyUsagePerSmelt = nbt.getInt("energyUsagePerSmelt");
        this.storage.readFromNBT(nbt);
        inventory = DefaultedList.create(2, ItemStack.EMPTY);
        InventoryUtil.deserialize(nbt, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        nbt.putInt("upgradeTier", upgradeTier);
        nbt.putInt("smeltTime", this.smeltTime);
        nbt.putInt("energyUsagePerSmelt", this.energyUsagePerSmelt);
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
                storage.extractEnergy(energyUsagePerSmelt);
                BlockState state = world.getBlockState(pos);
                world.updateListeners(pos, state, state, 3);
            }
        }else {
            if (smeltTime > 0) {
                smeltTime = 0;
                BlockState state = world.getBlockState(pos);
                world.updateListeners(pos, state, state, 3);
            }
        }
    }

    public SmeltingRecipe getRecipeFromInput() {
        Recipe recipe = null;

        for (Recipe recipe1 : world.getRecipeManager().values()) {
            if(recipe1 instanceof SmeltingRecipe && recipe1.getPreviewInputs().get(0).matches(inventory.get(0))) {
                recipe = recipe1;
                break;
            }
        }

        return (SmeltingRecipe) recipe;
    }

    public boolean canRun() {
        SmeltingRecipe recipe = getRecipeFromInput();
        if(recipe == null || inventory.get(0).isEmpty()|| recipe.getOutput().isEmpty() || inventory.get(1).getAmount() > 64 || storage.getEnergyStored() < energyUsagePerSmelt) {
            return false;
        }else if(!inventory.get(1).isEmpty()) {
            if (!ItemStack.areEqual(recipe.getOutput(), inventory.get(1))) {
                return false;
            }
        }

        return true;
    }

    public void smeltItem() {
        SmeltingRecipe recipe = getRecipeFromInput();
        ItemStack output = recipe.getOutput();

        if(inventory.get(1).isEmpty()) {
            inventory.set(1, output);
        }else {
            inventory.get(1).addAmount(1);
        }

        inventory.get(0).subtractAmount(1);
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
    public boolean canInsertInvStack(int i, ItemStack itemStack, Direction direction) {
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
        return inventory.get(i);
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
    public TextComponent getName() {
        return new StringTextComponent("RF Furnace");
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public boolean isEnergyReceiver() {
        return true;
    }
}
