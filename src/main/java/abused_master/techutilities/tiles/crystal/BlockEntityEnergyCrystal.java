package abused_master.techutilities.tiles.crystal;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityBase;
import abused_master.techutilities.utils.energy.EnergyStorage;
import abused_master.techutilities.utils.energy.IEnergyProvider;
import abused_master.techutilities.utils.energy.IEnergyReceiver;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Iterator;

public class BlockEntityEnergyCrystal extends BlockEntityBase implements IEnergyReceiver, IEnergyProvider {

    public EnergyStorage storage = new EnergyStorage(100000);
    public final HashSet<BlockPos> tilePositions = new HashSet<>();
    public int sendPerTick = 250;

    public BlockEntityEnergyCrystal() {
        super(ModBlockEntities.ENERGY_CRYSTAL);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        storage.readFromNBT(nbt);

        if(nbt.containsKey("tilePositions")) {
            tilePositions.clear();
            ListTag tags = nbt.getList("tilePositions", 9);
            for (Tag tag : tags) {
                tilePositions.add(TagHelper.deserializeBlockPos((CompoundTag) tag));
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        storage.writeEnergyToNBT(nbt);

        if(tilePositions.size() > 0) {
            ListTag tags = new ListTag();
            for (BlockPos pos1 : tilePositions) {
                tags.add(TagHelper.serializeBlockPos(pos1));
            }

            nbt.put("tilePositions", tags);
        }

        return nbt;
    }

    @Override
    public void tick() {
        if(storage.getEnergyStored() >= sendPerTick && tilePositions.size() > 0) {
            sendEnergy();
        }
    }

    public void sendEnergy() {
        for (Iterator<BlockPos> it = tilePositions.iterator(); it.hasNext();) {
            BlockPos blockPos = it.next();
            if(blockPos == null || world.getBlockState(blockPos) == null || !(world.getBlockState(blockPos) instanceof IEnergyReceiver)) {
                tilePositions.remove(blockPos);
                return;
            }

            sendEnergy(world, blockPos, sendPerTick);
        }
    }

    @Override
    public boolean receiveEnergy(int amount) {
        if(canReceive(amount)) {
            storage.recieveEnergy(amount);
            return true;
        }

        return false;
    }

    public boolean canReceive(int amount) {
        return (storage.getEnergyCapacity() - storage.getEnergyStored()) >= amount;
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public boolean sendEnergy(World world, BlockPos pos, int amount) {
        return storage.sendEnergy(world, pos, amount);
    }
}
