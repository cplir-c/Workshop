package io.github.cottonmc.workshop.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public interface Blocker {
	BlockState getBlockerState(World world, BlockPos controllerPos, BlockState controllerState, Vec3i offsetToController);

	void configure(World world, BlockPos controllerPos, BlockState controllerState, Vec3i offsetToController);

	BlockPos getControllerPosition(World world, BlockPos pos, BlockState state);
}
