package abused_master.techutilities.tiles.generator;

import abused_master.abusedlib.client.render.hud.IHudSupport;
import abused_master.abusedlib.fluid.FluidStack;
import abused_master.abusedlib.fluid.FluidContainer;
import abused_master.abusedlib.fluid.IFluidHandler;
import abused_master.abusedlib.tiles.BlockEntityBase;
import abused_master.techutilities.registry.ModBlockEntities;
import nerdhub.cardinalenergy.api.IEnergyHandler;
import nerdhub.cardinalenergy.impl.EnergyStorage;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityLavaGenerator extends BlockEntityBase implements IEnergyHandler, IHudSupport, IFluidHandler {

    public EnergyStorage storage = new EnergyStorage(100000);
    public int sendPerTick = 500;
    public int generatePer10 = 100;
    public FluidContainer tank = new FluidContainer(8000);

    public BlockEntityLavaGenerator() {
        super(ModBlockEntities.LAVA_GENERATOR);
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        super.fromTag(nbt);
        storage.readEnergyFromTag(nbt);

        if(this.tank != null) {
            this.tank.setBlockEntity(this);
            if(this.tank.getFluidStack() != null) {
                this.tank.readFromNBT(nbt);
            }

            if (nbt.containsKey("FluidData")) {
                this.tank.setFluidStack(FluidStack.fluidFromTag(nbt.getCompound("FluidData")));
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        super.toTag(nbt);
        storage.writeEnergyToTag(nbt);

        if (this.tank != null && this.tank.getFluidStack() != null) {
            CompoundTag tankTag = new CompoundTag();
            this.tank.getFluidStack().toTag(tankTag);
            nbt.put("FluidData", tankTag);
            this.tank.writeToNBT(nbt);
        }

        return nbt;
    }

    @Override
    public void tick() {
        if (tank.getFluidAmount() >= 10 && tank.getFluidStack().getFluid() instanceof LavaFluid && (storage.getEnergyStored() + generatePer10) <= storage.getEnergyCapacity()) {
            if(!world.isReceivingRedstonePower(pos)) {
                storage.receiveEnergy(generatePer10);
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
    public EnergyStorage getEnergyStorage(Direction direction) {
        return storage;
    }

    @Override
    public boolean isEnergyProvider(Direction direction) {
        return true;
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
        toDisplay.add("Energy: " + storage.getEnergyStored() + " / " + storage.getEnergyCapacity() + " CE");
        return toDisplay;
    }

    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }

    @Override
    public FluidContainer getFluidTank() {
        return tank;
    }
}
