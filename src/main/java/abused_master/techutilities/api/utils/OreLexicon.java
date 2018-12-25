package abused_master.techutilities.api.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OreLexicon {

    public static Map<ItemStack, String> oreDictionaryMap = new HashMap<>();

    public static void initVanillaLexiconEntries() {
        registerOre("logWood", Blocks.ACACIA_LOG);
        registerOre("logWood", Blocks.BIRCH_LOG);
        registerOre("logWood", Blocks.DARK_OAK_LOG);
        registerOre("logWood", Blocks.JUNGLE_LOG);
        registerOre("logWood", Blocks.OAK_LOG);
        registerOre("logWood", Blocks.SPRUCE_LOG);
        registerOre("logWood", Blocks.STRIPPED_ACACIA_LOG);
        registerOre("logWood", Blocks.STRIPPED_BIRCH_LOG);
        registerOre("logWood", Blocks.STRIPPED_DARK_OAK_LOG);
        registerOre("logWood", Blocks.STRIPPED_JUNGLE_LOG);
        registerOre("logWood", Blocks.STRIPPED_OAK_LOG);
        registerOre("logWood", Blocks.STRIPPED_SPRUCE_LOG);
        registerOre("plankWood", Blocks.ACACIA_PLANKS);
        registerOre("plankWood", Blocks.BIRCH_PLANKS);
        registerOre("plankWood", Blocks.DARK_OAK_PLANKS);
        registerOre("plankWood", Blocks.JUNGLE_PLANKS);
        registerOre("plankWood", Blocks.OAK_PLANKS);
        registerOre("plankWood", Blocks.SPRUCE_PLANKS);
        registerOre("slabWood", Blocks.ACACIA_SLAB);
        registerOre("slabWood", Blocks.SANDSTONE_SLAB);
        registerOre("slabWood", Blocks.SMOOTH_QUARTZ_SLAB);
        registerOre("slabWood", Blocks.SMOOTH_RED_SANDSTONE_SLAB);
        registerOre("slabWood", Blocks.SMOOTH_SANDSTONE_SLAB);
        registerOre("slabWood", Blocks.SMOOTH_STONE_SLAB);
        registerOre("slabWood", Blocks.SPRUCE_SLAB);
        registerOre("slabWood", Blocks.STONE_BRICK_SLAB);
        registerOre("slabWood", Blocks.STONE_SLAB);
        registerOre("slabWood", Blocks.ANDESITE_SLAB);
        registerOre("slabWood", Blocks.BIRCH_SLAB);
        registerOre("slabWood", Blocks.BRICK_SLAB);
        registerOre("slabWood", Blocks.COBBLESTONE_SLAB);
        registerOre("slabWood", Blocks.DARK_OAK_SLAB);
        registerOre("slabWood", Blocks.DARK_PRISMARINE_SLAB);
        registerOre("slabWood", Blocks.DIORITE_SLAB);
        registerOre("slabWood", Blocks.END_STONE_BRICK_SLAB);
        registerOre("slabWood", Blocks.GRANITE_SLAB);
        registerOre("slabWood", Blocks.JUNGLE_SLAB);
        registerOre("slabWood", Blocks.MOSSY_COBBLESTONE_SLAB);
        registerOre("slabWood", Blocks.MOSSY_STONE_BRICK_SLAB);
        registerOre("slabWood", Blocks.SMOOTH_STONE_SLAB);
        registerOre("slabWood", Blocks.PETRIFIED_OAK_SLAB);
        registerOre("slabWood", Blocks.POLISHED_ANDESITE_SLAB);
        registerOre("slabWood", Blocks.POLISHED_DIORITE_SLAB);
        registerOre("slabWood", Blocks.POLISHED_GRANITE_SLAB);
        registerOre("slabWood", Blocks.PRISMARINE_BRICK_SLAB);
        registerOre("slabWood", Blocks.PRISMARINE_SLAB);
        registerOre("slabWood", Blocks.PURPUR_SLAB);
        registerOre("slabWood", Blocks.QUARTZ_SLAB);
        registerOre("slabWood", Blocks.RED_NETHER_BRICK_SLAB);
        registerOre("slabWood", Blocks.RED_SANDSTONE_SLAB);
        registerOre("stairWood", Blocks.OAK_STAIRS);
        registerOre("stairWood", Blocks.SPRUCE_STAIRS);
        registerOre("stairWood", Blocks.BIRCH_STAIRS);
        registerOre("stairWood", Blocks.JUNGLE_STAIRS);
        registerOre("stairWood", Blocks.ACACIA_STAIRS);
        registerOre("stairWood", Blocks.DARK_OAK_STAIRS);
        registerOre("fenceWood", Blocks.OAK_FENCE);
        registerOre("fenceWood", Blocks.SPRUCE_FENCE);
        registerOre("fenceWood", Blocks.BIRCH_FENCE);
        registerOre("fenceWood", Blocks.JUNGLE_FENCE);
        registerOre("fenceWood", Blocks.DARK_OAK_FENCE);
        registerOre("fenceWood", Blocks.ACACIA_FENCE);
        registerOre("fenceGateWood", Blocks.OAK_FENCE_GATE);
        registerOre("fenceGateWood", Blocks.SPRUCE_FENCE_GATE);
        registerOre("fenceGateWood", Blocks.BIRCH_FENCE_GATE);
        registerOre("fenceGateWood", Blocks.JUNGLE_FENCE_GATE);
        registerOre("fenceGateWood", Blocks.DARK_OAK_FENCE_GATE);
        registerOre("fenceGateWood", Blocks.ACACIA_FENCE_GATE);
        registerOre("doorWood", Blocks.DARK_OAK_DOOR);
        registerOre("doorWood", Blocks.ACACIA_DOOR);
        registerOre("doorWood", Blocks.BIRCH_DOOR);
        registerOre("doorWood", Blocks.IRON_DOOR);
        registerOre("doorWood", Blocks.JUNGLE_DOOR);
        registerOre("doorWood", Blocks.OAK_DOOR);
        registerOre("doorWood", Blocks.SPRUCE_DOOR);
        registerOre("doorWood", Blocks.DARK_OAK_TRAPDOOR);
        registerOre("doorWood", Blocks.ACACIA_TRAPDOOR);
        registerOre("stickWood", Items.STICK);
        registerOre("treeSapling", Blocks.SPRUCE_SAPLING);
        registerOre("treeSapling", Blocks.ACACIA_SAPLING);
        registerOre("treeSapling", Blocks.BIRCH_SAPLING);
        registerOre("treeSapling", Blocks.DARK_OAK_SAPLING);
        registerOre("treeSapling", Blocks.JUNGLE_SAPLING);
        registerOre("treeSapling", Blocks.OAK_SAPLING);
        registerOre("treeLeaves", Blocks.ACACIA_LEAVES);
        registerOre("treeLeaves", Blocks.BIRCH_LEAVES);
        registerOre("treeLeaves", Blocks.DARK_OAK_LEAVES);
        registerOre("treeLeaves", Blocks.JUNGLE_LEAVES);
        registerOre("treeLeaves", Blocks.OAK_LEAVES);
        registerOre("treeLeaves", Blocks.SPRUCE_LEAVES);
        registerOre("vine", Blocks.VINE);

        // EnumResourceOres
        registerOre("oreGold",Blocks.GOLD_ORE);
        registerOre("oreIron", Blocks.IRON_ORE);
        registerOre("oreLapis", Blocks.LAPIS_ORE);
        registerOre("oreDiamond", Blocks.DIAMOND_ORE);
        registerOre("oreRedstone", Blocks.REDSTONE_ORE);
        registerOre("oreEmerald", Blocks.EMERALD_ORE);
        registerOre("oreQuartz", Blocks.NETHER_QUARTZ_ORE);
        registerOre("oreCoal", Blocks.COAL_ORE);

        // ingots/nuggets
        registerOre("ingotIron", Items.IRON_INGOT);
        registerOre("ingotGold", Items.GOLD_INGOT);
        registerOre("ingotBrick", Items.BRICK);
        registerOre("ingotBrickNether", Items.NETHER_BRICK);
        registerOre("nuggetGold", Items.GOLD_NUGGET);
        registerOre("nuggetIron", Items.IRON_NUGGET);

        // gems and dusts
        registerOre("gemDiamond", Items.DIAMOND);
        registerOre("gemEmerald", Items.EMERALD);
        registerOre("gemQuartz", Items.QUARTZ);
        registerOre("gemPrismarine", Items.PRISMARINE_SHARD);
        registerOre("dustPrismarine", Items.PRISMARINE_CRYSTALS);
        registerOre("dustRedstone", Items.REDSTONE);
        registerOre("dustGlowstone", Items.GLOWSTONE_DUST);
        registerOre("gemLapis", Items.LAPIS_LAZULI);

        // storage blocks
        registerOre("blockGold", Blocks.GOLD_BLOCK);
        registerOre("blockIron", Blocks.IRON_BLOCK);
        registerOre("blockLapis", Blocks.LAPIS_BLOCK);
        registerOre("blockDiamond", Blocks.DIAMOND_BLOCK);
        registerOre("blockRedstone", Blocks.REDSTONE_BLOCK);
        registerOre("blockEmerald", Blocks.EMERALD_BLOCK);
        registerOre("blockQuartz", Blocks.QUARTZ_BLOCK);
        registerOre("blockCoal", Blocks.COAL_BLOCK);

        // crops
        registerOre("cropWheat", Items.WHEAT);
        registerOre("cropPotato", Items.POTATO);
        registerOre("cropCarrot", Items.CARROT);
        registerOre("cropNetherWart", Items.NETHER_WART);
        registerOre("sugarcane", Blocks.SUGAR_CANE);
        registerOre("blockCactus", Blocks.CACTUS);

        // misc materials
        registerOre("dye", Items.BLACK_DYE);
        registerOre("dye", Items.BLUE_DYE);
        registerOre("dye", Items.BROWN_DYE);
        registerOre("dye", Items.CYAN_DYE);
        registerOre("dye", Items.GRAY_DYE);
        registerOre("dye", Items.GREEN_DYE);
        registerOre("dye", Items.LIGHT_BLUE_DYE);
        registerOre("dye", Items.LIGHT_GRAY_DYE);
        registerOre("dye", Items.LIME_DYE);
        registerOre("dye", Items.MAGENTA_DYE);
        registerOre("dye", Items.ORANGE_DYE);
        registerOre("dye", Items.PINK_DYE);
        registerOre("dye", Items.PURPLE_DYE);
        registerOre("dye", Items.RED_DYE);
        registerOre("dye", Items.WHITE_DYE);
        registerOre("dye", Items.YELLOW_DYE);
        registerOre("paper", new ItemStack(Items.PAPER));

        // mob drops
        registerOre("slimeball", Items.SLIME_BALL);
        registerOre("enderpearl", Items.ENDER_PEARL);
        registerOre("bone", Items.BONE);
        registerOre("gunpowder", Items.GUNPOWDER);
        registerOre("string", Items.STRING);
        registerOre("netherStar", Items.NETHER_STAR);
        registerOre("leather", Items.LEATHER);
        registerOre("feather", Items.FEATHER);
        registerOre("egg", Items.EGG);

        // records
        registerOre("record", Items.MUSIC_DISC_BLOCKS);
        registerOre("record", Items.MUSIC_DISC_CAT);
        registerOre("record", Items.MUSIC_DISC_13);
        registerOre("record", Items.MUSIC_DISC_11);
        registerOre("record", Items.MUSIC_DISC_CHIRP);
        registerOre("record", Items.MUSIC_DISC_FAR);
        registerOre("record", Items.MUSIC_DISC_MALL);
        registerOre("record", Items.MUSIC_DISC_MELLOHI);
        registerOre("record", Items.MUSIC_DISC_STAL);
        registerOre("record", Items.MUSIC_DISC_STRAD);
        registerOre("record", Items.MUSIC_DISC_WAIT);
        registerOre("record", Items.MUSIC_DISC_WARD);

        // blocks
        registerOre("dirt", Blocks.DIRT);
        registerOre("grass", Blocks.GRASS);
        registerOre("stone", Blocks.STONE);
        registerOre("cobblestone", Blocks.COBBLESTONE);
        registerOre("gravel", Blocks.GRAVEL);
        registerOre("sand", Blocks.SAND);
        registerOre("sand", Blocks.RED_SAND);
        registerOre("sandstone", Blocks.SANDSTONE);
        registerOre("sandstone", Blocks.SMOOTH_RED_SANDSTONE);
        registerOre("sandstone", Blocks.SMOOTH_SANDSTONE);
        registerOre("sandstone", Blocks.CHISELED_RED_SANDSTONE);
        registerOre("sandstone", Blocks.CHISELED_SANDSTONE);
        registerOre("sandstone", Blocks.CUT_RED_SANDSTONE);
        registerOre("sandstone", Blocks.RED_SANDSTONE);
        registerOre("netherrack", Blocks.NETHERRACK);
        registerOre("obsidian", Blocks.OBSIDIAN);
        registerOre("glowstone", Blocks.GLOWSTONE);
        registerOre("endstone", Blocks.END_STONE);
        registerOre("torch", Blocks.TORCH);
        registerOre("workbench", Blocks.CRAFTING_TABLE);
        registerOre("blockSlime", Blocks.SLIME_BLOCK);
        registerOre("blockPrismarine", Blocks.PRISMARINE);
        registerOre("blockPrismarine", Blocks.DARK_PRISMARINE);
        registerOre("blockPrismarineBrick", Blocks.PRISMARINE_BRICKS);
        registerOre("stoneGranite", Blocks.GRANITE);
        registerOre("stoneGranitePolished", Blocks.POLISHED_GRANITE);
        registerOre("stoneDiorite", Blocks.DIORITE);
        registerOre("stoneDioritePolished", Blocks.POLISHED_DIORITE);
        registerOre("stoneAndesite", Blocks.ANDESITE);
        registerOre("stoneAndesitePolished", Blocks.POLISHED_ANDESITE);
        registerOre("blockGlassColorless", Blocks.GLASS);
        registerOre("blockGlass", Blocks.GLASS);
        registerOre("blockGlass", Blocks.GRAY_STAINED_GLASS);
        registerOre("blockGlass", Blocks.GREEN_STAINED_GLASS);
        registerOre("blockGlass", Blocks.BLACK_STAINED_GLASS);
        registerOre("blockGlass", Blocks.BLUE_STAINED_GLASS);
        registerOre("blockGlass", Blocks.BROWN_STAINED_GLASS);
        registerOre("blockGlass", Blocks.CYAN_STAINED_GLASS);
        registerOre("blockGlass", Blocks.LIGHT_BLUE_STAINED_GLASS);
        registerOre("blockGlass", Blocks.LIGHT_GRAY_STAINED_GLASS);
        registerOre("blockGlass", Blocks.LIME_STAINED_GLASS);
        registerOre("blockGlass", Blocks.MAGENTA_STAINED_GLASS);
        registerOre("blockGlass", Blocks.ORANGE_STAINED_GLASS);
        registerOre("blockGlass", Blocks.PINK_STAINED_GLASS);
        registerOre("blockGlass", Blocks.PURPLE_STAINED_GLASS);
        registerOre("blockGlass", Blocks.RED_STAINED_GLASS);
        registerOre("blockGlass", Blocks.WHITE_STAINED_GLASS_PANE);
        registerOre("blockGlass", Blocks.YELLOW_STAINED_GLASS);

        //blockGlass{Color} is added below with dyes
        registerOre("paneGlassColorless", Blocks.GLASS_PANE);
        registerOre("paneGlass", Blocks.GLASS_PANE);
        registerOre("paneGlass", Blocks.WHITE_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.PINK_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.PURPLE_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.BLACK_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.BLUE_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.BROWN_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.CYAN_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.GRAY_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.GREEN_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.LIME_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.MAGENTA_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.ORANGE_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.RED_STAINED_GLASS_PANE);
        registerOre("paneGlass", Blocks.YELLOW_STAINED_GLASS_PANE);
        //paneGlass{Color} is added below with dyes

        // chests
        registerOre("chest", Blocks.CHEST);
        registerOre("chest", Blocks.ENDER_CHEST);
        registerOre("chest", Blocks.TRAPPED_CHEST);
        registerOre("chestWood", Blocks.CHEST);
        registerOre("chestEnder", Blocks.ENDER_CHEST);
        registerOre("chestTrapped", Blocks.TRAPPED_CHEST);
    }

    public static void registerOre(String name, Block ore) { registerOre(name, new ItemStack(ore)); }

    public static void registerOre(String name, Item ore) { registerOre(name, new ItemStack(ore)); }

    public static void registerOre(String name, ItemStack ore) {
        if(ore.isEmpty()) {
            throw new EmptyItemException("Input itemstack for OreLexicon registration cannot be empty! Cause: " + name);
        }

        oreDictionaryMap.put(ore, name);
    }

    public static boolean doesOreRegistryExist(String name) {
        return !getOresFromName(name).isEmpty();
    }

    public static List<ItemStack> getOresFromName(String name) {
        List<ItemStack> availableOres = new ArrayList<>();
        for (ItemStack oreDictItemStack: oreDictionaryMap.keySet()) {
            if (oreDictionaryMap.get(oreDictItemStack).equals(name)) {
                availableOres.add(oreDictItemStack);
            }
        }

        return availableOres;
    }

    public static ItemStack getOreFromName(String name) {
        return getOresFromName(name).get(0);
    }
}
