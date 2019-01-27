package abused_master.techutilities.utils.fluid;

import net.minecraft.nbt.CompoundTag;

public interface IFluidTank {

    boolean extractFluid(int amount);
    boolean fillFluid(FluidPack fluid);
    int getFluidAmount();
    int getFluidCapacity();
    FluidPack getFluidPack();
    void setCapacity(int capacity);
    void setFluidPack(FluidPack fluidPack);
    FluidTank readFromNBT(CompoundTag nbt);
    CompoundTag writeToNBT(CompoundTag nbt);
}
