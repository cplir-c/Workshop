package io.github.cottonmc.workshop.block;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;

public class ToolFurnaceBlock extends AbstractFurnaceBlock {

	public ToolFurnaceBlock(Block.Settings blockSettings) {
		super(blockSettings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView var1) {
		return new ToolFurnaceBlockEntity();
	}

	@Override
	protected void openContainer(World var1, BlockPos var2, PlayerEntity var3) {
		// TODO Auto-generated method stub
	}

}
