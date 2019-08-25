package io.github.cottonmc.workshop.block.entity;

import io.github.cottonmc.workshop.block.controller.ToolFurnaceController;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.container.NameableContainerProvider;
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
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Nameable;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public class ToolFurnaceBlockEntity extends BlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider, Tickable, NameableContainerProvider, Inventory, Nameable {
	
	//Constants for clear access to specific slot types
	public static final int INPUT = 0;
	public static final int FUEL = INPUT + 1;
	public static final int MOLD = FUEL + 1;
	public static final int OUTPUT = MOLD + 1;
	//Total size of the inventory
	public static final int INV_SIZE = OUTPUT + 1;
	
	public ToolFurnaceBlockEntity() {
		super(WorkshopBlockEntities.TOOL_FURNACE);
		this.slots = DefaultedList.ofSize(INV_SIZE, ItemStack.EMPTY);
		this.burnTime = 0;
		this.meltTime = 0;
		this.lastRecipe = null;
	}
	
	protected DefaultedList<ItemStack> slots;
	protected int burnTime;
	protected int meltTime;
	protected Recipe<ToolFurnaceBlockEntity> lastRecipe;
	
	@Override
	public int getInvSize() {
		return INV_SIZE;
	}

	@Override
	public boolean isInvEmpty() {
		return this.slots.isEmpty();
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
		// TODO Auto-generated method stub
	}

	@Override
	public void provideRecipeInputs(RecipeFinder finder) {
		finder.addItem(this.slots.get(INPUT));
		finder.addItem(this.slots.get(MOLD));
	}

	@Override
	public void setLastRecipe(Recipe<?> var1) {
		if (this.lastRecipe == null || !this.lastRecipe.equals(var1))
			this.lastRecipe = (Recipe<ToolFurnaceBlockEntity>) var1;
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
		if(dir == Direction.DOWN) {
			if (i != OUTPUT)
				return false;
		} else if(dir == Direction.UP) {
			if (i != INPUT)
				return false; // No inserting into random slots, this is sided
		} else {
			if (i != MOLD && i != FUEL)
				return false;
		}
		return true;
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
	public Container createMenu(int var1, PlayerInventory var2, PlayerEntity var3) {
		return new ToolFurnaceController(var1, var2, (BlockContext) this);
	}

	@Override
	public Text getName() {
		return new TranslatableText("Tool Furnace");
	}

	@Override
	public Text getDisplayName() {
		return getName();
	}
}
