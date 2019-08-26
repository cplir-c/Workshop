package io.github.cottonmc.workshop.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BigBlock extends Block {

	public BigBlock(Settings settings) {
		super(settings);
	}

	public abstract Vec3i[] getBlockerOffsets(Direction facing);

	@Nullable
	abstract EnumProperty<Direction> getFacingProperty();

	public abstract BlockerBlock getBlocker();

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState newState, boolean boolean_1) {
		super.onBlockAdded(state, world, pos, newState, boolean_1);
		Direction dir = getFacingProperty() == null? Direction.NORTH : state.get(getFacingProperty());
		for (Vec3i offset : getBlockerOffsets(dir)) {
			BlockPos toAdd = pos.add(offset);
			world.setBlockState(toAdd, getBlocker().getDefaultState());
		}
	}

	@Override
	public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean boolean_1) {
		super.onBlockRemoved(state, world, pos, newState, boolean_1);
		Direction dir = getFacingProperty() == null? Direction.NORTH : state.get(getFacingProperty());
		for (Vec3i offset : getBlockerOffsets(dir)) {
			BlockPos toRemove = pos.add(offset);
			world.breakBlock(toRemove, false);
		}
	}

	@Override
	public boolean canPlaceAt(BlockState state, ViewableWorld world, BlockPos pos) {
		Direction dir = getFacingProperty() == null? Direction.NORTH : state.get(getFacingProperty());
		for (Vec3i offset : getBlockerOffsets(dir)) {
			BlockPos blocker = pos.add(offset);
			if (!world.getBlockState(blocker).getMaterial().isReplaceable()) return false;
		}
		return true;
	}
}
