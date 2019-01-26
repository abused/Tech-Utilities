package abused_master.techutilities;

import abused_master.techutilities.utils.Config;
import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class TechUtilities implements ModInitializer, ClientModInitializer {

    public static String MODID = "techutilities";
    public static ItemGroup modItemGroup = FabricItemGroupBuilder.build(new Identifier(MODID, "techutilities"), () -> new ItemStack(BlockResources.EnumResourceOres.COPPER_ORE.getBlockOres()));
    public static Logger LOGGER = Logger.getLogger("TechUtilities");

    public static Config config = new Config(MODID, TechUtilities.class, true);

    @Override
    public void onInitialize() {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModBlockEntities.registerBlockEntities();
        ModBlockEntities.registerGUIs();
        TechWorldGeneration.generateOres();
        ModRecipes.registerRecipes();
    }

    @Override
    public void onInitializeClient() {
        ModBlockEntities.registerClientGUIs();
    }
}
