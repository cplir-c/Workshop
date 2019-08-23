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

public class WorkshopBlockEntities {

	public static BlockEntityType<ToolFurnaceBlockEntity> TOOL_FURNACE;
	
	protected static <T extends BlockEntity> BlockEntityType<T> register(String name, Supplier<T> tConstructor, Block... blocks){
		
		BlockEntityType.Builder<T> builder = BlockEntityType.Builder.create(tConstructor, blocks);
		BlockEntityType<T> type = builder.build(null);

		Registry.register(Registry.BLOCK_ENTITY, new Identifier(Workshop.MODID, name), type);
		
		return type;
	}
	
	public static void init() {
		TOOL_FURNACE = WorkshopBlockEntities.register("tool_furnace", ToolFurnaceBlockEntity::new, WorkshopBlocks.TOOL_FURNACE);
	}
}
