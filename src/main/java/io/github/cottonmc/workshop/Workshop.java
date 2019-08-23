package io.github.cottonmc.workshop;

import io.github.cottonmc.workshop.block.WorkshopBlocks;
import io.github.cottonmc.workshop.item.WorkshopItems;
import net.fabricmc.api.ModInitializer;

public class Workshop implements ModInitializer {
	public static final String MODID = "workshop";

	@Override
	public void onInitialize() {
		WorkshopItems.init();
		WorkshopBlocks.init();
	}
}
