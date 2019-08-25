package org.cyclops.cyclopscore.metadata;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Item translation key exporter.
 */
public class RegistryExportableItemTranslationKeys implements IRegistryExportable {

    @Override
    public JsonObject export() {
        JsonObject element = new JsonObject();

        JsonArray elements = new JsonArray();
        element.add("items", elements);
        for (ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            Item value = ForgeRegistries.ITEMS.getValue(key);
            ItemStack itemStack = new ItemStack(value);
            String translationKey = itemStack.getTranslationKey();
            if (!translationKey.endsWith(".name")) {
                translationKey += ".name";
            }

            // Forge's universal bucket doesn't override the translation key, so we do it manually
            if (value instanceof UniversalBucket) {
                translationKey = ((UniversalBucket) value).getFluid(itemStack).getUnlocalizedName();
            }

            JsonObject object = new JsonObject();
            object.addProperty("translationKey", translationKey);
            object.add("item", IRegistryExportable.serializeItemStack(itemStack));
            elements.add(object);
        }

        return element;
    }

    @Override
    public String getName() {
        return "item_translation_keys";
    }

}
