package io.github.cottonmc.workshop.block.entity;

import java.util.Arrays;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ToolFurnaceBlockEntity extends BlockEntity implements Inventory{
	public ToolFurnaceBlockEntity() {
		super(WorkshopBlockEntities.TOOL_FURNACE);
		Arrays.stream(new Object[0]);
	}
	
	protected List<InventorySlot> slots;

	protected static final Text CONTAINER_NAME = new TranslatableText("Tool Furnace");

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getInvSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInvEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getInvStack(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack takeInvStack(int var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeInvStack(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInvStack(int var1, ItemStack var2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity var1) {
		// TODO Auto-generated method stub
		return false;
	}

}
