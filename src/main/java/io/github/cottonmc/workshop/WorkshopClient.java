package io.github.cottonmc.workshop;

import io.github.cottonmc.workshop.block.entity.WorkshopBlockEntitiesClient;
import net.fabricmc.api.ClientModInitializer;

public class WorkshopClient implements ClientModInitializer{

	@Override
	public void onInitializeClient() {
		WorkshopBlockEntitiesClient.clinit();
	}

}
