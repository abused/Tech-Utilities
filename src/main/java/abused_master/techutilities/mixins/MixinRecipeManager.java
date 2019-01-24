package abused_master.techutilities.mixins;

import abused_master.techutilities.utils.CacheMapHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public class MixinRecipeManager {

    @Inject(method = "add", at = @At("RETURN"))
    public void add(Recipe<?> recipe_1, CallbackInfo ci) {
        if(recipe_1.getType() == RecipeType.SMELTING) {
            ItemStack input = recipe_1.getPreviewInputs().get(0).getStackArray()[0];
            if(!CacheMapHolder.INSTANCE.furnaceRecipes.containsKey(input)) {
                CacheMapHolder.INSTANCE.furnaceRecipes.put(input, recipe_1.getOutput());
            }
        }
    }
}
