package org.cyclops.cyclopscore.infobook.pageelement;

import com.google.common.collect.Maps;
import lombok.Data;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import org.cyclops.cyclopscore.CyclopsCore;
import org.cyclops.cyclopscore.Reference;
import org.cyclops.cyclopscore.helper.EntityHelpers;
import org.cyclops.cyclopscore.network.packet.AdvancementRewardsObtainPacket;

import java.util.List;
import java.util.Map;

/**
 * A holder for advancements and rewards.
 * @author rubensworks
 */
@Data
public class AdvancementRewards {

    private static final Map<String, AdvancementRewards> ACHIEVEMENT_REWARDS = Maps.newHashMap();

    public static String NBT_KEY_OBTAINED_PREFIX = Reference.MOD_ID + ":" + "obtainedAdvancements:";

    private final String id;
    private final List<ResourceLocation> advancements;
    private final List<IReward> rewards;

    public AdvancementRewards(String id, List<ResourceLocation> advancements, List<IReward> rewards) {
        this.id = id;
        this.advancements = advancements;
        this.rewards = rewards;
        if (ACHIEVEMENT_REWARDS.put(id, this) != null) {
            throw new IllegalArgumentException(String.format("Duplicate advancements rewards id '%s' was found.", id));
        }
    }

    public static AdvancementRewards getAdvancementRewards(String achievementRewardsId) {
        return ACHIEVEMENT_REWARDS.get(achievementRewardsId);
    }

    protected String getNbtTagKey() {
        return NBT_KEY_OBTAINED_PREFIX + id;
    }

    public boolean isObtained(PlayerEntity player) {
        return EntityHelpers.getPersistedPlayerNbt(player).getBoolean(getNbtTagKey());
    }

    public void obtain(PlayerEntity player) {
        if (!isObtained(player)) {
            // If client-side, send packet to server, otherwise, obtain the rewards server-side
            if (player.world.isRemote()) {
                CyclopsCore._instance.getPacketHandler().sendToServer(new AdvancementRewardsObtainPacket(id));
            } else {
                for (IReward reward : getRewards()) {
                    reward.obtain(player);
                }
            }

            // Set NBT
            CompoundNBT tag = player.getEntityData();
            if (!tag.contains(PlayerEntity.PERSISTED_NBT_TAG)) {
                tag.put(PlayerEntity.PERSISTED_NBT_TAG, new CompoundNBT());
            }
            CompoundNBT persistedTag = tag.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
            persistedTag.putBoolean(getNbtTagKey(), true);
        }
    }
}
