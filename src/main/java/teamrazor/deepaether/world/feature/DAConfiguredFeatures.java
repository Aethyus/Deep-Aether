package teamrazor.deepaether.world.feature;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import teamrazor.deepaether.DeepAetherMod;


public class DAConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> POISON_LAKE_CONFIGURATION = createKey("poison_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POISON_SPRING_CONFIGURATION = createKey("poison_spring");
    public static final ResourceKey<ConfiguredFeature<?,?>> YAGROOT_TREE_CONFIGURATION = createKey("yagroot_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> CRUDEROOT_TREE_CONFIGURATION = createKey("cruderoot_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROSEROOT_AND_BLUE_ROSEROOT_TREES_PLACEMENT = createKey("roseroot_and_blue_roseroot_trees_placement");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YAGROOT_AND_CRUDEROOT_TREES_PLACEMENT = createKey("yagroot_and_cruderoot_trees_placement");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_MOSS_VEGETATION = createKey("aether_moss_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_MOSS_PATCH_BONEMEAL = createKey("aether_moss_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SKYJADE_CONFIGURATION = createKey("skyjade_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASETERITE_CONFIGURATION = createKey("aseterite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLORITE_CONFIGURATION = createKey("clorite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AERLAVENDER_PATCH = createKey("aerlavender_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_TREE_CONFIGURATION = createKey("skyroot_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ROSEROOT_TREE_LARGE = createKey("roseroot_tree_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROSEROOT_TREE_SMALL = createKey("roseroot_tree_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_ROSEROOT_TREE_LARGE = createKey("blue_roseroot_tree_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_ROSEROOT_TREE_SMALL = createKey("blue_roseroot_tree_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROSEROOT_FOREST_VEGETATION = createKey("roseroot_forest_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROSEROOT_TREES_PLACEMENT = createKey("roseroot_trees_placement");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_ROSEROOT_TREES_PLACEMENT = createKey("blue_roseroot_trees_placement");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_CATTAILS_PATCH =  createKey("aether_cattails_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_GRASS_PATCH = createKey("golden_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONBERRY_TREE = createKey("conberry_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUNROOT_TREE = createKey("sunroot_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_VINES_PATCH = createKey("golden_vines_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_GRASS_BLOCK_BONEMEAL_PATCH = createKey("golden_grass_block_bonemeal_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VIRULENT_QUICKSAND_PATCH = createKey("virulent_quicksand_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_HEIGHTS_FLOWERS = createKey("golden_heights_flowers");


    public static final ResourceKey<ConfiguredFeature<?, ?>> STERLING_AERCLOUD_CONFIGURATION = createKey("sterling_aercloud");
    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(DeepAetherMod.MODID, name));
    }

/*
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> holdergetter = context.lookup(Registries.BLOCK);
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, POISON_LAKE_CONFIGURATION, AetherFeatures.LAKE.get(), AetherConfiguredFeatureBuilders.lake(BlockStateProvider.simple(DABlocks.POISON_BLOCK.get()),
                BlockStateProvider.simple(DABlocks.AETHER_MUD.get())));
        register(context, POISON_SPRING_CONFIGURATION, Feature.SPRING,
                AetherConfiguredFeatureBuilders.spring(DABlocks.POISON_BLOCK.get().getFluid().defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, AetherBlocks.HOLYSTONE.get(), DABlocks.AETHER_MUD.get())));

        register(context, SKYROOT_TREE_CONFIGURATION, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LEAVES),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());

        register(context, ROSEROOT_TREE_LARGE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(DAFeatureStates.ROSEROOT_LOG),
                        new StraightTrunkPlacer(9,10,0),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DAFeatureStates.ROSEROOT_LEAVES, 2).add(DAFeatureStates.FLOWERING_ROSEROOT_LEAVES,1)),
                        new RoserootFoliagePlacer(ConstantInt.of(1), ConstantInt.ZERO, ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());

        register(context, ROSEROOT_TREE_SMALL, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(DAFeatureStates.ROSEROOT_LOG),
                        new StraightTrunkPlacer(5,2,0),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DAFeatureStates.ROSEROOT_LEAVES, 2).add(DAFeatureStates.FLOWERING_ROSEROOT_LEAVES,1)),
                        new RoserootFoliagePlacer(ConstantInt.of(1), ConstantInt.ZERO, ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());

        register(context, VIRULENT_QUICKSAND_PATCH, AetherFeatures.SHELF.get(),
                new ShelfConfiguration(
                        BlockStateProvider.simple(DAFeatureStates.VIRULENT_QUICKSAND),
                        ConstantFloat.of(Mth.sqrt(12)),
                        UniformInt.of(0, 48),
                        HolderSet.direct(Block::builtInRegistryHolder, DABlocks.AETHER_MUD.get())));


        register(context, BLUE_ROSEROOT_TREE_LARGE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(DAFeatureStates.ROSEROOT_LOG),
                        new StraightTrunkPlacer(9,10,0),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DAFeatureStates.BLUE_ROSEROOT_LEAVES, 2).add(DAFeatureStates.FLOWERING_BLUE_ROSEROOT_LEAVES,1)),
                        new RoserootFoliagePlacer(ConstantInt.of(1), ConstantInt.ZERO, ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());

        register(context, BLUE_ROSEROOT_TREE_SMALL, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(DAFeatureStates.ROSEROOT_LOG),
                        new StraightTrunkPlacer(5,2,0),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DAFeatureStates.BLUE_ROSEROOT_LEAVES, 2).add(DAFeatureStates.FLOWERING_BLUE_ROSEROOT_LEAVES,1)),
                        new RoserootFoliagePlacer(ConstantInt.of(1), ConstantInt.ZERO, ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());


        register(context, YAGROOT_TREE_CONFIGURATION, Feature.TREE,
                (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(DAFeatureStates.YAGROOT_LOG),
                        new YagrootTrunkPlacer(4, 6, 2), BlockStateProvider.simple(DAFeatureStates.YAGROOT_LEAVES),
                        new YagrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), ConstantInt.of(1)), Optional.of(
                        new YagrootRootPlacer(UniformInt.of(0, 1), BlockStateProvider.simple(DAFeatureStates.YAGROOT_ROOTS), Optional.of(
                                new AboveRootPlacement(BlockStateProvider.simple(DAFeatureStates.AETHER_MOSS_CARPET), 0.5F)),
                                new MangroveRootPlacement(holdergetter.getOrThrow(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), HolderSet.direct(Block::builtInRegistryHolder, DABlocks.AETHER_MUD.get(), DABlocks.MUDDY_YAGROOT_ROOTS.get()), BlockStateProvider.simple(DAFeatureStates.YAGROOT_ROOTS), 8, 15, 0.0F))),
                        new TwoLayersFeatureSize(2, 0, 2))).decorators(List.of(new YagrootVineDecorator(0.2f))).ignoreVines().build());


        register(context, CRUDEROOT_TREE_CONFIGURATION, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(DAFeatureStates.CRUDEROOT_LOG),
                        new StraightTrunkPlacer(5, 7, 3),
                        BlockStateProvider.simple(DAFeatureStates.CRUDEROOT_LEAVES),
                        new RoserootFoliagePlacer(ConstantInt.of(1), ConstantInt.ZERO, ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context, CONBERRY_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(DAFeatureStates.CONBERRY_LOG),
                        new TwinTrunkPlacer(7, 6, 3),
                        BlockStateProvider.simple(DAFeatureStates.CONBERRY_LEAVES),
                        new GoldenOakFoliagePlacer(ConstantInt.of(0), ConstantInt.ZERO, ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context, SUNROOT_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(DABlocks.SUNROOT_LOG.get()),
                        new SunrootHookedTrunkPlacer(8, 14, 14),
                        BlockStateProvider.simple(DABlocks.SUNROOT_LEAVES.get()),
                        new SunrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(2, 1, 4)
                ).ignoreVines().build());


        register(context, GOLDEN_GRASS_PATCH, Feature.FLOWER,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(DABlocks.MINI_GOLDEN_GRASS.get().defaultBlockState(), 32)
                        .add(DABlocks.SHORT_GOLDEN_GRASS.get().defaultBlockState(), 32)
                        .add(DABlocks.MEDIUM_GOLDEN_GRASS.get().defaultBlockState(), 16)
                        .add(DABlocks.GOLDEN_FLOWER.get().defaultBlockState(), 8)
                        .add(DABlocks.ENCHANTED_BLOSSOM.get().defaultBlockState(), 3)
                        .add(DABlocks.TALL_GOLDEN_GRASS.get().defaultBlockState(), 2)), 418));


        register(context, GOLDEN_GRASS_BLOCK_BONEMEAL_PATCH,  Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(DABlocks.MEDIUM_GOLDEN_GRASS.get().defaultBlockState(), 1)
                        .add(DABlocks.MINI_GOLDEN_GRASS.get().defaultBlockState(), 1)
                        .add(DABlocks.SHORT_GOLDEN_GRASS.get().defaultBlockState(),1)
                        .add(DABlocks.TALL_GOLDEN_GRASS.get().defaultBlockState(),1))));

        register(context, GOLDEN_HEIGHTS_FLOWERS,  Feature.FLOWER,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(DABlocks.GOLDEN_FLOWER.get().defaultBlockState(), 3)
                        .add(DABlocks.ENCHANTED_BLOSSOM.get().defaultBlockState(), 3)), 64));

        register(context, GOLDEN_VINES_PATCH, Feature.RANDOM_PATCH,
               new RandomPatchConfiguration(1, 1, 0,
                       PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN,
                               new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(
                                               new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                                       .add(UniformInt.of(0, 1), 1)
                                                       .add(UniformInt.of(0, 2), 4)
                                                       .add(UniformInt.of(0, 3), 5).build()), weightedstateprovider),
                                       BlockColumnConfiguration.layer(ConstantInt.of(1), randomizedintstateprovider)),
                                       Direction.UP, BlockPredicate.ONLY_IN_AIR_PREDICATE, true),
                               BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.wouldSurvive(DABlocks.GOLDEN_VINES_PLANT.get().defaultBlockState(), BlockPos.ZERO), BlockPredicate.not(BlockPredicate.matchesBlocks(DABlocks.GOLDEN_VINES.get())))))));

        register(context, AERLAVENDER_PATCH, Feature.FLOWER,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(DAFeatureStates.AERLAVENDER, 64)
                        .add(DAFeatureStates.TALL_AERLAVENDER, 32)
                        .add(Blocks.TALL_GRASS.defaultBlockState(), 16)
                        .add(AetherFeatureStates.BERRY_BUSH, 1)
                        .add(Blocks.GRASS.defaultBlockState(), 32)), 418));


        register(context, AETHER_CATTAILS_PATCH, Feature.FLOWER,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(DAFeatureStates.AETHER_CATTAILS, 5)
                        .add(Blocks.GRASS.defaultBlockState(), 5)
                        .add(DAFeatureStates.TALL_AETHER_CATTAILS, 3)), 15));

        register(context, ROSEROOT_FOREST_VEGETATION, Feature.FLOWER,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(DAFeatureStates.RADIANT_ORCHID, 6)
                        .add(AetherFeatureStates.PURPLE_FLOWER, 3)
                        .add(AetherFeatureStates.WHITE_FLOWER, 3)
                        .add(Blocks.TALL_GRASS.defaultBlockState(), 32)
                        .add(AetherFeatureStates.BERRY_BUSH, 1)
                        .add(Blocks.GRASS.defaultBlockState(), 64)), 400));

        register(context, ROSEROOT_TREES_PLACEMENT, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(ROSEROOT_TREE_LARGE), PlacementUtils.filteredByBlockSurvival(DABlocks.ROSEROOT_SAPLING.get())), 0.33F)),
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(ROSEROOT_TREE_SMALL), PlacementUtils.filteredByBlockSurvival(DABlocks.ROSEROOT_SAPLING.get()))));

        register(context, BLUE_ROSEROOT_TREES_PLACEMENT, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(BLUE_ROSEROOT_TREE_LARGE), PlacementUtils.filteredByBlockSurvival(DABlocks.BLUE_ROSEROOT_SAPLING.get())), 0.33F)),
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(BLUE_ROSEROOT_TREE_SMALL), PlacementUtils.filteredByBlockSurvival(DABlocks.BLUE_ROSEROOT_SAPLING.get()))));

        register(context, ROSEROOT_AND_BLUE_ROSEROOT_TREES_PLACEMENT, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(BLUE_ROSEROOT_TREES_PLACEMENT), PlacementUtils.filteredByBlockSurvival(DABlocks.BLUE_ROSEROOT_SAPLING.get())), 0.15F)),
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(ROSEROOT_TREES_PLACEMENT), PlacementUtils.filteredByBlockSurvival(DABlocks.BLUE_ROSEROOT_SAPLING.get()))));


        register(context, YAGROOT_AND_CRUDEROOT_TREES_PLACEMENT, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CRUDEROOT_TREE_CONFIGURATION), PlacementUtils.filteredByBlockSurvival(DABlocks.CRUDEROOT_SAPLING.get())), 0.25F)),
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(YAGROOT_TREE_CONFIGURATION), PlacementUtils.filteredByBlockSurvival(DABlocks.YAGROOT_SAPLING.get()))));


        register(context, AETHER_MOSS_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DABlocks.AETHER_MOSS_CARPET.get().defaultBlockState(), 25).add(Blocks.GRASS.defaultBlockState(), 50).add(Blocks.TALL_GRASS.defaultBlockState(), 10))));
        register(context, AETHER_MOSS_PATCH_BONEMEAL, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.MOSS_REPLACEABLE, BlockStateProvider.simple(DABlocks.AETHER_MOSS_BLOCK.get()),
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AETHER_MOSS_VEGETATION)), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.6F, UniformInt.of(1, 2), 0.75F));


        register(context, ORE_SKYJADE_CONFIGURATION, Feature.ORE, new OreConfiguration(AetherFeatureRules.HOLYSTONE, DAFeatureStates.SKYJADE_ORE, 6, 0.65F));
        register(context, ASETERITE_CONFIGURATION, Feature.ORE, new OreConfiguration(AetherFeatureRules.HOLYSTONE, DAFeatureStates.ASETERITE, 64));
        register(context, CLORITE_CONFIGURATION, Feature.ORE, new OreConfiguration(AetherFeatureRules.HOLYSTONE, DAFeatureStates.CLORITE, 64));

        register(context, STERLING_AERCLOUD_CONFIGURATION, AetherFeatures.AERCLOUD.get(), new AercloudConfiguration(2,
                SimpleStateProvider.simple(DABlocks.STERLING_AERCLOUD.get())));
    }

    static WeightedStateProvider weightedstateprovider = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DABlocks.GOLDEN_VINES_PLANT.get().defaultBlockState(), 4).add(DABlocks.GOLDEN_VINES_PLANT.get().defaultBlockState().setValue(GoldenVines.BERRIES, Boolean.valueOf(true)), 1));
    static RandomizedIntStateProvider randomizedintstateprovider = new RandomizedIntStateProvider(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DABlocks.GOLDEN_VINES.get().defaultBlockState(), 4).add(DABlocks.GOLDEN_VINES.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1)), CaveVinesBlock.AGE, UniformInt.of(23, 25));
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }*/
}
