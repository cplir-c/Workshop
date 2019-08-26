package io.github.cottonmc.workshop.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

public abstract class BigBlock extends Block {

	public BigBlock(Settings settings) {
		super(settings);
	}

	//TODO: support for more blocker locations
	public abstract Vec3i[] getBlockerOffsets(Direction facing);

	public abstract BlockerBlock getBlocker();

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState newState, boolean boolean_1) {
		//TODO: support for more blocker locations
		super.onBlockAdded(state, world, pos, newState, boolean_1);
		world.setBlockState(pos.up(), WorkshopBlocks.BLOCKER.getDefaultState());
	}

	@Override
	public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean boolean_1) {
		//TODO: support for more blocker locations
		super.onBlockRemoved(state, world, pos, newState, boolean_1);
		world.breakBlock(pos.up(), false);
	}

	@Override
	public boolean canPlaceAt(BlockState state, ViewableWorld world, BlockPos pos) {
		//TODO: support for more blocker locations
		return world.getBlockState(pos.up()).getMaterial().isReplaceable();
	}
}
