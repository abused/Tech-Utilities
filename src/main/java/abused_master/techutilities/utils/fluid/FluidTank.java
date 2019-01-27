package abused_master.techutilities.utils.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;

public class FluidTank implements IFluidTank {

    private int capacity;
    private FluidPack fluidPack;

    public FluidTank(int capacity) {
        this.capacity = capacity;
        this.fluidPack = null;
    }

    public FluidTank(FluidPack fluid, int capacity) {
        this.capacity = capacity;
        this.fluidPack = fluid;
    }

    public FluidTank(Fluid fluid, int amount, int capacity) {
        this(new FluidPack(fluid, amount), capacity);
    }

    @Override
    public boolean extractFluid(int amount) {
        return drain(amount);
    }

    @Override
    public boolean fillFluid(FluidPack fluid) {
        return fill(fluid) > 0;
    }

    @Override
    public int getFluidAmount() {
        if (fluidPack == null) {
            return 0;
        }

        return fluidPack.getAmount();
    }

    @Override
    public int getFluidCapacity() {
        return capacity;
    }

    @Override
    public FluidPack getFluidPack() {
        return fluidPack;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void setFluidPack(FluidPack fluidPack) {
        this.fluidPack = fluidPack;
    }

    @Override
    public FluidTank readFromNBT(CompoundTag nbt) {
        if (!nbt.containsKey("null")) {
            FluidPack fluid = FluidPack.loadFluidFromTag(nbt);
            setFluidPack(fluid);
        } else {
            setFluidPack(null);
        }

        return this;
    }

    @Override
    public CompoundTag writeToNBT(CompoundTag nbt) {
        if (fluidPack != null) {
            fluidPack.writeToTag(nbt);
        } else {
            nbt.putString("null", "");
        }

        return nbt;
    }

    public int fill(FluidPack fillFluid) {
        if (fillFluid == null || fillFluid.getAmount() <= 0) {
            return 0;
        }

        if (fluidPack == null) {
            fluidPack = new FluidPack(fillFluid, Math.min(capacity, fillFluid.getAmount()));
            return fluidPack.getAmount();
        }

        if (!fluidPack.isFluidEqual(fillFluid)) {
            return 0;
        }
        int filled = capacity - fluidPack.getAmount();

        if (fillFluid.getAmount() < filled) {
            fluidPack.addAmount(fillFluid.getAmount());
            filled = fillFluid.getAmount();
        } else {
            fluidPack.setAmount(capacity);
        }
        return filled;
    }

    public boolean drain(int amount) {
        if (fluidPack == null || amount <= 0) {
            return false;
        }

        int drained = amount;
        if (fluidPack.getAmount() < drained) {
            drained = fluidPack.getAmount();
        }

        fluidPack.subtractAmount(drained);
        if (fluidPack.getAmount() <= 0) {
            fluidPack = null;
        }

        return true;
    }
}
