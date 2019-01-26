package abused_master.techutilities.registry;

import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.items.EnumResourceItems;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;

import java.util.Map;

public class PulverizerRecipes {

    public static PulverizerRecipes INSTANCE = instance();
    private final Map<ItemStack, PulverizerRecipe> recipesMap = Maps.newHashMap();

    private static PulverizerRecipes instance() {
        return new PulverizerRecipes();
    }

    public PulverizerRecipes() {
        //Default recipes
        registerRecipe(new ItemStack(Blocks.STONE), new ItemStack(Blocks.COBBLESTONE), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL), 1, new ItemStack(Blocks.SAND), 1, 15);
        registerRecipe(new ItemStack(Blocks.NETHERRACK), new ItemStack(Blocks.GRAVEL), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND), 1, new ItemStack(Items.FLINT), 1, 15);
        registerRecipe(new ItemStack(Blocks.SANDSTONE), new ItemStack(Blocks.SAND), 2, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.SMOOTH_SANDSTONE), new ItemStack(Blocks.SAND), 2, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.CHISELED_SANDSTONE), new ItemStack(Blocks.SAND), 2, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.CLAY), new ItemStack(Items.CLAY_BALL), 4, ItemStack.EMPTY, 0, 0);

        registerRecipe(new ItemStack(Items.COAL), new ItemStack(EnumResourceItems.COAL_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Items.CHARCOAL), new ItemStack(EnumResourceItems.COAL_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Items.BONE), new ItemStack(Items.BONE_MEAL), 6, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.GLOWSTONE_DUST), 4, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER), 4, ItemStack.EMPTY, 0, 0);

        registerRecipe(new ItemStack(Blocks.IRON_ORE), new ItemStack(EnumResourceItems.IRON_DUST.getItemIngot()), 2, new ItemStack(EnumResourceItems.NICKEL_DUST.getItemIngot()), 1, 10);
        registerRecipe(new ItemStack(Blocks.GOLD_ORE), new ItemStack(EnumResourceItems.GOLD_DUST.getItemIngot()), 2, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(BlockResources.EnumResourceOres.COPPER_ORE.getBlockOres()), new ItemStack(EnumResourceItems.COPPER_DUST.getItemIngot()), 2, new ItemStack(EnumResourceItems.GOLD_DUST.getItemIngot()), 1, 10);
        registerRecipe(new ItemStack(BlockResources.EnumResourceOres.TIN_ORE.getBlockOres()), new ItemStack(EnumResourceItems.TIN_DUST.getItemIngot()), 2, new ItemStack(EnumResourceItems.IRON_DUST.getItemIngot()), 1, 10);
        registerRecipe(new ItemStack(BlockResources.EnumResourceOres.SILVER_ORE.getBlockOres()), new ItemStack(EnumResourceItems.SILVER_DUST.getItemIngot()), 2, new ItemStack(EnumResourceItems.LEAD_DUST.getItemIngot()), 1, 10);
        registerRecipe(new ItemStack(BlockResources.EnumResourceOres.LEAD_ORE.getBlockOres()), new ItemStack(EnumResourceItems.LEAD_DUST.getItemIngot()), 2, new ItemStack(EnumResourceItems.SILVER_DUST.getItemIngot()), 1, 10);
        registerRecipe(new ItemStack(BlockResources.EnumResourceOres.NICKEL_ORE.getBlockOres()), new ItemStack(EnumResourceItems.NICKEL_DUST.getItemIngot()), 2, new ItemStack(EnumResourceItems.PLATINUM_DUST.getItemIngot()), 1, 10);
        registerRecipe(new ItemStack(BlockResources.EnumResourceOres.PLATINUM_ORE.getBlockOres()), new ItemStack(EnumResourceItems.PLATINUM_DUST.getItemIngot()), 2, ItemStack.EMPTY, 0, 0);

        registerRecipe(new ItemStack(Blocks.COAL_ORE), new ItemStack(Items.COAL), 3, new ItemStack(EnumResourceItems.COAL_DUST.getItemIngot()), 1, 25);
        registerRecipe(new ItemStack(Blocks.LAPIS_ORE), new ItemStack(Items.LAPIS_LAZULI), 10, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.REDSTONE_ORE), new ItemStack(Items.REDSTONE), 6, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.DIAMOND_ORE), new ItemStack(Items.DIAMOND), 2, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.EMERALD_ORE), new ItemStack(Items.EMERALD), 2, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(Blocks.NETHER_QUARTZ_ORE), new ItemStack(Items.QUARTZ), 4, ItemStack.EMPTY, 0, 0);

        for (Block woolEntry : BlockTags.WOOL.values()) {
            registerRecipe(new ItemStack(woolEntry), new ItemStack(Items.STRING), 4, ItemStack.EMPTY, 0, 0);
        }

        registerRecipe(new ItemStack(EnumResourceItems.COPPER_INGOT.getItemIngot()), new ItemStack(EnumResourceItems.COPPER_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(EnumResourceItems.TIN_INGOT.getItemIngot()), new ItemStack(EnumResourceItems.TIN_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(EnumResourceItems.LEAD_INGOT.getItemIngot()), new ItemStack(EnumResourceItems.LEAD_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(EnumResourceItems.SILVER_INGOT.getItemIngot()), new ItemStack(EnumResourceItems.SILVER_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(EnumResourceItems.NICKEL_INGOT.getItemIngot()), new ItemStack(EnumResourceItems.NICKEL_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(EnumResourceItems.PLATINUM_INGOT.getItemIngot()), new ItemStack(EnumResourceItems.PLATINUM_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(EnumResourceItems.ELECTRUM_INGOT.getItemIngot()), new ItemStack(EnumResourceItems.ELECTRUM_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
        registerRecipe(new ItemStack(EnumResourceItems.INVAR_INGOT.getItemIngot()), new ItemStack(EnumResourceItems.INVAR_DUST.getItemIngot()), 1, ItemStack.EMPTY, 0, 0);
    }

    public void registerRecipe(ItemStack input, ItemStack output, int outputAmount, ItemStack randomDrop, int randomDropAmount, int percentageDrop) {
        recipesMap.put(input, new PulverizerRecipe(input, output, outputAmount, randomDrop, randomDropAmount, percentageDrop));
    }

    public PulverizerRecipe getOutputRecipe(ItemStack input) {
        for (Map.Entry<ItemStack, PulverizerRecipe> entry : this.recipesMap.entrySet()) {
            if(input.getItem() == entry.getKey().getItem()) {
                return entry.getValue();
            }
        }

        return null;
    }

    public class PulverizerRecipe {

        private ItemStack input, output, randomDrop;
        private int outputAmount, randomDropAmoumt, percentageDrop;

        public PulverizerRecipe(ItemStack input, ItemStack output, int outputAmount, ItemStack randomDrop, int randomDropAmount, int percentageDrop) {
            this.input = input;
            this.output = output;
            this.randomDrop = randomDrop;
            this.percentageDrop = percentageDrop;
            this.outputAmount = outputAmount;
            this.randomDropAmoumt = randomDropAmount;
        }

        public ItemStack getInput() {
            return input;
        }

        public ItemStack getOutput() {
            return output;
        }

        public ItemStack getRandomDrop() {
            return randomDrop;
        }

        public int getOutputAmount() {
            return outputAmount;
        }

        public int getRandomDropAmoumt() {
            return randomDropAmoumt;
        }

        public int getPercentageDrop() {
            return percentageDrop;
        }
    }
}
