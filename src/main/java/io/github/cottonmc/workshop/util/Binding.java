package io.github.cottonmc.workshop.util;

import net.minecraft.util.StringIdentifiable;

public enum Binding implements StringIdentifiable {
	GRASS(ColorPalettes.GRASS_ROPE, "grass"),
	VINE(ColorPalettes.VINE_ROPE, "vine"),
	SILK(ColorPalettes.IRON, "silk"); //TODO: get proper color

	private final int color;
	private final String name;
	private Binding(int color, String name) {
		this.color = color;
		this.name = name;
	}

	public int getColor() {
		return color;
	}

	@Override
	public String asString() {
		return name;
	}

	public static Binding forName(String name) {
		for (Binding value : Binding.values()) {
			if (name.equals(value.asString())) {
				return value;
			}
		}
		return Binding.GRASS;
	}
}
