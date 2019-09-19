package io.github.cottonmc.workshop.block.controller;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.workshop.Workshop;
import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ToolFurnaceController extends CottonScreenController {
	public static final RecipeType<?> TOOL_FURNACE = RecipeType.register("tool_furnace");
	public ToolFurnaceController(int syncId, PlayerInventory playerInventory, BlockContext context) {
		super(TOOL_FURNACE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
		//TODO: switch to a plain panel once we have the final gui design!
		WPlainPanel rootPanel = new WPlainPanel();
		rootPanel.setBackgroundPainter((left, top, panel) -> {
			ScreenDrawing.rect(new Identifier(Workshop.MODID,"gui/tool_furnaceui"), left, top, rootPanel.getWidth(), rootPanel.getHeight(), 0xffffffff);
		});//                                                                                  111, 72
		this.setRootPanel(rootPanel);
		rootPanel.add(
				new WLabel(
						new TranslatableText("block.workshop.tool_furnace"),
						WLabel.DEFAULT_TEXT_COLOR
				), 0, 0);
        WItemSlot inputSlot = WItemSlot.of(blockInventory, ToolFurnaceBlockEntity.INPUT);
		WItemSlot fuelSlot = WItemSlot.of(blockInventory, ToolFurnaceBlockEntity.FUEL);
		WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, ToolFurnaceBlockEntity.OUTPUT);
		WItemSlot moldSlot = WItemSlot.of(blockInventory, ToolFurnaceBlockEntity.MOLD);
		WBar burnTimeBar = WBar.withConstantMaximum(
				new Identifier(Workshop.MODID,"gui/filled_fire"),
				new Identifier(Workshop.MODID,"gui/filled_fire"),
				0, ToolFurnaceBlockEntity.MAX_FUEL, WBar.Direction.UP);
		rootPanel.add(inputSlot, 14, 10);
		rootPanel.add(burnTimeBar, 15, 29);
		rootPanel.add(fuelSlot, 14, 46);
		rootPanel.add(outputSlot, 73, 23);
		rootPanel.add(moldSlot, 42, 27);
		
		rootPanel.add(this.createPlayerInventoryPanel(), 0, 72);
		
		rootPanel.validate(this);
	}
	
	@Override
	public int getCraftingResultSlotIndex() {
		return ToolFurnaceBlockEntity.OUTPUT;
	}
	
	@Override
	public PropertyDelegate getPropertyDelegate() {
		if (!(this.blockInventory instanceof PropertyDelegate))
			return null;
		return (PropertyDelegate)this.blockInventory;
	}

}
