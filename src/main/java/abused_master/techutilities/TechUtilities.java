package abused_master.techutilities;

import abused_master.techutilities.tiles.BlockEntityEnergyFurnace;
import abused_master.techutilities.utils.Config;
import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.client.gui.gui.GuiEnergyFurnace;
import abused_master.techutilities.client.gui.container.ContainerEnergyFurnace;
import abused_master.techutilities.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

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
        ContainerProviderRegistry.INSTANCE.registerFactory(ModBlockEntities.ENERGY_FURNACE_CONTAINER, (syncid, identifier, player, buf) -> new ContainerEnergyFurnace(syncid, player.inventory, (BlockEntityEnergyFurnace) player.world.getBlockEntity(buf.readBlockPos())));
        TechWorldGeneration.generateOres();
    }

    @Override
    public void onInitializeClient() {
        GuiProviderRegistry.INSTANCE.registerFactory(ModBlockEntities.ENERGY_FURNACE_CONTAINER, ((syncid, identifier, player, buf) -> {
            BlockPos pos = buf.readBlockPos();
            BlockEntityEnergyFurnace furnace = (BlockEntityEnergyFurnace) player.world.getBlockEntity(pos);
            return new GuiEnergyFurnace(furnace, new ContainerEnergyFurnace(syncid, player.inventory, furnace));
        }));
    }
}
