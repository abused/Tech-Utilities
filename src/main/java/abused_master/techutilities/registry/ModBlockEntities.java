package abused_master.techutilities.registry;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.client.gui.container.ContainerEnergyFurnace;
import abused_master.techutilities.client.gui.container.ContainerPulverizer;
import abused_master.techutilities.client.gui.gui.GuiEnergyFurnace;
import abused_master.techutilities.client.gui.gui.GuiPulverizer;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCollector;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCrystal;
import abused_master.techutilities.tiles.generator.BlockEntityLavaGenerator;
import abused_master.techutilities.tiles.generator.BlockEntitySolarPanel;
import abused_master.techutilities.tiles.machine.BlockEntityEnergyFurnace;
import abused_master.techutilities.tiles.machine.BlockEntityPulverizer;
import abused_master.techutilities.tiles.machine.BlockEntityQuarry;
import abused_master.techutilities.utils.RegistryHelper;
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModBlockEntities {

    public static BlockEntityType<BlockEntityEnergyFurnace> ENERGY_FURNACE;
    public static BlockEntityType<BlockEntityQuarry> QUARRY;
    public static BlockEntityType<BlockEntitySolarPanel> SOLAR_PANEL;
    public static BlockEntityType<BlockEntityEnergyCrystal> ENERGY_CRYSTAL;
    public static BlockEntityType<BlockEntityEnergyCollector> ENERGY_CRYSTAL_COLLECTOR;
    public static BlockEntityType<BlockEntityPulverizer> PULVERIZER;
    public static BlockEntityType<BlockEntityLavaGenerator> LAVA_GENERATOR;

    //Container Identifiers
    public static final Identifier ENERGY_FURNACE_CONTAINER = new Identifier(TechUtilities.MODID, "energy_furnace_container");
    public static final Identifier PULVERIZER_CONTAINER = new Identifier(TechUtilities.MODID, "pulverizer_container");

    public static void registerBlockEntities() {
        ENERGY_FURNACE = RegistryHelper.registerTile(TechUtilities.MODID, "energy_furnace", BlockEntityEnergyFurnace.class);
        QUARRY = RegistryHelper.registerTile(TechUtilities.MODID, "quarry", BlockEntityQuarry.class);
        SOLAR_PANEL = RegistryHelper.registerTile(TechUtilities.MODID, "solar_panel", BlockEntitySolarPanel.class);
        ENERGY_CRYSTAL = RegistryHelper.registerTile(TechUtilities.MODID, "energy_crystal", BlockEntityEnergyCrystal.class);
        ENERGY_CRYSTAL_COLLECTOR = RegistryHelper.registerTile(TechUtilities.MODID, "energy_crystal_collector", BlockEntityEnergyCollector.class);
        PULVERIZER = RegistryHelper.registerTile(TechUtilities.MODID, "pulverizer", BlockEntityPulverizer.class);
        LAVA_GENERATOR = RegistryHelper.registerTile(TechUtilities.MODID, "lava_generator", BlockEntityLavaGenerator.class);
    }

    public static void registerGUIs() {
        ContainerProviderRegistry.INSTANCE.registerFactory(ENERGY_FURNACE_CONTAINER, (syncid, identifier, player, buf) -> new ContainerEnergyFurnace(syncid, player.inventory, (BlockEntityEnergyFurnace) player.world.getBlockEntity(buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(PULVERIZER_CONTAINER, (syncid, identifier, player, buf) -> new ContainerPulverizer(syncid, player.inventory, (BlockEntityPulverizer) player.world.getBlockEntity(buf.readBlockPos())));
    }

    public static void registerClientGUIs() {
        GuiProviderRegistry.INSTANCE.registerFactory(ENERGY_FURNACE_CONTAINER, ((syncid, identifier, player, buf) -> {
            BlockPos pos = buf.readBlockPos();
            BlockEntityEnergyFurnace furnace = (BlockEntityEnergyFurnace) player.world.getBlockEntity(pos);
            return new GuiEnergyFurnace(furnace, new ContainerEnergyFurnace(syncid, player.inventory, furnace));
        }));

        GuiProviderRegistry.INSTANCE.registerFactory(PULVERIZER_CONTAINER, ((syncid, identifier, player, buf) -> {
            BlockPos pos = buf.readBlockPos();
            BlockEntityPulverizer pulverizer = (BlockEntityPulverizer) player.world.getBlockEntity(pos);
            return new GuiPulverizer(pulverizer, new ContainerPulverizer(syncid, player.inventory, pulverizer));
        }));
    }
}
