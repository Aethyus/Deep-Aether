package teamrazor.deepaether.item.gear;

import com.gildedgames.aether.item.accessories.gloves.GlovesItem;
import net.minecraft.sounds.SoundEvent;


import java.util.function.Supplier;

public class DAGlovesItem extends GlovesItem {
    public DAGlovesItem(double punchDamage, String glovesName, Supplier<SoundEvent> glovesSound, Properties properties)
    {
        super(punchDamage, glovesName, glovesSound, properties);
        this.setRenderTexture("deep_aether", glovesName);
    }
}
