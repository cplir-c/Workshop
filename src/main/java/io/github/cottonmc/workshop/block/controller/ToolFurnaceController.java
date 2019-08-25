package io.github.cottonmc.workshop.block.controller;

import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.TranslatableText;
import static io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity.INV_SIZE;

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
		
		WItemSlot outputSlot = WItemSlot.of(blockInventory, INV_SIZE);
		WItemSlot moldSlot = WItemSlot.of(blockInventory, INV_SIZE + 1);
		rootPanel.add(outputSlot, 4, 2);
		rootPanel.add(moldSlot, 6, 2);
		
		WItemSlot[] inputSlots = new WItemSlot[INV_SIZE];
		double angle = 2*Math.PI/INV_SIZE;
		for(int i = 0; i < INV_SIZE; i++) {
			inputSlots[i] = WItemSlot.of(blockInventory, i);
			rootPanel.add(
					inputSlots[i],
					1 + ((int)(2 * Math.cos(i*angle))),
					1 + ((int)(2 * Math.sin(i*angle))));
		}
		rootPanel.add(this.createPlayerInventoryPanel(), 0, 5);
		
		//rootPanel.add(WSprite);
		
		rootPanel.validate(this);
	}
	
	@Override
	public int getCraftingResultSlotIndex() {
		return INV_SIZE;
	}

}
