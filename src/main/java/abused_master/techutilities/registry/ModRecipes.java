package abused_master.techutilities.registry;

import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.items.EnumResourceItems;
import abused_master.techutilities.utils.RecipeGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

public class ModRecipes {

    public static void registerRecipes() {
        //Furnace
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.COPPER_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.COPPER_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.TIN_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.TIN_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.LEAD_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.LEAD_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.SILVER_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.SILVER_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.NICKEL_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.NICKEL_ORE.getBlockOres())), 1, 200);
        RecipeGenerator.createFurnace(new ItemStack(EnumResourceItems.PLATINUM_INGOT.getItemIngot()), null, Ingredient.ofStacks(new ItemStack(BlockResources.EnumResourceOres.PLATINUM_ORE.getBlockOres())), 1, 200);

        RecipeGenerator.createShaped(new ItemStack(BlockResources.EnumResourceBlocks.COPPER_BLOCK.getBlockOres()), null, new RecipeGenerator.ShapedParser("XXX", "XXX", "XXX", 'X', new ItemStack(EnumResourceItems.COPPER_INGOT.getItemIngot())));
        RecipeGenerator.createShaped(new ItemStack(BlockResources.EnumResourceBlocks.TIN_BLOCK.getBlockOres()), null, new RecipeGenerator.ShapedParser("XXX", "XXX", "XXX", 'X', new ItemStack(EnumResourceItems.TIN_INGOT.getItemIngot())));
        RecipeGenerator.createShaped(new ItemStack(BlockResources.EnumResourceBlocks.LEAD_BLOCK.getBlockOres()), null, new RecipeGenerator.ShapedParser("XXX", "XXX", "XXX", 'X', new ItemStack(EnumResourceItems.LEAD_INGOT.getItemIngot())));
        RecipeGenerator.createShaped(new ItemStack(BlockResources.EnumResourceBlocks.SILVER_BLOCK.getBlockOres()), null, new RecipeGenerator.ShapedParser("XXX", "XXX", "XXX", 'X', new ItemStack(EnumResourceItems.SILVER_INGOT.getItemIngot())));
        RecipeGenerator.createShaped(new ItemStack(BlockResources.EnumResourceBlocks.NICKEL_BLOCK.getBlockOres()), null, new RecipeGenerator.ShapedParser("XXX", "XXX", "XXX", 'X', new ItemStack(EnumResourceItems.NICKEL_INGOT.getItemIngot())));
        RecipeGenerator.createShaped(new ItemStack(BlockResources.EnumResourceBlocks.PLATINUM_BLOCK.getBlockOres()), null, new RecipeGenerator.ShapedParser("XXX", "XXX", "XXX", 'X', new ItemStack(EnumResourceItems.PLATINUM_INGOT.getItemIngot())));
        RecipeGenerator.createShaped(new ItemStack(BlockResources.EnumResourceBlocks.INVAR_BLOCK.getBlockOres()), null, new RecipeGenerator.ShapedParser("XXX", "XXX", "XXX", 'X', new ItemStack(EnumResourceItems.INVAR_INGOT.getItemIngot())));
        RecipeGenerator.createShaped(new ItemStack(BlockResources.EnumResourceBlocks.ELECTRUM_BLOCK.getBlockOres()), null, new RecipeGenerator.ShapedParser("XXX", "XXX", "XXX", 'X', new ItemStack(EnumResourceItems.ELECTRUM_INGOT.getItemIngot())));
    }
}
