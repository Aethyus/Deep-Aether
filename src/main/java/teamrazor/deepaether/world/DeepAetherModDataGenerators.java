package teamrazor.deepaether.world;
/*
import com.gildedgames.aether.data.generators.AetherDataGenerators;
import com.gildedgames.aether.data.resources.AetherDimensions;
import com.gildedgames.aether.data.resources.AetherNoiseGeneratorSettings;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import teamrazor.deepaether.DeepAetherMod;

import java.util.HashMap;
import java.util.Map;

public class DeepAetherModDataGenerators extends AetherDataGenerators {

    public static <T> DataProvider create(DataGenerator generator, ExistingFileHelper helper, Map<ResourceLocation, T> map, ResourceKey<Registry<T>> registryKey) {
        return JsonCodecProvider.forDatapackRegistry(generator, helper, DeepAetherMod.MODID, RegistryOps.create(JsonOps.INSTANCE, DATA_REGISTRY), registryKey, map);
    }

    public static <T> DataProvider create(RegistryAccess registryAccess, DataGenerator generator, ExistingFileHelper helper, Map<ResourceLocation, T> map, ResourceKey<Registry<T>> registryKey) {
        return JsonCodecProvider.forDatapackRegistry(generator, helper, DeepAetherMod.MODID, RegistryOps.create(JsonOps.INSTANCE, registryAccess), registryKey, map);
    }

    public static DataProvider levelStem(DataGenerator generator, ExistingFileHelper helper) {
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, DATA_REGISTRY);
        Map<ResourceLocation, LevelStem> map = new HashMap<>();
        Registry<Biome> biomeRegistry = registryOps.registry(Registry.BIOME_REGISTRY).orElseThrow();
        Registry<DimensionType> dimensionTypeRegistry = registryOps.registry(Registry.DIMENSION_TYPE_REGISTRY).orElseThrow();
        Registry<StructureSet> structureSetRegistry = registryOps.registry(Registry.STRUCTURE_SET_REGISTRY).orElseThrow();
        Registry<NormalNoise.NoiseParameters> noiseParametersRegistry = registryOps.registry(Registry.NOISE_REGISTRY).orElseThrow();
        Registry<NoiseGeneratorSettings> noiseGeneratorSettingsRegistry = registryOps.registry(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY).orElseThrow();
        BiomeSource source = DeepAetherModBiomeBuilders.buildDeepAetherBiomeSource(biomeRegistry);
        NoiseBasedChunkGenerator deepAetherChunkGen = new NoiseBasedChunkGenerator(structureSetRegistry, noiseParametersRegistry, source, getNoiseGeneratorSettings(noiseGeneratorSettingsRegistry));
        LevelStem levelStem = new LevelStem(dimensionTypeRegistry.getOrCreateHolderOrThrow(AetherDimensions.AETHER_DIMENSION_TYPE), deepAetherChunkGen);
        map.put(AetherDimensions.AETHER_LEVEL_STEM.location(), levelStem);
        final ResourceLocation registryId = Registry.LEVEL_STEM_REGISTRY.location();
        final String registryFolder = registryId.getPath();
        return new JsonCodecProvider<>(generator, helper, DeepAetherMod.MODID, registryOps, PackType.SERVER_DATA, registryFolder, LevelStem.CODEC, map);
    }

}*/