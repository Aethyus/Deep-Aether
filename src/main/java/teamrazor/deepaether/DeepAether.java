package teamrazor.deepaether;


import com.aetherteam.aether.entity.AetherEntityTypes;
import com.legacy.lost_aether.registry.LCEntityTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;
import teamrazor.aeroblender.aether.AetherRuleCategory;
import teamrazor.deepaether.advancement.DAAdvancementTriggers;
import teamrazor.deepaether.block.behavior.DADispenseBehaviors;
import teamrazor.deepaether.block.behavior.DaCauldronInteraction;
import teamrazor.deepaether.datagen.*;
import teamrazor.deepaether.datagen.loot.DALootTableData;
import teamrazor.deepaether.datagen.loot.modifiers.DAGlobalLootModifiers;
import teamrazor.deepaether.datagen.loot.modifiers.DALootDataProvider;
import teamrazor.deepaether.datagen.tags.*;
import teamrazor.deepaether.event.DAGeneralEvents;
import teamrazor.deepaether.fluids.DAFluidTypes;
import teamrazor.deepaether.init.*;
import teamrazor.deepaether.networking.attachment.DAAttachments;
import teamrazor.deepaether.networking.packet.DAPlayerSyncPacket;
import teamrazor.deepaether.networking.packet.MoaEffectSyncPacket;
import teamrazor.deepaether.recipe.DARecipe;
import teamrazor.deepaether.recipe.DARecipeSerializers;
import teamrazor.deepaether.util.BetterBrewingRecipe;
import teamrazor.deepaether.world.biomes.DARegion;
import teamrazor.deepaether.world.biomes.DASurfaceData;
import teamrazor.deepaether.world.feature.DAFeatures;
import teamrazor.deepaether.world.feature.tree.decorators.DADecoratorType;
import teamrazor.deepaether.world.feature.tree.decorators.DARootPlacers;
import teamrazor.deepaether.world.feature.tree.foliage.DAFoliagePlacers;
import teamrazor.deepaether.world.feature.tree.trunk.DaTrunkPlacerTypes;
import teamrazor.deepaether.world.placementmodifier.DAPlacementModifiers;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod("deep_aether")
public class DeepAether {

	//TODO: add it_lang translation
	//TODO: add se_lang translation
	//TODO: clean up code
	//TODO: Add configs for flawless boss drops
	//TODO: Custom plants are solid
	//TODO: Some grass has rendering issues
	//TODO: Continue Sacred Lands Testing

	public static final Logger LOGGER = LogUtils.getLogger();

	public static final String MODID = "deep_aether";
	public static final String LOST_AETHER_CONTENT = "lost_aether_content";
	public static final String AETHER_GENESIS = "aether_genesis";
	public static final String AETHER_REDUX = "aether_redux";
	public static final String ANCIENT_AETHER = "ancient_aether";
	public static final String EMISSIVITY = "aether_emissivity";

	private static final String PROTOCOL_VERSION = "1";

	static Calendar CALENDER = Calendar.getInstance();
	public static boolean IS_HALLOWEEN = ((CALENDER.get(Calendar.MONTH) == Calendar.OCTOBER && CALENDER.get(Calendar.DAY_OF_MONTH) > 20)
			|| (CALENDER.get(Calendar.MONTH) == Calendar.NOVEMBER) && CALENDER.get(Calendar.DAY_OF_MONTH) < 10);

	public static boolean IsHalloweenContentEnabled() {
		return IS_HALLOWEEN || DeepAetherConfig.COMMON.always_enable_halloween_content.get();
	}

	public DeepAether(IEventBus bus, Dist dist) {
		bus.addListener(this::dataSetup);
		bus.addListener(this::commonSetup);
		bus.addListener(this::registerPackets);
		bus.addListener(this::addAetherAdditionalResourcesPack);

		//NeoForge.EVENT_BUS.register(this);


		GeckoLib.initialize(bus);
		DABlocks.BLOCKS.register(bus);
		DAItems.ITEMS.register(bus);
		DAParticles.PARTICLE_TYPES.register(bus);
		DAEntities.ENTITY_TYPES.register(bus);
		DASounds.SOUNDS.register(bus);
		DAFluids.FLUIDS.register(bus);
		DAFluidTypes.FLUID_TYPES.register(bus);
		DADecoratorType.REGISTRY.register(bus);
		DABlockEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
		DABlocks.registerWoodTypes();
		DAFoliagePlacers.FOLIAGE_PLACERS.register(bus);
		DARootPlacers.ROOT_PLACERS.register(bus);
		DaTrunkPlacerTypes.TRUNK_PLACERS.register(bus);
		DAFeatures.FEATURES.register(bus);
		DAGlobalLootModifiers.LOOT_MODIFIERS.register(bus);
		DAMobEffects.EFFECTS.register(bus);
		DAPotions.POTIONS.register(bus);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DeepAetherConfig.COMMON_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DeepAetherConfig.CLIENT_SPEC);
		DARecipe.RECIPE_TYPES.register(bus);
		DARecipeSerializers.RECIPE_SERIALIZERS.register(bus);
		DAMenuTypes.MENUS.register(bus);
		DAAdvancementTriggers.TRIGGERS.register(bus);
		DAAttachments.ATTACHMENTS.register(bus);
		DAPlacementModifiers.PLACEMENT_MODIFIERS.register(bus);
	}

	public void registerPackets(RegisterPayloadHandlerEvent event) {
		IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();

		registrar.play(DAPlayerSyncPacket.ID, DAPlayerSyncPacket::decode, DAPlayerSyncPacket::handle);
		registrar.play(MoaEffectSyncPacket.ID, MoaEffectSyncPacket::decode, MoaEffectSyncPacket::handle);
	}

	public void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper fileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		PackOutput packOutput = generator.getPackOutput();

		// Client Data
		generator.addProvider(event.includeClient(), new DABlockstateData(packOutput, fileHelper));
		generator.addProvider(event.includeClient(), new DAItemModelData(packOutput, fileHelper));

		// Server Data
		generator.addProvider(event.includeServer(), new DAWorldGenData(packOutput, lookupProvider));
		generator.addProvider(event.includeServer(), new DARecipeData(packOutput, lookupProvider));
		generator.addProvider(event.includeServer(), DALootTableData.create(packOutput));
		DABlockTagData blockTags = new DABlockTagData(packOutput, lookupProvider, fileHelper);
		generator.addProvider(event.includeServer(), blockTags);
		generator.addProvider(event.includeServer(), new DAItemTagData(packOutput, lookupProvider, blockTags.contentsGetter(), fileHelper));
		generator.addProvider(event.includeServer(), new DABiomeTagData(packOutput, lookupProvider, fileHelper));
		generator.addProvider(event.includeServer(), new DAFluidTagData(packOutput, lookupProvider, fileHelper));
		generator.addProvider(event.includeServer(), new DAEntityTagData(packOutput, lookupProvider, fileHelper));
		generator.addProvider(event.includeServer(), new DALootDataProvider(packOutput));
		generator.addProvider(event.includeClient(), new DADataMapData(packOutput, lookupProvider));
	}

	public void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			DaCauldronInteraction.bootStrap();
			DABlocks.registerPots();
			DABlocks.registerFlammability();
			DAItems.setupBucketReplacements();
			this.registerDispenserBehaviors();
			registerFlawlessBossDrops();

		});

		event.enqueueWork(() ->
		{
			Regions.register(new DARegion(new ResourceLocation(MODID, "deep_aether"), DeepAetherConfig.COMMON.deep_aether_biome_weight.get()));
			SurfaceRuleManager.addSurfaceRules(AetherRuleCategory.THE_AETHER, MODID, DASurfaceData.makeRules());
			BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.WATER, DAItems.BIO_CRYSTAL.get(), DAPotions.REMEDY_POTION.get()));
		});
	}

	private void registerFlawlessBossDrops() {
		this.getFlawlessBossDrop(AetherEntityTypes.SLIDER.get(), DeepAetherConfig.COMMON.slider_flawless_boss_drop.get(), DAItems.SLIDER_EYE.get());
		this.getFlawlessBossDrop(AetherEntityTypes.VALKYRIE_QUEEN.get(), DeepAetherConfig.COMMON.valkyrie_queen_flawless_boss_drop.get(), DAItems.MEDAL_OF_HONOR.get());
		this.getFlawlessBossDrop(AetherEntityTypes.SUN_SPIRIT.get(), DeepAetherConfig.COMMON.sun_spirit_flawless_boss_drop.get(), DAItems.SUN_CORE.get());
		if(ModList.get().isLoaded(DeepAether.LOST_AETHER_CONTENT))
			this.getFlawlessBossDrop(LCEntityTypes.AERWHALE_KING, DeepAetherConfig.COMMON.aerwhale_king_flawless_boss_drop.get(), DAItems.AERWHALE_SADDLE.get());
	}

	private void getFlawlessBossDrop(EntityType type, String string, Item fallBack) {
		if(string.equals("null")) {
			DAGeneralEvents.FLAWLESS_BOSS_DROP.put(type, null);
		}
		else {
			String[] SliderItemId = string.split(":");
			if (BuiltInRegistries.ITEM.containsKey(new ResourceLocation(SliderItemId[0], SliderItemId[1])))
				DAGeneralEvents.FLAWLESS_BOSS_DROP.put(type, BuiltInRegistries.ITEM.get(new ResourceLocation(SliderItemId[0], SliderItemId[1])));
			else {
				DAGeneralEvents.FLAWLESS_BOSS_DROP.put(type, fallBack);
				LOGGER.info("Config value " + string + " is referring to a missing item! Resolving to default value");
			}
		}
	}

	private void registerDispenserBehaviors() {
		DispenserBlock.registerBehavior(Items.POTION, DADispenseBehaviors.WATER_BOTTLE_TO_AETHER_MUD_DISPENSE_BEHAVIOR);
		DispenserBlock.registerBehavior(DAItems.PLACEABLE_POISON_BUCKET.get(), DADispenseBehaviors.DEEP_AETHER_BUCKET_PICKUP_DISPENSE_BEHAVIOR);
		DispenserBlock.registerBehavior(DAItems.VIRULENT_QUICKSAND_BUCKET.get(), DADispenseBehaviors.DEEP_AETHER_BUCKET_PICKUP_DISPENSE_BEHAVIOR);
	}
	public void addAetherAdditionalResourcesPack(AddPackFindersEvent event) {
		if (event.getPackType() == PackType.CLIENT_RESOURCES) {
			Path resourcePath = ModList.get().getModFileById(DeepAether.MODID).getFile().findResource("packs/overrides/deep_aether_additional_assets");
			var pack = Pack.readMetaAndCreate("builtin/deep_aether_additional_assets", Component.literal("Deep Aether Additional Assets"), false,
					new PathPackResources.PathResourcesSupplier(resourcePath, true), PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
			event.addRepositorySource(consumer -> consumer.accept(pack));

			if(ModList.get().isLoaded(EMISSIVITY)) {
				Path resourcePath1 = ModList.get().getModFileById(DeepAether.MODID).getFile().findResource("packs/overrides/deep_aether_emissivity");
				PathPackResources pack1 = new PathPackResources(ModList.get().getModFileById(DeepAether.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath1, true);
				PackMetadataSection metadata = new PackMetadataSection(Component.literal(""), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
				event.addRepositorySource((source) ->
						source.accept(Pack.create(
								"builtin/deep_aether_emissivity",
								Component.literal(""),
								true,
								new PathPackResources.PathResourcesSupplier(resourcePath, true),
								new Pack.Info(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), true),
								Pack.Position.TOP,
								false,
								PackSource.BUILT_IN)
						)
				);
			}

			if(ModList.get().isLoaded(AETHER_GENESIS) || ModList.get().isLoaded(AETHER_REDUX)) {
				var resourcePath1 = ModList.get().getModFileById(DeepAether.MODID).getFile().findResource("packs/overrides/golden_swet_ball/DAGoldenSwetBallFixClient");
				var pack1 = Pack.readMetaAndCreate("builtin/DAGoldenSwetBallFixClient", Component.literal("Deep Aether Golden Swet Ball Texture Fix"), true,
						new PathPackResources.PathResourcesSupplier(resourcePath, true), PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.DEFAULT);
				event.addRepositorySource(consumer -> consumer.accept(pack1));
			}
		}

		if(ModList.get().isLoaded(AETHER_GENESIS) && event.getPackType() == PackType.SERVER_DATA) {
			if (event.getPackType() == PackType.SERVER_DATA) {
				var resourcePath = ModList.get().getModFileById(DeepAether.MODID).getFile().findResource("packs/overrides/golden_swet_ball/DAGoldenSwetBallAetherGenesisFixData");
				var pack = Pack.readMetaAndCreate("builtin/DAGoldenSwetBallAetherGenesisFix", Component.literal("Deep Aether Golden Swet Ball Aether Genesis Fix"), true,
						new PathPackResources.PathResourcesSupplier(resourcePath, true), PackType.SERVER_DATA, Pack.Position.TOP, PackSource.SERVER);
				event.addRepositorySource(consumer -> consumer.accept(pack));
			}
		}

		else if(ModList.get().isLoaded(AETHER_REDUX) && event.getPackType() == PackType.SERVER_DATA) {
			if (event.getPackType() == PackType.SERVER_DATA) {
				var resourcePath = ModList.get().getModFileById(DeepAether.MODID).getFile().findResource("packs/overrides/golden_swet_ball/DAGoldenSwetBallAetherReduxFixData");
				var pack = Pack.readMetaAndCreate("builtin/DAGoldenSwetBallAetherReduxFix", Component.literal("Deep Aether Golden Swet Ball Aether Redux Fix"), true,
						new PathPackResources.PathResourcesSupplier(resourcePath, true), PackType.SERVER_DATA, Pack.Position.TOP, PackSource.SERVER);

				event.addRepositorySource(consumer -> consumer.accept(pack));
			}
		}

		if(ModList.get().isLoaded(LOST_AETHER_CONTENT) && event.getPackType() == PackType.SERVER_DATA) {
			if (event.getPackType() == PackType.SERVER_DATA) {
				var resourcePath = ModList.get().getModFileById(DeepAether.MODID).getFile().findResource("packs/compat_recipes/aether_lost_content_compat");
				var pack = Pack.readMetaAndCreate("builtin/lost_aether_content_compat", Component.literal("Lost Aether Content Compat"), true,
						new PathPackResources.PathResourcesSupplier(resourcePath, true), PackType.SERVER_DATA, Pack.Position.TOP, PackSource.SERVER);

				event.addRepositorySource(consumer -> consumer.accept(pack));
			}
		}
		//else {
			if (event.getPackType() == PackType.SERVER_DATA) {
				var resourcePath = ModList.get().getModFileById(DeepAether.MODID).getFile().findResource("packs/compat_recipes/aether_lost_content_not_compat");
				var pack = Pack.readMetaAndCreate("builtin/aether_lost_content_not_compat", Component.literal("Deep Aether Aerwhale Saddle Recipe"), true,
						new PathPackResources.PathResourcesSupplier(resourcePath, true), PackType.SERVER_DATA, Pack.Position.TOP, PackSource.SERVER);

				event.addRepositorySource(consumer -> consumer.accept(pack));
			}
		//}

		if(ModList.get().isLoaded(AETHER_REDUX) && event.getPackType() == PackType.SERVER_DATA) {
			if (event.getPackType() == PackType.SERVER_DATA) {
				var resourcePath = ModList.get().getModFileById(DeepAether.MODID).getFile().findResource("packs/compat_recipes/aether_redux_compat");
				var pack = Pack.readMetaAndCreate("builtin/aether_redux_compat", Component.literal("Aether Redux Compat"), true,
						new PathPackResources.PathResourcesSupplier(resourcePath, true), PackType.SERVER_DATA, Pack.Position.TOP, PackSource.SERVER);

				event.addRepositorySource(consumer -> consumer.accept(pack));
			}
		}

		if(ModList.get().isLoaded(ANCIENT_AETHER) && event.getPackType() == PackType.SERVER_DATA) {
			if (event.getPackType() == PackType.SERVER_DATA) {
				var resourcePath = ModList.get().getModFileById(DeepAether.MODID).getFile().findResource("packs/compat_recipes/ancient_aether_compat");
				var pack = Pack.readMetaAndCreate("builtin/ancient_aether_compat", Component.literal("Ancient Aether Compat"), true,
						new PathPackResources.PathResourcesSupplier(resourcePath, true), PackType.SERVER_DATA, Pack.Position.TOP, PackSource.SERVER);

				event.addRepositorySource(consumer -> consumer.accept(pack));
			}
		}
	}
}