package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class TechUtilitiesTags {

    //Item Tags
    public static final Tag<Item> COPPER_INGOT = TagRegistry.item(new Identifier(TechUtilities.MODID, "copper_ingot"));
    public static final Tag<Item> TIN_INGOT = TagRegistry.item(new Identifier(TechUtilities.MODID, "tin_ingot"));
    public static final Tag<Item> LEAD_INGOT = TagRegistry.item(new Identifier(TechUtilities.MODID, "lead_ingot"));
    public static final Tag<Item> SILVER_INGOT = TagRegistry.item(new Identifier(TechUtilities.MODID, "silver_ingot"));
    public static final Tag<Item> NICKEL_INGOT = TagRegistry.item(new Identifier(TechUtilities.MODID, "nickel_ingot"));

    public static final Tag<Item> COAL_DUST = TagRegistry.item(new Identifier(TechUtilities.MODID, "coal_dust"));
    public static final Tag<Item> DIAMOND_DUST = TagRegistry.item(new Identifier(TechUtilities.MODID, "diamond_dust"));
    public static final Tag<Item> IRON_DUST = TagRegistry.item(new Identifier(TechUtilities.MODID, "iron_dust"));
    public static final Tag<Item> GOLD_DUST = TagRegistry.item(new Identifier(TechUtilities.MODID, "gold_dust"));
    public static final Tag<Item> COPPER_DUST = TagRegistry.item(new Identifier(TechUtilities.MODID, "copper_dust"));
    public static final Tag<Item> TIN_DUST = TagRegistry.item(new Identifier(TechUtilities.MODID, "tin_dust"));
    public static final Tag<Item> LEAD_DUST = TagRegistry.item(new Identifier(TechUtilities.MODID, "lead_dust"));
    public static final Tag<Item> SILVER_DUST = TagRegistry.item(new Identifier(TechUtilities.MODID, "silver_dust"));
    public static final Tag<Item> NICKEL_DUST = TagRegistry.item(new Identifier(TechUtilities.MODID, "nickel_dust"));

    //Block Tags
    public static final Tag<Block> COPPER_BLOCK = TagRegistry.block(new Identifier(TechUtilities.MODID, "copper_block"));
    public static final Tag<Block> TIN_BLOCK = TagRegistry.block(new Identifier(TechUtilities.MODID, "tin_block"));
    public static final Tag<Block> LEAD_BLOCK = TagRegistry.block(new Identifier(TechUtilities.MODID, "lead_block"));
    public static final Tag<Block> SILVER_BLOCK = TagRegistry.block(new Identifier(TechUtilities.MODID, "silver_block"));
    public static final Tag<Block> NICKEL_BLOCK = TagRegistry.block(new Identifier(TechUtilities.MODID, "nickel_block"));

    public static final Tag<Block> COPPER_ORE = TagRegistry.block(new Identifier(TechUtilities.MODID, "copper_ore"));
    public static final Tag<Block> TIN_ORE = TagRegistry.block(new Identifier(TechUtilities.MODID, "tin_ore"));
    public static final Tag<Block> LEAD_ORE = TagRegistry.block(new Identifier(TechUtilities.MODID, "lead_ore"));
    public static final Tag<Block> SILVER_ORE = TagRegistry.block(new Identifier(TechUtilities.MODID, "silver_ore"));
    public static final Tag<Block> NICKEL_ORE = TagRegistry.block(new Identifier(TechUtilities.MODID, "nickel_ore"));
}
