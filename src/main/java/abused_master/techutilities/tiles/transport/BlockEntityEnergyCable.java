package abused_master.techutilities.tiles.transport;

import abused_master.abusedlib.tiles.BlockEntityBase;
import abused_master.techutilities.registry.ModBlockEntities;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityEnergyCable extends BlockEntityBase {

    public List<BlockPos> connections = new ArrayList<>();
    public List<BlockPos> powerSources = new ArrayList<>();

    public BlockEntityEnergyCable() {
        super(ModBlockEntities.ENERGY_CABLE);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);

        if(tag.containsKey("connections")) {
            this.connections.clear();
            ListTag connectionsList = tag.getList("connections", NbtType.COMPOUND);

            for (Tag tag1 : connectionsList) {
                this.connections.add(TagHelper.deserializeBlockPos((CompoundTag) tag1));
            }
        }

        if(tag.containsKey("powerSources")) {
            this.powerSources.clear();
            ListTag connectionsList = tag.getList("powerSources", NbtType.COMPOUND);

            for (Tag tag1 : connectionsList) {
                this.powerSources.add(TagHelper.deserializeBlockPos((CompoundTag) tag1));
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        if(!connections.isEmpty()) {
            ListTag connectionsList = new ListTag();
            for (BlockPos conn : connections) {
                connectionsList.add(TagHelper.serializeBlockPos(conn));
            }

            tag.put("connections", connectionsList);
        }

        if(!powerSources.isEmpty()) {
            ListTag powerSourcesList = new ListTag();
            for (BlockPos power : powerSources) {
                powerSourcesList.add(TagHelper.serializeBlockPos(power));
            }

            tag.put("powerSources", powerSourcesList);
        }


        return tag;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void addConnection(BlockPos connection) {
        this.connections.add(connection);
        this.updateEntity();
        this.updateNeighborCables();
    }

    public void addPowerSource(BlockPos source) {
        this.powerSources.add(source);
        this.updateEntity();
        this.updateNeighborCables();
    }

    public void removeConnection(BlockPos connection) {
        this.connections.remove(connection);
        this.updateEntity();
    }

    public void removePowerSource(BlockPos source) {
        this.powerSources.remove(source);
        this.updateEntity();
    }

    public void updateNeighborCables() {
        for (Direction direction : Direction.values()) {
            BlockEntity blockEntity = world.getBlockEntity(pos.offset(direction));

            if(blockEntity instanceof BlockEntityEnergyCable) {
                ((BlockEntityEnergyCable) blockEntity).connections.addAll(this.connections);
                ((BlockEntityEnergyCable) blockEntity).powerSources.addAll(this.powerSources);
            }
        }
    }
}
