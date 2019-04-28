package abused_master.techutilities.blocks.generators;

import abused_master.abusedlib.blocks.BlockWithEntityBase;
import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.tiles.generator.BlockEntitySolarPanel;
import abused_master.techutilities.utils.IWrenchable;
import abused_master.techutilities.utils.WrenchHelper;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSolarPanel extends BlockWithEntityBase implements IWrenchable {

    public EnumSolarPanelTypes solarPanelType;

    public BlockSolarPanel(EnumSolarPanelTypes type, String name) {
        super(name, Material.STONE, 1.0f, TechUtilities.modItemGroup);
        this.solarPanelType = type;
    }

    @Override
    public boolean activate(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
        BlockEntitySolarPanel panel = (BlockEntitySolarPanel) world.getBlockEntity(blockPos);

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
        BlockEntitySolarPanel solarPanel = new BlockEntitySolarPanel();
        solarPanel.setType(solarPanelType);
        return solarPanel;
    }

    @Override
    public boolean onWrenched(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return player.isSneaking() ? WrenchHelper.dropBlock(world, pos) : false;
    }
}
