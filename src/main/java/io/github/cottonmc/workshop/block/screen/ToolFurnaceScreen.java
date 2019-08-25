package io.github.cottonmc.workshop.block.screen;

import io.github.cottonmc.cotton.gui.client.CottonScreen;
import io.github.cottonmc.workshop.block.controller.ToolFurnaceController;
import net.minecraft.entity.player.PlayerEntity;

public class ToolFurnaceScreen extends CottonScreen<ToolFurnaceController> {
	public ToolFurnaceScreen(ToolFurnaceController container, PlayerEntity player) {
		super(container, player);
	}
}
