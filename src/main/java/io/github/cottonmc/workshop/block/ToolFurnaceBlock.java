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
				Block.createCuboidShape(-1, 0, 0, 17, 1, 2),
				Block.createCuboidShape(-1, 0, 14, 17, 1, 16),
				Block.createCuboidShape(0, 0, -1, 2, 1, 0),
				Block.createCuboidShape(0, 0, 2, 16, 1, 14),
				Block.createCuboidShape(0, 0, 16, 2, 1, 17),
				Block.createCuboidShape(0, 1, 0, 2, 2, 2),
				Block.createCuboidShape(0, 1, 14, 2, 2, 16),
				Block.createCuboidShape(1, 1, 2, 15, 14, 14),
				Block.createCuboidShape(1, 2, 1, 2, 14, 2),
				Block.createCuboidShape(1, 2, 14, 15, 14, 15),
				Block.createCuboidShape(2, 1, 1, 16, 2, 2),
				Block.createCuboidShape(2, 1, 14, 16, 2, 15),
				Block.createCuboidShape(2, 4, 1, 3, 14, 2),
				Block.createCuboidShape(2, 14, 2, 3, 15, 14),
				Block.createCuboidShape(2, 15, 3, 3, 22, 13),
				Block.createCuboidShape(2, 21, 2, 14, 22, 3),
				Block.createCuboidShape(2, 21, 13, 14, 22, 14),
				Block.createCuboidShape(3, 7, 1, 4, 14, 2),
				Block.createCuboidShape(3, 14, 2, 14, 15, 3),
				Block.createCuboidShape(3, 14, 13, 14, 15, 14),
				Block.createCuboidShape(3, 15, 2, 13, 21, 3),
				Block.createCuboidShape(3, 15, 13, 13, 21, 14),
				Block.createCuboidShape(4, 8, 1, 6, 14, 2),
				Block.createCuboidShape(6, 9, 1, 15, 14, 2),
				Block.createCuboidShape(10, 8, 1, 15, 9, 2),
				Block.createCuboidShape(12, 7, 1, 15, 8, 2),
				Block.createCuboidShape(13, 4, 1, 15, 7, 2),
				Block.createCuboidShape(13, 14, 3, 14, 22, 13),
				Block.createCuboidShape(14, 0, -1, 16, 1, 0),
				Block.createCuboidShape(14, 0, 16, 16, 1, 17),
				Block.createCuboidShape(14, 1, 0, 16, 2, 1),
				Block.createCuboidShape(14, 1, 15, 16, 2, 16),
				Block.createCuboidShape(14, 2, 1, 15, 4, 2)
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
