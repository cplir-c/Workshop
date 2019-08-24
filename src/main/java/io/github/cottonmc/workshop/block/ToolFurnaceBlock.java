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
			Block.createCuboidShape(-2, 0, -1, -1, 1, 0),
			Block.createCuboidShape(-2, 0, 0, -1, 1, 1),
			Block.createCuboidShape(-2, 0, 13, -1, 1, 14),
			Block.createCuboidShape(-2, 0, 14, -1, 1, 15),
			Block.createCuboidShape(-1, 0, -2, 0, 1, -1),
			Block.createCuboidShape(-1, 0, -1, 0, 1, 0),
			Block.createCuboidShape(-1, 0, 0, 0, 1, 1),
			Block.createCuboidShape(-1, 0, 1, 0, 1, 13),
			Block.createCuboidShape(-1, 0, 13, 0, 1, 14),
			Block.createCuboidShape(-1, 0, 14, 0, 1, 15),
			Block.createCuboidShape(-1, 0, 15, 0, 1, 16),
			Block.createCuboidShape(-1, 1, -1, 0, 2, 0),
			Block.createCuboidShape(-1, 1, 0, 0, 2, 1),
			Block.createCuboidShape(-1, 1, 13, 0, 2, 14),
			Block.createCuboidShape(-1, 1, 14, 0, 2, 15),
			Block.createCuboidShape(0, 0, -2, 1, 1, -1),
			Block.createCuboidShape(0, 0, -1, 1, 1, 0),
			Block.createCuboidShape(0, 0, 0, 14, 2, 1),
			Block.createCuboidShape(0, 0, 1, 1, 2, 14),
			Block.createCuboidShape(0, 0, 14, 1, 1, 15),
			Block.createCuboidShape(0, 0, 15, 1, 1, 16),
			Block.createCuboidShape(0, 1, -1, 1, 2, 0),
			Block.createCuboidShape(0, 1, 14, 1, 2, 15),
			Block.createCuboidShape(0, 2, 0, 1, 4, 1),
			Block.createCuboidShape(0, 2, 1, 14, 14, 14),
			Block.createCuboidShape(0, 4, 0, 1, 8, 1),
			Block.createCuboidShape(0, 8, 0, 5, 9, 1),
			Block.createCuboidShape(0, 9, 0, 14, 14, 1),
			Block.createCuboidShape(1, 0, -1, 13, 1, 0),
			Block.createCuboidShape(1, 0, 1, 2, 4, 13),
			Block.createCuboidShape(1, 0, 13, 2, 2, 14),
			Block.createCuboidShape(1, 0, 14, 13, 1, 15),
			Block.createCuboidShape(1, 4, 0, 2, 6, 1),
			Block.createCuboidShape(1, 6, 0, 2, 8, 1),
			Block.createCuboidShape(1, 14, 1, 2, 15, 2),
			Block.createCuboidShape(1, 14, 2, 2, 22, 12),
			Block.createCuboidShape(1, 14, 12, 2, 15, 13),
			Block.createCuboidShape(1, 21, 1, 2, 22, 2),
			Block.createCuboidShape(1, 21, 12, 2, 22, 13),
			Block.createCuboidShape(2, 0, 1, 12, 2, 14),
			Block.createCuboidShape(2, 2, 12, 12, 3, 13),
			Block.createCuboidShape(2, 3, 12, 12, 4, 13),
			Block.createCuboidShape(2, 4, 1, 3, 7, 13),
			Block.createCuboidShape(2, 7, 0, 3, 8, 1),
			Block.createCuboidShape(2, 14, 1, 12, 22, 2),
			Block.createCuboidShape(2, 14, 12, 12, 22, 13),
			Block.createCuboidShape(3, 4, 12, 11, 5, 13),
			Block.createCuboidShape(3, 5, 11, 11, 6, 12),
			Block.createCuboidShape(3, 6, 3, 11, 7, 4),
			Block.createCuboidShape(3, 6, 4, 11, 7, 5),
			Block.createCuboidShape(3, 6, 5, 11, 7, 6),
			Block.createCuboidShape(3, 6, 6, 5, 7, 8),
			Block.createCuboidShape(3, 6, 8, 11, 7, 9),
			Block.createCuboidShape(3, 6, 9, 11, 7, 10),
			Block.createCuboidShape(3, 6, 10, 11, 7, 11),
			Block.createCuboidShape(3, 7, 1, 4, 8, 10),
			Block.createCuboidShape(4, 7, 2, 10, 8, 3),
			Block.createCuboidShape(4, 8, 1, 10, 9, 2),
			Block.createCuboidShape(5, 5, 6, 6, 7, 8),
			Block.createCuboidShape(6, 5, 5, 8, 6, 6),
			Block.createCuboidShape(6, 5, 8, 8, 6, 9),
			Block.createCuboidShape(8, 5, 6, 9, 7, 8),
			Block.createCuboidShape(9, 6, 6, 11, 7, 8),
			Block.createCuboidShape(9, 8, 0, 14, 9, 1),
			Block.createCuboidShape(10, 7, 1, 11, 8, 10),
			Block.createCuboidShape(11, 4, 1, 12, 7, 13),
			Block.createCuboidShape(11, 7, 0, 12, 8, 1),
			Block.createCuboidShape(12, 0, 1, 13, 4, 13),
			Block.createCuboidShape(12, 0, 13, 13, 2, 14),
			Block.createCuboidShape(12, 4, 0, 13, 6, 1),
			Block.createCuboidShape(12, 6, 0, 13, 8, 1),
			Block.createCuboidShape(12, 14, 1, 13, 15, 2),
			Block.createCuboidShape(12, 14, 2, 13, 22, 12),
			Block.createCuboidShape(12, 14, 12, 13, 15, 13),
			Block.createCuboidShape(12, 21, 1, 13, 22, 2),
			Block.createCuboidShape(12, 21, 12, 13, 22, 13),
			Block.createCuboidShape(13, 0, -2, 14, 1, -1),
			Block.createCuboidShape(13, 0, -1, 14, 1, 0),
			Block.createCuboidShape(13, 0, 1, 14, 2, 14),
			Block.createCuboidShape(13, 0, 14, 14, 1, 15),
			Block.createCuboidShape(13, 0, 15, 14, 1, 16),
			Block.createCuboidShape(13, 1, -1, 14, 2, 0),
			Block.createCuboidShape(13, 1, 14, 14, 2, 15),
			Block.createCuboidShape(13, 2, 0, 14, 4, 1),
			Block.createCuboidShape(13, 4, 0, 14, 8, 1),
			Block.createCuboidShape(14, 0, -2, 15, 1, -1),
			Block.createCuboidShape(14, 0, -1, 15, 1, 0),
			Block.createCuboidShape(14, 0, 0, 15, 1, 1),
			Block.createCuboidShape(14, 0, 1, 15, 1, 13),
			Block.createCuboidShape(14, 0, 13, 15, 1, 14),
			Block.createCuboidShape(14, 0, 14, 15, 1, 15),
			Block.createCuboidShape(14, 0, 15, 15, 1, 16),
			Block.createCuboidShape(14, 1, -1, 15, 2, 0),
			Block.createCuboidShape(14, 1, 0, 15, 2, 1),
			Block.createCuboidShape(14, 1, 13, 15, 2, 14),
			Block.createCuboidShape(14, 1, 14, 15, 2, 15),
			Block.createCuboidShape(15, 0, -1, 16, 1, 0),
			Block.createCuboidShape(15, 0, 0, 16, 1, 1),
			Block.createCuboidShape(15, 0, 13, 16, 1, 14),
			Block.createCuboidShape(15, 0, 14, 16, 1, 15)
		};
		VoxelShape pedestal = Block.createCuboidShape(0,0,0,16,20,16);
		VoxelShape unioned = subshapes[0];
		for (VoxelShape subshape:subshapes) {
			unioned = VoxelShapes.union(unioned, subshape);
		}
		unioned.simplify();
		unioned.forEachBox((a,b,c,d,e,f) -> System.out.println(Arrays.toString(new double[] {a,b,c,d,e,f})));
		return unioned;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityContext entityContext) {
		return collisionShape;
	}
}
