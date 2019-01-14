package abused_master.techutilities.blocks.generators;

import abused_master.abusedlib.blocks.BlockBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.TileEntitySolarPanel;
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

public class BlockSolarPanel extends BlockBase {

    public EnumSolarPanelTypes solarPanelType;

    public BlockSolarPanel(EnumSolarPanelTypes type, String name) {
        super(name, Material.STONE, 1.0f, TechUtilities.modItemGroup);
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
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public boolean skipRenderingSide(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
        return blockState_1.getBlock() == this ? true : super.skipRenderingSide(blockState_1, blockState_2, direction_1);
    }

    @Override
    public boolean isTranslucent(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return new TileEntitySolarPanel(solarPanelType);
    }
}
