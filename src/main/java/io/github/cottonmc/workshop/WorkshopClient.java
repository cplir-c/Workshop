package io.github.cottonmc.workshop;

import io.github.cottonmc.workshop.block.controller.ToolFurnaceController;
import io.github.cottonmc.workshop.client.screen.ToolFurnaceScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;

public class WorkshopClient implements ClientModInitializer{

	@Override
	public void onInitializeClient() {
		ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier(Workshop.MODID, "tool_furnace"),
				(syncId, identifier, player, buf) -> new ToolFurnaceScreen(
						new ToolFurnaceController(
								syncId, player.inventory,
								BlockContext.create(player.world, buf.readBlockPos())),
						player));
	}

}
