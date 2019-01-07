package abused_master.techutilities.blocks.generators;

import abused_master.techutilities.tiles.TileEntitySolarPanel;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSolarPanel extends BlockWithEntity {

    public EnumSolarPanelTypes solarPanelType;

    public BlockSolarPanel(EnumSolarPanelTypes type) {
        super(FabricBlockSettings.of(Material.STONE).strength(1.0f, 1.0f).build());
        this.solarPanelType = type;
    }

    @Override
    public boolean activate(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, Direction direction, float float_1, float float_2, float float_3) {
        TileEntitySolarPanel panel = (TileEntitySolarPanel) world.getBlockEntity(blockPos);

        if(!world.isClient) {
            playerEntity.addChatMessage(new StringTextComponent("Energy: " + panel.storage.getEnergyStored() + " / " + panel.storage.getEnergyCapacity() + " PE"), true);
        }

        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState var1) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public boolean isSideVisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
        return blockState_1.getBlock() == this ? true : super.isSideVisible(blockState_1, blockState_2, direction_1);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return new TileEntitySolarPanel(solarPanelType);
    }
}
