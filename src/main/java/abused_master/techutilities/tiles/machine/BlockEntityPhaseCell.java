package abused_master.techutilities.tiles.machine;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityEnergy;
import abused_master.energy.EnergyStorage;
import abused_master.energy.IEnergyReceiver;
import abused_master.techutilities.utils.linker.ILinkerHandler;
import abused_master.techutilities.utils.render.hud.IHudSupport;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.List;

public class BlockEntityPhaseCell extends BlockEntityEnergy implements IHudSupport, IEnergyReceiver, ILinkerHandler {

    public EnergyStorage storage = new EnergyStorage(1000000);

    public BlockEntityPhaseCell() {
        super(ModBlockEntities.PHASE_CELL);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        storage.readFromNBT(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        storage.writeEnergyToNBT(tag);
        return tag;
    }

    @Override
    public void tick() {
        sendEnergy();
    }

    public void sendEnergy() {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.offset(direction);
            storage.sendEnergy(world, offsetPos, 1000);
            this.markDirty();
        }
    }

    @Override
    public boolean receiveEnergy(int amount) {
        return handleEnergyReceive(storage, amount);
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return storage;
    }

    @Override
    public Direction getBlockOrientation() {
        return null;
    }

    @Override
    public boolean isBlockAboveAir() {
        return world.isAir(pos.up());
    }

    @Override
    public List<String> getClientLog() {
        return Arrays.asList(new String[]{"Energy: " + storage.getEnergyStored() + " / " + storage.getEnergyCapacity() + " PE"});
    }

    @Override
    public BlockPos getBlockPos() {
        return pos;
    }

    @Override
    public void link(PlayerEntity player, CompoundTag tag) {
        if(!world.isClient) {
            if (tag.containsKey("collectorPos")) {
                tag.remove("collectorPos");
            }
            tag.put("blockPos", TagHelper.serializeBlockPos(pos));
            player.addChatMessage(new StringTextComponent("Saved block position!"), true);
        }
    }
}
