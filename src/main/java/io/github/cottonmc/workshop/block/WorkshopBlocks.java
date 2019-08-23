package io.github.cottonmc.workshop.block;

import io.github.cottonmc.workshop.Workshop;
import io.github.cottonmc.workshop.block.entity.WorkshopBlockEntities;
import io.github.cottonmc.workshop.item.WorkshopItems;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.FabricItemTags;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WorkshopBlocks {

    /** This block is for making molds. */
    //public static final Block MOLD_TABLE = new Block(
    //		FabricBlockSettings.of(Material.WOOD, MaterialColor.BROWN)
    //		.build());
    
    /** These blocks are for crafting tool parts and materials. */
    //public static final Block WOOD_CUTTING_TABLE = new Block(Block.Settings.copy(MOLD_TABLE));
    public static final Block TOOL_FURNACE = new ToolFurnaceBlock(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES).build());
    //public static final Block TANNING_CAULDRON = new Block(Block.Settings.copy(Blocks.CAULDRON));
    //public static final Block METAL_ANVIL = new Block(Block.Settings.copy(Blocks.ANVIL));
    
    /** These blocks are for enchanted smelting and smithing, respectively. */
    //public static final Block ENCHANTED_FURNACE = new Block(Block.Settings.copy(Blocks.BLAST_FURNACE));
    //public static final Block ENCHANTED_ANVIL = new Block(Block.Settings.copy(METAL_ANVIL));
    
    public static void init() {
    	/*register("mold_table", MOLD_TABLE);
    	
    	register("wood_cutting_table", WOOD_CUTTING_TABLE);
    	register("tanning_cauldron", TANNING_CAULDRON);*/
    	register("tool_furnace", TOOL_FURNACE);
    	/*register("metal_anvil", METAL_ANVIL);
    	
    	register("enchanted_furnace", ENCHANTED_FURNACE);
    	register("enchanted_anvil", ENCHANTED_ANVIL);*/
    }
    
    public static Block register(String name, Block block) {
    	Registry.register(Registry.BLOCK, new Identifier(Workshop.MODID, name), block);
    	WorkshopItems.register(name, new BlockItem(block, WorkshopItems.defaultSettings()));
		return block;
    }
}
