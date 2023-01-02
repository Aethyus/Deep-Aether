package teamrazor.deepaether.block;

import com.gildedgames.aether.blockentity.SkyrootSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import teamrazor.deepaether.entity.DeepAetherModSignBlockEntity;

public class DeepAetherModWallSignBlock extends WallSignBlock {
    public DeepAetherModWallSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DeepAetherModSignBlockEntity(pos, state);
    }
}