package teamrazor.deepaether.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.init.DAItems;

public class GlowingGrassBlock extends DoublePlantBlock {
    public GlowingGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof ShearsItem) {
            popResource(context.getLevel(), context.getClickedPos(), new ItemStack(DAItems.GLOWING_SPORES.get()));
            return Blocks.TALL_GRASS.defaultBlockState().setValue(HALF, state.getValue(HALF));
        }
        else return super.getToolModifiedState(state, context, toolAction, simulate);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if(state.getValue(DoublePlantBlock.HALF).equals(DoubleBlockHalf.UPPER))
            return 8;
        else return 0;
    }
}