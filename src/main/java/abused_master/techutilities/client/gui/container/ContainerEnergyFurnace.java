package abused_master.techutilities.client.gui.container;

import abused_master.techutilities.client.gui.slots.OutputSlot;
import net.minecraft.container.ContainerListener;
import net.minecraft.container.CraftingContainer;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.world.World;

public class ContainerEnergyFurnace extends CraftingContainer {

    private final Inventory inventory;
    private final World world;

    public ContainerEnergyFurnace(PlayerInventory playerInventory, Inventory inventory) {
        this.inventory = inventory;
        this.world = playerInventory.player.world;

        this.addSlot(new Slot(inventory, 0, 56, 26));
        this.addSlot(new OutputSlot(inventory, 1, 116, 26));


        int i;
        for(i = 0; i < 3; ++i) {
            for(int var4 = 0; var4 < 9; ++var4) {
                this.addSlot(new Slot(playerInventory, var4 + i * 9 + 9, 8 + var4 * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void addListener(ContainerListener containerListener) {
        super.addListener(containerListener);
        containerListener.onContainerInvRegistered(this, this.inventory);
    }

    @Override
    public void populateRecipeFinder(RecipeFinder recipeFinder) {
        if (this.inventory instanceof RecipeInputProvider) {
            ((RecipeInputProvider) this.inventory).provideRecipeInputs(recipeFinder);
        }
    }

    @Override
    public void clearCraftingSlots() {
        this.inventory.clearInv();
    }

    @Override
    public boolean matches(Recipe recipe) {
        return recipe.matches(this.inventory, world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 1;
    }

    @Override
    public int getCraftingWidth() {
        return 1;
    }

    @Override
    public int getCraftingHeight() {
        return 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 2;
    }

    @Override
    public void setProperty(int int_1, int int_2) {
        this.inventory.setInvProperty(int_1, int_2);
    }

    @Override
    public boolean canUse(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity playerEntity, int i) {
        return ItemStack.EMPTY;
    }
}
