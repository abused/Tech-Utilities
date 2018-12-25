package abused_master.techutilities.tiles;

import abused_master.techutilities.api.phase.EnergyStorage;
import abused_master.techutilities.registry.ModTiles;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public class TileEntityEnergyFurnace extends BlockEntity implements Tickable, SidedInventory {

    private DefaultedList<ItemStack> inventory = DefaultedList.create(2, ItemStack.EMPTY);
    public EnergyStorage storage = new EnergyStorage(50000);
    private int upgradeTier = 1;
    private int smeltTime = 0;
    private int energyUsagePerSmelt = 400;
    public boolean isRunning = false;

    public TileEntityEnergyFurnace() {
        super(ModTiles.ENERGY_FURNACE);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.upgradeTier = nbt.getInt("upgradeTier");
        this.smeltTime = nbt.getInt("smeltTime");
        this.energyUsagePerSmelt = nbt.getInt("energyUsagePerSmelt");
        this.isRunning = nbt.getBoolean("isRunning");
        this.storage.readFromNBT(nbt);
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        nbt.putInt("upgradeTier", upgradeTier);
        nbt.putInt("smeltTime", this.smeltTime);
        nbt.putInt("energyUsagePerSmelt", this.energyUsagePerSmelt);
        nbt.putBoolean("isRunning", this.isRunning);
        this.storage.writeEnergyToNBT(nbt);
        return super.toTag(nbt);
    }

    @Override
    public void tick() {
        this.storage.recieveEnergy(1);

        if(!inventory.get(0).isEmpty()) {
            Recipe recipe = this.world.getRecipeManager().get(this, this.world);
            if (this.canRun(recipe)) {
                smeltTime++;
                isRunning = true;
                if (smeltTime >= this.getTotalSmeltTime()) {
                    smeltTime = 0;
                    this.smeltItem(recipe);
                }
            } else {
                if (smeltTime > 0) {
                    smeltTime = 0;
                }
                isRunning = false;
            }
        }
    }

    public boolean canRun(Recipe recipe) {
        if(!inventory.get(0).isEmpty()) {
            if(!recipe.getOutput().isEmpty() && inventory.get(1).getAmount() < 64) {
                return true;
            }
        }

        return false;
    }

    public void smeltItem(Recipe recipe) {
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
        return 200 / this.upgradeTier;
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
}
