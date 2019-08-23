package io.github.cottonmc.workshop.block.entity;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public class ToolFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider, Tickable {
	protected int burnTime;
	protected int meltTime;
	
	protected ToolFurnaceBlockEntity(BlockEntityType<?> blockEntityType_1) {
		super(blockEntityType_1);
		slots = new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
	}

	protected ItemStack[] slots;

	@Override
	public int getInvSize() {
		return 3;
	}

	@Override
	public boolean isInvEmpty() {
		for (ItemStack stack:slots)
			if(!stack.isEmpty())
				return false;
		
		return true;
	}

	@Override
	public ItemStack getInvStack(int i) {
		return slots[i];
	}

	@Override
	public ItemStack takeInvStack(int j, int k) {
		if(j > k)
			throw new AssertionError();
		int i = j;
		for (; i < k; i++)
			if(!slots[i].isEmpty())
				return removeInvStack(i);
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeInvStack(int i) {
		ItemStack found = slots[i];
		slots[i] = ItemStack.EMPTY;
		return found;
	}

	@Override
	public void setInvStack(int i, ItemStack stack) {
		slots[i] = stack;
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity var1) {
		return false; //NotImplemented
	}

	@Override
	public void clear() {
		for(int i = 0; i < 3; i++)
			slots[i] = ItemStack.EMPTY;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void provideRecipeInputs(RecipeFinder var1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastRecipe(Recipe<?> var1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Recipe<?> getLastRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getInvAvailableSlots(Direction var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertInvStack(int var1, ItemStack var2, Direction var3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractInvStack(int var1, ItemStack var2, Direction var3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Text getContainerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Container createContainer(int var1, PlayerInventory var2) {
		// TODO Auto-generated method stub
		return null;
	}
}
