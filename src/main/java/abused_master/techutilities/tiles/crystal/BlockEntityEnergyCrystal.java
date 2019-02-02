package abused_master.techutilities.tiles.crystal;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityEnergy;
import abused_master.energy.EnergyStorage;
import abused_master.energy.IEnergyProvider;
import abused_master.energy.IEnergyReceiver;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Iterator;

public class BlockEntityEnergyCrystal extends BlockEntityEnergy implements IEnergyReceiver, IEnergyProvider, ILinkerHandler {

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
            ListTag tags = nbt.getList("tilePositions", NbtType.COMPOUND);
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
            if(blockPos == null || !(world.getBlockEntity(blockPos) instanceof IEnergyReceiver)) {
                it.remove();
                this.markDirty();
                world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                continue;
            }

            sendEnergy(world, blockPos, sendPerTick);
        }
    }

    @Override
    public boolean receiveEnergy(int amount) {
        return this.handleEnergyReceive(storage, amount);
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public boolean sendEnergy(World world, BlockPos pos, int amount) {
        boolean sent = storage.sendEnergy(world, pos, amount);
        markDirty();
        return sent;
    }

    @Override
    public void link(PlayerEntity player, CompoundTag tag) {
        if(!world.isClient) {
            if (tag.containsKey("collectorPos")) {
                BlockPos collectorPos = TagHelper.deserializeBlockPos(tag.getCompound("collectorPos"));
                BlockEntityEnergyCollector energyCollector = (BlockEntityEnergyCollector) world.getBlockEntity(collectorPos);
                if (energyCollector != null) {
                    energyCollector.setCrystalPos(pos);
                    energyCollector.markDirty();
                    world.updateListeners(collectorPos, world.getBlockState(collectorPos), world.getBlockState(collectorPos), 3);
                    player.addChatMessage(new StringTextComponent("Linked collector position!"), true);
                } else {
                    player.addChatMessage(new StringTextComponent("Invalid collector position!"), true);
                }
            } else if (tag.containsKey("blockPos")) {
                BlockPos blockPos = TagHelper.deserializeBlockPos(tag.getCompound("blockPos"));
                if (world.getBlockEntity(blockPos) != null && world.getBlockEntity(blockPos) instanceof IEnergyReceiver && !tilePositions.contains(blockPos)) {
                    tilePositions.add(blockPos);
                    markDirty();
                    world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                    player.addChatMessage(new StringTextComponent("Linked BlockEntity position!"), true);
                }
            } else {
                player.addChatMessage(new StringTextComponent("No block position has been linked!"), true);
            }
        }
    }
}
