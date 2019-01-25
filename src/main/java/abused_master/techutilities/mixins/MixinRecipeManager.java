package abused_master.techutilities.mixins;

import abused_master.techutilities.TechUtilities;
import abused_master.techutilities.utils.CacheMapHolder;
import abused_master.techutilities.utils.RecipeGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public abstract class MixinRecipeManager {

    @Inject(method = "onResourceReload", at = @At("RETURN"))
    public void onResourceReload(ResourceManager resourceManager_1, CallbackInfo ci) {
        for (RecipeGenerator.Output output : RecipeGenerator.getRecipes()) {
            this.add(RecipeManager.deserialize(new Identifier(TechUtilities.MODID, output.name), output.recipe));
        }
    }

    @Inject(method = "add", at = @At("RETURN"))
    public void add(Recipe<?> recipe_1, CallbackInfo ci) {
        if (recipe_1.getType() == RecipeType.SMELTING) {
            ItemStack input = recipe_1.getPreviewInputs().get(0).getStackArray()[0];
            if (!CacheMapHolder.INSTANCE.furnaceRecipes.containsKey(input)) {
                CacheMapHolder.INSTANCE.furnaceRecipes.put(input, recipe_1.getOutput());
            }
        }
    }

    @Shadow
    public abstract void add(Recipe<?> recipe_1);
}
