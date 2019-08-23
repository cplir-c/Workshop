package io.github.cottonmc.workshop.block.entity;

import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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
	
	public ToolFurnaceBlockEntity() {
		super(WorkshopBlockEntities.TOOL_FURNACE);
		slots = new ItemStack[INV_SIZE];
		this.clear();
	}

	protected ItemStack[] slots;
	public static final int INV_SIZE = 5;
	
	@Override
	public int getInvSize() {
		return INV_SIZE;
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
			throw new AssertionError("Either you're using this wrong, or I guessed this method wrong.");
		
		ItemStack stack;
		for (int i = j; i < k; i++) {
			stack = slots[i];
			if(!stack.isEmpty()) {
				slots[i] = ItemStack.EMPTY;
				return stack;
			}
		}
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
		for(int i = 0; i < INV_SIZE; i++)
			slots[i] = ItemStack.EMPTY;
	}

	@Override
	public void tick() {
		if(burnTime <= 0)
			return;
		burnTime--;
		
		if(meltTime <= 0)
			return;
		
		if (--meltTime <= 0)
			outputRecipeResult();
	}

	private void outputRecipeResult() {
		// TODO Auto-generated method stub
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
	public boolean canInsertInvStack(int i, ItemStack var2, Direction var3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractInvStack(int i, ItemStack var2, Direction var3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText("Tool Furnace");
	}

	@Override
	protected Container createContainer(int i, PlayerInventory var2) {
		// TODO Auto-generated method stub
		return null;
	}
}
