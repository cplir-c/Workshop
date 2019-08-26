package io.github.cottonmc.workshop.block;

import io.github.cottonmc.workshop.Workshop;
import io.github.cottonmc.workshop.block.controller.ToolFurnaceController;
import io.github.cottonmc.workshop.block.entity.ToolFurnaceBlockEntity;
import io.github.cottonmc.workshop.item.WorkshopItems;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.BlockContext;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class WorkshopBlocks {
	//prevents blocks from being placed in the blockspace above a tool furnace or the line
	public static Block BLOCKER;

    /** This block is for making molds. */
    //public static final Block MOLD_TABLE = new Block(
    //		FabricBlockSettings.of(Material.WOOD, MaterialColor.BROWN)
    //		.build());
    
    /** These blocks are for crafting tool parts and materials. */
    //public static final Block WOOD_CUTTING_TABLE = new Block(Block.Settings.copy(MOLD_TABLE));
    public static Block TOOL_FURNACE;

    //public static final Block TANNING_CAULDRON = new Block(Block.Settings.copy(Blocks.CAULDRON));
    //public static final Block METAL_ANVIL = new Block(Block.Settings.copy(Blocks.ANVIL));
    
    /** These blocks are for enchanted smelting and smithing, respectively. */
    //public static final Block ENCHANTED_FURNACE = new Block(Block.Settings.copy(Blocks.BLAST_FURNACE));
    //public static final Block ENCHANTED_ANVIL = new Block(Block.Settings.copy(METAL_ANVIL));

	public static BlockEntityType<ToolFurnaceBlockEntity> TOOL_FURNACE_BE;
    
    public static void init() {
    	BLOCKER = Registry.register(Registry.BLOCK, new Identifier(Workshop.MODID, "blocker"), new BlockerBlock(FabricBlockSettings.of(Material.STONE).noCollision().dropsNothing().strength(3.5f, 3.5f).build()));
    	/*register("mold_table", MOLD_TABLE);
    	
    	register("wood_cutting_table", WOOD_CUTTING_TABLE);
    	register("tanning_cauldron", TANNING_CAULDRON);*/
    	TOOL_FURNACE = register("tool_furnace", new ToolFurnaceBlock(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES).strength(3.5f, 3.5f).build()));
    	/*register("metal_anvil", METAL_ANVIL);
    	
    	register("enchanted_furnace", ENCHANTED_FURNACE);
    	register("enchanted_anvil", ENCHANTED_ANVIL);*/

    	TOOL_FURNACE_BE = register("tool_furnace", ToolFurnaceBlockEntity::new, WorkshopBlocks.TOOL_FURNACE);

		ContainerProviderRegistry.INSTANCE.registerFactory(
				new Identifier(Workshop.MODID, "tool_furnace"),
				(syncId, id, player, buf) ->
						new ToolFurnaceController(syncId, player.inventory,
								BlockContext.create(player.world, buf.readBlockPos())));
    }
    
    public static Block register(String name, Block block) {
    	Registry.register(Registry.BLOCK, new Identifier(Workshop.MODID, name), block);
    	WorkshopItems.register(name, new BlockItem(block, WorkshopItems.defaultSettings()));
		return block;
    }

	protected static <T extends BlockEntity> BlockEntityType<T> register(String name, Supplier<T> tConstructor, Block... blocks){

		BlockEntityType.Builder<T> builder = BlockEntityType.Builder.create(tConstructor, blocks);
		BlockEntityType<T> type = builder.build(null);

		Registry.register(Registry.BLOCK_ENTITY, new Identifier(Workshop.MODID, name), type);

		return type;
	}
}
