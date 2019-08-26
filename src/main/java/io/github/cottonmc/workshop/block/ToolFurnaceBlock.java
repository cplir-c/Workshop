package io.github.cottonmc.workshop.block;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;
import io.github.cottonmc.workshop.Workshop;
import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;


public class ToolFurnaceBlock extends Block implements BlockEntityProvider, InventoryProvider {
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
				//main box
				Block.createCuboidShape(0, 0, 0, 16, 1, 16),
				Block.createCuboidShape(1, 1, 1, 15, 14, 15),

				//bottom-half upper corners
				Block.createCuboidShape(0, 1, 0, 2, 2, 2),
				Block.createCuboidShape(14, 1, 0, 16, 2, 2),
				Block.createCuboidShape(14, 1, 14, 16, 2, 16),
				Block.createCuboidShape(0, 1, 14, 2, 2, 16),

				//bottom-half lower corners
				Block.createCuboidShape(0, 0, -1, 2, 1, 0),
				Block.createCuboidShape(14, 0, -1, 16, 1, 0),
				Block.createCuboidShape(16, 0, 0, 17, 1, 2),
				Block.createCuboidShape(16, 0, 14, 17, 1, 16),
				Block.createCuboidShape(0, 0, 16, 2, 1, 17),
				Block.createCuboidShape(14, 0, 16, 16, 1, 17),
				Block.createCuboidShape(-1, 0, 0, 0, 1, 2),
				Block.createCuboidShape(-1, 0, 14, 0, 1, 16),

				//top-half walls
				Block.createCuboidShape(3, 14, 2, 13, 22, 3),
				Block.createCuboidShape(13, 14, 3, 14, 22, 13),
				Block.createCuboidShape(3, 14, 13, 13, 22, 14),
				Block.createCuboidShape(2, 14, 3, 3, 22, 13),

				//top-half corners
				Block.createCuboidShape(2, 14, 2, 3, 15, 3),
				Block.createCuboidShape(2, 21, 2, 3, 22, 3),
				Block.createCuboidShape(13, 14, 2, 14, 15, 3),
				Block.createCuboidShape(13, 21, 2, 14, 22, 3),
				Block.createCuboidShape(13, 14, 13, 14, 15, 14),
				Block.createCuboidShape(13, 21, 13, 14, 22, 14),
				Block.createCuboidShape(2, 14, 13, 3, 15, 14),
				Block.createCuboidShape(2, 21, 13, 3, 22, 14)
		};
		VoxelShape unioned = subshapes[0];
		for (VoxelShape subshape : subshapes) unioned = VoxelShapes.union(unioned, subshape);
		return unioned.simplify();
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, EntityContext ctx) {
		return collisionShape;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, EntityContext ctx) {
		return collisionShape;
	}

	@Override
	public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
		if (!world.isClient) {
			BlockEntity be = world.getBlockEntity(pos);
			if (be instanceof ToolFurnaceBlockEntity) {
				ContainerProviderRegistry.INSTANCE.openContainer(
						new Identifier(Workshop.MODID, "tool_furnace"),
						player, (buf)-> buf.writeBlockPos(pos));
			}
		}
		return true;
	}

	@Override
	public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos) {
		return (SidedInventory)world.getBlockEntity(pos);
	}
}
