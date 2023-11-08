package teamrazor.deepaether.recipe;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
public class PoisonRecipe extends AbstractPoisonRecipe {
    public PoisonRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result) {
        super(DARecipe.POISON_RECIPE.get(), id, group, ingredient, result);
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(AetherBlocks.FREEZER.get());
    }

    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.FREEZING.get();
    }

    public static class Serializer extends PoisonRecipeSerializer<PoisonRecipe> {
        public Serializer() {
            super(PoisonRecipe::new);
        }
    }
}
