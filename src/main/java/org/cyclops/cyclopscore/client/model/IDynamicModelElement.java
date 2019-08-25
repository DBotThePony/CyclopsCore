package org.cyclops.cyclopscore.client.model;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Interface for blocks and items which can have a dynamic model.
 * @author rubensworks
 */
public interface IDynamicModelElement {

    /**
     * @return If this block has a dynamic model.
     */
    public boolean hasDynamicModel();

    /**
     * Should return not null if {@link IDynamicModelElement#hasDynamicModel()} is true.
     * This will only be called once.
     * @return A dynamic model instance.
     */
    @OnlyIn(Dist.CLIENT)
    public IBakedModel createDynamicModel();

}
