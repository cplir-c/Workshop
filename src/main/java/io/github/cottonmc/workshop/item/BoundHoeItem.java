package io.github.cottonmc.workshop.item;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public class BoundHoeItem extends HoeItem {
	public BoundHoeItem(ToolMaterial material, Settings settings) {
		super(material, material.getMiningLevel()-3F, settings);
	}
}
