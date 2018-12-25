package abused_master.techutilities.blocks;

import abused_master.techutilities.registry.ModGuis;
import abused_master.techutilities.tiles.TileEntityEnergyFurnace;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EnergyFurnace extends BlockWithEntity {

    public EnergyFurnace() {
        super(FabricBlockSettings.of(Material.STONE).build().strength(1, 1));
    }

    @Override
    public boolean activate(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, Direction direction, float v, float v1, float v2) {
        if(!world.isClient) {
            ContainerProviderRegistry.INSTANCE.openContainer(ModGuis.ENERGY_FURNACE_CONTAINER, player, buf -> buf.writeBlockPos(blockPos));
        }

        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState var1) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new TileEntityEnergyFurnace();
    }
}
