package io.github.cottonmc.workshop.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.NameableContainerProvider;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

public class ToolFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider, Tickable, InventoryProvider, NameableContainerProvider {
	protected int burnTime;
	protected int meltTime;
	
	public ToolFurnaceBlockEntity() {
		super(WorkshopBlockEntities.TOOL_FURNACE);
		slots = new ItemStack[INV_SIZE];
		this.clear();
	}
	
	protected ItemStack output;
	protected ItemStack mold;
	protected ItemStack[] slots;
	//the rest are inputs
	public static final int INV_SIZE = 1;
	
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
		if(i >= INV_SIZE) {
			i -= INV_SIZE;
			if (i <= 0)
				return output;
			else if (i == 1)
				return mold;
		}
		return slots[i];
	}

	@Override
	public ItemStack takeInvStack(int j, int k) {
		if(j > k)
			throw new AssertionError("Either you're using this wrong, or I guessed this method wrong.");
		
		ItemStack stack;
		int bigK = -1;
		if (k >= INV_SIZE) {
			bigK = k;
			k = INV_SIZE;
		}
		for (int i = j; i < k; i++) {
			stack = slots[i];
			if(!stack.isEmpty()) {
				slots[i] = ItemStack.EMPTY;
				return stack;
			}
		}
		if (bigK > -1)
			for (int i = k; i < k; i++) {
				stack = getInvStack(i);
				if (!stack.isEmpty()) {
					setInvStack(i, ItemStack.EMPTY);
					return stack;
				}
			}
		
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeInvStack(int i) {
		ItemStack found = getInvStack(i);
		setInvStack(i, ItemStack.EMPTY);
		return found;
	}

	@Override
	public void setInvStack(int i, ItemStack stack) {
		if(i >= INV_SIZE) {
			i -= INV_SIZE;
			if (i <= 0)
				output = stack;
			else if (i == 1)
				mold = stack;
		}
		slots[i] = stack;
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity var1) {
		return true; //NotImplemented
	}

	@Override
	public void clear() {
		for(int i = 0; i < INV_SIZE; i++)
			slots[i] = ItemStack.EMPTY;
		output = ItemStack.EMPTY;
		mold = ItemStack.EMPTY;
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
		if(var1 == Direction.DOWN)
			return new int[] {INV_SIZE + 1};
		
		if(var1 == Direction.UP)
			return new int[] {INV_SIZE};
		
		int[] slots = new int[INV_SIZE];
		for (int i = 0; i < INV_SIZE; i++)
			slots[i] = i;
		
		return slots;
	}

	@Override
	public boolean canInsertInvStack(int i, ItemStack var2, Direction var3) {
		if(var3 == Direction.DOWN)
			return false;
		
		if(var3 == Direction.UP){
			for(ItemStack stack:slots)
				if(stack.isEmpty())
					return true;
			
			for(ItemStack stack:slots)
				if(stack.isStackable() && stack.isItemEqual(var2)
				  && (stack.getMaxCount() - stack.getCount()) < var2.getCount())
					return true;
			
			return false;
		}
		
		// var3 is a side
		return mold.isEmpty() || ( mold.isStackable() && mold.isItemEqual(var2)
			&& (mold.getMaxCount() - mold.getCount()) < var2.getCount());
	}

	@Override
	public boolean canExtractInvStack(int i, ItemStack var2, Direction var3) {
		if (var3 == Direction.DOWN)
			return !output.isEmpty() && var2.equals(output);
		
		if (var3 == Direction.UP) {
			for (ItemStack slot:slots)
				if(!slot.isEmpty() && var2.equals(slot))
					return true;
			
			return false;
		}
			
		return !mold.isEmpty() && var2.equals(mold);
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
	
	

	@Override
	public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos) {
		return (SidedInventory) world.getBlockEntity(pos);
	}
}
