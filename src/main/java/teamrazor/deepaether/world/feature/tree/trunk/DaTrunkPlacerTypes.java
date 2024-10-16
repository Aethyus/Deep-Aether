package teamrazor.deepaether.world.feature.tree.trunk;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import teamrazor.deepaether.DeepAether;

public class DaTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, DeepAether.MODID);

    public static final RegistryObject<TrunkPlacerType<TwinTrunkPlacer>> TWIN_TRUNK_PLACER = TRUNK_PLACERS.register("twin_trunk_placer", () -> new TrunkPlacerType<>(TwinTrunkPlacer.CODEC));
    public static final RegistryObject<TrunkPlacerType<YagrootTrunkPlacer>> YAGROOT_TRUNK_PLACER = TRUNK_PLACERS.register("yagroot_trunk_placer", () -> new TrunkPlacerType<>(YagrootTrunkPlacer.CODEC));
    public static final RegistryObject<TrunkPlacerType<SunrootTunkPlacer>> SUNROOT_TRUNK_PLACER = TRUNK_PLACERS.register("sunroot_trunk_placer", () -> new TrunkPlacerType<>(SunrootTunkPlacer.CODEC));

}