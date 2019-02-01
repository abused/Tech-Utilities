package abused_master.techutilities.registry;

import abused_master.techutilities.items.EnumResourceItems;
import abused_master.techutilities.utils.RecipeGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModRecipes {

    public static void registerRecipes() {
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.ENERGY_FURNACE), null, new RecipeGenerator.ShapedParser("GFG", "PRP", "PCP", 'G', Blocks.GLASS, 'F', Blocks.FURNACE, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'C', ModBlocks.PHASE_CELL));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.PULVERIZER), null, new RecipeGenerator.ShapedParser("PRP", "PRP", "FCF", 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'R', Items.REDSTONE, 'F', Items.FLINT, 'C', ModBlocks.PHASE_CELL));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.QUARRY), null, new RecipeGenerator.ShapedParser("ORO", "PDP", "OCO", 'O', Blocks.OBSIDIAN, 'R', Items.REDSTONE, 'P', Items.DIAMOND_PICKAXE, "C", ModBlocks.PHASE_CELL));
    }
}
