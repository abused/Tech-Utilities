package abused_master.techutilities.mixins;

import abused_master.techutilities.client.render.*;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCollector;
import abused_master.techutilities.tiles.crystal.BlockEntityEnergyCrystal;
import abused_master.techutilities.tiles.crystal.BlockEntityItemTransfer;
import abused_master.techutilities.tiles.generator.BlockEntityLavaGenerator;
import abused_master.techutilities.tiles.machine.BlockEntityFluidPump;
import abused_master.techutilities.tiles.machine.BlockEntityMobGrinder;
import abused_master.techutilities.tiles.machine.BlockEntityQuarry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Map;

@Mixin(BlockEntityRenderDispatcher.class)
public class MixinBlockEntityRenderDispatcher {

    @Shadow
    @Final
    private Map<Class<? extends BlockEntity>, BlockEntityRenderer<? extends BlockEntity>> renderers;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(CallbackInfo ci) {
        this.renderers.put(BlockEntityQuarry.class, new QuarryRenderer());
        this.renderers.put(BlockEntityEnergyCrystal.class, new CrystalRenderer());
        this.renderers.put(BlockEntityEnergyCollector.class, new CrystalCollectorRenderer());
        this.renderers.put(BlockEntityLavaGenerator.class, new LavaGeneratorRenderer());
        this.renderers.put(BlockEntityFluidPump.class, new FluidPumpRenderer());
        this.renderers.put(BlockEntityMobGrinder.class, new MobGrinderRenderer());
        this.renderers.put(BlockEntityItemTransfer.class, new ItemTransferRenderer());

        Iterator var1 = this.renderers.values().iterator();

        while(var1.hasNext()) {
            BlockEntityRenderer<?> blockEntityRenderer_1 = (BlockEntityRenderer)var1.next();
            blockEntityRenderer_1.setRenderManager((BlockEntityRenderDispatcher) (Object) this);
        }
    }
}
