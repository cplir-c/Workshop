package io.github.cottonmc.workshop.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;


public class ToolFurnaceBlock extends Block implements BlockEntityProvider {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public ToolFurnaceBlock(Block.Settings settings) {
		super(settings);
		getStateFactory().getDefaultState().with(FACING, Direction.NORTH);
	}
	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory) {
		stateFactory.add(FACING);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView view) {
		return new ToolFurnaceBlockEntity();
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		Direction facing = ctx.getPlayerFacing();
		return this.getDefaultState().with(FACING, facing);
	}
}
