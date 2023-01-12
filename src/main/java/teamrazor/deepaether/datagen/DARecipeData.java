package teamrazor.deepaether.datagen;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import teamrazor.deepaether.init.*;
import net.minecraftforge.common.Tags;
import teamrazor.deepaether.init.DABlocks;
import com.gildedgames.aether.data.providers.AetherRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import teamrazor.deepaether.DeepAetherMod;
import teamrazor.deepaether.datagen.tags.DATags;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DARecipeData extends AetherRecipeProvider {
    public DARecipeData(PackOutput output) {
        super(output, DeepAetherMod.MODID);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        woodFromLogs(consumer, DABlocks.ROSE_WOOD.get(), DABlocks.ROSE_LOG.get());
        woodFromLogs(consumer, DABlocks.STRIPPED_ROSE_WOOD.get(), DABlocks.STRIPPED_ROSE_LOG.get());
        planksFromLogs(consumer, DABlocks.ROSE_PLANKS.get(), DATags.Items.CRAFTS_ROSEROOT_PLANKS, 4);
        stairs(DABlocks.ROSE_STAIRS, DABlocks.ROSE_STAIRS).group("wooden_stairs").save(consumer);

        slabBuilder(RecipeCategory.BUILDING_BLOCKS, DABlocks.ROSE_SLAB.get(), Ingredient.of(DABlocks.ROSE_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(DABlocks.ROSE_PLANKS.get()), has(DABlocks.ROSE_PLANKS.get()))
                .save(consumer);

        fence(DABlocks.ROSE_FENCE, DABlocks.ROSE_PLANKS).save(consumer);
        fenceGate(DABlocks.ROSE_FENCE_GATE, DABlocks.ROSE_PLANKS).save(consumer);

        doorBuilder(DABlocks.ROSE_DOOR.get(), Ingredient.of(DABlocks.ROSE_PLANKS.get())).unlockedBy(getHasName(DABlocks.ROSE_PLANKS.get()), has(DABlocks.ROSE_PLANKS.get())).group("wooden_door").save(consumer);
        trapdoorBuilder(DABlocks.ROSE_TRAPDOOR.get(), Ingredient.of(DABlocks.ROSE_PLANKS.get())).unlockedBy(getHasName(DABlocks.ROSE_PLANKS.get()), has(DABlocks.ROSE_PLANKS.get())).group("wooden_trapdoor").save(consumer);
        pressurePlateBuilder(RecipeCategory.REDSTONE, DABlocks.ROSE_PRESSURE_PLATE.get(), Ingredient.of(DABlocks.ROSE_PLANKS.get())).unlockedBy(getHasName(DABlocks.ROSE_PLANKS.get()), has(DABlocks.ROSE_PLANKS.get())).group("wooden_pressure_plate").save(consumer);
        buttonBuilder(DABlocks.ROSE_BUTTON.get(), Ingredient.of(DABlocks.ROSE_PLANKS.get())).unlockedBy(getHasName(DABlocks.ROSE_PLANKS.get()), has(DABlocks.ROSE_PLANKS.get())).group("wooden_button").save(consumer);
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.ROSE_WALL.get(), DABlocks.ROSE_LOG.get());
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.STRIPPED_ROSE_WALL.get(), DABlocks.STRIPPED_ROSE_LOG.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.ROSEROOT_SIGN.get(), 3)
                .group("wooden_sign")
                .define('P', DABlocks.ROSE_PLANKS.get().asItem())
                .define('/', Tags.Items.RODS_WOODEN)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" / ")
                .unlockedBy(getHasName(DABlocks.ROSE_PLANKS.get()), has(DABlocks.ROSE_PLANKS.get()))
                .save(consumer);

        woodFromLogs(consumer, DABlocks.YAGROOT_WOOD.get(), DABlocks.YAGROOT_LOG.get());
        woodFromLogs(consumer, DABlocks.STRIPPED_YAGROOT_WOOD.get(), DABlocks.STRIPPED_YAGROOT_LOG.get());
        planksFromLogs(consumer, DABlocks.YAGROOT_PLANKS.get(), DATags.Items.CRAFTS_YAGROOT_PLANKS, 4);
        stairs(DABlocks.YAGROOT_STAIRS, DABlocks.YAGROOT_STAIRS).group("wooden_stairs").save(consumer);

        slabBuilder(RecipeCategory.BUILDING_BLOCKS, DABlocks.YAGROOT_SLAB.get(), Ingredient.of(DABlocks.YAGROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(DABlocks.YAGROOT_PLANKS.get()), has(DABlocks.YAGROOT_PLANKS.get()))
                .save(consumer);

        fence(DABlocks.YAGROOT_FENCE, DABlocks.YAGROOT_PLANKS).save(consumer);
        fenceGate(DABlocks.YAGROOT_FENCE_GATE, DABlocks.YAGROOT_PLANKS).save(consumer);

        doorBuilder(DABlocks.YAGROOT_DOOR.get(), Ingredient.of(DABlocks.YAGROOT_PLANKS.get())).unlockedBy(getHasName(DABlocks.YAGROOT_PLANKS.get()), has(DABlocks.YAGROOT_PLANKS.get())).group("wooden_door").save(consumer);
        trapdoorBuilder(DABlocks.YAGROOT_TRAPDOOR.get(), Ingredient.of(DABlocks.YAGROOT_PLANKS.get())).unlockedBy(getHasName(DABlocks.YAGROOT_PLANKS.get()), has(DABlocks.YAGROOT_PLANKS.get())).group("wooden_trapdoor").save(consumer);
        pressurePlateBuilder(RecipeCategory.REDSTONE, DABlocks.YAGROOT_PRESSURE_PLATE.get(), Ingredient.of(DABlocks.YAGROOT_PLANKS.get())).unlockedBy(getHasName(DABlocks.YAGROOT_PLANKS.get()), has(DABlocks.YAGROOT_PLANKS.get())).group("wooden_pressure_plate").save(consumer);
        buttonBuilder(DABlocks.YAGROOT_BUTTON.get(), Ingredient.of(DABlocks.YAGROOT_PLANKS.get())).unlockedBy(getHasName(DABlocks.YAGROOT_PLANKS.get()), has(DABlocks.YAGROOT_PLANKS.get())).group("wooden_button").save(consumer);
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.YAGROOT_WALL.get(), DABlocks.YAGROOT_LOG.get());
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.STRIPPED_YAGROOT_WALL.get(), DABlocks.STRIPPED_YAGROOT_LOG.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.YAGROOT_SIGN.get(), 3)
                .group("wooden_sign")
                .define('P', DABlocks.YAGROOT_PLANKS.get().asItem())
                .define('/', Tags.Items.RODS_WOODEN)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" / ")
                .unlockedBy(getHasName(DABlocks.YAGROOT_PLANKS.get()), has(DABlocks.YAGROOT_PLANKS.get()))
                .save(consumer);

        woodFromLogs(consumer, DABlocks.CRUDEROOT_WOOD.get(), DABlocks.CRUDEROOT_LOG.get());
        woodFromLogs(consumer, DABlocks.STRIPPED_CRUDEROOT_WOOD.get(), DABlocks.STRIPPED_CRUDEROOT_LOG.get());
        planksFromLogs(consumer, DABlocks.CRUDEROOT_PLANKS.get(), DATags.Items.CRAFTS_CRUDEROOT_PLANKS, 4);
        stairs(DABlocks.CRUDEROOT_STAIRS, DABlocks.CRUDEROOT_STAIRS).group("wooden_stairs").save(consumer);

        slabBuilder(RecipeCategory.BUILDING_BLOCKS, DABlocks.CRUDEROOT_SLAB.get(), Ingredient.of(DABlocks.CRUDEROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(DABlocks.CRUDEROOT_PLANKS.get()), has(DABlocks.CRUDEROOT_PLANKS.get()))
                .save(consumer);

        fence(DABlocks.CRUDEROOT_FENCE, DABlocks.CRUDEROOT_PLANKS).save(consumer);
        fenceGate(DABlocks.CRUDEROOT_FENCE_GATE, DABlocks.CRUDEROOT_PLANKS).save(consumer);

        doorBuilder(DABlocks.CRUDEROOT_DOOR.get(), Ingredient.of(DABlocks.CRUDEROOT_PLANKS.get())).unlockedBy(getHasName(DABlocks.CRUDEROOT_PLANKS.get()), has(DABlocks.CRUDEROOT_PLANKS.get())).group("wooden_door").save(consumer);
        trapdoorBuilder(DABlocks.CRUDEROOT_TRAPDOOR.get(), Ingredient.of(DABlocks.CRUDEROOT_PLANKS.get())).unlockedBy(getHasName(DABlocks.CRUDEROOT_PLANKS.get()), has(DABlocks.CRUDEROOT_PLANKS.get())).group("wooden_trapdoor").save(consumer);
        pressurePlateBuilder(RecipeCategory.REDSTONE, DABlocks.CRUDEROOT_PRESSURE_PLATE.get(), Ingredient.of(DABlocks.CRUDEROOT_PLANKS.get())).unlockedBy(getHasName(DABlocks.CRUDEROOT_PLANKS.get()), has(DABlocks.CRUDEROOT_PLANKS.get())).group("wooden_pressure_plate").save(consumer);
        buttonBuilder(DABlocks.CRUDEROOT_BUTTON.get(), Ingredient.of(DABlocks.CRUDEROOT_PLANKS.get())).unlockedBy(getHasName(DABlocks.CRUDEROOT_PLANKS.get()), has(DABlocks.CRUDEROOT_PLANKS.get())).group("wooden_button").save(consumer);
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.CRUDEROOT_WALL.get(), DABlocks.CRUDEROOT_LOG.get());
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.STRIPPED_CRUDEROOT_WALL.get(), DABlocks.STRIPPED_CRUDEROOT_LOG.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.CRUDEROOT_SIGN.get(), 3)
                .group("wooden_sign")
                .define('P', DABlocks.CRUDEROOT_PLANKS.get().asItem())
                .define('/', Tags.Items.RODS_WOODEN)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" / ")
                .unlockedBy(getHasName(DABlocks.CRUDEROOT_PLANKS.get()), has(DABlocks.CRUDEROOT_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.AERGLOW_PETAL_BLOCK.get(), 1)
                .define('A', DAItems.AERGLOW_PETAL.get())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy(getHasName(DAItems.AERGLOW_PETAL.get()), has(DAItems.AERGLOW_PETAL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.AETHER_MOSS_CARPET.get(), 3)
                .define('A', DABlocks.AETHER_MOSS_BLOCK.get().asItem())
                .pattern("AAA")
                .unlockedBy(getHasName(DABlocks.AETHER_MOSS_BLOCK.get()), has(DABlocks.AETHER_MOSS_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DABlocks.PACKED_AETHER_MUD.get())
                .requires(DABlocks.AETHER_MUD.get())
                .requires(Items.WHEAT)
                .unlockedBy(getHasName(DABlocks.AETHER_MUD.get()), has(DABlocks.AETHER_MUD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DABlocks.MUDDY_YAGROOT_ROOTS.get())
                .requires(DABlocks.AETHER_MUD.get())
                .requires(DABlocks.MUDDY_YAGROOT_ROOTS.get())
                .unlockedBy(getHasName(DABlocks.AETHER_MUD.get()), has(DABlocks.AETHER_MUD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.AETHER_MUD_BRICKS.get(), 4)
                .define('A', DABlocks.PACKED_AETHER_MUD.get().asItem())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy(getHasName(DABlocks.PACKED_AETHER_MUD.get()), has(DABlocks.PACKED_AETHER_MUD.get()))
                .save(consumer);


        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.ASETERITE_STAIRS.get(), DABlocks.ASETERITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.ASETERITE_SLAB.get(), DABlocks.ASETERITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.ASETERITE_WALL.get(), DABlocks.ASETERITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_ASETERITE.get(), DABlocks.ASETERITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_ASETERITE_STAIRS.get(), DABlocks.ASETERITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_ASETERITE_SLAB.get(), DABlocks.ASETERITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_ASETERITE_STAIRS.get(), DABlocks.POLISHED_ASETERITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_ASETERITE_SLAB.get(), DABlocks.POLISHED_ASETERITE.get(),2);

        stairs(DABlocks.ASETERITE_STAIRS, DABlocks.ASETERITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.ASETERITE_SLAB.get(), DABlocks.ASETERITE.get());
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.ASETERITE_WALL.get(), DABlocks.ASETERITE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.POLISHED_ASETERITE.get(), 4)
                .define('A', DABlocks.ASETERITE.get().asItem())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy(getHasName(DABlocks.ASETERITE.get()), has(DABlocks.ASETERITE.get()))
                .save(consumer);

        stairs(DABlocks.POLISHED_ASETERITE_STAIRS, DABlocks.POLISHED_ASETERITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_ASETERITE_SLAB.get(), DABlocks.POLISHED_ASETERITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.CLORITE_STAIRS.get(), DABlocks.CLORITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.CLORITE_SLAB.get(), DABlocks.CLORITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.CLORITE_WALL.get(), DABlocks.CLORITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_CLORITE.get(), DABlocks.CLORITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_CLORITE_STAIRS.get(), DABlocks.CLORITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_CLORITE_SLAB.get(), DABlocks.CLORITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_CLORITE_STAIRS.get(), DABlocks.POLISHED_CLORITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_CLORITE_SLAB.get(), DABlocks.POLISHED_CLORITE.get(),2);

        stairs(DABlocks.CLORITE_STAIRS, DABlocks.CLORITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.CLORITE_SLAB.get(), DABlocks.CLORITE.get());
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.CLORITE_WALL.get(), DABlocks.CLORITE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.POLISHED_CLORITE.get(), 4)
                .define('A', DABlocks.CLORITE.get().asItem())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy(getHasName(DABlocks.CLORITE.get()), has(DABlocks.CLORITE.get()))
                .save(consumer);

        stairs(DABlocks.POLISHED_CLORITE_STAIRS, DABlocks.POLISHED_CLORITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_CLORITE_SLAB.get(), DABlocks.POLISHED_CLORITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.JARINITE_STAIRS.get(), DABlocks.JARINITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.JARINITE_SLAB.get(), DABlocks.JARINITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.JARINITE_WALL.get(), DABlocks.JARINITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_JARINITE.get(), DABlocks.JARINITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_JARINITE_STAIRS.get(), DABlocks.JARINITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_JARINITE_SLAB.get(), DABlocks.JARINITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_JARINITE_STAIRS.get(), DABlocks.POLISHED_JARINITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_JARINITE_SLAB.get(), DABlocks.POLISHED_JARINITE.get(),2);

        stairs(DABlocks.JARINITE_STAIRS, DABlocks.JARINITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.JARINITE_SLAB.get(), DABlocks.JARINITE.get());
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.JARINITE_WALL.get(), DABlocks.JARINITE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.POLISHED_JARINITE.get(), 4)
                .define('A', DABlocks.JARINITE.get().asItem())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy(getHasName(DABlocks.JARINITE.get()), has(DABlocks.JARINITE.get()))
                .save(consumer);

        stairs(DABlocks.POLISHED_JARINITE_STAIRS, DABlocks.POLISHED_JARINITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_JARINITE_SLAB.get(), DABlocks.POLISHED_JARINITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.YALLESITE_STAIRS.get(), DABlocks.YALLESITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.YALLESITE_SLAB.get(), DABlocks.YALLESITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.YALLESITE_WALL.get(), DABlocks.YALLESITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_YALLESITE.get(), DABlocks.YALLESITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_YALLESITE_STAIRS.get(), DABlocks.YALLESITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_YALLESITE_SLAB.get(), DABlocks.YALLESITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_YALLESITE_STAIRS.get(), DABlocks.POLISHED_YALLESITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_YALLESITE_SLAB.get(), DABlocks.POLISHED_YALLESITE.get(),2);

        stairs(DABlocks.YALLESITE_STAIRS, DABlocks.YALLESITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.YALLESITE_SLAB.get(), DABlocks.YALLESITE.get());
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.YALLESITE_WALL.get(), DABlocks.YALLESITE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.POLISHED_YALLESITE.get(), 4)
                .define('A', DABlocks.YALLESITE.get().asItem())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy(getHasName(DABlocks.YALLESITE.get()), has(DABlocks.YALLESITE.get()))
                .save(consumer);

        stairs(DABlocks.POLISHED_YALLESITE_STAIRS, DABlocks.POLISHED_YALLESITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_YALLESITE_SLAB.get(), DABlocks.POLISHED_YALLESITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.DARKERITE_STAIRS.get(), DABlocks.DARKERITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.DARKERITE_SLAB.get(), DABlocks.DARKERITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.DARKERITE_WALL.get(), DABlocks.DARKERITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_DARKERITE.get(), DABlocks.DARKERITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_DARKERITE_STAIRS.get(), DABlocks.DARKERITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_DARKERITE_SLAB.get(), DABlocks.DARKERITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_DARKERITE_STAIRS.get(), DABlocks.POLISHED_DARKERITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_DARKERITE_SLAB.get(), DABlocks.POLISHED_DARKERITE.get(),2);

        stairs(DABlocks.DARKERITE_STAIRS, DABlocks.DARKERITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.DARKERITE_SLAB.get(), DABlocks.DARKERITE.get());
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.DARKERITE_WALL.get(), DABlocks.DARKERITE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.POLISHED_DARKERITE.get(), 4)
                .define('A', DABlocks.DARKERITE.get().asItem())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy(getHasName(DABlocks.DARKERITE.get()), has(DABlocks.DARKERITE.get()))
                .save(consumer);

        stairs(DABlocks.POLISHED_DARKERITE_STAIRS, DABlocks.POLISHED_DARKERITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_DARKERITE_SLAB.get(), DABlocks.POLISHED_DARKERITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.GREOTITE_STAIRS.get(), DABlocks.GREOTITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.GREOTITE_SLAB.get(), DABlocks.GREOTITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.GREOTITE_WALL.get(), DABlocks.GREOTITE.get());

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_GREOTITE.get(), DABlocks.GREOTITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_GREOTITE_STAIRS.get(), DABlocks.GREOTITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_GREOTITE_SLAB.get(), DABlocks.GREOTITE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_GREOTITE_STAIRS.get(), DABlocks.POLISHED_GREOTITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_GREOTITE_SLAB.get(), DABlocks.POLISHED_GREOTITE.get(),2);

        stairs(DABlocks.GREOTITE_STAIRS, DABlocks.GREOTITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.GREOTITE_SLAB.get(), DABlocks.GREOTITE.get());
        wall(consumer, RecipeCategory.DECORATIONS, DABlocks.GREOTITE_WALL.get(), DABlocks.GREOTITE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.POLISHED_GREOTITE.get(), 4)
                .define('A', DABlocks.GREOTITE.get().asItem())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy(getHasName(DABlocks.GREOTITE.get()), has(DABlocks.GREOTITE.get()))
                .save(consumer);

        stairs(DABlocks.POLISHED_GREOTITE_STAIRS, DABlocks.POLISHED_GREOTITE).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.POLISHED_GREOTITE_SLAB.get(), DABlocks.POLISHED_GREOTITE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.SKYJADE_BLOCK.get())
                .define('A', DAItems.SKYJADE.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .unlockedBy(getHasName(DAItems.SKYJADE.get()), has(DAItems.SKYJADE.get()))
                .save(consumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.CLOUDIUM_BLOCK.get())
                .define('A', DAItems.CLOUDIUM_INGOT.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .unlockedBy(getHasName(DAItems.CLOUDIUM_INGOT.get()), has(DAItems.CLOUDIUM_INGOT.get()))
                .save(consumer);

        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.HOLYSTONE_BRICKS.get(), AetherBlocks.HOLYSTONE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.HOLYSTONE_BRICKS_STAIRS.get(), AetherBlocks.HOLYSTONE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.HOLYSTONE_BRICKS_SLAB.get(), AetherBlocks.HOLYSTONE.get(),2);
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.HOLYSTONE_BRICKS_STAIRS.get(), DABlocks.POLISHED_GREOTITE.get());
        stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.HOLYSTONE_BRICKS_SLAB.get(), DABlocks.POLISHED_GREOTITE.get(),2);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DABlocks.HOLYSTONE_BRICKS.get(), 4)
                .define('A', AetherBlocks.HOLYSTONE.get().asItem())
                .pattern("AA")
                .pattern("AA")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer);
        stairs(DABlocks.HOLYSTONE_BRICKS_STAIRS, DABlocks.HOLYSTONE_BRICKS).save(consumer);
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, DABlocks.HOLYSTONE_BRICKS_SLAB.get(), DABlocks.HOLYSTONE_BRICKS.get());

        smeltingOreRecipe(DAItems.SKYJADE.get(), DABlocks.SKYJADE_ORE.get(), 1F).save(consumer);

        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_TOOLS_SWORD.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_TOOLS_AXE.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_axe_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_TOOLS_PICKAXE.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_TOOLS_SHOVEL.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_shovel_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_TOOLS_HOE.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_hoe_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_ARMOR_BOOTS.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_ARMOR_LEGGINGS.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_ARMOR_CHESTPLATE.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_ARMOR_HELMET.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.SKYJADE_GLOVES.get(), 7000).group("altar_sword_repair").save(consumer, name("skyjade_gloves_repairing"));

        makeSword(DAItems.SKYJADE_TOOLS_SWORD, DAItems.SKYJADE).save(consumer);
        makeAxe(DAItems.SKYJADE_TOOLS_AXE, DAItems.SKYJADE).save(consumer);
        makePickaxe(DAItems.SKYJADE_TOOLS_PICKAXE, DAItems.SKYJADE).save(consumer);
        makeShovel(DAItems.SKYJADE_TOOLS_SHOVEL, DAItems.SKYJADE).save(consumer);
        makeHoe(DAItems.SKYJADE_TOOLS_HOE, DAItems.SKYJADE).save(consumer);

        makeBoots(DAItems.SKYJADE_ARMOR_BOOTS, DAItems.SKYJADE).save(consumer);
        makeLeggings(DAItems.SKYJADE_ARMOR_LEGGINGS, DAItems.SKYJADE).save(consumer);
        makeChestplate(DAItems.SKYJADE_ARMOR_CHESTPLATE, DAItems.SKYJADE).save(consumer);
        makeHelmet(DAItems.SKYJADE_ARMOR_HELMET, DAItems.SKYJADE).save(consumer);
        makeRing(DAItems.SKYJADE_RING, DAItems.SKYJADE.get()).save(consumer);
        makeGloves(DAItems.SKYJADE_GLOVES, DAItems.SKYJADE).save(consumer);

        makeRing(DAItems.GRAVITIE_RING, AetherBlocks.ENCHANTED_GRAVITITE.get().asItem()).save(consumer);

        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_SWORD.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_AXE.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_axe_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_PICKAXE.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_SHOVEL.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_shovel_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_HOE.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_hoe_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_BOOTS.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_LEGGINGS.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_CHESTPLATE.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_HELMET.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, DAItems.CLOUDIUM_GLOVES.get(), 16000).group("altar_sword_repair").save(consumer, name("cloudium_gloves_repairing"));


        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_SWORD.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_SWORD.get());
        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_AXE.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_AXE.get());
        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_PICKAXE.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_PICKAXE.get());
        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_SHOVEL.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_SHOVEL.get());
        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_HOE.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_HOE.get());

        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_BOOTS.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_BOOTS.get());
        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_LEGGINGS.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_LEGGINGS.get());
        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_CHESTPLATE.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_CHESTPLATE.get());
        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_HELMET.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_HELMET.get());
        cloudiumSmithingRecipe(consumer, DAItems.GRAVITIE_RING.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_RING.get());
        cloudiumSmithingRecipe(consumer, AetherItems.GRAVITITE_GLOVES.get(), RecipeCategory.COMBAT, DAItems.CLOUDIUM_GLOVES.get());

        enchantingRecipe(RecipeCategory.BUILDING_BLOCKS, DAItems.CLOUDIUM_SCRAP.get(), DABlocks.CLOUDIUM_DEBRIS.get(), 2.0F, 2000).save(consumer, name("cloudium_enchanting"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DAItems.CLOUDIUM_INGOT.get())
                .requires(DAItems.CLOUDIUM_SCRAP.get(),4)
                .requires(AetherBlocks.ENCHANTED_GRAVITITE.get(),4)
                .unlockedBy(getHasName(DABlocks.CLOUDIUM_DEBRIS.get()), has(DABlocks.CLOUDIUM_DEBRIS.get()))
                .save(consumer);

        smeltingFoodRecipe(DAItems.COOKED_QUAIL.get(), DAItems.RAW_QUAIL.get(), 0.35F).save(consumer);
        smeltingFoodRecipe(DAItems.COOKED_AERGLOW_FISH.get(), DAItems.RAW_AERGLOW_FISH.get(), 0.35F).save(consumer);

        makeBoat(DAItems.ROSEROOT_BOAT, DABlocks.ROSE_PLANKS.get()).save(consumer);
        makeBoat(DAItems.YAGROOT_BOAT, DABlocks.YAGROOT_PLANKS.get()).save(consumer);
        makeBoat(DAItems.CRUDEROOT_BOAT, DABlocks.CRUDEROOT_PLANKS.get()).save(consumer);

        makeChestBoat(DAItems.ROSEROOT_CHEST_BOAT.get()).save(consumer);
        makeChestBoat(DAItems.YAGROOT_CHEST_BOAT.get()).save(consumer);
        makeChestBoat(DAItems.CRUDEROOT_CHEST_BOAT.get()).save(consumer);
        hiddenEnchantingRecipe(RecipeCategory.MISC, DAItems.MUSIC_DISC_A_MORNING_WISH.get(), Items.MUSIC_DISC_OTHERSIDE, 2.0F, 2500).save(consumer, name("a_moring_wish_enchanting"));
        hiddenEnchantingRecipe(RecipeCategory.MISC, DAItems.MUSIC_DISC_NABOORU.get(), Items.MUSIC_DISC_PIGSTEP, 1.0F, 2500).save(consumer, name("nabooru_enchanting"));

        makeskyrootSticks(DABlocks.ROSE_PLANKS.get()).save(consumer, name("skyroot_sticks_from_roseroot_planks"));
        makeskyrootSticks(DABlocks.YAGROOT_PLANKS.get()).save(consumer, name("skyroot_sticks_from_yagroot_planks"));
        makeskyrootSticks(DABlocks.CRUDEROOT_PLANKS.get()).save(consumer, name("skyroot_sticks_from_cruderoot_planks"));


    }



    protected static void cloudiumSmithingRecipe(Consumer<FinishedRecipe> consumer, Item ingredient, RecipeCategory category, Item item) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(ingredient), Ingredient.of(DAItems.CLOUDIUM_INGOT.get()), category, item).unlocks("has_cloudium_ingot", has(DAItems.CLOUDIUM_INGOT.get())).save(consumer, name(getItemName(item) + "_smithing"));
    }
    protected static SimpleCookingRecipeBuilder smeltingFoodRecipe(ItemLike result, ItemLike ingredient, float experience) {
        return SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected static ShapedRecipeBuilder makeBoat(Supplier<? extends Item> boat, Block material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat.get())
                .define('#', material)
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(material), has(material));
    }
    protected static ShapedRecipeBuilder makeskyrootSticks(Block material) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AetherItems.SKYROOT_STICK.get(),4)
                .group("sticks")
                .define('#', material)
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(material), has(material));
    }
    protected static ShapelessRecipeBuilder makeChestBoat(Item boat) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, boat)
                .requires(boat)
                .requires(Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(boat), has(boat));
    }
}