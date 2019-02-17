package abused_master.techutilities.registry;

import abused_master.techutilities.blocks.generators.EnumSolarPanelTypes;
import abused_master.techutilities.items.EnumResourceItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModRecipes {

    public static void registerRecipes() {
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.ENERGY_FURNACE), null, new RecipeGenerator.ShapedParser("GFG", "PRP", "PCP", 'G', Blocks.GLASS, 'F', Blocks.FURNACE, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'C', ModBlocks.PHASE_CELL, 'R', Items.REDSTONE));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.PULVERIZER), null, new RecipeGenerator.ShapedParser("PRP", "PRP", "FCF", 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'R', Items.REDSTONE, 'F', Items.FLINT, 'C', ModBlocks.PHASE_CELL));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.QUARRY), null, new RecipeGenerator.ShapedParser("ORO", "PDP", "OCO", 'O', Blocks.OBSIDIAN, 'R', Items.REDSTONE, 'P', Items.DIAMOND_PICKAXE, 'C', ModBlocks.PHASE_CELL, 'D', Blocks.CRAFTING_TABLE));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.CONVEYOR_BELT, 8), null, new RecipeGenerator.ShapedParser("LLL", "IRI", 'L', Items.LEATHER, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.LAVA_GENERATOR), null, new RecipeGenerator.ShapedParser("PFP", "LCL", "PFP", 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'F', Blocks.FURNACE, 'L', Items.LAVA_BUCKET, 'C', ModBlocks.PHASE_CELL));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.VACUUM), null, new RecipeGenerator.ShapedParser("HPH", "C C", "HPH", 'H', Blocks.HOPPER, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'C', Blocks.CHEST));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.FLUID_PUMP), null, new RecipeGenerator.ShapedParser("BPB", "PCP", "BPB", 'B', Items.BUCKET, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'C', Blocks.CHEST));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.MOB_GRINDER), null, new RecipeGenerator.ShapedParser("SCS", "DND", "SCS", 'S', Blocks.WITHER_SKELETON_SKULL, 'C', ModBlocks.PHASE_CELL, 'D', Items.DIAMOND_SWORD));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.ENERGY_CHARGER), null, new RecipeGenerator.ShapedParser("GRG", "PCP", "GRG", 'G', Items.GOLD_INGOT, 'R', Items.REDSTONE, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'C', ModBlocks.PHASE_CELL));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.FARMER), null, new RecipeGenerator.ShapedParser("PDP", "ACH", "PCP", 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'D', Items.DIAMOND, 'A', Items.IRON_AXE, 'C', Blocks.CHEST, 'H', Items.IRON_HOE, 'C', ModBlocks.PHASE_CELL));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.PHASE_CELL), null, new RecipeGenerator.ShapedParser("PLPee", "GRG", "PLP", 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'L', Items.LAPIS_LAZULI, 'G', Blocks.GLASS, 'R', Blocks.REDSTONE_BLOCK));
        RecipeGenerator.createShaped(new ItemStack(EnumSolarPanelTypes.SOLAR_PANEL_MK1.getBlockSolar()), null, new RecipeGenerator.ShapedParser("DDD", "LCL", "PGP", 'D', Blocks.DAYLIGHT_DETECTOR, 'L', Items.LAPIS_LAZULI, 'C', ModBlocks.PHASE_CELL, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'G', Items.GLOWSTONE_DUST));
        RecipeGenerator.createShaped(new ItemStack(EnumSolarPanelTypes.SOLAR_PANEL_MK2.getBlockSolar()), null, new RecipeGenerator.ShapedParser("SSS", "IGI", "PLP", 'S', EnumSolarPanelTypes.SOLAR_PANEL_MK1.getBlockSolar(), 'I', Items.IRON_INGOT, 'G', Blocks.GLOWSTONE, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'L', Items.LAPIS_LAZULI));
        RecipeGenerator.createShaped(new ItemStack(EnumSolarPanelTypes.SOLAR_PANEL_MK3.getBlockSolar()), null, new RecipeGenerator.ShapedParser("SSS", "GDG", "PLP", 'S', EnumSolarPanelTypes.SOLAR_PANEL_MK2.getBlockSolar(), 'G', Items.GOLD_INGOT, 'D', Blocks.DIAMOND_BLOCK, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'L', Blocks.SEA_LANTERN));

        RecipeGenerator.createShaped(new ItemStack(ModBlocks.ENERGY_CRYSTAL), null, new RecipeGenerator.ShapedParser("GPG", "GPG", "GPG", 'G', Items.GOLD_INGOT, 'P', EnumResourceItems.PHASE_INGOT));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.ENERGY_CRYSTAL_COLLECTOR), null, new RecipeGenerator.ShapedParser("GPG", "GPG", "OOO", 'G', Items.GOLD_INGOT, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'O', Blocks.OBSIDIAN));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.ITEM_RECEIVER), null, new RecipeGenerator.ShapedParser("OPO", "GPG", "OPO", 'O', Blocks.OBSIDIAN, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'G', Items.GOLD_INGOT));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.ITEM_TRANSFER), null, new RecipeGenerator.ShapedParser("GPG", "OPO", "GPG", 'G', Items.GOLD_INGOT, 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'O', Blocks.OBSIDIAN));

        RecipeGenerator.createShaped(new ItemStack(ModBlocks.GLASS_BLOCK, 8), null, new RecipeGenerator.ShapedParser("GGG", "GIG", "GGG", 'G', Blocks.GLASS, 'I', Items.INK_SAC));
        RecipeGenerator.createShaped(new ItemStack(ModBlocks.BLACK_GLASS_BLOCK, 8), null, new RecipeGenerator.ShapedParser("GGG", "GIG", "GGG", 'G', Blocks.GLASS, 'I', Items.BONE_MEAL));

        RecipeGenerator.createShaped(new ItemStack(ModItems.RECORDER), null, new RecipeGenerator.ShapedParser("PGP", "IBI", "PGP", 'P', EnumResourceItems.PHASE_INGOT.getItemIngot(), 'G', Items.GOLD_INGOT, 'I', Items.INK_SAC, 'B', Items.WRITABLE_BOOK));
        RecipeGenerator.createShaped(new ItemStack(ModItems.LINKER), null, new RecipeGenerator.ShapedParser("P P", " P ", " P ", 'P', EnumResourceItems.PHASE_INGOT));
        RecipeGenerator.createShapeless(new ItemStack(EnumResourceItems.PHASE_INGOT.getItemIngot(), 4), null, new RecipeGenerator.ShapelessParser(Items.IRON_INGOT, Items.ENDER_PEARL, Items.REDSTONE));
    }
}
