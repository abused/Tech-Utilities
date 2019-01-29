package abused_master.techutilities.tiles.generator;

import abused_master.techutilities.registry.ModBlockEntities;
import abused_master.techutilities.tiles.BlockEntityEnergy;
import abused_master.techutilities.utils.energy.EnergyStorage;
import abused_master.techutilities.utils.energy.IEnergyProvider;
import abused_master.techutilities.utils.fluid.FluidPack;
import abused_master.techutilities.utils.fluid.FluidTank;
import abused_master.techutilities.utils.fluid.IFluidHandler;
import abused_master.techutilities.utils.render.hud.IHudSupport;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityLavaGenerator extends BlockEntityEnergy implements IEnergyProvider, IHudSupport, IFluidHandler {

    public EnergyStorage storage = new EnergyStorage(100000);
    public int sendPerTick = 500;
    public int generatePer10 = 100;
    public FluidTank tank = new FluidTank(8000) {
        @Override
        public void onContentsChanged() {
            BlockEntityLavaGenerator blockEntity = BlockEntityLavaGenerator.this;
            BlockState state = blockEntity.world.getBlockState(blockEntity.pos);
            blockEntity.world.updateListeners(blockEntity.pos, state, state, 8);
            blockEntity.markDirty();
        }
    };

    public BlockEntityLavaGenerator() {
        super(ModBlockEntities.LAVA_GENERATOR);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        storage.readFromNBT(nbt);

        if (nbt.containsKey("FluidData")) {
            this.tank.setFluidPack(FluidPack.loadFluidFromTag(nbt.getCompound("FluidData")));
        }

        if (this.tank != null && this.tank.getFluidPack() != null) {
            this.tank.readFromNBT(nbt);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        storage.writeEnergyToNBT(nbt);

        if (this.tank != null && this.tank.getFluidPack() != null) {
            CompoundTag tankTag = new CompoundTag();
            this.tank.getFluidPack().writeToTag(tankTag);
            nbt.put("FluidData", tankTag);
            this.tank.writeToNBT(nbt);
        }
        return nbt;
    }

    @Override
    public void tick() {
        if (tank.getFluidAmount() >= 10 && tank.getFluidPack().getFluid() instanceof LavaFluid && (storage.getEnergyStored() + generatePer10) <= storage.getEnergyCapacity()) {
            if(!world.isReceivingRedstonePower(pos)) {
                storage.recieveEnergy(generatePer10);
                tank.extractFluid(10);
                this.markDirty();
            }
        }

        sendEnergy();
    }

    public void sendEnergy() {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.offset(direction);
            storage.sendEnergy(world, offsetPos, sendPerTick);
            this.markDirty();
        }
    }

    @Override
    public boolean sendEnergy(World world, BlockPos pos, int amount) {
        boolean sent = storage.sendEnergy(world, pos, amount);
        markDirty();
        return sent;
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
        return getWorld().isAir(pos.up());
    }

    @Override
    public List<String> getClientLog() {
        List<String> toDisplay = new ArrayList<>();
        toDisplay.add("Lava: " + tank.getFluidAmount() + " / " + tank.getFluidCapacity() + " Lava");
        toDisplay.add("Energy: " + storage.getEnergyStored() + " / " + storage.getEnergyCapacity() + " PE");
        return toDisplay;
    }

    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }

    @Override
    public FluidTank getFluidTank() {
        return tank;
    }
}
