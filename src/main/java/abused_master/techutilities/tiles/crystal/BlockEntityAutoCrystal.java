package abused_master.techutilities.tiles.crystal;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityBase;
import abused_master.techutilities.utils.energy.EnergyStorage;
import abused_master.techutilities.utils.energy.IEnergyProvider;
import abused_master.techutilities.utils.energy.IEnergyReceiver;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Iterator;

public class BlockEntityAutoCrystal extends BlockEntityBase implements IEnergyProvider, IEnergyReceiver {

    public EnergyStorage storage = new EnergyStorage(50000);
    public HashSet<BlockPos> tilePositions = new HashSet<>();
    public int areaLookupTimer = 0;
    public int sendPerTick = 250;
    public boolean loopingThroughTiles = false;

    public BlockEntityAutoCrystal() {
        super(ModBlockEntities.AUTO_ENERGY_CRYSTAL);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        storage.readFromNBT(nbt);

        if(nbt.containsKey("tilePositions")) {
            tilePositions.clear();
            ListTag tags = nbt.getList("tilePositions", NbtType.COMPOUND);
            for (Tag tag : tags) {
                tilePositions.add(TagHelper.deserializeBlockPos((CompoundTag) tag));
            }
        }

        this.areaLookupTimer = nbt.getInt("areaLookupTimer");
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

        nbt.putInt("areaLookupTimer", this.areaLookupTimer);
        return nbt;
    }


    @Override
    public void tick() {
        areaLookupTimer++;
        if(areaLookupTimer >= 500) {
            loopingThroughTiles = true;
            lookForBlockEntities();
            areaLookupTimer = 0;
            loopingThroughTiles = false;
        }

        if(storage.getEnergyStored() >= sendPerTick && !loopingThroughTiles && tilePositions.size() > 0) {
            sendEnergy();
        }
    }

    public void sendEnergy() {
        for (Iterator<BlockPos> it = tilePositions.iterator(); it.hasNext();) {
            BlockPos blockPos = it.next();
            if(blockPos == null || !(world.getBlockEntity(blockPos) instanceof IEnergyReceiver)) {
                it.remove();
                continue;
            }

            sendEnergy(world, blockPos, sendPerTick);
        }
    }

    public void lookForBlockEntities() {
        Iterable<BlockPos> posIterable = BlockPos.iterateBoxPositions(new BlockPos(pos.getX() - 12, pos.getY() - 12, pos.getZ() - 12), new BlockPos(pos.getX() + 12, pos.getY() + 12, pos.getZ() + 12));
        for (BlockPos pos1 : posIterable) {
            BlockEntity blockEntity = world.getBlockEntity(pos1);
            if(world.isAir(pos1) || blockEntity == null || blockEntity instanceof BlockEntityEnergyCollector || blockEntity instanceof BlockEntityEnergyCrystal) {
                continue;
            }

            if(blockEntity instanceof IEnergyReceiver) {
                if(!tilePositions.contains(pos1)) {
                    tilePositions.add(pos1);
                }
            }
        }
    }

    @Override
    public boolean sendEnergy(World world, BlockPos pos, int amount) {
        boolean sent = storage.sendEnergy(world, pos, amount);
        this.markDirty();
        return sent;
    }

    @Override
    public boolean receiveEnergy(int amount) {
        if(canReceive(amount)) {
            storage.recieveEnergy(amount);
            this.markDirty();
            world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
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
}
