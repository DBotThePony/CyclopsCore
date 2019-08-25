package org.cyclops.cyclopscore.recipe.event;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;

/**
 * Observer interface of {@link ObservableShapedRecipe}.
 * @author rubensworks
 *
 */
public interface IRecipeOutputObserver {

	/**
	 * Called when a recipe output is asked from a recipe.
	 * @param craftingGrid The input crafting grid.
	 * @param output The original recipe output.
	 * @return An optionally different output.
	 */
	public ItemStack getRecipeOutput(CraftingInventory craftingGrid, ItemStack output);
	
}
