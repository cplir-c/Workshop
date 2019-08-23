package io.github.cottonmc.workshop.block.entity;

import java.util.function.Supplier;

import io.github.cottonmc.workshop.block.WorkshopBlocks;
import io.github.cottonmc.workshop.Workshop;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class WorkshopBlockEntities {
	private WorkshopBlockEntities() {}
	
	public static final BlockEntityType<ToolFurnaceBlockEntity> TOOL_FURNACE;
	
	protected static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(Supplier<T> tConstructor, Block blockWithEntity){
		if (blockWithEntity == null || tConstructor == null)
			throw new NullPointerException();
		
		BlockEntityType.Builder<T> builder = BlockEntityType.Builder.<T>create(tConstructor, blockWithEntity);
		BlockEntityType<T> entityType = builder.build(null);
		
		return entityType;
	}
	
	static {
		TOOL_FURNACE = WorkshopBlockEntities.<ToolFurnaceBlockEntity>createBlockEntityType(ToolFurnaceBlockEntity::new, WorkshopBlocks.TOOL_FURNACE);
	}
	
	public static void init() {
		Registry.register(Registry.BLOCK_ENTITY, new Identifier(Workshop.MODID, "tool_furnace"), TOOL_FURNACE);
	}
}
