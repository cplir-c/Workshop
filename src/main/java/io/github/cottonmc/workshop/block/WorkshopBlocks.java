package io.github.cottonmc.workshop.block;

import java.lang.reflect.Field;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class WorkshopBlocks {
    private WorkshopBlocks() {}
    
    /** This block is for making molds. */
    public static final Block MOLD_TABLE = new Block(
    		FabricBlockSettings.of(Material.WOOD, MaterialColor.BROWN)
    		.build());
    
    /** These blocks are for crafting tool parts and materials. */
    public static final Block WOOD_CUTTING_TABLE = new Block(Block.Settings.copy(MOLD_TABLE));
    public static final Block TOOL_FURNACE = new Block(Block.Settings.copy(Blocks.FURNACE));
    public static final Block TANNING_CAULDRON = new Block(Block.Settings.copy(Blocks.CAULDRON));
    public static final Block METAL_ANVIL = new Block(Block.Settings.copy(Blocks.ANVIL));
    
    /** These blocks are for enchanted smelting and smithing, respectively. */
    public static final Block ENCHANTED_FURNACE = new Block(Block.Settings.copy(Blocks.BLAST_FURNACE));
    public static final Block ENCHANTED_ANVIL = new Block(Block.Settings.copy(METAL_ANVIL));
    
    public static void init() {
    	Field[] blocks = WorkshopBlocks.class.getFields();
    	Item.Settings craftingTableGroup = new Item.Settings().group(Items.CRAFTING_TABLE.getGroup());
    	try {
    		for (Field block_field:blocks) {
    			Identifier id = new Identifier("workshop",block_field.getName().toLowerCase());
    			Block block = (Block) block_field.get(null);
    			
	    		Registry.<Block>register(Registry.BLOCK, id, block);
	    		Registry.<Item>register(Registry.ITEM, id, 
	    			new BlockItem(block, craftingTableGroup));
	    	}
    	} catch (IllegalAccessException iae) {System.exit(-1);}
    }
}
