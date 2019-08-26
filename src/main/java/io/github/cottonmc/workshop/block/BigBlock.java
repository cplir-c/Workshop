package io.github.cottonmc.workshop.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class BigBlock extends Block {

	public BigBlock(Settings settings) {
		super(settings);
	}

	public abstract Vec3i[] getBlockerOffsets();

	@Nullable
	abstract EnumProperty<Direction> getFacingProperty();

	public abstract Blocker getBlocker();

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState newState, boolean boolean_1) {
		super.onBlockAdded(state, world, pos, newState, boolean_1);
		Direction dir = getFacingProperty() == null? Direction.NORTH : state.get(getFacingProperty());
		for (Vec3i offset : getRotatedVectors(dir)) {
			Vec3i opposite = new Vec3i(offset.getX() * -1, offset.getY() * -1, offset.getZ() * -1);
			BlockPos toAdd = pos.add(offset);
			world.setBlockState(toAdd, getBlocker().getBlockerState(world, pos, state, opposite));
			getBlocker().configure(world, pos, state, opposite);
		}
	}

	@Override
	public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean boolean_1) {
		super.onBlockRemoved(state, world, pos, newState, boolean_1);
		Direction dir = getFacingProperty() == null? Direction.NORTH : state.get(getFacingProperty());
		for (Vec3i offset : getRotatedVectors(dir)) {
			BlockPos toRemove = pos.add(offset);
			world.breakBlock(toRemove, false);
		}
	}

	@Override
	public boolean canPlaceAt(BlockState state, ViewableWorld world, BlockPos pos) {
		Direction dir = getFacingProperty() == null? Direction.NORTH : state.get(getFacingProperty());
		for (Vec3i offset : getRotatedVectors(dir)) {
			BlockPos blocker = pos.add(offset);
			if (!world.getBlockState(blocker).getMaterial().isReplaceable()) return false;
		}
		return true;
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.BLOCK;
	}

	Vec3i[] getRotatedVectors(Direction dir) {
		if (dir == Direction.NORTH) return getBlockerOffsets();
		List<Vec3i> ret = new ArrayList<>();
		if (dir.getAxis() == Direction.Axis.Y) {
			//rotate around the X axis
			for (Vec3i vec : getBlockerOffsets()) {
				int y = vec.getY();
				int z = vec.getZ();
				int angle = dir == Direction.UP? 90 : 270;

				angle *= Math.PI/180.0; //Convert amount to radians

				double theta = Math.atan2(y, z);
				double r = Math.sqrt(z*z+y*y);

				z = (int)(r * Math.cos(theta-angle));
				y = (int)(r * Math.sin(theta-angle));

				ret.add(new Vec3i(vec.getX(), y, z));
			}
		} else {
			//rotate around the Y axis
			for (Vec3i vec : getBlockerOffsets()) {
				int x = vec.getX();
				int z = vec.getZ();
				int angle = dir == Direction.EAST? 90 : dir == Direction.SOUTH? 180 : 270;

				angle *= Math.PI/180.0; //Convert amount to radians

				double theta = Math.atan2(x, z);
				double r = Math.sqrt(z*z+x*x);

				z = (int)(r * Math.cos(theta-angle));
				x = (int)(r * Math.sin(theta-angle));

				ret.add(new Vec3i(x, vec.getY(), z));
			}
		}
		return ret.toArray(new Vec3i[0]);
	}
}
