package io.github.cottonmc.workshop.recipe;

import io.github.cottonmc.workshop.Workshop;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WorkshopRecipes {
	public static RecipeType<ToolFurnaceRecipe> TOOL_FURNACE;
	public static RecipeSerializer<ToolFurnaceRecipe> TOOL_FURNACE_RECIPE_SERIALIZER;

	public static void init() {
		TOOL_FURNACE = register("tool_furnace");
	}

	public static <T extends Recipe<?>> RecipeType<T> register(String id) {
		return Registry.register(Registry.RECIPE_TYPE, new Identifier(Workshop.MODID, id), new RecipeType<T>() {
			public String toString() {
				return id;
			}
		});
		
	}

	public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S serializer) {
		return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Workshop.MODID, name), serializer);
	}

}
