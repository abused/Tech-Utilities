package abused_master.techutilities.registry;

import abused_master.abusedlib.registry.RegistryHelper;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.client.gui.container.*;
import abused_master.techutilities.client.gui.gui.*;
import abused_master.techutilities.client.render.*;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCollector;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCrystal;
import abused_master.techutilities.tiles.crystal.BlockEntityItemReceiver;
import abused_master.techutilities.tiles.crystal.BlockEntityItemTransfer;
import abused_master.techutilities.tiles.generator.BlockEntityLavaGenerator;
import abused_master.techutilities.tiles.generator.BlockEntitySolarPanel;
import abused_master.techutilities.tiles.machine.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
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
    public static BlockEntityType<BlockEntityEnergyCharger> ENERGY_CHARGER;
    public static BlockEntityType<BlockEntityFluidPump> FLUID_PUMP;
    public static BlockEntityType<BlockEntityFarmer> FARMER;
    public static BlockEntityType<BlockEntityMobGrinder> MOB_GRINDER;
    public static BlockEntityType<BlockEntityVacuum> VACUUM;
    public static BlockEntityType<BlockEntityItemReceiver> ITEM_RECEIVER;
    public static BlockEntityType<BlockEntityItemTransfer> ITEM_TRANSFER;
    public static BlockEntityType<BlockEntityPhaseCell> PHASE_CELL;

    //Container Identifiers
    public static final Identifier ENERGY_FURNACE_CONTAINER = new Identifier(TechUtilities.MODID, "energy_furnace_container");
    public static final Identifier PULVERIZER_CONTAINER = new Identifier(TechUtilities.MODID, "pulverizer_container");
    public static final Identifier ENERGY_CHARGER_CONTAINER = new Identifier(TechUtilities.MODID, "energy_charger_container");
    public static final Identifier FARMER_CONTAINER = new Identifier(TechUtilities.MODID, "farmer_container");
    public static final Identifier VACUUM_CONTAINER = new Identifier(TechUtilities.MODID, "vacuum_container");

    public static void registerBlockEntities() {
        ENERGY_FURNACE = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "energy_furnace"), BlockEntityEnergyFurnace.class);
        QUARRY = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "quarry"), BlockEntityQuarry.class);
        SOLAR_PANEL = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "solar_panel"), BlockEntitySolarPanel.class);
        ENERGY_CRYSTAL = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "energy_crystal"), BlockEntityEnergyCrystal.class);
        ENERGY_CRYSTAL_COLLECTOR = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "energy_crystal_collector"), BlockEntityEnergyCollector.class);
        PULVERIZER = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "pulverizer"), BlockEntityPulverizer.class);
        LAVA_GENERATOR = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "lava_generator"), BlockEntityLavaGenerator.class);
        ENERGY_CHARGER = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "energy_charger"), BlockEntityEnergyCharger.class);
        FLUID_PUMP = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "fluid_pump"), BlockEntityFluidPump.class);
        FARMER = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "farmer"), BlockEntityFarmer.class);
        MOB_GRINDER = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "mob_grinder"), BlockEntityMobGrinder.class);
        VACUUM = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "vacuum"), BlockEntityVacuum.class);
        ITEM_RECEIVER = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "item_receiver"), BlockEntityItemReceiver.class);
        ITEM_TRANSFER = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "item_transfer"), BlockEntityItemTransfer.class);
        PHASE_CELL = RegistryHelper.registerTile(new Identifier(TechUtilities.MODID, "phase_cell"), BlockEntityPhaseCell.class);
    }

    @Environment(EnvType.CLIENT)
    public static void registerEntityRenders() {
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityQuarry.class, new QuarryRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityEnergyCollector.class, new CrystalCollectorRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityLavaGenerator.class, new LavaGeneratorRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityFluidPump.class, new FluidPumpRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityMobGrinder.class, new MobGrinderRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityItemTransfer.class, new ItemTransferRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityPhaseCell.class, new PhaseCellRenderer());
    }

    public static void registerServerGUIs() {
        ContainerProviderRegistry.INSTANCE.registerFactory(ENERGY_FURNACE_CONTAINER, (syncid, identifier, player, buf) -> new ContainerEnergyFurnace(syncid, player.inventory, (BlockEntityEnergyFurnace) player.world.getBlockEntity(buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(PULVERIZER_CONTAINER, (syncid, identifier, player, buf) -> new ContainerPulverizer(syncid, player.inventory, (BlockEntityPulverizer) player.world.getBlockEntity(buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(ENERGY_CHARGER_CONTAINER, (syncid, identifier, player, buf) -> new ContainerEnergyCharger(syncid, player.inventory, (BlockEntityEnergyCharger) player.world.getBlockEntity(buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(FARMER_CONTAINER, (syncid, identifier, player, buf) -> new ContainerFarmer(syncid, player.inventory, (BlockEntityFarmer) player.world.getBlockEntity(buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(VACUUM_CONTAINER, (syncid, identifier, player, buf) -> new ContainerVacuum(syncid, player.inventory, (BlockEntityVacuum) player.world.getBlockEntity(buf.readBlockPos())));
    }
}
