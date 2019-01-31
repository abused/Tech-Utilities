package abused_master.techutilities.utils.linker;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public interface ILinkerHandler {

    void link(PlayerEntity player, CompoundTag tag);
}
