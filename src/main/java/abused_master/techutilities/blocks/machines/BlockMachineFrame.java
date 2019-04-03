package abused_master.techutilities.blocks.machines;

import abused_master.abusedlib.blocks.BlockBase;
import abused_master.techutilities.TechUtilities;
import net.minecraft.block.Material;

public class BlockMachineFrame extends BlockBase {

    public BlockMachineFrame() {
        super("machine_frame", Material.STONE, 1.0f, TechUtilities.modItemGroup);
    }
}
