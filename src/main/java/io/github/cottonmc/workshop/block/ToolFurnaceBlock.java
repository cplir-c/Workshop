package io.github.cottonmc.workshop.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Arrays;

import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;


public class ToolFurnaceBlock extends Block implements BlockEntityProvider {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	protected static VoxelShape collisionShape = createShape();
	
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
		Direction facing = ctx.getPlayerFacing().getOpposite();
		return this.getDefaultState().with(FACING, facing);
	}
	
	public static VoxelShape createShape() {
		VoxelShape[] subshapes = new VoxelShape[] {
				Block.createCuboidShape(-2, 0, -1, 16, 1, 1),
				Block.createCuboidShape(-2, 0, 13, 16, 1, 15),
				Block.createCuboidShape(-1, 0, -2, 1, 1, -1),
				Block.createCuboidShape(-1, 0, 1, 15, 1, 13),
				Block.createCuboidShape(-1, 0, 15, 1, 1, 16),
				Block.createCuboidShape(-1, 1, -1, 1, 2, 1),
				Block.createCuboidShape(-1, 1, 13, 1, 2, 15),
				Block.createCuboidShape(0, 1, 1, 14, 14, 13),
				Block.createCuboidShape(0, 2, 0, 1, 14, 1),
				Block.createCuboidShape(0, 2, 13, 14, 14, 14),
				Block.createCuboidShape(1, 1, 0, 15, 2, 1),
				Block.createCuboidShape(1, 1, 13, 15, 2, 14),
				Block.createCuboidShape(1, 4, 0, 2, 14, 1),
				Block.createCuboidShape(1, 14, 1, 2, 15, 13),
				Block.createCuboidShape(1, 15, 2, 2, 22, 12),
				Block.createCuboidShape(1, 21, 1, 13, 22, 2),
				Block.createCuboidShape(1, 21, 12, 13, 22, 13),
				Block.createCuboidShape(2, 7, 0, 3, 14, 1),
				Block.createCuboidShape(2, 14, 1, 13, 15, 2),
				Block.createCuboidShape(2, 14, 12, 13, 15, 13),
				Block.createCuboidShape(2, 15, 1, 12, 21, 2),
				Block.createCuboidShape(2, 15, 12, 12, 21, 13),
				Block.createCuboidShape(3, 8, 0, 5, 14, 1),
				Block.createCuboidShape(5, 9, 0, 14, 14, 1),
				Block.createCuboidShape(9, 8, 0, 14, 9, 1),
				Block.createCuboidShape(11, 7, 0, 14, 8, 1),
				Block.createCuboidShape(12, 4, 0, 14, 7, 1),
				Block.createCuboidShape(12, 14, 2, 13, 22, 12),
				Block.createCuboidShape(13, 0, -2, 15, 1, -1),
				Block.createCuboidShape(13, 0, 15, 15, 1, 16),
				Block.createCuboidShape(13, 1, -1, 15, 2, 0),
				Block.createCuboidShape(13, 1, 14, 15, 2, 15),
				Block.createCuboidShape(13, 2, 0, 14, 4, 1)
		};
		VoxelShape unioned = subshapes[0];
		for (VoxelShape subshape:subshapes)
			unioned = VoxelShapes.union(unioned, subshape);
		unioned = unioned.simplify();
		//unioned.forEachBox((a,b,c,d,e,f) -> System.out.println(Arrays.toString(new double[] {a,b,c,d,e,f})));
		return unioned;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityContext entityContext) {
		return collisionShape;
	}
}
