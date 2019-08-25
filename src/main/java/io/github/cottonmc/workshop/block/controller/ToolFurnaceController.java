package io.github.cottonmc.workshop.block.controller;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.TranslatableText;

public class ToolFurnaceController extends CottonScreenController {
	public static final RecipeType<?> TOOL_FURNACE = RecipeType.register("tool_furnace");
	public ToolFurnaceController(int syncId, PlayerInventory playerInventory, BlockContext context) {
		super(TOOL_FURNACE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
		
		WGridPanel rootPanel = (WGridPanel) getRootPanel();
		
		rootPanel.add(
				new WLabel(
						new TranslatableText("block.workshop.tool_furnace"),
						WLabel.DEFAULT_TEXT_COLOR
				), 0, 0);
		WItemSlot inputSlot = WItemSlot.of(blockInventory, ToolFurnaceBlockEntity.INPUT);
		WItemSlot fuelSlot = WItemSlot.of(blockInventory, ToolFurnaceBlockEntity.FUEL);
		WItemSlot outputSlot = WItemSlot.of(blockInventory, ToolFurnaceBlockEntity.OUTPUT);
		WItemSlot moldSlot = WItemSlot.of(blockInventory, ToolFurnaceBlockEntity.MOLD);
		rootPanel.add(inputSlot, 0, 0);
		//rootPanel.add(burnTimeSprite, 0, 1);
		rootPanel.add(fuelSlot, 0, 2);
		rootPanel.add(outputSlot, 4, 1);
		rootPanel.add(moldSlot, 2, 1);
		
		rootPanel.add(this.createPlayerInventoryPanel(), 0, 3);
		
		rootPanel.validate(this);
	}
	
	@Override
	public int getCraftingResultSlotIndex() {
		return ToolFurnaceBlockEntity.OUTPUT;
	}

}
