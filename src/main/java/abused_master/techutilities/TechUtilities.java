package abused_master.techutilities;

import abused_master.techutilities.api.utils.OreLexicon;
import abused_master.techutilities.blocks.BlockResources;
import abused_master.techutilities.gui.client.GuiEnergyFurnace;
import abused_master.techutilities.gui.container.ContainerEnergyFurnace;
import abused_master.techutilities.registry.ModBlocks;
import abused_master.techutilities.registry.ModGuis;
import abused_master.techutilities.registry.ModItems;
import abused_master.techutilities.registry.ModTiles;
import abused_master.techutilities.tiles.TileEntityEnergyFurnace;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TechUtilities implements ModInitializer, ClientModInitializer {

    public static String MODID = "techutilities";
    //TODO CHANGE THIS TO A CUSTOM CREATIVE TAB
    public static ItemGroup modItemGroup = FabricItemGroupBuilder.build(new Identifier(MODID, "techutilities"), () -> new ItemStack(BlockResources.EnumResourceOres.COPPER_ORE.getBlockOres()));

    @Override
    public void onInitialize() {
        OreLexicon.initVanillaLexiconEntries();
        ModBlocks.registerBlocks(Registry.BLOCK);
        ModItems.registerItems(Registry.ITEM);
        ModTiles.registerTile(Registry.BLOCK_ENTITY);
        ContainerProviderRegistry.INSTANCE.registerFactory(ModGuis.ENERGY_FURNACE_CONTAINER, (identifier, player, buf) -> new ContainerEnergyFurnace(player.inventory, (TileEntityEnergyFurnace) player.world.getBlockEntity(buf.readBlockPos())));
    }

    @Override
    public void onInitializeClient() {
        GuiProviderRegistry.INSTANCE.registerFactory(ModGuis.ENERGY_FURNACE_CONTAINER, (identifier, player, buf) -> new GuiEnergyFurnace((TileEntityEnergyFurnace) player.world.getBlockEntity(buf.readBlockPos()), new ContainerEnergyFurnace(player.inventory, (TileEntityEnergyFurnace) player.world.getBlockEntity(buf.readBlockPos()))));
    }
}
