package teamrazor.deepaether.entity;

import org.spongepowered.asm.mixin.Unique;


public interface MoaBonusJump {

    @Unique
    void deep_Aether$setBonusJumps(int jumps);
}
