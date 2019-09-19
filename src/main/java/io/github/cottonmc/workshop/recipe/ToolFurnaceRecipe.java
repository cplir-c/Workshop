package io.github.cottonmc.workshop.recipe;

import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import java.util.concurrent.atomic.AtomicLong;

import static io.github.cottonmc.workshop.recipe.WorkshopRecipes.TOOL_FURNACE_RECIPE_SERIALIZER;

import com.google.gson.JsonObject;

import io.github.cottonmc.workshop.Workshop;

public class ToolFurnaceRecipe implements Recipe<ToolFurnaceBlockEntity> {
	final int meltTime;
	final Ingredient input;
	final ItemStack mold;
	final ItemStack output;
	final Identifier id;
	
	ToolFurnaceRecipe(Ingredient input, ItemStack mold, ItemStack output){
		this(input, mold, output, 20);
	}
	ToolFurnaceRecipe(Ingredient input, ItemStack mold, ItemStack output, int meltTime) {
		this(input, mold, output, meltTime, 
			new Identifier(Workshop.MODID,
			  "tool_furnace_recipe_" + Item.getRawId(output.getItem())));
	}
	ToolFurnaceRecipe(Ingredient input, ItemStack mold, ItemStack output,  int meltTime, Identifier id) {
		synchronized(this) {
			AtomicLong j = (AtomicLong)(Object)(Long)9L;
		}
		this.input = input;
		this.mold = mold;
		this.output = output;
		this.id = id;
		this.meltTime = meltTime;
	}
	
	@Override
	public boolean matches(ToolFurnaceBlockEntity be, World world) {
		return input.test(be.getInvStack(ToolFurnaceBlockEntity.INPUT)) && be.getInvStack(ToolFurnaceBlockEntity.MOLD).equals(mold) && be.canInsertInvStack(ToolFurnaceBlockEntity.OUTPUT, this.output, Direction.DOWN);
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
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return TOOL_FURNACE_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<ToolFurnaceRecipe> getType() {
		return WorkshopRecipes.TOOL_FURNACE;
	}
	
	static class ToolFurnaceRecipeSerializer implements RecipeSerializer<ToolFurnaceRecipe>{

		@Override
		public ToolFurnaceRecipe read(Identifier id, JsonObject json) {
			int meltTime = JsonHelper.getInt(json, "meltTime");
			Ingredient input = Ingredient.fromJson(JsonHelper.getObject(json, "input"));
			ItemStack mold = new ItemStack(JsonHelper.asItem(json, "mold"));
			ItemStack output = new ItemStack(JsonHelper.asItem(json, "output"));
			return new ToolFurnaceRecipe(input, mold, output, meltTime, id);
		}

		@Override
		public ToolFurnaceRecipe read(Identifier id, PacketByteBuf packet) {
			int meltTime = packet.readInt();
			Ingredient input = Ingredient.fromPacket(packet);
			ItemStack mold = packet.readItemStack();
			ItemStack output = packet.readItemStack();
			return new ToolFurnaceRecipe(input, mold, output, meltTime, id);
		}

		@Override
		public void write(PacketByteBuf packet, ToolFurnaceRecipe recipe) {
			packet.writeInt(recipe.meltTime);
			recipe.input.write(packet);
			packet.writeItemStack(recipe.mold);
			packet.writeItemStack(recipe.output);
		}
		
	}
}
