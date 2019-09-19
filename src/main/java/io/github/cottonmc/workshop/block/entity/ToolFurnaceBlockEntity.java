package io.github.cottonmc.workshop.block.entity;

import java.util.Objects;

import io.github.cottonmc.workshop.block.WorkshopBlocks;
import io.github.cottonmc.workshop.block.controller.ToolFurnaceController;
import io.github.cottonmc.workshop.recipe.ToolFurnaceRecipe;
import io.github.cottonmc.workshop.recipe.WorkshopRecipes;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class ToolFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider, Tickable, PropertyDelegate {
	public static final int MAX_FUEL = 20/*ticks per item*/*90/*items per lava bucket*/;
	//Constants for clear access to specific slot types
	public static final int INPUT = 0;
	public static final int FUEL = INPUT + 1;
	public static final int MOLD = FUEL + 1;
	public static final int OUTPUT = MOLD + 1;
	//Total size of the inventory
	public static final int INV_SIZE = OUTPUT + 1;
	
	protected DefaultedList<ItemStack> slots;
	protected int burnTime;
	protected int meltTime;
	protected ToolFurnaceRecipe lastRecipe;

	public ToolFurnaceBlockEntity() {
		super(WorkshopBlocks.TOOL_FURNACE_BE);
		this.slots = DefaultedList.ofSize(INV_SIZE, ItemStack.EMPTY);
		this.burnTime = 0;
		this.meltTime = 0;
		this.lastRecipe = null;
	}
	
	@Override
	public int getInvSize() {
		return INV_SIZE;
	}

	@Override
	public boolean isInvEmpty() {
		if (this.slots == null || this.slots.isEmpty())
			return true;
		for (ItemStack stack: this.slots)
			if(!stack.isEmpty())
				return false;
		return true;
	}

	@Override
	public ItemStack getInvStack(int i) {
		return this.slots.get(i);
	}

	@Override
	public ItemStack takeInvStack(int slotIndex, int requestSize) {
		ItemStack slotAtIndex = this.slots.get(slotIndex);
		
		int internalSize = slotAtIndex.getCount();
		
		if(internalSize >= requestSize) {
			return slotAtIndex.split(requestSize);
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
		this.slots.set(i, stack);
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity var1) {
		return true;
	}

	@Override
	public void clear() {
		for (int i = 0; i < INV_SIZE; i++)
			this.slots.set(i, ItemStack.EMPTY);
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
		if (this.lastRecipe != null && this.lastRecipe.matches(this, world)) {
			ItemStack result = this.lastRecipe.craft(this);
			this.slots.set(INPUT, ItemStack.EMPTY);
			this.slots.set(OUTPUT, result);
		} else {
			this.lastRecipe = this.world.getRecipeManager()
					.getFirstMatch(WorkshopRecipes.TOOL_FURNACE, this, world)
					.orElse(null);
			this.outputRecipeResult();
		}
	}

	@Override
	public void provideRecipeInputs(RecipeFinder finder) {
		finder.addItem(this.slots.get(INPUT));
		finder.addItem(this.slots.get(MOLD));
	}

	@Override
	public void setLastRecipe(Recipe<?> recipe) {
		if ((!Objects.equals(this.lastRecipe, recipe)) && recipe instanceof ToolFurnaceRecipe)
			this.lastRecipe = (ToolFurnaceRecipe) recipe;
	}

	@Override
	public Recipe<?> getLastRecipe() {
		return this.lastRecipe;
	}

	@Override
	public int[] getInvAvailableSlots(Direction var1) {
		if(var1 == Direction.DOWN)
			return new int[] {OUTPUT};
		
		if(var1 == Direction.UP)
			return new int[] {INPUT};
		
		return new int[] {FUEL, MOLD};
	}
	
	public static boolean canAccessSide(int i, Direction dir) {
		if(dir == Direction.DOWN)
			return i == OUTPUT;
		
		if(dir == Direction.UP)
			return i == INPUT; // No inserting into random slots, this is sided

        return i == MOLD || i == FUEL;
	}
	
	@Override
	public boolean canInsertInvStack(int i, ItemStack var2, Direction var3) {
		//Check i
		if (!canAccessSide(i, var3))
			return false;
		// Check if the new itemStack can fit on the internal one
		ItemStack internalSlot = this.slots.get(i);
		return internalSlot.isEmpty()
			|| (
					internalSlot.isStackable()
					&& internalSlot.isItemEqual(var2)
					&& (internalSlot.getCount() + var2.getCount()) < internalSlot.getMaxCount()
			);
	}

	@Override
	public boolean canExtractInvStack(int i, ItemStack var2, Direction var3) {
		if(!canAccessSide(i, var3))
			return false;
		
		ItemStack internalSlot = this.slots.get(i);
		return var2.isEmpty()
			|| (
				var2.isItemEqual(internalSlot)
				&& var2.getCount() <= internalSlot.getCount()
			);
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText("tool_furnace");
	}

	@Override
	public Container createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new ToolFurnaceController(syncId, inv, BlockContext.create(world, pos));
	}

	@Override
	protected Container createContainer(int syncId, PlayerInventory player) {
		return new ToolFurnaceController(syncId, player, BlockContext.create(world, pos));
	}
	
	/*
	public PropertyDelegate getPropertyDelegate() {
		return new PropertyDelegate() {
	//*/

			@Override
			public int get(int n) {
				if (n == 0)
					return ToolFurnaceBlockEntity.this.burnTime;
				else
					return ToolFurnaceBlockEntity.this.meltTime;
			}

			@Override
			public void set(int n, int value) {
				if (n == 0)
					ToolFurnaceBlockEntity.this.burnTime = value;
				else
					ToolFurnaceBlockEntity.this.meltTime = value;
			}

			@Override
			public int size() {
				return 2;
			}
			
	/*	
	    }
	}
	//*/
}
