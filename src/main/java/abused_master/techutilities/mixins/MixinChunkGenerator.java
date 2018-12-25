package abused_master.techutilities.mixins;

import abused_master.techutilities.api.utils.world.WorldGenRegistry;
import net.minecraft.class_3233;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGenerator.class)
public class MixinChunkGenerator {

    @Inject(method = "generateFeatures", at = @At("HEAD"))
    public void generateFeatures(class_3233 class_3233_1, CallbackInfo ci) {
        WorldGenRegistry.generateWorld(class_3233_1.method_14336(), class_3233_1.method_14339(), class_3233_1.getWorld(), class_3233_1.getWorld().getChunkManager().getChunkGenerator(), class_3233_1.getWorld().getChunkManager());
    }

}