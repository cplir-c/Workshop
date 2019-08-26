package io.github.cottonmc.workshop.recipe;

import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ToolFurnaceRecipe implements Recipe<ToolFurnaceBlockEntity> {
	//TODO: implement

	@Override
	public boolean matches(ToolFurnaceBlockEntity be, World world) {
		return false;
	}

	@Override
	public ItemStack craft(ToolFurnaceBlockEntity be) {
		return null;
	}

	@Override
	public boolean fits(int i, int i1) {
		return false;
	}

	@Override
	public ItemStack getOutput() {
		return null;
	}

	@Override
	public Identifier getId() {
		return null;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return null;
	}

	@Override
	public RecipeType<?> getType() {
		return null;
	}
}
