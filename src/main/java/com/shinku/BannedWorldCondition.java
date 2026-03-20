package com.shinku;

import java.time.Instant;
import java.util.Arrays;

import javax.annotation.Nonnull;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.modules.entity.condition.Condition;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class BannedWorldCondition extends Condition {

    @Nonnull
    public static final BuilderCodec<BannedWorldCondition> CODEC = 
        BuilderCodec.builder(BannedWorldCondition.class, BannedWorldCondition::new, Condition.BASE_CODEC)
                    .build();

    public BannedWorldCondition() {
        super();
    }
    
    public BannedWorldCondition(boolean inverse) {
        super(inverse);
    }

    @Override
    public boolean eval0(@Nonnull ComponentAccessor<EntityStore> accessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant time) {
        World currentWorld = accessor.getExternalData().getWorld();
        if (currentWorld == null) return false;

        String worldName = currentWorld.getName().toLowerCase();
        
        String[] disabledList = NotEnoughGliders.CONFIG.get().disabledInstances;

        if (disabledList != null && disabledList.length > 0) {
            return Arrays.stream(disabledList)
                         .anyMatch(bannedName -> worldName.contains(bannedName.toLowerCase()));
        }

        return false;
    }
}