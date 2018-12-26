package abused_master.techutilities.blocks.machines;

import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class Quarry extends BlockWithEntity {

    public Quarry() {
        super(FabricBlockSettings.of(Material.STONE).build().strength(1, 1));
    }

    @Override
    public BlockRenderType getRenderType(BlockState var1) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return null;
    }
}
