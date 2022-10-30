package teamrazor.deepaether.client.renderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class DeepAetherModelLayers {
    public static final ModelLayerLocation ROSEROOT_BOAT = register("roseroot_boat");
    public static final ModelLayerLocation ROSEROOT_CHEST_BOAT = register("roseroot_chest_boat");


    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String type) {
        return register(new ResourceLocation("deep_aether", name), type);
    }

    private static ModelLayerLocation register(ResourceLocation identifier, String type) {
        return new ModelLayerLocation(identifier, type);
    }
}
