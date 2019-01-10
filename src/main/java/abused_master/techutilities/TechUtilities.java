package abused_master.techutilities;

import abused_master.abusedlib.utils.Config;
import abused_master.techutilities.utils.OreLexicon;
import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.client.gui.gui.GuiEnergyFurnace;
import abused_master.techutilities.client.gui.container.ContainerEnergyFurnace;
import abused_master.techutilities.registry.*;
import abused_master.techutilities.tiles.TileEntityEnergyFurnace;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;


public class TechUtilities implements ModInitializer, ClientModInitializer {

    public static String MODID = "techutilities";
    public static ItemGroup modItemGroup = FabricItemGroupBuilder.build(new Identifier(MODID, "techutilities"), () -> new ItemStack(BlockResources.EnumResourceOres.COPPER_ORE.getBlockOres()));

    public static Config config = new Config(MODID, TechUtilities.class, true);

    @Override
    public void onInitialize() {
        OreLexicon.initVanillaLexiconEntries();
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModTiles.registerTile();
        ContainerProviderRegistry.INSTANCE.registerFactory(ModGuis.ENERGY_FURNACE_CONTAINER, (identifier, player, buf) -> new ContainerEnergyFurnace(player.inventory, (TileEntityEnergyFurnace) player.world.getBlockEntity(buf.readBlockPos())));
        TechWorldGeneration.generateOres();
    }

    @Override
    public void onInitializeClient() {
        GuiProviderRegistry.INSTANCE.registerFactory(ModGuis.ENERGY_FURNACE_CONTAINER, ((identifier, player, buf) -> {
            BlockPos pos = buf.readBlockPos();
            TileEntityEnergyFurnace furnace = (TileEntityEnergyFurnace) player.world.getBlockEntity(pos);
            return new GuiEnergyFurnace(furnace, new ContainerEnergyFurnace(player.inventory, furnace));
        }));
    }
}
