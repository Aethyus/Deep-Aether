package io.github.razordevs.deep_aether.item.gear.skyjade;

import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import io.github.razordevs.deep_aether.init.DASounds;
import io.github.razordevs.deep_aether.item.gear.DaArmorMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import teamrazor.deepaether.DeepAether;

public class SkyjadeGlovesItem extends GlovesItem {
    public SkyjadeGlovesItem(double punchDamage, Item.Properties properties) {
        super(DaArmorMaterials.SKYJADE, punchDamage, ResourceLocation.fromNamespaceAndPath(DeepAether.MODID,"skyjade_gloves"), DASounds.ITEM_ARMOR_EQUIP_SKYJADE, properties);
    }
}
