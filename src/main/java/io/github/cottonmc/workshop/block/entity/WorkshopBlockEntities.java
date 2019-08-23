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
	
	protected static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(Supplier<T> tConstructor, String identifier, Block blockWithEntity){
		if (blockWithEntity == null || identifier == null || tConstructor == null)
			throw new NullPointerException();
		if (!(blockWithEntity instanceof BlockWithEntity))
			throw new IllegalArgumentException();
		
		BlockEntityType.Builder<T> builder = BlockEntityType.Builder.<T>create(tConstructor, blockWithEntity);
		BlockEntityType<T> entityType = builder.build(null);
		
		return Registry.register(Registry.BLOCK_ENTITY, new Identifier(Workshop.MODID, identifier), entityType);
	}
	
	static {
		TOOL_FURNACE = WorkshopBlockEntities.<ToolFurnaceBlockEntity>createBlockEntityType(ToolFurnaceBlockEntity::new, "tool_furnace", WorkshopBlocks.TOOL_FURNACE);
	}
}
