package abused_master.techutilities.tiles.crystal;

import abused_master.abusedlib.tiles.BlockEntityBase;
import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import nerdhub.cardinalenergy.api.IEnergyHandler;
import nerdhub.cardinalenergy.impl.EnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockEntityEnergyCollector extends BlockEntityBase implements IEnergyHandler, ILinkerHandler {

    public EnergyStorage storage = new EnergyStorage(10000);
    private BlockPos crystalPos = null;
    private int sendPerTick = 500;

    public BlockEntityEnergyCollector() {
        super(ModBlockEntities.ENERGY_CRYSTAL_COLLECTOR);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        this.storage.readEnergyFromTag(nbt);
        if(nbt.containsKey("crystalPos")) {
            this.crystalPos = TagHelper.deserializeBlockPos(nbt.getCompound("crystalPos"));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        storage.writeEnergyToTag(nbt);
        if(crystalPos != null) {
            nbt.put("crystalPos", TagHelper.serializeBlockPos(crystalPos));
        }
        return nbt;
    }

    @Override
    public void tick() {
        if (crystalPos != null && world.getBlockEntity(crystalPos) != null && world.getBlockEntity(crystalPos) instanceof BlockEntityEnergyCrystal && storage.getEnergyStored() >= sendPerTick) {
            BlockEntityEnergyCrystal energyCrystal = (BlockEntityEnergyCrystal) world.getBlockEntity(crystalPos);
            storage.extractEnergy(energyCrystal.getEnergyStorage(null).receiveEnergy(sendPerTick));
        } else if (crystalPos != null && world.getBlockEntity(crystalPos) == null) {
            crystalPos = null;
            world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }

    public void setCrystalPos(BlockPos crystalPos) {
        this.crystalPos = crystalPos;
    }

    public BlockPos getCrystalPos() {
        return crystalPos;
    }

    public boolean canReceive(int amount) {
        return (storage.getEnergyCapacity() - storage.getEnergyStored()) >= amount;
    }

    @Override
    public EnergyStorage getEnergyStorage(Direction direction) {
        return storage;
    }

    @Override
    public void link(PlayerEntity player, CompoundTag tag) {
        if(!world.isClient) {
            if (tag.containsKey("blockPos")) {
                tag.remove("blockPos");
            }
            tag.put("collectorPos", TagHelper.serializeBlockPos(pos));
            player.addChatMessage(new StringTextComponent("Saved collector position!"), true);
        }
    }
}
