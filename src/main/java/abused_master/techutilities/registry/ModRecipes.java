package abused_master.techutilities.registry;

import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.items.EnumResourceItems;
import abused_master.techutilities.utils.RecipeGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

public class ModRecipes {

    public static void registerRecipes() {
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.COPPER_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.COPPER_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.TIN_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.TIN_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.LEAD_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.LEAD_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.SILVER_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.SILVER_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.NICKEL_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.NICKEL_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.PLATINUM_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.PLATINUM_ORE.getBlockOres())), 1, 200);
    }
}
