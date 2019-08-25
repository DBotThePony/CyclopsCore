package org.cyclops.cyclopscore.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.stats.Stat;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Block with a gui.
 *
 * Implement {@link #getContainer(BlockState, World, BlockPos)} to specify the gui.
 *
 * Optionally implement {@link #getOpenStat()} to specify a stat on gui opening.
 *
 * @author rubensworks
 *
 */
public abstract class BlockGui extends Block {

    public BlockGui(Block.Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (super.onBlockActivated(blockState, world, blockPos, player, hand, rayTraceResult)) {
            return true;
        }

        // Drop through if the player is sneaking
        if (player.isSneaking()) {
            return false;
        }

        if (!world.isRemote()) {
            INamedContainerProvider containerProvider = this.getContainer(blockState, world, blockPos);
            if (containerProvider != null) {
                player.openContainer(containerProvider);
                Stat<ResourceLocation> openStat = this.getOpenStat();
                if (openStat != null) {
                    player.addStat(openStat);
                }
            }
        }

        return true;
    }

    /**
     * @return An optional gui opening statistic.
     */
    @Nullable
    protected Stat<ResourceLocation> getOpenStat() {
        return null;
    }

}
