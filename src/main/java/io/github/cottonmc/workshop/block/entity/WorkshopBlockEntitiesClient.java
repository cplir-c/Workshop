package io.github.cottonmc.workshop.block.entity;

import io.github.cottonmc.workshop.Workshop;
import io.github.cottonmc.workshop.block.controller.ToolFurnaceController;
import io.github.cottonmc.workshop.block.screen.ToolFurnaceScreen;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;
import static io.github.cottonmc.workshop.block.entity.WorkshopBlockEntities.TOOL_FURNACE_ID;

public abstract interface WorkshopBlockEntitiesClient {
	public static void clinit() {
		ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier(Workshop.MODID, TOOL_FURNACE_ID),
				(syncId, identifier, player, buf) -> new ToolFurnaceScreen(
						new ToolFurnaceController(
								syncId, player.inventory, 
								BlockContext.create(player.world, buf.readBlockPos())),
						player));
	}
}
