package abused_master.techutilities.utils.fluid;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidPack {

    private int amount;
    private Fluid fluid;
    private CompoundTag tag;

    public FluidPack(Fluid fluid, int amount) {
        if (fluid != null) {
            this.amount = amount;
            this.fluid = fluid;
        } else {
            throw new NullPointerException("Input Fluid into FluidPack cannot be null!");
        }
    }

    public FluidPack(Fluid fluid, int amount, CompoundTag compoundTag) {
        this(fluid, amount);

        if (compoundTag != null) {
            this.tag = compoundTag;
        }
    }

    public FluidPack(FluidPack pack, int amount) {
        this(pack.getFluid(), amount, pack.getTag());
    }

    public static FluidPack loadFluidFromTag(CompoundTag nbt) {
        if (nbt == null || !nbt.containsKey("FluidName", NbtType.STRING)) {
            return null;
        }

        String fluidName = nbt.getString("FluidName");
        if (Registry.FLUID.get(new Identifier(fluidName)) == null) {
            return null;
        }

        FluidPack pack = new FluidPack(Registry.FLUID.get(new Identifier(fluidName)), nbt.getInt("amount"));

        if (nbt.containsKey("tag")) {
            pack.setTag(nbt.getCompound("tag"));
        }

        return pack;
    }

    public CompoundTag writeToTag(CompoundTag nbt) {
        nbt.putString("FluidName", Registry.FLUID.getId(getFluid()).toString());
        nbt.putInt("amount", getAmount());

        if (nbt != null) {
            nbt.put("tag", tag);
        }

        return nbt;
    }

    public FluidPack copy() {
        return new FluidPack(getFluid(), getAmount(), getTag());
    }

    public boolean isFluidEqual(FluidPack other) {
        return other != null && getFluid() == other.getFluid() && isFluidStackTagEqual(other);
    }

    public boolean isFluidStackTagEqual(FluidPack other) {
        return tag == null ? other.tag == null : other.tag == null ? false : tag.equals(other.tag);
    }

    public void addAmount(int add) {
        this.amount += add;
    }

    public void subtractAmount(int subtract) {
        this.amount -= subtract;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public CompoundTag getTag() {
        return tag;
    }

    public void setTag(CompoundTag tag) {
        this.tag = tag;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof FluidPack)) {
            return false;
        }

        return isFluidEqual((FluidPack) o);
    }
}
