package io.github.cottonmc.workshop.block;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;


public class ToolFurnaceBlock extends Block implements BlockEntityProvider {
	public static final DirectionProperty FACING = AbstractFurnaceBlock.FACING;
	public static final BooleanProperty LIT = AbstractFurnaceBlock.LIT;
	
	public ToolFurnaceBlock(Block.Settings blockSettings) {
		super(blockSettings);
		getStateFactory().getDefaultState().with(FACING, Direction.NORTH).with(LIT, false);
	}
	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory) {
		stateFactory.add(FACING);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView var1) {
		return new ToolFurnaceBlockEntity();
	}
	
	@Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        return world.setBlockState(pos, WorkshopBlocks.TOOL_FURNACE.getDefaultState().with(FACING, player.getHorizontalFacing()));
    }
}
