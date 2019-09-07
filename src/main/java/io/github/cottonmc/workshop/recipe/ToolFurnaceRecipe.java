package io.github.cottonmc.workshop.recipe;

import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static io.github.cottonmc.workshop.recipe.WorkshopRecipes.TOOL_FURNACE_RECIPE_SERIALIZER;

import com.google.gson.JsonObject;

import io.github.cottonmc.workshop.Workshop;

public class ToolFurnaceRecipe implements Recipe<ToolFurnaceBlockEntity> {
	
	ItemStack input;
	ItemStack mold;
	ItemStack output;
	
	ToolFurnaceRecipe(ItemStack input, ItemStack mold, ItemStack output){
		this.input = input;
		this.mold = mold;
		this.output = output;
	}
	
	@Override
	public boolean matches(ToolFurnaceBlockEntity be, World world) {
		return be.getInvStack(ToolFurnaceBlockEntity.INPUT).equals(input) && be.getInvStack(ToolFurnaceBlockEntity.MOLD).equals(mold);
	}

	@Override
	public ItemStack craft(ToolFurnaceBlockEntity be) {
		return output;
	}

	@Override
	public boolean fits(int craftingWidth, int craftingHeight) {
		return craftingWidth * craftingHeight <= 2 && craftingWidth != 0 && craftingHeight != 0;
	}

	@Override
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public Identifier getId() {
		return new Identifier(Workshop.MODID, "tool_furnace_recipe_" + Item.getRawId(input.getItem()));
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
